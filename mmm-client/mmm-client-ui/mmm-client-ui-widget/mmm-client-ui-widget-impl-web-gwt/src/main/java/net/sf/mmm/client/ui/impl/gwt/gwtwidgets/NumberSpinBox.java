/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.gwtwidgets;

import net.sf.mmm.util.gwt.api.JavaScriptUtil;

import com.google.gwt.text.shared.Parser;
import com.google.gwt.text.shared.Renderer;

/**
 * This is the abstract base class for a {@link NumberBox} that represents a number input for {@link Integer} values.
 * 
 * @param <VALUE> is the generic type of the {@link #getValue() value}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class NumberSpinBox<VALUE extends Number> extends NumberBox<VALUE> {

  /**
   * The constructor.
   * 
   * @param renderer is the {@link Renderer} used to format the value.
   * @param parser is the {@link Parser} used to parse the input as value.
   */
  public NumberSpinBox(Renderer<VALUE> renderer, Parser<VALUE> parser) {

    super(JavaScriptUtil.getInstance().createInputElement(HtmlConstants.INPUT_TYPE_NUMBER), renderer, parser);
  }

  /**
   * @param step is the step used to increment or decrement the value when arrows are used.
   */
  public void setStep(int step) {

    assert (step > 0);
    getElement().setAttribute("step", Integer.toString(step));
  }

}