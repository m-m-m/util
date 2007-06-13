/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.api;

import net.sf.mmm.nls.base.NlsRuntimeException;
import net.sf.mmm.util.pojo.NlsBundlePojo;

/**
 * A {@link PojoPropertyNotFoundException} is thrown if a property should be
 * accessed that does NOT exist (was NOT found) or can NOT be accessed in the
 * intended way.
 * 
 * @see PojoDescriptor#getProperty(Object, String)
 * @see PojoDescriptor#setProperty(Object, String, Object)
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class PojoPropertyNotFoundException extends NlsRuntimeException {

  /** UID for serialization. */
  private static final long serialVersionUID = -7713121978429674081L;

  /**
   * The constructor.
   * 
   * @param pojoType is the class reflecting the POJO.
   * @param propertyName is the name of the missing property.
   */
  public PojoPropertyNotFoundException(Class<?> pojoType, String propertyName) {

    super(NlsBundlePojo.ERR_PROPERTY_NOT_FOUND, propertyName, pojoType);
  }

  /**
   * The constructor.
   * 
   * TODO: own exception?
   * 
   * @param pojoType is the class reflecting the POJO.
   * @param propertyName is the name of the missing property.
   * @param mode is the access-mode for which the property is NOT available.
   */
  public PojoPropertyNotFoundException(Class<?> pojoType, String propertyName,
      PojoPropertyAccessMode mode) {

    super(NlsBundlePojo.ERR_PROPERTY_NOT_ACCESSABLE, propertyName, pojoType, mode);
  }

}
