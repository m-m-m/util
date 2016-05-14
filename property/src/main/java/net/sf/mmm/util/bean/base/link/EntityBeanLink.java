/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.bean.base.link;

import java.util.Objects;

import net.sf.mmm.util.bean.api.entity.EntityBean;
import net.sf.mmm.util.bean.api.id.Id;

/**
 * Implementation of {@link AbstractLink} based on an already resolved {@link EntityBean}.
 *
 * @param <E> the generic type of the {@link #getTarget() linked} {@link EntityBean}.
 *
 * @author hohwille
 * @since 8.0.0
 */
public class EntityBeanLink<E extends EntityBean> extends AbstractLink<E> {

  private final E bean;

  /**
   * The constructor (for serialization and strange frameworks).
   */
  protected EntityBeanLink() {
    super();
    this.bean = null;
  }

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

  @SuppressWarnings("unchecked")
  @Override
  public Id<E> getId() {

    return (Id<E>) this.bean.getId();
  }

  @Override
  public E getTarget() {

    return this.bean;
  }

  @Override
  public boolean equals(Object obj) {

    if (this == obj) {
      return true;
    }
    if (!super.equals(obj)) {
      return false;
    }
    EntityBeanLink<?> other = (EntityBeanLink<?>) obj;
    if (!Objects.equals(this.bean, other.bean)) {
      return false;
    }
    return true;
  }

  /**
   * @param <E> the generic type of the {@link #getTarget() linked} {@link EntityBean}.
   * @param bean the {@link #getTarget() linked} {@link EntityBean}.
   * @return the new {@link EntityBeanLink} instance.
   */
  public static <E extends EntityBean> EntityBeanLink<E> valueOf(E bean) {

    if (bean == null) {
      return null;
    }
    return new EntityBeanLink<>(bean);
  }

}
