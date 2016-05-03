/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.base.datatype.descriptor;


/**
 * This is the implementation of {@link net.sf.mmm.util.lang.api.DatatypeDescriptor} for an atomic Java
 * {@link net.sf.mmm.util.lang.api.Datatype} such as {@link Integer}, {@link String}, {@link Long},
 * {@link Double}, {@link Boolean}, etc.
 *
 * @param <T> is the generic type of the atomic Java {@link net.sf.mmm.util.lang.api.Datatype} to describe.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 6.0.0
 */
public class DatatypeDescriptorAtomicJavaDatatype<T> extends AbstractDatatypeDescriptor<T> {

  /**
   * The constructor.
   *
   * @param datatype is the {@link Class} reflecting the {@link Enum}.
   */
  public DatatypeDescriptorAtomicJavaDatatype(Class<T> datatype) {

    super(datatype);
  }

  @Override
  protected T doCreate(Object... segments) {

    throw new IllegalStateException();
  }
}
