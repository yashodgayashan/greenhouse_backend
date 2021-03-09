package com.teamgreen.greenhouse.dao.search.dao;

import com.teamgreen.greenhouse.dao.SearchCondition;
import com.teamgreen.greenhouse.exceptions.CustomException;

import java.util.Map;

import static com.teamgreen.greenhouse.utils.MiscellaneousUtils.encapFieldWithBackTick;

public class SearchDaoUtils {

    private SearchDaoUtils() {
    }

    public static final String EQUAL = "eq";
    public static final String NIL = "nil";
    public static final String LESS_THAN = "lt";
    public static final String LESS_THAN_OR_EQUAL = "lte";
    public static final String GREATER_THAN = "gt";
    public static final String GREATER_THAN_OR_EQUAL = "gte";
    public static final String CONTAINS = "con";

    static final String OPEN_BRACKET = "(";
    static final String CLOSE_BRACKET = ")";

    public static final String IS_DELETED = "is_deleted";
    private static final String WHERE = "WHERE";

    static String addStringFilter(SearchCondition searchCondition, String tableColumn, String fieldQueryName,
                                  Map<String, Object> map) throws CustomException {
        String condition = searchCondition.getCondition();
        String value = searchCondition.getValue();

        String query;
        String colanTableField = ":" + fieldQueryName;
        String spaceAndSpace = " AND ";
        if (EQUAL.equals(condition)) {
            query = encapFieldWithBackTick(tableColumn) + " = " + colanTableField + spaceAndSpace;
            map.put(fieldQueryName, value);
        } else if (CONTAINS.equals(condition)) {
            query = encapFieldWithBackTick(tableColumn) + " like " + colanTableField + spaceAndSpace; // '%?%'
            map.put(fieldQueryName, "%" + value + "%");
        } else if (NIL.equals(condition)) {
            query = encapFieldWithBackTick(tableColumn) + " is " + colanTableField + spaceAndSpace;
            map.put(fieldQueryName, value);
        } else {
            throw new CustomException("invalid filter condition, " + fieldQueryName + ":" + condition);
        }
        return query;
    }

    public static String addIsDeletedClause(SearchDao searchDao, String query) {
        String andIsDeletedClause = " AND " + IS_DELETED + " = 0";
        String isDeletedClause = " WHERE " + IS_DELETED + " = 0";

        if (query.contains(WHERE)) {
            query = query + andIsDeletedClause;
        } else {
            query = query + isDeletedClause;
        }
        return query;
    }

    static String addPostQueryClause(String query, SearchDao searchDao, boolean applySizeFilters) {
        query = query.trim();
        if (query.substring(query.length() - 5).equals("WHERE")) {
            query = query.replace("WHERE", "");
        } else if (query.substring(query.length() - 3).equals("AND")) {
            int length = query.length();
            query = query.substring(0, length - 3) + "";
        }

        // add `is_deleted` clause
        query = addIsDeletedClause(searchDao, query);

        // add size and from filters
        if (applySizeFilters) {
            query = query + " LIMIT " + searchDao.getSize() + " OFFSET " + searchDao.getFrom();
        }
        return query;
    }

}
