/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.base.datatype.descriptor;

import net.sf.mmm.util.exception.api.IllegalCaseException;
import net.sf.mmm.util.lang.api.StringUtil;
import net.sf.mmm.util.value.api.WrongValueTypeException;

/**
 * This is the implementation of {@link net.sf.mmm.util.lang.api.DatatypeDescriptor} for a {@link Enum}.
 *
 * @param <T> is the generic type of the {@link Enum} to describe.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 6.0.0
 */
public class DatatypeDescriptorEnum<T extends Enum<T>> extends AbstractDatatypeDescriptor<T> {

  /** The {@link StringUtil} instance. */
  private final StringUtil stringUtil;

  /**
   * The constructor.
   *
   * @param datatype is the {@link Class} reflecting the {@link Enum}.
   * @param stringUtil is the {@link StringUtil} to use.
   */
  public DatatypeDescriptorEnum(Class<T> datatype, StringUtil stringUtil) {

    super(datatype, new DatatypeSegmentDescriptorEnum<T>("name", stringUtil));
    assert (datatype.isEnum());
    this.stringUtil = stringUtil;
  }

  @Override
  protected T doCreate(Object... segments) {

    Object value = segments[0];
    if (value == null) {
      return null;
    }
    if (!(value instanceof String)) {
      throw new WrongValueTypeException(value, String.class);
    }
    for (T instance : getDatatype().getEnumConstants()) {
      if (instance.name().equals(value)) {
        return instance;
      }
    }
    String deCamlCased = this.stringUtil.fromCamlCase(value.toString(), '_');
    for (T instance : getDatatype().getEnumConstants()) {
      if (instance.name().equals(deCamlCased)) {
        return instance;
      }
    }
    throw new IllegalCaseException(value.toString() + "@" + getDatatype().getName());
  }
}
