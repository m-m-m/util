/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.base.datatype.adapter.jpa;

import net.sf.mmm.util.lang.api.DatatypeDescriptor;
import net.sf.mmm.util.lang.api.DatatypeDescriptorManager;
import net.sf.mmm.util.lang.api.SimpleDatatype;

/**
 * This is a generic implementation of {@link AbstractSimpleDatatypeAttributeConverter}.
 *
 * @param <V> the generic for the basic java type representing the {@link SimpleDatatype#getValue() value}.
 * @param <T> the generic for the adapted {@link SimpleDatatype}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 6.0.0
 */
public class SimpleDatatypeAttributeConverter<T extends SimpleDatatype<V>, V> extends
    AbstractSimpleDatatypeAttributeConverter<T, V> {

  private  final DatatypeDescriptor<T> datatypeDescriptor;

  /**
   * The constructor.
   *
   * @param datatype is the {@link Class} reflecting the datatype to adapt.
   * @param datatypeDescriptorManager the {@link DatatypeDescriptorManager} instance.
   */
  public SimpleDatatypeAttributeConverter(Class<T> datatype, DatatypeDescriptorManager datatypeDescriptorManager) {

    this(datatypeDescriptorManager.getDatatypeDescriptor(datatype));
  }

  /**
   * The constructor.
   *
   * @param datatypeDescriptor the {@link DatatypeDescriptor} for the datatype to adapt.
   */
  public SimpleDatatypeAttributeConverter(DatatypeDescriptor<T> datatypeDescriptor) {

    super();
    this.datatypeDescriptor = datatypeDescriptor;
  }

  @Override
  public T convertToEntityAttribute(V value) {

    return this.datatypeDescriptor.create(value);
  }
}
