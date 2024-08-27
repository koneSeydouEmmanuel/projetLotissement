
/*
 * Created on 2021-09-10 ( Time 15:58:06 )
 * Generator tool : Telosys Tools Generator ( version 3.1.2 )
 * Copyright 2018 Geo. All Rights Reserved.
 */

package com.ilot.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import com.ilot.utils.contract.SearchParam;
import com.ilot.utils.enums.OperatorEnum;


/*
 * Created on 2022-02-21 ( Time 10:40:43 )
 * Generator tool : Telosys Tools Generator ( version 3.1.2 )
 * Copyright 2018 Geo. All Rights Reserved.
 */

/**
 * @author geovani.anoman
 */
public class CriteriaUtils {

	final static String START_SUFFIX = "_start";


	final static String END_SUFFIX   = "_end";
	final static String LIST_SUFFIX  = "_list";

	/**
	 * @param listOfQuery
	 * @return
	 */
	public static String getCriteriaByListOfQuery(List<String> listOfQuery) {
		StringBuilder query = new StringBuilder();
		if (!listOfQuery.isEmpty()) {
			for (String q : listOfQuery) {
				query.append(q);
				if (!listOfQuery.get(listOfQuery.size() - 1).equals(q)) {
					query.append(" and ");
				}
			}
		}
		return query.toString();
	}

	/**
	 * @param fieldName
	 * @param fieldValue
	 * @param jpqlFieldName
	 * @param fieldType
	 * @param fieldParam
	 * @param params
	 * @param locale
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static <T> String generateCriteria(String fieldName, Object fieldValue, String jpqlFieldName, String fieldType, SearchParam<T> fieldParam, HashMap<String, Object> params, Integer index,
											  Locale locale) throws Exception {
		String  req      = "";
		String  operator = "";
		T       start    = null;
		T       end      = null;
		List<T> datas    = null;

		if (fieldParam != null) {
			operator = fieldParam.getOperator();
			start    = fieldParam.getStart();
			end      = fieldParam.getEnd();
			datas    = fieldParam.getDatas();
		}
		fieldName += "_" + index;

		if (OperatorEnum.IS_BETWEEN_OPERATOR(operator) && (start == null || end == null)) {
			throw new Exception("Field not given (start, end)");
		}

		switch (fieldType) {
			case "String":
				switch (operator) {
					case OperatorEnum.NOT_EQUAL_1:
					case OperatorEnum.NOT_EQUAL_2:
						req += String.format(" %1$s <> :%2$s ", jpqlFieldName, fieldName);
						break;
					case OperatorEnum.EQUAL:
						req += String.format(" %1$s = :%2$s ", jpqlFieldName, fieldName);
						break;
					case OperatorEnum.START_WTIH:
						req += String.format(" %1$s LIKE :%2$s ", jpqlFieldName, fieldName);
						fieldValue = fieldValue + "%";
						break;
					case OperatorEnum.END_WTIH:
						req += String.format(" %1$s LIKE :%2$s ", jpqlFieldName, fieldName);
						fieldValue = "%" + fieldValue;
						break;
					case OperatorEnum.IS_NULL:
						req += String.format(" %1$s IS NULL", jpqlFieldName);
						break;
					case OperatorEnum.IN:
						params.put(fieldName + LIST_SUFFIX, datas);
						req += String.format(" %1$s IN :%2$s ", jpqlFieldName, fieldName + LIST_SUFFIX);
						break;
					case OperatorEnum.NOT_IN:
						params.put(fieldName + LIST_SUFFIX, datas);
						req += String.format(" %1$s NOT IN :%2$s ", jpqlFieldName, fieldName + LIST_SUFFIX);
						break;
					default:
						req += String.format(" %1$s LIKE :%2$s ", jpqlFieldName, fieldName);
						fieldValue = "%" + fieldValue + "%";
						break;
				}
				break;
			case "Integer":
			case "Long":
			case "Double":
			case "Decimal":
			case "BigInteger":
			case "Float":
				switch (operator) {
					case OperatorEnum.NOT_EQUAL_1:
					case OperatorEnum.NOT_EQUAL_2:
						req += String.format(" %1$s <> :%2$s ", jpqlFieldName, fieldName);
						break;
					case OperatorEnum.LESS:
						req += String.format(" %1$s < :%2$s ", jpqlFieldName, fieldName);
						break;
					case OperatorEnum.LESS_OR_EQUAL:
						req += String.format(" %1$s <= :%2$s ", jpqlFieldName, fieldName);
						break;
					case OperatorEnum.MORE:
						req += String.format(" %1$s > :%2$s ", jpqlFieldName, fieldName);
						break;
					case OperatorEnum.MORE_OR_EQUAL:
						req += String.format(" %1$s >= :%2$s ", jpqlFieldName, fieldName);
						break;
					case OperatorEnum.BETWEEN:
						params.put(fieldName + START_SUFFIX, start);
						params.put(fieldName + END_SUFFIX, end);
						req += String.format(" %1$s >= :%2$s and %1$s <= :%3$s ", jpqlFieldName, fieldName + START_SUFFIX, fieldName + END_SUFFIX);
						break;
					case OperatorEnum.BETWEEN_OUT:
						params.put(fieldName + START_SUFFIX, start);
						params.put(fieldName + END_SUFFIX, end);
						req += String.format(" %1$s > :%2$s and %1$s < :%3$s ", jpqlFieldName, fieldName + START_SUFFIX, fieldName + END_SUFFIX);
						break;
					case OperatorEnum.BETWEEN_LEFT_OUT:
						params.put(fieldName + START_SUFFIX, start);
						params.put(fieldName + END_SUFFIX, end);
						req += String.format(" %1$s > :%2$s and %1$s <= :%3$s ", jpqlFieldName, fieldName + START_SUFFIX, fieldName + END_SUFFIX);
						break;
					case OperatorEnum.BETWEEN_RIGHT_OUT:
						params.put(fieldName + START_SUFFIX, start);
						params.put(fieldName + END_SUFFIX, end);
						req += String.format(" %1$s >= :%2$s and %1$s < :%3$s ", jpqlFieldName, fieldName + START_SUFFIX, fieldName + END_SUFFIX);
						break;
					case OperatorEnum.IS_NULL:
						req += String.format(" %1$s IS NULL", jpqlFieldName);
						break;
					case OperatorEnum.IN:
						params.put(fieldName + LIST_SUFFIX, datas);
						req += String.format(" %1$s IN :%2$s ", jpqlFieldName, fieldName + LIST_SUFFIX);
						break;
					case OperatorEnum.NOT_IN:
						params.put(fieldName + LIST_SUFFIX, datas);
						req += String.format(" %1$s NOT IN :%2$s ", jpqlFieldName, fieldName + LIST_SUFFIX);
						break;
					default:
						req += String.format(" %1$s = :%2$s ", jpqlFieldName, fieldName);
						break;
				}
				break;
			case "Date":
				switch (operator) {
					case OperatorEnum.NOT_EQUAL_1:
					case OperatorEnum.NOT_EQUAL_2:
						req += String.format(" DATE(%1$s) <> DATE(:%2$s) ", jpqlFieldName, fieldName);
						break;
					case OperatorEnum.LESS:
						req += String.format(" DATE(%1$s) < DATE(:%2$s) ", jpqlFieldName, fieldName);
						break;
					case OperatorEnum.LESS_OR_EQUAL:
						req += String.format(" DATE(%1$s) <= DATE(:%2$s) ", jpqlFieldName, fieldName);
						break;
					case OperatorEnum.MORE:
						req += String.format(" DATE(%1$s) > DATE(:%2$s) ", jpqlFieldName, fieldName);
						break;
					case OperatorEnum.MORE_OR_EQUAL:
						req += String.format(" DATE(%1$s) >= DATE(:%2$s) ", jpqlFieldName, fieldName);
						break;
					case OperatorEnum.BETWEEN:
						start = (T) new SimpleDateFormat("dd/MM/yyyy").parse(start.toString());
						end = (T) new SimpleDateFormat("dd/MM/yyyy").parse(end.toString());
						params.put(fieldName + START_SUFFIX, start);
						params.put(fieldName + END_SUFFIX, end);
						req += String.format(" DATE(%1$s) >= DATE(:%2$s) and DATE(%1$s) <= DATE(:%3$s) ", jpqlFieldName, fieldName + START_SUFFIX, fieldName + END_SUFFIX);
						break;
					case OperatorEnum.BETWEEN_OUT:
						start = (T) new SimpleDateFormat("dd/MM/yyyy").parse(start.toString());
						end = (T) new SimpleDateFormat("dd/MM/yyyy").parse(end.toString());
						params.put(fieldName + START_SUFFIX, start);
						params.put(fieldName + END_SUFFIX, end);
						req += String.format(" DATE(%1$s) > DATE(:%2$s) and DATE(%1$s) < DATE(:%3$s) ", jpqlFieldName, fieldName + START_SUFFIX, fieldName + END_SUFFIX);
						break;
					case OperatorEnum.BETWEEN_LEFT_OUT:
						start = (T) new SimpleDateFormat("dd/MM/yyyy").parse(start.toString());
						end = (T) new SimpleDateFormat("dd/MM/yyyy").parse(end.toString());
						params.put(fieldName + START_SUFFIX, start);
						params.put(fieldName + END_SUFFIX, end);
						req += String.format(" DATE(%1$s) > DATE(:%2$s) and DATE(%1$s) <= DATE(:%3$s) ", jpqlFieldName, fieldName + START_SUFFIX, fieldName + END_SUFFIX);
						break;
					case OperatorEnum.BETWEEN_RIGHT_OUT:
						start = (T) new SimpleDateFormat("dd/MM/yyyy").parse(start.toString());
						end = (T) new SimpleDateFormat("dd/MM/yyyy").parse(end.toString());
						params.put(fieldName + START_SUFFIX, start);
						params.put(fieldName + END_SUFFIX, end);
						req += String.format(" DATE(%1$s) >= DATE(:%2$s) and DATE(%1$s) < DATE(:%3$s) ", jpqlFieldName, fieldName + START_SUFFIX, fieldName + END_SUFFIX);
						break;
					default:
						req += String.format(" DATE(%1$s) = DATE(:%2$s) ", jpqlFieldName, fieldName);
						break;
				}
				break;
			case "Boolean":
				switch (operator) {
					case OperatorEnum.NOT_EQUAL_1:
					case OperatorEnum.NOT_EQUAL_2:
						req += jpqlFieldName + " <> :" + fieldName;
						break;
					default:
						req += jpqlFieldName + " = :" + fieldName;
						break;
				}
				break;
		}
		if (!OperatorEnum.IS_BETWEEN_OPERATOR(operator) && "Date".equals(fieldType)) {
			fieldValue = new SimpleDateFormat("dd/MM/yyyy").parse(fieldValue.toString());
		}
		if (!OperatorEnum.IS_BETWEEN_OPERATOR(operator) && !OperatorEnum.OPERATOR_NOT_NEEDS_ANY_VALUE(operator) && !OperatorEnum.IS_IN_OPERATOR(operator)) {
			params.put(fieldName, fieldValue);
		}

		return req;
	}
}