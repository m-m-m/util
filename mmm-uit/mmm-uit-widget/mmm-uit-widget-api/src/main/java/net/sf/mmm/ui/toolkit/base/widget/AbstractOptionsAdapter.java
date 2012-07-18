/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.base.widget;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.mmm.ui.toolkit.api.attribute.AttributeWriteFormatter;
import net.sf.mmm.ui.toolkit.api.attribute.AttributeWriteOptions;
import net.sf.mmm.util.lang.api.Formatter;
import net.sf.mmm.util.lang.api.attribute.AttributeWriteValue;
import net.sf.mmm.util.lang.base.FormatterToString;
import net.sf.mmm.util.nls.api.NlsNullPointerException;

/**
 * This is an abstract class that helps to implement
 * {@link net.sf.mmm.ui.toolkit.api.widget.atomic.UiWidgetOptionsField}. Due to the lack of multi-inheritance
 * it should be extended as inner class of the according widget base-class.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <VALUE> is the generic type of the {@link #getValue() value}.
 */
public abstract class AbstractOptionsAdapter<VALUE> implements AttributeWriteValue<VALUE>,
    AttributeWriteOptions<VALUE>, AttributeWriteFormatter<VALUE> {

  /** @see #getOptions() */
  private final List<VALUE> mutableOptions;

  /** @see #getOptions() */
  private final List<VALUE> options;

  /** Maps the {@link Formatter#format(Object) formatted} title back to the original value. */
  private final Map<String, VALUE> title2OptionMap;

  /** @see #getFormatter() */
  private Formatter<VALUE> formatter;

  /** @see #getValue() */
  private VALUE storedValue;

  /**
   * The constructor.
   */
  public AbstractOptionsAdapter() {

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
    this.mutableOptions.clear();
    this.mutableOptions.addAll(options);
    List<String> titles = new ArrayList<String>(options.size());
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
      titles.add(title);
    }
    doSetOptions(titles);
    this.storedValue = null;
    setValue(currentValue);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public VALUE getValue() {

    if (this.storedValue != null) {
      return this.storedValue;
    }
    String title = getValueAsString();
    return this.title2OptionMap.get(title);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setValue(VALUE value) {

    if (this.title2OptionMap.isEmpty()) {
      // options may be set asynchronously after value has been set...
      this.storedValue = value;
      return;
    }
    for (String title : this.title2OptionMap.keySet()) {
      VALUE option = this.title2OptionMap.get(title);
      if (value.equals(option)) {
        setValueAsString(title);
        return;
      }
    }
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

}
