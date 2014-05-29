/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.base.sequence;

import net.sf.mmm.persistence.api.sequence.Sequence;
import net.sf.mmm.util.nls.api.NlsNullPointerException;

/**
 * This is the implementation of the {@link Sequence} interface as simple POJO. If you have a fixed set of
 * {@link Sequence}s you may want to use an {@link Enum} instead. For a more extendable approach you can
 * define constants with instances of this class.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 0.9.0
 */
public class SequenceImpl implements Sequence {

  /** @see #getSchema() */
  private final String schema;

  /** @see #getName() */
  private final String name;

  /**
   * The constructor.
   *
   * @param name - see {@link #getName()}.
   */
  public SequenceImpl(String name) {

    this(null, name);
  }

  /**
   * The constructor.
   *
   * @param schema - see {@link #getSchema()}.
   * @param name - see {@link #getName()}.
   */
  public SequenceImpl(String schema, String name) {

    super();
    if (name == null) {
      throw new NlsNullPointerException("name");
    }
    this.schema = schema;
    this.name = name;
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
  @Override
  public String toString() {

    StringBuilder sb = new StringBuilder();
    if (this.schema != null) {
      sb.append(this.schema);
      sb.append(".");
    }
    sb.append(this.name);
    return sb.toString();
  }

}
