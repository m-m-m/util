/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.api.lang;

import java.util.Objects;
import java.util.function.Supplier;

import javax.json.stream.JsonGenerator;
import javax.json.stream.JsonParser;

import net.sf.mmm.util.property.api.AbstractRegularProperty;
import net.sf.mmm.util.property.api.WritableProperty;
import net.sf.mmm.util.reflect.api.GenericType;
import net.sf.mmm.util.reflect.impl.SimpleGenericTypeImpl;
import net.sf.mmm.util.validation.base.AbstractValidator;
import net.sf.mmm.util.validation.base.ObjectValidatorBuilder;
import net.sf.mmm.util.validation.base.ValidatorBuilderObject;

/**
 * This is the implementation of {@link WritableProperty}.
 *
 * @param <V> is the generic type of the {@link #getValue() value}.
 *
 * @author hohwille
 * @since 8.5.0
 */
public class GenericProperty<V> extends AbstractRegularProperty<V> {

  /** The {@link #getType() type} of {@link Object}. */
  public static final GenericType<Object> TYPE_DEFAULT = new SimpleGenericTypeImpl<>(Object.class);

  private final GenericType<V> type;

  private V value;

  /**
   * The constructor.
   *
   * @param name - see {@link #getName()}.
   * @param type - see {@link #getType()}.
   * @param bean - see {@link #getBean()}.
   */
  public GenericProperty(String name, GenericType<V> type, Object bean) {
    this(name, type, bean, (AbstractValidator<? super V>) null);
  }

  /**
   * The constructor.
   *
   * @param name - see {@link #getName()}.
   * @param type - see {@link #getType()}.
   * @param bean - see {@link #getBean()}.
   * @param validator - see {@link #validate()}.
   */
  public GenericProperty(String name, GenericType<V> type, Object bean, AbstractValidator<? super V> validator) {
    super(name, bean, validator);
    this.type = type;
  }

  /**
   * The constructor.
   *
   * @param name - see {@link #getName()}.
   * @param type - see {@link #getType()}.
   * @param bean - see {@link #getBean()}.
   * @param expression the {@link Supplier} {@link Supplier#get() providing} the actual {@link #getValue() value}.
   */
  public GenericProperty(String name, GenericType<V> type, Object bean, Supplier<? extends V> expression) {
    super(name, bean, expression);
    this.type = type;
  }

  @Override
  public GenericType<V> getType() {

    return this.type;
  }

  @Override
  protected V doGetValue() {

    return this.value;
  }

  @Override
  protected boolean doSetValue(V newValue) {

    if (Objects.equals(this.value, newValue)) {
      return false;
    }
    this.value = newValue;
    return true;
  }

  /**
   * @return a new {@link ObjectValidatorBuilder builder} for the validator of this property with a
   *         {@link ObjectValidatorBuilder#and() parent-builder} to create a {@link #copy(AbstractValidator)} of this
   *         property with the configured validator.
   */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  @Override
  public ObjectValidatorBuilder<? extends V, ? extends PropertyBuilder<? extends GenericProperty<? extends V>>, ?> withValdidator() {

    return withValdidator(x -> new ValidatorBuilderObject(x));
  }

  @Override
  protected void toJson(JsonGenerator json, V propertyValue) {

    getJsonUtil().write(json, getName(), propertyValue);
  }

  @Override
  public void fromJson(JsonParser json) {

    V v = getJsonUtil().read(json, this.type);
    setValue(v);
  }

}
