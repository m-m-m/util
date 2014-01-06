/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.binding;

import java.lang.annotation.Annotation;
import java.util.Collection;

import javax.validation.Validator;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.metadata.ConstraintDescriptor;
import javax.validation.metadata.PropertyDescriptor;

import net.sf.mmm.client.ui.api.binding.DatatypeDetector;
import net.sf.mmm.util.math.api.MathUtilLimited;
import net.sf.mmm.util.math.api.NumberType;
import net.sf.mmm.util.pojo.descriptor.api.PojoDescriptor;
import net.sf.mmm.util.pojo.descriptor.api.PojoDescriptorBuilder;
import net.sf.mmm.util.pojo.descriptor.api.PojoDescriptorBuilderFactory;
import net.sf.mmm.util.pojo.descriptor.api.PojoPropertyDescriptor;
import net.sf.mmm.util.pojo.descriptor.api.PojoPropertyNotFoundException;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorNonArg;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorNonArgMode;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorOneArg;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorOneArgMode;
import net.sf.mmm.util.pojo.path.api.TypedProperty;
import net.sf.mmm.util.validation.api.ValueValidator;
import net.sf.mmm.util.validation.base.ValidatorJsr303;
import net.sf.mmm.util.value.api.Range;

/**
 * This is the default implementation of {@link UiDataBindingAdapter} based on {@link PojoDescriptor}.
 * 
 * @param <VALUE> is the generic type of the value to adapt.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiDataBindingAdapterImpl<VALUE> implements UiDataBindingAdapter<VALUE> {

  /** The {@link PojoDescriptorBuilder} instance. */
  private final PojoDescriptorBuilder descriptorBuilder;

  /** The {@link PojoDescriptor} used to implement the functionality. */
  private final PojoDescriptor<VALUE> descriptor;

  /** The {@link DatatypeDetector} instance. */
  private final DatatypeDetector datatypeDetector;

  /** The {@link Validator} instance. */
  private final Validator validator;

  /** @see #getMathUtil() */
  private final MathUtilLimited mathUtil;

  /**
   * The constructor.
   * 
   * @param type is the {@link Class} reflecting the value to adapt.
   * @param descriptorBuilderFactory is the {@link PojoDescriptorBuilderFactory} instance.
   * @param datatypeDetector is the {@link DatatypeDetector} instance.
   * @param validator is the {@link Validator} instance.
   * @param mathUtil is the {@link MathUtilLimited} instance.
   */
  public UiDataBindingAdapterImpl(Class<VALUE> type, PojoDescriptorBuilderFactory descriptorBuilderFactory,
      DatatypeDetector datatypeDetector, Validator validator, MathUtilLimited mathUtil) {

    super();
    this.descriptorBuilder = descriptorBuilderFactory.createPublicMethodDescriptorBuilder();
    this.descriptor = this.descriptorBuilder.getDescriptor(type);
    this.datatypeDetector = datatypeDetector;
    this.validator = validator;
    this.mathUtil = mathUtil;
  }

  /**
   * @return the mathUtil
   */
  protected MathUtilLimited getMathUtil() {

    return this.mathUtil;
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  @Override
  public VALUE copy(VALUE value) {

    if (value == null) {
      return null;
    }
    if (this.descriptor.getPojoClass() == value.getClass()) {
      return copy(value, this.descriptor);
    } else {
      PojoDescriptor subClassDescriptor = this.descriptorBuilder.getDescriptor(value.getClass());
      return (VALUE) copy(value, subClassDescriptor);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public VALUE createNewValue() {

    return this.descriptor.newInstance();
  }

  /**
   * @see #copy(Object)
   * 
   * @param <V> the generic type of the value (for recursive invocation).
   * @param value is the value to copy.
   * @param pojoDescriptor is the {@link PojoDescriptor} for <code>value</code>.
   * @return the copy of <code>value</code>.
   */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  private <V> V copy(V value, PojoDescriptor<V> pojoDescriptor) {

    // might be wrong as value can be a sub-class...
    V copy = pojoDescriptor.newInstance();
    Collection<? extends PojoPropertyDescriptor> propertyDescriptors = pojoDescriptor.getPropertyDescriptors();
    for (PojoPropertyDescriptor propertyDescriptor : propertyDescriptors) {
      PojoPropertyAccessorOneArg setter = propertyDescriptor.getAccessor(PojoPropertyAccessorOneArgMode.SET);
      if (setter != null) {
        PojoPropertyAccessorNonArg getter = propertyDescriptor.getAccessor(PojoPropertyAccessorNonArgMode.GET);
        if (getter != null) {
          Object propertyValue = getter.invoke(value);
          if (propertyValue != null) {
            Class<?> propertyClass = propertyValue.getClass();
            if (!this.datatypeDetector.isDatatype(propertyClass)) {
              PojoDescriptor propertyPojoDescriptor = this.descriptorBuilder.getDescriptor(propertyClass);
              propertyValue = copy(propertyValue, propertyPojoDescriptor);
            }
          }
          setter.invoke(copy, propertyValue);
        }
      }
    }
    return copy;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <T> ValueValidator<T> getPropertyValidator(TypedProperty<T> property, Class<T> propertyType) {

    return new ValidatorJsr303<T>(this.validator, this.descriptor.getPojoClass(), property.getSegment(), propertyType);
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  public <T> Range<?> getRangeConstraints(TypedProperty<T> property, Class<T> propertyType) {

    PropertyDescriptor propertyDescriptor = this.validator.getConstraintsForClass(this.descriptor.getPojoClass())
        .getConstraintsForProperty(property.getSegment());

    NumberType<?> numberType = this.mathUtil.getNumberType(propertyType);
    Object minimum = null;
    Object maximum = null;
    for (ConstraintDescriptor<?> constraint : propertyDescriptor.getConstraintDescriptors()) {
      Annotation annotation = constraint.getAnnotation();
      Class<? extends Annotation> annotationType = annotation.annotationType();
      if (Min.class == annotationType) {
        Min min = (Min) annotation;
        if (propertyType == Long.class) {
          minimum = numberType.valueOf(Long.valueOf(min.value()), false);
          // if (maximum == null) {
          // maximum = numberType.getMaximumValue();
          // }
        }
      } else if (Max.class == annotationType) {
        Max max = (Max) annotation;
        maximum = numberType.valueOf(Long.valueOf(max.value()), false);
        // if (minimum == null) {
        // minimum = numberType.getMinimumValue();
        // }
      } else if (DecimalMin.class == annotationType) {
        DecimalMin min = (DecimalMin) annotation;
        minimum = numberType.valueOf(min.value());
        // if (maximum == null) {
        // maximum = numberType.getMaximumValue();
        // }
      } else if (DecimalMax.class == annotationType) {
        DecimalMin max = (DecimalMin) annotation;
        maximum = numberType.valueOf(max.value());
        // if (minimum == null) {
        // minimum = numberType.getMinimumValue();
        // }
      }
    }
    if ((minimum != null) || (maximum != null)) {
      return new Range((Comparable) minimum, (Comparable) maximum);
    }
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  @Override
  public <T> Class<T> getPropertyType(TypedProperty<T> property) {

    Class<T> propertyType = property.getPropertyType();
    if (propertyType == null) {
      PojoPropertyDescriptor propertyDescriptor = this.descriptor.getPropertyDescriptor(property);
      PojoPropertyAccessorNonArgMode mode = PojoPropertyAccessorNonArgMode.GET;
      PojoPropertyAccessorNonArg accessor = propertyDescriptor.getAccessor(mode);
      if (accessor == null) {
        throw new PojoPropertyNotFoundException(this.descriptor.getPojoClass(), property.getPojoPath(), mode);
      }
      return (Class) accessor.getPropertyClass();
    }
    return propertyType;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <T> T getPropertyValue(VALUE pojo, TypedProperty<T> property) {

    return this.descriptor.getProperty(pojo, property);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Object getPropertyValue(VALUE pojo, String property) {

    return this.descriptor.getProperty(pojo, property);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <T> void setPropertyValue(VALUE pojo, TypedProperty<T> property, T propertyValue) {

    if ((propertyValue == null) && getPropertyType(property).isPrimitive()) {
      // TODO hohwille Doing nothing is also wrong null might actually mean 0 or what to do here?
      return;
    }
    this.descriptor.setProperty(pojo, property, propertyValue);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setPropertyValue(VALUE pojo, String property, Object propertyValue) {

    this.descriptor.setProperty(pojo, property, propertyValue);
  }

}
