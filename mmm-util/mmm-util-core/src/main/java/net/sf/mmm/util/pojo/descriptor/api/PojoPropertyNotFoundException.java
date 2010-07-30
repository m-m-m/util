/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.descriptor.api;

import net.sf.mmm.util.NlsBundleUtilCore;
import net.sf.mmm.util.nls.api.NlsRuntimeException;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorMode;

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

    super(NlsBundleUtilCore.ERR_POJO_PROPERTY_NOT_FOUND, toMap(KEY_PROPERTY, propertyName,
        KEY_TYPE, pojoType));
  }

  /**
   * The constructor.
   * 
   * @param pojoType is the class reflecting the POJO.
   * @param propertyName is the name of the missing property.
   * @param mode is the access-mode for which the property is NOT available.
   */
  public PojoPropertyNotFoundException(Class<?> pojoType, String propertyName,
      PojoPropertyAccessorMode<?> mode) {

    super(NlsBundleUtilCore.ERR_POJO_PROPERTY_NOT_ACCESSABLE, toMap(KEY_PROPERTY, propertyName,
        KEY_TYPE, pojoType, KEY_MODE, mode));
  }

}
