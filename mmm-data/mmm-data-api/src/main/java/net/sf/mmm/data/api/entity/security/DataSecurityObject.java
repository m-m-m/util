/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.api.entity.security;

import net.sf.mmm.data.api.DataObject;
import net.sf.mmm.data.api.reflection.DataClassAnnotation;

/**
 * This is the abstract interface for a security object. This is any object related to the authentication and
 * authorization of the system.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@DataClassAnnotation(id = DataSecurityObject.CLASS_ID)
public abstract interface DataSecurityObject extends DataObject {

  /**
   * The {@link net.sf.mmm.data.api.datatype.DataId#getClassId() class-ID} of the
   * {@link net.sf.mmm.data.api.reflection.DataClass} reflecting this type.
   */
  int CLASS_ID = 20;

}
