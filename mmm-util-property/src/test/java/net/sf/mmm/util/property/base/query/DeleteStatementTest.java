/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.base.query;

import java.util.Arrays;
import java.util.List;

import net.sf.mmm.util.bean.api.Bean;
import net.sf.mmm.util.bean.api.BeanFactory;
import net.sf.mmm.util.bean.impl.example.ExampleBean;
import net.sf.mmm.util.property.api.query.SelectStatement;
import net.sf.mmm.util.property.api.query.feature.FeatureLimit;
import net.sf.mmm.util.property.api.query.feature.FeatureModify;
import net.sf.mmm.util.property.api.query.feature.FeatureWhere;
import net.sf.mmm.util.property.api.query.feature.StatementFeature;
import net.sf.mmm.util.property.base.query.DeleteStatementTest.TestDeleteStatement;

/**
 * Test of {@link SelectStatement} and {@link AbstractSelectStatement}.
 *
 * @author hohwille
 */
public class DeleteStatementTest extends AbstractStatementTest<TestDeleteStatement<ExampleBean>> {

  @Override
  protected TestDeleteStatement<ExampleBean> createStatement(SqlDialect dialect) {

    return new TestDeleteStatement<>(getPrototype(), dialect);
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

    private final E prototype;

    /**
     * The constructor.
     *
     * @param prototype the {@link Bean}-{@link BeanFactory#getPrototype(Bean) prototype}.
     */
    public TestDeleteStatement(E prototype, SqlDialect dialect) {
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
