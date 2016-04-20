/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.query.base.statement;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import net.sf.mmm.util.bean.impl.BeanFactoryImpl;
import net.sf.mmm.util.lang.api.Orientation;
import net.sf.mmm.util.query.api.feature.FeatureGroupBy;
import net.sf.mmm.util.query.api.feature.FeatureLet;
import net.sf.mmm.util.query.api.feature.FeatureLimit;
import net.sf.mmm.util.query.api.feature.FeatureOrderBy;
import net.sf.mmm.util.query.api.feature.FeaturePaging;
import net.sf.mmm.util.query.api.feature.FeatureSet;
import net.sf.mmm.util.query.api.feature.FeatureValues;
import net.sf.mmm.util.query.api.feature.FeatureWhere;
import net.sf.mmm.util.query.api.feature.StatementFeature;
import net.sf.mmm.util.query.api.path.Path;
import net.sf.mmm.util.query.api.statement.Statement;
import net.sf.mmm.util.query.api.variable.Variable;
import net.sf.mmm.util.query.base.argument.Args;
import net.sf.mmm.util.query.base.example.QueryTestPropertyBean;
import net.sf.mmm.util.query.base.path.Alias;

/**
 * Test of {@link Statement} and {@link AbstractStatement}.
 *
 * @author hohwille
 */
public abstract class AbstractStatementTest<S extends Statement<QueryTestPropertyBean, S>> extends Assertions {

  private QueryTestPropertyBean prototype = BeanFactoryImpl.getInstance()
      .createPrototype(QueryTestPropertyBean.class);

  private Alias<QueryTestPropertyBean> alias = Alias.ofBean(this.prototype).as("q");

  private Set<Class<? extends StatementFeature>> features;

  protected S createStatement() {

    return createStatement(DefaultSqlDialect.INSTANCE);
  }

  protected abstract S createStatement(SqlDialect dialect);

  protected QueryTestPropertyBean getPrototype() {

    return this.prototype;
  }

  protected Alias<QueryTestPropertyBean> getAlias() {

    return this.alias;
  }

  protected abstract List<Class<? extends StatementFeature>> getFeatures();

  protected boolean hasFeature(Class<? extends StatementFeature> feature, S statement) {

    if (this.features == null) {
      this.features = new HashSet<>(getFeatures());
      for (Class<? extends StatementFeature> f : this.features) {
        assertThat(statement).isInstanceOf(f);
      }
      // polymorphism...
      if (this.features.contains(FeaturePaging.class)) {
        this.features.add(FeatureLimit.class);
      }
    }
    return this.features.contains(feature);
  }

  protected <F extends StatementFeature> F asFeature(Class<F> feature, S statement) {

    if (hasFeature(feature, statement)) {
      return feature.cast(statement);
    } else {
      assertThat(statement).isNotInstanceOf(feature);
      return null;
    }
  }

  protected abstract String getSqlStart();

  @Test
  public void testSql() {

    checkSql(DefaultSqlDialect.INSTANCE);
  }

  protected void checkSql(SqlDialect dialect) {

    // given
    S statement = createStatement(dialect);

    String as = "";
    if (isSupportingAlias()) {
      as = statement.getDialect().as(this.alias, false);
    }
    // test "empty" statement
    String sql = getSqlStart() + quote(statement, QueryTestPropertyBean.class.getSimpleName()) + as;
    assertThat(statement.getSql()).isEqualTo(sql);
    assertThat(statement.getParameters()).isEmpty();

    List<Object> variables = new ArrayList<>();
    // do the additional tests...
    sql = checkValues(statement, sql, variables);
    sql = checkSet(statement, sql, variables);
    sql = checkLet(statement, sql, variables);
    sql = checkWhere(statement, sql, variables);
    sql = checkGroupBy(statement, sql, variables);
    sql = checkOrderBy(statement, sql, variables);
    sql = checkOffset(statement, sql, variables);
    sql = checkLimit(statement, sql, variables);
    checkExtended(statement, sql, variables);
  }

  /**
   * @return see {@link AbstractStatement#isSupportingAlias()}
   */
  protected boolean isSupportingAlias() {

    return true;
  }

  @Test
  public void testSqlOptimization() {

    // given
    S statement = createStatement();
    QueryTestPropertyBean prototype = getPrototype();

    String sql = getSqlStart() + quote(statement, QueryTestPropertyBean.class.getSimpleName())
        + statement.getDialect().as(getAlias(), false);

    FeatureWhere<?> where = asFeature(FeatureWhere.class, statement);
    if (where != null) {
      // add regular equals condition to where clause
      where.where(Args.of(Boolean.FALSE).isFalse());
      assertThat(statement.getSql()).isEqualTo(sql);
      assertThat(statement.getParameters()).isEmpty();

      try {
        where.where(Args.of(Boolean.FALSE).isTrue());
        failBecauseExceptionWasNotThrown(IllegalArgumentException.class);
      } catch (IllegalArgumentException e) {
        assertThat(e).hasMessage("Expression can never match!");
      }
      assertThat(statement.getSql()).isEqualTo(sql);
      assertThat(statement.getParameters()).isEmpty();
    }
  }

  protected String quote(S statement, String name) {

    SqlDialect dialect = statement.getDialect();
    return dialect.quoteReference() + name + dialect.quoteReference();
  }

  protected String property(S statement, String propertyName) {

    String name;
    if (isSupportingAlias()) {
      name = this.alias.getName() + "." + propertyName;
    } else {
      name = propertyName;
    }
    return quote(statement, name);
  }

  protected String variable(S statement, List<Object> variables, Object value) {

    String variable = statement.getDialect().parameter(variables.size());
    variables.add(value);
    return variable;
  }

  protected String checkSet(S statement, String sql, List<Object> variables) {

    FeatureSet<?> set = asFeature(FeatureSet.class, statement);
    if (set == null) {
      return sql;
    }
    // add assignment to SET clause
    Integer age = 18;
    String sqlSet = sql + " SET " + property(statement, "Age") + " = " + variable(statement, variables, age);
    set.set(this.prototype.Age(), age);
    assertThat(statement.getSql()).isEqualTo(sqlSet);
    assertThat(statement.getParameters()).containsExactlyElementsOf(variables);

    // add 2nd assignment to SET clause
    String name = "Superman";
    sqlSet = sqlSet + ", " + property(statement, "LastName") + " = " + property(statement, "FirstName");
    set.set(this.prototype.LastName(), this.prototype.FirstName());
    assertThat(statement.getSql()).isEqualTo(sqlSet);
    assertThat(statement.getParameters()).containsExactlyElementsOf(variables);

    return sqlSet;
  }

  protected String checkLet(S statement, String sql, List<Object> variables) {

    FeatureLet<?> let = asFeature(FeatureLet.class, statement);
    if (let == null) {
      return sql;
    }
    // add variable to LET clause
    Path<QueryTestPropertyBean> path = this.alias.to(this.prototype.Parent()).to(this.prototype.Parent());
    Variable<QueryTestPropertyBean> variable = let.let(path, "pp");
    String sqlLet = sql + " LET " + statement.getDialect().variable(variable) + " = "
        + property(statement, "Parent.Parent");
    assertThat(statement.getSql()).isEqualTo(sqlLet);
    assertThat(statement.getParameters()).containsExactlyElementsOf(variables);

    return sqlLet;
  }

  protected String checkValues(S statement, String sql, List<Object> variables) {

    FeatureValues<?> values = asFeature(FeatureValues.class, statement);
    if (values == null) {
      return sql;
    }

    // add assignment to SET clause
    boolean friend = true;
    String sqlValues = sql + " (" + property(statement, "Friend") + ") VALUES ("
        + variable(statement, variables, friend) + ")";
    values.value(this.prototype.Friend(), friend);
    assertThat(statement.getSql()).isEqualTo(sqlValues);
    assertThat(statement.getParameters()).containsExactlyElementsOf(variables);

    // add 2nd assignment to SET clause
    Orientation orientation = Orientation.HORIZONTAL;
    sqlValues = sql + " (" + property(statement, "Friend") + ", " + property(statement, "Orientaton")
        + ") VALUES (" + variable(statement, variables, friend) + ", "
        + variable(statement, variables, orientation) + ")";
    values.value(this.prototype.Orientation(), orientation);
    assertThat(statement.getSql()).isEqualTo(sqlValues);
    assertThat(statement.getParameters()).containsExactlyElementsOf(variables);

    return sqlValues;
  }

  protected String checkWhere(S statement, String sql, List<Object> variables) {

    FeatureWhere<?> where = asFeature(FeatureWhere.class, statement);
    if (where == null) {
      return sql;
    }

    // add regular equals condition to where clause
    String sqlWhere = sql + " WHERE ";
    String firstName = "Heinz";
    String condFirstName = property(statement, "FirstName") + " = " + variable(statement, variables, firstName);
    where.where(this.alias.to(this.prototype.FirstName()).eq(firstName));

    assertThat(statement.getSql()).isEqualTo(sqlWhere + condFirstName);
    assertThat(statement.getParameters()).containsExactlyElementsOf(variables);

    // add between expression to where clause
    Integer min = 16;
    Integer max = 21;
    String condAge = property(statement, "Age") + " BETWEEN " + variable(statement, variables, min) + " AND "
        + variable(statement, variables, max);
    where.where(this.alias.to(this.prototype.Age()).between(min, max));
    assertThat(statement.getSql()).isEqualTo(sqlWhere + condFirstName + " AND " + condAge);
    assertThat(statement.getParameters()).containsExactlyElementsOf(variables);

    // add like expression to where clause
    String pattern = "%\\%";
    char escape = '\\';
    String condName = property(statement, "LastName") + " LIKE " + variable(statement, variables, pattern)
        + " ESCAPE '\\'";
    where.where(this.alias.to(this.prototype.LastName()).like(pattern, escape));
    assertThat(statement.getSql()).isEqualTo(sqlWhere + condFirstName + " AND " + condAge + " AND " + condName);
    assertThat(statement.getParameters()).containsExactlyElementsOf(variables);

    // add 2 expressions combined with OR to where clause
    Orientation orientation = Orientation.HORIZONTAL;
    sqlWhere = sqlWhere + condFirstName + " AND " + condAge + " AND " + condName + " AND ("
        + property(statement, "Friend") + " = FALSE OR " + property(statement, "Orientation") + " = "
        + variable(statement, variables, orientation) + ")";
    where.where(this.alias.to(this.prototype.Friend()).isFalse()
        .or(this.alias.to(this.prototype.Orientation()).eq(orientation)));
    assertThat(statement.getSql()).isEqualTo(sqlWhere);
    assertThat(statement.getParameters()).containsExactlyElementsOf(variables);

    // add pointless expression to where clause
    where.where(Args.of(Boolean.FALSE).isFalse());
    assertThat(statement.getSql()).isEqualTo(sqlWhere);
    assertThat(statement.getParameters()).containsExactlyElementsOf(variables);
    return sqlWhere;
  }

  protected String checkGroupBy(S statement, String sql, List<Object> variables) {

    FeatureGroupBy<?> groupBy = asFeature(FeatureGroupBy.class, statement);
    if (groupBy == null) {
      return sql;
    }

    // add first property to group by clause
    groupBy.groupBy(this.prototype.Age());
    String sqlGroupBy = sql + " GROUP BY " + property(statement, "Age");
    assertThat(statement.getSql()).isEqualTo(sqlGroupBy);
    assertThat(statement.getParameters()).containsExactlyElementsOf(variables);

    // add second property to group by clause
    groupBy.groupBy(this.prototype.FirstName());
    sqlGroupBy += ", " + property(statement, "FirstName");
    assertThat(statement.getSql()).isEqualTo(sqlGroupBy);
    assertThat(statement.getParameters()).containsExactlyElementsOf(variables);
    return sqlGroupBy;
  }

  protected String checkOrderBy(S statement, String sql, List<Object> variables) {

    FeatureOrderBy<?> orderBy = asFeature(FeatureOrderBy.class, statement);
    if (orderBy == null) {
      return sql;
    }

    // add first property to order by clause
    orderBy.orderBy(this.alias.to(this.prototype.Age()));
    String sqlOrderBy = sql + " ORDER BY " + property(statement, "Age");
    assertThat(statement.getSql()).isEqualTo(sqlOrderBy);
    assertThat(statement.getParameters()).containsExactlyElementsOf(variables);

    // add second property to order by clause
    orderBy.orderBy(this.alias.to(this.prototype.FirstName()));
    sqlOrderBy += ", " + property(statement, "FirstName");
    assertThat(statement.getSql()).isEqualTo(sqlOrderBy);
    assertThat(statement.getParameters()).containsExactlyElementsOf(variables);
    return sqlOrderBy;
  }

  protected String checkLimit(S statement, String sql, List<Object> variables) {

    FeatureLimit<?> sLimit = asFeature(FeatureLimit.class, statement);
    if (sLimit == null) {
      return sql;
    }

    // add limit
    int limit = 100;
    sLimit.limit(limit);
    String sqlLimit = sql;
    if (!statement.getDialect().limit().isEmpty()) {
      sqlLimit = sqlLimit + " LIMIT " + variable(statement, variables, Integer.valueOf(limit));
    }
    assertThat(statement.getSql()).isEqualTo(sqlLimit);
    assertThat(statement.getParameters()).containsExactlyElementsOf(variables);
    return sqlLimit;
  }

  protected String checkOffset(S statement, String sql, List<Object> variables) {

    FeaturePaging<?> paging = asFeature(FeaturePaging.class, statement);
    if (paging == null) {
      return sql;
    }

    // add offset
    long offset = 500;
    paging.offset(offset);
    String sqlOffset = sql;
    if (!statement.getDialect().offset().isEmpty()) {
      sqlOffset = sqlOffset + " OFFSET " + variable(statement, variables, Long.valueOf(offset));
    }
    assertThat(statement.getSql()).isEqualTo(sqlOffset);
    assertThat(statement.getParameters()).containsExactlyElementsOf(variables);
    return sqlOffset;
  }

  protected void checkExtended(S statement, String sql, List<Object> variables) {

    // nothing by default, override for additional tests...
  }

}
