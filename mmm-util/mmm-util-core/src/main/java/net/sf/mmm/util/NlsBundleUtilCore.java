/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util;

import net.sf.mmm.util.cli.api.CliMode;
import net.sf.mmm.util.nls.base.AbstractResourceBundle;

/**
 * This class holds the internationalized messages for this module.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.2
 */
public class NlsBundleUtilCore extends AbstractResourceBundle {

  /** @see net.sf.mmm.util.value.api.WrongValueTypeException */
  public static final String ERR_VALUE_WRONG_TYPE = "The value \"{value}\" with the "
      + "type \"{valueType}\" can NOT be converted to the requested type \"{targetType}\"!";

  /** @see net.sf.mmm.util.value.api.WrongValueTypeException */
  public static final String ERR_VALUE_WRONG_TYPE_SOURCE = "The value \"{value}\" "
      + "from \"{source}\" with the type \"{valueType}\" can NOT be converted to the requested type \"{targetType}\"!";

  /** @see net.sf.mmm.util.value.api.ValueNotSetException */
  public static final String ERR_VALUE_NOT_SET = "The value \"{value}\" is not set!";

  /**
   * @see net.sf.mmm.util.nls.api.NlsParseException
   */
  public static final String ERR_PARSE_TYPE = "Failed to parse \"{value}\" as value "
      + "of the type \"{type}\"!";

  /**
   * @see net.sf.mmm.util.nls.api.NlsParseException
   */
  public static final String ERR_PARSE_TYPE_SOURCE = "Failed to parse \"{value}\" "
      + "from \"{source}\" as value of the type \"{type}\"!";

  /** @see net.sf.mmm.util.nls.api.NlsParseException */
  public static final String ERR_PARSE_FORMAT = "Failed to parse \"{value}\" as "
      + "\"{type}\" - required format is \"{format}\"!";

  /** @see net.sf.mmm.util.nls.api.NlsParseException */
  public static final String ERR_PARSE_FORMAT_SOURCE = "Failed to parse \"{value}\" "
      + "from \"{source}\" as \"{type}\" - required format is \"{format}\"!";

  /** @see net.sf.mmm.util.value.api.ValueOutOfRangeException */
  public static final String ERR_VALUE_OUT_OF_RANGE = "The value \"{value}\" "
      + "is not in the expected range of \"[{min}-{max}]\"!";

  /** @see net.sf.mmm.util.value.api.ValueOutOfRangeException */
  public static final String ERR_VALUE_OUT_OF_RANGE_SOURCE = "The value \"{value}\" from \"{source}\" "
      + "is not in the expected range of \"[{min}-{max}]\"!";

  /** @see net.sf.mmm.util.value.api.ValueConvertException */
  public static final String ERR_VALUE_CONVERT = "The value \"{value}\" could NOT be converted to \"{type}\"";

  /** @see net.sf.mmm.util.value.api.ValueConvertException */
  public static final String ERR_VALUE_CONVERT_SOURCE = "The value \"{value}\" from \"{source}\" could NOT be converted to \"{type}\"";

  /** @see net.sf.mmm.util.component.api.ResourceMissingException */
  public static final String ERR_RESOURCE_MISSING = "The required resource \"{resource}\" is missing!";

  /** @see net.sf.mmm.util.component.api.AlreadyInitializedException */
  public static final String ERR_ALREADY_INITIALIZED = "The object is already initialized!";

  /** @see net.sf.mmm.util.component.api.NotInitializedException */
  public static final String ERR_NOT_INITIALIZED = "The object is NOT initialized!";

  /** @see net.sf.mmm.util.nls.api.NlsIllegalArgumentException */
  public static final String ERR_ILLEGAL_ARGUMENT = "The given argument \"{object}\" is illegal!";

  /** @see net.sf.mmm.util.date.api.IllegalDateFormatException */
  public static final String ERR_ILLEGAL_DATE = "Illegal date \"{value}\"!";

  /** @see net.sf.mmm.util.math.api.NumberConversionException */
  public static final String ERR_NUMBER_CONVERSION = "Can not convert number \"{value}\" to \"{type}\"!";

  /** @see net.sf.mmm.util.io.api.RuntimeIoException */
  public static final String ERR_IO = "An unexpected input/output error has ocurred!";

  /** @see net.sf.mmm.util.nls.api.NlsNullPointerException */
  public static final String ERR_ARGUMENT_NULL = "The object \"{object}\" is null!";

  /** @see net.sf.mmm.util.nls.api.DuplicateObjectException */
  public static final String ERR_DUPLICATE_OBJECT = "Duplicate object \"{object}\"!";

  /** @see net.sf.mmm.util.nls.api.DuplicateObjectException */
  public static final String ERR_DUPLICATE_OBJECT_WITH_KEY = "Duplicate object \"{object}\" for key \"{key}\"!";

  /** @see net.sf.mmm.util.nls.api.ObjectNotFoundException */
  public static final String ERR_OBJECT_NOT_FOUND = "Could NOT find object \"{object}\"!";

  /** @see net.sf.mmm.util.nls.api.ObjectNotFoundException */
  public static final String ERR_OBJECT_NOT_FOUND_WITH_KEY = "Could NOT find object \"{object}\" for key \"{key}\"!";

  /** @see net.sf.mmm.util.nls.api.NlsIllegalStateException */
  public static final String ERR_ILLEGAL_STATE = "Illegal state!";

  /** @see net.sf.mmm.util.nls.api.IllegalCaseException */
  public static final String ERR_ILLEGAL_CASE = "The case \"{case}\" is NOT covered!";

  /** @see net.sf.mmm.util.resource.api.ResourceNotAvailableException */
  public static final String ERR_RESOURCE_NOT_AVAILABLE = "The resource \"{resource}\" is not available in your classpath!";

  /** @see net.sf.mmm.util.resource.api.ResourceUriUndefinedException */
  public static final String ERR_RESOURCE_UNDEFINED_URI = "The resource URI \"{uri}\" is undefined!";

  /** @see net.sf.mmm.util.nls.api.NlsUnsupportedOperationException */
  public static final String ERR_UNSUPPORTED_OPERATION = "An operation was invoked that is NOT supported!";

  /** @see net.sf.mmm.util.nls.api.NlsUnsupportedOperationException */
  public static final String ERR_UNSUPPORTED_OPERATION_WITH_NAME = "The operation \"{operation}\" was invoked but is NOT supported!";

  /** @see net.sf.mmm.util.reflect.base.IllegalWildcardSequenceException */
  public static final String ERR_TYPE_ILLEGAL_WILDCARD = "Illegal sequence in wildcard type \"{sequence}\"!";

  /** @see net.sf.mmm.util.reflect.api.AnnotationNotRuntimeException */
  public static final String ERR_ANNOTATION_NOT_RUNTIME = "The given annotation "
      + "\"{annotation}\" can NOT be resolved at runtime!";

  /** @see net.sf.mmm.util.reflect.api.AnnotationNotForTargetException */
  public static final String ERR_ANNOTATION_NOT_FOR_TARGET = "The given annotation "
      + "\"{annotation}\" can NOT annotate the target \"{1}\"!";

  /** @see net.sf.mmm.util.reflect.base.CollectionReflectionUtilImpl#create(Class) */
  public static final String ERR_INCREASE_EXCEEDS_MAX_GROWTH = "Can not increase "
      + "size of array or list by \"{size}\", because limit is \"{max}\"!";

  /** @see net.sf.mmm.util.reflect.base.UnknownCollectionInterfaceException */
  public static final String ERR_UNKNOWN_COLLECTION_INTERFACE = "Unknown collection interface \"{type}\"!";

  /** @see net.sf.mmm.util.reflect.api.InstantiationFailedException */
  public static final String ERR_INSTANTIATION_FAILED = "Failed to create an instance of \"{type}\"!";

  /** @see net.sf.mmm.util.reflect.api.InvocationFailedException */
  public static final String ERR_INVOCATION_FAILED = "Reflective invocation failed!";

  /** @see net.sf.mmm.util.reflect.api.InvocationFailedException */
  public static final String ERR_INVOCATION_FAILED_ON = "Reflective invocation of \"{accessible}\" on \"{object}\" failed!";

  /** @see net.sf.mmm.util.reflect.api.AccessFailedException */
  public static final String ERR_ACCESS_FAILED = "Reflective access for \"{accessible}\" failed!";

  /** @see net.sf.mmm.util.io.api.BufferExceedException */
  public static final String ERR_BUFFER_LENGTH_EXCEED = "Offset or length \"{value}\" exceeds buffer with capacity \"{capacity}\"!";

  /** @see net.sf.mmm.util.xml.base.XmlInvalidException */
  public static final String ERR_XML_INVALID = "Invalid XML!";

  /** @see net.sf.mmm.util.cli.api.CliModeCycleException */
  public static final String ERR_CLI_MODE_CYCLE = "A @" + CliMode.class.getSimpleName()
      + "-cycle has been detected[{cycle}]!";

  /** @see net.sf.mmm.util.cli.api.CliOptionIllegalNameOrAliasException */
  public static final String ERR_CLI_OPTION_NAME_ILLEGAL = "The name or alias \"{name}\" of \"{option}\" is illegal!";

  /** @see net.sf.mmm.util.cli.api.CliModeMixException */
  public static final String ERR_CLI_MODE_MIX = "The options \"{option}\" and \"{option2}\" have incompatible modes and can not be mixed!";

  /** @see net.sf.mmm.util.cli.api.CliParserExcepiton */
  public static final String ERR_CLI_PARSER = "The class \"{type}\" is invalid as command-line interface state-object!";

  /** @see net.sf.mmm.util.cli.api.AbstractMain */
  public static final String INF_MAIN_HELP = "help";

  /** @see net.sf.mmm.util.cli.api.AbstractMain */
  public static final String INF_MAIN_HELP_USAGE = "Print this help.";

  /** @see net.sf.mmm.util.cli.api.AbstractMain */
  public static final String INF_MAIN_VERSION = "version";

  /** @see net.sf.mmm.util.cli.api.AbstractVersionedMain */
  public static final String INF_MAIN_VERSION_USAGE = "Print the version of this program.";

  /** @see net.sf.mmm.util.cli.api.AbstractMain */
  public static final String INF_MAIN_DEFAULT = "default";

  /** @see net.sf.mmm.util.cli.base.AbstractCliParser */
  public static final String MSG_MAIN_USAGE = "Usage: {mainClass} [<option>*]";

}
