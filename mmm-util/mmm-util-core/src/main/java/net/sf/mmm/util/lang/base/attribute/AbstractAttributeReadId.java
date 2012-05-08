/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.base.attribute;

import net.sf.mmm.util.lang.api.attribute.AttributeReadId;

/**
 * This is the abstract base implementation of the {@link AttributeReadId} interface. It implements the common
 * methods {@link #equals(Object)}, {@link #hashCode()} and {@link #toString()} to make your life easier. You
 * may want to override {@link #toString()} with a more specific implementation.
 * 
 * @param <T> is the generic type of the {@link #getId() ID}.
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public abstract class AbstractAttributeReadId<T> implements AttributeReadId<T> {

  /**
   * The constructor.
   */
  public AbstractAttributeReadId() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {

    T id = getId();
    if (id == null) {
      return 0;
    } else {
      return id.hashCode();
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean equals(Object object) {

    if (object == null) {
      return false;
    }
    if (this == object) {
      return true;
    }
    if (getClass() == object.getClass()) {

      T myId = getId();
      if (myId != null) {
        Object otherId = ((AttributeReadId<?>) object).getId();
        if (myId.equals(otherId)) {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    return getClass().getSimpleName() + " [" + getId() + "]";
  }

}
