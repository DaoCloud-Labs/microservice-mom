package com.yonyou.cloud.mom.demo.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ConsumerMsgExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ConsumerMsgExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andMsgKeyIsNull() {
            addCriterion("MSG_KEY is null");
            return (Criteria) this;
        }

        public Criteria andMsgKeyIsNotNull() {
            addCriterion("MSG_KEY is not null");
            return (Criteria) this;
        }

        public Criteria andMsgKeyEqualTo(String value) {
            addCriterion("MSG_KEY =", value, "msgKey");
            return (Criteria) this;
        }

        public Criteria andMsgKeyNotEqualTo(String value) {
            addCriterion("MSG_KEY <>", value, "msgKey");
            return (Criteria) this;
        }

        public Criteria andMsgKeyGreaterThan(String value) {
            addCriterion("MSG_KEY >", value, "msgKey");
            return (Criteria) this;
        }

        public Criteria andMsgKeyGreaterThanOrEqualTo(String value) {
            addCriterion("MSG_KEY >=", value, "msgKey");
            return (Criteria) this;
        }

        public Criteria andMsgKeyLessThan(String value) {
            addCriterion("MSG_KEY <", value, "msgKey");
            return (Criteria) this;
        }

        public Criteria andMsgKeyLessThanOrEqualTo(String value) {
            addCriterion("MSG_KEY <=", value, "msgKey");
            return (Criteria) this;
        }

        public Criteria andMsgKeyLike(String value) {
            addCriterion("MSG_KEY like", value, "msgKey");
            return (Criteria) this;
        }

        public Criteria andMsgKeyNotLike(String value) {
            addCriterion("MSG_KEY not like", value, "msgKey");
            return (Criteria) this;
        }

        public Criteria andMsgKeyIn(List<String> values) {
            addCriterion("MSG_KEY in", values, "msgKey");
            return (Criteria) this;
        }

        public Criteria andMsgKeyNotIn(List<String> values) {
            addCriterion("MSG_KEY not in", values, "msgKey");
            return (Criteria) this;
        }

        public Criteria andMsgKeyBetween(String value1, String value2) {
            addCriterion("MSG_KEY between", value1, value2, "msgKey");
            return (Criteria) this;
        }

        public Criteria andMsgKeyNotBetween(String value1, String value2) {
            addCriterion("MSG_KEY not between", value1, value2, "msgKey");
            return (Criteria) this;
        }

        public Criteria andMsgContentIsNull() {
            addCriterion("MSG_CONTENT is null");
            return (Criteria) this;
        }

        public Criteria andMsgContentIsNotNull() {
            addCriterion("MSG_CONTENT is not null");
            return (Criteria) this;
        }

        public Criteria andMsgContentEqualTo(String value) {
            addCriterion("MSG_CONTENT =", value, "msgContent");
            return (Criteria) this;
        }

        public Criteria andMsgContentNotEqualTo(String value) {
            addCriterion("MSG_CONTENT <>", value, "msgContent");
            return (Criteria) this;
        }

        public Criteria andMsgContentGreaterThan(String value) {
            addCriterion("MSG_CONTENT >", value, "msgContent");
            return (Criteria) this;
        }

        public Criteria andMsgContentGreaterThanOrEqualTo(String value) {
            addCriterion("MSG_CONTENT >=", value, "msgContent");
            return (Criteria) this;
        }

        public Criteria andMsgContentLessThan(String value) {
            addCriterion("MSG_CONTENT <", value, "msgContent");
            return (Criteria) this;
        }

        public Criteria andMsgContentLessThanOrEqualTo(String value) {
            addCriterion("MSG_CONTENT <=", value, "msgContent");
            return (Criteria) this;
        }

        public Criteria andMsgContentLike(String value) {
            addCriterion("MSG_CONTENT like", value, "msgContent");
            return (Criteria) this;
        }

        public Criteria andMsgContentNotLike(String value) {
            addCriterion("MSG_CONTENT not like", value, "msgContent");
            return (Criteria) this;
        }

        public Criteria andMsgContentIn(List<String> values) {
            addCriterion("MSG_CONTENT in", values, "msgContent");
            return (Criteria) this;
        }

        public Criteria andMsgContentNotIn(List<String> values) {
            addCriterion("MSG_CONTENT not in", values, "msgContent");
            return (Criteria) this;
        }

        public Criteria andMsgContentBetween(String value1, String value2) {
            addCriterion("MSG_CONTENT between", value1, value2, "msgContent");
            return (Criteria) this;
        }

        public Criteria andMsgContentNotBetween(String value1, String value2) {
            addCriterion("MSG_CONTENT not between", value1, value2, "msgContent");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("STATUS is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("STATUS is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(Integer value) {
            addCriterion("STATUS =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Integer value) {
            addCriterion("STATUS <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Integer value) {
            addCriterion("STATUS >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("STATUS >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Integer value) {
            addCriterion("STATUS <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Integer value) {
            addCriterion("STATUS <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Integer> values) {
            addCriterion("STATUS in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Integer> values) {
            addCriterion("STATUS not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Integer value1, Integer value2) {
            addCriterion("STATUS between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("STATUS not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("CREATE_TIME is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("CREATE_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("CREATE_TIME =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("CREATE_TIME <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("CREATE_TIME >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("CREATE_TIME >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("CREATE_TIME <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("CREATE_TIME <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("CREATE_TIME in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("CREATE_TIME not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("CREATE_TIME between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("CREATE_TIME not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNull() {
            addCriterion("UPDATE_TIME is null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNotNull() {
            addCriterion("UPDATE_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeEqualTo(Date value) {
            addCriterion("UPDATE_TIME =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(Date value) {
            addCriterion("UPDATE_TIME <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(Date value) {
            addCriterion("UPDATE_TIME >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("UPDATE_TIME >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(Date value) {
            addCriterion("UPDATE_TIME <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(Date value) {
            addCriterion("UPDATE_TIME <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<Date> values) {
            addCriterion("UPDATE_TIME in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<Date> values) {
            addCriterion("UPDATE_TIME not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(Date value1, Date value2) {
            addCriterion("UPDATE_TIME between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(Date value1, Date value2) {
            addCriterion("UPDATE_TIME not between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andExchangeIsNull() {
            addCriterion("EXCHANGE is null");
            return (Criteria) this;
        }

        public Criteria andExchangeIsNotNull() {
            addCriterion("EXCHANGE is not null");
            return (Criteria) this;
        }

        public Criteria andExchangeEqualTo(String value) {
            addCriterion("EXCHANGE =", value, "exchange");
            return (Criteria) this;
        }

        public Criteria andExchangeNotEqualTo(String value) {
            addCriterion("EXCHANGE <>", value, "exchange");
            return (Criteria) this;
        }

        public Criteria andExchangeGreaterThan(String value) {
            addCriterion("EXCHANGE >", value, "exchange");
            return (Criteria) this;
        }

        public Criteria andExchangeGreaterThanOrEqualTo(String value) {
            addCriterion("EXCHANGE >=", value, "exchange");
            return (Criteria) this;
        }

        public Criteria andExchangeLessThan(String value) {
            addCriterion("EXCHANGE <", value, "exchange");
            return (Criteria) this;
        }

        public Criteria andExchangeLessThanOrEqualTo(String value) {
            addCriterion("EXCHANGE <=", value, "exchange");
            return (Criteria) this;
        }

        public Criteria andExchangeLike(String value) {
            addCriterion("EXCHANGE like", value, "exchange");
            return (Criteria) this;
        }

        public Criteria andExchangeNotLike(String value) {
            addCriterion("EXCHANGE not like", value, "exchange");
            return (Criteria) this;
        }

        public Criteria andExchangeIn(List<String> values) {
            addCriterion("EXCHANGE in", values, "exchange");
            return (Criteria) this;
        }

        public Criteria andExchangeNotIn(List<String> values) {
            addCriterion("EXCHANGE not in", values, "exchange");
            return (Criteria) this;
        }

        public Criteria andExchangeBetween(String value1, String value2) {
            addCriterion("EXCHANGE between", value1, value2, "exchange");
            return (Criteria) this;
        }

        public Criteria andExchangeNotBetween(String value1, String value2) {
            addCriterion("EXCHANGE not between", value1, value2, "exchange");
            return (Criteria) this;
        }

        public Criteria andRouterKeyIsNull() {
            addCriterion("ROUTER_KEY is null");
            return (Criteria) this;
        }

        public Criteria andRouterKeyIsNotNull() {
            addCriterion("ROUTER_KEY is not null");
            return (Criteria) this;
        }

        public Criteria andRouterKeyEqualTo(String value) {
            addCriterion("ROUTER_KEY =", value, "routerKey");
            return (Criteria) this;
        }

        public Criteria andRouterKeyNotEqualTo(String value) {
            addCriterion("ROUTER_KEY <>", value, "routerKey");
            return (Criteria) this;
        }

        public Criteria andRouterKeyGreaterThan(String value) {
            addCriterion("ROUTER_KEY >", value, "routerKey");
            return (Criteria) this;
        }

        public Criteria andRouterKeyGreaterThanOrEqualTo(String value) {
            addCriterion("ROUTER_KEY >=", value, "routerKey");
            return (Criteria) this;
        }

        public Criteria andRouterKeyLessThan(String value) {
            addCriterion("ROUTER_KEY <", value, "routerKey");
            return (Criteria) this;
        }

        public Criteria andRouterKeyLessThanOrEqualTo(String value) {
            addCriterion("ROUTER_KEY <=", value, "routerKey");
            return (Criteria) this;
        }

        public Criteria andRouterKeyLike(String value) {
            addCriterion("ROUTER_KEY like", value, "routerKey");
            return (Criteria) this;
        }

        public Criteria andRouterKeyNotLike(String value) {
            addCriterion("ROUTER_KEY not like", value, "routerKey");
            return (Criteria) this;
        }

        public Criteria andRouterKeyIn(List<String> values) {
            addCriterion("ROUTER_KEY in", values, "routerKey");
            return (Criteria) this;
        }

        public Criteria andRouterKeyNotIn(List<String> values) {
            addCriterion("ROUTER_KEY not in", values, "routerKey");
            return (Criteria) this;
        }

        public Criteria andRouterKeyBetween(String value1, String value2) {
            addCriterion("ROUTER_KEY between", value1, value2, "routerKey");
            return (Criteria) this;
        }

        public Criteria andRouterKeyNotBetween(String value1, String value2) {
            addCriterion("ROUTER_KEY not between", value1, value2, "routerKey");
            return (Criteria) this;
        }

        public Criteria andInfoMsgIsNull() {
            addCriterion("INFO_MSG is null");
            return (Criteria) this;
        }

        public Criteria andInfoMsgIsNotNull() {
            addCriterion("INFO_MSG is not null");
            return (Criteria) this;
        }

        public Criteria andInfoMsgEqualTo(String value) {
            addCriterion("INFO_MSG =", value, "infoMsg");
            return (Criteria) this;
        }

        public Criteria andInfoMsgNotEqualTo(String value) {
            addCriterion("INFO_MSG <>", value, "infoMsg");
            return (Criteria) this;
        }

        public Criteria andInfoMsgGreaterThan(String value) {
            addCriterion("INFO_MSG >", value, "infoMsg");
            return (Criteria) this;
        }

        public Criteria andInfoMsgGreaterThanOrEqualTo(String value) {
            addCriterion("INFO_MSG >=", value, "infoMsg");
            return (Criteria) this;
        }

        public Criteria andInfoMsgLessThan(String value) {
            addCriterion("INFO_MSG <", value, "infoMsg");
            return (Criteria) this;
        }

        public Criteria andInfoMsgLessThanOrEqualTo(String value) {
            addCriterion("INFO_MSG <=", value, "infoMsg");
            return (Criteria) this;
        }

        public Criteria andInfoMsgLike(String value) {
            addCriterion("INFO_MSG like", value, "infoMsg");
            return (Criteria) this;
        }

        public Criteria andInfoMsgNotLike(String value) {
            addCriterion("INFO_MSG not like", value, "infoMsg");
            return (Criteria) this;
        }

        public Criteria andInfoMsgIn(List<String> values) {
            addCriterion("INFO_MSG in", values, "infoMsg");
            return (Criteria) this;
        }

        public Criteria andInfoMsgNotIn(List<String> values) {
            addCriterion("INFO_MSG not in", values, "infoMsg");
            return (Criteria) this;
        }

        public Criteria andInfoMsgBetween(String value1, String value2) {
            addCriterion("INFO_MSG between", value1, value2, "infoMsg");
            return (Criteria) this;
        }

        public Criteria andInfoMsgNotBetween(String value1, String value2) {
            addCriterion("INFO_MSG not between", value1, value2, "infoMsg");
            return (Criteria) this;
        }

        public Criteria andRetryCountIsNull() {
            addCriterion("RETRY_COUNT is null");
            return (Criteria) this;
        }

        public Criteria andRetryCountIsNotNull() {
            addCriterion("RETRY_COUNT is not null");
            return (Criteria) this;
        }

        public Criteria andRetryCountEqualTo(Integer value) {
            addCriterion("RETRY_COUNT =", value, "retryCount");
            return (Criteria) this;
        }

        public Criteria andRetryCountNotEqualTo(Integer value) {
            addCriterion("RETRY_COUNT <>", value, "retryCount");
            return (Criteria) this;
        }

        public Criteria andRetryCountGreaterThan(Integer value) {
            addCriterion("RETRY_COUNT >", value, "retryCount");
            return (Criteria) this;
        }

        public Criteria andRetryCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("RETRY_COUNT >=", value, "retryCount");
            return (Criteria) this;
        }

        public Criteria andRetryCountLessThan(Integer value) {
            addCriterion("RETRY_COUNT <", value, "retryCount");
            return (Criteria) this;
        }

        public Criteria andRetryCountLessThanOrEqualTo(Integer value) {
            addCriterion("RETRY_COUNT <=", value, "retryCount");
            return (Criteria) this;
        }

        public Criteria andRetryCountIn(List<Integer> values) {
            addCriterion("RETRY_COUNT in", values, "retryCount");
            return (Criteria) this;
        }

        public Criteria andRetryCountNotIn(List<Integer> values) {
            addCriterion("RETRY_COUNT not in", values, "retryCount");
            return (Criteria) this;
        }

        public Criteria andRetryCountBetween(Integer value1, Integer value2) {
            addCriterion("RETRY_COUNT between", value1, value2, "retryCount");
            return (Criteria) this;
        }

        public Criteria andRetryCountNotBetween(Integer value1, Integer value2) {
            addCriterion("RETRY_COUNT not between", value1, value2, "retryCount");
            return (Criteria) this;
        }

        public Criteria andBizclassNameIsNull() {
            addCriterion("BIZ_CLASS_NAME is null");
            return (Criteria) this;
        }

        public Criteria andBizclassNameIsNotNull() {
            addCriterion("BIZ_CLASS_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andBizclassNameEqualTo(String value) {
            addCriterion("BIZ_CLASS_NAME =", value, "bizclassName");
            return (Criteria) this;
        }

        public Criteria andBizclassNameNotEqualTo(String value) {
            addCriterion("BIZ_CLASS_NAME <>", value, "bizclassName");
            return (Criteria) this;
        }

        public Criteria andBizclassNameGreaterThan(String value) {
            addCriterion("BIZ_CLASS_NAME >", value, "bizclassName");
            return (Criteria) this;
        }

        public Criteria andBizclassNameGreaterThanOrEqualTo(String value) {
            addCriterion("BIZ_CLASS_NAME >=", value, "bizclassName");
            return (Criteria) this;
        }

        public Criteria andBizclassNameLessThan(String value) {
            addCriterion("BIZ_CLASS_NAME <", value, "bizclassName");
            return (Criteria) this;
        }

        public Criteria andBizclassNameLessThanOrEqualTo(String value) {
            addCriterion("BIZ_CLASS_NAME <=", value, "bizclassName");
            return (Criteria) this;
        }

        public Criteria andBizclassNameLike(String value) {
            addCriterion("BIZ_CLASS_NAME like", value, "bizclassName");
            return (Criteria) this;
        }

        public Criteria andBizclassNameNotLike(String value) {
            addCriterion("BIZ_CLASS_NAME not like", value, "bizclassName");
            return (Criteria) this;
        }

        public Criteria andBizclassNameIn(List<String> values) {
            addCriterion("BIZ_CLASS_NAME in", values, "bizclassName");
            return (Criteria) this;
        }

        public Criteria andBizclassNameNotIn(List<String> values) {
            addCriterion("BIZ_CLASS_NAME not in", values, "bizclassName");
            return (Criteria) this;
        }

        public Criteria andBizclassNameBetween(String value1, String value2) {
            addCriterion("BIZ_CLASS_NAME between", value1, value2, "bizclassName");
            return (Criteria) this;
        }

        public Criteria andBizclassNameNotBetween(String value1, String value2) {
            addCriterion("BIZ_CLASS_NAME not between", value1, value2, "bizclassName");
            return (Criteria) this;
        }

        public Criteria andConsumerClassNameIsNull() {
            addCriterion("CONSUMER_CLASS_NAME is null");
            return (Criteria) this;
        }

        public Criteria andConsumerClassNameIsNotNull() {
            addCriterion("CONSUMER_CLASS_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andConsumerClassNameEqualTo(String value) {
            addCriterion("CONSUMER_CLASS_NAME =", value, "consumerClassName");
            return (Criteria) this;
        }

        public Criteria andConsumerClassNameNotEqualTo(String value) {
            addCriterion("CONSUMER_CLASS_NAME <>", value, "consumerClassName");
            return (Criteria) this;
        }

        public Criteria andConsumerClassNameGreaterThan(String value) {
            addCriterion("CONSUMER_CLASS_NAME >", value, "consumerClassName");
            return (Criteria) this;
        }

        public Criteria andConsumerClassNameGreaterThanOrEqualTo(String value) {
            addCriterion("CONSUMER_CLASS_NAME >=", value, "consumerClassName");
            return (Criteria) this;
        }

        public Criteria andConsumerClassNameLessThan(String value) {
            addCriterion("CONSUMER_CLASS_NAME <", value, "consumerClassName");
            return (Criteria) this;
        }

        public Criteria andConsumerClassNameLessThanOrEqualTo(String value) {
            addCriterion("CONSUMER_CLASS_NAME <=", value, "consumerClassName");
            return (Criteria) this;
        }

        public Criteria andConsumerClassNameLike(String value) {
            addCriterion("CONSUMER_CLASS_NAME like", value, "consumerClassName");
            return (Criteria) this;
        }

        public Criteria andConsumerClassNameNotLike(String value) {
            addCriterion("CONSUMER_CLASS_NAME not like", value, "consumerClassName");
            return (Criteria) this;
        }

        public Criteria andConsumerClassNameIn(List<String> values) {
            addCriterion("CONSUMER_CLASS_NAME in", values, "consumerClassName");
            return (Criteria) this;
        }

        public Criteria andConsumerClassNameNotIn(List<String> values) {
            addCriterion("CONSUMER_CLASS_NAME not in", values, "consumerClassName");
            return (Criteria) this;
        }

        public Criteria andConsumerClassNameBetween(String value1, String value2) {
            addCriterion("CONSUMER_CLASS_NAME between", value1, value2, "consumerClassName");
            return (Criteria) this;
        }

        public Criteria andConsumerClassNameNotBetween(String value1, String value2) {
            addCriterion("CONSUMER_CLASS_NAME not between", value1, value2, "consumerClassName");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}