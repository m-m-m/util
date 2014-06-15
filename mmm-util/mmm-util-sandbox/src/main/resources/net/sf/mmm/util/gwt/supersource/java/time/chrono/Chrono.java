/*
 * Copyright (c) 2012, Stephen Colebourne & Michael Nascimento Santos
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
package java.time.chrono;

import java.time.Clock;
import java.time.DateTimeException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.calendrical.ChronoField;
import java.time.calendrical.DateTime;
import java.time.calendrical.DateTimeAccessor;
import java.time.calendrical.DateTimeAccessor.Query;
import java.time.calendrical.DateTimeValueRange;
import java.time.format.TextStyle;
import java.time.jdk8.Jdk7Methods;
import java.time.zone.ZoneRules;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A calendar system, defining a set of human-scale date fields.
 * <p>
 * The main date and time API is built on the ISO calendar system. This class operates behind the scenes to
 * represent the general concept of a calendar system. For example, the Gregorian, Japanese, Minguo, Thai
 * Buddhist and others.
 * <p>
 * Most other calendar systems also operate on the shared concepts of year, month and day, linked to the
 * cycles of the Earth around the Sun, and the Moon around the Earth. These shared concepts are defined by
 * {@link ChronoField} and are availalbe for use by any {@code Chrono} implementation:
 * 
 * <pre>
 *   LocalDate isoDate = ...
 *   ChronoLocalDate&lt;ThaiBuddhistChrono&gt; minguoDate = ...
 *   int isoYear = isoDate.get(ChronoField.YEAR);
 *   int thaiYear = thaiDate.get(ChronoField.YEAR);
 * </pre>
 * As shown, although the date objects are in different calendar systems, represented by different
 * {@code Chrono} instances, both can be queried using the same constant on {@code ChronoField}. For a full
 * discussion of the implications of this, see {@link ChronoLocalDate}. In general, the advice is to use the
 * known ISO-based {@code LocalDate}, rather than {@code ChronoLocalDate}.
 * <p>
 * While a {@code Chrono} object typically uses {@code ChronoField} and is based on an era, year-of-era,
 * month-of-year, day-of-month model of a date, this is not required. A {@code Chrono} instance may represent
 * a totally different kind of calendar system, such as the Mayan.
 * <p>
 * In practical terms, the {@code Chrono} instance also acts as a factory. The {@link #of(String)} method
 * allows an instance to be looked up by identifier, while the {@link #ofLocale(Locale)} method allows lookup
 * by locale.
 * <p>
 * The {@code Chrono} instance provides a set of methods to create {@code ChronoLocalDate} instances. The date
 * classes are used to manipulate specific dates.
 * <p>
 * <ul>
 * <li> {@link #dateNow() dateNow()}
 * <li> {@link #dateNow(Clock) dateNow(clock)}
 * <li> {@link #dateNow(ZoneId) dateNow(zone)}
 * <li> {@link #date(int, int, int) date(yearProleptic, month, day)}
 * <li> {@link #date(java.time.chrono.Era, int, int, int) date(era, yearOfEra, month, day)}
 * <li> {@link #dateYearDay(int, int) dateYearDay(yearProleptic, dayOfYear)}
 * <li> {@link #dateYearDay(Era, int, int) dateYearDay(era, yearOfEra, dayOfYear)}
 * <li> {@link #date(DateTimeAccessor) date(DateTimeAccessor)}
 * </ul>
 * <p>
 * 
 * <h4 id="addcalendars">Adding New Calendars</h4>
 * The set of available chronologies can be extended by applications. Adding a new calendar system requires
 * the writing of an implementation of {@code Chrono}, {@code ChronoLocalDate} and {@code Era}. The majority
 * of the logic specific to the calendar system will be in {@code ChronoLocalDate}. The {@code Chrono}
 * subclass acts as a factory.
 * <p>
 * To permit the discovery of additional chronologies, the {@link java.util.ServiceLoader ServiceLoader} is
 * used. A file must be added to the {@code META-INF/services} directory with the name
 * 'javax.time.chrono.Chrono' listing the implementation classes. See the service loader for more details on
 * service loading.
 * <p>
 * Each chronology must define a chronology ID that is unique within the system. If the chronology represents
 * a calendar system defined by the <em>Unicode Locale Data Markup Language (LDML)</em> specification then
 * that calendar type should also be specified.
 * 
 * <h4>Implementation notes</h4>
 * This interface must be implemented with care to ensure other classes operate correctly. All implementations
 * that can be instantiated must be final, immutable and thread-safe. Subclasses should be Serializable
 * wherever possible.
 * 
 * @param <C> the type of the implementing subclass
 */
public abstract class Chrono<C extends Chrono<C>> implements Comparable<Chrono<?>> {

  /**
   * Map of available calendars by ID.
   */
  private static final ConcurrentHashMap<String, Chrono<?>> CHRONOS_BY_ID;

  /**
   * Map of available calendars by calendar type.
   */
  private static final ConcurrentHashMap<String, Chrono<?>> CHRONOS_BY_TYPE;
  static {
    // TODO: defer initialization?
    ConcurrentHashMap<String, Chrono<?>> ids = new ConcurrentHashMap<String, Chrono<?>>();
    ConcurrentHashMap<String, Chrono<?>> types = new ConcurrentHashMap<String, Chrono<?>>();
    CHRONOS_BY_ID = ids;
    CHRONOS_BY_TYPE = types;
  }

  // -----------------------------------------------------------------------
  /**
   * Obtains an instance of {@code Chrono} from a date-time object.
   * <p>
   * A {@code DateTimeAccessor} represents some form of date and time information. This factory converts the
   * arbitrary date-time object to an instance of {@code Chrono}. If the specified date-time object does not
   * have a chronology, {@link ISOChrono} is returned.
   * 
   * @param dateTime the date-time to convert, not null
   * @return the chronology, not null
   * @throws DateTimeException if unable to convert to an {@code Chrono}
   */
  public static Chrono<?> from(DateTimeAccessor dateTime) {

    Jdk7Methods.Objects_requireNonNull(dateTime, "dateTime");
    Chrono<?> obj = dateTime.query(Query.CHRONO);
    return (obj != null ? obj : ISOChrono.INSTANCE);
  }

  // -----------------------------------------------------------------------
  /**
   * Obtains an instance of {@code Chrono} from a locale.
   * <p>
   * The locale can be used to identify a calendar. This uses {@link Locale#getUnicodeLocaleType(String)} to
   * obtain the "ca" key to identify the calendar system.
   * <p>
   * If the locale does not contain calendar system information, the standard ISO calendar system is used.
   * 
   * @param locale the locale to use to obtain the calendar system, not null
   * @return the calendar system associated with the locale, not null
   * @throws DateTimeException if the locale-specified calendar cannot be found
   */
  public static Chrono<?> ofLocale(Locale locale) {

    Jdk7Methods.Objects_requireNonNull(locale, "locale");
    return ISOChrono.INSTANCE;
  }

  // -----------------------------------------------------------------------
  /**
   * Obtains an instance of {@code Chrono} from a chronology ID or calendar system type.
   * <p>
   * This returns a chronology based on either the ID or the type. The {@link #getId() chronology ID} uniquely
   * identifies the chronology. The {@link #getCalendarType() calendar system type} is defined by the LDML
   * specification.
   * <p>
   * Since some calendars can be customized, the ID or type typically refers to the default customization. For
   * example, the Gregorian calendar can have multiple cutover dates from the Julian, but the lookup only
   * provides the default cutover date.
   * 
   * @param id the chronology ID or calendar system type, not null
   * @return the chronology with the identifier requested, not null
   * @throws DateTimeException if the chronology cannot be found
   */
  public static Chrono<?> of(String id) {

    Chrono<?> chrono = CHRONOS_BY_ID.get(id);
    if (chrono != null) {
      return chrono;
    }
    chrono = CHRONOS_BY_TYPE.get(id);
    if (chrono != null) {
      return chrono;
    }
    throw new DateTimeException("Unknown chronology: " + id);
  }

  /**
   * Returns the available chronologies.
   * <p>
   * Each returned {@code Chrono} is available for use in the system.
   * 
   * @return the independent, modifiable set of the available chronology IDs, not null
   */
  public static Set<Chrono<?>> getAvailableChronologies() {

    return new HashSet<Chrono<?>>(CHRONOS_BY_ID.values());
  }

  // -----------------------------------------------------------------------
  /**
   * Obtains a local date-time from the a date and time.
   * <p>
   * This combines a {@link ChronoLocalDate}, which provides the {@code Chrono}, with a {@link LocalTime} to
   * produce a {@link ChronoLocalDateTime}.
   * <p>
   * This method is intended for chronology implementations. It uses a standard implementation that is shared
   * for all chronologies.
   * 
   * @param <R> the chronology of the date
   * @param date the date, not null
   * @param time the time, not null
   * @return the local date-time combining the input date and time, not null
   */
  public static <R extends Chrono<R>> ChronoLocalDateTime<R> dateTime(ChronoLocalDate<R> date, LocalTime time) {

    return ChronoDateTimeImpl.of(date, time);
  }

  // -----------------------------------------------------------------------
  /**
   * Creates an instance.
   */
  protected Chrono() {

    // register the subclass
    CHRONOS_BY_ID.putIfAbsent(this.getId(), this);
    String type = this.getCalendarType();
    if (type != null) {
      CHRONOS_BY_TYPE.putIfAbsent(type, this);
    }
  }

  /**
   * Casts the {@code DateTime} to {@code ChronoLocalDate} with the same chronology.
   * 
   * @param dateTime a date-time to cast, not null
   * @return the date-time checked and cast to {@code ChronoLocalDate}, not null
   * @throws ClassCastException if the date-time cannot be cast to ChronoLocalDate or the chronology is not
   *         equal this Chrono
   */
  public/* package-scoped */ChronoLocalDate<C> ensureChronoLocalDate(DateTime dateTime) { // TODO: non-public

    @SuppressWarnings("unchecked")
    ChronoLocalDate<C> other = (ChronoLocalDate<C>) dateTime;
    if (this.equals(other.getChrono()) == false) {
      throw new ClassCastException("Chrono mismatch, expected: " + getId() + ", actual: " + other.getChrono().getId());
    }
    return other;
  }

  /**
   * Casts the {@code DateTime} to {@code ChronoLocalDateTime} with the same chronology.
   * 
   * @param dateTime a date-time to cast, not null
   * @return the date-time checked and cast to {@code ChronoLocalDateTime}, not null
   * @throws ClassCastException if the date-time cannot be cast to ChronoDateTimeImpl or the chronology is not
   *         equal this Chrono
   */
  public/* package-scoped */ChronoDateTimeImpl<C> ensureChronoLocalDateTime(DateTime dateTime) { // TODO:

    // non-public

    @SuppressWarnings("unchecked")
    ChronoDateTimeImpl<C> other = (ChronoDateTimeImpl<C>) dateTime;
    if (this.equals(other.getDate().getChrono()) == false) {
      throw new ClassCastException("Chrono mismatch, required: " + getId() + ", supplied: "
          + other.getDate().getChrono().getId());
    }
    return other;
  }

  /**
   * Casts the {@code DateTime} to {@code ChronoZonedDateTimeImpl} with the same chronology.
   * 
   * @param dateTime a date-time to cast, not null
   * @return the date-time checked and cast to {@code ChronoZonedDateTimeImpl}, not null
   * @throws ClassCastException if the date-time cannot be cast to ChronoZonedDateTimeImpl or the chronology
   *         is not equal this Chrono
   */
  public/* package-scoped */ChronoZonedDateTimeImpl<C> ensureChronoZonedDateTime(DateTime dateTime) { // TODO:

    // non-public

    @SuppressWarnings("unchecked")
    ChronoZonedDateTimeImpl<C> other = (ChronoZonedDateTimeImpl<C>) dateTime;
    if (this.equals(other.getDate().getChrono()) == false) {
      throw new ClassCastException("Chrono mismatch, required: " + getId() + ", supplied: "
          + other.getDate().getChrono().getId());
    }
    return other;
  }

  // -----------------------------------------------------------------------
  /**
   * Gets the ID of the chronology.
   * <p>
   * The ID uniquely identifies the {@code Chrono}. It can be used to lookup the {@code Chrono} using
   * {@link #of(String)}.
   * 
   * @return the chronology ID, not null
   * @see #getCalendarType()
   */
  public abstract String getId();

  /**
   * Gets the calendar type of the underlying calendar system.
   * <p>
   * The calendar type is an identifier defined by the <em>Unicode Locale Data Markup Language (LDML)</em>
   * specification. It can be used to lookup the {@code Chrono} using {@link #of(String)}. It can also be used
   * as part of a locale, accessible via {@link Locale#getUnicodeLocaleType(String)} with the key 'ca'.
   * 
   * @return the calendar system type, null if the calendar is not defined by LDML
   * @see #getId()
   */
  public abstract String getCalendarType();

  // -----------------------------------------------------------------------
  /**
   * Obtains a local date in this chronology from the era, year-of-era, month-of-year and day-of-month fields.
   * 
   * @param era the era of the correct type for the chronology, not null
   * @param yearOfEra the chronology year-of-era
   * @param month the chronology month-of-year
   * @param dayOfMonth the chronology day-of-month
   * @return the local date in this chronology, not null
   * @throws DateTimeException if unable to create the date
   */
  public ChronoLocalDate<C> date(Era<C> era, int yearOfEra, int month, int dayOfMonth) {

    return date(prolepticYear(era, yearOfEra), month, dayOfMonth);
  }

  /**
   * Obtains a local date in this chronology from the proleptic-year, month-of-year and day-of-month fields.
   * 
   * @param prolepticYear the chronology proleptic-year
   * @param month the chronology month-of-year
   * @param dayOfMonth the chronology day-of-month
   * @return the local date in this chronology, not null
   * @throws DateTimeException if unable to create the date
   */
  public abstract ChronoLocalDate<C> date(int prolepticYear, int month, int dayOfMonth);

  /**
   * Obtains a local date in this chronology from the era, year-of-era and day-of-year fields.
   * 
   * @param era the era of the correct type for the chronology, not null
   * @param yearOfEra the chronology year-of-era
   * @param dayOfYear the chronology day-of-year
   * @return the local date in this chronology, not null
   * @throws DateTimeException if unable to create the date
   */
  public ChronoLocalDate<C> dateYearDay(Era<C> era, int yearOfEra, int dayOfYear) {

    return dateYearDay(prolepticYear(era, yearOfEra), dayOfYear);
  }

  /**
   * Obtains a local date in this chronology from the proleptic-year and day-of-year fields.
   * 
   * @param prolepticYear the chronology proleptic-year
   * @param dayOfYear the chronology day-of-year
   * @return the local date in this chronology, not null
   * @throws DateTimeException if unable to create the date
   */
  public abstract ChronoLocalDate<C> dateYearDay(int prolepticYear, int dayOfYear);

  /**
   * Obtains a local date in this chronology from another date-time object.
   * <p>
   * This creates a date in this chronology based on the specified {@code DateTimeAccessor}.
   * <p>
   * The standard mechanism for conversion between date types is the {@link ChronoField#EPOCH_DAY local
   * epoch-day} field.
   * 
   * @param dateTime the date-time object to convert, not null
   * @return the local date in this chronology, not null
   * @throws DateTimeException if unable to create the date
   */
  public abstract ChronoLocalDate<C> date(DateTimeAccessor dateTime);

  // -----------------------------------------------------------------------
  /**
   * Obtains the current local date in this chronology from the system clock in the default time-zone.
   * <p>
   * This will query the {@link Clock#systemDefaultZone() system clock} in the default time-zone to obtain the
   * current date.
   * <p>
   * Using this method will prevent the ability to use an alternate clock for testing because the clock is
   * hard-coded.
   * <p>
   * This implementation uses {@link #dateNow(Clock)}.
   * 
   * @return the current local date using the system clock and default time-zone, not null
   * @throws DateTimeException if unable to create the date
   */
  public ChronoLocalDate<C> dateNow() {

    return dateNow(Clock.systemDefaultZone());
  }

  /**
   * Obtains the current local date in this chronology from the system clock in the specified time-zone.
   * <p>
   * This will query the {@link Clock#system(ZoneId) system clock} to obtain the current date. Specifying the
   * time-zone avoids dependence on the default time-zone.
   * <p>
   * Using this method will prevent the ability to use an alternate clock for testing because the clock is
   * hard-coded.
   * 
   * @return the current local date using the system clock, not null
   * @throws DateTimeException if unable to create the date
   */
  public ChronoLocalDate<C> dateNow(ZoneId zone) {

    return dateNow(Clock.system(zone));
  }

  /**
   * Obtains the current local date in this chronology from the specified clock.
   * <p>
   * This will query the specified clock to obtain the current date - today. Using this method allows the use
   * of an alternate clock for testing. The alternate clock may be introduced using {@link Clock dependency
   * injection}.
   * 
   * @param clock the clock to use, not null
   * @return the current local date, not null
   * @throws DateTimeException if unable to create the date
   */
  public ChronoLocalDate<C> dateNow(Clock clock) {

    Jdk7Methods.Objects_requireNonNull(clock, "clock");
    return date(LocalDate.now(clock));
  }

  // -----------------------------------------------------------------------
  /**
   * Creates a local date-time in this chronology from another date-time object.
   * <p>
   * This creates a date-time in this chronology based on the specified {@code DateTimeAccessor}.
   * <p>
   * The date of the date-time should be equivalent to that obtained by calling
   * {@link #date(DateTimeAccessor)}. The standard mechanism for conversion between time types is the
   * {@link ChronoField#NANO_OF_DAY nano-of-day} field.
   * 
   * @param dateTime the date-time object to convert, not null
   * @return the local date-time in this chronology, not null
   * @throws DateTimeException if unable to create the date-time
   */
  public ChronoLocalDateTime<C> localDateTime(DateTimeAccessor dateTime) {

    return date(dateTime).atTime(LocalTime.from(dateTime));
  }

  /**
   * Creates a local date-time in this chronology from an instant and zone.
   * 
   * @param instant the instant, not null
   * @param zoneId the zone ID, not null
   * @return the local date-time, not null
   */
  ChronoDateTimeImpl<C> localInstant(Instant instant, ZoneId zoneId) {

    ZoneRules rules = zoneId.getRules();
    ZoneOffset offset = rules.getOffset(instant);
    LocalDateTime ldt = LocalDateTime.ofEpochSecond(instant.getEpochSecond(), instant.getNano(), offset);
    return ChronoDateTimeImpl.of(dateNow(), LocalTime.MIDNIGHT).with(ldt); // not very efficient...
  }

  /**
   * Creates a zoned date-time in this chronology from another date-time object.
   * <p>
   * This creates a date-time in this chronology based on the specified {@code DateTimeAccessor}.
   * <p>
   * This should obtain a {@code ZoneId} using {@link ZoneId#from(DateTimeAccessor)}. The date-time should be
   * obtained by obtaining an {@code Instant}. If that fails, the local date-time should be used.
   * 
   * @param dateTime the date-time object to convert, not null
   * @return the zoned date-time in this chronology, not null
   * @throws DateTimeException if unable to create the date-time
   */
  public ChronoZonedDateTime<C> zonedDateTime(DateTimeAccessor dateTime) {

    try {
      ZoneId zoneId = ZoneId.from(dateTime);
      ChronoDateTimeImpl<C> cldt;
      try {
        Instant instant = Instant.from(dateTime);
        cldt = localInstant(instant, zoneId);
      } catch (DateTimeException ex1) {
        cldt = ensureChronoLocalDateTime(localDateTime(dateTime));
      }
      return ChronoZonedDateTimeImpl.ofBest(cldt, zoneId, null);
    } catch (DateTimeException ex) {
      throw new DateTimeException("Unable to convert DateTimeAccessor to ZonedDateTime: " + dateTime.getClass(), ex);
    }
  }

  // -----------------------------------------------------------------------
  /**
   * Checks if the specified year is a leap year.
   * <p>
   * A leap-year is a year of a longer length than normal. The exact meaning is determined by the chronology
   * according to the following constraints.
   * <p>
   * <ul>
   * <li>a leap-year must imply a year-length longer than a non leap-year.
   * <li>a chronology that does not support the concept of a year must return false.
   * </ul>
   * <p>
   * 
   * @param prolepticYear the proleptic-year to check, not validated for range
   * @return true if the year is a leap year
   */
  public abstract boolean isLeapYear(long prolepticYear);

  /**
   * Calculates the proleptic-year given the era and year-of-era.
   * <p>
   * This combines the era and year-of-era into the single proleptic-year field.
   * 
   * @param era the era of the correct type for the chronology, not null
   * @param yearOfEra the chronology year-of-era
   * @return the proleptic-year
   * @throws DateTimeException if unable to convert
   */
  public abstract int prolepticYear(Era<C> era, int yearOfEra);

  /**
   * Creates the chronology era object from the numeric value.
   * <p>
   * The era is, conceptually, the largest division of the time-line. Most calendar systems have a single
   * epoch dividing the time-line into two eras. However, some have multiple eras, such as one for the reign
   * of each leader. The exact meaning is determined by the chronology according to the following constraints.
   * <p>
   * The era in use at 1970-01-01 must have the value 1. Later eras must have sequentially higher values.
   * Earlier eras must have sequentially lower values. Each chronology must refer to an enum or similar
   * singleton to provide the era values.
   * <p>
   * This method returns the singleton era of the correct type for the specified era value.
   * 
   * @param eraValue the era value
   * @return the calendar system era, not null
   * @throws DateTimeException if unable to create the era
   */
  public abstract Era<C> eraOf(int eraValue);

  /**
   * Gets the list of eras for the chronology.
   * <p>
   * Most calendar systems have an era, within which the year has meaning. If the calendar system does not
   * support the concept of eras, an empty list must be returned.
   * 
   * @return the list of eras for the chronology, may be immutable, not null
   */
  public abstract List<Era<C>> eras();

  // -----------------------------------------------------------------------
  /**
   * Gets the range of valid values for the specified field.
   * <p>
   * All fields can be expressed as a {@code long} integer. This method returns an object that describes the
   * valid range for that value.
   * <p>
   * Note that the result only describes the minimum and maximum valid values and it is important not to read
   * too much into them. For example, there could be values within the range that are invalid for the field.
   * <p>
   * This method will return a result whether or not the chronology supports the field.
   * 
   * @param field the field to get the range for, not null
   * @return the range of valid values for the field, not null
   * @throws DateTimeException if the range for the field cannot be obtained
   */
  public abstract DateTimeValueRange range(ChronoField field);

  // -----------------------------------------------------------------------
  /**
   * Gets the textual representation of this chronology.
   * <p>
   * This returns the textual name used to identify the chronology. The parameters control the length of the
   * returned text and the locale.
   * 
   * @param style the length of the text required, not null
   * @param locale the locale to use, not null
   * @return the text value of the chronology, not null
   */
  public String getText(TextStyle style, Locale locale) {

    // TODO: support at least the style?
    return toString();
    // return new DateTimeFormatterBuilder().appendChronoText(style).toFormatter(locale)
    // .print(new DefaultInterfaceDateTimeAccessor() {
    //
    // @Override
    // public boolean isSupported(DateTimeField field) {
    //
    // return false;
    // }
    //
    // @Override
    // public long getLong(DateTimeField field) {
    //
    // throw new DateTimeException("Unsupported field: " + field);
    // }
    //
    // @SuppressWarnings("unchecked")
    // @Override
    // public <R> R query(Query<R> query) {
    //
    // if (query == Query.CHRONO) {
    // return (R) Chrono.this;
    // }
    // return super.query(query);
    // }
    // });
  }

  // -----------------------------------------------------------------------
  /**
   * Compares this chronology to another chronology.
   * <p>
   * The comparison order first by the chronology ID string, then by any additional information specific to
   * the subclass. It is "consistent with equals", as defined by {@link Comparable}.
   * <p>
   * The default implementation compares the chronology ID. Subclasses must compare any additional state that
   * they store.
   * 
   * @param other the other chronology to compare to, not null
   * @return the comparator value, negative if less, positive if greater
   */
  @Override
  public int compareTo(Chrono<?> other) {

    return getId().compareTo(other.getId());
  }

  /**
   * Checks if this chronology is equal to another chronology.
   * <p>
   * The comparison is based on the entire state of the object.
   * <p>
   * The default implementation checks the type and calls {@link #compareTo(Chrono)}.
   * 
   * @param obj the object to check, null returns false
   * @return true if this is equal to the other chronology
   */
  @Override
  public boolean equals(Object obj) {

    if (this == obj) {
      return true;
    }
    if (obj instanceof Chrono) {
      return compareTo((Chrono<?>) obj) == 0;
    }
    return false;
  }

  /**
   * A hash code for this chronology.
   * <p>
   * The default implementation is based on the ID and class. Subclasses should add any additional state that
   * they store.
   * 
   * @return a suitable hash code
   */
  @Override
  public int hashCode() {

    return getClass().hashCode() ^ getId().hashCode();
  }

  // -----------------------------------------------------------------------
  /**
   * Outputs this chronology as a {@code String}, using the ID.
   * 
   * @return a string representation of this chronology, not null
   */
  @Override
  public String toString() {

    return getId();
  }

}
