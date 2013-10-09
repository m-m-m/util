/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.impl.jpa.query.jpql;

import net.sf.mmm.persistence.api.query.SimpleQuery;
import net.sf.mmm.persistence.api.query.jpql.JpqlConditionalExpression;
import net.sf.mmm.persistence.api.query.jpql.JpqlCore;
import net.sf.mmm.persistence.api.query.jpql.JpqlFromClause;
import net.sf.mmm.persistence.api.query.jpql.JpqlSimpleExpression;
import net.sf.mmm.util.nls.api.NlsIllegalStateException;
import net.sf.mmm.util.pojo.path.api.TypedProperty;

/**
 * This is the implementation of {@link JpqlConditionalExpression}.
 * 
 * @param <E> is the generic type of the {@link #getEntityType() entity type}.
 * @param <SELF> is the generic type reflecting the type of this instance itself.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 0.9.0
 */
@SuppressWarnings("unchecked")
public abstract class AbstractJpqlConditionalExpression<E, SELF extends JpqlConditionalExpression<E, SELF>> extends
    AbstractJpqlFragmentWithGroupBySupport<E> implements JpqlConditionalExpression<E, SELF> {

  /** @see #setConjunctionDefaultToOr() */
  private boolean conjunctionDefaultOr;

  /**
   * <code>true</code> if a expression has previously been added), <code>false</code> otherwise (initially or
   * after conjunction like {@link #and()} or {@link #or()} has been added.
   */
  private boolean hasPreviousExpression;

  /**
   * The constructor.
   * 
   * @param context is the {@link JpqlContext}.
   * @param propertyBasePath - see {@link #getPropertyBasePath()}.
   */
  public AbstractJpqlConditionalExpression(JpqlContext<E> context, String propertyBasePath) {

    super(context);
    setPropertyBasePath(propertyBasePath);
    this.conjunctionDefaultOr = false;
    this.hasPreviousExpression = false;
  }

  /**
   * This method automatically appends the {@link #setConjunctionDefaultToAnd() default conjunction} as
   * needed.
   */
  protected void appendConjunctionIfRequired() {

    if (this.hasPreviousExpression) {
      appendConjunction(this.conjunctionDefaultOr);
    }
  }

  /**
   * This method appends a conjunction.
   * 
   * @param or - <code>true</code> for {@link JpqlCore#JPQL_CONJUNCTION_OR}, <code>false</code> for
   *        {@link JpqlCore#JPQL_CONJUNCTION_AND}.
   */
  private void appendConjunction(boolean or) {

    if (!this.hasPreviousExpression) {
      // TODO hohwille create explicit exception
      throw new NlsIllegalStateException();
    }
    String conjunction;
    if (or) {
      conjunction = JpqlCore.JPQL_CONJUNCTION_OR;
    } else {
      conjunction = JpqlCore.JPQL_CONJUNCTION_AND;
    }
    getContext().getQueryBuffer().append(conjunction);
    this.hasPreviousExpression = false;
  }

  /**
   * This method is called after a previous expression has been appended.
   */
  protected void setExpressionAppended() {

    this.hasPreviousExpression = true;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public JpqlSimpleExpression<Object, SELF> property(String basePath, String property) {

    return property(basePath, property, Object.class);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <T> JpqlSimpleExpression<T, SELF> property(String basePath, TypedProperty<T> property) {

    return property(basePath, property.getPojoPath(), property.getPropertyType());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <T> JpqlSimpleExpression<T, SELF> property(String basePath, String property, Class<T> propertyType) {

    return new JpqlPropertyExpressionImpl<T, SELF>((SELF) this, basePath, property);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public JpqlSimpleExpression<Object, SELF> property(String property) {

    return property(null, property);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <T> JpqlSimpleExpression<T, SELF> property(TypedProperty<T> property) {

    return property(null, property);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <T> JpqlSimpleExpression<T, SELF> subQuery(SimpleQuery<T> subQuery) {

    return property("", subQuery.getJpqlStatement(), null);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public SELF setConjunctionDefaultToAnd() {

    this.conjunctionDefaultOr = false;
    return (SELF) this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public SELF setConjunctionDefaultToOr() {

    this.conjunctionDefaultOr = true;
    return (SELF) this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public SELF and() {

    appendConjunction(false);
    return (SELF) this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public SELF or() {

    appendConjunction(true);
    return (SELF) this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public SELF setPropertyBasePath(String path) {

    return (SELF) super.setPropertyBasePath(path);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <T> JpqlFromClause<T> newSubQuery(Class<T> entityType, String alias) {

    JpqlContext<T> context = new JpqlContext<T>(getContext().getEntityManager(), entityType, alias, true);
    return new JpqlFromClauseImpl<T>(context);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public JpqlFromClause<?> newSubQuery(String basePath, String property, String alias) {

    JpqlContext<Object> context = new JpqlContext<Object>(getContext().getEntityManager(), Object.class, alias, true);
    return new JpqlFromClauseImpl<Object>(context, getProperty(basePath, property));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <T> JpqlFromClause<T> newSubQuery(String basePath, TypedProperty<T> property, String alias) {

    JpqlContext<T> context = new JpqlContext<T>(getContext().getEntityManager(), property.getPropertyType(), alias,
        true);
    return new JpqlFromClauseImpl<T>(context, getProperty(basePath, property.getPojoPath()));
  }

}
