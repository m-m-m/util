/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.query.base.statement;

import java.util.Arrays;
import java.util.List;

import net.sf.mmm.util.bean.api.Bean;
import net.sf.mmm.util.query.api.feature.FeatureLimit;
import net.sf.mmm.util.query.api.feature.FeatureModify;
import net.sf.mmm.util.query.api.feature.FeatureWhere;
import net.sf.mmm.util.query.api.feature.StatementFeature;
import net.sf.mmm.util.query.api.statement.SelectStatement;
import net.sf.mmm.util.query.base.example.QueryTestBean;
import net.sf.mmm.util.query.base.path.Alias;
import net.sf.mmm.util.query.base.statement.DeleteStatementTest.TestDeleteStatement;

/**
 * Test of {@link SelectStatement} and {@link AbstractSelectStatement}.
 *
 * @author hohwille
 */
public class DeleteStatementTest extends AbstractStatementTest<TestDeleteStatement<QueryTestBean>> {

  @Override
  protected TestDeleteStatement<QueryTestBean> createStatement(SqlDialect dialect) {

    return new TestDeleteStatement<>(dialect, getAlias());
  }

  @Override
  protected List<Class<? extends StatementFeature>> getFeatures() {

    return Arrays.asList(FeatureWhere.class, FeatureLimit.class, FeatureModify.class);
  }

  @Override
  protected String getSqlStart() {

    return "DELETE FROM ";
  }

  public static class TestDeleteStatement<E extends Bean>
      extends AbstractDeleteStatement<E, TestDeleteStatement<E>> {

    public TestDeleteStatement(SqlDialect dialect, Alias<E> alias) {
      super(dialect, alias);
    }

    @Override
    protected long doExecute(String sql, Integer limit) {

      throw new UnsupportedOperationException();
    }

  }

}
