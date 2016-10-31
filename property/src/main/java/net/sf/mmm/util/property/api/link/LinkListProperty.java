/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.api.link;

import java.util.List;
import java.util.function.Supplier;

import javafx.collections.ObservableList;
import net.sf.mmm.util.data.api.entity.Entity;
import net.sf.mmm.util.data.api.link.Link;
import net.sf.mmm.util.property.api.lang.GenericProperty;
import net.sf.mmm.util.property.api.util.ListProperty;
import net.sf.mmm.util.reflect.api.GenericType;
import net.sf.mmm.util.validation.base.AbstractValidator;

/**
 * This class represents a {@link GenericProperty property} containing a {@link List} of {@link Link}s that each
 * {@link Link#getTarget() point to} an {@link Entity}.
 *
 * @param <E> the generic type of the {@link Link#getTarget() linked} {@link Entity}.
 *
 * @author hohwille
 * @since 1.0.0
 */
public class LinkListProperty<E extends Entity> extends ListProperty<Link<E>> {

  /**
   * The constructor.
   *
   * @param name - see {@link #getName()}.
   * @param type - see {@link #getType()}.
   * @param bean - see {@link #getBean()}.
   */
  public LinkListProperty(String name, GenericType<ObservableList<Link<E>>> type, Object bean) {
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
  public LinkListProperty(String name, GenericType<ObservableList<Link<E>>> type, Object bean, AbstractValidator<? super ObservableList<Link<E>>> validator) {
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
  public LinkListProperty(String name, GenericType<? extends ObservableList<Link<E>>> type, Object bean, Supplier<ObservableList<Link<E>>> expression) {
    super(name, type, bean, expression);
  }

  /**
   * @param <E> the generic type of the {@link Link#getTarget() linked} {@link Entity}.
   * @param beanClass the class reflecting the linked {@link Entity}.
   * @return the {@link GenericType} for an {@link Link} {@link Link#getTarget() pointing to} an {@link Entity} of the
   *         given {@link Class}.
   */
  public static <E extends Entity> GenericType<ObservableList<Link<E>>> createLinkType(Class<E> beanClass) {

    return createListType(LinkProperty.createLinkType(beanClass));
  }

}
