/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.base.query;

import java.util.Arrays;
import java.util.List;

import net.sf.mmm.util.bean.api.Bean;
import net.sf.mmm.util.bean.api.BeanFactory;
import net.sf.mmm.util.bean.impl.example.ExampleBean;
import net.sf.mmm.util.property.api.query.SelectStatement;
import net.sf.mmm.util.property.api.query.feature.FeatureFetch;
import net.sf.mmm.util.property.api.query.feature.FeatureGroupBy;
import net.sf.mmm.util.property.api.query.feature.FeatureOrderBy;
import net.sf.mmm.util.property.api.query.feature.FeaturePaging;
import net.sf.mmm.util.property.api.query.feature.FeatureWhere;
import net.sf.mmm.util.property.api.query.feature.StatementFeature;

/**
 * Test of {@link SelectStatement} and {@link AbstractSelectStatement}.
 *
 * @author hohwille
 */
public class SelectStatementTest
    extends AbstractStatementTest<SelectStatementTest.TestSelectStatement<ExampleBean>> {

  @Override
  protected TestSelectStatement<ExampleBean> createStatement(SqlDialect dialect) {

    return new TestSelectStatement<>(getPrototype(), dialect);
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

    private final E prototype;

    /**
     * The constructor.
     *
     * @param dialect the {@link SqlDialect}.
     * @param prototype the {@link Bean}-{@link BeanFactory#getPrototype(Bean) prototype}.
     */
    public TestSelectStatement(E prototype, SqlDialect dialect) {
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
