/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.base.query;

import net.sf.mmm.util.lang.api.Conjunction;
import net.sf.mmm.util.lang.api.SortOrder;
import net.sf.mmm.util.property.base.expression.SqlOperator;

/**
 * This is the interface for an SQL dialect.
 *
 * @author hohwille
 * @since 8.0.0
 */
public interface SqlDialect {

  /**
   * @return the {@code SELECT} statement keyword.
   */
  default String select() {

    return "SELECT ";
  }

  /**
   * @return the {@code INSERT} statement keyword.
   */
  default String insertInto() {

    return insert() + into();
  }

  /**
   * @return the {@code INSERT} statement keyword.
   */
  default String insert() {

    return "INSERT ";
  }

  /**
   * @return the {@code INTO} keyword.
   */
  default String into() {

    return "INTO ";
  }

  /**
   * @return the {@code UPDATE} statement keyword.
   */
  default String update() {

    return "UPDATE ";
  }

  /**
   * @return the {@code DELETE} statement keyword.
   */
  default String delete() {

    return "DELETE ";
  }

  /**
   * @return the {@code FROM} selector keyword.
   */
  default String from() {

    return "FROM ";
  }

  /**
   * @return the {@code WHERE} clause keyword.
   */
  default String where() {

    return " WHERE ";
  }

  /**
   * @return the {@code ORDER BY} clause keyword.
   */
  default String orderBy() {

    return " ORDER BY ";
  }

  /**
   * @return the {@code GROUP BY} clause keyword.
   */
  default String groupBy() {

    return " GROUP BY ";
  }

  /**
   * @return the {@code HAVING} clause keyword.
   */
  default String having() {

    return " HAVING ";
  }

  /**
   * @return the {@code FOR UPDATE} keyword.
   */
  default String forUpdate() {

    return " FOR UPDATE";
  }

  /**
   * @return the {@code SET} keyword.
   */
  default String set() {

    return " SET ";
  }

  /**
   * @return the assignment of a {@link #set() SET} expression.
   */
  default Object setAssignment() {

    return " = ";
  }

  /**
   * @return the {@code LIKE} condition keyword to match a pattern.
   */
  default String like() {

    return " LIKE ";
  }

  /**
   * @return the {@code NOT} keyword to negate expressions or conditions.
   */
  default String not() {

    return "NOT ";
  }

  /**
   * @return the {@code IS} condition keyword.
   */
  default String is() {

    return " IS ";
  }

  /**
   * @return the {@link #is() IS} {@link #literalNull() NULL} condition.
   */
  default String isNull() {

    return is() + literalNull();
  }

  /**
   * @return the {@link #is() IS} {@link #not() NOT} {@link #literalNull() NULL} condition.
   */
  default String isNotNull() {

    return is() + not() + literalNull();
  }

  /**
   * @return the {@link #is() IS} {@link #empty() EMPTY} condition.
   */
  default String isEmpty() {

    return is() + empty();
  }

  /**
   * @return the {@link #is() IS} {@link #not() NOT} {@link #empty() EMPTY} condition.
   */
  default String isNotEmpty() {

    return is() + not() + empty();
  }

  /**
   * @return the {@code ESCAPE} keyword to supply an escape character for {@link #like()} expressions. Will be the empty
   *         {@link String} if NOT support via this {@link SqlDialect} (maybe only via query metadata).
   */
  default String escape() {

    return " ESCAPE ";
  }

  /**
   * @param escape the character to escape {@link #like()} wildcards such as '%' and '_'.
   * @return the {@code ESCAPE} clause to supply an escape character for {@link #like()} expressions. Will be the empty
   *         {@link String} if NOT support via this {@link SqlDialect} (maybe only via query metadata).
   */
  default String escape(char escape) {

    String sql = escape();
    if (sql.isEmpty()) {
      return sql;
    }
    if (escape == '\'') {
      return sql + "\"" + escape + "\"";
    }
    return sql + "'" + escape + "'";
  }

  /**
   * @return the {@code LIMIT} keyword to supply the maximum number of results. Will be the empty {@link String} if NOT
   *         supported via this {@link SqlDialect} (maybe only via query metadata).
   */
  default String limit() {

    return " LIMIT ";
  }

  /**
   * @return the {@code OFFSET} (or SKIP) keyword to supply an offset. Will be the empty {@link String} if NOT support
   *         via SQL (maybe only via query metadata).
   */
  default String offset() {

    return " OFFSET ";
  }

  /**
   * @return the {@code EMPTY} condition keyword.
   */
  default String empty() {

    return " EMPTY";
  }

  /**
   * @return the {@code IN} expression keyword.
   */
  default String in() {

    return " IN ";
  }

  /**
   * @return the SQL literal for {@link Boolean#TRUE}.
   */
  default String literalTrue() {

    return "TRUE";
  }

  /**
   * @return the SQL literal for {@link Boolean#FALSE}.
   */
  default String literalFalse() {

    return "FALSE";
  }

  /**
   * @return the SQL literal for {@code null}.
   */
  default String literalNull() {

    return "NULL";
  }

  /**
   * @return the SQL keyword for {@link Conjunction#AND logical AND}.
   */
  default String and() {

    return " AND ";
  }

  /**
   * @return the SQL keyword for {@link Conjunction#OR logical OR}.
   */
  default String or() {

    return " OR ";
  }

  /**
   * @return the optional quote used to escape references such as selection source, paths, etc. to prevent keyword
   *         collisions. Will be the empty {@link String} for no quoting of references.
   */
  default String quoteReference() {

    return "\"";
  }

  /**
   * @param reference the reference such as selection source, paths, etc.
   * @return the {@link #quoteReference() potentially quoted} reference to prevent keyword collisions.
   */
  default String ref(String reference) {

    String quote = quoteReference();
    if (quote.isEmpty()) {
      return reference;
    }
    return quote + reference + quote;
  }

  /**
   * @return the SQL to start an expression.
   */
  default String startExpression() {

    return "(";
  }

  /**
   * @return the SQL to end an expression.
   */
  default String endExpression() {

    return ")";
  }

  /**
   * @param conjunction the {@link Conjunction}.
   * @return the SQL to start a {@link Conjunction} expression.
   */
  default String startConjunction(Conjunction conjunction) {

    if (conjunction.isNegation()) {
      return not() + startExpression();
    }
    return startExpression();
  }

  /**
   * @param conjunction the {@link Conjunction}.
   * @return the SQL to end a {@link Conjunction} expression.
   */
  default String endConjunction(Conjunction conjunction) {

    return endExpression();
  }

  /**
   * @param conjunction the {@link Conjunction}.
   * @return the SQL for the {@link Conjunction} such as {@link #and()}.
   */
  default String conjuction(Conjunction conjunction) {

    Conjunction c = conjunction;
    if (c.isNegation()) {
      c = c.negate();
    }
    if (c == Conjunction.OR) {
      return or();
    } else if (c == Conjunction.AND) {
      return and();
    } else {
      // fallback, should never happen
      return " " + c.name() + " ";
    }
  }

  /**
   * @param operator the {@link SqlOperator}.
   * @return the SQL for the given {@link SqlOperator}.
   */
  default String operator(SqlOperator<?, ?> operator) {

    return " " + operator.getSql() + " ";
  }

  /**
   * @param index the zero-based index of the current parameter.
   * @return the SQL for the parameter as bind variable.
   */
  default String variable(int index) {

    return "?";
  }

  /**
   * @return the SQL separator for paths in {@link #groupBy() GROUP BY} clause, {@link #orderBy() ORDER BY} expressions,
   *         etc.
   */
  default String separator() {

    return ", ";
  }

  /**
   * @param order the {@link SortOrder}. May be {@code null}.
   * @return the corresponding SQL.
   */
  default Object order(SortOrder order) {

    if (order == SortOrder.DESCENDING) {
      return " DESC";
    } else if (order == SortOrder.ASCENDING) {
      return " ASC";
    } else {
      return "";
    }
  }

}
