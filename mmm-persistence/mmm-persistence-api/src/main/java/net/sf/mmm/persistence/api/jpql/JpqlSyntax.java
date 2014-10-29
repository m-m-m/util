/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.api.jpql;

/**
 * This interface is (mis)used to document the syntax the <a
 * href="http://docs.oracle.com/cd/E17904_01/apirefs.1111/e13946/ejb3_langref.html#ejb3_langref_select">Java
 * Persistence Query Language (JPQL)</a>. JPQL is the language for {@link #JPQL_STATEMENT statements} against
 * persistent entities independent of the mechanism used to store those entities. It is part of the Java
 * Persistence API (JPA).
 *
 * @see #JPQL_STATEMENT
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface JpqlSyntax extends JpqlConstants {

  /**
   * This is the root element of the {@link JpqlSyntax} and represents any statement. <br>
   * The BNF definition is as following: <br>
   * {@link #JPQL_STATEMENT} ::= {@link #SELECT_STATEMENT} | {@link #UPDATE_STATEMENT} |
   * {@link #DELETE_STATEMENT}
   */
  JpqlSyntax JPQL_STATEMENT = null;

  /**
   * A Select Statement in JPQL is a {@link #JPQL_STATEMENT} used to search on persistent entities. It will
   * result in a query object and will only read from the store without causing modifications. <br>
   * The BNF definition is as following: <br>
   * {@link #SELECT} ::= {@link #SELECT_CLAUSE} {@link #FROM_CLAUSE} [ {@link #WHERE_CLAUSE}] [
   * {@link #GROUP_BY_CLAUSE}] [{@link #HAVING_CLAUSE}] [ {@link #ORDER_BY_CLAUSE}]
   */
  JpqlSyntax SELECT_STATEMENT = null;

  /**
   * The actual SELECT clause of a {@link #SELECT_STATEMENT}. <br>
   * The BNF definition is as following: <br>
   * {@link #SELECT_CLAUSE} ::= {@link #SELECT SELECT} [{@link #DISTINCT}] {@link #SELECT_EXPRESSION} {,
   * {@link #SELECT_EXPRESSION} &#125; {@literal *}
   */
  JpqlSyntax SELECT_CLAUSE = null;

  /**
   * The actual FROM clause of a {@link #JPQL_STATEMENT}. <br>
   * The BNF definition is as following: <br>
   *
   * {@link #FROM_CLAUSE} ::= {@link #FROM} {@link #IDENTIFICATION_VARIABLE_DECLARATION} {, {
   * {@link #IDENTIFICATION_VARIABLE_DECLARATION} | {@link #COLLECTION_MEMBER_DECLARATION} &#125;&#125;
   * {@literal *}
   */
  JpqlSyntax FROM_CLAUSE = null;

  /**
   * An expression of a {@link #SELECT_CLAUSE} that specifies what to select. <br>
   * The BNF definition is as following: <br>
   *
   * {@link #SELECT_EXPRESSION} ::= {@link #SINGLE_VALUED_PATH_EXPRESSION} | {@link #AGGREGATE_EXPRESSION} |
   * {@link #IDENTIFICATION_VARIABLE} | {@link #OBJECT OBJECT}({@link #IDENTIFICATION_VARIABLE})|
   * {@link #CONSTRUCTOR_EXPRESSION}
   */
  JpqlSyntax SELECT_EXPRESSION = null;

  /**
   * An expression of a {@link #SELECT_CLAUSE} that creates new transient objects using the specified
   * constructor. <br>
   * The BNF definition is as following: <br>
   * {@link #CONSTRUCTOR_EXPRESSION} ::= {@link #NEW NEW} {@link #CONSTRUCTOR_NAME}( {@link #CONSTRUCTOR_ITEM}
   * {, {@link #CONSTRUCTOR_ITEM} {@literal *})
   */
  JpqlSyntax CONSTRUCTOR_EXPRESSION = null;

  /**
   *
   * {@link #CONSTRUCTOR_ITEM} ::= {@link #SINGLE_VALUED_PATH_EXPRESSION} | {@link #AGGREGATE_EXPRESSION}
   */
  JpqlSyntax CONSTRUCTOR_ITEM = null;

  /**
   * The {@link java.lang.reflect.Constructor#getName() name of the constructor} to invoke for creating
   * transient result objects in a constructor query.
   *
   * @see #CONSTRUCTOR_EXPRESSION
   */
  JpqlSyntax CONSTRUCTOR_NAME = null;

  /**
   *
   * {@link #AGGREGATE_EXPRESSION} ::= {{@link #AVG} |{@link #MAX} | {@link #MIN} | {@link #SUM} ([
   * {@link #DISTINCT}] {@link #STATE_FIELD_PATH_EXPRESSION}) | {@link #COUNT} ([{@link #DISTINCT}]
   * {@link #IDENTIFICATION_VARIABLE} | {@link #STATE_FIELD_PATH_EXPRESSION} |
   * {@link #SINGLE_VALUED_ASSOCIATION_PATH_EXPRESSION})
   */
  JpqlSyntax AGGREGATE_EXPRESSION = null;

  /**
   *
   * {@link #RANGE_VARIABLE_DECLARATION} ::= {@link #ABSTRACT_SCHEMA_NAME} [{@link #AS}]
   * {@link #IDENTIFICATION_VARIABLE}
   */
  JpqlSyntax RANGE_VARIABLE_DECLARATION = null;

  /**
   *
   * {@link #IDENTIFICATION_VARIABLE_DECLARATION} ::= {@link #RANGE_VARIABLE_DECLARATION} { {@link #JOIN} |
   * {@link #FETCH_JOIN} &#125;{@literal *}
   */
  JpqlSyntax IDENTIFICATION_VARIABLE_DECLARATION = null;

  /**
   * A simple variable to use as identifier, typically called <em>alias</em>. It is like a variable used to
   * identify an {@link javax.persistence.Entity entity} (see
   * {@link net.sf.mmm.util.entity.api.PersistenceEntity}) or an expression in statements like FROM, JOIN, IN,
   * etc. <br>
   * E.g. in the JPQL query "SELECT DISTINCT alias2.author FROM Magazine alias1, IN(alias1.articles) alias2"
   * there are two aliases used:
   * <ul>
   * <li>alias1 is an identifier for any persistent instance of the entity <code>Magazine</code></li>
   * <li>alias2 is an identifier for all values of the property <code>articles</code> of <code>alias1</code></li>
   * </ul>
   */
  JpqlSyntax IDENTIFICATION_VARIABLE = null;

  /**
   *
   * {@link #JOIN} ::= {@link #JOIN_SPEC} {@link #JOIN_ASSOCIATION_PATH_EXPRESSION} [{@link #AS}]
   * {@link #IDENTIFICATION_VARIABLE}
   */
  JpqlSyntax JOIN = null;

  /**
   *
   * {@link #FETCH_JOIN} ::= {@link #JOIN_SPEC} {@link #FETCH} {@link #JOIN_ASSOCIATION_PATH_EXPRESSION}
   */
  JpqlSyntax FETCH_JOIN = null;

  /**
   *
   * {@link #JOIN_SPEC} ::= [{@link #LEFT} [{@link #OUTER}]|{@link #INNER}] {@link JpqlConstants#JOIN JOIN}
   */
  JpqlSyntax JOIN_SPEC = null;

  /**
   *
   * {@link #JOIN_ASSOCIATION_PATH_EXPRESSION} ::= {@link #JOIN_COLLECTION_VALUED_PATH_EXPRESSION} |
   * {@link #JOIN_SINGLE_VALUED_ASSOCIATION_PATH_EXPRESSION}
   */
  JpqlSyntax JOIN_ASSOCIATION_PATH_EXPRESSION = null;

  /**
   *
   * {@link #JOIN_COLLECTION_VALUED_PATH_EXPRESSION} ::= {@link #IDENTIFICATION_VARIABLE}.
   * {@link #COLLECTION_VALUED_ASSOCIATION_FIELD}
   */
  JpqlSyntax JOIN_COLLECTION_VALUED_PATH_EXPRESSION = null;

  /**
   *
   * {@link #JOIN_SINGLE_VALUED_ASSOCIATION_PATH_EXPRESSION} ::= {@link #IDENTIFICATION_VARIABLE}.
   * {@link #SINGLE_VALUED_ASSOCIATION_FIELD}
   */
  JpqlSyntax JOIN_SINGLE_VALUED_ASSOCIATION_PATH_EXPRESSION = null;

  /**
   *
   * {@link #SINGLE_VALUED_PATH_EXPRESSION} ::= {@link #STATE_FIELD_PATH_EXPRESSION} |
   * {@link #SINGLE_VALUED_ASSOCIATION_PATH_EXPRESSION}
   */
  JpqlSyntax SINGLE_VALUED_PATH_EXPRESSION = null;

  /**
   *
   * {@link #ASSOCIATION_PATH_EXPRESSION} ::= {@link #COLLECTION_VALUED_PATH_EXPRESSION} |
   * {@link #SINGLE_VALUED_ASSOCIATION_PATH_EXPRESSION}
   */
  JpqlSyntax ASSOCIATION_PATH_EXPRESSION = null;

  /**
   *
   * {@link #COLLECTION_VALUED_PATH_EXPRESSION} ::= {@link #IDENTIFICATION_VARIABLE}.{
   * {@link #SINGLE_VALUED_ASSOCIATION_FIELD}.&#125;{@literal *}{@link #COLLECTION_VALUED_ASSOCIATION_FIELD}
   */
  JpqlSyntax COLLECTION_VALUED_PATH_EXPRESSION = null;

  /**
   * A {@link #COLLECTION_VALUED_ASSOCIATION_FIELD} is designated by the name of an association-field in a
   * one-to-many or a many-to-many relationship. The type of a {@link #COLLECTION_VALUED_ASSOCIATION_FIELD} is
   * a collection of values of the abstract schema type of the related entity. <br>
   * It is the {@link java.lang.reflect.Field#getName() name of a field}/property to identify.
   */
  JpqlSyntax COLLECTION_VALUED_ASSOCIATION_FIELD = null;

  /**
   *
   * {@link #SINGLE_VALUED_ASSOCIATION_PATH_EXPRESSION} ::= {@link #IDENTIFICATION_VARIABLE}.{
   * {@link #SINGLE_VALUED_ASSOCIATION_FIELD}.&#125;{@literal *} {@link #SINGLE_VALUED_ASSOCIATION_FIELD}
   */
  JpqlSyntax SINGLE_VALUED_ASSOCIATION_PATH_EXPRESSION = null;

  /**
   *
   * {@link #WHERE_CLAUSE} ::= {@link #WHERE WHERE} {@link #CONDITIONAL_EXPRESSION}
   */
  JpqlSyntax WHERE_CLAUSE = null;

  /**
   *
   * {@link #GROUP_BY_CLAUSE} ::= {@link #GROUP_BY GROUP BY} {@link #GROUP_BY_ITEM} {, {@link #GROUP_BY_ITEM}
   * &#125;{@literal *}
   */
  JpqlSyntax GROUP_BY_CLAUSE = null;

  /**
   *
   * {@link #GROUP_BY_ITEM} ::= {@link #SINGLE_VALUED_PATH_EXPRESSION} | {@link #IDENTIFICATION_VARIABLE}
   */
  JpqlSyntax GROUP_BY_ITEM = null;

  /**
   *
   * {@link #HAVING_CLAUSE} ::= {@link #HAVING} {@link #CONDITIONAL_EXPRESSION}
   */
  JpqlSyntax HAVING_CLAUSE = null;

  /**
   *
   * {@link #ORDER_BY_CLAUSE} ::= {@link #ORDER_BY ORDER BY} {@link #ORDER_BY_ITEM} {, {@link #ORDER_BY_ITEM}
   * &#125;{@literal *}
   */
  JpqlSyntax ORDER_BY_CLAUSE = null;

  /**
   *
   * {@link #ORDER_BY_ITEM} ::= {@link #STATE_FIELD_PATH_EXPRESSION} [
   * {@link net.sf.mmm.util.lang.api.SortOrder#ASCENDING ASC} |
   * {@link net.sf.mmm.util.lang.api.SortOrder#DESCENDING DESC} ]
   */
  JpqlSyntax ORDER_BY_ITEM = null;

  /**
   *
   * {@link #STATE_FIELD_PATH_EXPRESSION} ::= {{@link #IDENTIFICATION_VARIABLE} |
   * {@link #SINGLE_VALUED_ASSOCIATION_PATH_EXPRESSION}&#125;.{@link #STATE_FIELD}
   */
  JpqlSyntax STATE_FIELD_PATH_EXPRESSION = null;

  /**
   *
   * {@link #CONDITIONAL_EXPRESSION} ::= {@link #CONDITIONAL_TERM} | {@link #CONDITIONAL_EXPRESSION}
   * {@link #OR} {@link #CONDITIONAL_TERM}
   */
  JpqlSyntax CONDITIONAL_EXPRESSION = null;

  /**
   *
   * {@link #SIMPLE_CONDITIONAL_EXPRESSION} ::= {@link #COMPARISON_EXPRESSION} | {@link #BETWEEN_EXPRESSION} |
   * {@link #LIKE_EXPRESSION} | {@link #IN_EXPRESSION} | {@link #NULL_COMPARISON_EXPRESSION} |
   * {@link #EMPTY_COLLECTION_COMPARISON_EXPRESSION} | {@link #COLLECTION_MEMBER_EXPRESSION} |
   * {@link #EXISTS_EXPRESSION}
   */
  JpqlSyntax SIMPLE_CONDITIONAL_EXPRESSION = null;

  /**
   *
   * {@link #COMPARISON_EXPRESSION} ::= {@link #STRING_EXPRESSION_COMPARISON_OPERATOR}{
   * {@link #STRING_EXPRESSION}| {@link #ALL_OR_ANY_EXPRESSION}&#125;| {@link #BOOLEAN_EXPRESSION} {=|
   * {@literal <>} {{@link #BOOLEAN_EXPRESSION} | {@link #ALL_OR_ANY_EXPRESSION}&#125; |
   * {@link #ENUM_EXPRESSION} {=|{@literal <>}&#125; {{@link #ENUM_EXPRESSION} |
   * {@link #ALL_OR_ANY_EXPRESSION}&#125; | {@link #DATETIME_EXPRESSION} {@link #COMPARISON_OPERATOR} {
   * {@link #DATETIME_EXPRESSION} | {@link #ALL_OR_ANY_EXPRESSION}&#125; | {@link #ENTITY_EXPRESSION} {= |<>
   * &#125; {{@link #ENTITY_EXPRESSION} | {@link #ALL_OR_ANY_EXPRESSION}&#125; |
   * {@link #ARITHMETIC_EXPRESSION} {@link #COMPARISON_OPERATOR} {{@link #ARITHMETIC_EXPRESSION} |
   * {@link #ALL_OR_ANY_EXPRESSION}
   */
  JpqlSyntax COMPARISON_EXPRESSION = null;

  /**
   * An operator for comparison of values.
   *
   * {@link #COMPARISON_OPERATOR} ::== |> |>= |< |<= |<>
   */
  JpqlSyntax COMPARISON_OPERATOR = null;

  /**
   * No clue what this actually is. Not explained in specification.
   */
  JpqlSyntax STRING_EXPRESSION_COMPARISON_OPERATOR = null;

  /**
   *
   * {@link #ALL_OR_ANY_EXPRESSION} ::= {{@link #ALL} |{@link #ANY} |{@link #SOME} &#125;({@link #SUBQUERY})
   */
  JpqlSyntax ALL_OR_ANY_EXPRESSION = null;

  /**
   *
   * {@link #ENTITY_EXPRESSION} ::= {@link #SINGLE_VALUED_ASSOCIATION_PATH_EXPRESSION} |
   * {@link #SIMPLE_ENTITY_EXPRESSION}
   */
  JpqlSyntax ENTITY_EXPRESSION = null;

  /**
   *
   * {@link #SIMPLE_ENTITY_EXPRESSION} ::= {@link #IDENTIFICATION_VARIABLE} | {@link #INPUT_PARAMETER}
   */
  JpqlSyntax SIMPLE_ENTITY_EXPRESSION = null;

  /**
   *
   * {@link #ENUM_EXPRESSION} ::= {@link #ENUM_PRIMARY} |({@link #SUBQUERY})
   */
  JpqlSyntax ENUM_EXPRESSION = null;

  /**
   *
   * {@link #ENUM_PRIMARY} ::= {@link #STATE_FIELD_PATH_EXPRESSION} | {@link #ENUM_LITERAL} |
   * {@link #INPUT_PARAMETER}
   */
  JpqlSyntax ENUM_PRIMARY = null;

  /** The {@link Enum#name() name of an Enum value}. */
  JpqlSyntax ENUM_LITERAL = null;

  /**
   *
   * {@link #BOOLEAN_EXPRESSION} ::= {@link #BOOLEAN_PRIMARY} |({@link #SUBQUERY})
   */
  JpqlSyntax BOOLEAN_EXPRESSION = null;

  /**
   *
   * {@link #BOOLEAN_PRIMARY} ::= {@link #STATE_FIELD_PATH_EXPRESSION} | {@link #BOOLEAN_LITERAL} |
   * {@link #INPUT_PARAMETER}
   */
  JpqlSyntax BOOLEAN_PRIMARY = null;

  /**
   * A {@link Boolean} literal, case insensitive.
   *
   * {@link #BOOLEAN_LITERAL} ::= {@link Boolean#TRUE TRUE} | {@link Boolean#FALSE FALSE}
   */
  JpqlSyntax BOOLEAN_LITERAL = null;

  /**
   *
   * {@link #NULL_COMPARISON_EXPRESSION} ::= {{@link #SINGLE_VALUED_PATH_EXPRESSION} |
   * {@link #INPUT_PARAMETER}&#125; {@link #IS IS} [{@link #NOT}] {@link #NULL NULL}
   */
  JpqlSyntax NULL_COMPARISON_EXPRESSION = null;

  /**
   *
   * {@link #EMPTY_COLLECTION_COMPARISON_EXPRESSION} ::= {@link #COLLECTION_VALUED_PATH_EXPRESSION}
   * {@link #IS IS} [{@link #NOT}] {@link #EMPTY EMPTY}
   */
  JpqlSyntax EMPTY_COLLECTION_COMPARISON_EXPRESSION = null;

  /**
   *
   * {@link #COLLECTION_MEMBER_EXPRESSION} ::= {@link #ENTITY_EXPRESSION} [{@link #NOT}] {@link #MEMBER
   * MEMBER} [{@link #OF OF}] {@link #COLLECTION_VALUED_PATH_EXPRESSION}
   */
  JpqlSyntax COLLECTION_MEMBER_EXPRESSION = null;

  /**
   *
   * {@link #COLLECTION_MEMBER_DECLARATION} ::= {@link #IN} ({@link #COLLECTION_VALUED_PATH_EXPRESSION}) [
   * {@link #AS}] {@link #IDENTIFICATION_VARIABLE}
   */
  JpqlSyntax COLLECTION_MEMBER_DECLARATION = null;

  /**
   * {@link #EXISTS_EXPRESSION} ::= [{@link #NOT}] {@link #EXISTS EXISTS}({@link #SUBQUERY})
   */
  JpqlSyntax EXISTS_EXPRESSION = null;

  /**
   *
   * {@link #IN_EXPRESSION} ::= {@link #STATE_FIELD_PATH_EXPRESSION} [{@link #NOT}] {@link #IN IN}(
   * {@link #IN_ITEM} {, {@link #IN_ITEM}&#125;{@literal *} | {@link #SUBQUERY})
   */
  JpqlSyntax IN_EXPRESSION = null;

  /**
   *
   * {@link #IN_ITEM} ::= {@link #LITERAL} | {@link #INPUT_PARAMETER}
   */
  JpqlSyntax IN_ITEM = null;

  /**
   * An arbitrary literal, such as {@link #STRING_LITERAL}, {@link #BOOLEAN_LITERAL}, or
   * {@link #NUMERIC_LITERAL}.
   */
  JpqlSyntax LITERAL = null;

  /**
   *
   * {@link #LIKE_EXPRESSION} ::= {@link #STRING_EXPRESSION} [{@link #NOT}] {@link #LIKE LIKE}
   * {@link #PATTERN_VALUE} [{@link #ESCAPE ESCAPE} #escape_character]
   */
  JpqlSyntax LIKE_EXPRESSION = null;

  /**
   * The value of a pattern in a {@link #LIKE_EXPRESSION}, e.g. <code>'%foo%'</code> to match any string that
   * {@link String#contains(CharSequence) contains} "foo". <br>
   * Unlike the more common glob-patterns (typically used and expected by end-users) SQL has its own syntax
   * that is also used in JPQL. The following table explains the two pattern styles.
   * <table border="1">
   * <tr>
   * <th>Wildcard</th>
   * <th>Glob</th>
   * <th>SQL</th>
   * </tr>
   * <tr>
   * <td>Any substring (zero or more characters).</td>
   * <td>*</td>
   * <td>%</td>
   * </tr>
   * <tr>
   * <td>A single character.</td>
   * <td>?</td>
   * <td>_</td>
   * </tr>
   * <tr>
   * <td>Any single character out of the given list.</td>
   * <td>&lt;not supported&gt;</td>
   * <td>[&lt;characters&gt;]</td>
   * </tr>
   * <tr>
   * <td>Any single character NOT in the given list.</td>
   * <td>&lt;not supported&gt;</td>
   * <td>[^&lt;characters&gt;]<br>
   * or [!&lt;characters&gt;]</td>
   * </tr>
   * </table>
   */
  JpqlSyntax PATTERN_VALUE = null;

  /**
   * A {@link #BETWEEN} expression to test value {@link net.sf.mmm.util.value.api.Range}s.
   *
   * {@link #BETWEEN_EXPRESSION} ::= {@link #ARITHMETIC_EXPRESSION} [{@link #NOT}] {@link #BETWEEN BETWEEN}
   * {@link #ARITHMETIC_EXPRESSION} {@link #AND} {@link #ARITHMETIC_EXPRESSION} | {@link #STRING_EXPRESSION} [
   * {@link #NOT}] {@link #BETWEEN} {@link #STRING_EXPRESSION} {@link #AND} {@link #STRING_EXPRESSION} |
   * {@link #DATETIME_EXPRESSION} [ {@link #NOT}] {@link #BETWEEN} {@link #DATETIME_EXPRESSION} {@link #AND}
   * {@link #DATETIME_EXPRESSION}
   */
  JpqlSyntax BETWEEN_EXPRESSION = null;

  /**
   *
   * {@link #ARITHMETIC_EXPRESSION} ::= {@link #SIMPLE_ARITHMETIC_EXPRESSION} | ({@link #SUBQUERY})
   */
  JpqlSyntax ARITHMETIC_EXPRESSION = null;

  /**
   *
   * {@link #SIMPLE_ARITHMETIC_EXPRESSION} ::= {@link #ARITHMETIC_TERM} |
   * {@link #SIMPLE_ARITHMETIC_EXPRESSION} {+ |- } {@link #ARITHMETIC_TERM}
   */
  JpqlSyntax SIMPLE_ARITHMETIC_EXPRESSION = null;

  /**
   *
   * {@link #ARITHMETIC_TERM} ::= {@link #ARITHMETIC_FACTORY} | {@link #ARITHMETIC_TERM} {* | &#125;
   * {@link #ARITHMETIC_FACTORY}
   */
  JpqlSyntax ARITHMETIC_TERM = null;

  /**
   *
   * {@link #ARITHMETIC_FACTORY} ::= [{+ |-}] #arithmetic_primary
   *
   */
  JpqlSyntax ARITHMETIC_FACTORY = null;

  /**
   *
   * {@link #ARITHMETIC_PRIMARY} ::= {@link #STATE_FIELD_PATH_EXPRESSION} | {@link #NUMERIC_LITERAL} | (
   * {@link #SIMPLE_ARITHMETIC_EXPRESSION}) | {@link #INPUT_PARAMETER} | {@link #FUNCTIONS_RETURNING_NUMERICS}
   * | {@link #AGGREGATE_EXPRESSION}
   */
  JpqlSyntax ARITHMETIC_PRIMARY = null;

  /**
   * A numeric literal such as e.g. <code>42</code> or <code>1.5</code>.
   */
  JpqlSyntax NUMERIC_LITERAL = null;

  /**
   *
   * {@link #FUNCTIONS_RETURNING_NUMERICS} ::= {@link #LENGTH}({@link #STRING_PRIMARY})| {@link #LOCATE}(
   * {@link #STRING_PRIMARY},{@link #STRING_PRIMARY} [, {@link #SIMPLE_ARITHMETIC_EXPRESSION}]) | {@link #ABS}
   * ({@link #SIMPLE_ARITHMETIC_EXPRESSION}) | {@link #SQRT}({@link #SIMPLE_ARITHMETIC_EXPRESSION}) |
   * {@link #MOD}({@link #SIMPLE_ARITHMETIC_EXPRESSION}, {@link #SIMPLE_ARITHMETIC_EXPRESSION}) |
   * {@link #SIZE}({@link #COLLECTION_VALUED_PATH_EXPRESSION})
   */
  JpqlSyntax FUNCTIONS_RETURNING_NUMERICS = null;

  /**
   * {@link #STRING_EXPRESSION} ::= {@link #STRING_PRIMARY} |({@link #SUBQUERY})
   */
  JpqlSyntax STRING_EXPRESSION = null;

  /**
   *
   * {@link #STRING_PRIMARY} ::= {@link #STATE_FIELD_PATH_EXPRESSION} | {@link #STRING_LITERAL} |
   * {@link #INPUT_PARAMETER} | {@link #FUNCTIONS_RETURNING_STRINGS} | {@link #AGGREGATE_EXPRESSION}
   */
  JpqlSyntax STRING_PRIMARY = null;

  /**
   * A {@link String} literal enclosed with single quotes, e.g. <code>'foo'</code>.
   */
  JpqlSyntax STRING_LITERAL = null;

  /**
   *
   * {@link #FUNCTIONS_RETURNING_STRINGS} ::= {@link #CONCAT}({@link #STRING_PRIMARY}, {@link #STRING_PRIMARY}
   * ) | {@link #SUBSTRING}({@link #STRING_PRIMARY}, {@link #SIMPLE_ARITHMETIC_EXPRESSION},
   * {@link #SIMPLE_ARITHMETIC_EXPRESSION})| {@link #TRIM}([[{@link #TRIM_SPECIFICATION}] [
   * {@link #TRIM_CHARACTER}] {@link #FROM}] {@link #STRING_PRIMARY}) | {@link #LOWER}({@link #STRING_PRIMARY}
   * ) | {@link #UPPER}( {@link #STRING_PRIMARY})
   */
  JpqlSyntax FUNCTIONS_RETURNING_STRINGS = null;

  /**
   *
   * {@link #TRIM_SPECIFICATION} ::= {@link #LEADING} | {@link #TRAILING} | {@link #BOTH}
   */
  JpqlSyntax TRIM_SPECIFICATION = null;

  /** The character to trim. */
  JpqlSyntax TRIM_CHARACTER = null;

  /**
   *
   * {@link #DATETIME_EXPRESSION} ::= {@link #DATETIME_PRIMARY} |({@link #SUBQUERY})
   */
  JpqlSyntax DATETIME_EXPRESSION = null;

  /**
   *
   * {@link #DATETIME_PRIMARY} ::= {@link #STATE_FIELD_PATH_EXPRESSION} | {@link #INPUT_PARAMETER} |
   * {@link #FUNCTIONS_RETURNING_DATETIME} | {@link #AGGREGATE_EXPRESSION}
   */
  JpqlSyntax DATETIME_PRIMARY = null;

  /**
   *
   * {@link #FUNCTIONS_RETURNING_DATETIME} ::= {@link #CURRENT_DATE}| {@link #CURRENT_TIME} |
   * {@link #CURRENT_TIMESTAMP}
   */
  JpqlSyntax FUNCTIONS_RETURNING_DATETIME = null;

  /**
   * An input parameter is a placeholder for a value given as parameter to the JPQL Statement. Input
   * parameters are either positional (?) or named (e.g. <code>:foo</code>).
   *
   * @see javax.persistence.Query#setParameter(int, Object)
   * @see javax.persistence.Query#setParameter(String, Object)
   */
  JpqlSyntax INPUT_PARAMETER = null;

  /**
   *
   * {@link #CONDITIONAL_TERM} ::= {@link #CONDITIONAL_FACTOR} | {@link #CONDITIONAL_TERM} {@link #AND}
   * {@link #CONDITIONAL_FACTOR}
   */
  JpqlSyntax CONDITIONAL_TERM = null;

  /**
   *
   * {@link #CONDITIONAL_FACTOR} ::= [{@link #NOT}] {@link #CONDITIONAL_PRIMARY}
   */
  JpqlSyntax CONDITIONAL_FACTOR = null;

  /**
   *
   * {@link #CONDITIONAL_PRIMARY} ::= {@link #SIMPLE_CONDITIONAL_EXPRESSION} |({@link #CONDITIONAL_EXPRESSION}
   * )
   */
  JpqlSyntax CONDITIONAL_PRIMARY = null;

  /**
   * A DELETE statement in JPQL is a {@link #JPQL_STATEMENT} used to delete persistent entities. It is similar
   * to a {@link #SELECT_STATEMENT} but will delete the {@link javax.persistence.Query#getResultList()
   * results} instead of returning them. <br>
   * The BNF definition is as following: <br>
   * {@link #DELETE_STATEMENT} ::= {@link #DELETE_CLAUSE} [ {@link #WHERE_CLAUSE}]
   */
  JpqlSyntax DELETE_STATEMENT = null;

  /**
   * The actual DELETE clause of a {@link #DELETE_STATEMENT}. <br>
   * The BNF definition is as following: <br>
   * {@link #DELETE_CLAUSE} ::= {@link #DELETE DELETE} {@link #FROM} {@link #ABSTRACT_SCHEMA_NAME} [[
   * {@link #AS}] {@link #IDENTIFICATION_VARIABLE}]
   */
  JpqlSyntax DELETE_CLAUSE = null;

  /**
   * The name (typically {@link Class#getSimpleName()}) of an {@link javax.persistence.Entity} (see
   * {@link net.sf.mmm.util.entity.api.PersistenceEntity}).
   */
  JpqlSyntax ABSTRACT_SCHEMA_NAME = null;

  /**
   * An UPDATE statement in JPQL is a {@link #JPQL_STATEMENT} used to delete persistent entities. It is
   * similar to a {@link #SELECT_STATEMENT} but will update the
   * {@link javax.persistence.Query#getResultList() results} instead of returning them. <br>
   * The BNF definition is as following: <br>
   *
   * {@link #UPDATE_STATEMENT} ::= {@link #UPDATE_CLAUSE} [{@link #WHERE_CLAUSE}]
   */
  JpqlSyntax UPDATE_STATEMENT = null;

  /**
   * The actual UPDATE clause of an {@link #UPDATE_STATEMENT}. <br>
   * The BNF definition is as following: <br>
   *
   * {@link #UPDATE_CLAUSE} ::= {@link #UPDATE} {@link #ABSTRACT_SCHEMA_NAME} [[{@link #AS}]
   * {@link #IDENTIFICATION_VARIABLE}] {@link #SET} {@link #UPDATE_ITEM} {, {@link #UPDATE_ITEM}&#125;
   * {@literal *}
   */
  JpqlSyntax UPDATE_CLAUSE = null;

  /**
   * An update assignment of a single item in a {@link #UPDATE_CLAUSE}. <br>
   * The BNF definition is as following: <br>
   *
   * {@link #UPDATE_ITEM} ::= [{@link #IDENTIFICATION_VARIABLE}.]{{@link #STATE_FIELD} |
   * {@link #SINGLE_VALUED_ASSOCIATION_FIELD} = {@link #NEW_VALUE}
   */
  JpqlSyntax UPDATE_ITEM = null;

  /**
   * A navigation path to a {@link java.lang.reflect.Field field} or property of an entity.
   *
   * {@link #STATE_FIELD} ::= {{@link #EMBEDDED_CLASS_STATE_FIELD}.&#125;{@literal *}
   * {@link #SIMPLE_STATE_FIELD}
   */
  JpqlSyntax STATE_FIELD = null;

  /**
   * A {@link #SINGLE_VALUED_ASSOCIATION_FIELD} is designated by the name of an association-field in a
   * one-to-one or many-to-one relationship. The type of a {@link #SINGLE_VALUED_ASSOCIATION_FIELD} and thus a
   * {@link #SINGLE_VALUED_ASSOCIATION_PATH_EXPRESSION} is the abstract schema type of the related entity. <br>
   * It is the {@link java.lang.reflect.Field#getName() name of a field}/property to identify.
   */
  JpqlSyntax SINGLE_VALUED_ASSOCIATION_FIELD = null;

  /**
   *
   */
  JpqlSyntax SIMPLE_STATE_FIELD = null;

  /**
   * An {@link #EMBEDDED_CLASS_STATE_FIELD} is designated by the name of an entity-state field that
   * corresponds to an embedded class. Navigation to a related entity results in a value of the related
   * entity's abstract schema type.
   *
   * @see javax.persistence.Embeddable
   */
  JpqlSyntax EMBEDDED_CLASS_STATE_FIELD = null;

  /**
   *
   * {@link #NEW_VALUE} ::= {@link #SIMPLE_ARITHMETIC_EXPRESSION} | {@link #STRING_PRIMARY} |
   * {@link #DATETIME_PRIMARY} | {@link #BOOLEAN_PRIMARY} | {@link #ENUM_PRIMARY}
   * {@link #SIMPLE_ENTITY_EXPRESSION} | {@link #NULL}
   */
  JpqlSyntax NEW_VALUE = null;

  /**
   *
   * {@link #SUBQUERY} ::= {@link #SIMPLE_SELECT_CLAUSE} {@link #SUBQUERY_FROM_CLAUSE} [{@link #WHERE_CLAUSE}]
   * [ {@link #GROUP_BY_CLAUSE}] [{@link #HAVING_CLAUSE}]
   */
  JpqlSyntax SUBQUERY = null;

  /**
   *
   * {@link #SUBQUERY_FROM_CLAUSE} ::= {@link #FROM} {@link #SUBSELECT_IDENTIFICATION_VARIABLE_DECLARATION} {,
   * {@link #SUBSELECT_IDENTIFICATION_VARIABLE_DECLARATION} &#125; {@literal *}
   */
  JpqlSyntax SUBQUERY_FROM_CLAUSE = null;

  /**
   *
   * {@link #SIMPLE_SELECT_CLAUSE} ::= {@link #SELECT SELECT} [{@link #DISTINCT}]
   * {@link #SIMPLE_SELECT_EXPRESSION}
   */
  JpqlSyntax SIMPLE_SELECT_CLAUSE = null;

  /**
   *
   * {@link #SIMPLE_SELECT_EXPRESSION} ::= {@link #SINGLE_VALUED_PATH_EXPRESSION} |
   * {@link #AGGREGATE_EXPRESSION} | {@link #IDENTIFICATION_VARIABLE}
   */
  JpqlSyntax SIMPLE_SELECT_EXPRESSION = null;

  /**
   *
   * {@link #SUBSELECT_IDENTIFICATION_VARIABLE_DECLARATION} ::= {@link #IDENTIFICATION_VARIABLE_DECLARATION} |
   * {@link #ASSOCIATION_PATH_EXPRESSION} [{@link #AS}] {@link #IDENTIFICATION_VARIABLE} |
   * {@link #COLLECTION_MEMBER_DECLARATION}
   */
  JpqlSyntax SUBSELECT_IDENTIFICATION_VARIABLE_DECLARATION = null;
}
