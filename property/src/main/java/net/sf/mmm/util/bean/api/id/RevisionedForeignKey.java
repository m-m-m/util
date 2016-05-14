/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.bean.api.id;

/**
 * This is a simple implementation of {@link Id}.
 *
 * @param <E> the generic type of the identified entity.
 *
 * @author hohwille
 * @since 8.0.0
 */
public class RevisionedForeignKey<E> extends AbstractId<E> {

  private static final long serialVersionUID = 1L;

  private long version;

  /**
   * The constructor.
   */
  protected RevisionedForeignKey() {
    super();
  }

  /**
   * The constructor.
   *
   * @param type - see {@link #getType()}.
   * @param id - see {@link #getId()}.
   * @param version - see {@link #getVersion()}.
   */
  public RevisionedForeignKey(Class<E> type, long id, long version) {
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
   * @param id - see {@link #getId()}. May be {@code null}.
   * @param version - see {@link #getVersion()}. May be {@code null}.
   * @return a new instance of {@link RevisionedForeignKey}.
   */
  public static <E> RevisionedForeignKey<E> valueOf(Class<E> type, Long id, Long version) {

    if (id == null) {
      return null;
    } else {
      long v;
      if (version == null) {
        v = VERSION_LATEST;
      } else {
        v = version.longValue();
      }
      return valueOf(type, id.longValue(), v);
    }
  }

  /**
   * @param <E> the generic type of the identified entity.
   * @param type - see {@link #getType()}.
   * @param id - see {@link #getId()}.
   * @param version - see {@link #getVersion()}.
   * @return a new instance of {@link RevisionedForeignKey}.
   */
  public static <E> RevisionedForeignKey<E> valueOf(Class<E> type, long id, long version) {

    return new RevisionedForeignKey<>(type, id, version);
  }

  /**
   * <b>ATTENTION</b>:<br/>
   * This method shall not be used for persistence infrastructure where <em>modification counter</em> and
   * <em>revision</em> are different concepts such as for hibernate-envers. In such case use
   * {@link #valueOf(Id, long)} instead.
   *
   * @param <E> the generic type of the identified entity.
   * @param id the {@link Id} to convert. May be {@code null}.
   * @return the {@link Id} as {@link RevisionedForeignKey} or {@code null} if the given {@link Id} was
   *         {@code null}.
   */
  public static <E> RevisionedForeignKey<E> valueOf(Id<E> id) {

    if (id == null) {
      return null;
    } else if (id instanceof RevisionedForeignKey) {
      return (RevisionedForeignKey<E>) id;
    } else {
      return new RevisionedForeignKey<>(id.getType(), id.getId(), id.getVersion());
    }
  }

  /**
   * <b>ATTENTION</b>:<br/>
   * This method shall not be used for persistence infrastructure where <em>modification counter</em> and
   * <em>revision</em> are different concepts such as for hibernate-envers.
   *
   * @param <E> the generic type of the identified entity.
   * @param id the {@link Id} to convert. May be {@code null}.
   * @param revision the {@link #getVersion() version} acting as revision to link a historic revision. May be
   *        {@link #VERSION_LATEST}.
   * @return the {@link Id} as {@link RevisionedForeignKey} or {@code null} if the given {@link Id} was
   *         {@code null}.
   */
  public static <E> RevisionedForeignKey<E> valueOf(Id<E> id, long revision) {

    if (id == null) {
      return null;
    } else {
      return new RevisionedForeignKey<>(id.getType(), id.getId(), revision);
    }
  }

}
