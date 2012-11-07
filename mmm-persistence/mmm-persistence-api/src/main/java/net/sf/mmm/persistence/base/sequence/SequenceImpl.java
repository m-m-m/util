/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.base.sequence;

import net.sf.mmm.persistence.api.sequence.Sequence;
import net.sf.mmm.util.nls.api.NlsNullPointerException;

/**
 * This is the implementation of the {@link Sequence} interface as simple POJO.
 * If you have a fixed set of {@link Sequence}s you may want to use an
 * {@link Enum} instead. For a more extendable approach you can define constants
 * with instances of this class.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class SequenceImpl implements Sequence {

  /** @see #getSchema() */
  private final String schema;

  /** @see #getName() */
  private final String name;

  /** @see #getMinimumValue() */
  private final long minimumValue;

  /** @see #getMaximumValue() */
  private final Long maximumValue;

  /**
   * The constructor.
   * 
   * @param name - see {@link #getName()}.
   */
  public SequenceImpl(String name) {

    this(null, name, 0, null);
  }

  /**
   * The constructor.
   * 
   * @param name - see {@link #getName()}.
   * @param minimumValue - see {@link #getMinimumValue()}.
   */
  public SequenceImpl(String name, long minimumValue) {

    this(null, name, minimumValue, null);
  }

  /**
   * The constructor.
   * 
   * @param schema - see {@link #getSchema()}.
   * @param name - see {@link #getName()}.
   */
  public SequenceImpl(String schema, String name) {

    this(schema, name, 0, null);
  }

  /**
   * The constructor.
   * 
   * @param schema - see {@link #getSchema()}.
   * @param name - see {@link #getName()}.
   * @param minimumValue - see {@link #getMinimumValue()}.
   */
  public SequenceImpl(String schema, String name, long minimumValue) {

    this(schema, name, minimumValue, null);
  }

  /**
   * The constructor.
   * 
   * @param schema - see {@link #getSchema()}.
   * @param name - see {@link #getName()}.
   * @param minimumValue - see {@link #getMinimumValue()}.
   * @param maximumValue - see {@link #getMaximumValue()}
   */
  public SequenceImpl(String schema, String name, long minimumValue, Long maximumValue) {

    super();
    if (name == null) {
      throw new NlsNullPointerException("name");
    }
    this.schema = schema;
    this.name = name;
    this.minimumValue = minimumValue;
    this.maximumValue = maximumValue;
  }

  /**
   * {@inheritDoc}
   */
  public String getSchema() {

    return this.schema;
  }

  /**
   * {@inheritDoc}
   */
  public String getName() {

    return this.name;
  }

  /**
   * {@inheritDoc}
   */
  public long getMinimumValue() {

    return this.minimumValue;
  }

  /**
   * {@inheritDoc}
   */
  public Long getMaximumValue() {

    return this.maximumValue;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    StringBuilder sb = new StringBuilder();
    if (this.schema != null) {
      sb.append(this.schema);
      sb.append(".");
    }
    sb.append(this.name);
    sb.append('[');
    sb.append(this.minimumValue);
    sb.append('-');
    if (this.maximumValue == null) {
      sb.append(Long.MAX_VALUE);
    } else {
      sb.append(this.maximumValue);
    }
    sb.append(']');
    return sb.toString();
  }

}
