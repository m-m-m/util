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
public interface AttributeWriteStyles extends AttributeReadStyles {

  /**
   * This method clears all {@link AttributeReadStylesAdvanced#getStyles() styles} and sets them to the given
   * <code>styles</code>.
   * 
   * @see AttributeReadStylesAdvanced#getStyles()
   * 
   * @param styles are the styles of this object. Either a single style or a list of styles separated by
   *        whitespaces. Use the empty string to unset all styles.
   */
  void setStyles(String styles);

}
