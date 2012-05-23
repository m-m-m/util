/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.attribute;

/**
 * This interface gives read access to the {@link #getStyles() style(s)} of an object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface AttributeReadStyles {

  /**
   * This method gets the style(s) of this object. The style defines aspects for the appearance (look & feel)
   * of a UI element when displayed to the user.<br/>
   * If you are familiar with web-technology just think of this as the <code>class</code> attribute of HTML
   * elements that are then configured via some cascading style sheet (CSS).
   * 
   * @return the style of this object. May also be a list of styles separated by whitespaces. Will be the
   *         empty string if NOT set.
   */
  String getStyles();

  /**
   * This method checks if the given <code>style</code> is contained in the {@link #getStyles() set of styles}
   * .
   * 
   * @param style is the style to check.
   * @return true if the given <code>style</code> is active.
   */
  boolean hasStyle(String style);

}
