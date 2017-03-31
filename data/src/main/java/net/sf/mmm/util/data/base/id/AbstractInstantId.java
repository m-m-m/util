/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.data.base.id;

import java.time.Instant;

import net.sf.mmm.util.data.api.id.AbstractId;
import net.sf.mmm.util.data.api.id.Id;

/**
 * An abstract base implementation of {@link Id} using {@link Instant} as type for the {@link #getVersion() version}.
 *
 * @param <E> the generic type of the identified entity.
 * @param <I> the generic type of the {@link #getId() ID}.
 *
 * @author hohwille
 * @since 8.4.0
 */
public abstract class AbstractInstantId<E, I> extends AbstractId<E, I, Instant> {

  private final Instant version;

  /**
   * The constructor.
   *
   * @param type - see {@link #getType()}.
   * @param version - see {@link #getVersion()}.
   */
  public AbstractInstantId(Class<E> type, Instant version) {
    super(type);
    this.version = version;
  }

  @Override
  public Instant getVersion() {

    return this.version;
  }

}
