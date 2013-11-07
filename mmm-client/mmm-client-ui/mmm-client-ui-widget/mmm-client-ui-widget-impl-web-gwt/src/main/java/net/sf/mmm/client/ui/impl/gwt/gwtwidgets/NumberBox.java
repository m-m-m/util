/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.gwtwidgets;

import net.sf.mmm.util.lang.api.attribute.AttributeWriteMaximumValue;
import net.sf.mmm.util.lang.api.attribute.AttributeWriteMinimumValue;

import com.google.gwt.dom.client.InputElement;
import com.google.gwt.i18n.client.LocaleInfo;
import com.google.gwt.text.shared.Parser;
import com.google.gwt.text.shared.Renderer;

/**
 * This is the abstract base class for a {@link com.google.gwt.user.client.ui.ValueBoxBase} that represents a
 * number or range input.
 * 
 * @param <VALUE> is the generic type of the {@link #getValue() value}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class NumberBox<VALUE extends Number> extends InputBox<VALUE> implements
    AttributeWriteMinimumValue<VALUE>, AttributeWriteMaximumValue<VALUE> {

  /** @see #getMinimumValue() */
  private VALUE min;

  /** @see #getMaximumValue() */
  private VALUE max;

  /**
   * The constructor.
   * 
   * @param element is the {@link InputElement} to wrap.
   * @param renderer is the {@link Renderer} used to format the value.
   * @param parser is the {@link Parser} used to parse the input as value.
   */
  public NumberBox(InputElement element, Renderer<VALUE> renderer, Parser<VALUE> parser) {

    super(element, renderer, parser);
    setDirectionEstimator(false);
    if (LocaleInfo.getCurrentLocale().isRTL()) {
      setDirection(Direction.LTR);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public VALUE getMinimumValue() {

    return this.min;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setMinimumValue(VALUE minimum) {

    this.min = minimum;
    getElement().setAttribute("min", minimum.toString());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public VALUE getMaximumValue() {

    return this.max;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setMaximumValue(VALUE maximum) {

    this.max = maximum;
    getElement().setAttribute("max", maximum.toString());
  }

}
