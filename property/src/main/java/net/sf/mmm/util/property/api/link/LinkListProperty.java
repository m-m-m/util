/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.api.link;

import java.util.List;

import javafx.collections.ObservableList;
import net.sf.mmm.util.bean.api.Bean;
import net.sf.mmm.util.bean.api.entity.EntityBean;
import net.sf.mmm.util.bean.api.link.Link;
import net.sf.mmm.util.property.api.lang.GenericProperty;
import net.sf.mmm.util.property.api.util.ListProperty;
import net.sf.mmm.util.reflect.api.GenericType;
import net.sf.mmm.util.validation.base.AbstractValidator;

/**
 * This class represents a {@link GenericProperty property} containing a {@link List} of {@link Link}s that each
 * {@link Link#getTarget() point to} an {@link EntityBean}.
 *
 * @param <E> the generic type of the {@link Link#getTarget() linked} {@link EntityBean}.
 *
 * @author hohwille
 * @since 1.0.0
 */
public class LinkListProperty<E extends EntityBean> extends ListProperty<Link<E>> {

  /**
   * The constructor.
   *
   * @param name - see {@link #getName()}.
   * @param type - see {@link #getType()}.
   * @param bean - see {@link #getBean()}.
   */
  public LinkListProperty(String name, GenericType<ObservableList<Link<E>>> type, Bean bean) {
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
  public LinkListProperty(String name, GenericType<ObservableList<Link<E>>> type, Bean bean,
      AbstractValidator<? super ObservableList<Link<E>>> validator) {
    super(name, type, bean, validator);
  }

  /**
   * @param <E> the generic type of the {@link Link#getTarget() linked} {@link EntityBean}.
   * @param beanClass the class reflecting the linked {@link EntityBean}.
   * @return the {@link GenericType} for an {@link Link} {@link Link#getTarget() pointing to} an {@link EntityBean} of
   *         the given {@link Class}.
   */
  public static <E extends EntityBean> GenericType<ObservableList<Link<E>>> createLinkType(Class<E> beanClass) {

    return createListType(LinkProperty.createLinkType(beanClass));
  }

}
