/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.impl.jpa.query.jpql;

import net.sf.mmm.persistence.api.query.jpql.JpqlPropertySupport;
import net.sf.mmm.util.lang.api.attribute.AttributeWriteDisposed;
import net.sf.mmm.util.nls.api.NlsNullPointerException;
import net.sf.mmm.util.nls.api.ObjectDisposedException;

/**
 * This is the implementation of {@link JpqlPropertySupport}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 0.9.0
 */
public abstract class AbstractJpqlPropertySupport implements JpqlPropertySupport, AttributeWriteDisposed {

  /** @see #getPropertyBasePath() */
  private String propertyBasePath;

  /** @see #isDisposed() */
  private boolean disposed;

  /**
   * The constructor.
   */
  public AbstractJpqlPropertySupport() {

    super();
    this.disposed = false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getPropertyBasePath() {

    return this.propertyBasePath;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public JpqlPropertySupport setPropertyBasePath(String path) {

    NlsNullPointerException.checkNotNull("path", path);
    this.propertyBasePath = path;
    return this;
  }

  /**
   * This method appends the {@link #getPropertyBasePath() base path} and the given <code>property</code> to
   * the query.
   * 
   * @param property is the {@link #PROPERTY property}.
   * @param context is the {@link JpqlContext}.
   */
  protected void appendProperty(String property, JpqlContext<?> context) {

    appendProperty(this.propertyBasePath, property, context);
  }

  /**
   * This method appends the given <code>basePath</code> and <code>property</code> to the query.
   * 
   * @param basePath is the {@link #PROPERTY_BASE_PATH base path}.
   * @param property is the {@link #PROPERTY property}.
   * @param context is the {@link JpqlContext}.
   */
  protected void appendProperty(String basePath, String property, JpqlContext<?> context) {

    StringBuilder queryBuffer = context.getQueryBuffer();
    queryBuffer.append(getProperty(basePath, property));
  }

  /**
   * This method gets the absolute {@link #PROPERTY property} build form the given <code>basePath</code> and
   * <code>property</code>.
   * 
   * @param basePath is the {@link #PROPERTY_BASE_PATH base path}.
   * @param property is the {@link #PROPERTY property}.
   * @return the absolute {@link #PROPERTY property}.
   */
  protected String getProperty(String basePath, String property) {

    NlsNullPointerException.checkNotNull("property", property);
    String path = basePath;
    if (path == null) {
      path = this.propertyBasePath;
    }
    if ((path != null) && (path.length() > 0)) {
      // TODO define regexp for legal properties...
      // assert (!property.contains(" "));
      // if (!property.startsWith("(")) {
      return path + '.' + property;
    } else {
      return property;
    }
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

}
