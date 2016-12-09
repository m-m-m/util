/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.api.link;

import java.util.function.Supplier;

import net.sf.mmm.util.data.api.entity.Entity;
import net.sf.mmm.util.data.api.link.Link;
import net.sf.mmm.util.property.api.lang.GenericProperty;
import net.sf.mmm.util.reflect.api.GenericType;
import net.sf.mmm.util.reflect.base.GenericTypeBuilder;
import net.sf.mmm.util.reflect.base.GenericTypeVariable;
import net.sf.mmm.util.reflect.impl.SimpleGenericTypeImpl;
import net.sf.mmm.util.validation.base.AbstractValidator;

/**
 * This class represents a {@link GenericProperty property} containing a {@link Link} that {@link Link#getTarget()
 * points to} an {@link Entity}.
 *
 * @param <E> the generic type of the {@link Link#getTarget() linked} {@link Entity}.
 *
 * @author hohwille
 * @since 1.0.0
 */
public class LinkProperty<E extends Entity> extends GenericProperty<Link<E>> {

  /** The fallback for {@link #getType()} if linked class is unknown. */
  @SuppressWarnings("rawtypes")
  private static final GenericType<Link> TYPE = new SimpleGenericTypeImpl<>(Link.class);

  /**
   * The constructor.
   *
   * @param name - see {@link #getName()}.
   * @param type - see {@link #getType()}.
   * @param bean - see {@link #getBean()}.
   */
  public LinkProperty(String name, GenericType<Link<E>> type, Object bean) {
    super(name, type, bean);
  }

  /**
   * The constructor.
   *
   * @param name - see {@link #getName()}.
   * @param type - see {@link #getType()}.
   * @param bean - see {@link #getBean()}.
   * @param validator - see {@link #validate()}.
   */
  public LinkProperty(String name, GenericType<Link<E>> type, Object bean, AbstractValidator<? super Link<E>> validator) {
    super(name, type, bean, validator);
  }

  /**
   * The constructor.
   *
   * @param name - see {@link #getName()}.
   * @param type - see {@link #getType()}.
   * @param bean - see {@link #getBean()}.
   * @param expression the {@link Supplier} {@link Supplier#get() providing} the actual {@link #getValue() value}.
   */
  public LinkProperty(String name, GenericType<Link<E>> type, Object bean, Supplier<? extends Link<E>> expression) {
    super(name, type, bean, expression);
  }

  /**
   * @param <E> the generic type of the {@link Link#getTarget() linked} {@link Entity}.
   * @param beanClass the class reflecting the linked {@link Entity}.
   * @return the {@link GenericType} for an {@link Link} {@link Link#getTarget() pointing to} an {@link Entity} of the
   *         given {@link Class}.
   */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  public static <E extends Entity> GenericType<Link<E>> createLinkType(Class<E> beanClass) {

    if (beanClass == null) {
      return (GenericType) TYPE;
    }
    return new GenericTypeBuilder<Link<E>>() {}.with(new GenericTypeVariable<E>() {}, beanClass).build();
  }

}