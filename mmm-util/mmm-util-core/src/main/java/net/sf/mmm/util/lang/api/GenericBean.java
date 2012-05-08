/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.api;

/**
 * This class is a simple java bean that has one generic property named {@link #getValue() value}. It can be
 * used for various purposes - e.g. to receive a value by passing it as argument to a method that already
 * returns something else.
 * 
 * @param <TYPE> is the generic type of the {@link #getValue() value}.
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.1
 */
public class GenericBean<TYPE> {

  /** @see #getValue() */
  private TYPE value;

  /**
   * The constructor.
   * 
   */
  public GenericBean() {

    super();
  }

  /**
   * The constructor.
   * 
   * @param value is the initial {@link #getValue() value}.
   */
  public GenericBean(TYPE value) {

    super();
    this.value = value;
  }

  /**
   * This method gets the value of this bean.
   * 
   * @return the value.
   */
  public TYPE getValue() {

    return this.value;
  }

  /**
   * This method sets the {@link #getValue() value}.
   * 
   * @param value is the value to set.
   */
  public void setValue(TYPE value) {

    this.value = value;
  }

}
