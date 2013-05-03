/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.attribute;

/**
 * This interface gives read access to the {@link #getStyles() style(s)} of an object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeReadStyles {

  /** The regular expression pattern for a single style. */
  String STYLE_PATTERN_SINGLE = "[a-zA-Z][_a-zA-Z0-9-]*";

  /** The regular expression pattern for any number of {@link #getStyles() styles}. */
  String STYLE_PATTERN_MULTIPLE = "(" + STYLE_PATTERN_SINGLE + ")?([ ]" + STYLE_PATTERN_SINGLE + ")*";

  /**
   * This method gets the style(s) (also called style-name(s)) of this object. The style defines aspects for
   * the appearance (look & feel) of a UI element when displayed to the user.<br/>
   * If you are familiar with web-technology just think of this as the <code>class</code> attribute of HTML
   * elements that are then configured via some cascading style sheet (CSS).<br/>
   * The following situations can be distinguished:
   * <ul>
   * <li><b>no style</b><br/>
   * The empty string is returned.</li>
   * <li><b>single style</b><br/>
   * The name of that single style is returned. The result does NOT contain any whitespace.</li>
   * <li><b>multiple styles</b><br/>
   * The list of individual styles are separated by whitespaces in the result.</li>
   * </ul>
   * Each individual style has to match {@link #STYLE_PATTERN_SINGLE}. Any number of styles or in other words
   * the result of this has to match {@link #STYLE_PATTERN_MULTIPLE}. The first style is called the
   * {@link AttributeReadPrimaryStyle#getPrimaryStyle() primary style}.
   * 
   * @return the style(s) of this object.
   */
  String getStyles();

}
