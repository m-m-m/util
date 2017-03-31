/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.data.base.link;

import java.util.function.Function;

import net.sf.mmm.util.data.api.entity.Entity;
import net.sf.mmm.util.data.api.id.Id;
import net.sf.mmm.util.data.api.link.Link;

/**
 * This is an implementation of {@link Link} that may initially only carry the {@link Id} of an entity and allows lazy
 * loading on the first call of {@link #getTarget()} via an externally given resolver {@link Function function}. This
 * approach is easier, less invasive and more lightweight as using proxy objects for entities.
 *
 * @param <E> the generic type of the {@link #getTarget() linked} {@link Entity}.
 *
 * @author hohwille
 * @since 8.5.0
 */
public class IdLink<E extends Entity> extends AbstractLink<E> {

  private final Id<E> id;

  private transient Function<Id<E>, E> resolver;

  private E entity;

  /**
   * The constructor (for serialization and strange frameworks).
   */
  protected IdLink() {
    super();
    this.id = null;
  }

  /**
   * The constructor.
   *
   * @param id - see {@link #getId()}.
   * @param resolver the {@link Function} to {@link #isResolved() resolve} the {@link #getTarget() link target} (the
   *        entity).
   */
  protected IdLink(Id<E> id, Function<Id<E>, E> resolver) {
    super();
    this.id = id;
    this.resolver = resolver;
  }

  @Override
  public Id<E> getId() {

    return this.id;
  }

  @Override
  public boolean isResolved() {

    return (this.entity != null);
  }

  @Override
  public E getTarget() {

    if (this.entity == null) {
      return resolve();
    }
    return this.entity;
  }

  /**
   * @return
   */
  private synchronized E resolve() {

    if (this.entity == null) {
      this.entity = this.resolver.apply(this.id);
      this.resolver = null;
    }
    return this.entity;
  }

  /**
   * @param <E> the generic type of the {@link #getTarget() linked} {@link Entity}.
   * @param id - see {@link #getId()}.
   * @param resolver the {@link Function} to {@link Function#apply(Object) resolve} the {@link Id}.
   * @return the {@link IdLink} instance.
   */
  public static <E extends Entity> IdLink<E> valueOf(Id<E> id, Function<Id<E>, E> resolver) {

    return new IdLink<>(id, resolver);
  }

}
