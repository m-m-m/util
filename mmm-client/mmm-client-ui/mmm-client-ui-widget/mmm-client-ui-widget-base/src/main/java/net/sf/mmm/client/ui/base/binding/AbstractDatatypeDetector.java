/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.binding;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * This is the abstract base implementation of {@link DatatypeDetector}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractDatatypeDetector implements DatatypeDetector {

  /** @see #isDatatype(Class) */
  private final Set<Class<?>> datatypeSet;

  /**
   * The constructor.
   */
  public AbstractDatatypeDetector() {

    super();
    this.datatypeSet = new HashSet<Class<?>>();
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

    this.datatypeSet.add(datatype);
  }

  /**
   * Registers the default datatypes.
   */
  protected void registerDefaultDatatypes() {

    registerDatatype(String.class);
    registerDatatype(Boolean.class);
    registerDatatype(Character.class);
    registerDatatype(Integer.class);
    registerDatatype(Double.class);
    registerDatatype(Float.class);
    registerDatatype(Byte.class);
    registerDatatype(Short.class);
    registerDatatype(BigInteger.class);
    registerDatatype(BigDecimal.class);
    registerDatatype(Number.class);
    registerDatatype(Date.class);
    // registerDatatype(Calendar.class);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isDatatype(Class<?> type) {

    if (type.isEnum()) {
      return true;
    }
    return this.datatypeSet.contains(type);
  }

}
