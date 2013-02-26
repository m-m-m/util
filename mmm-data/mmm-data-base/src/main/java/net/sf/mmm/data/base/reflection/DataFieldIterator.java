/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.base.reflection;

import java.util.Iterator;

import net.sf.mmm.data.api.DataObject;
import net.sf.mmm.util.collection.base.AbstractIterator;

/**
 * This class is an {@link Iterator} for the fields of a class. It will enumerate the fields defined by the
 * class and the fields inherited from the sub-classes.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class DataFieldIterator extends AbstractIterator<AbstractDataField<? extends DataObject, ?>> {

  /** the current class */
  private AbstractDataClass<? extends DataObject> currentClass;

  /** the current own field enumeration */
  private Iterator<AbstractDataField<? extends DataObject, ?>> currentIt;

  /**
   * The constructor.
   * 
   * @param contentClass is the class of that all {@link net.sf.mmm.data.api.reflection.DataField fields} are
   *        to be iterated.
   */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  public DataFieldIterator(AbstractDataClass<? extends DataObject> contentClass) {

    super();
    this.currentClass = contentClass;
    // Generics sometimes suck
    Iterator it = contentClass.getDeclaredFields().iterator();
    this.currentIt = it;
    findFirst();
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  @Override
  protected AbstractDataField<? extends DataObject, ?> findNext() {

    while (this.currentIt.hasNext()) {
      AbstractDataField<? extends DataObject, ?> field = this.currentIt.next();
      if (field.getSuperField() == null) {
        return field;
      }
    }
    if (this.currentClass != null) {
      this.currentClass = this.currentClass.getSuperClass();
      // Generics sometimes suck
      Iterator it = this.currentClass.getDeclaredFields().iterator();
      this.currentIt = it;
      return findNext();
    }
    return null;
  }

}
