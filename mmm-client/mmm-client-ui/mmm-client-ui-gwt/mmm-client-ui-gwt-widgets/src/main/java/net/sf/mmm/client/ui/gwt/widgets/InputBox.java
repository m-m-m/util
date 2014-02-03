/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.gwt.widgets;

import net.sf.mmm.client.ui.api.attribute.AttributeWriteHtmlId;
import net.sf.mmm.client.ui.api.attribute.AttributeWriteMaximumTextLength;

import com.google.gwt.dom.client.InputElement;
import com.google.gwt.text.shared.Parser;
import com.google.gwt.text.shared.Renderer;
import com.google.gwt.user.client.ui.ValueBoxBase;

/**
 * A {@link InputBox} extends {@link ValueBoxBase} for HTML5 input widgets.
 * 
 * @param <VALUE> is the generic type of the {@link #getValue() value}.
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class InputBox<VALUE> extends ValueBoxBase<VALUE> implements AttributeWriteDataList,
    AttributeWriteMaximumTextLength, AttributeWriteHtmlId {

  /**
   * The constructor.
   * 
   * @param element - see {@link #getInputElement()}.
   * @param renderer is the {@link Renderer} for the {@literal <VALUE>}.
   * @param parser is the {@link Parser} for the {@literal <VALUE>}.
   */
  public InputBox(InputElement element, Renderer<VALUE> renderer, Parser<VALUE> parser) {

    super(element, renderer, parser);
  }

  /**
   * {@inheritDoc}
   */
  public void setDataList(DataList dataList) {

    getElement().setAttribute(HtmlConstants.ATTRIBUTE_LIST, dataList.getId());
    dataList.setOwner(this);
  }

  /**
   * @return the {@link #getElement() element} as {@link InputElement}.
   */
  protected InputElement getInputElement() {

    return getElement().cast();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getMaximumTextLength() {

    return getInputElement().getMaxLength();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setMaximumTextLength(int length) {

    getInputElement().setMaxLength(length);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getId() {

    return getElement().getId();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setId(String id) {

    getElement().setId(id);
  }

}
