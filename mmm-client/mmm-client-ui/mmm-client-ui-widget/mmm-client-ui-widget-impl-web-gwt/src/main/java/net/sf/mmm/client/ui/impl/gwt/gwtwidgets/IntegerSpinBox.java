/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.gwtwidgets;

import com.google.gwt.text.client.IntegerParser;
import com.google.gwt.text.client.IntegerRenderer;

/**
 * This is the abstract base class for a {@link NumberBox} that represents a number input for {@link Integer} values.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class IntegerSpinBox extends NumberSpinBox<Integer> {

  /**
   * The constructor.
   */
  public IntegerSpinBox() {

    super(IntegerRenderer.instance(), IntegerParser.instance());
  }

}