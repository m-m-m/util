/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.orient.impl.property;

import javax.inject.Named;

import com.orientechnologies.orient.core.metadata.schema.OProperty;
import com.orientechnologies.orient.core.metadata.schema.OType;

import net.sf.mmm.util.property.api.WritableProperty;
import net.sf.mmm.util.property.api.lang.GenericProperty;
import net.sf.mmm.util.reflect.api.GenericType;

/**
 * The implementation of {@link SinglePropertyBuilder} for {@link OType#ANY}.
 *
 * @author hohwille
 * @since 1.0.0
 */
@Named
public class SinglePropertyBuilderAny implements SinglePropertyBuilder<Object> {

  /**
   * The constructor.
   */
  public SinglePropertyBuilderAny() {
    super();
  }

  @Override
  public OType getType() {

    return OType.ANY;
  }

  @Override
  public Class<?> getValueClass() {

    return Object.class;
  }

  @SuppressWarnings({ "unchecked", "rawtypes" })
  @Override
  public Class<? extends WritableProperty<Object>> getPropertyType(OProperty oProperty) {

    return (Class) GenericProperty.class;
  }

  @Override
  public GenericType<Object> getValueType(OProperty oProperty) {

    return GenericProperty.TYPE_DEFAULT;
  }

}
