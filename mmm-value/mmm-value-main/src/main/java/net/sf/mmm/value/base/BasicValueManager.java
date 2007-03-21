/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.value.base;

/**
 * This is the enhanced base implementation of the
 * {@link net.sf.mmm.value.api.ValueManager} interface.
 * 
 * @param <V>
 *        is the templated type of the managed value type.
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class BasicValueManager<V> extends AbstractValueManager<V> {

  /** the name of the value manager */
  protected final String name;

  /** the class of the managed value */
  protected final Class<V> valueType;

  /**
   * The constructor.
   * 
   * @param valueClass
   *        is the {@link #getValueClass() implementation} of the managed value
   *        type.
   */
  public BasicValueManager(Class<V> valueClass) {

    this(valueClass, valueClass.getSimpleName());
  }

  /**
   * The constructor.
   * 
   * @param valueClass
   *        is the {@link #getValueClass() implementation} of the managed value
   *        type.
   * @param typeName
   *        is the {@link #getName() "logical name"} of the managed value
   *        type.
   */
  public BasicValueManager(Class<V> valueClass, String typeName) {

    super();
    this.valueType = valueClass;
    this.name = typeName;
  }

  /**
   * @see net.sf.mmm.value.api.ValueManager#getValueClass()
   */
  public Class<V> getValueClass() {

    return this.valueType;
  }

  /**
   * @see net.sf.mmm.value.api.ValueManager#getName()
   */
  public String getName() {

    return this.name;
  }

}
