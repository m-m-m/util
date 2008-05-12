/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Map;

/**
 * This is an implementation of the {@link GenericType} interface for a simple
 * {@link Class}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class SimpleGenericType extends AbstractGenericType {

  /** The {@link GenericType} for {@link Object}. */
  public static final GenericType TYPE_OBJECT = new SimpleGenericType(Object.class);

  /** The {@link GenericType} for <code>void</code>. */
  public static final GenericType TYPE_VOID = new SimpleGenericType(void.class);

  /** The {@link GenericType} for <code>int</code>. */
  public static final GenericType TYPE_INT = new SimpleGenericType(int.class);

  /** @see #getType() */
  private final Class<?> type;

  /** @see #getComponentType() */
  private GenericType componentType;

  /**
   * The constructor.
   * 
   * @param type is the {@link #getType() type} to represent.
   */
  public SimpleGenericType(Class<?> type) {

    super();
    this.type = type;
    if (type.isArray()) {
      this.componentType = new SimpleGenericType(type.getComponentType());
    } else if (Collection.class.isAssignableFrom(type) || Map.class.isAssignableFrom(type)) {
      this.componentType = TYPE_OBJECT;
    } else {
      this.componentType = null;
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public GenericType getDefiningType() {

    return null;
  }

  /**
   * {@inheritDoc}
   */
  public GenericType getComponentType() {

    return this.componentType;
  }

  /**
   * {@inheritDoc}
   */
  public Type getType() {

    return this.type;
  }

  /**
   * {@inheritDoc}
   */
  public Class<?> getLowerBound() {

    return this.type;
  }

  /**
   * {@inheritDoc}
   */
  public Class<?> getUpperBound() {

    return this.type;
  }

  /**
   * {@inheritDoc}
   */
  public GenericType getTypeArgument(int index) {

    throw new IndexOutOfBoundsException(Integer.toString(index));
  }

  /**
   * {@inheritDoc}
   */
  public int getTypeArgumentCount() {

    return 0;
  }

}
