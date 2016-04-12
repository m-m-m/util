/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.bean.base.link;

import net.sf.mmm.util.bean.api.EntityBean;

/**
 * Implementation of {@link AbstractLink} based on an already resolved {@link EntityBean}.
 *
 * @param <ID> the generic type of the {@link #getId() unique ID}.
 * @param <E> the generic type of the {@link #getTarget() linked} {@link EntityBean}.
 *
 * @author hohwille
 * @since 1.0.0
 */
public class EntityBeanLink<ID, E extends EntityBean<ID>> extends AbstractLink<ID, E> {

  private final E bean;

  /**
   * The constructor.
   *
   * @param bean - see {@link #getTarget()}.
   */
  protected EntityBeanLink(E bean) {
    super();
    this.bean = bean;
  }

  @Override
  public boolean isResolved() {

    return true;
  }

  @Override
  public ID getId() {

    return this.bean.getId();
  }

  @Override
  public E getTarget() {

    return this.bean;
  }

  /**
   * @param <ID> the generic type of the {@link #getId() unique ID}.
   * @param <E> the generic type of the {@link #getTarget() linked} {@link EntityBean}.
   * @param bean the {@link #getTarget() linked} {@link EntityBean}.
   * @return the new {@link EntityBeanLink} instance.
   */
  public static <ID, E extends EntityBean<ID>> EntityBeanLink<ID, E> valueOf(E bean) {

    if (bean == null) {
      return null;
    }
    return new EntityBeanLink<>(bean);
  }

}
