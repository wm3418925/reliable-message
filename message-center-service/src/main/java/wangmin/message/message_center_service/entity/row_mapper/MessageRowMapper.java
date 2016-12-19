package wangmin.message.message_center_service.entity.row_mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import wangmin.message.core.entity.Message;
import wangmin.message.core.entity.MessageStatus;

public class MessageRowMapper implements RowMapper<Message> {
	public static final String selectAllSql = "select `id`, `source`, `retry`, `status`, `create_time`, `update_time`, `queue`, `content` from `message`";

	@Override
	public Message mapRow(ResultSet rs, int rowNumber) throws SQLException {
		Message element = new Message();

		element.id = rs.getString(1);
		element.source = rs.getString(2);
		element.retry = rs.getInt(3);
		element.status = MessageStatus.valueOf(rs.getInt(4));
		element.createTime = rs.getDate(5);
		element.updateTime = rs.getDate(6);
		element.queue = rs.getString(7);
		element.content = rs.getString(8);

		return element;
	}
}
