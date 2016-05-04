/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.base.datatype.descriptor;

import net.sf.mmm.util.lang.api.DatatypeSegmentDescriptor;

/**
 * This is the implementation of {@link DatatypeSegmentDescriptor}.
 *
 * @param <T> is the generic type of the {@link net.sf.mmm.util.lang.api.Datatype} owning this segment.
 * @param <V> is the generic {@link #getType() type} of the {@link #getSegment(Object) segment value}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 6.0.0
 */
public abstract class AbstractDatatypeSegmentDescriptor<T, V> implements DatatypeSegmentDescriptor<T, V> {

  private final String name;

  private final Class<V> type;

  private final boolean optional;

  /**
   * The constructor.
   *
   * @param name - see {@link #getName()}.
   * @param type - see {@link #getType()}.
   */
  public AbstractDatatypeSegmentDescriptor(String name, Class<V> type) {

    this(name, type, false);
  }

  /**
   * The constructor.
   *
   * @param name - see {@link #getName()}.
   * @param type - see {@link #getType()}.
   * @param optional - see {@link #isOptional()}.
   */
  public AbstractDatatypeSegmentDescriptor(String name, Class<V> type, boolean optional) {

    super();

    this.name = name;
    this.type = type;
    this.optional = optional;
  }

  @Override
  public V getSegment(T datatype) {

    if (datatype == null) {
      return null;
    }
    return doGetSegment(datatype);
  }

  /**
   * Called from {@link #getSegment(Object)} after validation (null-check).
   *
   * @param datatype is the datatype instance. May not be null.
   * @return the {@link #getSegment(Object) segment value}.
   */
  protected abstract V doGetSegment(T datatype);

  @Override
  public String getName() {

    return this.name;
  }

  @Override
  public Class<V> getType() {

    return this.type;
  }

  @Override
  public boolean isOptional() {

    return this.optional;
  }

  @Override
  public String toString() {

    return "DatatypeSegmentDescriptor for " + this.name + "@" + this.type.getName();
  }

}
