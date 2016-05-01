/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.orient.impl.property;

import javax.inject.Named;

import com.orientechnologies.orient.core.metadata.schema.OProperty;
import com.orientechnologies.orient.core.metadata.schema.OType;

import net.sf.mmm.util.property.api.WritableProperty;
import net.sf.mmm.util.property.api.lang.DoubleProperty;
import net.sf.mmm.util.property.api.lang.ReadableDoubleProperty;
import net.sf.mmm.util.reflect.api.GenericType;

/**
 * The implementation of {@link SinglePropertyBuilder} for {@link OType#DOUBLE}.
 *
 * @author hohwille
 * @since 1.0.0
 */
@Named
public class SinglePropertyBuilderDouble implements SinglePropertyBuilder<Number> {

  /**
   * The constructor.
   */
  public SinglePropertyBuilderDouble() {
    super();
  }

  @Override
  public OType getType() {

    return OType.DOUBLE;
  }

  @Override
  public Class<Double> getValueClass() {

    return Double.class;
  }

  @Override
  public Class<? extends WritableProperty<Number>> getPropertyType(OProperty oProperty) {

    return DoubleProperty.class;
  }

  @Override
  public GenericType<Double> getValueType(OProperty oProperty) {

    return ReadableDoubleProperty.TYPE;
  }

}
