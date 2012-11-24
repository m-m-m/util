/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.impl.jpa.query.jpql;

import net.sf.mmm.persistence.api.query.ListQuery;
import net.sf.mmm.persistence.api.query.SimpleQuery;
import net.sf.mmm.persistence.api.query.jpql.JpqlFragment;
import net.sf.mmm.persistence.impl.jpa.query.ListQueryImpl;
import net.sf.mmm.persistence.impl.jpa.query.SimpleQueryImpl;
import net.sf.mmm.util.lang.api.attribute.AttributeWriteDisposed;
import net.sf.mmm.util.nls.api.NlsIllegalArgumentException;
import net.sf.mmm.util.nls.api.NlsNullPointerException;
import net.sf.mmm.util.nls.api.ObjectDisposedException;

/**
 * This is the implementation of {@link JpqlFragment}.
 * 
 * @param <E> is the generic type of the {@link #getEntityType() entity type}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractJpqlFragment<E> extends AbstractJpqlPropertySupport implements JpqlFragment<E>,
    AttributeWriteDisposed {

  /** @see #getContext() */
  private final JpqlContext<E> context;

  /** @see #isDisposed() */
  private boolean disposed;

  /**
   * The constructor.
   * 
   * @param context is the {@link JpqlContext}.
   */
  public AbstractJpqlFragment(JpqlContext<E> context) {

    super();
    this.context = context;
    this.disposed = false;
  }

  /**
   * @return the {@link JpqlContext}.
   */
  protected JpqlContext<E> getContext() {

    return this.context;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<E> getEntityType() {

    return this.context.getEntityType();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getEntityAlias() {

    return this.context.getEntityAlias();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isDisposed() {

    return this.disposed;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void dispose() {

    this.disposed = true;
  }

  /**
   * This method ensures that this object is NOT {@link #isDisposed() disposed}.
   * 
   * @throws ObjectDisposedException if this fragment is already {@link #isDisposed() disposed}.
   */
  protected void ensureNotDisposed() throws ObjectDisposedException {

    if (this.disposed) {
      throw new ObjectDisposedException(this);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getCurrentQuery() {

    return this.context.getQueryBuffer().toString();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ListQuery<E> select() {

    return select(getEntityAlias());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ListQuery<E> selectDistinct() {

    return select(JPQL_DISTINCT + getEntityAlias());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ListQuery<E> select(String selection) {

    String jpqlStatement = createSelectStatement(selection);
    return new ListQueryImpl<E>(jpqlStatement, getEntityType(), this.context);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public SimpleQuery<Long> selectCount() {

    String jpqlStatement = createSelectStatement("COUNT(" + getEntityAlias() + ")");
    return new SimpleQueryImpl<Long>(jpqlStatement, Long.class, this.context);
  }

  /**
   * @param selection is the selection criteria.
   * @return the JPQL SELECT statement.
   */
  private String createSelectStatement(String selection) {

    StringBuilder queryBuffer = this.context.getQueryBuffer();
    StringBuilder statement = new StringBuilder(queryBuffer.length() + selection.length() + 10);
    if (this.context.isSubQuery()) {
      statement.append('(');
    }
    statement.append(JPQL_SELECT);
    statement.append(selection);
    statement.append(queryBuffer);
    if (this.context.isSubQuery()) {
      statement.append(')');
    }
    return statement.toString();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public SimpleQuery<Double> selectAverage(String selection) {

    String jpqlStatement = createSelectStatement("AVG(" + selection + ")");
    return new SimpleQueryImpl<Double>(jpqlStatement, Double.class, this.context);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <R> ListQuery<R> selectNew(Class<R> resultType, String... arguments) {

    NlsNullPointerException.checkNotNull("resultType", resultType);
    NlsNullPointerException.checkNotNull("arguments", arguments);
    if (arguments.length == 0) {
      throw new NlsIllegalArgumentException("0", "arguments.length");
    }
    StringBuilder selection = new StringBuilder();
    selection.append(JPQL_NEW);
    selection.append(resultType.getName());
    char separator = '(';
    for (String arg : arguments) {
      selection.append(separator);
      // TODO: alias/propertyPrefix... generic property handling
      selection.append(arg);
      separator = ',';
    }
    selection.append(')');
    String jpqlStatement = createSelectStatement(selection.toString());
    return new ListQueryImpl<R>(jpqlStatement, resultType, this.context);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public SimpleQuery<Number> selectMinimum(String selection) {

    String jpqlStatement = createSelectStatement("MIN(" + selection + ")");
    return new SimpleQueryImpl<Number>(jpqlStatement, Number.class, this.context);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public SimpleQuery<Number> selectMaximum(String selection) {

    String jpqlStatement = createSelectStatement("MAX(" + selection + ")");
    return new SimpleQueryImpl<Number>(jpqlStatement, Number.class, this.context);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public JpqlFragment<E> setPropertyBasePath(String path) {

    return (JpqlFragment<E>) super.setPropertyBasePath(path);
  }

  /**
   * This method appends the {@link #getPropertyPrefix() prefix} and the given <code>property</code> to the
   * query.
   * 
   * @param property is the property. See {@link #getPropertyPrefix()}.
   */
  protected void appendProperty(String property) {

    NlsNullPointerException.checkNotNull("property", property);
    // TODO define regexp for legal properties...
    // assert (!property.contains(" "));
    StringBuilder queryBuffer = getContext().getQueryBuffer();
    if (!property.startsWith("(")) {
      queryBuffer.append(getPropertyPrefix());
    }
    queryBuffer.append(property);
  }

}
