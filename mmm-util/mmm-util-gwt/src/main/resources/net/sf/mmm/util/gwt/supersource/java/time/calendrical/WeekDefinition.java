/*
 * Copyright (c) 2011-2012, Stephen Colebourne & Michael Nascimento Santos
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  * Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 *  * Neither the name of JSR-310 nor the names of its contributors
 *    may be used to endorse or promote products derived from this software
 *    without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package java.time.calendrical;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.chrono.Chrono;
import java.time.chrono.ChronoLocalDate;
import java.time.jdk8.Jdk7Methods;
import java.time.jdk8.Jdk8Methods;
import java.util.Locale;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Expresses how a week is defined.
 * <p>
 * A standard week is seven days long, but cultures have different definitions for some other aspects of a
 * week.
 * <p>
 * WeekDefinition provides three fields, {@link #dayOfWeek()}, {@link #weekOfMonth()}, and
 * {@link #weekOfYear()} that provide access to the values from any {@link java.time.calendrical.DateTime
 * dateTime}.
 * <p>
 * The computations for day-of-week, week-of-month, and week-of-year are based on the {@link ChronoField#YEAR
 * proleptic-year}, {@link ChronoField#MONTH_OF_YEAR month-of-year}, {@link ChronoField#DAY_OF_MONTH
 * day-of-month}, and {@link ChronoField#DAY_OF_WEEK ISO day-of-week} which are based on the
 * {@link ChronoField#EPOCH_DAY epoch-day} and the chronology. The values may not be aligned with the
 * {@link ChronoField#YEAR_OF_ERA year-of-Era} depending on the Chronology.
 * <p>
 * A week is defined by:
 * <ul>
 * <li>The first day-of-week. For example, the ISO-8601 standard considers Monday to be the first day-of-week.
 * <li>The minimal number of days in the first week. For example, the ISO-08601 standard counts the first week
 * as needing at least 4 days.
 * </ul>
 * <p>
 * Together these two values allow a year or month to be divided into weeks.
 * <p>
 * <h4>Week of Month</h4>
 * One field is used: week-of-month. The calculation ensures that weeks never overlap a month boundary. The
 * month is divided into periods where each period starts on the defined first day-of-week. The earliest
 * period is referred to as week 0 if it has less than the minimal number of days and week 1 if it has at
 * least the minimal number of days.
 * <p>
 * <table cellpadding="0" cellspacing="3" border="0" style="text-align: left; width: 50%;">
 * <tr>
 * <th>Date</th>
 * <td>Day-of-week</td>
 * <td>First day: Monday<br />
 * Minimal days: 4</td>
 * <td>First day: Monday<br />
 * Minimal days: 5</td>
 * </tr>
 * <tr>
 * <th>2008-12-31</th>
 * <td>Wednesday</td>
 * <td>Week 5 of December 2008</td>
 * <td>Week 5 of December 2008</td>
 * </tr>
 * <tr>
 * <th>2009-01-01</th>
 * <td>Thursday</td>
 * <td>Week 1 of January 2009</td>
 * <td>Week 0 of January 2009</td>
 * </tr>
 * <tr>
 * <th>2009-01-04</th>
 * <td>Sunday</td>
 * <td>Week 1 of January 2009</td>
 * <td>Week 0 of January 2009</td>
 * </tr>
 * <tr>
 * <th>2009-01-05</th>
 * <td>Monday</td>
 * <td>Week 2 of January 2009</td>
 * <td>Week 1 of January 2009</td>
 * </tr>
 * </table>
 * <p>
 * <h4>Week of Year</h4>
 * One field is used: week-of-year. The calculation ensures that weeks never overlap a year boundary. The year
 * is divided into periods where each period starts on the defined first day-of-week. The earliest period is
 * referred to as week 0 if it has less than the minimal number of days and week 1 if it has at least the
 * minimal number of days.
 * <p>
 * This class is immutable and thread-safe.
 * 
 * @author Stephen Colebourne
 */
public final class WeekDefinition implements Serializable {

  // implementation notes
  // querying week-of-month or week-of-year should return the week value bound within the month/year
  // however, setting the week value should be lenient (use plus/minus weeks)
  // allow week-of-month outer range [0 to 5]
  // allow week-of-year outer range [0 to 53]
  // this is because callers shouldn't be expected to know the details of validity

  /**
   * The ISO-8601 definition, where a week starts on Monday and the first week has a minimum of 4 days.
   * <p>
   * The ISO-8601 standard defines a calendar system based on weeks. It uses the week-based-year and
   * week-of-week-based-year concepts to split up the passage of days instead of the standard year/month/day.
   * <p>
   * Note that the first week may start in the previous calendar year. Note also that the first few days of a
   * calendar year may be in the week-based-year corresponding to the previous calendar year.
   */
  public static final WeekDefinition ISO = new WeekDefinition(DayOfWeek.MONDAY, 4);

  /**
   * Serialization version.
   */
  private static final long serialVersionUID = -1177360819670808121L;

  /**
   * The cache of rules by locale.
   */
  private static final ConcurrentMap<String, WeekDefinition> CACHE = new ConcurrentHashMap<String, WeekDefinition>(4,
      0.75f, 2);

  /**
   * The first day-of-week.
   */
  private final DayOfWeek firstDayOfWeek;

  /**
   * The minimal number of days in the first week.
   */
  private final int minimalDays;

  /**
   * The DateTimeField used to access the computed DayOfWeek.
   */
  private final DateTimeField dayOfWeek = ComputedDayOfField.ofDayOfWeekField(this);

  /**
   * The DateTimeField used to access the computed WeekOfMonth.
   */
  private final DateTimeField weekOfMonth = ComputedDayOfField.ofWeekOfMonthField(this);

  /**
   * The DateTimeField used to access the computed WeekOfYear.
   */
  private final DateTimeField weekOfYear = ComputedDayOfField.ofWeekOfYearField(this);

  /**
   * Obtains an instance of {@code WeekDefinition} appropriate for a locale.
   * <p>
   * This will look up appropriate values from the provider of localization data.
   * 
   * @param locale the locale to use, not null
   * @return the week-definition, not null
   */
  public static WeekDefinition of(Locale locale) {

    Jdk7Methods.Objects_requireNonNull(locale, "locale");
    DayOfWeek dow = DayOfWeek.MONDAY;
    if ("US".equals(locale.getCountry())) {
      dow = DayOfWeek.SUNDAY;
    }
    int minDays = 4; // ISO-08601 // gcal.getMinimalDaysInFirstWeek();
    return WeekDefinition.of(dow, minDays);
  }

  /**
   * Obtains an instance of {@code WeekDefinition} from the first day-of-week and minimal days.
   * <p>
   * The first day-of-week defines the ISO {@code DayOfWeek} that is day 1 of the week. The minimal number of
   * days in the first week defines how many days must be present in a month or year, starting from the first
   * day-of-week, before the week is counted as the first week. A value of 1 will count the first day of the
   * month or year as part of the first week, whereas a value of 7 will require the whole seven days to be in
   * the new month or year.
   * 
   * @param firstDayOfWeek the first day of the week, not null
   * @param minimalDaysInFirstWeek the minimal number of days in the first week, from 1 to 7
   * @return the week-definition, not null
   * @throws IllegalArgumentException if the minimal days value is invalid
   */
  public static WeekDefinition of(DayOfWeek firstDayOfWeek, int minimalDaysInFirstWeek) {

    if (firstDayOfWeek == DayOfWeek.MONDAY && minimalDaysInFirstWeek == 4) {
      return ISO;
    }
    String key = firstDayOfWeek.toString() + minimalDaysInFirstWeek;
    WeekDefinition rules = CACHE.get(key);
    if (rules == null) {
      rules = new WeekDefinition(firstDayOfWeek, minimalDaysInFirstWeek);
      CACHE.putIfAbsent(key, rules);
      rules = CACHE.get(key);
    }
    return rules;
  }

  // -----------------------------------------------------------------------
  /**
   * Creates an instance of the definition.
   * 
   * @param firstDayOfWeek the first day of the week, not null
   * @param minimalDaysInFirstWeek the minimal number of days in the first week, from 1 to 7
   * @throws IllegalArgumentException if the minimal days value is invalid
   */
  private WeekDefinition(DayOfWeek firstDayOfWeek, int minimalDaysInFirstWeek) {

    Jdk7Methods.Objects_requireNonNull(firstDayOfWeek, "firstDayOfWeek");
    if (minimalDaysInFirstWeek < 1 || minimalDaysInFirstWeek > 7) {
      throw new IllegalArgumentException("Minimal number of days is invalid");
    }
    this.firstDayOfWeek = firstDayOfWeek;
    this.minimalDays = minimalDaysInFirstWeek;
  }

  // -----------------------------------------------------------------------
  /**
   * Gets the first day-of-week.
   * <p>
   * The first day-of-week varies by culture. For example, the US uses Sunday, while France and the ISO-8601
   * standard use Monday. This method returns the first day using the standard {@code DayOfWeek} enum.
   * 
   * @return the first day-of-week, not null
   */
  public DayOfWeek getFirstDayOfWeek() {

    return this.firstDayOfWeek;
  }

  /**
   * Gets the minimal number of days in the first week.
   * <p>
   * The number of days considered to define the first week of a month or year varies by culture. For example,
   * the ISO-8601 requires 4 days (more than half a week) to be present before counting the first week.
   * 
   * @return the minimal number of days in the first week of a month or year, from 1 to 7
   */
  public int getMinimalDaysInFirstWeek() {

    return this.minimalDays;
  }

  // -----------------------------------------------------------------------
  /**
   * Returns a DateTimeField to access the day of week, computed based on this WeekDefinition.
   * <p>
   * The days of week are numbered from 1 to 7. Day number 1 is the {@link #getFirstDayOfWeek() first
   * day-of-week}.
   * 
   * @return the field for day-of-week using this week definition, not null
   */
  public DateTimeField dayOfWeek() {

    return this.dayOfWeek;
  }

  /**
   * Returns a DateTimeField to access the week of month, computed based on this WeekDefinition.
   * <p>
   * This represents concept of the count of weeks within the month where weeks start on a fixed day-of-week,
   * such as Monday. This field is typically used with {@link WeekDefinition#dayOfWeek()}.
   * <p>
   * Week one (1) is the week starting on the {@link WeekDefinition#getFirstDayOfWeek} where there are at
   * least {@link WeekDefinition#getMinimalDaysInFirstWeek()} days in the month. Thus, week one may start up
   * to {@code minDays} days before the start of the month. If the first week starts after the start of the
   * month then the period before is week zero (0).
   * <p>
   * For example:<br />
   * - if the 1st day of the month is a Monday, week one starts on the 1st and there is no week zero<br />
   * - if the 2nd day of the month is a Monday, week one starts on the 2nd and the 1st is in week zero<br />
   * - if the 4th day of the month is a Monday, week one starts on the 4th and the 1st to 3rd is in week zero<br />
   * - if the 5th day of the month is a Monday, week two starts on the 5th and the 1st to 4th is in week one
   * <br />
   * <p>
   * This field can be used with any calendar system.
   */
  public DateTimeField weekOfMonth() {

    return this.weekOfMonth;
  }

  /**
   * Returns a DateTimeField to access the week of year, computed based on this WeekDefinition.
   * <p>
   * This represents concept of the count of weeks within the year where weeks start on a fixed day-of-week,
   * such as Monday. This field is typically used with {@link WeekDefinition#dayOfWeek()}.
   * <p>
   * Week one(1) is the week starting on the {@link WeekDefinition#getFirstDayOfWeek} where there are at least
   * {@link WeekDefinition#getMinimalDaysInFirstWeek()} days in the month. Thus, week one may start up to
   * {@code minDays} days before the start of the year. If the first week starts after the start of the year
   * then the period before is week zero (0).
   * <p>
   * For example:<br />
   * - if the 1st day of the year is a Monday, week one starts on the 1st and there is no week zero<br />
   * - if the 2nd day of the year is a Monday, week one starts on the 2nd and the 1st is in week zero<br />
   * - if the 4th day of the year is a Monday, week one starts on the 4th and the 1st to 3rd is in week zero<br />
   * - if the 5th day of the year is a Monday, week two starts on the 5th and the 1st to 4th is in week one<br
   * * * * * * * * * * * * />
   * <p>
   * This field can be used with any calendar system.
   */
  public DateTimeField weekOfYear() {

    return this.weekOfYear;
  }

  /**
   * Checks if these rules are equal to the specified rules.
   * <p>
   * The comparison is based on the entire state of the rules, which is the first day-of-week and minimal
   * days.
   * 
   * @param object the other rules to compare to, null returns false
   * @return true if this is equal to the specified rules
   */
  @Override
  public boolean equals(Object object) {

    if (this == object) {
      return true;
    }
    if (object instanceof WeekDefinition) {
      return hashCode() == object.hashCode();
    }
    return false;
  }

  /**
   * A hash code for these rules.
   * 
   * @return a suitable hash code
   */
  @Override
  public int hashCode() {

    return this.firstDayOfWeek.ordinal() * 7 + this.minimalDays;
  }

  // -----------------------------------------------------------------------
  /**
   * A string representation of this definition.
   * 
   * @return the string representation, not null
   */
  @Override
  public String toString() {

    return "WeekDefinition[" + this.firstDayOfWeek + ',' + this.minimalDays + ']';
  }

  // -----------------------------------------------------------------------
  /**
   * Field type that computes DayOfWeek, WeekOfMonth, and WeekOfYear based on a WeekDefinition. A separate
   * Field instance is required for each different WeekDefinition; combination of start of week and minimum
   * number of days. Constructors are provided to create fields for DayOfWeek, WeekOfMonth, and WeekOfYear.
   */
  static class ComputedDayOfField implements DateTimeField {

    /**
     * Returns a DateTimeField to access the day of week, computed based on a WeekDefinition.
     * <p>
     * The WeekDefintion of the first day of the week is used with the ISO DAY_OF_WEEK field to compute week
     * boundaries.
     */
    static ComputedDayOfField ofDayOfWeekField(WeekDefinition weekDef) {

      return new ComputedDayOfField("DayOfWeek", weekDef, ChronoUnit.DAYS, ChronoUnit.WEEKS, DAY_OF_WEEK_RANGE);
    }

    /**
     * Returns a DateTimeField to access the week of month, computed based on a WeekDefinition.
     * 
     * @see WeekDefinition#weekOfMonth()
     */
    static ComputedDayOfField ofWeekOfMonthField(WeekDefinition weekDef) {

      return new ComputedDayOfField("WeekOfMonth", weekDef, ChronoUnit.WEEKS, ChronoUnit.MONTHS, WEEK_OF_MONTH_RANGE);
    }

    /**
     * Returns a DateTimeField to access the week of year, computed based on a WeekDefinition.
     * 
     * @see WeekDefinition#weekOfYear()
     */
    static ComputedDayOfField ofWeekOfYearField(WeekDefinition weekDef) {

      return new ComputedDayOfField("WeekOfYear", weekDef, ChronoUnit.WEEKS, ChronoUnit.YEARS, WEEK_OF_YEAR_RANGE);
    }

    private final String name;

    private final WeekDefinition weekDef;

    private final PeriodUnit baseUnit;

    private final PeriodUnit rangeUnit;

    private final DateTimeValueRange range;

    private ComputedDayOfField(String name, WeekDefinition weekDef, PeriodUnit baseUnit, PeriodUnit rangeUnit,
        DateTimeValueRange range) {

      this.name = name;
      this.weekDef = weekDef;
      this.baseUnit = baseUnit;
      this.rangeUnit = rangeUnit;
      this.range = range;
    }

    private static final DateTimeValueRange DAY_OF_WEEK_RANGE = DateTimeValueRange.of(1, 7);

    private static final DateTimeValueRange WEEK_OF_MONTH_RANGE = DateTimeValueRange.of(0, 1, 4, 5);

    private static final DateTimeValueRange WEEK_OF_YEAR_RANGE = DateTimeValueRange.of(0, 1, 52, 53);

    @Override
    public long doGet(DateTimeAccessor dateTime) {

      // Offset the ISO DOW by the start of this week
      int sow = this.weekDef.getFirstDayOfWeek().getValue();
      int isoDow = dateTime.get(ChronoField.DAY_OF_WEEK);
      int dow = Jdk8Methods.floorMod(isoDow - sow, 7) + 1;

      if (this.rangeUnit == ChronoUnit.WEEKS) {
        return dow;
      } else if (this.rangeUnit == ChronoUnit.MONTHS) {
        int dom = dateTime.get(ChronoField.DAY_OF_MONTH);
        int offset = startOfWeekOffset(dom, dow);
        int week = (offset + (dom - 1)) / 7;
        return week;
      } else if (this.rangeUnit == ChronoUnit.YEARS) {
        int doy = dateTime.get(ChronoField.DAY_OF_YEAR);
        int offset = startOfWeekOffset(doy, dow);
        int week = (offset + (doy - 1)) / 7;
        return week;
      } else {
        throw new IllegalStateException("unreachable");
      }
    }

    /**
     * Returns an offset to align week start with a day of month or day of year.
     * 
     * @param day the day; 1 through infinity
     * @param dow the day of the week of that day; 1 through 7
     * @param minDays the minimum number of days required the first week
     * @return an offset in days to align a day with the start of a the first 'full' week
     */
    private int startOfWeekOffset(int day, int dow) {

      // offset of first day corresponding to the day of week in first 7 days (zero origin)
      int weekStart = Jdk8Methods.floorMod(day - dow, 7);
      int offset = -weekStart;
      if (weekStart + 1 > this.weekDef.getMinimalDaysInFirstWeek()) {
        // The previous week has the minimum days in the current month to be a 'week'
        offset = 7 - weekStart;
      }
      offset += 7;
      return offset;
    }

    @Override
    public <R extends DateTime> R doWith(R dateTime, long newValue) {

      // Check the new value and get the old value of the field
      int newVal = this.range.checkValidIntValue(newValue, this);
      int currentVal = dateTime.get(this);
      if (newVal == currentVal) {
        return dateTime;
      }
      // Compute the difference and add that using the base using of the field
      int delta = newVal - currentVal;
      return (R) dateTime.plus(delta, this.baseUnit);
    }

    @Override
    public boolean resolve(DateTimeBuilder builder, long value) {

      int newValue = this.range.checkValidIntValue(value, this);
      // DOW and YEAR are necessary for all fields; Chrono defaults to ISO if not present
      int sow = this.weekDef.getFirstDayOfWeek().getValue();
      int isoDow = builder.get(ChronoField.DAY_OF_WEEK);
      int dow = Jdk8Methods.floorMod(isoDow - sow, 7) + 1;
      int year = builder.get(ChronoField.YEAR);
      Chrono chrono = Chrono.from(builder);

      // The WOM and WOY fields are the critical values
      if (this.rangeUnit == ChronoUnit.MONTHS) {
        // Process WOM value by combining with DOW and MONTH, YEAR
        int month = builder.get(ChronoField.MONTH_OF_YEAR);
        ChronoLocalDate cd = chrono.date(year, month, 1);
        int offset = startOfWeekOffset(1, dow);
        offset += sow - dow; // offset to desired day of week
        offset += 7 * newValue; // offset by week number
        ChronoLocalDate result = cd.plus(offset, ChronoUnit.DAYS);
        builder.addFieldValue(ChronoField.DAY_OF_MONTH, result.get(ChronoField.DAY_OF_MONTH));
        return true;
      } else if (this.rangeUnit == ChronoUnit.YEARS) {
        // Process WOY
        ChronoLocalDate cd = chrono.date(year, 1, 1);
        int offset = startOfWeekOffset(1, dow);
        offset += sow - dow; // offset to desired day of week
        offset += 7 * newValue; // offset by week number
        ChronoLocalDate result = cd.plus(offset, ChronoUnit.DAYS);
        builder.addFieldValue(ChronoField.DAY_OF_MONTH, result.get(ChronoField.DAY_OF_MONTH));
        builder.addFieldValue(ChronoField.MONTH_OF_YEAR, result.get(ChronoField.MONTH_OF_YEAR));
        return true;
      } else {
        // ignore DOW of WEEK field; the value will be processed by WOM or WOY
        return false;
      }
    }

    // -----------------------------------------------------------------------
    @Override
    public String getName() {

      return this.name;
    }

    @Override
    public PeriodUnit getBaseUnit() {

      return this.baseUnit;
    }

    @Override
    public PeriodUnit getRangeUnit() {

      return this.rangeUnit;
    }

    @Override
    public DateTimeValueRange range() {

      return this.range;
    }

    // -------------------------------------------------------------------------
    @Override
    public int compare(DateTimeAccessor dateTime1, DateTimeAccessor dateTime2) {

      return Jdk7Methods.Long_compare(dateTime1.getLong(this), dateTime2.getLong(this));
    }

    // -----------------------------------------------------------------------
    @Override
    public boolean doIsSupported(DateTimeAccessor dateTime) {

      if (dateTime.isSupported(ChronoField.DAY_OF_WEEK)) {
        if (this.rangeUnit == ChronoUnit.WEEKS) {
          return true;
        } else if (this.rangeUnit == ChronoUnit.MONTHS) {
          return dateTime.isSupported(ChronoField.DAY_OF_MONTH);
        } else if (this.rangeUnit == ChronoUnit.YEARS) {
          return dateTime.isSupported(ChronoField.DAY_OF_YEAR);
        }
      }
      return false;
    }

    @Override
    public DateTimeValueRange doRange(DateTimeAccessor dateTime) {

      if (this.rangeUnit == ChronoUnit.WEEKS) {
        return this.range;
      }
      // Offset the ISO DOW by the start of this week
      int sow = this.weekDef.getFirstDayOfWeek().getValue();
      int isoDow = dateTime.get(ChronoField.DAY_OF_WEEK);
      int dow = Jdk8Methods.floorMod(isoDow - sow, 7) + 1;

      if (this.rangeUnit == ChronoUnit.MONTHS) {
        DateTimeValueRange fieldRange = dateTime.range(ChronoField.DAY_OF_MONTH);
        int dom = dateTime.get(ChronoField.DAY_OF_MONTH);
        int offset = startOfWeekOffset(dom, dow);
        int lastDay = (int) fieldRange.getMaximum();
        int week = (offset + (lastDay - 1)) / 7;
        return DateTimeValueRange.of(offset / 7, week);
      } else if (this.rangeUnit == ChronoUnit.YEARS) {
        DateTimeValueRange fieldRange = dateTime.range(ChronoField.DAY_OF_YEAR);
        int doy = dateTime.get(ChronoField.DAY_OF_YEAR);
        int offset = startOfWeekOffset(doy, dow);
        int lastDay = (int) fieldRange.getMaximum();
        int week = (offset + (lastDay - 1)) / 7;
        return DateTimeValueRange.of(offset / 7, week);
      } else {
        throw new IllegalStateException("unreachable");
      }
    }

    // -----------------------------------------------------------------------
    @Override
    public String toString() {

      StringBuilder sb = new StringBuilder(getName());
      sb.append('[');
      sb.append(this.weekDef);
      sb.append(']');
      return sb.toString();
    }
  }
}
