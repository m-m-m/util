/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.impl;

import java.util.Iterator;

import net.sf.mmm.util.pojo.base.AbstractPojoPropertyAccessor;
import net.sf.mmm.util.pojo.base.PojoPropertyIntrospector;

/**
 * This is an implementation of the {@link PojoPropertyIntrospector} that
 * introspects the methods to find property-accessors (getters, setters, etc.).
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class MethodPropertyIntrospector implements PojoPropertyIntrospector {

  /** @see MethodAccessorIterator#MethodAccessorIterator(Class, boolean) */
  private final boolean publicOnly;

  /**
   * The constructor
   * 
   * @param onlyPublic
   *        if <code>true</code> only public methods will be considered for
   *        when searching for property-accessors, else if <code>false</code>
   *        all implemented methods are introspected.
   */
  public MethodPropertyIntrospector(boolean onlyPublic) {

    super();
    this.publicOnly = onlyPublic;
  }

  /**
   * {@inheritDoc}
   */
  public Iterator<AbstractPojoPropertyAccessor> findAccessors(Class<?> pojoType) {

    return new MethodAccessorIterator(pojoType, this.publicOnly);
  }

}
