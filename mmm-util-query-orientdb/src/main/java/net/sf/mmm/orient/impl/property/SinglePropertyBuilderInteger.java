/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.orient.impl.property;

import javax.inject.Named;

import com.orientechnologies.orient.core.metadata.schema.OProperty;
import com.orientechnologies.orient.core.metadata.schema.OType;

import net.sf.mmm.util.property.api.WritableProperty;
import net.sf.mmm.util.property.api.lang.IntegerProperty;
import net.sf.mmm.util.property.api.lang.ReadableIntegerProperty;
import net.sf.mmm.util.reflect.api.GenericType;

/**
 * The implementation of {@link SinglePropertyBuilder} for {@link OType#INTEGER}.
 *
 * @author hohwille
 * @since 1.0.0
 */
@Named
public class SinglePropertyBuilderInteger implements SinglePropertyBuilder<Number> {

  /**
   * The constructor.
   */
  public SinglePropertyBuilderInteger() {
    super();
  }

  @Override
  public OType getType() {

    return OType.INTEGER;
  }

  @Override
  public Class<Integer> getValueClass() {

    return Integer.class;
  }

  @Override
  public Class<? extends WritableProperty<Number>> getPropertyType(OProperty oProperty) {

    return IntegerProperty.class;
  }

  @Override
  public GenericType<Integer> getValueType(OProperty oProperty) {

    return ReadableIntegerProperty.TYPE;
  }

}
