/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.base.widget.field;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.mmm.ui.toolkit.api.widget.field.UiWidgetOptionsField;
import net.sf.mmm.ui.toolkit.base.widget.adapter.UiWidgetAdapterOptionsField;
import net.sf.mmm.util.lang.api.Formatter;
import net.sf.mmm.util.lang.base.FormatterToString;
import net.sf.mmm.util.nls.api.NlsNullPointerException;

/**
 * This is the abstract base implementation of {@link UiWidgetOptionsField}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <VALUE> is the generic type of the {@link #getValue() value}.
 * @param <ADAPTER> is the generic type of {@link #getWidgetAdapter()}.
 */
public abstract class AbstractUiWidgetOptionsField<ADAPTER extends UiWidgetAdapterOptionsField<?, VALUE>, VALUE>
    extends AbstractUiWidgetField<ADAPTER, VALUE, String> implements UiWidgetOptionsField<VALUE> {

  /** @see #getOptions() */
  private final List<VALUE> mutableOptions;

  /** @see #getOptions() */
  private final List<VALUE> options;

  /** @see #setOptions(List) */
  private List<String> optionTitleList;

  /** Maps the {@link Formatter#format(Object) formatted} title back to the original value. */
  private final Map<String, VALUE> title2OptionMap;

  /** @see #getFormatter() */
  private Formatter<VALUE> formatter;

  /** @see #getNullValue() */
  private String nullValue;

  /**
   * The constructor.
   */
  public AbstractUiWidgetOptionsField() {

    super();
    this.mutableOptions = new ArrayList<VALUE>();
    this.options = Collections.unmodifiableList(this.mutableOptions);
    this.title2OptionMap = new HashMap<String, VALUE>();
    this.formatter = FormatterToString.getInstance();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setOptions(List<VALUE> options) {

    NlsNullPointerException.checkNotNull(List.class, options);
    VALUE currentValue = getValue();
    this.nullValue = null;
    this.mutableOptions.clear();
    this.mutableOptions.addAll(options);
    if (this.optionTitleList == null) {
      this.optionTitleList = new ArrayList<String>(options.size());
    } else {
      this.optionTitleList.clear();
    }
    for (VALUE option : options) {
      String title = this.formatter.format(option);
      if (title == null) {
        title = "<>";
      }
      if (this.title2OptionMap.containsKey(title)) {
        StringBuilder titleBuffer = new StringBuilder(title);
        int length = title.length();
        int duplicateIndex = 2;
        do {
          if (duplicateIndex >= 100) {
            throw new IllegalStateException("Too many options with ambiguous title!");
          }
          titleBuffer.setLength(length);
          titleBuffer.append(" (");
          titleBuffer.append(duplicateIndex++);
          titleBuffer.append(")");
          title = titleBuffer.toString();
        } while (this.title2OptionMap.containsKey(title));
      }
      this.title2OptionMap.put(title, option);
      if (option == null) {
        this.nullValue = title;
      }
      this.optionTitleList.add(title);
    }
    if (hasWidgetAdapter()) {
      getWidgetAdapter().setOptions(this.optionTitleList);
    }
    setValue(currentValue);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public VALUE getValueOrException() throws RuntimeException {

    if (this.optionTitleList == null) {
      // the options have not been set yet! As they may be received asynchronously but the widget adapter may
      // have already been created, we return the original value here.
      return getOriginalValue();
    }
    return super.getValueOrException();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected String convertFromValue(VALUE widgetValue) {

    for (String title : this.optionTitleList) {
      VALUE option = this.title2OptionMap.get(title);
      if (widgetValue.equals(option)) {
        return title;
      }
    }
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected VALUE convertToValue(String adapterValue) {

    return this.title2OptionMap.get(adapterValue);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected String getNullValue() {

    return this.nullValue;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<VALUE> getOptions() {

    return this.options;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setFormatter(Formatter<VALUE> formatter) {

    NlsNullPointerException.checkNotNull(Formatter.class, formatter);
    this.formatter = formatter;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Formatter<VALUE> getFormatter() {

    return this.formatter;
  }

}
