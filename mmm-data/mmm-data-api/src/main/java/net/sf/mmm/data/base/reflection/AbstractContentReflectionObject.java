/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.base.reflection;

import net.sf.mmm.data.api.reflection.DataClass;
import net.sf.mmm.data.api.reflection.DataReflectionObject;
import net.sf.mmm.data.base.AbstractContentObject;

/**
 * This is the abstract base implementation of the
 * {@link DataReflectionObject} interface.
 * 
 * @param <CLASS> is the generic type of the reflected {@link #getJavaClass()
 *        class}.
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractContentReflectionObject<CLASS> extends AbstractContentObject
    implements DataReflectionObject<CLASS> {

  /** UID for serialization. */
  private static final long serialVersionUID = 1971525692050639234L;

  /** @see #getDeletedFlag() */
  private boolean deletedFlag;

  /**
   * The constructor.
   */
  public AbstractContentReflectionObject() {

    super();
  }

  /**
   * The constructor.
   * 
   * @param title is the {@link #getTitle() title}.
   */
  public AbstractContentReflectionObject(String title) {

    super(title);
  }

  /**
   * {@inheritDoc}
   */
  public final boolean getDeletedFlag() {

    return this.deletedFlag;
  }

  /**
   * This method sets the {@link #isDeleted() deleted} flag.
   * 
   * @param deleted - if <code>true</code> the object will be marked as deleted.
   */
  protected void setDeletedFlag(boolean deleted) {

    this.deletedFlag = deleted;
  }

  /**
   * {@inheritDoc}
   * 
   * <b>ATTENTION:</b><br>
   * This field/method is logically
   * {@link net.sf.mmm.data.api.reflection.DataFieldAnnotation#isInheritedFromParent()
   * inherited} but NOT annotated with <code>isInherited = true</code>. This
   * feature is programmatically implemented since it is required at a very low
   * level.
   */
  public boolean isDeleted() {

    if (this.deletedFlag) {
      return true;
    } else {
      DataClass<?> parent = getParent();
      if (parent == null) {
        return false;
      } else {
        return parent.isDeleted();
      }
    }
  }

}
