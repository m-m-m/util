/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.orient.impl.property;

import javax.inject.Named;

import com.orientechnologies.orient.core.metadata.schema.OProperty;
import com.orientechnologies.orient.core.metadata.schema.OType;

import net.sf.mmm.util.property.api.WritableProperty;
import net.sf.mmm.util.property.api.lang.ReadableShortProperty;
import net.sf.mmm.util.property.api.lang.ShortProperty;
import net.sf.mmm.util.reflect.api.GenericType;

/**
 * The implementation of {@link SinglePropertyBuilder} for {@link OType#SHORT}.
 *
 * @author hohwille
 * @since 1.0.0
 */
@Named
public class SinglePropertyBuilderShort implements SinglePropertyBuilder<Number> {

  /**
   * The constructor.
   */
  public SinglePropertyBuilderShort() {
    super();
  }

  @Override
  public OType getType() {

    return OType.SHORT;
  }

  @Override
  public Class<Short> getValueClass() {

    return Short.class;
  }

  @Override
  public Class<? extends WritableProperty<Number>> getPropertyType(OProperty oProperty) {

    return ShortProperty.class;
  }

  @Override
  public GenericType<Short> getValueType(OProperty oProperty) {

    return ReadableShortProperty.TYPE;
  }

}
