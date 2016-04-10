/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.query.base;

import java.util.Arrays;
import java.util.List;

import net.sf.mmm.util.bean.api.Bean;
import net.sf.mmm.util.bean.api.BeanFactory;
import net.sf.mmm.util.bean.impl.example.ExampleBean;
import net.sf.mmm.util.query.api.SelectStatement;
import net.sf.mmm.util.query.api.feature.FeatureLimit;
import net.sf.mmm.util.query.api.feature.FeatureModify;
import net.sf.mmm.util.query.api.feature.FeatureSet;
import net.sf.mmm.util.query.api.feature.FeatureWhere;
import net.sf.mmm.util.query.api.feature.StatementFeature;
import net.sf.mmm.util.query.base.AbstractSelectStatement;
import net.sf.mmm.util.query.base.AbstractUpdateStatement;
import net.sf.mmm.util.query.base.SqlDialect;
import net.sf.mmm.util.query.base.UpdateStatementTest.TestUpdateStatement;

/**
 * Test of {@link SelectStatement} and {@link AbstractSelectStatement}.
 *
 * @author hohwille
 */
public class UpdateStatementTest extends AbstractStatementTest<TestUpdateStatement<ExampleBean>> {

  @Override
  protected TestUpdateStatement<ExampleBean> createStatement(SqlDialect dialect) {

    return new TestUpdateStatement<>(getPrototype(), dialect);
  }

  @Override
  protected List<Class<? extends StatementFeature>> getFeatures() {

    return Arrays.asList(FeatureWhere.class, FeatureLimit.class, FeatureModify.class, FeatureSet.class);
  }

  @Override
  protected String getSqlStart() {

    return "UPDATE ";
  }

  public static class TestUpdateStatement<E extends Bean>
      extends AbstractUpdateStatement<E, TestUpdateStatement<E>> {

    private final E prototype;

    /**
     * The constructor.
     *
     * @param prototype the {@link Bean}-{@link BeanFactory#getPrototype(Bean) prototype}.
     */
    public TestUpdateStatement(E prototype, SqlDialect dialect) {
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
