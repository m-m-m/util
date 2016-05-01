/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.orient.impl.property;

import java.util.ArrayList;
import java.util.List;

import com.orientechnologies.orient.core.metadata.schema.OClass;
import com.orientechnologies.orient.core.metadata.schema.OProperty;
import com.orientechnologies.orient.core.metadata.schema.OType;

import net.sf.mmm.orient.api.bean.OrientBean;
import net.sf.mmm.util.bean.api.BeanAccess;
import net.sf.mmm.util.component.api.ComponentSpecification;
import net.sf.mmm.util.property.api.AbstractProperty;
import net.sf.mmm.util.property.api.WritableProperty;
import net.sf.mmm.util.reflect.api.GenericType;
import net.sf.mmm.util.validation.base.AbstractValidator;
import net.sf.mmm.util.validation.base.text.ValidatorPattern;

/**
 * This is the interface for an {@link AbstractPropertyBuilder} responsible for a single {@link #getType() type} of
 * {@link OProperty properties}.
 *
 * @param <V> the generic type of the {@link WritableProperty#getValue() property value}.
 *
 * @author hohwille
 * @since 1.0.0
 */
@ComponentSpecification(plugin = true)
public interface SinglePropertyBuilder<V> extends AbstractPropertyBuilder {

  /**
   * @return the static {@link OType} value this {@link SinglePropertyBuilder} is responsible for.
   */
  OType getType();

  /**
   * @return the static and raw {@link Class} of the {@link WritableProperty#getType() bean property value type} this
   *         {@link SinglePropertyBuilder} is responsible for.
   */
  Class<? extends V> getValueClass();

  @SuppressWarnings({ "unchecked", "rawtypes" })
  @Override
  default WritableProperty<V> build(OProperty oProperty, OrientBean prototype) {

    assert (oProperty.getType() == getType());
    GenericType<? extends V> valueType = getValueType(oProperty);
    Class<? extends WritableProperty<V>> propertyType = getPropertyType(oProperty);
    String propertyName = oProperty.getName();
    BeanAccess access = prototype.access();
    WritableProperty<V> property = access.getOrCreateProperty(propertyName, valueType, propertyType);

    // sync validators
    List<AbstractValidator<? super V>> validators = null;
    String regexp = oProperty.getRegexp();
    if (regexp != null) {
      validators = new ArrayList<>(2);
      validators.add((AbstractValidator) new ValidatorPattern(regexp));
    }
    String min = oProperty.getMin();
    String max = oProperty.getMax();
    if ((min != null) || (max != null)) {
      if (validators == null) {
        validators = new ArrayList<>(1);
      }
      AbstractValidator validator = ((AbstractProperty<V>) property).withValdidator().range(min, max).build();
      validators.add(validator);
    }
    if (validators != null) {
      access.addPropertyValidators(property, validators);
    }
    return property;
  }

  /**
   * @param property the {@link WritableProperty property} to create in the given {@link OClass}, that is currently
   *        missing.
   * @param oClass the {@link OClass} of the OrientDB schema.
   * @return the created {@link OProperty}. May be {@code null} if the {@link WritableProperty#getType() property type}
   *         is not supported.
   */
  default OProperty build(WritableProperty<V> property, OClass oClass) {

    String name = property.getName();
    return oClass.createProperty(name, getType());
    // OProperty oProperty = oClass.getProperty(name);
    // if (oProperty == null) {
    // oProperty = create(property, oClass);
    // }
    // return oProperty;
  }

  /**
   * @param oProperty the {@link OProperty}.
   * @return the {@link Class} reflecting the corresponding {@link WritableProperty} type such as e.g.
   *         {@link net.sf.mmm.util.property.api.lang.StringProperty} for {@link OType#STRING}.
   */
  Class<? extends WritableProperty<V>> getPropertyType(OProperty oProperty);

  /**
   * @param oProperty the {@link OProperty}.
   * @return the {@link GenericType} reflecting the corresponding {@link WritableProperty#getType() value type}.
   */
  GenericType<? extends V> getValueType(OProperty oProperty);

}