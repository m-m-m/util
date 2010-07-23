/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.path.api;

import java.lang.reflect.Type;

import net.sf.mmm.util.NlsBundleUtilCore;

/**
 * A {@link PojoPathConversionException} is thrown if a
 * {@link net.sf.mmm.util.pojo.api.Pojo} has the wrong type and could NOT be
 * converted to the required type.
 * 
 * @see PojoPathNavigator#set(Object, String, PojoPathMode, PojoPathContext,
 *      Object)
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class PojoPathConversionException extends PojoPathException {

  /** UID for serialization. */
  private static final long serialVersionUID = -577255365330996887L;

  /** Key for the NLS message. */
  private static final String KEY_TARGET_TYPE = "targetType";

  /**
   * The constructor.
   * 
   * @param pojoPath is the {@link PojoPath} pointing to the
   *        {@link PojoPath#getSegment() segment} that could NOT be converted.
   * @param pojoClass is the type of the {@link net.sf.mmm.util.pojo.api.Pojo}.
   * @param targetType is the required type for the given <code>pojoPath</code>.
   */
  public PojoPathConversionException(String pojoPath, Type pojoClass, Type targetType) {

    super(NlsBundleUtilCore.ERR_POJO_PATH_CONVERSION, toMap(KEY_PATH, pojoPath, KEY_TYPE, pojoClass,
        KEY_TARGET_TYPE, targetType));
  }

  /**
   * The constructor.
   * 
   * @param nested is the {@link #getCause() cause} of this exception.
   * @param pojoPath is the {@link PojoPath} pointing to the
   *        {@link PojoPath#getSegment() segment} that could NOT be converted.
   * @param pojoClass is the type of the {@link net.sf.mmm.util.pojo.api.Pojo}.
   * @param targetType is the required type for the given <code>pojoPath</code>.
   */
  public PojoPathConversionException(Throwable nested, String pojoPath, Class<?> pojoClass,
      Object targetType) {

    super(nested, NlsBundleUtilCore.ERR_POJO_PATH_CONVERSION, toMap(KEY_PATH, pojoPath, KEY_TYPE,
        pojoClass, KEY_TARGET_TYPE, targetType));
  }

}
