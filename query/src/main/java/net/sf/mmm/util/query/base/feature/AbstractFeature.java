/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.query.base.feature;

import net.sf.mmm.util.lang.api.Conjunction;
import net.sf.mmm.util.property.api.ReadableProperty;
import net.sf.mmm.util.property.api.path.PropertyPath;
import net.sf.mmm.util.query.api.argument.Argument;
import net.sf.mmm.util.query.api.expression.Expression;
import net.sf.mmm.util.query.api.feature.StatementFeature;
import net.sf.mmm.util.query.api.path.Path;
import net.sf.mmm.util.query.api.statement.Statement;
import net.sf.mmm.util.query.base.expression.Expressions;
import net.sf.mmm.util.query.base.statement.AbstractStatement;
import net.sf.mmm.util.query.base.statement.SqlBuilder;
import net.sf.mmm.util.query.base.statement.SqlDialect;
import net.sf.mmm.util.reflect.api.AccessFailedException;
import net.sf.mmm.util.reflect.api.InstantiationFailedException;

/**
 * This is the abstract base implementation of {@link StatementFeature}.
 *
 * @author hohwille
 * @since 8.5.0
 */
public abstract class AbstractFeature implements StatementFeature, Comparable<AbstractFeature> {

  /** {@link #getSortIndex() Sort index} for {@link FeatureAndFromImpl}. */
  protected static final int SORT_INDEX_AND_FROM = 0;

  /** {@link #getSortIndex() Sort index} for {@link FeatureSetImpl}. */
  protected static final int SORT_INDEX_SET = 10;

  /** {@link #getSortIndex() Sort index} for {@link FeatureUpsertImpl}. */
  protected static final int SORT_INDEX_UPSERT = 11;

  /** {@link #getSortIndex() Sort index} for {@link FeatureWhereImpl}. */
  protected static final int SORT_INDEX_WHERE = 20;

  /** {@link #getSortIndex() Sort index} for {@link FeatureLetImpl}. */
  protected static final int SORT_INDEX_LET = 25;

  /** {@link #getSortIndex() Sort index} for {@link FeatureGroupByImpl}. */
  protected static final int SORT_INDEX_GROUP_BY = 30;

  /** {@link #getSortIndex() Sort index} for {@link FeatureHavingImpl}. */
  protected static final int SORT_INDEX_HAVING = 40;

  /** {@link #getSortIndex() Sort index} for {@link FeatureOrderByImpl}. */
  protected static final int SORT_INDEX_ORDER_BY = 50;

  /** {@link #getSortIndex() Sort index} for {@link FeatureUnwindImpl}. */
  protected static final int SORT_INDEX_UNWIND = 51;

  /** {@link #getSortIndex() Sort index} for {@link FeaturePagingImpl}. */
  protected static final int SORT_INDEX_PAGING = 60;

  /** {@link #getSortIndex() Sort index} for {@link FeatureFetchplanImpl}. */
  protected static final int SORT_INDEX_FETCHPLAN = 61;

  /** {@link #getSortIndex() Sort index} for {@link FeatureTimeoutImpl}. */
  protected static final int SORT_INDEX_TIMEOUT = 70;

  /** {@link #getSortIndex() Sort index} for {@link FeatureLockImpl}. */
  protected static final int SORT_INDEX_LOCK = 80;

  /** {@link #getSortIndex() Sort index} for {@link FeatureParallelImpl}. */
  protected static final int SORT_INDEX_PARALLEL = 81;

  /** {@link #getSortIndex() Sort index} for {@link FeatureNoCacheImpl}. */
  protected static final int SORT_INDEX_NOCACHE = 82;

  private final int sortIndex;

  private AbstractStatement<?, ?> statement;

  /**
   * The constructor.
   *
   * @param sortIndex the sort index.
   */
  public AbstractFeature(int sortIndex) {
    super();
    this.sortIndex = sortIndex;
  }

  /**
   * @return the sort index used to define the order of the SQL fragments when building a statement from multiple
   *         features.
   */
  public int getSortIndex() {

    return this.sortIndex;
  }

  /**
   * @return the {@link Statement}.
   */
  public AbstractStatement<?, ?> getStatement() {

    return this.statement;
  }

  /**
   * @return the {@link SqlDialect}.
   */
  protected SqlDialect getDialect() {

    return this.statement.getDialect();
  }

  /**
   * @param statement is the {@link Statement} to set.
   */
  public void setStatement(AbstractStatement<?, ?> statement) {

    if (this.statement != null) {
      throw new IllegalStateException();
    }
    this.statement = statement;
  }

  /**
   * @param <V> the generic type of the property value identified by path.
   * @param path the {@link PropertyPath}.
   * @return the given {@code path} as {@link Argument}.
   */
  @SuppressWarnings("unchecked")
  protected <V> Argument<V> asArg(PropertyPath<V> path) {

    if (path instanceof Argument) {
      return (Argument<V>) path;
    } else {
      return this.statement.getAlias().to((ReadableProperty<V>) path);
    }
  }

  /**
   * @param <V> the generic type of the property value identified by path.
   * @param path the {@link PropertyPath}.
   * @return the given {@code path} as {@link Argument}.
   */
  protected <V> Path<V> asPath(PropertyPath<V> path) {

    if (path instanceof Path) {
      return (Path<V>) path;
    } else {
      return this.statement.getAlias().to((ReadableProperty<V>) path);
    }
  }

  /**
   * @param builder the {@link SqlBuilder} with the query context to build the SQL and bind variables.
   */
  public abstract void build(SqlBuilder builder);

  /**
   * Combines the given {@link Expression} with the given array of new {@code Expression}s using the given
   * {@link Conjunction}.
   *
   * @param expression the current {@link Expression} or {@code null}.
   * @param conjunction the {@link Conjunction} used to combine.
   * @param newExpressions the array with the new {@link Expression}s to combine.
   * @return the combined {@link Expression}.
   */
  protected Expression combine(Expression expression, Conjunction conjunction, Expression... newExpressions) {

    if ((newExpressions == null) || (newExpressions.length == 0)) {
      return expression;
    }
    if (expression == null) {
      if (newExpressions.length == 1) {
        return newExpressions[0];
      } else {
        return Expressions.combine(conjunction, newExpressions);
      }
    } else {
      return expression.combine(conjunction, newExpressions);
    }
  }

  @Override
  public int compareTo(AbstractFeature o) {

    int diff = this.sortIndex - o.sortIndex;
    if (diff == 0) {
      return 0;
    } else if (diff < 0) {
      return -1;
    } else {
      return 1;
    }
  }

  /**
   * @param <F> the generic type of the {@link AbstractFeature feature}.
   * @param featureClass the {@link Class} reflecting the {@link AbstractFeature feature}.
   * @return the new instance of the requested {@link AbstractFeature feature}.
   */
  public static <F extends AbstractFeature> F create(Class<F> featureClass) {

    AbstractFeature feature;
    if (featureClass == FeatureWhereImpl.class) {
      feature = new FeatureWhereImpl();
    } else if (featureClass == FeatureOrderByImpl.class) {
      feature = new FeatureOrderByImpl();
    } else if (featureClass == FeatureGroupByImpl.class) {
      feature = new FeatureGroupByImpl();
    } else if (featureClass == FeaturePagingImpl.class) {
      feature = new FeaturePagingImpl();
    } else if (featureClass == FeatureSetImpl.class) {
      feature = new FeatureSetImpl();
    } else if (featureClass == FeatureLetImpl.class) {
      feature = new FeatureLetImpl();
    } else {
      try {
        // throw new IllegalCaseException("Unsupported feature " + featureClass.getName());
        return featureClass.newInstance();
      } catch (InstantiationException e) {
        throw new InstantiationFailedException(e, featureClass);
      } catch (IllegalAccessException e) {
        throw new AccessFailedException(e, featureClass);
      }
    }
    return featureClass.cast(feature);
  }

}
