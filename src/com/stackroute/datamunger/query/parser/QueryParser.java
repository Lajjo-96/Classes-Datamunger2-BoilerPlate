package com.stackroute.datamunger.query.parser;

/*There are total 4 DataMungerTest file:
 * 
 * 1)DataMungerTestTask1.java file is for testing following 4 methods
 * a)getBaseQuery()  b)getFileName()  c)getOrderByClause()  d)getGroupByFields()
 * 
 * Once you implement the above 4 methods,run DataMungerTestTask1.java
 * 
 * 2)DataMungerTestTask2.java file is for testing following 2 methods
 * a)getFields() b) getAggregateFunctions()
 * 
 * Once you implement the above 2 methods,run DataMungerTestTask2.java
 * 
 * 3)DataMungerTestTask3.java file is for testing following 2 methods
 * a)getRestrictions()  b)getLogicalOperators()
 * 
 * Once you implement the above 2 methods,run DataMungerTestTask3.java
 * 
 * Once you implement all the methods run DataMungerTest.java.This test case consist of all
 * the test cases together.
 */

import java.util.ArrayList;
import java.util.List;

public class QueryParser {


	private QueryParameter queryParameter = new QueryParameter();

	/*
	 * This method will parse the queryString and will return the object of
	 * QueryParameter class
	 */
	public QueryParameter parseQuery(String queryString) {
		QueryParser queryParser = new QueryParser();
		queryParameter.setBaseQuery(getBaseQuery(queryString));
		queryParameter.setFileName(getFileName(queryString));
		queryParameter.setOrderByFeilds(getOrderBy(queryString));
		queryParameter.setGroupByFeilds(getGroupBy(queryString));
		queryParameter.setFeilds(getFeilds(queryString));
		queryParameter.setAggregateFunctions(getAggregateFunctions(queryString));


		return queryParameter;
	}

	public String getBaseQuery(String queryString) {
		queryString = queryString.toLowerCase();
		String s = queryString.substring(queryString.indexOf("select"), queryString.indexOf("csv") + 3);
		return s;

	}

	public String getFileName(String queryString) {
		String result = "";
		queryString = queryString.toLowerCase();
		String[] name = queryString.split(" ");

		for (
				int i = 0; i < name.length; i++) {

			if (name[i].contains("from")) {
				result = name[i + 1];
			}
		}


		return result;
	}

	public ArrayList<String> getOrderBy(String queryString) {
		ArrayList<String> list = new ArrayList<>();

		String result = "";
		if (!queryString.contains("order by")) {
			return null;
		} else {
			int position = queryString.indexOf("order");
			System.out.println(position);
			result = queryString.substring(position + 9, queryString.length());
		}
		list.add(result);
		return list;
	}

	public ArrayList<String> getGroupBy(String queryString) {
		ArrayList<String> list = new ArrayList<>();

		String result = "";
		if (!queryString.contains("group by")) {
			return null;
		} else {
			int position = queryString.indexOf("group");
			int position2 = queryString.indexOf("order");
			System.out.println(position);
			if (queryString.contains("order")) {
				result = queryString.substring(position + 9, position2 - 1);
			} else
				result = queryString.substring(position + 9, queryString.length());
		}
		list.add(result);
		return list;
	}

	public ArrayList<String> getFeilds(String queryString) {
		ArrayList<String> list = new ArrayList<>();
		String result = "";
		String[] arrayResult;
		int position = queryString.indexOf("select");
//        System.out.println(position);
		int anotherPosition = queryString.indexOf("from");
		result = queryString.substring(position + 7, anotherPosition - 1);
//        System.out.println(result);
		String[] array = result.split(",");
		for (String s : array) {
			list.add(s);
		}
		return list;
	}

	public ArrayList<String> getAggregateFunctions(String queryString) {
		ArrayList<AggregateFunction> list = new ArrayList<>();
		


	}
}
	/*
	 * Extract the name of the file from the query. File name can be found after the
	 * "from" clause.
	 */

	/*
	 * 
	 * Extract the baseQuery from the query.This method is used to extract the
	 * baseQuery from the query string. BaseQuery contains from the beginning of the
	 * query till the where clause
	 */

	/*
	 * extract the order by fields from the query string. Please note that we will
	 * need to extract the field(s) after "order by" clause in the query, if at all
	 * the order by clause exists. For eg: select city,winner,team1,team2 from
	 * data/ipl.csv order by city from the query mentioned above, we need to extract
	 * "city". Please note that we can have more than one order by fields.
	 */

	/*
	 * Extract the group by fields from the query string. Please note that we will
	 * need to extract the field(s) after "group by" clause in the query, if at all
	 * the group by clause exists. For eg: select city,max(win_by_runs) from
	 * data/ipl.csv group by city from the query mentioned above, we need to extract
	 * "city". Please note that we can have more than one group by fields.
	 */

	/*
	 * Extract the selected fields from the query string. Please note that we will
	 * need to extract the field(s) after "select" clause followed by a space from
	 * the query string. For eg: select city,win_by_runs from data/ipl.csv from the
	 * query mentioned above, we need to extract "city" and "win_by_runs". Please
	 * note that we might have a field containing name "from_date" or "from_hrs".
	 * Hence, consider this while parsing.
	 */

	/*
	 * Extract the conditions from the query string(if exists). for each condition,
	 * we need to capture the following: 1. Name of field 2. condition 3. value
	 * 
	 * For eg: select city,winner,team1,team2,player_of_match from data/ipl.csv
	 * where season >= 2008 or toss_decision != bat
	 * 
	 * here, for the first condition, "season>=2008" we need to capture: 1. Name of
	 * field: season 2. condition: >= 3. value: 2008
	 * 
	 * the query might contain multiple conditions separated by OR/AND operators.
	 * Please consider this while parsing the conditions.
	 * 
	 */

	/*
	 * Extract the logical operators(AND/OR) from the query, if at all it is
	 * present. For eg: select city,winner,team1,team2,player_of_match from
	 * data/ipl.csv where season >= 2008 or toss_decision != bat and city =
	 * bangalore
	 * 
	 * The query mentioned above in the example should return a List of Strings
	 * containing [or,and]
	 */

	/*
	 * Extract the aggregate functions from the query. The presence of the aggregate
	 * functions can determined if we have either "min" or "max" or "sum" or "count"
	 * or "avg" followed by opening braces"(" after "select" clause in the query
	 * string. in case it is present, then we will have to extract the same. For
	 * each aggregate functions, we need to know the following: 1. type of aggregate
	 * function(min/max/count/sum/avg) 2. field on which the aggregate function is
	 * being applied.
	 * 
	 * Please note that more than one aggregate function can be present in a query.
	 * 
	 * 
	 */
