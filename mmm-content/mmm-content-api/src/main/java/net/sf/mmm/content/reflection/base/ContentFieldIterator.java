/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.reflection.base;

import java.util.Iterator;

import net.sf.mmm.content.api.ContentObject;
import net.sf.mmm.util.collection.base.AbstractIterator;

/**
 * This class is an enumeration for the fields of a class. It will enumerate the
 * fields defined by the class and the fields inherited from the sub-classes.
 * 
 * @param <CLASS> is the generic type of the reflected
 *        {@link net.sf.mmm.content.reflection.api.ContentClass#getJavaClass()
 *        class} .
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class ContentFieldIterator<CLASS extends ContentObject> extends
    AbstractIterator<AbstractContentField<? super CLASS, ?>> {

  /** the current class */
  private AbstractContentClass<? super CLASS> currentClass;

  /** the current own field enumeration */
  private Iterator<AbstractContentField<? super CLASS, ?>> currentIt;

  /**
   * The constructor.
   * 
   * @param contentClass is the class of that all
   *        {@link net.sf.mmm.content.reflection.api.ContentField fields} are to
   *        be iterated.
   */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  public ContentFieldIterator(AbstractContentClass<CLASS> contentClass) {

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
  protected AbstractContentField<? super CLASS, ?> findNext() {

    while (this.currentIt.hasNext()) {
      AbstractContentField<? super CLASS, ?> field = this.currentIt.next();
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
