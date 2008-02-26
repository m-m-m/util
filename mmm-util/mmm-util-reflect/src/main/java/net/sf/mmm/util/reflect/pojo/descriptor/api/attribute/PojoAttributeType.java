/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.pojo.descriptor.api.attribute;

/**
 * This is the interface for an object that is related to a
 * {@link net.sf.mmm.util.reflect.pojo.Pojo} and therefore contains the
 * {@link #getPojoType() pojo-type}.
 * 
 * @param <POJO> is the {@link #getPojoType() pojo-type}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract interface PojoAttributeType<POJO> {

  /**
   * This method gets the type reflecting the
   * {@link net.sf.mmm.util.reflect.pojo.Pojo} this object is related to.
   * 
   * @return the type of the according {@link net.sf.mmm.util.reflect.pojo.Pojo}.
   */
  Class<POJO> getPojoType();

}
