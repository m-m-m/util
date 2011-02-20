/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.attribute;

/**
 * This interface gives read access to the {@link #getStyle() style(s)} of an
 * {@link net.sf.mmm.ui.toolkit.api.UiObject object}. The style defines aspects
 * for the appearance (look & feel) of a UI element when displayed to the user.<br/>
 * If you are familiar with web-technology just think of this as the
 * <code>class</code> attribute of HTML elements that are then configured via
 * some cascading style sheet (CSS).
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiReadStyle {

  /**
   * This method gets the style(s) of this object.
   * 
   * @return the style of this object. May also be a list of styles separated by
   *         whitespaces. Will be the empty string if NOT set.
   */
  String getStyle();

}
