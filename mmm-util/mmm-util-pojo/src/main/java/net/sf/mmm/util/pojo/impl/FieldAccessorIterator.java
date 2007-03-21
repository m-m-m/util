/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.impl;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Iterator;
import java.util.NoSuchElementException;

import net.sf.mmm.util.pojo.api.PojoPropertyAccessMode;
import net.sf.mmm.util.pojo.base.AbstractPojoPropertyAccessor;

/**
 * This class iterates all
 * {@link net.sf.mmm.util.pojo.api.PojoPropertyAccessor property-accessors} for
 * the methods of a given class.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class FieldAccessorIterator implements Iterator<AbstractPojoPropertyAccessor> {

  /** the current class */
  private Class<?> currentClass;
  
  /** the fields */
  private Field[] fields;

  /** the current index of {@link #fields} */
  private int index;

  /** the current field */
  private Field field;

  /** the next accessor */
  private AbstractPojoPropertyAccessor next;

  /**
   * The constructor
   * 
   * @param pojoClass
   *        is the class for which the property-accessors should be iterated.
   */
  public FieldAccessorIterator(Class<?> pojoClass) {

    super();
    this.currentClass = pojoClass;
    this.fields = pojoClass.getDeclaredFields();
    this.index = 0;
    this.field = null;
    stepNext();
  }

  /**
   * This method steps to the next accessor if available.
   */
  public void stepNext() {

    this.next = null;
    if (this.field == null) {
      while (this.index < this.fields.length) {
        Field currentField = this.fields[this.index++];
        int modifiers = currentField.getModifiers();
        if (!Modifier.isStatic(modifiers)) {
          this.field = currentField;
          this.field.setAccessible(true);
          this.next = new FieldPojoPropertyAccessor(this.field, PojoPropertyAccessMode.READ);
          return;
        }
      }
      if (this.currentClass != null) {
        this.currentClass = this.currentClass.getSuperclass();
        if (this.currentClass != null) {
          this.fields = this.currentClass.getDeclaredFields();
          this.index = 0;
          stepNext();
        }
      }
    } else {
      this.next = new FieldPojoPropertyAccessor(this.field, PojoPropertyAccessMode.WRITE);
      this.field = null;
    }
  }

  /**
   * {@inheritDoc}
   */
  public boolean hasNext() {

    return (this.next != null);
  }

  /**
   * {@inheritDoc}
   */
  public AbstractPojoPropertyAccessor next() {

    if (this.next == null) {
      throw new NoSuchElementException();
    }
    AbstractPojoPropertyAccessor accessor = this.next;
    stepNext();
    return accessor;
  }

  /**
   * {@inheritDoc}
   */
  public void remove() {

    throw new UnsupportedOperationException();
  }

}
