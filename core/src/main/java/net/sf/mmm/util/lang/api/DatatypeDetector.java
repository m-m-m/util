/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.api;

import net.sf.mmm.util.component.api.ComponentSpecification;

/**
 * This is the interface to {@link #isDatatype(Class) detect} if some type is a
 * {@link net.sf.mmm.util.lang.api.Datatype}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 4.0.0
 */
@ComponentSpecification
public interface DatatypeDetector {

  /**
   * This method determines if the given {@link Class} reflects an (immutable) {@link net.sf.mmm.util.lang.api.Datatype}
   * . It will return <code>true</code> if the given <code>value</code> is a {@link String}, {@link Boolean},
   * {@link Character}, anything derived from {@link Number}, an {@link Enum}, an instance of
   * {@link net.sf.mmm.util.lang.api.Datatype}, a {@link java.util.Date} (even though not immutable) or anything
   * similar.
   *
   * @param type is the {@link Class} to check.
   * @return <code>true</code> if the given {@link Class} represents a {@link net.sf.mmm.util.lang.api.Datatype},
   *         <code>false</code> otherwise.
   */
  boolean isDatatype(Class<?> type);

  /**
   * This method determines if the given {@link Class} reflects a Java standard
   * {@link net.sf.mmm.util.lang.api.Datatype} provided by the JDK. This is any {@link Class#isPrimitive() primitive
   * type} or any of {@link Integer}, {@link Long}, {@link Double}, {@link Float}, {@link Byte}, {@link Short},
   * {@link Boolean}, {@link String}, {@link Character}, {@link java.math.BigInteger}, {@link java.math.BigDecimal},
   * {@link java.util.Date}, {@link java.util.Calendar}, {@link java.time.LocalDate}, {@link java.time.LocalTime},
   * {@link java.time.LocalDateTime}, {@link java.time.Instant}, {@link java.time.OffsetDateTime},
   * {@link java.time.OffsetTime}, {@link java.time.Duration}, {@link java.time.ZonedDateTime},
   * {@link java.time.YearMonth}, {@link java.time.MonthDay}, {@link java.time.Year}, {@link java.time.Period},
   * {@link java.time.ZoneOffset}.
   *
   * @param type is the {@link Class} to check.
   * @return <code>true</code> if the given {@link Class} represents a Java standard
   *         {@link net.sf.mmm.util.lang.api.Datatype}, <code>false</code> otherwise.
   * @since 6.0.0
   */
  boolean isJavaStandardDatatype(Class<?> type);

}
