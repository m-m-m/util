/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.gwtwidgets;

import net.sf.mmm.util.gwt.api.JavaScriptUtil;

import com.google.gwt.text.client.DoubleParser;
import com.google.gwt.text.client.DoubleRenderer;

/**
 * This is the abstract base class for a {@link NumberBox} that represents a number input for {@link Double} values.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class DoubleSpinBox extends NumberBox<Double> {

  /**
   * The constructor.
   */
  public DoubleSpinBox() {

    super(JavaScriptUtil.getInstance().createInputElement(INPUT_TYPE_NUMBER), DoubleRenderer.instance(),
        DoubleParser.instance());
    getElement().setAttribute("step", "any");
  }

}