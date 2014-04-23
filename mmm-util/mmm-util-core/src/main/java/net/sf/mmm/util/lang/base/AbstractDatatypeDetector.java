/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.base;

import java.math.BigDecimal;
import java.math.BigInteger;
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
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 4.0.0
 */
public abstract class AbstractDatatypeDetector extends AbstractLoggableComponent implements DatatypeDetector {

  /** @see #isDatatype(Class) */
  private final Set<String> datatypeSet;

  /**
   * The constructor.
   */
  public AbstractDatatypeDetector() {

    super();
    this.datatypeSet = new HashSet<>();
  }

  /**
   * This method registers a {@link net.sf.mmm.util.lang.api.Datatype} so it is recognized by
   * {@link #isDatatype(Class)}.<br/>
   * <b>NOTE:</b><br/>
   * There is no need in registering {@link Enum} datatypes as they are detected as such automatically.
   *
   * @param datatype is the {@link net.sf.mmm.util.lang.api.Datatype} to register.
   */
  protected void registerDatatype(Class<?> datatype) {

    this.datatypeSet.add(datatype.getName());
  }

  /**
   * Like {@link #registerDatatype(Class)} but via {@link Class#getName() fully qualified name}. Can be used
   * to prevent compile-time dependencies on datatype.
   *
   * @see #registerDatatype(Class)
   *
   *
   * @param fullQualifiedDatatypeName is the {@link Class#getName() fully qualified name} of the
   *        {@link net.sf.mmm.util.lang.api.Datatype} to register.
   */
  protected void registerDatatype(String fullQualifiedDatatypeName) {

    this.datatypeSet.add(fullQualifiedDatatypeName);
  }

  /**
   * Registers the default datatypes.
   */
  protected void registerDefaultDatatypes() {

    registerDatatype(String.class);
    registerDatatype(Boolean.class);
    registerDatatype(Character.class);
    registerDatatype(Datatype.class); // internal trick...
    registerNumberDatatypes();
    registerJavaTimeDatatypes();
    registerJavaUtilDateCalendarDatatypes();
  }

  /**
   * Called from {@link #registerDefaultDatatypes()} to add {@link Number} based datatypes. Can also be called
   * standalone or overridden for customization.
   */
  protected void registerNumberDatatypes() {

    registerDatatype(Integer.class);
    registerDatatype(Long.class);
    registerDatatype(Double.class);
    registerDatatype(Float.class);
    registerDatatype(Byte.class);
    registerDatatype(Short.class);
    registerDatatype(BigInteger.class);
    registerDatatype(BigDecimal.class);
    registerDatatype(Number.class);
  }

  /**
   * Called from {@link #registerDefaultDatatypes()} to add legacy {@link Date} and {@link java.util.Calendar}
   * datatypes. Can also be called standalone or overridden for customization.
   */
  protected void registerJavaUtilDateCalendarDatatypes() {

    registerDatatype(Date.class);
    registerDatatype("java.util.Calendar");
  }

  /**
   * Called from {@link #registerDefaultDatatypes()} to add JSR310 datatypes. Can also be called standalone or
   * overridden for customization.
   */
  protected void registerJavaTimeDatatypes() {

    // prevent compile-time dependencies and GWT availability problems
    registerDatatype("java.time.LocalTime");
    registerDatatype("java.time.LocalTime");
    registerDatatype("java.time.LocalDate");
    registerDatatype("java.time.LocalDateTime");
    registerDatatype("java.time.MonthDay");
    registerDatatype("java.time.Year");
    registerDatatype("java.time.YearMonth");
    registerDatatype("java.time.Instant");
    registerDatatype("java.time.Duration");
    registerDatatype("java.time.Period");
    registerDatatype("java.time.OffsetTime");
    registerDatatype("java.time.OffsetDate");
    registerDatatype("java.time.OffsetDateTime");
    registerDatatype("java.time.ZoneId");
    registerDatatype("java.time.ZonedDateTime");
    registerDatatype("java.time.ZoneOffset");
    registerDatatype("java.time.ZoneRegion");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isDatatype(Class<?> type) {

    if (type.isEnum()) {
      return true;
    }
    return this.datatypeSet.contains(type.getName());
  }

  /**
   * Adds a list of additional datatypes to register. E.g. for easy spring configuration and custom extension.
   *
   * @param datatypeList is the {@link List} of {@link Class#getName() fully qualified names} of additional
   *        {@link Datatype}s to {@link #registerDatatype(String) register}.
   */
  public void setExtraDatatypes(List<String> datatypeList) {

    getInitializationState().requireNotInitilized();
    for (String fqn : datatypeList) {
      registerDatatype(fqn);
    }
  }

}
