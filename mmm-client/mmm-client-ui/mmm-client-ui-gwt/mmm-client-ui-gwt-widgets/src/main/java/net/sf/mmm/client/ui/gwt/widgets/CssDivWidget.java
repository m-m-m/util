/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.gwt.widgets;

import net.sf.mmm.client.ui.gwt.widgets.handler.AbstractMouseDragHandler;

import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Document;

/**
 * This is a {@link com.google.gwt.user.client.ui.Widget} for an empty {@literal <div>} element that is used
 * as placeholder for a feature styled with CSS.<br/>
 * This may e.g. be a border that allows resizing by dragging with the mouse. Via CSS an absolute positioning
 * and a specific mouse cursor can be configured. Behavior has to be added in the code (technically via
 * java-script) - see e.g. {@link AbstractMouseDragHandler}.<br/>
 * The icon style is given at {@link #CssDivWidget(String) construction time} but can also be
 * {@link #setStyleName(String) modified} for dynamic icons.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class CssDivWidget extends AbstractWidget {

  /**
   * The constructor.
   * 
   * @param style is the {@link #setStyleName(String) style name} for the icon.
   */
  public CssDivWidget(String style) {

    super();
    DivElement divElement = Document.get().createDivElement();
    setElement(divElement);
    setStyleName(style);
  }

}
