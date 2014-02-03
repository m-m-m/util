/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.gwt.widgets;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.SpanElement;

/**
 * This is a {@link com.google.gwt.user.client.ui.Widget} for an empty {@literal <span>} element that is used
 * as placeholder for an icon. Via CSS it can be styled to appear as an icon. This can be done via
 * background-image or web-fonts (font-awesome).<br/>
 * The icon style is given at {@link #IconCssWidget(String) construction time} but can also be
 * {@link #setStyleName(String) modified} for dynamic icons.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class IconCssWidget extends AbstractWidget {

  /**
   * The constructor.
   * 
   * @param style is the {@link #setStyleName(String) style name} for the icon.
   */
  public IconCssWidget(String style) {

    super();
    SpanElement spanElement = Document.get().createSpanElement();
    setElement(spanElement);
    setStyleName(style);
  }

}
