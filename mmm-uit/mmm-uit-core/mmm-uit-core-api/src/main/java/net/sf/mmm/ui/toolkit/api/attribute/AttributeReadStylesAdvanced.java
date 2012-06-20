/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.attribute;

/**
 * This interface gives advanced read access to the {@link #getStyles() style(s)} of an object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface AttributeReadStylesAdvanced extends AttributeReadStyles {

  /**
   * This method checks if the given <code>style</code> is contained in the {@link #getStyles() set of styles}
   * of this object.
   * 
   * @param style is the style to check.
   * @return true if the given <code>style</code> is active.
   */
  boolean hasStyle(String style);

}
