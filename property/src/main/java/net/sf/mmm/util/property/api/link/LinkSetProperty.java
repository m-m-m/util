/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.api.link;

import java.util.Set;
import java.util.function.Supplier;

import javafx.collections.ObservableSet;
import net.sf.mmm.util.data.api.entity.Entity;
import net.sf.mmm.util.data.api.link.Link;
import net.sf.mmm.util.property.api.lang.GenericProperty;
import net.sf.mmm.util.property.api.util.SetProperty;
import net.sf.mmm.util.reflect.api.GenericType;
import net.sf.mmm.util.validation.base.AbstractValidator;

/**
 * This class represents a {@link GenericProperty property} containing a {@link Set} of {@link Link}s that each
 * {@link Link#getTarget() point to} an {@link Entity}. Unlike {@link LinkListProperty} the {@link Link}s of an
 * {@link LinkSetProperty} have no order and can not contain duplicates.
 *
 * @param <E> the generic type of the {@link Link#getTarget() linked} {@link Entity}.
 *
 * @author hohwille
 * @since 1.0.0
 */
public class LinkSetProperty<E extends Entity> extends SetProperty<Link<E>> {

  /**
   * The constructor.
   *
   * @param name - see {@link #getName()}.
   * @param type - see {@link #getType()}.
   * @param bean - see {@link #getBean()}.
   */
  public LinkSetProperty(String name, GenericType<ObservableSet<Link<E>>> type, Object bean) {
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
  public LinkSetProperty(String name, GenericType<ObservableSet<Link<E>>> type, Object bean, AbstractValidator<? super ObservableSet<Link<E>>> validator) {
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
  public LinkSetProperty(String name, GenericType<? extends ObservableSet<Link<E>>> type, Object bean, Supplier<ObservableSet<Link<E>>> expression) {
    super(name, type, bean, expression);
  }

  /**
   * @param <E> the generic type of the {@link Link#getTarget() linked} {@link Entity}.
   * @param beanClass the class reflecting the linked {@link Entity}.
   * @return the {@link GenericType} for an {@link Link} {@link Link#getTarget() pointing to} an {@link Entity} of the
   *         given {@link Class}.
   */
  public static <E extends Entity> GenericType<ObservableSet<Link<E>>> createLinkType(Class<E> beanClass) {

    return createSetType(LinkProperty.createLinkType(beanClass));
  }

}
