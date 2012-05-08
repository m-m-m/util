/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.api;

import java.lang.reflect.Type;

import net.sf.mmm.util.NlsBundleUtilCore;

/**
 * This is the exception thrown if a cast failed. It is the equivalent of a {@link ClassCastException} but
 * gives more details about the problem. It can be used by generic converters and the like.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.2
 */
public class CastFailedException extends ReflectionException {

  /** UID for serialization. */
  private static final long serialVersionUID = -4116558423565287160L;

  /**
   * The constructor.
   * 
   * @param object is the object that should be cased.
   * @param destination is the type the <code>object</code> should be casted to.
   */
  public CastFailedException(Object object, Type destination) {

    this(object, getType(object), destination);
  }

  /**
   * @param object is an object.
   * @return {@link Object#getClass()} or <code>null</code>.
   */
  private static Type getType(Object object) {

    if (object == null) {
      return null;
    } else {
      return object.getClass();
    }
  }

  /**
   * The constructor.
   * 
   * @param object is the object that should be cased.
   * @param source is the type of the <code>object</code> that should be casted.
   * @param destination is the type the <code>object</code> should be casted to.
   */
  public CastFailedException(Object object, Type source, Type destination) {

    super(NlsBundleUtilCore.ERR_CAST, toMap(KEY_OBJECT, object, KEY_SOURCE, source, KEY_TARGET_TYPE, destination));
  }

  /**
   * The constructor.
   * 
   * @param nested is the {@link #getCause() cause} of this exception.
   * @param object is the object that should be cased.
   * @param source is the type of the <code>object</code> that should be casted.
   * @param destination is the type the <code>object</code> should be casted to.
   */
  public CastFailedException(Throwable nested, Object object, Type source, Type destination) {

    super(nested, NlsBundleUtilCore.ERR_CAST, toMap(KEY_OBJECT, object, KEY_SOURCE, source, KEY_TARGET_TYPE,
        destination));
  }

}
