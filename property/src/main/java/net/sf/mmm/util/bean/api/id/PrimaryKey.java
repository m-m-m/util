/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.bean.api.id;

import javax.persistence.Id;

/**
 * This is a simple implementation of {@link Id}.
 *
 * @param <E> the generic type of the identified entity.
 *
 * @author hohwille
 * @since 8.0.0
 */
public class PrimaryKey<E> extends AbstractId<E> {

  private static final long serialVersionUID = 1L;

  private long version;

  /**
   * The constructor.
   */
  protected PrimaryKey() {
    super();
  }

  /**
   * The constructor.
   *
   * @param type - see {@link #getType()}.
   * @param id - see {@link #getId()}.
   * @param version - see {@link #getVersion()}.
   */
  public PrimaryKey(Class<E> type, long id, long version) {
    super(type, id);
    this.version = version;
  }

  @Override
  public long getVersion() {

    return this.version;
  }

  /**
   * @param <E> the generic type of the identified entity.
   * @param type - see {@link #getType()}.
   * @param id - see {@link #getId()}.
   * @param version - see {@link #getVersion()}.
   * @return a new instance of {@link PrimaryKey}.
   */
  public static <E> PrimaryKey<E> valueOf(Class<E> type, long id, long version) {

    return new PrimaryKey<>(type, id, version);
  }

}
