/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.pojo.api.attribute;

/**
 * This is the interface for an object that is related to a
 * {@link net.sf.mmm.util.reflect.pojo.api.PojoDescriptor POJO} and therefore
 * contains the {@link #getPojoType() POJO-type}.
 * 
 * @param <P> is the {@link #getPojoType() POJO-type}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract interface PojoAttributeType<P> {

  /**
   * This method gets the type reflecting the
   * {@link net.sf.mmm.util.reflect.pojo.api.PojoDescriptor POJO} represented by
   * this descriptor.
   * 
   * @return the type of the according POJO.
   */
  Class<P> getPojoType();

}
