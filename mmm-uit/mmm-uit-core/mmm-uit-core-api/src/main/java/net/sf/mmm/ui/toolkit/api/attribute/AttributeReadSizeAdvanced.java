/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.attribute;

import net.sf.mmm.ui.toolkit.api.common.SizeUnit;

/**
 * This interface gives read access to the size of an object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeReadSizeAdvanced extends AttributeReadSize {

  /**
   * @return the numeric part of the {@link #getWidth() width}. Will be <code>0</code> if undefined (NOT set).
   */
  double getWidthValue();

  /**
   * @return the {@link SizeUnit unit} part of the {@link #getWidth() width}. Will be <code>null</code> if
   *         undefined (NOT set).
   */
  SizeUnit getWidthUnit();

  /**
   * @return the numeric part of the {@link #getHeight() height}. Will be <code>0</code> if undefined (NOT
   *         set).
   */
  double getHeightValue();

  /**
   * @return the {@link SizeUnit unit} part of the {@link #getHeight() height}. Will be <code>null</code> if
   *         undefined (NOT set).
   */
  SizeUnit getHeightUnit();

}
