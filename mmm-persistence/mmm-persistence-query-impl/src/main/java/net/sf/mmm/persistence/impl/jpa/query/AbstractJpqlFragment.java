/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.impl.jpa.query;

import javax.persistence.TypedQuery;

import net.sf.mmm.persistence.api.jpa.query.JpqlFragment;
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
  private final JpqlQueryContext<E> context;

  /** @see #isDisposed() */
  private boolean disposed;

  /**
   * The constructor.
   * 
   * @param context is the {@link JpqlQueryContext}.
   */
  public AbstractJpqlFragment(JpqlQueryContext<E> context) {

    super();
    this.context = context;
    this.disposed = false;
  }

  /**
   * @return the {@link JpqlQueryContext}.
   */
  protected JpqlQueryContext<E> getContext() {

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
  public TypedQuery<E> select() {

    return select(getEntityAlias());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public TypedQuery<Long> selectCount() {

    return this.context.createSelectQuery("COUNT(*)", Long.class);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public TypedQuery<Double> selectAverage(String selection) {

    return this.context.createSelectQuery("AVG(" + selection + ")", Double.class);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <R> TypedQuery<R> selectNew(Class<R> resultType, String... arguments) {

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
    return this.context.createSelectQuery(JPQL_NEW + resultType.getName() + "(", resultType);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public TypedQuery<Number> selectMinimum(String selection) {

    return this.context.createSelectQuery("MIN(" + selection + ")", Number.class);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public TypedQuery<Number> selectMaximum(String selection) {

    return this.context.createSelectQuery("MAX(" + selection + ")", Number.class);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public TypedQuery<E> select(String selection) {

    return this.context.createSelectQuery(selection, getEntityType());
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
    assert (!property.contains(" "));
    StringBuilder queryBuffer = getContext().getQueryBuffer();
    queryBuffer.append(getPropertyPrefix());
    queryBuffer.append(property);
  }

}
