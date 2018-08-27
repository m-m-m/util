/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.base;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Currency;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.sf.mmm.util.component.base.AbstractLoggableComponent;
import net.sf.mmm.util.lang.api.Datatype;
import net.sf.mmm.util.lang.api.DatatypeDetector;

/**
 * This is the abstract base implementation of {@link DatatypeDetector}.
 *
 * @deprecated see {@link DatatypeDetector}.
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 4.0.0
 */
@Deprecated
public abstract class AbstractDatatypeDetector extends AbstractLoggableComponent implements DatatypeDetector {

  private final Set<String> customDatatypeSet;

  private final Set<String> standardDatatypeSet;

  /**
   * The constructor.
   */
  public AbstractDatatypeDetector() {

    super();
    this.customDatatypeSet = new HashSet<>();
    this.standardDatatypeSet = new HashSet<>();
  }

  /**
   * This method registers a custom {@link net.sf.mmm.util.lang.api.Datatype} so it is recognized by
   * {@link #isDatatype(Class)}. <br>
   * <b>NOTE:</b><br>
   * There is no need in registering {@link Enum} datatypes as they are detected as such automatically.
   *
   * @param datatype is the {@link net.sf.mmm.util.lang.api.Datatype} to register.
   */
  protected void registerCustomDatatype(Class<?> datatype) {

    this.customDatatypeSet.add(datatype.getName());
  }

  /**
   * Like {@link #registerCustomDatatype(Class)} but via {@link Class#getName() fully qualified name}. Can be
   * used to prevent compile-time dependencies on datatype.
   *
   * @see #registerCustomDatatype(Class)
   *
   *
   * @param fullQualifiedDatatypeName is the {@link Class#getName() fully qualified name} of the
   *        {@link net.sf.mmm.util.lang.api.Datatype} to register.
   */
  protected void registerCustomDatatype(String fullQualifiedDatatypeName) {

    this.customDatatypeSet.add(fullQualifiedDatatypeName);
  }

  /**
   * This method registers a Java standard {@link net.sf.mmm.util.lang.api.Datatype} so it is recognized by
   * {@link #isJavaStandardDatatype(Class)}.
   *
   * @param datatype is the {@link net.sf.mmm.util.lang.api.Datatype} to register.
   */
  protected void registerStandardDatatype(Class<?> datatype) {

    this.standardDatatypeSet.add(datatype.getName());
  }

  /**
   * Like {@link #registerStandardDatatype(Class)} but via {@link Class#getName() fully qualified name}. Can
   * be used to prevent compile-time dependencies on datatype.
   *
   * @see #registerStandardDatatype(Class)
   *
   *
   * @param fullQualifiedDatatypeName is the {@link Class#getName() fully qualified name} of the
   *        {@link net.sf.mmm.util.lang.api.Datatype} to register.
   */
  protected void registerStandardDatatype(String fullQualifiedDatatypeName) {

    this.standardDatatypeSet.add(fullQualifiedDatatypeName);
  }

  /**
   * Registers the default datatypes.
   */
  protected void registerDefaultDatatypes() {

    registerStandardDatatype(String.class);
    registerStandardDatatype(Boolean.class);
    registerStandardDatatype(Character.class);
    registerStandardDatatype(Currency.class);
    registerCustomDatatype(Datatype.class); // internal trick...
    registerNumberDatatypes();
    registerJavaTimeDatatypes();
    registerJavaUtilDateCalendarDatatypes();
  }

  /**
   * Called from {@link #registerDefaultDatatypes()} to add {@link Number} based datatypes. Can also be called
   * standalone or overridden for customization.
   */
  protected void registerNumberDatatypes() {

    registerStandardDatatype(Integer.class);
    registerStandardDatatype(Long.class);
    registerStandardDatatype(Double.class);
    registerStandardDatatype(Float.class);
    registerStandardDatatype(Byte.class);
    registerStandardDatatype(Short.class);
    registerStandardDatatype(BigInteger.class);
    registerStandardDatatype(BigDecimal.class);
    registerStandardDatatype(Number.class);
  }

  /**
   * Called from {@link #registerDefaultDatatypes()} to add legacy {@link Date} and {@link java.util.Calendar}
   * datatypes. Can also be called standalone or overridden for customization.
   */
  protected void registerJavaUtilDateCalendarDatatypes() {

    registerStandardDatatype(Date.class);
    registerStandardDatatype("java.util.Calendar");
  }

  /**
   * Called from {@link #registerDefaultDatatypes()} to add JSR310 datatypes. Can also be called standalone or
   * overridden for customization.
   */
  protected void registerJavaTimeDatatypes() {

    // prevent compile-time dependencies and GWT availability problems
    registerStandardDatatype("java.time.LocalTime");
    registerStandardDatatype("java.time.LocalDate");
    registerStandardDatatype("java.time.LocalDateTime");
    registerStandardDatatype("java.time.MonthDay");
    registerStandardDatatype("java.time.Year");
    registerStandardDatatype("java.time.YearMonth");
    registerStandardDatatype("java.time.Instant");
    registerStandardDatatype("java.time.Duration");
    registerStandardDatatype("java.time.Period");
    registerStandardDatatype("java.time.OffsetTime");
    registerStandardDatatype("java.time.OffsetDate");
    registerStandardDatatype("java.time.OffsetDateTime");
    registerStandardDatatype("java.time.ZoneId");
    registerStandardDatatype("java.time.ZonedDateTime");
    registerStandardDatatype("java.time.ZoneOffset");
    registerStandardDatatype("java.time.ZoneRegion");
  }

  @Override
  public boolean isDatatype(Class<?> type) {

    if (type.isEnum()) {
      return true;
    }
    if (isJavaStandardDatatype(type)) {
      return true;
    }
    return this.customDatatypeSet.contains(type.getName());
  }

  @Override
  public boolean isJavaStandardDatatype(Class<?> type) {

    if (type.isPrimitive()) {
      return true;
    }
    return this.standardDatatypeSet.contains(type.getName());
  }

  /**
   * Adds a list of additional datatypes to register. E.g. for easy spring configuration and custom extension.
   *
   * @param datatypeList is the {@link List} of {@link Class#getName() fully qualified names} of additional
   *        {@link Datatype}s to {@link #registerCustomDatatype(String) register}.
   */
  public void setExtraDatatypes(List<String> datatypeList) {

    getInitializationState().requireNotInitilized();
    for (String fqn : datatypeList) {
      registerCustomDatatype(fqn);
    }
  }

}
