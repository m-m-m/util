/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.data.base.link;

import java.util.Objects;

import net.sf.mmm.util.data.api.entity.Entity;
import net.sf.mmm.util.data.api.id.Id;

/**
 * Implementation of {@link AbstractLink} based on an already resolved {@link Entity}.
 *
 * @param <E> the generic type of the {@link #getTarget() linked} {@link Entity}.
 *
 * @author hohwille
 * @since 8.5.0
 */
public class EntityBeanLink<E extends Entity> extends AbstractLink<E> {

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
   * @param <E> the generic type of the {@link #getTarget() linked} {@link Entity}.
   * @param bean the {@link #getTarget() linked} {@link Entity}.
   * @return the new {@link EntityBeanLink} instance.
   */
  public static <E extends Entity> EntityBeanLink<E> valueOf(E bean) {

    if (bean == null) {
      return null;
    }
    return new EntityBeanLink<>(bean);
  }

}
