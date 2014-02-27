/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.attribute;

import net.sf.mmm.util.lang.api.Alignment;
import net.sf.mmm.util.pojo.path.api.TypedProperty;

/**
 * This interface gives read access to the {@link #getAlignment() alignment} of an object.
 * 
 * @see AttributeWriteAlignment
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeReadAlignment {

  /** {@link TypedProperty} for {@link #getAlignment()}. */
  TypedProperty<Alignment> PROPERTY_ALIGNMENT = new TypedProperty<Alignment>(Alignment.class, "alignment");

  /**
   * This method gets the {@link Alignment} of this object.
   * 
   * @return the {@link Alignment} or <code>null</code> if not set and no default available.
   */
  Alignment getAlignment();

}
