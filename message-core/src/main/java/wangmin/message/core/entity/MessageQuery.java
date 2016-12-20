package wangmin.message.core.entity;

import com.google.common.collect.Lists;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by wm on 2016/12/18.
 */
public class MessageQuery implements Serializable {

    public enum Field implements Serializable {
        id("id"),
        source("source"),
        queue("queue"),
        content("content"),
        retry("retry"),
        status("status"),
        createTime("create_time"),
        updateTime("update_time");

        protected final String fieldName;
        private Field(String fieldName) {
            this.fieldName = fieldName;
        }
        public String getFieldName() {return fieldName;}
    }
    public enum CompareOperator implements Serializable {
        equal("="),
        notEqual("!="),
        biggerThen(">"),
        smallerThan("<"),
        notBiggerThen("<="),
        notSmallerThan(">=");

        protected final String operatorName;
        private CompareOperator(String operatorName) {
            this.operatorName = operatorName;
        }
        public String getOperatorName() {return operatorName;}
    }



    public class FieldCompareElement implements Serializable {
        public Field field;
        public CompareOperator compareOperator;
        public Object value;
        public FieldCompareElement() {}
        public FieldCompareElement(Field field, CompareOperator compareOperator, Object value) {
            this.field = field;
            this.compareOperator = compareOperator;
            this.value = value;
        }
    }
    private List<FieldCompareElement> whereParamList = Lists.newArrayList();

    // 添加where语句的列
    public MessageQuery whereId(CompareOperator compareOperator, String id) {
        this.whereParamList.add(new FieldCompareElement(Field.id, compareOperator, id));
        return this;
    }
    public MessageQuery whereSource(CompareOperator compareOperator, String source) {
        this.whereParamList.add(new FieldCompareElement(Field.source, compareOperator, source));
        return this;
    }
    public MessageQuery whereQueue(CompareOperator compareOperator, String queue) {
        this.whereParamList.add(new FieldCompareElement(Field.queue, compareOperator, queue));
        return this;
    }
    public MessageQuery whereContent(CompareOperator compareOperator, String content) {
        this.whereParamList.add(new FieldCompareElement(Field.content, compareOperator, content));
        return this;
    }
    public MessageQuery whereRetry(CompareOperator compareOperator, Integer retry) {
        this.whereParamList.add(new FieldCompareElement(Field.retry, compareOperator, retry));
        return this;
    }
    public MessageQuery whereStatus(CompareOperator compareOperator, MessageStatus status) {
        this.whereParamList.add(new FieldCompareElement(Field.status, compareOperator, status.getValue()));
        return this;
    }
    public MessageQuery whereCreateTime(CompareOperator compareOperator, Date createTime) {
        this.whereParamList.add(new FieldCompareElement(Field.createTime, compareOperator, createTime));
        return this;
    }
    public MessageQuery whereUpdateTime(CompareOperator compareOperator, Date updateTime) {
        this.whereParamList.add(new FieldCompareElement(Field.updateTime, compareOperator, updateTime));
        return this;
    }



    private StringBuilder orderByClause = new StringBuilder();
    // 添加排序列
    public MessageQuery orderBy(Field field, boolean isDesc) {
        if (0 == orderByClause.length()) {
            orderByClause.append("ORDER BY `");
        } else {
            orderByClause.append(",`");
        }
        orderByClause.append(field.getFieldName());
        orderByClause.append('`');

        if (isDesc)
            orderByClause.append(" DESC");

        return this;
    }


    private String limitClause = "";
    public MessageQuery limit(long limit) {
        limitClause = "limit " + limit;
        return this;
    }
    public MessageQuery limit(long offset, long limit) {
        limitClause = "limit " + offset + "," + limit;
        return this;
    }


    private StringBuilder appendWhereOrAndClause(StringBuilder sb, Field field, CompareOperator compareOperator) {
        if (0 == sb.length())
            sb.append("WHERE `");
        else
            sb.append("AND `");

        sb.append(field.getFieldName());
        sb.append('`');
        sb.append(compareOperator.getOperatorName());
        sb.append("? ");
        return sb;
    }

    // 生成sql语句
    private String generateClauseWithoutSelectAndParamList(List<Object> paramList) {
        StringBuilder sb = new StringBuilder();

        for (FieldCompareElement fce : whereParamList) {
            appendWhereOrAndClause(sb, fce.field, fce.compareOperator);
            paramList.add(fce.value);
        }

        sb.append(' ');
        sb.append(orderByClause.toString());
        sb.append(' ');
        sb.append(limitClause);

        return sb.toString();
    }
    // 生成sql语句
    public String generateSelectAllAndParamList(List<Object> paramList) {
        return "SELECT `id`, `source`, `retry`, `status`, `create_time`, `update_time`, `queue`, `content` FROM `message` "
                + generateClauseWithoutSelectAndParamList(paramList);
    }
    // 生成sql语句
    public String generateSelectIdAndParamList(List<Object> paramList) {
        return "SELECT `id` FROM `message` "
                + generateClauseWithoutSelectAndParamList(paramList);
    }

    public static void main(String[] argv) {
        MessageQuery q = new MessageQuery();
        List<Object> paramList = Lists.newArrayList();
        String sql = q
                .whereContent(CompareOperator.equal, "{}")
                .whereStatus(CompareOperator.notEqual, MessageStatus.MessageStatus_confirmed)
                .whereCreateTime(CompareOperator.biggerThen, new Date())
                .whereQueue(CompareOperator.equal, "wm")
                .whereUpdateTime(CompareOperator.notBiggerThen, new Date())
                .whereContent(CompareOperator.equal, "{}")
                .whereSource(CompareOperator.equal,"gf")
                .whereRetry(CompareOperator.smallerThan, 3)
                .orderBy(Field.id, false)
                .orderBy(Field.source, true)
                .limit(10)
                .limit(0,10)
                .generateSelectAllAndParamList(paramList);


        System.out.println("sql="+sql);
        int i=0;
        for (Object param : paramList) {
            System.out.print("param["+i+"]="+param+"; ");
            ++i;
        }
        System.out.println("");
    }
}
