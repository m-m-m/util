/* $Id$
 * Copyright The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.api;


/**
 * This enum contains the available types of a {@link PojoPropertyAccessor}.
 * 
 * @see PojoPropertyAccessor#getAccessMode()
 * @see PojoPropertyDescriptor#getAccessor(PojoPropertyAccessMode)
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public enum PojoPropertyAccessMode {

  /** the mode for a read-accessor (e.g. via a getter method) */
  READ,
  
  /** the mode for a write-accessor (e.g. via a setter method) */
  WRITE,
  
  /** the mode for an add-accessor */
  ADD,
  
}
