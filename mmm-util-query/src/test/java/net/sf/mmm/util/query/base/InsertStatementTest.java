/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.query.base;

import java.util.Arrays;
import java.util.List;

import net.sf.mmm.util.bean.api.Bean;
import net.sf.mmm.util.bean.api.BeanFactory;
import net.sf.mmm.util.query.api.SelectStatement;
import net.sf.mmm.util.query.api.feature.FeatureModify;
import net.sf.mmm.util.query.api.feature.FeatureSet;
import net.sf.mmm.util.query.api.feature.StatementFeature;
import net.sf.mmm.util.query.base.InsertStatementTest.TestInsertStatement;
import net.sf.mmm.util.query.base.example.QueryTestBean;

/**
 * Test of {@link SelectStatement} and {@link AbstractSelectStatement}.
 *
 * @author hohwille
 */
public class InsertStatementTest extends AbstractStatementTest<TestInsertStatement<QueryTestBean>> {

  @Override
  protected TestInsertStatement<QueryTestBean> createStatement(SqlDialect dialect) {

    return new TestInsertStatement<>(getPrototype(), dialect);
  }

  @Override
  protected List<Class<? extends StatementFeature>> getFeatures() {

    return Arrays.asList(FeatureModify.class, FeatureSet.class);
  }

  @Override
  protected String getSqlStart() {

    return "INSERT INTO ";
  }

  public static class TestInsertStatement<E extends Bean>
      extends AbstractInsertStatement<E, TestInsertStatement<E>> {

    private final E prototype;

    /**
     * The constructor.
     *
     * @param prototype the {@link Bean}-{@link BeanFactory#getPrototype(Bean) prototype}.
     */
    public TestInsertStatement(E prototype, SqlDialect dialect) {
      super(dialect);
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
    public long execute() {

      throw new UnsupportedOperationException();
    }

  }

}
