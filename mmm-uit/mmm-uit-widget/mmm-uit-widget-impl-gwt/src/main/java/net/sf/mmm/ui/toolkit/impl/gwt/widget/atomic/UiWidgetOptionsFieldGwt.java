/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.gwt.widget.atomic;

import java.util.List;

import net.sf.mmm.ui.toolkit.api.widget.atomic.UiWidgetOptionsField;
import net.sf.mmm.ui.toolkit.base.widget.AbstractOptionsAdapter;
import net.sf.mmm.util.lang.api.Formatter;

import com.google.gwt.user.client.ui.Widget;

/**
 * This is the implementation of {@link UiWidgetOptionsField} using GWT.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <VALUE> is the generic type of the {@link #getValue() value}.
 * @param <WIDGET> is the generic type of {@link #getWidget()}.
 */
public abstract class UiWidgetOptionsFieldGwt<VALUE, WIDGET extends Widget> extends UiWidgetFieldGwt<VALUE, WIDGET>
    implements UiWidgetOptionsField<VALUE> {

  /** @see #getOptionsAdapter() */
  private final OptionsAdapterImpl optionsAdapter;

  /**
   * The constructor.
   * 
   * @param widget is the {@link #getWidget() widget}.
   */
  public UiWidgetOptionsFieldGwt(WIDGET widget) {

    super(widget);
    this.optionsAdapter = new OptionsAdapterImpl();
  }

  /**
   * @return the {@link OptionsAdapterImpl}.
   */
  OptionsAdapterImpl getOptionsAdapter() {

    return this.optionsAdapter;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<VALUE> getOptions() {

    return this.optionsAdapter.getOptions();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setOptions(List<VALUE> options) {

    this.optionsAdapter.setOptions(options);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public VALUE getValueOrException() {

    return this.optionsAdapter.getValue();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doSetValue(VALUE value) {

    this.optionsAdapter.setValue(value);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Formatter<VALUE> getFormatter() {

    return this.optionsAdapter.getFormatter();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setFormatter(Formatter<VALUE> formatter) {

    this.optionsAdapter.setFormatter(formatter);
  }

  /**
   * This method has to be implemented in order to populate the options as {@link List} of display titles.
   * 
   * @param titles is the {@link List} with the display titles.
   */
  protected abstract void doSetOptions(List<String> titles);

  /**
   * This method gets the current value from the widget.
   * 
   * @return the current widget value (display title) as {@link String}.
   */
  protected abstract String getValueAsString();

  /**
   * This method sets the current value in the widget.
   * 
   * @param value the new widget value (display title) as {@link String}.
   */
  protected abstract void setValueAsString(String value);

  /**
   * This inner class is the implementation of {@link AbstractOptionsAdapter}.
   */
  protected class OptionsAdapterImpl extends AbstractOptionsAdapter<VALUE> {

    /**
     * The constructor.
     */
    public OptionsAdapterImpl() {

      super();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doSetOptions(List<String> titles) {

      UiWidgetOptionsFieldGwt.this.doSetOptions(titles);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getValueAsString() {

      return UiWidgetOptionsFieldGwt.this.getValueAsString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void setValueAsString(String value) {

      UiWidgetOptionsFieldGwt.this.setValueAsString(value);
    }

  }

}
