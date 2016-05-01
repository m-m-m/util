/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.orient.impl.property;

import javax.inject.Named;

import com.orientechnologies.orient.core.metadata.schema.OProperty;
import com.orientechnologies.orient.core.metadata.schema.OType;

import net.sf.mmm.util.property.api.WritableProperty;
import net.sf.mmm.util.property.api.lang.ByteProperty;
import net.sf.mmm.util.property.api.lang.ReadableByteProperty;
import net.sf.mmm.util.reflect.api.GenericType;

/**
 * The implementation of {@link SinglePropertyBuilder} for {@link OType#BYTE}.
 *
 * @author hohwille
 * @since 1.0.0
 */
@Named
public class SinglePropertyBuilderByte implements SinglePropertyBuilder<Number> {

  /**
   * The constructor.
   */
  public SinglePropertyBuilderByte() {
    super();
  }

  @Override
  public OType getType() {

    return OType.BYTE;
  }

  @Override
  public Class<Byte> getValueClass() {

    return Byte.class;
  }

  @Override
  public Class<? extends WritableProperty<Number>> getPropertyType(OProperty oProperty) {

    return ByteProperty.class;
  }

  @Override
  public GenericType<Byte> getValueType(OProperty oProperty) {

    return ReadableByteProperty.TYPE;
  }

}
