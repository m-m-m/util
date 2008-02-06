/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect;

import net.sf.mmm.util.nls.AbstractResourceBundle;

/**
 * This class holds the internationalized messages for this module.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class NlsBundleUtilReflect extends AbstractResourceBundle {

  /** @see ReflectionUtil#toType(String, ClassResolver) */
  public static final String ERR_TYPE_ILLEGAL_WILDCARD = "Illegal sequence in wildcard type \"{0}\"!";

  /** @see AnnotationUtil#getMethodAnnotation(java.lang.reflect.Method, Class) */
  public static final String ERR_ANNOTATION_NOT_RUNTIME = "The given annotation \"{0}\" can NOT be resolved at runtime!";

  /** @see AnnotationUtil#getMethodAnnotation(java.lang.reflect.Method, Class) */
  public static final String ERR_ANNOTATION_NOT_FOR_TARGET = "The given annotation \"{0}\" can NOT annotate the target \"{1}\"!";

  /** @see CollectionUtil#create(Class) */
  public static final String ERR_UNKNOWN_COLLECTION_INTERFACE = "Unknown collection interface \"{0}\"!";

  /** @see InstantiationFailedException */
  public static final String ERR_INSTANTIATION_FAILED = "Failed to create an instance of \"{0}\"!";

}
