package wangmin.message.message_center_service.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.google.common.collect.Lists;
import wangmin.message.core.entity.Message;
import wangmin.message.core.entity.MessageQuery;
import wangmin.message.core.entity.MessageStatus;
import wangmin.message.message_center_service.entity.row_mapper.MessageRowMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

@Repository
public class MessageDaoImpl implements MessageDao {
    private static final Logger logger = LoggerFactory.getLogger(MessageDaoImpl.class);
	private static final RowMapper<Message> beanPropertyRowMapper = new MessageRowMapper();
	
    @Autowired
    private JdbcTemplate jdbcTemplate;

    
    @Override
    public Message create(final Message message) {
    	try {
	        final String sql = "insert into `message` (`id`, `source`, `retry`, `status`, `create_time`, `update_time`, `queue`, `content`) values(?,?,?,?,?,?,?,?)";

            int insertRowCount = jdbcTemplate.update(
                    sql,
                    message.id, message.source, message.retry, message.status.getValue(), message.createTime, message.updateTime, message.queue, message.content);
	
	        if (1 == insertRowCount)
	        	return message;
	        else
	        	return null;
    	} catch (DuplicateKeyException e) {
            logger.info("DuplicateKeyException");
        	throw e;
    	}
    }

    @Override
    public int updateById(Message message) {
    	final String sql = "update `message` set `source`=?, `retry`=?, `status`=?, `update_time`=?, `queue`=?, `content`=? where id=?";
        
        return jdbcTemplate.update(
                sql,
                message.source, message.retry, message.status.getValue(), message.updateTime, message.queue, message.content,
                message.id);
    }
    @Override
    public int updateWithoutContentById(Message message) {
        final String sql = "update `message` set `source`=?, `retry`=?, `status`=?, `create_time`=?, `update_time`=?, `queue`=? where id=?";

        return jdbcTemplate.update(
                sql,
                message.source, message.retry, message.status.getValue(), new java.sql.Date(message.createTime.getTime()), new java.sql.Date(message.updateTime.getTime()), message.queue,
                message.id);
    }
    @Override
    public int updateByIdAndStatus(Message message, MessageStatus messageStatus) {
        final String sql = "update `message` set `source`=?, `retry`=?, `create_time`=?, `update_time`=?, `queue`=?, `content`=? where `id`=? AND `status`=?";

        return jdbcTemplate.update(
                sql,
                message.source, message.retry, new java.sql.Date(message.createTime.getTime()), new java.sql.Date(message.updateTime.getTime()), message.queue, message.content,
                message.id, message.status.getValue());
    }
    
    @Override
    public int deleteById(String id) {
    	final String sql = "DELETE FROM `message` where `id`=?";
        return jdbcTemplate.update(sql, id);
    }
    @Override
    public int deleteByIds(List<String> ids) {
        if (null == ids || ids.isEmpty())
            return 0;

        StringBuilder sb = new StringBuilder();
        for (int i=ids.size()-1; i>=0; --i) {
            sb.append('?');
            sb.append(',');
        }
        sb.setLength(sb.length()-1);

        final String sql = "DELETE FROM `message` WHERE `id` IN (" + sb.toString() + ")";
        return jdbcTemplate.update(sql, ids.toArray());
    }


    @Override
    public Message findOneById(String id) {
    	final String sql = MessageRowMapper.selectAllSql + " WHERE `id`=?";
        List<Message> userList = jdbcTemplate.query(sql, beanPropertyRowMapper, id);
        if (userList.isEmpty()) {
            return null;
        }
        return userList.get(0);
    }
    @Override
    public Message findOneByIdAndStatus(String id, MessageStatus messageStatus) {
        final String sql = MessageRowMapper.selectAllSql + " WHERE `id`=? AND `status`=?";
        List<Message> userList = jdbcTemplate.query(sql, beanPropertyRowMapper, id, messageStatus.getValue());
        if (userList.isEmpty()) {
            return null;
        }
        return userList.get(0);
    }
    @Override
    public List<Message> findListByIds(Collection<String> ids) {
    	if (null == ids || ids.isEmpty())
    		return Lists.newArrayList();
    	
    	StringBuilder sb = new StringBuilder();
    	for (int i=ids.size()-1; i>=0; --i) {
			sb.append('?');
			sb.append(',');
    	}
    	sb.setLength(sb.length()-1);
    	
        final String sql = MessageRowMapper.selectAllSql + " WHERE `id` IN (" + sb.toString() + ")";
        return jdbcTemplate.query(sql, ids.toArray(), beanPropertyRowMapper);
    }
    @Override
    public List<Message> findListByIdsAndStatus(Collection<String> ids, MessageStatus messageStatus)  {
        if (null == ids || ids.isEmpty() || null == messageStatus)
            return Lists.newArrayList();

        StringBuilder sb = new StringBuilder();
        for (int i=ids.size()-1; i>=0; --i) {
            sb.append('?');
            sb.append(',');
        }
        sb.setLength(sb.length()-1);

        final String sql = MessageRowMapper.selectAllSql + " WHERE `id` IN (" + sb.toString() + ") AND `status`="+messageStatus.getValue();
        return jdbcTemplate.query(sql, ids.toArray(), beanPropertyRowMapper);
    }
    @Override
    public List<Message> findAll() {
        return jdbcTemplate.query(MessageRowMapper.selectAllSql, beanPropertyRowMapper);
    }
    @Override
    public List<String> findIdByMessageQuery(MessageQuery messageQuery) {
        List<Object> paramList = Lists.newArrayList();
        String sql = messageQuery.generateSelectIdAndParamList(paramList);
        return jdbcTemplate.queryForList(sql, paramList.toArray(), String.class);
    }
    @Override
    public List<Message> findByMessageQuery(MessageQuery messageQuery) {
        List<Object> paramList = Lists.newArrayList();
        String sql = messageQuery.generateSelectAllAndParamList(paramList);
        return jdbcTemplate.query(sql, paramList.toArray(), beanPropertyRowMapper);
    }

    @Override
    public List<Message> findUnconsumedMessage(int limit) {
        final String sql = MessageRowMapper.selectAllSql
                + " WHERE (`status`=" + MessageStatus.MessageStatus_confirmed.getValue() + " OR `status`=" + MessageStatus.MessageStatus_sending.getValue() + ")"
                + " AND `update_time` <= date_sub(now(), interval `retry`*20 second)"
                + " ORDER BY `update_time`"
                + " LIMIT " + limit;
        return jdbcTemplate.query(sql, beanPropertyRowMapper);
    }
    @Override
    public List<String> findUnconsumedMessageId(int limit) {
        final String sql = "SELECT id FROM `message` WHERE (`status`=" + MessageStatus.MessageStatus_confirmed.getValue() + " OR `status`=" + MessageStatus.MessageStatus_sending.getValue() + ")"
                + " AND `update_time` <= date_sub(now(), interval `retry`*20 second)"
                + " ORDER BY `update_time`"
                + " LIMIT " + limit;
        return jdbcTemplate.queryForList(sql, String.class);
    }
}
