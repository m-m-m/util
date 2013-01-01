/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.api.query.jpql;

/**
 * TODO: this class ...
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 0.9.0
 */
public interface JpqlCore {

  /**
   * This constant is used to document the concept of an <em>alias</em> or in official JPQL terminology an
   * <code>identification_variable_declaration</code>. An alias is like a variable used to identify an
   * {@link net.sf.mmm.util.entity.api.GenericEntity entity} or {@link #PROPERTY property} in statements like
   * FROM, JOIN, IN, etc.<br/>
   * E.g. in the JPQL query "SELECT DISTINCT alias2.author FROM Magazine alias1, IN(alias1.articles) alias2"
   * there are two aliases used:
   * <ul>
   * <li>alias1 is an identifier for any persistent instance of the entity <code>Magazine</code></li>.
   * <li>alias2 is an identifier for all values of the {@link #PROPERTY property} <code>articles</code> of
   * <code>alias1</code></li>.
   * </ul>
   */
  String ALIAS = "alias";

  /**
   * This constant is used to document the concept of the base path used as default prefix for
   * {@link #PROPERTY properties}. Within the API of {@link net.sf.mmm.persistence.api.query.JpqlBuilder}
   * {@link #PROPERTY properties} are provided by two arguments: a
   * {@link JpqlPropertySupport#getPropertyBasePath() base path} and the {@link #PROPERTY property}. While the
   * property can be given as {@link String} or {@link net.sf.mmm.util.pojo.path.api.TypedProperty} and may
   * NOT be <code>null</code>, the base path is always given as an optional {@link String}. We distinguish the
   * following cases for the value of a given base path:
   * <ul>
   * <li><code>null</code> - the {@link JpqlPropertySupport#getPropertyBasePath() default base path} is used.
   * Initially this is {@link JpqlFragment#getEntityAlias()}.</li>
   * <li>the empty string ("") - the property is taken as is.</li>
   * <li>otherwise - the base path is used as prefix followed by a period ('.') and the property.</li>
   * </ul>
   */
  String PROPERTY_BASE_PATH = "propertyBasePath";

  /**
   * This constant is used to document the concept of a property. A property can either be provided in a
   * type-safe way as a {@link net.sf.mmm.util.pojo.path.api.TypedProperty} (recommended) or as a regular
   * {@link String}. Properties are addressed in JPQL by a path of java bean properties separated by a dot.
   * E.g. for the {@link JpqlSegment#getEntityAlias alias} <code>myAlias</code> a final property may be
   * <code>myAlias.contact.address.city</code>. This refers to
   * <code>getContact().getAddress().getCity()</code> invoked on <code>myAlias</code>. If properties are
   * provided as {@link String} you can use
   * {@link net.sf.mmm.util.pojo.path.api.TypedProperty#createPath(String...)} to build that {@link String}
   * (e.g. <code>{@link net.sf.mmm.util.pojo.path.api.TypedProperty}{@literal
   * .}{@link net.sf.mmm.util.pojo.path.api.TypedProperty#createPath(String...) createPath}("contact", "address", "city")</code>
   * for "contact.address.city").<br/>
   * <b>NOTE:</b><br/>
   * In this API a property is always given together with a {@link #PROPERTY_BASE_PATH base path} that will
   * automatically be added before the actual property.
   */
  String PROPERTY = "property";

  /** JPQL-keyword SELECT. */
  String JPQL_SELECT = "SELECT ";

  /** JPQL-keyword FROM. */
  String JPQL_FROM = " FROM ";

  /** JPQL-keyword DISTINCT. */
  String JPQL_DISTINCT = "DISTINCT ";

  /** JPQL-keyword JOIN. */
  String JPQL_JOIN = " JOIN ";

  /** JPQL-keyword LEFT JOIN. */
  String JPQL_LEFT_JOIN = " LEFT JOIN ";

  /** JPQL-keyword LEFT OUTER JOIN. */
  String JPQL_LEFT_OUTER_JOIN = " LEFT OUTER JOIN ";

  /** JPQL-keyword INNER JOIN. */
  String JPQL_INNER_JOIN = " INNER JOIN ";

  /** JPQL-keyword FETCH. */
  String JPQL_FETCH = "FETCH ";

  /** JPQL-keyword for start of IN(...) expression. */
  String JPQL_IN_START = " IN(";

  /** JPQL-keyword for end of IN(...) expression. */
  String JPQL_IN_END = ") ";

  /** JPQL-keyword ORDER BY. */
  String JPQL_ORDER_BY = " ORDER BY ";

  /** JPQL-keyword GROUP BY. */
  String JPQL_GROUP_BY = " GROUP BY ";

  /** JPQL-keyword HAVING. */
  String JPQL_HAVING = " HAVING ";

  /** JPQL-keyword new. */
  String JPQL_NEW = "new ";

  /** JPQL-keyword WHERE. */
  String JPQL_WHERE = " WHERE ";

  /** JPQL-conjunction for AND. */
  String JPQL_CONJUNCTION_AND = " AND ";

  /** JPQL-conjunction for AND. */
  String JPQL_CONJUNCTION_OR = " OR ";

  /** JPQL-keyword for NOT (negation). */
  String JPQL_NOT = "NOT ";

  /** JPQL-operator LIKE. */
  String JPQL_OPERATOR_LIKE = "LIKE";

  /** JPQL-operator LIKE. */
  String JPQL_OPERATOR_NOT_LIKE = "NOT " + JPQL_OPERATOR_LIKE;

  /** JPQL-operator equal (=). */
  String JPQL_OPERATOR_EQUAL = "=";

  /** JPQL-operator not equal (<>). */
  String JPQL_OPERATOR_NOT_EQUAL = "<>";

  /** JPQL-operator greater (>). */
  String JPQL_OPERATOR_GREATER = ">";

  /** JPQL-operator greater-or-equal (>=). */
  String JPQL_OPERATOR_GREATER_OR_EQUAL = ">=";

  /** JPQL-operator less (<). */
  String JPQL_OPERATOR_LESS = "<";

  /** JPQL-operator less-or-equal (<=). */
  String JPQL_OPERATOR_LESS_OR_EQUAL = "<=";

  /** JPQL-operator BETWEEN. <b>ATTENTION:</b> Has to be used in the form "BETWEEN :min AND :max". */
  String JPQL_OPERATOR_BETWEEN = " BETWEEN ";

  /** JPQL-operator NOT BETWEEN. <b>ATTENTION:</b> Has to be used in the form "NOT BETWEEN :min AND :max". */
  String JPQL_OPERATOR_NOT_BETWEEN = " NOT BETWEEN ";

  /** JPQL-condition IS MEMBER. */
  String JPQL_CONDITION_IS_MEMBER = " IS MEMBER";

  /** JPQL-condition IS NOT MEMBER. */
  String JPQL_CONDITION_IS_NOT_MEMBER = " IS NOT MEMBER";

  /** JPQL-condition IS NULL. */
  String JPQL_CONDITION_IS_NULL = " IS NULL";

  /** JPQL-condition IS NOT NULL. */
  String JPQL_CONDITION_IS_NOT_NULL = " IS NOT NULL";

  /** JPQL-condition IS EMPTY. */
  String JPQL_CONDITION_IS_EMPTY = " IS EMPTY";

  /** JPQL-condition IS NOT EMPTY. */
  String JPQL_CONDITION_IS_NOT_EMPTY = " IS NOT EMPTY";

  /** JPQL-keyword for indexed parameter. */
  String JPQL_PARAMETER = "?";

}
