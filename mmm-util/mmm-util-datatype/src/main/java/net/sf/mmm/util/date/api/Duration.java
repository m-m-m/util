/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.date.api;

import java.util.concurrent.TimeUnit;

import net.sf.mmm.util.date.api.attribute.AttributeReadDays;
import net.sf.mmm.util.date.api.attribute.AttributeReadHours;
import net.sf.mmm.util.date.api.attribute.AttributeReadMilliseconds;
import net.sf.mmm.util.date.api.attribute.AttributeReadMinutes;
import net.sf.mmm.util.date.api.attribute.AttributeReadSeconds;
import net.sf.mmm.util.lang.api.AbstractSimpleDatatype;
import net.sf.mmm.util.lang.api.StringUtil;
import net.sf.mmm.util.lang.base.StringUtilImpl;
import net.sf.mmm.util.nls.api.NlsNullPointerException;

/**
 * This class is a {@link net.sf.mmm.util.lang.api.Datatype} that represents a time duration in milliseconds.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.0.1
 */
public class Duration extends AbstractSimpleDatatype<Long> implements AttributeReadMilliseconds, AttributeReadSeconds,
    AttributeReadMinutes, AttributeReadHours, AttributeReadDays, Comparable<Duration> {

  /** UID for serialization. */
  private static final long serialVersionUID = -6542588787452589302L;

  /** @see #getDays() */
  private transient int days;

  /** @see #getHours() */
  private transient int hours;

  /** @see #getMinutes() */
  private transient int minutes;

  /** @see #getSeconds() */
  private transient int seconds;

  /** @see #getMilliseconds() */
  private transient int milliseconds;

  /**
   * The constructor for de-serialization.
   */
  protected Duration() {

    super();
    this.milliseconds = -1;
  }

  /**
   * The constructor.
   * 
   * @param value is the duration in milliseconds.
   */
  public Duration(Long value) {

    super(value);
    this.milliseconds = -1;
  }

  /**
   * Returns the duration in milliseconds.
   * 
   * {@inheritDoc}
   */
  @Override
  public Long getValue() {

    return super.getValue();
  }

  /**
   * Returns the duration in the given {@link TimeUnit}.
   * 
   * @param unit is the {@link TimeUnit}.
   * @return the duration in the given {@link TimeUnit}.
   */
  public Long getValue(TimeUnit unit) {

    return Long.valueOf(unit.convert(getValue().longValue(), TimeUnit.MILLISECONDS));
  }

  /**
   * This method lazily initializes the transient fields. On the first call all transient fields are
   * calculated and stored. Further invocations will return immediately.
   */
  private void initFields() {

    if (this.milliseconds == -1) {
      long duration = getValue().longValue();
      this.milliseconds = (int) (duration % 1000);
      duration = duration / 1000;
      this.seconds = (int) (duration % 60);
      duration = duration / 60;
      this.minutes = (int) (duration % 60);
      duration = duration / 60;
      this.hours = (int) (duration % 24);
      this.days = (int) (duration / 24);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getDays() {

    initFields();
    return this.days;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getHours() {

    initFields();
    return this.hours;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getMinutes() {

    initFields();
    return this.minutes;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getSeconds() {

    initFields();
    return this.seconds;
  }

  /**
   * {@inheritDoc}
   * 
   * <br/>
   * <b>NOTE:</b><br/>
   * To get the entire duration in milliseconds you need to call {@link #getValue()}. However, please note
   * that the exact number of milliseconds of a duration is NOT known until {@link AdvancedDate#add(Duration)
   * added} to a specific date. E.g. a duration of one day can also be 23 or 25 hours due to daylight savings.
   */
  @Override
  public int getMilliseconds() {

    initFields();
    return this.milliseconds;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int compareTo(Duration o) {

    NlsNullPointerException.checkNotNull(Duration.class, o);
    return getValue().compareTo(o.getValue());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getTitle() {

    StringBuilder buffer = new StringBuilder();
    StringUtil stringUtil = StringUtilImpl.getInstance();
    if (this.days > 0) {
      buffer.append(this.days);
      buffer.append("d");
    }
    if ((this.hours > 0) || (buffer.length() > 0)) {
      buffer.append(stringUtil.padNumber(this.hours, 2));
      buffer.append(":");
    }
    if ((this.minutes > 0) || (buffer.length() > 0)) {
      buffer.append(stringUtil.padNumber(this.minutes, 2));
      buffer.append(":");
    }
    buffer.append(stringUtil.padNumber(this.seconds, 2));
    if (this.milliseconds > 0) {
      buffer.append(".");
      buffer.append(stringUtil.padNumber(this.milliseconds, 4));
    }
    return buffer.toString();
  }

}
