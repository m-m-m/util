/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.pojo.path.base;

import java.lang.reflect.Type;

/**
 * This class represents a
 * {@link net.sf.mmm.util.reflect.pojo.path.api.PojoPath}. It contains the
 * internal logic to validate and parse the
 * {@link net.sf.mmm.util.reflect.pojo.path.api.PojoPath}. Additional it can
 * also hold the {@link #getPojo() result} of the evaluation and the
 * {@link #getPojoType() generic type}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class SimplePojoPath extends BasicPojoPath {

  /** @see #getParent() */
  private SimplePojoPath parent;

  /** @see #getPojoType() */
  private Type pojoType;

  /** @see #getPojo() */
  private Object pojo;

  /** @see #getPojoHashCode() */
  private int pojoHashCode;

  /**
   * The constructor.
   * 
   * @param pojoPath is the {@link #getPojoPath() path} to represent.
   */
  public SimplePojoPath(String pojoPath) {

    super(pojoPath);
  }

  /**
   * @return the parent
   */
  public SimplePojoPath getParent() {

    return this.parent;
  }

  /**
   * @param parent is the parent to set
   */
  public void setParent(SimplePojoPath parent) {

    this.parent = parent;
  }

  /**
   * This method gets the {@link Type type} of the {@link #getPojo() Pojo} this
   * {@link net.sf.mmm.util.reflect.pojo.path.api.PojoPath} is leading to.
   * 
   * @return the property-type or <code>null</code> if NOT set.
   */
  public Type getPojoType() {

    return this.pojoType;
  }

  /**
   * This method sets the {@link #getPojoType() pojo-type}.
   * 
   * @param propertyType is the property-type to set.
   */
  public void setPojoType(Type propertyType) {

    this.pojoType = propertyType;
  }

  /**
   * This method gets the {@link net.sf.mmm.util.reflect.pojo.api.Pojo} instance
   * this {@link net.sf.mmm.util.reflect.pojo.path.api.PojoPath} is leading to.
   * 
   * @return the {@link net.sf.mmm.util.reflect.pojo.api.Pojo} or
   *         <code>null</code>.
   */
  public Object getPojo() {

    return this.pojo;
  }

  /**
   * This method sets the {@link #getPojo() pojo-instance}.
   * 
   * @param pojo is the {@link #getPojo() pojo-instance}.
   */
  public void setPojo(Object pojo) {

    this.pojo = pojo;
  }

  /**
   * @return the pojoHashCode
   */
  public int getPojoHashCode() {

    return this.pojoHashCode;
  }

  /**
   * @param pojoHashCode is the pojoHashCode to set
   */
  public void setPojoHashCode(int pojoHashCode) {

    this.pojoHashCode = pojoHashCode;
  }

}
