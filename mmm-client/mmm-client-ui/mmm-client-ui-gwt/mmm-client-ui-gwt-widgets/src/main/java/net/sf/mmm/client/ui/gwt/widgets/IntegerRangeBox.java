/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.gwt.widgets;

import net.sf.mmm.util.gwt.api.JavaScriptUtil;

import com.google.gwt.text.client.IntegerParser;
import com.google.gwt.text.client.IntegerRenderer;

/**
 * This is the abstract base class for a {@link NumberBox} that represents a range input for {@link Integer} values.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class IntegerRangeBox extends NumberBox<Integer> {

  /**
   * The constructor.
   */
  public IntegerRangeBox() {

    super(JavaScriptUtil.getInstance().createInputElement(HtmlConstants.INPUT_TYPE_RANGE), IntegerRenderer.instance(),
        IntegerParser.instance());
  }

}