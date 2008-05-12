/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.descriptor.api.attribute;

import net.sf.mmm.util.reflect.GenericType;

/**
 * This is the interface for an object that is related to a
 * {@link net.sf.mmm.util.pojo.api.Pojo} and therefore contains the
 * {@link #getPojoClass() pojo-type}.
 * 
 * @param <POJO> is the {@link #getPojoClass() pojo-type}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract interface PojoAttributeType<POJO> {

  /**
   * This method gets the type reflecting the
   * {@link net.sf.mmm.util.pojo.api.Pojo} this object is related to.
   * 
   * @return the type of the according {@link net.sf.mmm.util.pojo.api.Pojo}.
   */
  Class<POJO> getPojoClass();

  /**
   * This method gets the {@link GenericType} reflecting the
   * {@link net.sf.mmm.util.pojo.api.Pojo} this object is related to.
   * 
   * @return the type of the according {@link net.sf.mmm.util.pojo.api.Pojo}.
   */
  GenericType getPojoType();

}
