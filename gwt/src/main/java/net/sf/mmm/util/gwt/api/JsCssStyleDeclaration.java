/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.gwt.api;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * This is the {@link JavaScriptObject} representing a {@code CssStyleDeclaration}.
 *
 * @see JavaScriptUtil#getComputedStyle(com.google.gwt.dom.client.Element)
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class JsCssStyleDeclaration extends JavaScriptObject {

  public static final String STYLE_WIDTH = "width";

  public static final String STYLE_MIN_WIDTH = "min-width";

  public static final String STYLE_MAX_WIDTH = "max-width";

  public static final String STYLE_HEIGHT = "height";

  public static final String STYLE_MIN_HEIGHT = "min-height";

  public static final String STYLE_MAX_HEIGHT = "max-height";

  public static final String STYLE_Z_INDEX = "zIndex";

  public static final String STYLE_VISIBILITY = "visibility";

  public static final String STYLE_TOP = "top";

  public static final String STYLE_TEXT_DECORATION = "textDecoration";

  public static final String STYLE_RIGHT = "right";

  public static final String STYLE_POSITION = "position";

  public static final String STYLE_PADDING_TOP = "paddingTop";

  public static final String STYLE_PADDING_RIGHT = "paddingRight";

  public static final String STYLE_PADDING_LEFT = "paddingLeft";

  public static final String STYLE_PADDING_BOTTOM = "paddingBottom";

  public static final String STYLE_PADDING = "padding";

  public static final String STYLE_OVERFLOW = "overflow";

  public static final String STYLE_OVERFLOW_X = "overflowX";

  public static final String STYLE_OVERFLOW_Y = "overflowY";

  public static final String STYLE_OPACITY = "opacity";

  public static final String STYLE_MARGIN_TOP = "marginTop";

  public static final String STYLE_MARGIN_RIGHT = "marginRight";

  public static final String STYLE_MARGIN_LEFT = "marginLeft";

  public static final String STYLE_MARGIN_BOTTOM = "marginBottom";

  public static final String STYLE_MARGIN = "margin";

  public static final String STYLE_LIST_STYLE_TYPE = "listStyleType";

  public static final String STYLE_LEFT = "left";

  public static final String STYLE_FONT_WEIGHT = "fontWeight";

  public static final String STYLE_FONT_STYLE = "fontStyle";

  public static final String STYLE_FONT_SIZE = "fontSize";

  public static final String STYLE_DISPLAY = "display";

  public static final String STYLE_CURSOR = "cursor";

  public static final String STYLE_COLOR = "color";

  public static final String STYLE_CLEAR = "clear";

  public static final String STYLE_BOTTOM = "bottom";

  public static final String STYLE_BORDER_WIDTH = "borderWidth";

  public static final String STYLE_BORDER_STYLE = "borderStyle";

  public static final String STYLE_BORDER_COLOR = "borderColor";

  public static final String STYLE_BACKGROUND_IMAGE = "backgroundImage";

  public static final String STYLE_BACKGROUND_COLOR = "backgroundColor";

  public static final String STYLE_VERTICAL_ALIGN = "verticalAlign";

  public static final String STYLE_TABLE_LAYOUT = "tableLayout";

  public static final String STYLE_TEXT_ALIGN = "textAlign";

  public static final String STYLE_TEXT_INDENT = "textIndent";

  public static final String STYLE_TEXT_JUSTIFY = "textJustify";

  public static final String STYLE_TEXT_OVERFLOW = "textOverflow";

  public static final String STYLE_TEXT_TRANSFORM = "textTransform";

  public static final String STYLE_OUTLINE_WIDTH = "outlineWidth";

  public static final String STYLE_OUTLINE_STYLE = "outlineStyle";

  public static final String STYLE_OUTLINE_COLOR = "outlineColor";

  public static final String STYLE_LINE_HEIGHT = "lineHeight";

  public static final String STYLE_WHITE_SPACE = "whiteSpace";

  /**
   * The constructor.
   */
  protected JsCssStyleDeclaration() {

    super();
  }

  // formatter:off

  /**
   * This method gets the value of the given {@code property}.
   *
   * @param property is the name of the property to get. E.g. {@link #STYLE_WIDTH} or {@link #STYLE_BACKGROUND_COLOR}.
   * @return the value of the style property. Will be {@code null} if the property does not exist.
   */
  public final native String getPropertyValue(String property) /*-{
                                                               return this.getPropertyValue(property);
                                                               }-*/;

  // formatter:on

}
