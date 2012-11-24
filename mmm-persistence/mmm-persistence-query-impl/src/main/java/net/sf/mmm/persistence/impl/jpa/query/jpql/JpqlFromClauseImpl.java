/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.impl.jpa.query.jpql;

import net.sf.mmm.persistence.api.query.jpql.JpqlFromClause;
import net.sf.mmm.persistence.api.query.jpql.JpqlJoinType;
import net.sf.mmm.persistence.api.query.jpql.JpqlWhereClause;
import net.sf.mmm.util.nls.api.NlsNullPointerException;

/**
 * This is the implementation of {@link JpqlFromClause}.
 * 
 * @param <E> is the generic type of the {@link #getEntityType() entity type}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class JpqlFromClauseImpl<E> extends AbstractJpqlFragmentWithGroupBySupport<E> implements JpqlFromClause<E> {

  /**
   * The constructor.
   * 
   * @param context - see {@link #getContext()}.
   */
  public JpqlFromClauseImpl(JpqlContext<E> context) {

    this(context, context.getEntityType().getSimpleName());
  }

  /**
   * The constructor.
   * 
   * @param context - see {@link #getContext()}.
   * @param property is the actual selection to use (instead of using the default
   *        <code>{@link #getEntityType()}.{@link Class#getSimpleName() getSimpleName()}</code>).
   */
  public JpqlFromClauseImpl(JpqlContext<E> context, String property) {

    super(context);
    StringBuilder queryBuffer = context.getQueryBuffer();
    queryBuffer.append(JPQL_FROM);
    appendEntity(property, getEntityAlias());
    setPropertyBasePath(getEntityAlias());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public JpqlFromClause<E> also(Class<?> entityType, String alias) {

    StringBuilder queryBuffer = getContext().getQueryBuffer();
    queryBuffer.append(',');
    appendEntity(entityType, alias);
    return this;
  }

  /**
   * @see #also(Class, String)
   * 
   * @param entityType is the {@link Class} reflecting the additional
   *        {@link net.sf.mmm.util.entity.api.GenericEntity entity} to add to this from clause.
   * @param alias is the alias for the additional entity.
   */
  private void appendEntity(Class<?> entityType, String alias) {

    NlsNullPointerException.checkNotNull("entityType", entityType);
    appendEntity(entityType.getSimpleName(), alias);
  }

  /**
   * @see #also(Class, String)
   * 
   * @param selection is the selection (e.g. {@link #getEntityType()}).
   * @param alias is the alias for the additional entity.
   */
  private void appendEntity(String selection, String alias) {

    NlsNullPointerException.checkNotNull("alias", alias);
    StringBuilder queryBuffer = getContext().getQueryBuffer();
    queryBuffer.append(selection);
    queryBuffer.append(' ');
    queryBuffer.append(alias);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public JpqlFromClause<E> join(String valuedPathExpression, String alias) {

    return join(valuedPathExpression, alias, JpqlJoinType.JOIN);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public JpqlFromClause<E> join(String valuedPathExpression, String alias, JpqlJoinType type) {

    ensureNotDisposed();
    NlsNullPointerException.checkNotNull(JpqlJoinType.class, type);
    StringBuilder queryBuffer = getContext().getQueryBuffer();
    queryBuffer.append(type);
    queryBuffer.append(valuedPathExpression);
    queryBuffer.append(' ');
    queryBuffer.append(alias);
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public JpqlFromClause<E> joinFetch(String valuedPathExpression, JpqlJoinType type) {

    ensureNotDisposed();
    NlsNullPointerException.checkNotNull(JpqlJoinType.class, type);
    StringBuilder queryBuffer = getContext().getQueryBuffer();
    queryBuffer.append(type);
    queryBuffer.append(JPQL_FETCH);
    queryBuffer.append(valuedPathExpression);
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public JpqlFromClause<E> in(String collectionValuedPathExpression, String alias) {

    ensureNotDisposed();
    NlsNullPointerException.checkNotNull("expression", collectionValuedPathExpression);
    StringBuilder queryBuffer = getContext().getQueryBuffer();
    queryBuffer.append(JPQL_IN_START);
    appendProperty(collectionValuedPathExpression);
    queryBuffer.append(JPQL_IN_END);
    queryBuffer.append(alias);
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public JpqlWhereClause<E> where() {

    dispose();
    return new JpqlWhereClauseImpl<E>(getContext(), getEntityAlias());
  }

}
