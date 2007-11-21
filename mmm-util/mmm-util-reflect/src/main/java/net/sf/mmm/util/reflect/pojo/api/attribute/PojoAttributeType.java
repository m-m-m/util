/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.pojo.api.attribute;

/**
 * TODO: this class ...
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface PojoAttributeType<P> {

  /**
   * This method gets the type reflecting the POJO represented by this
   * descriptor.
   * 
   * @return the type of the according POJO.
   */
  Class<P> getPojoType();

}
