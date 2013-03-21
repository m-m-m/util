/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.date.base;

import java.util.Date;
import java.util.Locale;

import javax.inject.Named;
import javax.inject.Singleton;

import net.sf.mmm.util.component.base.AbstractLoggableComponent;
import net.sf.mmm.util.date.api.AdvancedDateMidnight;
import net.sf.mmm.util.date.api.AdvancedDateTime;
import net.sf.mmm.util.date.api.DateUtil;
import net.sf.mmm.util.date.api.Month;
import net.sf.mmm.util.date.api.SimpleDate;
import net.sf.mmm.util.date.api.SimpleTime;

/**
 * This is the implementation of the {@link net.sf.mmm.util.date.api.DateUtil} interface.
 * 
 * @see #getInstance()
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@Singleton
@Named
public class DateUtilImpl extends AbstractLoggableComponent implements DateUtil {

  /** @see #getInstance() */
  private static DateUtil instance;

  /**
   * The constructor.
   */
  public DateUtilImpl() {

    super();
  }

  /**
   * This method gets the singleton instance of this {@link DateUtil}.<br/>
   * <b>ATTENTION:</b><br/>
   * Please read {@link net.sf.mmm.util.component.api.Cdi#GET_INSTANCE} before using.
   * 
   * @return the singleton instance.
   */
  public static DateUtil getInstance() {

    if (instance == null) {
      synchronized (DateUtilImpl.class) {
        if (instance == null) {
          DateUtilImpl impl = new DateUtilImpl();
          impl.initialize();
          instance = impl;
        }
      }
    }
    return instance;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public AdvancedDateMidnight createDateMidnight(int year, Month month, int day) {

    return createDateMidnight(year, month, day, null);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public AdvancedDateMidnight createDateMidnight(int year, Month month, int day, Locale locale) {

    Date date = new Date(year - 1900, month.getValue().intValue() - 1, day, 0, 0, 0);
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public AdvancedDateMidnight createDateMidnight(Date date) {

    return createDateMidnight(date.getYear() + 1900, Month.fromDate(date), date.getDate());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public AdvancedDateTime createDateTime(int year, Month month, int day, int hours, int minutes, int seconds) {

    // TODO Auto-generated method stub
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public AdvancedDateTime createDateTime(int year, Month month, int day, int hours, int minutes, int seconds,
      int milliseconds) {

    // TODO Auto-generated method stub
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public AdvancedDateTime createDateTime(int year, Month month, int day, int hours, int minutes, int seconds,
      int milliseconds, Locale locale) {

    // TODO Auto-generated method stub
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public AdvancedDateTime createDateTime(Date date) {

    // TODO Auto-generated method stub
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public AdvancedDateTime createDateTime(SimpleDate date, SimpleTime time) {

    // TODO Auto-generated method stub
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public AdvancedDateTime now() {

    // TODO Auto-generated method stub
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public AdvancedDateMidnight today() {

    // TODO Auto-generated method stub
    return null;
  }

}
