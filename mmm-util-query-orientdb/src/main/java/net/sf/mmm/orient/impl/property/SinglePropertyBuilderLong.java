/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.orient.impl.property;

import javax.inject.Named;

import com.orientechnologies.orient.core.metadata.schema.OProperty;
import com.orientechnologies.orient.core.metadata.schema.OType;

import net.sf.mmm.util.property.api.WritableProperty;
import net.sf.mmm.util.property.api.lang.LongProperty;
import net.sf.mmm.util.property.api.lang.ReadableLongProperty;
import net.sf.mmm.util.reflect.api.GenericType;

/**
 * The implementation of {@link SinglePropertyBuilder} for {@link OType#LONG}.
 *
 * @author hohwille
 * @since 1.0.0
 */
@Named
public class SinglePropertyBuilderLong implements SinglePropertyBuilder<Number> {

  /**
   * The constructor.
   */
  public SinglePropertyBuilderLong() {
    super();
  }

  @Override
  public OType getType() {

    return OType.LONG;
  }

  @Override
  public Class<Long> getValueClass() {

    return Long.class;
  }

  @Override
  public Class<? extends WritableProperty<Number>> getPropertyType(OProperty oProperty) {

    return LongProperty.class;
  }

  @Override
  public GenericType<Long> getValueType(OProperty oProperty) {

    return ReadableLongProperty.TYPE;
  }

}
