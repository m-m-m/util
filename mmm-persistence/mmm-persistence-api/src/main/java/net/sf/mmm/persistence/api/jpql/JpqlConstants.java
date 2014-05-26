/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.api.jpql;

/**
 * This interface defines constants with keywords for {@link JpqlSyntax JPQL}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface JpqlConstants {

  /** JPQL-keyword SELECT. */
  String SELECT = "SELECT ";

  /** JPQL-keyword DELETE. */
  String DELETE = "DELETE ";

  /** JPQL-keyword UPDATE. */
  String UPDATE = "UPDATE ";

  /** JPQL-keyword SET. */
  String SET = "SET ";

  /** JPQL-keyword FROM. */
  String FROM = " FROM ";

  /** JPQL-keyword DISTINCT. */
  String DISTINCT = "DISTINCT ";

  /** JPQL-keyword AS. */
  String AS = "AS ";

  /** JPQL-keyword DISTINCT. */
  String EXISTS = "EXISTS ";

  /** JPQL-keyword JOIN. */
  String JOIN = " JOIN ";

  /**
   * JPQL-keyword LEFT.
   *
   * @see #LEFT_JOIN
   */
  String LEFT = " LEFT";

  /**
   * JPQL-keyword OUTER.
   *
   * @see #LEFT_OUTER_JOIN
   */
  String OUTER = " OUTER";

  /**
   * JPQL-keyword INNER.
   *
   * @see #INNER_JOIN
   */
  String INNER = " INNER";

  /** JPQL-keyword LEFT JOIN. */
  String LEFT_JOIN = LEFT + JOIN;

  /** JPQL-keyword LEFT OUTER JOIN. */
  String LEFT_OUTER_JOIN = LEFT + OUTER + JOIN;

  /** JPQL-keyword INNER JOIN. */
  String INNER_JOIN = INNER + JOIN;

  /** JPQL-keyword FETCH. */
  String FETCH = "FETCH ";

  /** JPQL-keyword for start of IN(...) expression. */
  String JPQL_IN_START = " IN(";

  /** JPQL-keyword for end of IN(...) expression. */
  String JPQL_IN_END = ") ";

  /** JPQL-keyword ORDER BY. */
  String ORDER_BY = " ORDER BY ";

  /** JPQL-keyword GROUP BY. */
  String GROUP_BY = " GROUP BY ";

  /** JPQL-keyword HAVING. */
  String HAVING = " HAVING ";

  /** JPQL-keyword NEW. */
  String NEW = "NEW ";

  /** JPQL-keyword COUNT. */
  String COUNT = "COUNT ";

  /** JPQL-keyword OBJECT. */
  String OBJECT = "OBJECT";

  /** JPQL-keyword WHERE. */
  String WHERE = " WHERE ";

  /** JPQL-conjunction for AND. */
  String AND = " AND ";

  /** JPQL-conjunction for OR. */
  String OR = " OR ";

  /** JPQL-keyword IS. */
  String IS = " IS ";

  /** JPQL-keyword NULL. */
  String NULL = "NULL";

  /** JPQL-keyword EMPTY. */
  String EMPTY = "EMPTY";

  /** JPQL-keyword MEMBER. */
  String MEMBER = "MEMBER";

  /** JPQL-keyword OF. */
  String OF = " OF";

  /** JPQL-keyword IN. */
  String IN = "IN";

  /** JPQL-keyword for NOT (negation). */
  String NOT = "NOT ";

  /** JPQL-keyword for ESCAPE (character). */
  String ESCAPE = "ESCAPE ";

  /** JPQL-keyword ANY. */
  String ANY = "ANY";

  /** JPQL-keyword ALL. */
  String ALL = "ALL";

  /** JPQL-keyword SOME. */
  String SOME = "SOME";

  /** JPQL-operator LIKE. */
  String LIKE = "LIKE";

  /** JPQL-operator LIKE. */
  String NOT_LIKE = NOT + LIKE;

  /** JPQL-operator equal (=). */
  String EQUAL = "=";

  /** JPQL-operator not equal (<>). */
  String NOT_EQUAL = "<>";

  /** JPQL-operator greater (>). */
  String GREATER = ">";

  /** JPQL-operator greater-or-equal (>=). */
  String GREATER_OR_EQUAL = ">=";

  /** JPQL-operator less (<). */
  String LESS = "<";

  /** JPQL-operator less-or-equal (<=). */
  String LESS_OR_EQUAL = "<=";

  /** JPQL-operator BETWEEN. <b>ATTENTION:</b> Has to be used in the form "BETWEEN :min AND :max". */
  String BETWEEN = " BETWEEN ";

  /** JPQL-operator NOT BETWEEN. <b>ATTENTION:</b> Has to be used in the form "NOT BETWEEN :min AND :max". */
  String OPERATOR_NOT_BETWEEN = "NOT" + BETWEEN;

  /** JPQL-condition IS MEMBER. */
  String IS_MEMBER = IS + MEMBER;

  /** JPQL-condition IS NOT MEMBER. */
  String IS_NOT_MEMBER = IS + NOT + MEMBER;

  /** JPQL-condition IS NULL. */
  String IS_NULL = IS + NULL;

  /** JPQL-condition IS NOT NULL. */
  String IS_NOT_NULL = IS + NOT + NULL;

  /** JPQL-condition IS EMPTY. */
  String IS_EMPTY = IS + EMPTY;

  /** JPQL-condition IS NOT EMPTY. */
  String IS_NOT_EMPTY = IS + NOT + EMPTY;

  /** JPQL-keyword for indexed parameter. */
  String PARAMETER = "?";

  /** The aggregation function for the average value. */
  String AVG = "AVG";

  /** The aggregation function for the maximum value. */
  String MAX = "MAX";

  /** The aggregation function for the minimum value. */
  String MIN = "MIN";

  /** The aggregation function for the sum of the values. */
  String SUM = "SUM";

  /** JPQL-function to determine the length (LENGTH). */
  String LENGTH = "LENGTH";

  /** JPQL-function to {@link String#concat(String) concat} strings (CONCAT). */
  String CONCAT = "CONCAT";

  /** JPQL-function for the {@link String#substring(int, int) substring} of a string (SUBSTRING). */
  String SUBSTRING = "SUBSTRING";

  /** JPQL-function for the {@link String#toLowerCase() lower case} of a string (LOWER). */
  String LOWER = "LOWER";

  /** JPQL-function for the {@link String#toUpperCase() upper case} of a string (UPPER). */
  String UPPER = "UPPER";

  /** JPQL-function to {@link String#trim() trim} a string (TRIM). */
  String TRIM = "TRIM";

  /** Mode for {@link #TRIM trimming} only leading spaces. */
  String LEADING = "LEADING";

  /** Mode for {@link #TRIM trimming} only trailing spaces. */
  String TRAILING = "TRAILING";

  /** Mode for {@link #TRIM trimming} both {@link #LEADING leading} and {@link #TRAILING trailing} spaces. */
  String BOTH = "BOTH";

  /** JPQL-function to determine the square-root (SQRT). */
  String SQRT = "SQRT";

  /** JPQL-function to determine the modulo (MOD). */
  String MOD = "MOD";

  /** JPQL-function to determine the absolute value (ABS). */
  String ABS = "ABS";

  /** JPQL-function to locate some value (LOCATE). */
  String LOCATE = "LOCATE";

  /** JPQL-function to determine the size (SIZE). */
  String SIZE = "SIZE";

  /** JPQL-function to determine the current time. */
  String CURRENT_TIME = "CURRENT_TIME";

  /** JPQL-function to determine the current date. */
  String CURRENT_DATE = "CURRENT_DATE";

  /** JPQL-function to determine the current timestamp. */
  String CURRENT_TIMESTAMP = "CURRENT_TIMESTAMP";

}
