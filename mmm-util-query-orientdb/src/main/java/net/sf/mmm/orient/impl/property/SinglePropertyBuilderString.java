/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.orient.impl.property;

import javax.inject.Named;

import com.orientechnologies.orient.core.metadata.schema.OProperty;
import com.orientechnologies.orient.core.metadata.schema.OType;

import net.sf.mmm.util.property.api.WritableProperty;
import net.sf.mmm.util.property.api.lang.ReadableStringProperty;
import net.sf.mmm.util.property.api.lang.StringProperty;
import net.sf.mmm.util.reflect.api.GenericType;

/**
 * The implementation of {@link SinglePropertyBuilder} for {@link OType#STRING}.
 *
 * @author hohwille
 * @since 1.0.0
 */
@Named
public class SinglePropertyBuilderString implements SinglePropertyBuilder<String> {

  /**
   * The constructor.
   */
  public SinglePropertyBuilderString() {
    super();
  }

  @Override
  public OType getType() {

    return OType.STRING;
  }

  @Override
  public Class<String> getValueClass() {

    return String.class;
  }

  @Override
  public Class<? extends WritableProperty<String>> getPropertyType(OProperty oProperty) {

    return StringProperty.class;
  }

  @Override
  public GenericType<String> getValueType(OProperty oProperty) {

    return ReadableStringProperty.TYPE;
  }

}
