/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.aria.attribute;

import net.sf.mmm.client.ui.api.aria.datatype.AriaDropEffect;

/**
 * This interface gives read and write access to the {@link #getDropEffect() dropEffect} attribute of an
 * object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeWriteAriaDropEffect extends AttributeReadAriaDropEffect {

  /**
   * This method sets the {@link #getDropEffect() dropEffect} attribute of this object.
   * 
   * @param dropEffect is the new value of {@link #getDropEffect()}. May be <code>null</code> to unset (for
   *        none).
   */
  void setDropEffect(AriaDropEffect dropEffect);

}
