/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.attribute;

/**
 * This interface gives write access to the {@link #setStyles(String)
 * style-name(s)} of an {@link net.sf.mmm.ui.toolkit.api.UiObject object}.
 * 
 * @see UiReadStyles
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiWriteStyles extends UiReadStyles {

  /**
   * This method clears all {@link #getStyles() styles} and sets them to the
   * given <code>styles</code>.
   * 
   * @see #getStyles()
   * 
   * @param styles are the styles of this object. Either a single style or a
   *        list of styles separated by whitespaces. Use the empty string to
   *        unset all styles.
   */
  void setStyles(String styles);

  /**
   * This method adds the given <code>style</code> to the {@link #getStyles()
   * styles}.
   * 
   * @param style is the style to add. If this style is already contained in the
   *        {@link #getStyles() styles}, this will have no effect.
   */
  void addStyle(String style);

  /**
   * This method removes the given <code>style</code> from the
   * {@link #getStyles() styles}.
   * 
   * @param style is the style to remove. If this style is NOT contained in the
   *        {@link #getStyles() styles}, this will have no effect.
   */
  void removeStyle(String style);

}
