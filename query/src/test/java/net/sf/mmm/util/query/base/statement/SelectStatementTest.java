/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.query.base.statement;

import java.util.Arrays;
import java.util.List;

import net.sf.mmm.util.bean.api.Bean;
import net.sf.mmm.util.query.api.ListQuery;
import net.sf.mmm.util.query.api.NumberQuery;
import net.sf.mmm.util.query.api.feature.FeatureFetch;
import net.sf.mmm.util.query.api.feature.FeatureGroupBy;
import net.sf.mmm.util.query.api.feature.FeatureOrderBy;
import net.sf.mmm.util.query.api.feature.FeaturePaging;
import net.sf.mmm.util.query.api.feature.FeatureWhere;
import net.sf.mmm.util.query.api.feature.StatementFeature;
import net.sf.mmm.util.query.api.statement.SelectStatement;
import net.sf.mmm.util.query.base.QueryMode;
import net.sf.mmm.util.query.base.example.QueryTestPropertyBean;
import net.sf.mmm.util.query.base.path.Alias;

/**
 * Test of {@link SelectStatement} and {@link AbstractSelectStatement}.
 *
 * @author hohwille
 */
public class SelectStatementTest
    extends AbstractStatementTest<SelectStatementTest.TestSelectStatement<QueryTestPropertyBean>> {

  @Override
  protected TestSelectStatement<QueryTestPropertyBean> createStatement(SqlDialect dialect) {

    return new TestSelectStatement<>(dialect, getAlias());
  }

  @Override
  protected List<Class<? extends StatementFeature>> getFeatures() {

    return Arrays.asList(FeatureWhere.class, FeatureOrderBy.class, FeatureGroupBy.class, FeatureFetch.class,
        FeaturePaging.class);
  }

  @Override
  protected String getSqlStart() {

    return "FROM ";
  }

  @Override
  protected void checkExtended(TestSelectStatement<QueryTestPropertyBean> statement, String sql,
      List<Object> variables) {

    super.checkExtended(statement, sql, variables);
    ListQuery<QueryTestPropertyBean> query = statement.query();
    assertThat(query.getSql()).isEqualTo("SELECT " + getAlias().getName() + " " + sql);
    NumberQuery<Long> countQuery = statement.queryCount();
    assertThat(countQuery.getSql()).isEqualTo("SELECT COUNT(*) " + sql);
  }

  public static class TestSelectStatement<E extends Bean>
      extends AbstractSelectStatement<E, TestSelectStatement<E>, E> {

    public TestSelectStatement(SqlDialect dialect, Alias<E> alias) {
      super(dialect, alias, null);
    }

    @Override
    protected Object doExecute(String sql, QueryMode mode, Long offset, Integer limit) {

      throw new UnsupportedOperationException();
    }
  }

}
