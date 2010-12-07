/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.descriptor.impl.dummy;

/**
 * This is a dummy pojo for testing.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class GenericPojo<E> {

  private E element;

  public E getElement() {

    return this.element;
  }

  public void setElement(E element) {

    this.element = element;
  }
}
