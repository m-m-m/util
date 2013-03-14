/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.transferobject.api;

import java.io.Serializable;

import net.sf.mmm.util.nls.api.NlsIllegalStateException;
import net.sf.mmm.util.nls.api.NlsNullPointerException;

/**
 * This is the abstract base class for a transfer object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.1.0
 */
public abstract class TransferObject implements Serializable, Cloneable {

  /** UID for serialization. */
  private static final long serialVersionUID = -6842823766837505377L;

  /**
   * The constructor.
   */
  public TransferObject() {

    super();
  }

  /**
   * The copy-constructor.
   * 
   * @param template is the object to create a deep-copy from.
   */
  protected TransferObject(Object template) {

    super();
    if (template != null) {
      copyFrom(template, true);
    }
  }

  /**
   * This method copies all properties from <code>source</code> to this object. If a property is copied whose
   * value is a mutable object (not a {@link net.sf.mmm.util.lang.api.Datatype} or the like), that object also
   * has to be copied/cloned.
   * 
   * @param source is the source object where to copy the properties from.
   * @param overwrite - <code>true</code> if all properties shall be copied, <code>false</code> if only the
   *        properties shall be copied that are <code>null</code> in this object.
   */
  protected void copyFrom(Object source, boolean overwrite) {

    // has to be overridden by every sub-class...
    NlsNullPointerException.checkNotNull("source", source);
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
   * {@inheritDoc}
   */
  @Override
  public TransferObject clone() {

    try {
      return (TransferObject) super.clone();
    } catch (CloneNotSupportedException e) {
      throw new NlsIllegalStateException(e);
    }
  }

}
