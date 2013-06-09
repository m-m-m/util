/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.transferobject.api;

import net.sf.mmm.util.component.base.AbstractLoggableComponent;
import net.sf.mmm.util.entity.api.Entity;
import net.sf.mmm.util.nls.api.NlsClassCastException;
import net.sf.mmm.util.nls.api.NlsIllegalStateException;
import net.sf.mmm.util.nls.api.NlsNullPointerException;
import net.sf.mmm.util.reflect.api.AccessFailedException;
import net.sf.mmm.util.reflect.api.InstantiationFailedException;

/**
 * This is the abstract base class for a {@link TransferObject}.
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
   * The copy-constructor.
   * 
   * @param template is the object to create a deep-copy from.
   */
  protected AbstractTransferObject(Object template) {

    super();
    if (template != null) {
      copyFromInternal(template);
    }
  }

  /**
   * @see #copyFrom(Object)
   * 
   * @param source is the source object where to copy the properties from.
   */
  final void copyFromInternal(Object source) {

    try {
      copyFrom(source);
    } catch (ClassCastException e) {
      throw new NlsClassCastException(source, AbstractTransferObject.this.getClass());
    }
  }

  /**
   * This method copies all properties from <code>source</code> to this object. If a property is copied whose
   * value is a mutable object (not a {@link net.sf.mmm.util.lang.api.Datatype} or the like), that object also
   * has to be copied/cloned.
   * 
   * @param source is the source object where to copy the properties from.
   */
  protected void copyFrom(Object source) {

    // has to be overridden by every sub-class...
    NlsNullPointerException.checkNotNull("source", source);
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

      NlsNullPointerException.checkNotNull(AbstractTransferObject.class.getSimpleName(), template);
      return (TO) template.clone();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <ENTITY extends Entity, TO extends AbstractTransferObject> void copyProperties(ENTITY source, TO target) {

      target.copyFromInternal(source);
    }

    /**
     * {@inheritDoc}
     */
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

}
