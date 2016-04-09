/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.base.query;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import net.sf.mmm.util.bean.api.Bean;
import net.sf.mmm.util.bean.api.BeanFactory;
import net.sf.mmm.util.bean.impl.BeanFactoryImpl;
import net.sf.mmm.util.bean.impl.example.ExampleBean;
import net.sf.mmm.util.lang.api.Orientation;
import net.sf.mmm.util.property.api.path.PropertyPath;
import net.sf.mmm.util.property.api.query.Statement;
import net.sf.mmm.util.property.api.query.feature.FeatureGroupBy;
import net.sf.mmm.util.property.api.query.feature.FeatureLimit;
import net.sf.mmm.util.property.api.query.feature.FeatureOrderBy;
import net.sf.mmm.util.property.api.query.feature.FeaturePaging;
import net.sf.mmm.util.property.api.query.feature.FeatureWhere;
import net.sf.mmm.util.property.api.query.feature.StatementFeature;
import net.sf.mmm.util.property.base.expression.Expressions;

/**
 * Test of {@link Statement} and {@link AbstractStatement}.
 *
 * @author hohwille
 */
public abstract class AbstractStatementTest<S extends Statement<ExampleBean, S>> extends Assertions {

  private ExampleBean prototype;

  private Set<Class<? extends StatementFeature>> features;

  protected S createStatement() {

    return createStatement(DefaultSqlDialect.INSTANCE);
  }

  protected abstract S createStatement(SqlDialect dialect);

  protected ExampleBean getPrototype() {

    if (this.prototype == null) {
      this.prototype = BeanFactoryImpl.getInstance().createPrototype(ExampleBean.class);
    }
    return this.prototype;
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

  protected String quote(S statement, String name) {

    SqlDialect dialect = statement.getSqlDialect();
    return dialect.quoteReference() + name + dialect.quoteReference();
  }

  protected String variable(S statement) {

    return variable(statement, 0);
  }

  protected String variable(S statement, int offset) {

    return statement.getSqlDialect().variable(statement.getVariables().size() + offset);
  }

  protected String checkWhere(S statement, String sql) {

    FeatureWhere<?> where = asFeature(FeatureWhere.class, statement);
    if (where == null) {
      return sql;
    }
    // add regular equals condition to where clause
    String sqlWhere = sql + " WHERE ";
    String countryCode = "DE";
    String condCountryCode = quote(statement, "CountryCode") + " = " + variable(statement);
    where.where(Expressions.of(this.prototype.CountryCode()).eq(countryCode));

    assertThat(statement.getSql()).isEqualTo(sqlWhere + condCountryCode);
    assertThat(statement.getVariables()).containsExactly(countryCode);

    // add between expression to where clause
    Integer min = 16;
    Integer max = 21;
    String condAge = quote(statement, "Age") + " BETWEEN " + variable(statement) + " AND "
        + variable(statement, 1);
    where.where(Expressions.ofNumber((PropertyPath) this.prototype.Age()).between(min, max));
    assertThat(statement.getSql()).isEqualTo(sqlWhere + "(" + condCountryCode + " AND " + condAge + ")");
    assertThat(statement.getVariables()).containsExactly(countryCode, min, max);

    // add like expression to where clause
    String pattern = "%\\%";
    char escape = '\\';
    String condName = quote(statement, "Name") + " LIKE " + variable(statement) + " ESCAPE '\\'";
    where.where(Expressions.ofString(this.prototype.Name()).like(pattern, escape));
    assertThat(statement.getSql())
        .isEqualTo(sqlWhere + "(" + condCountryCode + " AND " + condAge + " AND " + condName + ")");
    assertThat(statement.getVariables()).containsExactly(countryCode, min, max, pattern);

    // add 2 expressions combined with OR to where clause
    Orientation orientation = Orientation.HORIZONTAL;
    sqlWhere = sqlWhere + "(" + condCountryCode + " AND " + condAge + " AND " + condName + " AND ("
        + quote(statement, "Friend") + " = FALSE OR " + quote(statement, "Orientation") + " = "
        + variable(statement) + "))";
    where.where(Expressions.ofBoolean(this.prototype.Friend()).isFalse()
        .or(Expressions.of(this.prototype.Orientation()).eq(orientation)));
    assertThat(statement.getSql()).isEqualTo(sqlWhere);
    assertThat(statement.getVariables()).containsExactly(countryCode, min, max, pattern, orientation);

    // add pointless expression to where clause
    where.where(Expressions.of(Boolean.FALSE).isFalse());
    assertThat(statement.getSql()).isEqualTo(sqlWhere);
    assertThat(statement.getVariables()).containsExactly(countryCode, min, max, pattern, orientation);
    return sqlWhere;
  }

  protected String checkGroupBy(S statement, String sql) {

    FeatureGroupBy<?> groupBy = asFeature(FeatureGroupBy.class, statement);
    if (groupBy == null) {
      return sql;
    }

    List<Object> variables = new ArrayList<>(statement.getVariables());

    // add first property to group by clause
    groupBy.groupBy(this.prototype.Age());
    String sqlGroupBy = sql + " GROUP BY " + quote(statement, "Age");
    assertThat(statement.getSql()).isEqualTo(sqlGroupBy);
    assertThat(statement.getVariables()).containsExactlyElementsOf(variables);

    // add second property to group by clause
    groupBy.groupBy(this.prototype.CountryCode());
    sqlGroupBy += ", " + quote(statement, "CountryCode");
    assertThat(statement.getSql()).isEqualTo(sqlGroupBy);
    assertThat(statement.getVariables()).containsExactlyElementsOf(variables);
    return sqlGroupBy;
  }

  protected String checkOrderBy(S statement, String sql) {

    FeatureOrderBy<?> orderBy = asFeature(FeatureOrderBy.class, statement);
    if (orderBy == null) {
      return sql;
    }

    List<Object> variables = new ArrayList<>(statement.getVariables());

    // add first property to order by clause
    orderBy.orderBy(this.prototype.Age());
    String sqlOrderBy = sql + " ORDER BY " + quote(statement, "Age");
    assertThat(statement.getSql()).isEqualTo(sqlOrderBy);
    assertThat(statement.getVariables()).containsExactlyElementsOf(variables);

    // add second property to order by clause
    orderBy.orderBy(this.prototype.CountryCode());
    sqlOrderBy += ", " + quote(statement, "CountryCode");
    assertThat(statement.getSql()).isEqualTo(sqlOrderBy);
    assertThat(statement.getVariables()).containsExactlyElementsOf(variables);
    return sqlOrderBy;
  }

  protected String checkLimit(S statement, String sql) {

    FeatureLimit<?> sLimit = asFeature(FeatureLimit.class, statement);
    if (sLimit == null) {
      return sql;
    }

    List<Object> variables = new ArrayList<>(statement.getVariables());

    // add limit
    long limit = 100;
    sLimit.limit(limit);
    String sqlLimit = sql;
    if (!statement.getSqlDialect().limit().isEmpty()) {
      sqlLimit = sqlLimit + " LIMIT " + variable(statement);
      variables.add(limit);
    }
    assertThat(statement.getSql()).isEqualTo(sqlLimit);
    assertThat(statement.getVariables()).containsExactlyElementsOf(variables);
    return sqlLimit;
  }

  protected String checkOffset(S statement, String sql) {

    FeaturePaging<?> paging = asFeature(FeaturePaging.class, statement);
    if (paging == null) {
      return sql;
    }

    List<Object> variables = new ArrayList<>(statement.getVariables());

    // add offset
    long offset = 500;
    paging.offset(offset);
    String sqlOffset = sql;
    if (!statement.getSqlDialect().offset().isEmpty()) {
      sqlOffset = sqlOffset + " OFFSET " + variable(statement);
      variables.add(offset);
    }
    assertThat(statement.getSql()).isEqualTo(sqlOffset);
    assertThat(statement.getVariables()).containsExactlyElementsOf(variables);
    return sqlOffset;
  }

  protected void checkSql(SqlDialect dialect) {

    // given
    S statement = createStatement(dialect);

    // test "empty" statement
    String sql = getSqlStart() + quote(statement, ExampleBean.class.getSimpleName());
    assertThat(statement.getSql()).isEqualTo(sql);
    assertThat(statement.getVariables()).isEmpty();

    // do the additional tests...
    sql = checkWhere(statement, sql);
    sql = checkGroupBy(statement, sql);
    sql = checkOrderBy(statement, sql);
    sql = checkOffset(statement, sql);
    sql = checkLimit(statement, sql);
  }

  @Test
  public void testSql() {

    checkSql(DefaultSqlDialect.INSTANCE);
    checkSql(JpqlDialect.INSTANCE);
  }

  @Test
  public void testSqlOptimization() {

    // given
    S statement = createStatement();
    ExampleBean prototype = getPrototype();

    String sql = getSqlStart() + quote(statement, ExampleBean.class.getSimpleName());

    FeatureWhere<?> where = asFeature(FeatureWhere.class, statement);
    if (where != null) {
      // add regular equals condition to where clause
      where.where(Expressions.of(Boolean.FALSE).isFalse());
      assertThat(statement.getSql()).isEqualTo(sql);
      assertThat(statement.getVariables()).isEmpty();

      try {
        where.where(Expressions.of(Boolean.FALSE).isTrue());
        failBecauseExceptionWasNotThrown(IllegalArgumentException.class);
      } catch (IllegalArgumentException e) {
        assertThat(e).hasMessage("Expression can never match!");
      }
      assertThat(statement.getSql()).isEqualTo(sql);
      assertThat(statement.getVariables()).isEmpty();
    }
  }

  public static class TestSelectStatement<E extends Bean>
      extends AbstractSelectStatement<E, TestSelectStatement<E>> {

    private final E prototype;

    /**
     * The constructor.
     *
     * @param prototype the {@link Bean}-{@link BeanFactory#getPrototype(Bean) prototype}.
     */
    public TestSelectStatement(E prototype) {
      super(new DefaultSqlDialect());
      this.prototype = prototype;
    }

    /**
     * @return the prototype
     */
    public E getPrototype() {

      return this.prototype;
    }

    @Override
    protected String getSource() {

      return this.prototype.access().getSimpleName();
    }

    @Override
    public List<E> fetch() {

      throw new UnsupportedOperationException();
    }

    @Override
    public E fetchFirst() {

      throw new UnsupportedOperationException();
    }

    @Override
    public E fetchOne() {

      throw new UnsupportedOperationException();
    }

    @Override
    public long fetchCount() {

      throw new UnsupportedOperationException();
    }

  }

}
