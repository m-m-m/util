/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util;

import net.sf.mmm.util.nls.base.AbstractResourceBundle;

/**
 * This class holds the internationalized messages for this module.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.2
 */
public class NlsBundleUtilCore extends AbstractResourceBundle {

  /** @see net.sf.mmm.util.value.api.WrongValueTypeException */
  public static final String ERR_VALUE_WRONG_TYPE = "The value \"{0}\" with the "
      + "type \"{1}\" can NOT be converted to the requested type \"{2}\"!";

  /** @see net.sf.mmm.util.value.api.WrongValueTypeException */
  public static final String ERR_VALUE_WRONG_TYPE_SOURCE = "The value \"{0}\" "
      + "from \"{3}\" with the type \"{1}\" can NOT be converted to the requested type \"{2}\"!";

  /** @see net.sf.mmm.util.value.api.ValueNotSetException */
  public static final String ERR_VALUE_NOT_SET = "The value \"{0}\" is not set!";

  /** @see net.sf.mmm.util.value.api.ValueParseGenericException */
  public static final String ERR_PARSE = "Failed to parse value \"{0}\" as value"
      + " of the type \"{1}\"!";

  /** @see net.sf.mmm.util.value.api.ValueParseGenericException */
  public static final String ERR_PARSE_SOURCE = "Failed to parse value \"{0}\" from "
      + "\"{2}\" as value of the type \"{1}\"!";

  /** @see net.sf.mmm.util.value.api.ValueOutOfRangeException */
  public static final String ERR_VALUE_OUT_OF_RANGE = "The value \"{0}\" "
      + "is not in the expected range of \"[{1}-{2}]\"!";

  /** @see net.sf.mmm.util.value.api.ValueOutOfRangeException */
  public static final String ERR_VALUE_OUT_OF_RANGE_SOURCE = "The value \"{0}\" from \"{3}\" "
      + "is not in the expected range of \"[{1}-{2}]\"!";

  /** @see net.sf.mmm.util.value.api.ValueConvertException */
  public static final String ERR_VALUE_CONVERT = "The value \"{0}\" could NOT be converted to \"{1}\"";

  /** @see net.sf.mmm.util.value.api.ValueConvertException */
  public static final String ERR_VALUE_CONVERT_SOURCE = "The value \"{0}\" from \"{2}\" could NOT be converted to \"{1}\"";

  /** @see net.sf.mmm.util.component.api.ResourceMissingException */
  public static final String ERR_RESOURCE_MISSING = "The required resource \"{0}\" is missing!";

  /** @see net.sf.mmm.util.component.api.AlreadyInitializedException */
  public static final String ERR_ALREADY_INITIALIZED = "The object is already initialized!";

  /** @see net.sf.mmm.util.component.api.NotInitializedException */
  public static final String ERR_NOT_INITIALIZED = "The object is NOT initialized!";

  /** @see net.sf.mmm.util.nls.api.NlsIllegalArgumentException */
  public static final String ERR_ILLEGAL_ARGUMENT = "The given argument \"{0}\" is illegal!";

  /** @see net.sf.mmm.util.date.api.IllegalDateFormatException */
  public static final String ERR_ILLEGAL_DATA_FORMAT = "Illegal date-format \"{0}\"!";

  /** @see net.sf.mmm.util.math.api.NumberConversionException */
  public static final String ERR_NUMBER_CONVERSION = "Can not convert number \"{0}\" to \"{1}\"!";

  /** @see net.sf.mmm.util.io.api.RuntimeIoException */
  public static final String ERR_IO = "An unexpected input/output error has ocurred!";

  /** @see net.sf.mmm.util.nls.api.NlsNullPointerException */
  public static final String ERR_ARGUMENT_NULL = "The argument \"{0}\" is null!";

  /** @see net.sf.mmm.util.nls.api.DuplicateObjectException */
  public static final String ERR_DUPLICATE_OBJECT = "Duplicate object \"{0}\"!";

  /** @see net.sf.mmm.util.nls.api.DuplicateObjectException */
  public static final String ERR_DUPLICATE_OBJECT_WITH_KEY = "Duplicate object \"{0}\" for key \"{1}\"!";

  /** @see net.sf.mmm.util.nls.api.ObjectNotFoundException */
  public static final String ERR_OBJECT_NOT_FOUND = "Could NOT find object \"{0}\"!";

  /** @see net.sf.mmm.util.nls.api.ObjectNotFoundException */
  public static final String ERR_OBJECT_NOT_FOUND_WITH_KEY = "Could NOT find object \"{0}\" for key \"{1}\"!";

  /** @see net.sf.mmm.util.nls.api.NlsIllegalStateException */
  public static final String ERR_ILLEGAL_STATE = "Illegal state!";

  /** @see net.sf.mmm.util.nls.api.IllegalCaseException */
  public static final String ERR_ILLEGAL_CASE = "The case \"{0}\" is NOT covered!";

  /** @see net.sf.mmm.util.resource.api.ResourceNotAvailableException */
  public static final String ERR_RESOURCE_NOT_AVAILABLE = "The resource \"{0}\" is not available in your classpath!";

  /** @see net.sf.mmm.util.resource.api.ResourceUriUndefinedException */
  public static final String ERR_RESOURCE_UNDEFINED_URI = "The resource URI \"{0}\" is undefined!";

  /** @see net.sf.mmm.util.nls.api.NlsUnsupportedOperationException */
  public static final String ERR_UNSUPPORTED_OPERATION = "An operation was invoked that is NOT supported!";

  /** @see net.sf.mmm.util.nls.api.NlsUnsupportedOperationException */
  public static final String ERR_UNSUPPORTED_OPERATION_WITH_NAME = "The operation \"{0}\" was invoked but is NOT supported!";

  /**
   * @see net.sf.mmm.util.reflect.base.ReflectionUtilImpl#toType(String,
   *      net.sf.mmm.util.reflect.api.ClassResolver)
   */
  public static final String ERR_TYPE_ILLEGAL_WILDCARD = "Illegal sequence in wildcard type \"{0}\"!";

  /**
   * @see net.sf.mmm.util.reflect.base.AnnotationUtilImpl#getMethodAnnotation(java.lang.reflect.Method,
   *      Class)
   */
  public static final String ERR_ANNOTATION_NOT_RUNTIME = "The given annotation "
      + "\"{0}\" can NOT be resolved at runtime!";

  /**
   * @see net.sf.mmm.util.reflect.base.AnnotationUtilImpl#getMethodAnnotation(java.lang.reflect.Method,
   *      Class)
   */
  public static final String ERR_ANNOTATION_NOT_FOR_TARGET = "The given annotation "
      + "\"{0}\" can NOT annotate the target \"{1}\"!";

  /** @see net.sf.mmm.util.reflect.base.CollectionReflectionUtilImpl#create(Class) */
  public static final String ERR_INCREASE_EXCEEDS_MAX_GROWTH = "Can not increase "
      + "size of array or list by \"{0}\", because limit is \"{1}\"!";

  /** @see net.sf.mmm.util.reflect.base.CollectionReflectionUtilImpl#create(Class) */
  public static final String ERR_UNKNOWN_COLLECTION_INTERFACE = "Unknown collection interface \"{0}\"!";

  /** @see net.sf.mmm.util.reflect.api.InstantiationFailedException */
  public static final String ERR_INSTANTIATION_FAILED = "Failed to create an instance of \"{0}\"!";

  /** @see net.sf.mmm.util.reflect.api.InvocationFailedException */
  public static final String ERR_INVOCATION_FAILED = "Reflective invocation failed!";

  /** @see net.sf.mmm.util.reflect.api.InvocationFailedException */
  public static final String ERR_INVOCATION_FAILED_ON = "Reflective invocation of \"{0}\" on \"{1}\" failed!";

  /** @see net.sf.mmm.util.reflect.api.AccessFailedException */
  public static final String ERR_ACCESS_FAILED = "Reflective access for \"{0}\" failed!";

}
