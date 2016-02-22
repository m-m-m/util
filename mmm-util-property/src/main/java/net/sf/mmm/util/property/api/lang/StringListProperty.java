/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.api.lang;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.sf.mmm.util.bean.api.Bean;
import net.sf.mmm.util.validation.base.AbstractValidator;

/**
 * This is an extension of {@link StringProperty} that stores a list of {@link String} values that are enclosed with
 * pipe-symbols.
 *
 * @author hohwille
 * @since 8.0.0
 */
public class StringListProperty extends StringProperty {

  private static final char SEPARATOR = '|';

  private static final String SEPARATOR_PATTERN = "\\" + SEPARATOR;

  private static final char DEFAULT_CSV_SEPARATOR = ',';

  private static final String DEFAULT_CSV_SEPARATOR_STRING = String.valueOf(DEFAULT_CSV_SEPARATOR);

  /**
   * The constructor.
   *
   * @param name - see {@link #getName()}.
   * @param bean - see {@link #getBean()}.
   */
  public StringListProperty(String name, Bean bean) {
    super(name, bean);
  }

  /**
   * The constructor.
   *
   * @param name - see {@link #getName()}.
   * @param bean - see {@link #getBean()}.
   * @param validator - see {@link #validate()}.
   */
  public StringListProperty(String name, Bean bean, AbstractValidator<? super String> validator) {
    super(name, bean, validator);
  }

  @Override
  protected boolean doSetValue(String newValue) {

    if (newValue != null) {
      int length = newValue.length();
      if (length > 0) {
        if ((length < 2) || (newValue.charAt(0) != SEPARATOR) || (newValue.charAt(length - 1) != SEPARATOR)) {
          throw new IllegalArgumentException(newValue);
        }
      }
    }
    return super.doSetValue(newValue);
  }

  private String wrap(String value) {

    return SEPARATOR + value + SEPARATOR;
  }

  /**
   * @see List#remove(Object)
   *
   * @param element the element to remove from the {@link #getValueAsList() value as list}.
   * @return <code>true</code> if the given element was previously present and is now removed so the value actually has
   *         changed, {@code false} otherwise (if not present and no change).
   */
  public boolean remove(String element) {

    String wrapped = wrap(element);
    String value = getValue();
    if ((value == null) || (value.isEmpty())) {
      return false;
    }
    int index = value.indexOf(wrapped);
    if (index < 0) {
      return false;
    }
    int length = wrapped.length();
    if (length == value.length()) {
      value = "";
    } else {
      value = value.substring(0, index) + value.substring(index + length - 1);
    }
    setValue(value);
    return true;
  }

  /**
   * @see List#add(Object)
   *
   * @param element the element to add to the {@link #getValueAsList() value as list}.
   * @param distinct {@code true} if the value should be treated as {@link Set} and this method should not change the
   *        {@link #getValue() value} if the given {@code element} is already {@link #contains(String) present},
   *        {@code false} otherwise.
   * @return {@code true} if the {@link #getValue() value} has changed, {@code false} otherwise (if {@code distinct} was
   *         {@code true} and the given {@code element} was already {@link #contains(String) present}).
   */
  public boolean add(String element, boolean distinct) {

    String wrapped = wrap(element);
    String value = getValue();
    if ((value == null) || (value.isEmpty())) {
      value = wrapped;
    } else if (distinct && value.contains(wrapped)) {
      return false;
    } else {
      value = value + element + SEPARATOR;
    }
    setValue(value);
    return true;
  }

  /**
   * @see List#contains(Object)
   *
   * @param element the element to check.
   * @return {@code true} if the given {@code element} is contained in the {@link #getValueAsList() value as list}.
   */
  public boolean contains(String element) {

    return getValue().contains(wrap(element));
  }

  private List<String> asList() {

    String value = getValue();
    if ((value == null) || (value.isEmpty())) {
      return Collections.emptyList();
    }
    int length = value.length();
    if (length < 2) {
      throw new IllegalStateException(value);
    }
    assert (value.charAt(0) == SEPARATOR);
    assert (value.charAt(length - 1) == SEPARATOR);
    String[] elements = value.substring(1, length - 1).split(SEPARATOR_PATTERN);
    // List<String> result = new ArrayList<>(elements.length);
    return Arrays.asList(elements);
  }

  /**
   * @return a {@link List} with the tokenized {@link #getValue() value}.
   */
  public List<String> getValueAsList() {

    return new ArrayList<>(asList());
  }

  /**
   * @return a {@link Set} with the tokenized {@link #getValue() value}.
   */
  public Set<String> getValueAsSet() {

    return new HashSet<>(asList());
  }

  /**
   * @param list the {@link List} of elements to set as separated {@link #setValue(String) string value}.
   */
  public void setValueAsCollection(Collection<String> list) {

    StringBuilder buffer = new StringBuilder(list.size() * 10);
    buffer.append(SEPARATOR);
    for (String element : list) {
      if (element == null) {
        element = "";
      }
      assert (element.indexOf(SEPARATOR) == -1);
      buffer.append(element);
      buffer.append(SEPARATOR);
    }
    setValue(buffer.toString());
  }

  /**
   * @return the {@link #getValue() value} with comma as separator.
   */
  public String getValueAsCsv() {

    return getValueAsCsv(DEFAULT_CSV_SEPARATOR_STRING);
  }

  /**
   * @param separator the character used as separator.
   * @return the {@link #getValue() value} with elements separated by the given {@code separator}.
   */
  public String getValueAsCsv(String separator) {

    String value = getValue();
    if ((value == null) || (value.isEmpty())) {
      return value;
    }
    int length = value.length();
    if (length < 2) {
      throw new IllegalStateException(value);
    }
    assert (value.charAt(0) == SEPARATOR);
    assert (value.charAt(length - 1) == SEPARATOR);
    return value.substring(1, length - 1).replaceAll(SEPARATOR_PATTERN, separator);
  }

  /**
   * @param csv the new {@link #getValue() value} as comma separated value (e.g. "a,b,c").
   */
  public void setValueAsCsv(String csv) {

    setValueAsCsv(csv, DEFAULT_CSV_SEPARATOR, true);
  }

  /**
   * @param csv the new {@link #getValue() value} with elements separated by the given {@code separator}.
   * @param separator the character used as separator.
   */
  public void setValueAsCsv(String csv, char separator) {

    setValueAsCsv(csv, separator, true);
  }

  /**
   * @param csv the new {@link #getValue() value} with elements separated by the given {@code separator}.
   * @param separator the character used as separator.
   * @param trim if the elements from the given {@code csv} should be {@link String#trim() trimmed}.
   */
  public void setValueAsCsv(String csv, char separator, boolean trim) {

    String value;
    if ((csv == null) || csv.isEmpty()) {
      value = csv;
    } else if (trim) {
      value = SEPARATOR + csv.trim().replaceAll("[ ]*" + separator + "[ ]*", SEPARATOR_PATTERN) + SEPARATOR;
    } else {
      value = SEPARATOR + csv.replace(separator, SEPARATOR) + SEPARATOR;
    }
    setValue(value);
  }

}
