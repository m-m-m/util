/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.base;

import java.util.Calendar;
import java.util.Date;

import net.sf.mmm.search.api.config.SearchFieldConfiguration;
import net.sf.mmm.search.api.config.SearchFieldType;
import net.sf.mmm.search.api.config.SearchFields;
import net.sf.mmm.util.math.api.NumberType;
import net.sf.mmm.util.value.api.ValueConvertException;

/**
 * This is the abstract base implementation of the
 * {@link net.sf.mmm.search.api.SearchEntry} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class BasicSearchEntry extends AbstractSearchEntry {

  /** @see #getSearchDependencies() */
  private final SearchDependencies searchDependencies;

  /** @see #getSearchFields() */
  private final SearchFields searchFields;

  /**
   * The constructor.
   * 
   * @param searchFields are the {@link SearchFields}.
   * @param searchDependencies are the {@link SearchDependencies}.
   */
  public BasicSearchEntry(SearchFields searchFields, SearchDependencies searchDependencies) {

    super();
    this.searchFields = searchFields;
    this.searchDependencies = searchDependencies;
  }

  /**
   * This method gets the {@link SearchFields}.
   * 
   * @return the {@link SearchFields}.
   */
  protected SearchFields getSearchFields() {

    return this.searchFields;
  }

  /**
   * This method gets the {@link SearchDependencies}.
   * 
   * @return the {@link SearchDependencies}.
   */
  protected SearchDependencies getSearchDependencies() {

    return this.searchDependencies;
  }

  /**
   * @see #getField(String)
   * 
   * @param name is the
   *        {@link net.sf.mmm.search.api.config.SearchFieldConfiguration#getName()
   *        name} of the requested field.
   * @return the raw value of the field as it is given by the underlying search
   *         technology.
   */
  protected abstract Object getFieldRaw(String name);

  /**
   * {@inheritDoc}
   */
  public Object getField(String name) {

    Class<?> fieldType = String.class;
    SearchFieldConfiguration fieldConfiguration = getSearchFields().getFieldConfiguration(name);
    if (fieldConfiguration != null) {
      fieldType = fieldConfiguration.getType().getFieldClass();
    }
    return getField(name, fieldType);
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("unchecked")
  public <T> T getField(String name, Class<T> type) {

    Object fieldValue = getFieldRaw(name);
    if (fieldValue == null) {
      return null;
    }
    Class<?> fieldType = fieldValue.getClass();
    // if (type.isAssignableFrom(fieldType)) {
    // return type.cast(fieldValue);
    // }
    // conversion required...
    Object result = null;
    if ((type == String.class) || (type == CharSequence.class)) {
      // TODO: Date, etc.
      SearchFieldConfiguration fieldConfiguration = this.searchFields.getFieldConfiguration(name);
      if (fieldConfiguration != null) {
        if (fieldConfiguration.getType() == SearchFieldType.DATE) {
          if (fieldType == Long.class) {
            Long millis = (Long) fieldValue;
            fieldValue = new Date(millis.longValue());
          }
        }
      }
      if (fieldValue instanceof Date) {
        fieldValue = this.searchDependencies.getIso8601Util().formatDateTime((Date) fieldValue);
      }
      result = fieldValue.toString();
    } else if ((type == Calendar.class) || (type == Date.class)) {
      Date date = null;
      if (fieldType == Date.class) {
        date = (Date) fieldValue;
      } else if (fieldType == Long.class) {
        Long millis = (Long) fieldValue;
        date = new Date(millis.longValue());
      } else if (CharSequence.class.isAssignableFrom(fieldType)) {
        date = this.searchDependencies.getIso8601Util().parseDate(fieldValue.toString());
      }
      if (date != null) {
        if (type == Calendar.class) {
          Calendar calendar = Calendar.getInstance();
          calendar.setTime(date);
          result = calendar;
        } else {
          result = date;
        }
      }
    } else if (Number.class.isAssignableFrom(type)) {
      NumberType<? extends Number> numberType = this.searchDependencies.getMathUtil()
          .getNumberType(type);
      if (Number.class.isAssignableFrom(fieldType)) {
        Number number = (Number) fieldValue;
        result = numberType.valueOf(number, true);
      } else if (CharSequence.class.isAssignableFrom(fieldType)) {
        result = numberType.valueOf(fieldValue.toString());
      } else if (fieldType == Date.class) {
        Date date = (Date) fieldValue;
        result = numberType.valueOf(Long.valueOf(date.getTime()), true);
      }
    }
    if (result == null) {
      throw new ValueConvertException(fieldValue, type, name);
    }
    return (T) result;
  }

}
