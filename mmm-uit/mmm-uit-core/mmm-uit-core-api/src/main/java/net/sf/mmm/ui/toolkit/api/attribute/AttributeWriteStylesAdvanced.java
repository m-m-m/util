/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.attribute;

/**
 * This interface gives write access to the {@link #setStyles(String) style-name(s)} of an object.
 * 
 * @see AttributeReadStylesAdvanced
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeWriteStylesAdvanced extends AttributeReadStylesAdvanced, AttributeWriteStyles {

  /**
   * This method adds the given <code>style</code> to the {@link #getStyles() styles}.
   * 
   * @param style is the style to add. If this style is already contained in the {@link #getStyles() styles},
   *        this will have no effect.
   */
  void addStyle(String style);

  /**
   * This method removes the given <code>style</code> from the {@link #getStyles() styles}.
   * 
   * @param style is the style to remove. If this style is NOT contained in the {@link #getStyles() styles},
   *        this will have no effect.
   * @return <code>true</code> if the given <code>style</code> has actually been removed, <code>false</code>
   *         otherwise (if it {@link #hasStyle(String) was NOT present}).
   */
  boolean removeStyle(String style);

}
