/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.transferobject.api;

import net.sf.mmm.util.component.base.AbstractLoggableComponent;
import net.sf.mmm.util.nls.api.NlsIllegalStateException;
import net.sf.mmm.util.nls.api.NlsNullPointerException;
import net.sf.mmm.util.reflect.api.AccessFailedException;
import net.sf.mmm.util.reflect.api.InstantiationFailedException;

/**
 * This is the abstract base class for a {@link TransferObject}. It already contains a small and simple
 * infrastructure for {@link #clone() cloning}, {@link #equals(Object)} and {@link #hashCode()} as well as
 * {@link #toString()}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.1.0
 */
public abstract class AbstractTransferObject implements TransferObject, Cloneable {

  /** UID for serialization. */
  private static final long serialVersionUID = -6842823766837505377L;

  /**
   * The constructor.
   */
  public AbstractTransferObject() {

    super();
  }

  /**
   * {@inheritDoc}
   *
   * <b>ATTENTION:</b><br/>
   * For being type-safe please use {@link TransferObjectUtil#clone(AbstractTransferObject)} instead.
   */
  @Override
  public AbstractTransferObject clone() {

    try {
      return (AbstractTransferObject) super.clone();
    } catch (CloneNotSupportedException e) {
      throw new NlsIllegalStateException(e);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean equals(Object obj) {

    // has to be overridden by every sub-class (use Eclipse "Generate equals() and hashCode()")...
    if (obj == null) {
      return false;
    }
    if (obj.getClass() != getClass()) {
      return false;
    }
    return true;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {

    // has to be overridden by every sub-class (use Eclipse "Generate equals() and hashCode()")...
    return 1;
  }

  /**
   * This is the abstract base implementation of {@link TransferObjectUtilLimited}.
   */
  public abstract static class AbstractTransferObjectUtilLimited extends AbstractLoggableComponent implements
      TransferObjectUtilLimited {

    /**
     * The constructor.
     */
    public AbstractTransferObjectUtilLimited() {

      super();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    public <TO extends AbstractTransferObject> TO clone(TO template) {

      NlsNullPointerException.checkNotNull(TransferObject.class.getSimpleName(), template);
      return (TO) template.clone();
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public <TO extends AbstractTransferObject> TO newInstance(TO template) {

      NlsNullPointerException.checkNotNull(AbstractTransferObject.class.getSimpleName(), template);
      Class<? extends AbstractTransferObject> toClass = template.getClass();
      TO newInstance;
      try {
        newInstance = (TO) toClass.newInstance();
      } catch (InstantiationException e) {
        throw new InstantiationFailedException(e, toClass);
      } catch (IllegalAccessException e) {
        throw new AccessFailedException(e, toClass);
      }
      return newInstance;
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final String toString() {

    StringBuilder buffer = new StringBuilder();
    toString(buffer);
    return buffer.toString();
  }

  /**
   * Method to extend {@link #toString()} logic. Override to add additional information.
   *
   * @param buffer is the {@link StringBuilder} where to {@link StringBuilder#append(Object) append} the
   *        string representation.
   */
  protected void toString(StringBuilder buffer) {

    buffer.append(getClass().getSimpleName());
  }

}
