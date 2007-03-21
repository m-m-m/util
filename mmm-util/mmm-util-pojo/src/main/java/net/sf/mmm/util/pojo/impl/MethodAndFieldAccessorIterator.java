/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.impl;

import java.lang.reflect.Modifier;
import java.util.Iterator;

import net.sf.mmm.util.pojo.base.AbstractPojoPropertyAccessor;

/**
 * This class iterates all
 * {@link net.sf.mmm.util.pojo.api.PojoPropertyAccessor property-accessors} for
 * the methods and fields of a given class.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class MethodAndFieldAccessorIterator implements Iterator<AbstractPojoPropertyAccessor> {

  /** the method accessor iterator */
  private final Iterator<AbstractPojoPropertyAccessor> methodIterator;

  /** the field accessor iterator */
  private final Iterator<AbstractPojoPropertyAccessor> fieldIterator;

  /**
   * The constructor
   * 
   * @param pojoClass
   *        is the class for which the property-accessors should be iterated.
   * @param onlyPublic
   *        if <code>true</code> only {@link Modifier#isPublic(int) public}
   *        methods will be considered for when searching for
   *        property-accessors, else if <code>false</code> all implemented
   *        methods are introspected.
   */
  public MethodAndFieldAccessorIterator(Class<?> pojoClass, boolean onlyPublic) {

    super();
    this.methodIterator = new MethodAccessorIterator(pojoClass, onlyPublic);
    this.fieldIterator = new FieldAccessorIterator(pojoClass);
  }

  /**
   * {@inheritDoc}
   */
  public boolean hasNext() {

    return (this.methodIterator.hasNext() || this.fieldIterator.hasNext());
  }

  /**
   * {@inheritDoc}
   */
  public AbstractPojoPropertyAccessor next() {

    if (this.methodIterator.hasNext()) {
      return this.methodIterator.next();
    }
    return this.fieldIterator.next();
  }

  /**
   * {@inheritDoc}
   */
  public void remove() {

    throw new UnsupportedOperationException();
  }

}
