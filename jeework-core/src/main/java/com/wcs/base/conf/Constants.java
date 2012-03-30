package com.wcs.base.conf;

/**
 * Created by IntelliJ IDEA.
 * User: Chris
 * Date: 11-7-8
 * Time: 上午10:11
 * To change this template use File | Settings | File Templates.
 */
public interface Constants {

        // Define batch objects record size.
        public static final int DEFAULT_BATCH_SIZE = 1000;

        // Define application event audit executor name.
        public static final String AUDIT_EVENT_EXECUTOR = "auditEventExecutor";

        // Define HQL SELECT query.
        public static final String SQLMAP_KEYWORDS_FORUPDATE = "FOR UPDATE";
        public static final String HQL_SELECT_COUNT_FROM = "SELECT COUNT(*) as totalCount FROM ";
        public static final String HQL_SELECT_FROM = "SELECT obj FROM ";
        public static final String HQL_ALIAS_OBJECT = " obj ";

        // Define HQL Key Word.
        public static final String HQL_KEYWORD_OR = " or ";
        public static final String HQL_KEYWORD_AND = " and ";
        public static final String HQL_KEYWORD_AND_VALUE = " = ? and ";
        public static final String HQL_KEYWORD_WHERE = " where ";
        public static final String HQL_KEYWORD_LIKE = " like ";

        // Define HQL Placeholder sign.
        public static final String HQL_PLACEHOLDER_EQUALITY_COLON = " = :";
        public static final String HQL_PLACEHOLDER_EQUALITY = " = ";
        public static final String HQL_PLACEHOLDER_APOSTROPHE = "'";

        // Define HQL Operator sign.
        public static final String HQL_OPERATOR_LE = " <= ";
        public static final String HQL_OPERATOR_LT = " < ";
        public static final String HQL_OPERATOR_GE = " >= ";
        public static final String HQL_OPERATOR_GT = " > ";
        public static final String HQL_OPERATOR_NE = " != ";
        public static final String HQL_OPERATOR_PERCENT = "%";
        public static final String HQL_OPERATOR_BRACKET_BEFORE = "(";
        public static final String HQL_OPERATOR_BRACKET_AFTER = ")";

        // Define Prefix placeholder 'filter_'.
        public static final String PREFIX_PLACEHOLDER_FILTER = "filter_";
        
   	 	// Define page navigation string
        public static final String SUCCESS = "success";
        public static final String FAILURED = "failured";
}