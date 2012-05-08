/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util;

import net.sf.mmm.util.nls.api.NlsTemplateResolver;
import net.sf.mmm.util.nls.base.AbstractResourceBundle;

/**
 * This class holds the internationalized messages for this module.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.2
 */
public class NlsBundleUtilCore extends AbstractResourceBundle {

  /** @see net.sf.mmm.util.lang.api.HorizontalAlignment#LEFT */
  public static final String INF_LEFT = "left";

  /** @see net.sf.mmm.util.lang.api.HorizontalAlignment#RIGHT */
  public static final String INF_RIGHT = "right";

  /** @see net.sf.mmm.util.lang.api.HorizontalAlignment#CENTER */
  public static final String INF_CENTER = "center";

  /** @see net.sf.mmm.util.lang.api.Conjunction#AND */
  public static final String INF_AND = "and";

  /** @see net.sf.mmm.util.lang.api.Conjunction#OR */
  public static final String INF_OR = "or";

  /** @see net.sf.mmm.util.lang.api.Conjunction#NAND */
  public static final String INF_NAND = "nand";

  /** @see net.sf.mmm.util.lang.api.Conjunction#NOR */
  public static final String INF_NOR = "nor";

  /** @see net.sf.mmm.util.lang.api.Comparator#GREATER_THAN */
  public static final String INF_GREATER_THAN = "greater-than";

  /** @see net.sf.mmm.util.lang.api.Comparator#GREATER_OR_EQUAL */
  public static final String INF_GREATER_OR_EQUAL = "greater-or-equal";

  /** @see net.sf.mmm.util.lang.api.Comparator#EQUAL */
  public static final String INF_EQUAL = "equal";

  /** @see net.sf.mmm.util.lang.api.Comparator#NOT_EQUAL */
  public static final String INF_NOT_EQUAL = "not-equal";

  /** @see net.sf.mmm.util.lang.api.Comparator#LESS_THAN */
  public static final String INF_LESS_THAN = "less-than";

  /** @see net.sf.mmm.util.lang.api.Comparator#LESS_OR_EQUAL */
  public static final String INF_LESS_OR_EQUAL = "less-or-equal";

  /** @see net.sf.mmm.util.value.api.WrongValueTypeException */
  public static final String ERR_VALUE_WRONG_TYPE = "The value \"{value}\" with the "
      + "type \"{valueType}\" can NOT be converted to the requested type \"{targetType}\"!";

  /** @see net.sf.mmm.util.value.api.WrongValueTypeException */
  public static final String ERR_VALUE_WRONG_TYPE_SOURCE = "The value \"{value}\" "
      + "from \"{source}\" with the type \"{valueType}\" can NOT be converted to the requested type \"{targetType}\"!";

  /** @see net.sf.mmm.util.value.api.ValueNotSetException */
  public static final String ERR_VALUE_NOT_SET = "The value \"{value}\" is not set!";

  /** @see net.sf.mmm.util.nls.api.NlsParseException */
  public static final String ERR_PARSE_EXPECTED = "Failed to parse \"{value}\" - " + "expected \"{type}\"!";

  /** @see net.sf.mmm.util.nls.api.NlsParseException */
  public static final String ERR_PARSE_TYPE = "Failed to parse \"{value}\" as value " + "of the type \"{type}\"!";

  /** @see net.sf.mmm.util.nls.api.NlsParseException */
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
      + "is not in the expected range of \"[{min} - {max}]\"!";

  /** @see net.sf.mmm.util.value.api.ValueOutOfRangeException */
  public static final String ERR_VALUE_OUT_OF_RANGE_SOURCE = "The value \"{value}\" from \"{source}\" "
      + "is not in the expected range of \"[{min} - {max}]\"!";

  /** @see net.sf.mmm.util.value.api.ValueConvertException */
  public static final String ERR_VALUE_CONVERT = "The value \"{value}\" could NOT be converted to \"{type}\"";

  /** @see net.sf.mmm.util.value.api.ValueConvertException */
  public static final String ERR_VALUE_CONVERT_SOURCE = "The value \"{value}\" from \"{source}\" could NOT be converted to \"{type}\"";

  /** @see net.sf.mmm.util.component.api.ResourceMissingException */
  public static final String ERR_RESOURCE_MISSING = "The required resource \"{resource}\" is missing!";

  /** @see net.sf.mmm.util.component.api.ResourceAmbiguousException */
  public static final String ERR_RESOURCE_AMBIGUOUS = "The required resource \"{resource}\" is ambiguous!";

  /** @see net.sf.mmm.util.component.api.ResourceAmbiguousException */
  public static final String ERR_RESOURCE_AMBIGUOUS_WITH_IDS = "The required resource \"{resource}\" is ambiguous!\n{value}";

  /** @see net.sf.mmm.util.component.api.AlreadyInitializedException */
  public static final String ERR_ALREADY_INITIALIZED = "The object is already initialized!";

  /** @see net.sf.mmm.util.component.api.NotInitializedException */
  public static final String ERR_NOT_INITIALIZED = "The object is NOT initialized!";

  /** @see net.sf.mmm.util.nls.api.NlsIllegalArgumentException */
  public static final String ERR_ILLEGAL_ARGUMENT = "The given argument \"{value}\" is illegal!";

  /** @see net.sf.mmm.util.nls.api.NlsIllegalArgumentException */
  public static final String ERR_ILLEGAL_ARGUMENT_VALUE = "The given value \"{value}\" is illegal for the argument \"{name}\"!";

  /** @see net.sf.mmm.util.date.api.IllegalDateFormatException */
  public static final String ERR_ILLEGAL_DATE = "Illegal date \"{value}\"!";

  /** @see net.sf.mmm.util.math.api.NumberConversionException */
  public static final String ERR_NUMBER_CONVERSION = "Can not convert number \"{value}\" to \"{type}\"!";

  /** @see net.sf.mmm.util.io.api.RuntimeIoException */
  public static final String ERR_IO = "An unexpected input/output error has ocurred!";

  /** @see net.sf.mmm.util.io.api.RuntimeIoException */
  public static final String ERR_IO_READ = "An unexpetected error has occured while reading data!";

  /** @see net.sf.mmm.util.io.api.RuntimeIoException */
  public static final String ERR_IO_WRITE = "An unexpetected error has occured while writing data!";

  /** @see net.sf.mmm.util.io.api.RuntimeIoException */
  public static final String ERR_IO_CLOSE = "Failed to close handle!";

  /** @see net.sf.mmm.util.io.api.RuntimeIoException */
  public static final String ERR_IO_FLUSH = "Failed to flush handle!";

  /** @see net.sf.mmm.util.io.api.RuntimeIoException */
  public static final String ERR_IO_COPY = "An unexpetected error has occured while copying data!";

  /** @see net.sf.mmm.util.io.api.StreamClosedException */
  public static final String ERR_STREAM_CLOSED = "The stream is already closed!";

  /** @see net.sf.mmm.util.file.api.FileAlreadyExistsException */
  public static final String ERR_FILE_ALREADY_EXISTS = "The {directory,choice,(?==true)'directory'(else)'file'} \"{file}\" already exists!";

  /** @see net.sf.mmm.util.file.api.FileNotExistsException */
  public static final String ERR_FILE_NOT_EXISTS = "The {directory,choice,(?==true)'directory'(else)'file'} \"{file}\" does not exist!";

  /** @see net.sf.mmm.util.file.api.FileCreationFailedException */
  public static final String ERR_FILE_CREATION_FAILED = "The "
      + "{directory,choice,(?==true)'directory'(else)'file'} \"{file}\" could not be created!";

  /** @see net.sf.mmm.util.file.api.FileDeletionFailedException */
  public static final String ERR_FILE_DELETION_FAILED = "The "
      + "{directory,choice,(?==true)'directory'(else)'file'} \"{file}\" could not be deleted!";

  /** @see net.sf.mmm.util.nls.api.NlsNullPointerException */
  public static final String ERR_ARGUMENT_NULL = "The object \"{object}\" is null!";

  /** @see net.sf.mmm.util.nls.api.NlsClassCastException */
  public static final String ERR_CLASS_CAST = "The object \"{object}\" of type \"{type}\" can not be cast to \"{targetType}\"!";

  /** @see net.sf.mmm.util.nls.api.DuplicateObjectException */
  public static final String ERR_DUPLICATE_OBJECT = "Duplicate object \"{object}\"!";

  /** @see net.sf.mmm.util.nls.api.DuplicateObjectException */
  public static final String ERR_DUPLICATE_OBJECT_WITH_KEY = "Duplicate object \"{object}\" for key \"{key}\"!";

  /** @see net.sf.mmm.util.nls.api.DuplicateObjectException */
  public static final String ERR_DUPLICATE_OBJECT_WITH_KEY_AND_EXISTING = "Duplicate "
      + "object \"{object}\" for key \"{key}\" - already mapped to \"{existing}\"!";

  /** @see net.sf.mmm.util.nls.api.ObjectMismatchException */
  public static final String ERR_OBJECT_MISMATCH = "Mismatch detected: "
      + "found \"{object}\", but expected \"{expected}\"!";

  /** @see net.sf.mmm.util.nls.api.ObjectMismatchException */
  public static final String ERR_OBJECT_MISMATCH_WITH_CONTAINER = "Mismatch in "
      + "\"{container}\" detected: found \"{object}\", but expected \"{expected}\"!";

  /** @see net.sf.mmm.util.nls.api.ObjectMismatchException */
  public static final String ERR_OBJECT_MISMATCH_WITH_CONTAINER_AND_PROPERTY = "Mismatch in "
      + "\"{container}\" for \"{property}\" detected: found \"{object}\", but expected \"{expected}\"!";

  /** @see net.sf.mmm.util.nls.api.ObjectNotFoundException */
  public static final String ERR_OBJECT_NOT_FOUND = "Could NOT find object \"{object}\"!";

  /** @see net.sf.mmm.util.nls.api.ObjectNotFoundException */
  public static final String ERR_OBJECT_NOT_FOUND_WITH_KEY = "Could NOT find object \"{object}\" for key \"{key}\"!";

  /** @see net.sf.mmm.util.nls.api.NlsIllegalStateException */
  public static final String ERR_ILLEGAL_STATE = "Illegal state!";

  /** @see net.sf.mmm.util.nls.api.ComposedException */
  public static final String ERR_COMPOSED = "The following errors have occurred!\n{error}";

  /** @see net.sf.mmm.util.nls.api.IllegalCaseException */
  public static final String ERR_ILLEGAL_CASE = "The case \"{case}\" is NOT covered!";

  /** @see net.sf.mmm.util.resource.api.ResourceNotAvailableException */
  public static final String ERR_RESOURCE_NOT_AVAILABLE = "The resource \"{resource}\" is not available!";

  /** @see net.sf.mmm.util.resource.api.ResourceNotWritableException */
  public static final String ERR_RESOURCE_NOT_WRITABLE = "The resource \"{resource}\" is not writable!";

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

  /** @see net.sf.mmm.util.reflect.api.TypeNotFoundException */
  public static final String ERR_CLASS_NOT_FOUND = "The type \"{type}\" could NOT be found!";

  /** @see net.sf.mmm.util.reflect.base.CollectionReflectionUtilImpl#create(Class) */
  public static final String ERR_INCREASE_EXCEEDS_MAX_GROWTH = "Can not increase "
      + "size of array or list by \"{size}\", because limit is \"{max}\"!";

  /** @see net.sf.mmm.util.reflect.base.UnknownCollectionInterfaceException */
  public static final String ERR_UNKNOWN_COLLECTION_INTERFACE = "Unknown collection interface \"{type}\"!";

  /** @see net.sf.mmm.util.reflect.api.InstantiationFailedException */
  public static final String ERR_INSTANTIATION_FAILED = "Failed to create an instance of \"{type}\"!";

  /** @see net.sf.mmm.util.reflect.api.InvocationFailedException */
  public static final String ERR_INVOCATION_FAILED = "Invocation failed!";

  /** @see net.sf.mmm.util.reflect.api.InvocationFailedException */
  public static final String ERR_INVOCATION_FAILED_ON = "Invocation of \"{accessible}\" failed on \"{object}\"!";

  /** @see net.sf.mmm.util.reflect.api.AccessFailedException */
  public static final String ERR_ACCESS_FAILED = "Reflective access for \"{accessible}\" failed!";

  /** @see net.sf.mmm.util.io.api.BufferExceedException */
  public static final String ERR_BUFFER_LENGTH_EXCEED = "Offset or length \"{value}\" exceeds buffer with capacity \"{capacity}\"!";

  /** @see net.sf.mmm.util.nls.impl.formatter.NlsFormatterChoiceNoElseConditionException */
  public static final String ERR_NLS_CHOICE_NO_ELSE = "A choice format needs to end with an (else)-condition!";

  /** @see net.sf.mmm.util.nls.impl.formatter.NlsFormatterChoiceOnlyElseConditionException */
  public static final String ERR_NLS_CHOICE_ONLY_ELSE = "A choice format needs to have at least one regular condition before (else)-condition!";

  /** @see net.sf.mmm.util.xml.base.XmlInvalidException */
  public static final String ERR_XML_INVALID = "Invalid XML!";

  /** @see net.sf.mmm.util.xml.base.XmlInvalidException */
  public static final String ERR_XML_INVALID_WITH_SOURCE = "The XML from {source} is invalid!";

  /** @see net.sf.mmm.util.collection.base.NodeCycleException */
  public static final String ERR_NODE_CYCLE = "A cyclic dependency of {type}-nodes has been detected [{cycle}]!";

  /** @see net.sf.mmm.util.cli.api.CliOptionIllegalNameOrAliasException */
  public static final String ERR_CLI_OPTION_NAME_ILLEGAL = "The name or alias \"{name}\" of \"{option}\" is illegal!";

  /** @see net.sf.mmm.util.cli.api.CliOptionIncompatibleModesException */
  public static final String ERR_CLI_OPTION_INCOMPATIBLE_MODES = "The options \"{option}\" and \"{option2}\" have incompatible modes and can not be mixed!";

  /** @see net.sf.mmm.util.cli.api.CliParserExcepiton */
  public static final String ERR_CLI_PARSER = "The class \"{type}\" is invalid as command-line interface state-object!";

  /** @see net.sf.mmm.util.nls.api.ReadOnlyException */
  public static final String ERR_READ_ONLY = "Failed to modify \"{object}\" as it is read-only!";

  /** @see net.sf.mmm.util.cli.api.AbstractMain */
  public static final String INF_MAIN_MODE_HELP = "help";

  /** @see net.sf.mmm.util.cli.api.AbstractMain */
  public static final String INF_MAIN_MODE_VERSION = "version";

  /** @see net.sf.mmm.util.cli.api.AbstractMain */
  public static final String INF_MAIN_MODE_DEFAULT = "default";

  /** @see net.sf.mmm.util.cli.api.AbstractMain */
  public static final String MSG_MAIN_OPTION_HELP_USAGE = "Print this help.";

  /** @see net.sf.mmm.util.cli.api.AbstractMain */
  public static final String MSG_MAIN_MODE_HELP_USAGE = "Print help about this program.";

  /** @see net.sf.mmm.util.cli.api.AbstractVersionedMain */
  public static final String MSG_MAIN_OPTION_VERSION_USAGE = "Print the program-version.";

  /** @see net.sf.mmm.util.cli.api.AbstractMain */
  public static final String MSG_MAIN_MODE_VERSION_USAGE = "Print the version of this program.";

  /** @see net.sf.mmm.util.nls.base.ResourceBundleSynchronizer */
  public static final String MSG_SYNCHRONIZER_USAGE = "Create and/or update " + "resource-bundle property-files.";

  /** @see net.sf.mmm.util.nls.base.ResourceBundleSynchronizer */
  public static final String MSG_SYNCHRONIZER_USAGE_MODE_DEFAULT = "Create and/or "
      + "update resource-bundle property-files from <bundle-class> for the given "
      + "locales (including the root locale). Example:\n\n" + "{mainClass} --bundle-class foo.bar.NlsBundleMyExample "
      + "de de_DE en en_US en_GB fr zh ja_JP zh_TW\n\n"
      + "For each locale a property-file foo/bar/NlsBundleMyExample_<locale>.properties "
      + "will be created or updated in the base-path. In each property-file all "
      + "properties defined in <bundle-class> will be added with a TODO-marker "
      + "and the original text as value. If the property-file already exists, all "
      + "existing properties will remain unchanged and comments will be kept.";

  /** @see net.sf.mmm.util.nls.base.ResourceBundleSynchronizer */
  public static final String MSG_SYNCHRONIZER_USAGE_LOCALES = "The list of locales "
      + "to synchronize. Each locale has to be in the form \"ll[_CC[_vv]]\" where "
      + "\"ll\" is the lowercase ISO 639 code, CC is the uppercase ISO 3166 "
      + "2-letter code and vv is an arbitrary variant. Examples are \"de\", " + "\"en_US\" or \"th_TH_TH\".";

  /** @see net.sf.mmm.util.nls.base.ResourceBundleSynchronizer */
  public static final String MSG_SYNCHRONIZER_USAGE_ENCODING = "Read and write "
      + "property-files using the specified encoding {operand} (Default is {default}).";

  /** @see net.sf.mmm.util.nls.base.ResourceBundleSynchronizer */
  public static final String MSG_SYNCHRONIZER_USAGE_PATH = "Write property-files "
      + "to the base-path {operand} (Default is \"{default}\").";

  /** @see net.sf.mmm.util.nls.base.ResourceBundleSynchronizer */
  public static final String MSG_SYNCHRONIZER_USAGE_DATE_PATTERN = "Use the specified "
      + "date pattern for writing synchronization date to property-files (Default is \"{default}\").";

  /** @see net.sf.mmm.util.nls.base.ResourceBundleSynchronizer */
  public static final String MSG_SYNCHRONIZER_USAGE_BUNDLE_CLASS = "The explicit "
      + "list of bundle-classes for which the property-files should be created or updated. "
      + "It has to be the fully qualified name of a subclass of AbstractResourceBundle. "
      + "For all given locales the according property-file is created or updated. "
      + "If this option is omitted the bundle-classes are resolved from all instances of "
      + NlsTemplateResolver.CLASSPATH_NLS_BUNDLE + " on your classpath.";

  /** @see net.sf.mmm.util.cli.base.AbstractCliParser */
  public static final String MSG_CLI_USAGE = "Usage: {mainClass} {option}";

  /** @see net.sf.mmm.util.cli.base.AbstractCliParser */
  public static final String MSG_CLI_MODE_USAGE = "Mode {mode}:";

  /** @see net.sf.mmm.util.cli.base.AbstractCliParser */
  public static final String MSG_CLI_REQUIRED_OPTIONS = "Required options:";

  /** @see net.sf.mmm.util.cli.base.AbstractCliParser */
  public static final String MSG_CLI_ADDITIONAL_OPTIONS = "Additional options:";

  /** @see net.sf.mmm.util.cli.base.AbstractCliParser */
  public static final String MSG_CLI_ARGUMENTS = "Arguments:";

  /** @see net.sf.mmm.util.cli.api.CliOptionDuplicateException */
  public static final String ERR_CLI_OPTION_DUPLICATE = "Duplicate option \"{option}\"!";

  /** @see net.sf.mmm.util.cli.api.CliOptionUndefinedException */
  public static final String ERR_CLI_OPTION_UNDEFINED = "Undefined option \"{option}\"!";

  /** @see net.sf.mmm.util.cli.api.CliOptionMissingValueException */
  public static final String ERR_CLI_OPTION_MISSING_VALUE = "The option " + "\"{option}\" must be followed by a value!";

  /** @see net.sf.mmm.util.cli.api.CliOptionMissingException */
  public static final String ERR_CLI_OPTION_MISSING = "The option \"{option}\" " + "is required for mode \"{mode}\"!";

  /** @see net.sf.mmm.util.cli.api.CliArgumentMissingException */
  public static final String ERR_CLI_ARGUMENT_MISSING = "The argument \"{argument}\" "
      + "is required for mode \"{mode}\"!";

  /** @see net.sf.mmm.util.cli.api.CliArgumentReferenceMissingException */
  public static final String ERR_CLI_ARGUMENT_REFERENCE_MISSING = "The argument \"{key}\" "
      + "is referenced by \"{argument}\" but not defined!";

  /** @see net.sf.mmm.util.cli.api.CliOptionMisplacedException */
  public static final String ERR_CLI_OPTION_MISPLACED = "The option \"{option}\" "
      + "is misplaced and can not be given after the start of the arguments!";

  /** @see net.sf.mmm.util.cli.api.CliOptionAndArgumentAnnotationException */
  public static final String ERR_CLI_OPTION_AND_ARGUMENT_ANNOTATION = "The property "
      + "\"{property}\" can not be annotated both with @CliOption and @CliArgument!";

  /** @see net.sf.mmm.util.cli.api.CliParameterListEmptyException */
  public static final String ERR_CLI_PARAMETER_LIST_EMPTY = "No parameter given! You have to supply at least one commandline parameter.";

  /** @see net.sf.mmm.util.cli.api.CliClassNoPropertyException */
  public static final String ERR_CLI_CLASS_NO_PROPERTY = "The CLI class \"{type}\" "
      + "is illegal because it has no property annotated with @CliOption or @CliArgument!";

  /** @see net.sf.mmm.util.cli.api.CliModeUndefinedException */
  public static final String ERR_CLI_MODE_UNDEFINED = "The mode \"{mode}\" used "
      + "by \"{value}\" is undefined! You have to declare it via @CliMode or "
      + "change @CliStyle.modeUndefined() to something else than EXCEPTION.";

  /** @see net.sf.mmm.util.pojo.descriptor.api.PojoPropertyNotFoundException */
  public static final String ERR_POJO_PROPERTY_NOT_FOUND = "Property \"{property}\" not found in \"{type}\"!";

  /** @see net.sf.mmm.util.pojo.descriptor.api.PojoPropertyNotFoundException */
  public static final String ERR_POJO_PROPERTY_NOT_ACCESSABLE = "Property \"{property}\" not "
      + "accessible for \"{mode}\" in \"{type}\"!";

  /** @see net.sf.mmm.util.pojo.path.api.IllegalPojoPathException */
  public static final String ERR_POJO_FUNCTION_UNDEFINED = "Undefined function \"{function}\"!";

  /** @see net.sf.mmm.util.pojo.path.api.PojoPathFunctionUnsupportedOperationException */
  public static final String ERR_POJO_FUNCTION_UNSUPPORTED_OPERATION = "The function "
      + "\"{function}\" does NOT support the operation \"{operation}\"!";

  /** @see net.sf.mmm.util.pojo.path.api.PojoPathUnsafeException */
  public static final String ERR_POJO_PATH_UNSAFE = "The pojo-path \"{path}\" " + "is unsafe for type \"{type}\"!";

  /** @see net.sf.mmm.util.pojo.path.api.PojoPathSegmentIsNullException */
  public static final String ERR_POJO_PATH_SEGMENT_IS_NULL = "The pojo-path \"{path}\" "
      + "for object \"{object}\" evaluates to null!";

  /** @see net.sf.mmm.util.pojo.path.api.PojoPathCreationException */
  public static final String ERR_POJO_PATH_CREATION = "Failed to create the "
      + "object at the pojo-path \"{path}\" for object \"{object}\"!";

  /** @see net.sf.mmm.util.pojo.path.api.PojoPathAccessException */
  public static final String ERR_POJO_PATH_ACCESS = "Failed to access the pojo-path "
      + "\"{path}\" for current object of type \"{type}\"!";

  /** @see net.sf.mmm.util.pojo.path.api.IllegalPojoPathException */
  public static final String ERR_POJO_PATH_ILLEGAL = "Illegal pojo-path \"{path}\"!";

  /** @see net.sf.mmm.util.pojo.path.base.PojoPathCachingDisabledException */
  public static final String ERR_POJO_PATH_CACHING_DISABLED = "Caching was required "
      + "for pojo-path \"{path}\" but is disabled!";

  /** @see net.sf.mmm.util.pojo.path.api.PojoPathConversionException */
  public static final String ERR_POJO_PATH_CONVERSION = "Can NOT convert from \"{type}\""
      + " to \"{targetType}\" for pojo-path \"{path}\"!";

  /** @see net.sf.mmm.util.reflect.api.CastFailedException */
  public static final String ERR_CAST = "Can NOT cast \"{object}\" from \"{source}\" to \"{target}\"!";

}
