/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util;

import java.lang.reflect.Type;

import javax.inject.Named;

import net.sf.mmm.util.nls.api.NlsBundle;
import net.sf.mmm.util.nls.api.NlsBundleLocation;
import net.sf.mmm.util.nls.api.NlsBundleMessage;
import net.sf.mmm.util.nls.api.NlsMessage;
import net.sf.mmm.util.nls.api.NlsTemplateResolver;

/**
 * This class holds the {@link NlsBundle internationalized messages} for this module.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.0.0
 */
@NlsBundleLocation(bundleName = "NlsBundleUtilCore")
public interface NlsBundleUtilCoreRoot extends NlsBundle {

  /** @see #infoLeft() */
  String INF_LEFT = "left";

  /** @see #infoRight() */
  String INF_RIGHT = "right";

  /** @see #infoCenter() */
  String INF_CENTER = "center";

  /** @see #infoTop() */
  String INF_TOP = "top";

  /** @see #infoTopLeft() */
  String INF_TOP_LEFT = "top-left";

  /** @see #infoTopRight() */
  String INF_TOP_RIGHT = "top-right";

  /** @see #infoBottom() */
  String INF_BOTTOM = "bottom";

  /** @see #infoBottomLeft() */
  String INF_BOTTOM_LEFT = "bottom-left";

  /** @see #infoBottomRight() */
  String INF_BOTTOM_RIGHT = "bottom-right";

  /** @see #infoHorizontal() */
  String INF_HORIZONTAL = "horizontal";

  /** @see #infoVertical() */
  String INF_VERTICAL = "vertical";

  /** @see #infoAnd() */
  String INF_AND = "and";

  /** @see #infoOr() */
  String INF_OR = "or";

  /** @see #infoNand() */
  String INF_NAND = "nand";

  /** @see #infoNor() */
  String INF_NOR = "nor";

  /** @see #infoGreaterThan() */
  String INF_GREATER_THAN = "greater-than";

  /** @see #infoGreaterOrEqual() */
  String INF_GREATER_OR_EQUAL = "greater-or-equal";

  /** @see #infoEqual() */
  String INF_EQUAL = "equal";

  /** @see #infoNotEqual() */
  String INF_NOT_EQUAL = "not-equal";

  /** @see #infoLessThan() */
  String INF_LESS_THAN = "less-than";

  /** @see #infoLessOrEqual() */
  String INF_LESS_OR_EQUAL = "less-or-equal";

  /** @see net.sf.mmm.util.cli.api.AbstractMain */
  String INF_MAIN_MODE_HELP = "help";

  /** @see net.sf.mmm.util.cli.api.AbstractMain */
  String INF_MAIN_MODE_VERSION = "version";

  /** @see net.sf.mmm.util.cli.api.AbstractMain */
  String INF_MAIN_MODE_DEFAULT = "default";

  /** @see net.sf.mmm.util.cli.api.AbstractMain */
  String MSG_MAIN_OPTION_HELP_USAGE = "Print this help.";

  /** @see net.sf.mmm.util.cli.api.AbstractMain */
  String MSG_MAIN_MODE_HELP_USAGE = "Print help about this program.";

  /** @see net.sf.mmm.util.cli.api.AbstractVersionedMain */
  String MSG_MAIN_OPTION_VERSION_USAGE = "Print the program-version.";

  /** @see net.sf.mmm.util.cli.api.AbstractMain */
  String MSG_MAIN_MODE_VERSION_USAGE = "Print the version of this program.";

  /** @see net.sf.mmm.util.nls.base.ResourceBundleSynchronizer */
  String MSG_SYNCHRONIZER_USAGE = "Create and/or update " + "resource-bundle property-files.";

  /** @see net.sf.mmm.util.nls.base.ResourceBundleSynchronizer */
  String MSG_SYNCHRONIZER_USAGE_MODE_DEFAULT = "Create and/or "
      + "update resource-bundle property-files from <bundle-class> for the given "
      + "locales (including the root locale). Example:\n\n" + "{mainClass} --bundle-class foo.bar.NlsBundleMyExample "
      + "de de_DE en en_US en_GB fr zh ja_JP zh_TW\n\n"
      + "For each locale a property-file foo/bar/NlsBundleMyExample_<locale>.properties "
      + "will be created or updated in the base-path. In each property-file all "
      + "properties defined in <bundle-class> will be added with a TODO-marker "
      + "and the original text as value. If the property-file already exists, all "
      + "existing properties will remain unchanged and comments will be kept.";

  /** @see net.sf.mmm.util.nls.base.ResourceBundleSynchronizer */
  String MSG_SYNCHRONIZER_USAGE_LOCALES = "The list of locales "
      + "to synchronize. Each locale has to be in the form \"ll[_CC[_vv]]\" where "
      + "\"ll\" is the lowercase ISO 639 code, CC is the uppercase ISO 3166 "
      + "2-letter code and vv is an arbitrary variant. Examples are \"de\", " + "\"en_US\" or \"th_TH_TH\".";

  /** @see net.sf.mmm.util.nls.base.ResourceBundleSynchronizer */
  String MSG_SYNCHRONIZER_USAGE_ENCODING = "Read and write "
      + "property-files using the specified encoding {operand} (Default is {default}).";

  /** @see net.sf.mmm.util.nls.base.ResourceBundleSynchronizer */
  String MSG_SYNCHRONIZER_USAGE_PATH = "Write property-files "
      + "to the base-path {operand} (Default is \"{default}\").";

  /** @see net.sf.mmm.util.nls.base.ResourceBundleSynchronizer */
  String MSG_SYNCHRONIZER_USAGE_DATE_PATTERN = "Use the specified "
      + "date pattern for writing synchronization date to property-files (Default is \"{default}\").";

  /** @see net.sf.mmm.util.nls.base.ResourceBundleSynchronizer */
  String MSG_SYNCHRONIZER_USAGE_BUNDLE_CLASS = "The explicit "
      + "list of bundle-classes for which the property-files should be created or updated. "
      + "It has to be the fully qualified name of a subclass of AbstractResourceBundle. "
      + "For all given locales the according property-file is created or updated. "
      + "If this option is omitted the bundle-classes are resolved from all instances of "
      + NlsTemplateResolver.CLASSPATH_NLS_BUNDLE + " on your classpath.";

  /** @see net.sf.mmm.util.cli.base.AbstractCliParser */
  String MSG_CLI_USAGE = "Usage: {mainClass} {option}";

  /** @see net.sf.mmm.util.cli.base.AbstractCliParser */
  String MSG_CLI_MODE_USAGE = "Mode {mode}:";

  /** @see net.sf.mmm.util.cli.base.AbstractCliParser */
  String MSG_CLI_REQUIRED_OPTIONS = "Required options:";

  /** @see net.sf.mmm.util.cli.base.AbstractCliParser */
  String MSG_CLI_ADDITIONAL_OPTIONS = "Additional options:";

  /** @see net.sf.mmm.util.cli.base.AbstractCliParser */
  String MSG_CLI_ARGUMENTS = "Arguments:";

  /**
   * @see net.sf.mmm.util.lang.api.HorizontalAlignment#LEFT
   * 
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage(INF_LEFT)
  NlsMessage infoLeft();

  /**
   * @see net.sf.mmm.util.lang.api.HorizontalAlignment#RIGHT
   * 
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage(INF_RIGHT)
  NlsMessage infoRight();

  /**
   * @see net.sf.mmm.util.lang.api.HorizontalAlignment#CENTER
   * 
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage(INF_CENTER)
  NlsMessage infoCenter();

  /**
   * @see net.sf.mmm.util.lang.api.VerticalAlignment#TOP
   * 
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage(INF_TOP)
  NlsMessage infoTop();

  /**
   * @see net.sf.mmm.util.lang.api.Alignment#TOP_LEFT
   * 
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage(INF_TOP_LEFT)
  NlsMessage infoTopLeft();

  /**
   * @see net.sf.mmm.util.lang.api.Alignment#TOP_RIGHT
   * 
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage(INF_TOP_RIGHT)
  NlsMessage infoTopRight();

  /**
   * @see net.sf.mmm.util.lang.api.VerticalAlignment#BOTTOM
   * 
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage(INF_BOTTOM)
  NlsMessage infoBottom();

  /**
   * @see net.sf.mmm.util.lang.api.Alignment#BOTTOM_LEFT
   * 
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage(INF_BOTTOM_LEFT)
  NlsMessage infoBottomLeft();

  /**
   * @see net.sf.mmm.util.lang.api.Alignment#BOTTOM_RIGHT
   * 
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage(INF_BOTTOM_RIGHT)
  NlsMessage infoBottomRight();

  /**
   * @see net.sf.mmm.util.lang.api.Orientation#HORIZONTAL
   * 
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage(INF_HORIZONTAL)
  NlsMessage infoHorizontal();

  /**
   * @see net.sf.mmm.util.lang.api.Orientation#VERTICAL
   * 
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage(INF_VERTICAL)
  NlsMessage infoVertical();

  /**
   * @see net.sf.mmm.util.lang.api.Conjunction#AND
   * 
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage(INF_AND)
  NlsMessage infoAnd();

  /**
   * @see net.sf.mmm.util.lang.api.Conjunction#OR
   * 
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage(INF_OR)
  NlsMessage infoOr();

  /**
   * @see net.sf.mmm.util.lang.api.Conjunction#NAND
   * 
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage(INF_NAND)
  NlsMessage infoNand();

  /**
   * @see net.sf.mmm.util.lang.api.Conjunction#NOR
   * 
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage(INF_NOR)
  NlsMessage infoNor();

  /**
   * @see net.sf.mmm.util.lang.api.Comparator#GREATER_THAN
   * 
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage(INF_GREATER_THAN)
  NlsMessage infoGreaterThan();

  /**
   * @see net.sf.mmm.util.lang.api.Comparator#GREATER_OR_EQUAL
   * 
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage(INF_GREATER_OR_EQUAL)
  NlsMessage infoGreaterOrEqual();

  /**
   * @see net.sf.mmm.util.lang.api.Comparator#EQUAL
   * 
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage(INF_EQUAL)
  NlsMessage infoEqual();

  /**
   * @see net.sf.mmm.util.lang.api.Comparator#NOT_EQUAL
   * 
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage(INF_NOT_EQUAL)
  NlsMessage infoNotEqual();

  /**
   * @see net.sf.mmm.util.lang.api.Comparator#LESS_THAN
   * 
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage(INF_LESS_THAN)
  NlsMessage infoLessThan();

  /**
   * @see net.sf.mmm.util.lang.api.Comparator#LESS_OR_EQUAL
   * 
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage(INF_LESS_OR_EQUAL)
  NlsMessage infoLessOrEqual();

  /**
   * @return the {@link NlsMessage} for the label "OK".
   */
  @NlsBundleMessage("OK")
  NlsMessage infoOk();

  /**
   * @return the {@link NlsMessage} for the text "Yes".
   */
  @NlsBundleMessage("Yes")
  NlsMessage infoYes();

  /**
   * @return the {@link NlsMessage} for the text "No".
   */
  @NlsBundleMessage("No")
  NlsMessage infoNo();

  /**
   * @return the {@link NlsMessage} for the text "Information".
   */
  @NlsBundleMessage("Information")
  NlsMessage infoInformation();

  /**
   * @return the {@link NlsMessage} for the text "Warning".
   */
  @NlsBundleMessage("Warning")
  NlsMessage infoWarning();

  /**
   * @return the {@link NlsMessage} for the text "Error".
   */
  @NlsBundleMessage("Error")
  NlsMessage infoError();

  /**
   * @return the {@link NlsMessage} for the text "Confirmation".
   */
  @NlsBundleMessage("Confirmation")
  NlsMessage infoConfirmation();

  /**
   * @return the {@link NlsMessage} for the text "validation failure".
   */
  @NlsBundleMessage("validation failure")
  NlsMessage infoValidationFailure();

  /**
   * @see net.sf.mmm.util.value.api.WrongValueTypeException
   * 
   * @param value is the invalid value.
   * @param valueType is the actual type of the value.
   * @param targetType is the expected type of the value.
   * @param source is the source of the value.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("The value \"{value}\"{source,choice,(?==null)''(else)' from \"'{source}'\"'} with the type "
      + "\"{valueType}\" can NOT be converted to the requested type \"{targetType}\"!")
  NlsMessage errorValueWrongType(@Named("value")
  Object value, @Named("valueType")
  Object valueType, @Named("targetType")
  Object targetType, @Named("source")
  Object source);

  /**
   * @see net.sf.mmm.util.value.api.ValueNotSetException
   * 
   * @param value is the invalid value.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("The value from \"{source}\" is not set!")
  NlsMessage errorValueNotSet(@Named("source")
  Object value);

  /**
   * @see net.sf.mmm.util.nls.api.NlsParseException
   * 
   * @param value is the invalid value.
   * @param type is the expected type.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("Failed to parse \"{value}\" - expected \"{type}\"!")
  NlsMessage errorParseExpected(@Named("value")
  Object value, @Named("type")
  Object type);

  /**
   * @see net.sf.mmm.util.nls.api.NlsParseException
   * 
   * @param value is the invalid value.
   * @param type is the expected type.
   * @param source is the source of the value.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("Failed to parse \"{value}\"{source,choice,(?==null)''(else)' from \"'{source}'\"'} as value of the type \"{type}\"!")
  NlsMessage errorParseType(@Named("value")
  Object value, @Named("type")
  Object type, @Named("source")
  Object source);

  /**
   * @see net.sf.mmm.util.nls.api.NlsParseException
   * 
   * @param value is the value that could NOT be parsed.
   * @param format is the expected format.
   * @param type is the target type for the value to parse.
   * @param source is the source of the value.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("Failed to parse \"{value}\"{source,choice,(?==null)''(else)' from \"'{source}'\"'} as \"{type}\" - required format is \"{format}\"!")
  NlsMessage errorParseFormat(@Named("value")
  Object value, @Named("format")
  Object format, @Named("type")
  Object type, @Named("source")
  Object source);

  /**
   * @see net.sf.mmm.util.value.api.ValueOutOfRangeException
   * 
   * @param value is the invalid value.
   * @param min is the minimum value.
   * @param max is the maximum value.
   * @param source is the source of the value or <code>null</code> if NOT available.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("The value \"{value}\"{source,choice,(?==null)''(else)' from \"'{source}'\"'} is not in the expected range of \"[{min} - {max}]\"!")
  NlsMessage errorValueOutOfRangeWithSource(@Named("value")
  Object value, @Named("min")
  Object min, @Named("max")
  Object max, @Named("source")
  Object source);

  /**
   * @see net.sf.mmm.util.value.api.ValueConvertException
   * 
   * @param value is the value that could NOT be converted.
   * @param type is the type to convert to.
   * @param source is the source of the value.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("The value \"{value}\"{source,choice,(?==null)''(else)' from \"'{source}'\"'} could NOT be converted to \"{type}\"!")
  NlsMessage errorValueConvert(@Named("value")
  Object value, @Named("type")
  Type type, @Named("source")
  Object source);

  /**
   * @see net.sf.mmm.util.component.api.ResourceMissingException
   * 
   * @param resource is the identifier (path, URL, etc.) of the missing resource.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("The required resource \"{resource}\" is missing!")
  NlsMessage errorResourceMissing(@Named("resource")
  String resource);

  /**
   * @see net.sf.mmm.util.component.api.ResourceAmbiguousException
   * 
   * @param resource is the identifier (path, URL, etc.) of the ambiguous resource.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("The required resource \"{resource}\" is ambiguous!")
  NlsMessage errorResourceAmbiguous(@Named("resource")
  String resource);

  /**
   * @see net.sf.mmm.util.component.api.ResourceAmbiguousException
   * 
   * @param resource is the identifier (path, URL, class, etc.) of the missing resource.
   * @param ids are the IDs of the ambiguous resources.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("The required resource \"{resource}\" is ambiguous!\n{ids}")
  NlsMessage errorResourceAmbiguousWithIds(@Named("resource")
  String resource, @Named("ids")
  String... ids);

  /**
   * @see net.sf.mmm.util.component.api.AlreadyInitializedException
   * 
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("The object is already initialized!")
  NlsMessage errorAlreadyInitialized();

  /**
   * @see net.sf.mmm.util.component.api.NotInitializedException
   * 
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("The object is NOT initialized!")
  NlsMessage errorNotInitialized();

  /**
   * @see net.sf.mmm.util.nls.api.NlsIllegalArgumentException
   * 
   * @param value is the illegal value of the argument.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("The given argument \"{value}\" is illegal!")
  NlsMessage errorIllegalArgument(@Named("value")
  Object value);

  /**
   * @see net.sf.mmm.util.nls.api.NlsIllegalArgumentException
   * 
   * @param value is the illegal value of the argument.
   * @param name is the name of the argument.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("The given value \"{value}\" is illegal for the argument \"{name}\"!")
  NlsMessage errorIllegalArgumentWithName(@Named("value")
  Object value, @Named("name")
  String name);

  /**
   * @see net.sf.mmm.util.date.api.IllegalDateFormatException
   * 
   * @param value is the illegal date {@link String}.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("Illegal date \"{value}\"!")
  NlsMessage errorIllegalDate(@Named("value")
  String value);

  /**
   * @see net.sf.mmm.util.math.api.NumberConversionException
   * 
   * @param value is the value that could NOT be converted.
   * @param type is the type the value should be converted to.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("Can not convert number \"{value}\" to \"{type}\"!")
  NlsMessage errorNumberConversion(@Named("value")
  Object value, @Named("type")
  Object type);

  /**
   * @see net.sf.mmm.util.io.api.RuntimeIoException
   * 
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("An unexpected input/output error has ocurred!")
  NlsMessage errorIo();

  /**
   * @see net.sf.mmm.util.io.api.RuntimeIoException
   * 
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("An unexpetected error has occured while reading data!")
  NlsMessage errorIoRead();

  /**
   * @see net.sf.mmm.util.io.api.RuntimeIoException
   * 
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("An unexpetected error has occured while writing data!")
  NlsMessage errorIoWrite();

  /**
   * @see net.sf.mmm.util.io.api.RuntimeIoException
   * 
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("Failed to close handle!")
  NlsMessage errorIoClose();

  /**
   * @see net.sf.mmm.util.io.api.RuntimeIoException
   * 
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("Failed to flush handle!")
  NlsMessage errorIoFlush();

  /**
   * @see net.sf.mmm.util.io.api.RuntimeIoException
   * 
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("An unexpetected error has occured while copying data!")
  NlsMessage errorIoCopy();

  /**
   * @see net.sf.mmm.util.io.api.StreamClosedException
   * 
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("The stream is already closed!")
  NlsMessage errorStreamClosed();

  /**
   * @see net.sf.mmm.util.file.api.FileAlreadyExistsException
   * 
   * @param file is the name or path of the file.
   * @param directory <code>true</code> if the given <code>file</code> is a directory, <code>false</code>
   *        otherwise or if unknown.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("The {directory,choice,(?==true)'directory'(else)'file'} \"{file}\" already exists!")
  NlsMessage errorFileAlreadyExists(@Named("file")
  String file, @Named("directory")
  boolean directory);

  /**
   * @see net.sf.mmm.util.file.api.FileNotExistsException
   * 
   * @param file is the name or path of the file.
   * @param directory <code>true</code> if the given <code>file</code> is a directory, <code>false</code>
   *        otherwise or if unknown.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("The {directory,choice,(?==true)'directory'(else)'file'} \"{file}\" does not exist!")
  NlsMessage errorFileNotExists(@Named("file")
  String file, @Named("directory")
  boolean directory);

  /**
   * @see net.sf.mmm.util.file.api.FileCreationFailedException
   * 
   * @param file is the name or path of the file.
   * @param directory <code>true</code> if the given <code>file</code> is a directory, <code>false</code>
   *        otherwise or if unknown.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("The {directory,choice,(?==true)'directory'(else)'file'} \"{file}\" could not be created!")
  NlsMessage errorFileCreationFailed(@Named("file")
  String file, @Named("directory")
  boolean directory);

  /**
   * @see net.sf.mmm.util.file.api.FileDeletionFailedException
   * 
   * @param file is the name or path of the file.
   * @param directory <code>true</code> if the given <code>file</code> is a directory, <code>false</code>
   *        otherwise or if unknown.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("The {directory,choice,(?==true)'directory'(else)'file'} \"{file}\" could not be deleted!")
  NlsMessage errorFileDeletionFailed(@Named("file")
  String file, @Named("directory")
  boolean directory);

  /**
   * @see net.sf.mmm.util.nls.api.NlsNullPointerException
   * 
   * @param object is the name of the object that is <code>null</code>.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("The object \"{object}\" is null!")
  NlsMessage errorArgumentNull(@Named("object")
  Object object);

  /**
   * @see net.sf.mmm.util.nls.api.DuplicateObjectException
   * 
   * @param object is the duplicate object.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("Duplicate object \"{object}\"!")
  NlsMessage errorDuplicateObject(@Named("object")
  Object object);

  /**
   * @see net.sf.mmm.util.nls.api.DuplicateObjectException
   * 
   * @param object is the duplicate object.
   * @param key is the key associated with the object (e.g. in a {@link java.util.Map}).
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("Duplicate object \"{object}\" for key \"{key}\"!")
  NlsMessage errorDuplicateObjectWithKey(@Named("object")
  Object object, @Named("key")
  Object key);

  /**
   * @see net.sf.mmm.util.nls.api.DuplicateObjectException
   * 
   * @param object is the duplicate object.
   * @param key is the key associated with the object (e.g. in a {@link java.util.Map}).
   * @param existing is the object already associated with the given <code>key</code>.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("Duplicate object \"{object}\" for key \"{key}\" - already mapped to \"{existing}\"!")
  NlsMessage errorDuplicateObjectWithKeyAndExisting(@Named("object")
  Object object, @Named("key")
  Object key, @Named("existing")
  Object existing);

  /**
   * @see net.sf.mmm.util.nls.api.ObjectMismatchException
   * 
   * @param object is the mismatching object.
   * @param expected is the expected object (e.g. type).
   * @param source is the source of the mismatching object or <code>null</code> if unknown.
   * @param property is the property holding the mismatching object or <code>null</code> if undefined.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("Mismatch detected{source,choice,(?==null)''(else)' in \"'{source}'\"'}"
      + "{property,choice,(?==null)''(else)' for \"'{property}'\"'}: found \"{object}\", but expected \"{expected}\"!")
  NlsMessage errorObjectMismatch(@Named("object")
  Object object, @Named("expected")
  Object expected, @Named("source")
  Object source, @Named("property")
  Object property);

  /**
   * @see net.sf.mmm.util.nls.api.ObjectNotFoundException
   * 
   * @param object describes the missing object (e.g. the expected type).
   * @param key is the key associated with the object (e.g. in a {@link java.util.Map}).
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("Could NOT find object \"{object}\"{key,choice,(?==null)''(else)' for \"'{key}'\"'}!")
  NlsMessage errorObjectNotFound(@Named("object")
  Object object, @Named("key")
  Object key);

  /**
   * @see net.sf.mmm.util.nls.api.NlsIllegalStateException
   * 
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("Illegal state!")
  NlsMessage errorIllegalState();

  /**
   * @see net.sf.mmm.util.nls.api.ComposedException
   * 
   * @param error the error.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("The following errors have occurred!\n{error}")
  NlsMessage errorComposed(@Named("error")
  Object error);

  /**
   * @see net.sf.mmm.util.nls.api.IllegalCaseException
   * 
   * @param illegalCase is the illegal case.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("The case \"{case}\" is NOT covered!")
  NlsMessage errorIllegalCase(@Named("case")
  Object illegalCase);

  /**
   * @see net.sf.mmm.util.resource.api.ResourceNotAvailableException
   * 
   * @param resource is the unavailable resource.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("The resource \"{resource}\" is not available!")
  NlsMessage errorResourceNotAvailable(@Named("resource")
  Object resource);

  /**
   * @see net.sf.mmm.util.resource.api.ResourceNotWritableException
   * 
   * @param resource is the read-only resource.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("The resource \"{resource}\" is not writable!")
  NlsMessage errorResourceNotWritable(@Named("resource")
  Object resource);

  /**
   * @see net.sf.mmm.util.resource.api.ResourceUriUndefinedException
   * 
   * @param uri is the URI of the undefined resource.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("The resource URI \"{uri}\" is undefined!")
  NlsMessage errorResourceUndefinedUri(@Named("uri")
  Object uri);

  /**
   * @see net.sf.mmm.util.nls.api.NlsUnsupportedOperationException
   * 
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("An operation was invoked that is NOT supported!")
  NlsMessage errorUnsupportedOperation();

  /**
   * @see net.sf.mmm.util.nls.api.NlsUnsupportedOperationException
   * 
   * @param operation is the unsupported operation.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("The operation \"{operation}\" was invoked but is NOT supported!")
  NlsMessage errorUnsupportedOperationWithName(@Named("operation")
  Object operation);

  /**
   * @see net.sf.mmm.util.reflect.base.IllegalWildcardSequenceException
   * 
   * @param sequence is the illegal sequence that was used in a wildcard-type.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("Illegal sequence in wildcard type \"{sequence}\"!")
  NlsMessage errorTypeIllegalWildcard(@Named("sequence")
  Object sequence);

  /**
   * @see net.sf.mmm.util.reflect.api.AnnotationNotRuntimeException
   * 
   * @param annotation is the invalid {@link java.lang.annotation.Annotation}.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("The given annotation \"{annotation}\" can NOT be resolved at runtime!")
  NlsMessage errorAnnotationNotRuntime(@Named("annotation")
  Object annotation);

  /**
   * @see net.sf.mmm.util.reflect.api.AnnotationNotForTargetException
   * 
   * @param annotation is the invalid {@link java.lang.annotation.Annotation}.
   * @param target is the expected {@link java.lang.annotation.ElementType}.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("The given annotation \"{annotation}\" can NOT annotate the target \"{target}\"!")
  NlsMessage errorAnnotationNotForTarget(@Named("annotation")
  Object annotation, @Named("target")
  Object target);

  /**
   * @see net.sf.mmm.util.reflect.api.TypeNotFoundException
   * 
   * @param type is the missing {@link java.lang.reflect.Type}.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("The type \"{type}\" could NOT be found!")
  NlsMessage errorTypeNotFound(@Named("type")
  Object type);

  /**
   * @see net.sf.mmm.util.reflect.base.ContainerGrowthException
   * 
   * @param size is the size to increase.
   * @param max is the maximum allowed increase.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("Can not increase size of array or list by \"{size}\", because limit is \"{max}\"!")
  NlsMessage errorIncreaseExceedsMaxGrowth(@Named("size")
  int size, @Named("max")
  int max);

  /**
   * @see net.sf.mmm.util.reflect.base.UnknownCollectionInterfaceException
   * 
   * @param type is the {@link Class} reflecting the unknown collection.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("Unknown collection interface \"{type}\"!")
  NlsMessage errorUnknownCollectionInterface(@Named("type")
  Object type);

  /**
   * @see net.sf.mmm.util.reflect.api.InstantiationFailedException
   * 
   * @param type is the {@link java.lang.reflect.Type} that could NOT be instantiated.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("Failed to create an instance of \"{type}\"!")
  NlsMessage errorInstantiationFailed(@Named("type")
  Object type);

  /**
   * @see net.sf.mmm.util.reflect.api.InvocationFailedException
   * 
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("Invocation failed!")
  NlsMessage errorInvocationFailed();

  /**
   * @see net.sf.mmm.util.reflect.api.InvocationFailedException
   * 
   * @param object is the object on which the invocation failed.
   * @param accessible is the method or field that was invoked.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("Invocation of \"{accessible}\" failed on \"{object}\"!")
  NlsMessage errorInvocationFailedOn(@Named("object")
  Object object, @Named("accessible")
  Object accessible);

  /**
   * @see net.sf.mmm.util.reflect.api.AccessFailedException
   * 
   * @param accessible is the method, constructor or field that could not be accessed.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("Reflective access for \"{accessible}\" failed!")
  NlsMessage errorAccessFailed(@Named("accessible")
  Object accessible);

  /**
   * @see net.sf.mmm.util.io.api.BufferExceedException
   * 
   * @param length the given length.
   * @param capacity the maximum capacity.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("Offset or length \"{length}\" exceeds buffer with capacity \"{capacity}\"!")
  NlsMessage errorBufferLengthExceed(@Named("length")
  int length, @Named("capacity")
  int capacity);

  /**
   * @see net.sf.mmm.util.nls.impl.formatter.NlsFormatterChoiceNoElseConditionException
   * 
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("A choice format needs to end with an (else)-condition!")
  NlsMessage errorNlsChoiceNoElse();

  /**
   * @see net.sf.mmm.util.nls.impl.formatter.NlsFormatterChoiceOnlyElseConditionException
   * 
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("A choice format needs to have at least one regular condition before (else)-condition!")
  NlsMessage errorNlsChoiceOnlyElse();

  /**
   * @see net.sf.mmm.util.xml.base.XmlInvalidException
   * 
   * @param source is the source of the XML or <code>null</code> if unknown.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("The XML{source,choice,(?==null)''(else)' from \"'{source}'\"'} is invalid!")
  NlsMessage errorXmlInvalid(@Named("source")
  Object source);

  /**
   * @see net.sf.mmm.util.collection.base.NodeCycleException
   * 
   * @param cycle the {@link net.sf.mmm.util.collection.base.NodeCycle}.
   * @param type the type of the nodes.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("A cyclic dependency of {type}-nodes has been detected [{cycle}]!")
  NlsMessage errorNodeCycle(@Named("cycle")
  Object cycle, @Named("type")
  Object type);

  /**
   * @see net.sf.mmm.util.cli.api.CliOptionIllegalNameOrAliasException
   * 
   * @param option is the actual option.
   * @param name is the illegal name or alias.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("The name or alias \"{name}\" of \"{option}\" is illegal!")
  NlsMessage errorCliOptionNameIllegal(@Named("option")
  Object option, @Named("name")
  Object name);

  /**
   * @see net.sf.mmm.util.cli.api.CliOptionIncompatibleModesException
   * 
   * @param option1 is the first incompatible option.
   * @param option2 is the second incompatible option.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("The options \"{option1}\" and \"{option2}\" have incompatible modes and can not be mixed!")
  NlsMessage errorCliOptionIncompatibleModes(@Named("option1")
  Object option1, @Named("option2")
  Object option2);

  /**
   * @see net.sf.mmm.util.cli.api.CliParserExcepiton
   * 
   * @param type is the type of the invalid state.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("The class \"{type}\" is invalid as command-line interface state-object!")
  NlsMessage errorCliParser(@Named("type")
  Object type);

  /**
   * @see net.sf.mmm.util.nls.api.ReadOnlyException
   * 
   * @param object is the object that is read-only and can not be modified.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("Failed to modify \"{object}\" as it is read-only!")
  NlsMessage errorReadOnly(@Named("object")
  Object object);

  /**
   * @see net.sf.mmm.util.cli.api.AbstractMain
   * 
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage(INF_MAIN_MODE_HELP)
  NlsMessage infoMainModeHelp();

  /**
   * @see net.sf.mmm.util.cli.api.AbstractMain
   * 
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage(INF_MAIN_MODE_VERSION)
  NlsMessage infoMainModeVersion();

  /**
   * @see net.sf.mmm.util.cli.api.AbstractMain
   * 
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage(INF_MAIN_MODE_DEFAULT)
  NlsMessage infoMainModeDefault();

  /**
   * @see net.sf.mmm.util.cli.api.AbstractMain
   * 
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage(MSG_MAIN_OPTION_HELP_USAGE)
  NlsMessage messageMainOptionHelpUsage();

  /**
   * @see net.sf.mmm.util.cli.api.AbstractMain
   * 
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage(MSG_MAIN_MODE_HELP_USAGE)
  NlsMessage messageMainModeHelpUsage();

  /**
   * @see net.sf.mmm.util.cli.api.AbstractVersionedMain
   * 
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage(MSG_MAIN_OPTION_VERSION_USAGE)
  NlsMessage messageMainOptionVersionUsage();

  /**
   * @see net.sf.mmm.util.cli.api.AbstractMain
   * 
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage(MSG_MAIN_MODE_VERSION_USAGE)
  NlsMessage messageMainModeVersionUsage();

  /**
   * @see net.sf.mmm.util.nls.base.ResourceBundleSynchronizer
   * 
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage(MSG_SYNCHRONIZER_USAGE)
  NlsMessage messageSynchronizerUsage();

  /**
   * @see net.sf.mmm.util.nls.base.ResourceBundleSynchronizer
   * 
   * @param mainClass is the {@link Class} with the main-method.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage(MSG_SYNCHRONIZER_USAGE_MODE_DEFAULT)
  NlsMessage messageSynchronizerUsageModeDefault(@Named("mainClass")
  Object mainClass);

  /**
   * @see net.sf.mmm.util.nls.base.ResourceBundleSynchronizer
   * 
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage(MSG_SYNCHRONIZER_USAGE_LOCALES)
  NlsMessage messageSynchronizerUsageLocales();

  /**
   * @see net.sf.mmm.util.nls.base.ResourceBundleSynchronizer
   * 
   * @param operand is the operand is the name of the operand for this option.
   * @param defaultValue is the default value for this option.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage(MSG_SYNCHRONIZER_USAGE_ENCODING)
  NlsMessage messageSynchronizerUsageEncoding(@Named("operand")
  Object operand, @Named("defaultValue")
  Object defaultValue);

  /**
   * @see net.sf.mmm.util.nls.base.ResourceBundleSynchronizer
   * 
   * @param operand is the operand is the name of the operand for this option.
   * @param defaultValue is the default value for this option.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage(MSG_SYNCHRONIZER_USAGE_PATH)
  NlsMessage messageSynchronizerUsagePath(@Named("operand")
  Object operand, @Named("defaultValue")
  Object defaultValue);

  /**
   * @see net.sf.mmm.util.nls.base.ResourceBundleSynchronizer
   * 
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage(MSG_SYNCHRONIZER_USAGE_DATE_PATTERN)
  NlsMessage messageSynchronizerUsageDatePattern();

  /**
   * @see net.sf.mmm.util.nls.base.ResourceBundleSynchronizer
   * 
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage(MSG_SYNCHRONIZER_USAGE_BUNDLE_CLASS)
  NlsMessage messageSynchronizerUsageBundleClass();

  /**
   * @see net.sf.mmm.util.cli.base.AbstractCliParser
   * 
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage(MSG_CLI_USAGE)
  NlsMessage messageCliUsage();

  /**
   * @see net.sf.mmm.util.cli.base.AbstractCliParser
   * 
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage(MSG_CLI_MODE_USAGE)
  NlsMessage messageCliModeUsage();

  /**
   * @see net.sf.mmm.util.cli.base.AbstractCliParser
   * 
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage(MSG_CLI_REQUIRED_OPTIONS)
  NlsMessage messageCliRequiredOptions();

  /**
   * @see net.sf.mmm.util.cli.base.AbstractCliParser
   * 
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage(MSG_CLI_ADDITIONAL_OPTIONS)
  NlsMessage messageCliAdditionalOptions();

  /**
   * @see net.sf.mmm.util.cli.base.AbstractCliParser
   * 
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage(MSG_CLI_ARGUMENTS)
  NlsMessage messageCliArguments();

  /**
   * @see net.sf.mmm.util.cli.api.CliOptionDuplicateException
   * 
   * @param option is the duplicated option.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("Duplicate option \"{option}\"!")
  NlsMessage errorCliOptionDuplicate(@Named("option")
  String option);

  /**
   * @see net.sf.mmm.util.cli.api.CliOptionUndefinedException
   * 
   * @param option is the undefined option.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("Undefined option \"{option}\"!")
  NlsMessage errorCliOptionUndefined(@Named("option")
  String option);

  /**
   * @see net.sf.mmm.util.cli.api.CliOptionMissingValueException
   * 
   * @param option is the option with missing value.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("The option \"{option}\" must be followed by a value!")
  NlsMessage errorCliOptionMissingValue(@Named("option")
  String option);

  /**
   * @see net.sf.mmm.util.cli.api.CliOptionMissingException
   * 
   * @param option is the missing but required option.
   * @param mode is the current mode.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("The option \"{option}\" is required for mode \"{mode}\"!")
  NlsMessage errorCliOptionMissing(@Named("option")
  String option, @Named("mode")
  String mode);

  /**
   * @see net.sf.mmm.util.cli.api.CliArgumentMissingException
   * 
   * @param argument is the missing but required argument.
   * @param mode is the current mode.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("The argument \"{argument}\" is required for mode \"{mode}\"!")
  NlsMessage errorCliArgumentMissing(@Named("argument")
  String argument, @Named("mode")
  String mode);

  /**
   * @see net.sf.mmm.util.cli.api.CliArgumentReferenceMissingException
   * 
   * @param reference is the missing reference.
   * @param argument is the argument containing the reference.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("The argument \"{argument}\" referenced by \"{reference}\" is not defined!")
  NlsMessage errorCliArgumentReferenceMissing(@Named("reference")
  Object reference, @Named("argument")
  Object argument);

  /**
   * @see net.sf.mmm.util.cli.api.CliOptionMisplacedException
   * 
   * @param option is the misplaced option.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("The option \"{option}\" is misplaced and can not be given after the start of the arguments!")
  NlsMessage errorCliOptionMisplaced(@Named("option")
  String option);

  /**
   * @see net.sf.mmm.util.cli.api.CliOptionAndArgumentAnnotationException
   * 
   * @param property is the name of the annotated property.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("The property \"{property}\" can not be annotated both with @CliOption and @CliArgument!")
  NlsMessage errorCliOptionAndArgumentAnnotation(@Named("property")
  String property);

  /**
   * @see net.sf.mmm.util.cli.api.CliParameterListEmptyException
   * 
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("No parameter given! You have to supply at least one commandline parameter.")
  NlsMessage errorCliParameterListEmpty();

  /**
   * @see net.sf.mmm.util.cli.api.CliClassNoPropertyException
   * 
   * @param type is the CLI {@link Class}.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("The CLI class \"{type}\" "
      + "is illegal because it has no property annotated with @CliOption or @CliArgument!")
  NlsMessage errorCliClassNoProperty(@Named("type")
  Object type);

  /**
   * @see net.sf.mmm.util.cli.api.CliModeUndefinedException
   * 
   * @param mode is the undefined mode.
   * @param value is the referencing object.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("The mode \"{mode}\" used by \"{value}\" is undefined! You have to declare it via @CliMode or "
      + "change @CliStyle.modeUndefined() to something else than EXCEPTION.")
  NlsMessage errorCliModeUndefined(@Named("mode")
  String mode, @Named("value")
  Object value);

  /**
   * @see net.sf.mmm.util.pojo.descriptor.api.PojoPropertyNotFoundException
   * 
   * @param property is the property that was not found (typically the name of the property).
   * @param type is the type that was expected to contain the property.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("Property \"{property}\" not found in \"{type}\"!")
  NlsMessage errorPojoPropertyNotFound(@Named("property")
  Object property, @Named("type")
  Object type);

  /**
   * @see net.sf.mmm.util.pojo.descriptor.api.PojoPropertyNotFoundException
   * 
   * @param property is the property that could not be accessed (typically the name of the property).
   * @param type is the type containing the property.
   * @param mode is the mode of access.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("Property \"{property}\" not accessible for the mode \"{mode}\" in \"{type}\"!")
  NlsMessage errorPojoPropertyNotAccessible(@Named("property")
  Object property, @Named("type")
  Object type, @Named("mode")
  Object mode);

  /**
   * @see net.sf.mmm.util.pojo.path.api.IllegalPojoPathException
   * 
   * @param function is the name of the missing function.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("Undefined function \"{function}\"!")
  NlsMessage errorPojoFunctionUndefined(@Named("function")
  String function);

  /**
   * @see net.sf.mmm.util.pojo.path.api.PojoPathFunctionUnsupportedOperationException
   * 
   * @param function is the name of the function.
   * @param operation is the name of the unsupported operation.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("The function \"{function}\" does NOT support the operation \"{operation}\"!")
  NlsMessage errorPojoFunctionUnsupportedOperation(@Named("function")
  String function, @Named("operation")
  String operation);

  /**
   * @see net.sf.mmm.util.pojo.path.api.PojoPathUnsafeException
   * 
   * @param path is the {@link net.sf.mmm.util.pojo.path.api.PojoPath}.
   * @param type is the current type for which the path is unsafe.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("The pojo-path \"{path}\" is unsafe for type \"{type}\"!")
  NlsMessage errorPojoPathUnsafe(@Named("path")
  String path, @Named("type")
  Object type);

  /**
   * @see net.sf.mmm.util.pojo.path.api.PojoPathSegmentIsNullException
   * 
   * @param path is the {@link net.sf.mmm.util.pojo.path.api.PojoPath}.
   * @param object is the initial {@link Object} the path was invoked on resulting null.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("The pojo-path \"{path}\" for object \"{object}\" evaluates to null!")
  NlsMessage errorPojoPathSegmentIsNull(@Named("path")
  String path, @Named("object")
  Object object);

  /**
   * @see net.sf.mmm.util.pojo.path.api.PojoPathCreationException
   * 
   * @param path is the {@link net.sf.mmm.util.pojo.path.api.PojoPath}.
   * @param object is the current object at the path.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("Failed to create the object at the pojo-path \"{path}\" for object \"{object}\"!")
  NlsMessage errorPojoPathCreation(@Named("path")
  String path, @Named("object")
  Object object);

  /**
   * @see net.sf.mmm.util.pojo.path.api.PojoPathAccessException
   * 
   * @param path is the {@link net.sf.mmm.util.pojo.path.api.PojoPath}.
   * @param type is the current type that does not support the path.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("Failed to access the pojo-path \"{path}\" for current object of type \"{type}\"!")
  NlsMessage errorPojoPathAccess(@Named("path")
  String path, @Named("type")
  Object type);

  /**
   * @see net.sf.mmm.util.pojo.path.api.IllegalPojoPathException
   * 
   * @param path is the {@link net.sf.mmm.util.pojo.path.api.PojoPath}.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("Illegal pojo-path \"{path}\"!")
  NlsMessage errorPojoPathIllegal(@Named("path")
  String path);

  /**
   * @see net.sf.mmm.util.pojo.path.base.PojoPathCachingDisabledException
   * 
   * @param path is the {@link net.sf.mmm.util.pojo.path.api.PojoPath}.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("Caching was required for pojo-path \"{path}\" but is disabled!")
  NlsMessage errorPojoPathCachingDisabled(@Named("path")
  String path);

  /**
   * @see net.sf.mmm.util.pojo.path.api.PojoPathConversionException
   * 
   * @param path is the {@link net.sf.mmm.util.pojo.path.api.PojoPath}.
   * @param type is the actual type.
   * @param targetType is the type to convert to.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("Can NOT convert from \"{type}\" to \"{targetType}\" for pojo-path \"{path}\"!")
  NlsMessage errorPojoPathConversion(@Named("path")
  String path, @Named("type")
  Object type, @Named("targetType")
  Object targetType);

  /**
   * @see net.sf.mmm.util.nls.api.NlsClassCastException
   * 
   * @param object is the object that should be cased.
   * @param source is the actual type of the <code>object</code>.
   * @param target is the type the <code>object</code> should be casted to.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("Can NOT cast \"{object}\" from \"{source}\" to \"{target}\"!")
  NlsMessage errorCast(@Named("object")
  Object object, @Named("source")
  Type source, @Named("target")
  Type target);

  /**
   * @see net.sf.mmm.util.nls.api.ObjectDisposedException
   * 
   * @param object is the disposed object.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("The object \"{object}\" has already been disposed!")
  NlsMessage errorObjectDisposed(@Named("object")
  Object object);

}
