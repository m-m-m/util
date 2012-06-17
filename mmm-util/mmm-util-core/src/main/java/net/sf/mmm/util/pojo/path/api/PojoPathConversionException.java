/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.path.api;

import java.lang.reflect.Type;

import net.sf.mmm.util.NlsBundleUtilCoreRoot;

/**
 * A {@link PojoPathConversionException} is thrown if a {@link net.sf.mmm.util.pojo.api.Pojo} has the wrong
 * type and could NOT be converted to the required type.
 * 
 * @see PojoPathNavigator#set(Object, String, PojoPathMode, PojoPathContext, Object)
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.0
 */
public class PojoPathConversionException extends PojoPathException {

  /** UID for serialization. */
  private static final long serialVersionUID = -577255365330996887L;

  /**
   * The constructor.
   * 
   * @param pojoPath is the {@link PojoPath} pointing to the {@link PojoPath#getSegment() segment} that could
   *        NOT be converted.
   * @param pojoType is the type of the {@link net.sf.mmm.util.pojo.api.Pojo}.
   * @param targetType is the required type for the given <code>pojoPath</code>.
   */
  public PojoPathConversionException(String pojoPath, Type pojoType, Type targetType) {

    this(null, pojoPath, pojoType, targetType);
  }

  /**
   * The constructor.
   * 
   * @param nested is the {@link #getCause() cause} of this exception.
   * @param pojoPath is the {@link PojoPath} pointing to the {@link PojoPath#getSegment() segment} that could
   *        NOT be converted.
   * @param pojoType is the type of the {@link net.sf.mmm.util.pojo.api.Pojo}.
   * @param targetType is the required type for the given <code>pojoPath</code>.
   */
  public PojoPathConversionException(Throwable nested, String pojoPath, Type pojoType, Object targetType) {

    super(nested, createBundle(NlsBundleUtilCoreRoot.class)
        .errorPojoPathConversion(pojoPath, pojoType, targetType));
  }

}
