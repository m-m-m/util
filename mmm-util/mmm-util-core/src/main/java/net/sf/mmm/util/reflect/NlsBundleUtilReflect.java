/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect;

import net.sf.mmm.util.nls.AbstractResourceBundle;
import net.sf.mmm.util.reflect.api.AccessFailedException;
import net.sf.mmm.util.reflect.api.InstantiationFailedException;
import net.sf.mmm.util.reflect.api.InvocationFailedException;

/**
 * This class holds the internationalized messages for this module.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class NlsBundleUtilReflect extends AbstractResourceBundle {

  /**
   * @see ReflectionUtil#toType(String,
   *      net.sf.mmm.util.reflect.api.ClassResolver)
   */
  public static final String ERR_TYPE_ILLEGAL_WILDCARD = "Illegal sequence in wildcard type \"{0}\"!";

  /** @see AnnotationUtil#getMethodAnnotation(java.lang.reflect.Method, Class) */
  public static final String ERR_ANNOTATION_NOT_RUNTIME = "The given annotation "
      + "\"{0}\" can NOT be resolved at runtime!";

  /** @see AnnotationUtil#getMethodAnnotation(java.lang.reflect.Method, Class) */
  public static final String ERR_ANNOTATION_NOT_FOR_TARGET = "The given annotation "
      + "\"{0}\" can NOT annotate the target \"{1}\"!";

  /** @see CollectionReflectionUtil#create(Class) */
  public static final String ERR_INCREASE_EXCEEDS_MAX_GROWTH = "Can not increase "
      + "size of array or list by \"{0}\", because limit is \"{1}\"!";

  /** @see CollectionReflectionUtil#create(Class) */
  public static final String ERR_UNKNOWN_COLLECTION_INTERFACE = "Unknown collection interface \"{0}\"!";

  /** @see InstantiationFailedException */
  public static final String ERR_INSTANTIATION_FAILED = "Failed to create an instance of \"{0}\"!";

  /** @see InvocationFailedException */
  public static final String ERR_INVOCATION_FAILED = "Reflective invocation failed!";

  /** @see InvocationFailedException */
  public static final String ERR_INVOCATION_FAILED_ON = "Reflective invocation of \"{0}\" on \"{1}\" failed!";

  /** @see AccessFailedException */
  public static final String ERR_ACCESS_FAILED = "Reflective access for \"{0}\" failed!";

}
