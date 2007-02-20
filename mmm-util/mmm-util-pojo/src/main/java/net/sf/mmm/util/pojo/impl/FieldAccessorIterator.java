/* $Id$
 * Copyright The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.impl;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Iterator;
import java.util.NoSuchElementException;

import net.sf.mmm.util.pojo.api.PojoPropertyAccessMode;
import net.sf.mmm.util.pojo.api.PojoPropertyDescriptor;
import net.sf.mmm.util.pojo.base.AbstractPojoPropertyAccessor;

/**
 * This class iterates all
 * {@link net.sf.mmm.util.pojo.api.PojoPropertyAccessor property-accessors} for
 * the methods of a given class.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class FieldAccessorIterator implements Iterator<AbstractPojoPropertyAccessor> {

  /** the fields */
  private final Field[] fields;

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
          break;
        }
      }
    } else {
      this.next = new FieldPojoPropertyAccessor(this.field, PojoPropertyAccessMode.WRITE);
      this.field = null;
    }
  }

  /**
   * @see java.util.Iterator#hasNext()
   */
  public boolean hasNext() {

    return (this.next != null);
  }

  /**
   * @see java.util.Iterator#next()
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
   * @see java.util.Iterator#remove()
   */
  public void remove() {

    throw new UnsupportedOperationException();
  }

  /**
   * This method gets the according
   * {@link PojoPropertyDescriptor#getName() property-name} for the given
   * <code>methodName</code>.<br>
   * This is the un-capitalized substring of the <code>methodName</code> after
   * the prefix (given via <code>prefixLength</code>).
   * 
   * @param methodName
   *        is the {@link Method#getName() name} of the
   *        {@link net.sf.mmm.util.pojo.api.PojoPropertyAccessor#getAccessibleObject() accessor-method}.
   * @param prefixLength
   *        is the length of the method prefix (e.g. 3 for "get"/"set" or 2 for
   *        "is").
   * @return the requested property-name or <code>null</code> if NOT available
   *         <br> (<code>methodName</code>.{@link String#length() length()}
   *         &lt;= <code>prefixLength</code>).
   */
  private static String getPropertyName(String methodName, int prefixLength) {

    String methodSuffix = methodName.substring(prefixLength);
    if (methodSuffix.length() > 0) {
      StringBuffer sb = new StringBuffer(methodSuffix);
      sb.setCharAt(0, Character.toLowerCase(methodSuffix.charAt(0)));
      return sb.toString();
    }
    return null;
  }

}
