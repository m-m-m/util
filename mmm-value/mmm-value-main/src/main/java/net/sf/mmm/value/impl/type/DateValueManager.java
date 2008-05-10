/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.value.impl.type;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

import net.sf.mmm.util.StringUtil;
import net.sf.mmm.util.date.Iso8601Util;
import net.sf.mmm.util.value.api.ValueParseException;
import net.sf.mmm.value.api.ValueParseStringException;
import net.sf.mmm.value.base.AbstractValueManager;

/**
 * This is the {@link net.sf.mmm.value.api.ValueManager manager} for
 * {@link Date} values.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class DateValueManager extends AbstractValueManager<Date> {

  /** the {@link #getName() "logical name"} of the managed value. */
  public static final String VALUE_NAME = "Date";

  /** the {@link #getValueClass() type} of the managed value. */
  private static final Class<Date> VALUE_TYPE = Date.class;

  /** the XML tagname for the date */
  private static final String XML_TAG_DATE = "date";

  /** the XML attribute for the year */
  private static final String XML_ATR_DATE_YEAR = "year";

  /** the XML attribute for the month */
  private static final String XML_ATR_DATE_MONTH = "month";

  /** the XML attribute for the day */
  private static final String XML_ATR_DATE_DAY = "day";

  /** the XML tagname for the time */
  private static final String XML_TAG_TIME = "time";

  /** the XML attribute for the hour */
  private static final String XML_ATR_TIME_HOUR = "hour";

  /** the XML attribute for the minute */
  private static final String XML_ATR_TIME_MINUTE = "minute";

  /** the XML attribute for the second */
  private static final String XML_ATR_TIME_SECOND = "second";

  /** the XML attribute for the timezone */
  private static final String XML_ATR_TIME_ZONE = "timezone";

  /**
   * The constructor.
   */
  public DateValueManager() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  public String getName() {

    return VALUE_NAME;
  }

  /**
   * {@inheritDoc}
   */
  public Class<Date> getValueClass() {

    return VALUE_TYPE;
  }

  /**
   * {@inheritDoc}
   */
  public Date fromString(String valueAsString) throws ValueParseException {

    try {
      return Iso8601Util.getInstance().parseDate(valueAsString);
    } catch (Exception e) {
      throw new ValueParseStringException(valueAsString, VALUE_TYPE, VALUE_NAME, e);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Date fromXmlContent(XMLStreamReader xmlReader) throws XMLStreamException {

    // date
    int year = getStaxUtil().parseAttribute(xmlReader, null, XML_ATR_DATE_YEAR, Integer.class)
        .intValue();
    int month = getStaxUtil().parseAttribute(xmlReader, null, XML_ATR_DATE_MONTH, Integer.class)
        .intValue();
    int day = getStaxUtil().parseAttribute(xmlReader, null, XML_ATR_DATE_DAY, Integer.class)
        .intValue();
    // time
    Integer zero = Integer.valueOf(0);
    int hour = getStaxUtil()
        .parseAttribute(xmlReader, null, XML_ATR_TIME_HOUR, Integer.class, zero).intValue();
    int min = getStaxUtil().parseAttribute(xmlReader, null, XML_ATR_TIME_MINUTE, Integer.class,
        zero).intValue();
    int sec = getStaxUtil().parseAttribute(xmlReader, null, XML_ATR_TIME_SECOND, Integer.class,
        zero).intValue();
    String tz = xmlReader.getAttributeValue(null, XML_ATR_TIME_ZONE);
    if (tz == null) {
      tz = "UTC";
    }
    TimeZone zone = TimeZone.getTimeZone(tz);
    Calendar calendar = Calendar.getInstance(zone);
    calendar.set(year, (month - 1), day, hour, min, sec);
    calendar.set(Calendar.MILLISECOND, 0);
    return calendar.getTime();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected String toStringNotNull(Date value) {

    return Iso8601Util.getInstance().formatDateTime(value);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void toXmlContent(XMLStreamWriter xmlWriter, Date value) throws XMLStreamException {

    Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
    calendar.setTime(value);
    int year = calendar.get(Calendar.YEAR);
    xmlWriter.writeAttribute(XML_ATR_DATE_YEAR, Integer.toString(year));
    int month = calendar.get(Calendar.MONTH) + 1;
    xmlWriter.writeAttribute(XML_ATR_DATE_MONTH, Integer.toString(month));
    int day = calendar.get(Calendar.DAY_OF_MONTH);
    xmlWriter.writeAttribute(XML_ATR_DATE_DAY, Integer.toString(day));
    int hour = calendar.get(Calendar.HOUR_OF_DAY);
    if (hour != 0) {
      xmlWriter.writeAttribute(XML_ATR_TIME_HOUR, StringUtil.getInstance().padNumber(hour, 2));
    }
    int min = calendar.get(Calendar.MINUTE);
    if (min != 0) {
      xmlWriter.writeAttribute(XML_ATR_TIME_MINUTE, StringUtil.getInstance().padNumber(min, 2));
    }
    int sec = calendar.get(Calendar.SECOND);
    if (sec != 0) {
      xmlWriter.writeAttribute(XML_ATR_TIME_SECOND, StringUtil.getInstance().padNumber(sec, 2));
    }
  }

}
