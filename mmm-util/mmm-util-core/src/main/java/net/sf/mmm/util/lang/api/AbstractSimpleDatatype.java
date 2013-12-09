/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.api;

/**
 * This is the abstract base implementation of a simple {@link Datatype}.
 * 
 * @param <V> is the generic type of the {@link #getValue() value}.
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.0.0
 */
public abstract class AbstractSimpleDatatype<V> extends AbstractSimpleDatatypeBase<V> {

  /** UID for serialization. */
  private static final long serialVersionUID = -7672725330000849564L;

  /** @see #getValue() */
  private V value;

  /**
   * The constructor for de-serialization.
   */
  protected AbstractSimpleDatatype() {

    super();
  }

  /**
   * The constructor.
   * 
   * @param value is the actual {@link #getValue() value}.
   */
  public AbstractSimpleDatatype(V value) {

    super();
    this.value = value;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public V getValue() {

    return this.value;
  }

}
