/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.query.base.statement;

import java.util.Arrays;
import java.util.List;

import net.sf.mmm.util.bean.api.Bean;
import net.sf.mmm.util.query.api.feature.FeatureFetch;
import net.sf.mmm.util.query.api.feature.FeatureGroupBy;
import net.sf.mmm.util.query.api.feature.FeatureOrderBy;
import net.sf.mmm.util.query.api.feature.FeaturePaging;
import net.sf.mmm.util.query.api.feature.FeatureWhere;
import net.sf.mmm.util.query.api.feature.StatementFeature;
import net.sf.mmm.util.query.api.statement.SelectStatement;
import net.sf.mmm.util.query.base.example.QueryTestBean;
import net.sf.mmm.util.query.base.path.Alias;

/**
 * Test of {@link SelectStatement} and {@link AbstractSelectStatement}.
 *
 * @author hohwille
 */
public class SelectStatementTest
    extends AbstractStatementTest<SelectStatementTest.TestSelectStatement<QueryTestBean>> {

  @Override
  protected TestSelectStatement<QueryTestBean> createStatement(SqlDialect dialect) {

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

  public static class TestSelectStatement<E extends Bean>
      extends AbstractSelectStatement<E, TestSelectStatement<E>> {

    public TestSelectStatement(SqlDialect dialect, Alias<E> alias) {
      super(dialect, alias);
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
