/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.query.base.statement;

import java.util.Arrays;
import java.util.List;

import net.sf.mmm.util.bean.api.Bean;
import net.sf.mmm.util.query.api.feature.FeatureModify;
import net.sf.mmm.util.query.api.feature.FeatureSet;
import net.sf.mmm.util.query.api.feature.StatementFeature;
import net.sf.mmm.util.query.base.example.QueryTestPropertyBean;
import net.sf.mmm.util.query.base.path.Alias;
import net.sf.mmm.util.query.base.statement.InsertStatementTest.TestInsertStatement;

/**
 * Test of {@link InsertStatement} and {@link AbstractInsertStatement}.
 *
 * @author hohwille
 */
public class InsertStatementTest extends AbstractStatementTest<TestInsertStatement<QueryTestPropertyBean>> {

  @Override
  protected TestInsertStatement<QueryTestPropertyBean> createStatement(SqlDialect dialect) {

    return new TestInsertStatement<>(dialect, getAlias());
  }

  @Override
  protected List<Class<? extends StatementFeature>> getFeatures() {

    return Arrays.asList(FeatureModify.class, FeatureSet.class);
  }

  @Override
  protected String getSqlStart() {

    return "INSERT INTO ";
  }

  @Override
  protected boolean isSupportingAlias() {

    return false;
  }

  public static class TestInsertStatement<E extends Bean>
      extends AbstractInsertStatement<E, TestInsertStatement<E>> {

    public TestInsertStatement(SqlDialect dialect, Alias<E> alias) {
      super(dialect, alias);
    }

    @Override
    protected long doExecute(String sql, Integer limit) {

      throw new UnsupportedOperationException();
    }

  }

}
