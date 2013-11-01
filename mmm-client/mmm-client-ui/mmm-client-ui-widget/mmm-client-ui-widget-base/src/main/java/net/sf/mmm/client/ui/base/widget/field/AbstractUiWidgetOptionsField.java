/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget.field;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.mmm.client.ui.NlsBundleClientUiRoot;
import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.attribute.AttributeWriteAllowCustomInput;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetOptionsField;
import net.sf.mmm.client.ui.base.widget.field.adapter.UiWidgetAdapterOptionsField;
import net.sf.mmm.util.lang.api.Datatype;
import net.sf.mmm.util.lang.api.Formatter;
import net.sf.mmm.util.lang.base.FormatterToString;
import net.sf.mmm.util.nls.api.NlsAccess;
import net.sf.mmm.util.nls.api.NlsNullPointerException;
import net.sf.mmm.util.validation.api.ValidationState;
import net.sf.mmm.util.validation.base.ValidationFailureImpl;

/**
 * This is the abstract base implementation of {@link UiWidgetOptionsField}.
 * 
 * @param <VALUE> is the generic type of the {@link #getValue() value}.
 * @param <ADAPTER> is the generic type of {@link #getWidgetAdapter()}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractUiWidgetOptionsField<ADAPTER extends UiWidgetAdapterOptionsField<VALUE>, VALUE> extends
    AbstractUiWidgetField<ADAPTER, VALUE, String> implements UiWidgetOptionsField<VALUE>,
    AttributeWriteAllowCustomInput {

  /** TODO: javadoc. */
  private static final String CODE_UNDEFINED_OPTION = "UndefinedOption";

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

  /** @see #isAllowCustomInput() */
  private boolean allowCustomInput;

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   */
  public AbstractUiWidgetOptionsField(UiContext context) {

    super(context);
    this.mutableOptions = new ArrayList<VALUE>();
    this.options = Collections.unmodifiableList(this.mutableOptions);
    this.title2OptionMap = new HashMap<String, VALUE>();
    this.formatter = FormatterToString.getInstance();
    this.allowCustomInput = false;
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  @Override
  protected Class<VALUE> getValueClass() {

    // TODO hohwille This is a really bad hack that has to be removed!!!
    return (Class) Datatype.class;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void initializeWidgetAdapter(ADAPTER adapter) {

    super.initializeWidgetAdapter(adapter);
    if (this.optionTitleList != null) {
      adapter.setOptions(this.optionTitleList);
    }
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
    StringBuilder titleBuffer = null;
    for (VALUE option : options) {
      String title = this.formatter.format(option);
      if (title == null) {
        title = "<>";
      }
      if (this.title2OptionMap.containsKey(title)) {
        if (titleBuffer == null) {
          titleBuffer = new StringBuilder(title);
        } else {
          titleBuffer.setLength(0);
          titleBuffer.append(title);
        }
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
  protected VALUE doGetValue(VALUE template, ValidationState state) throws RuntimeException {

    if (this.optionTitleList == null) {
      // the options have not been set yet! As they may be received asynchronously but the widget adapter may
      // have already been created, we return the original value here. Without the options it should be
      // impossible for the user to change the value...
      return getOriginalValue();
    }
    return super.doGetValue(template, state);
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
  protected VALUE convertToValue(String adapterValue, ValidationState state) {

    VALUE value = this.title2OptionMap.get(adapterValue);
    if ((value == null) && (adapterValue != null) && (adapterValue.length() > 0)) {
      // user entered a value that is not available as option...
      if (this.allowCustomInput) {
        return convertCustomInputToValue(adapterValue);
      } else if (state != null) {
        state.onFailure(new ValidationFailureImpl(CODE_UNDEFINED_OPTION, getSource(), NlsAccess.getBundleFactory()
            .createBundle(NlsBundleClientUiRoot.class).failureUndefinedOption()));
      }
    }
    return value;
  }

  /**
   * Converts the given <code>customInput</code> to {@literal <VALUE>}.
   * 
   * @see #isAllowCustomInput()
   * 
   * @param customInput is the custom input.
   * @return the converted value.
   */
  @SuppressWarnings("unchecked")
  protected VALUE convertCustomInputToValue(String customInput) {

    return (VALUE) customInput;
    // return getDataBinding().getValueFromString(customInput);
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

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isAllowCustomInput() {

    return this.allowCustomInput;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setAllowCustomInput(boolean allowCustomInput) {

    this.allowCustomInput = allowCustomInput;
  }

}
