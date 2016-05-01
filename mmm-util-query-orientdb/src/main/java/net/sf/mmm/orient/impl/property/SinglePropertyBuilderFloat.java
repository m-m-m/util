/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.orient.impl.property;

import javax.inject.Named;

import com.orientechnologies.orient.core.metadata.schema.OProperty;
import com.orientechnologies.orient.core.metadata.schema.OType;

import net.sf.mmm.util.property.api.WritableProperty;
import net.sf.mmm.util.property.api.lang.FloatProperty;
import net.sf.mmm.util.property.api.lang.ReadableFloatProperty;
import net.sf.mmm.util.reflect.api.GenericType;

/**
 * The implementation of {@link SinglePropertyBuilder} for {@link OType#FLOAT}.
 *
 * @author hohwille
 * @since 1.0.0
 */
@Named
public class SinglePropertyBuilderFloat implements SinglePropertyBuilder<Number> {

  /**
   * The constructor.
   */
  public SinglePropertyBuilderFloat() {
    super();
  }

  @Override
  public OType getType() {

    return OType.FLOAT;
  }

  @Override
  public Class<Float> getValueClass() {

    return Float.class;
  }

  @Override
  public Class<? extends WritableProperty<Number>> getPropertyType(OProperty oProperty) {

    return FloatProperty.class;
  }

  @Override
  public GenericType<Float> getValueType(OProperty oProperty) {

    return ReadableFloatProperty.TYPE;
  }

}
