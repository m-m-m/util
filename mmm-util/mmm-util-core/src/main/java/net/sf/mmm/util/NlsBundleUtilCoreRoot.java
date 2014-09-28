/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util;

import java.lang.reflect.Type;
import java.util.Date;

import javax.inject.Named;

import net.sf.mmm.util.lang.api.Comparator;
import net.sf.mmm.util.nls.api.NlsBundle;
import net.sf.mmm.util.nls.api.NlsBundleMessage;
import net.sf.mmm.util.nls.api.NlsMessage;

/**
 * This interface holds the {@link NlsBundle internationalized messages} for this module.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.0.0
 */
public interface NlsBundleUtilCoreRoot extends NlsBundle {

  /** @see #infoSouth() */
  String INF_SOUTH = "south";

  /** @see #infoEast() */
  String INF_EAST = "east";

  /** @see #infoWest() */
  String INF_WEST = "west";

  /** @see #infoNorth() */
  String INF_NORTH = "north";

  /** @see #infoSouthEast() */
  String INF_SOUTH_EAST = "south-east";

  /** @see #infoSouthWest() */
  String INF_SOUTH_WEST = "south-west";

  /** @see #infoNorthEast() */
  String INF_NORTH_EAST = "north-east";

  /** @see #infoNorthWest() */
  String INF_NORTH_WEST = "north-west";

  /** @see #infoLeft() */
  String INF_LEFT = "left";

  /** @see #infoRight() */
  String INF_RIGHT = "right";

  /** @see #infoCenter() */
  String INF_CENTER = "center";

  /** @see #infoTop() */
  String INF_TOP = "top";

  /** @see #infoTopLeft() */
  String INF_TOP_LEFT = "top left";

  /** @see #infoTopRight() */
  String INF_TOP_RIGHT = "top right";

  /** @see #infoBottom() */
  String INF_BOTTOM = "bottom";

  /** @see #infoBottomLeft() */
  String INF_BOTTOM_LEFT = "bottom left";

  /** @see #infoBottomRight() */
  String INF_BOTTOM_RIGHT = "bottom right";

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
  String INF_GREATER_THAN = "greater than";

  /** @see #infoGreaterOrEqual() */
  String INF_GREATER_OR_EQUAL = "greater or equal to";

  /** @see #infoEqual() */
  String INF_EQUAL = "equal to";

  /** @see #infoNotEqual() */
  String INF_NOT_EQUAL = "not equal to";

  /** @see #infoLessThan() */
  String INF_LESS_THAN = "less than";

  /** @see #infoLessOrEqual() */
  String INF_LESS_OR_EQUAL = "less or equal";

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
   * @see net.sf.mmm.util.lang.api.Direction#SOUTH
   *
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage(INF_SOUTH)
  NlsMessage infoSouth();

  /**
   * @see net.sf.mmm.util.lang.api.Direction#EAST
   *
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage(INF_EAST)
  NlsMessage infoEast();

  /**
   * @see net.sf.mmm.util.lang.api.Direction#WEST
   *
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage(INF_WEST)
  NlsMessage infoWest();

  /**
   * @see net.sf.mmm.util.lang.api.Direction#NORTH
   *
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage(INF_NORTH)
  NlsMessage infoNorth();

  /**
   * @see net.sf.mmm.util.lang.api.Direction#SOUTH_EAST
   *
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage(INF_SOUTH_EAST)
  NlsMessage infoSouthEast();

  /**
   * @see net.sf.mmm.util.lang.api.Direction#SOUTH_WEST
   *
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage(INF_SOUTH_WEST)
  NlsMessage infoSouthWest();

  /**
   * @see net.sf.mmm.util.lang.api.Direction#NORTH_EAST
   *
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage(INF_NORTH_EAST)
  NlsMessage infoNorthEast();

  /**
   * @see net.sf.mmm.util.lang.api.Direction#NORTH_WEST
   *
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage(INF_NORTH_WEST)
  NlsMessage infoNorthWest();

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
   * @return the {@link NlsMessage} for the text "Undefined".
   */
  @NlsBundleMessage("Undefined")
  NlsMessage infoUndefined();

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
  NlsMessage errorValueWrongType(@Named("value") Object value, @Named("valueType") Object valueType,
      @Named("targetType") Object targetType, @Named("source") Object source);

  /**
   * @see net.sf.mmm.util.value.api.ValueNotSetException
   *
   * @param value is the invalid value.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("The value from \"{source}\" is not set!")
  NlsMessage errorValueNotSet(@Named("source") Object value);

  /**
   * @see net.sf.mmm.util.exception.api.NlsParseException
   *
   * @param value is the invalid value.
   * @param type is the expected type.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("Failed to parse \"{value}\" - expected \"{type}\"!")
  NlsMessage errorParseExpected(@Named("value") Object value, @Named("type") Object type);

  /**
   * @see net.sf.mmm.util.exception.api.NlsParseException
   *
   * @param value is the invalid value.
   * @param type is the expected type.
   * @param source is the source of the value.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("Failed to parse \"{value}\"{source,choice,(?==null)''(else)' from \"'{source}'\"'} as value of the type \"{type}\"!")
  NlsMessage errorParseType(@Named("value") Object value, @Named("type") Object type, @Named("source") Object source);

  /**
   * @see net.sf.mmm.util.exception.api.NlsParseException
   *
   * @param value is the value that could NOT be parsed.
   * @param format is the expected format.
   * @param type is the target type for the value to parse.
   * @param source is the source of the value.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("Failed to parse \"{value}\"{source,choice,(?==null)''(else)' from \"'{source}'\"'} as \"{type}\" - required format is \"{format}\"!")
  NlsMessage errorParseFormat(@Named("value") Object value, @Named("format") Object format, @Named("type") Object type,
      @Named("source") Object source);

  /**
   * @see net.sf.mmm.util.value.api.ValueOutOfRangeException
   *
   * @param value is the invalid value.
   * @param min is the minimum value.
   * @param max is the maximum value.
   * @param source is the source of the value or <code>null</code> if NOT available.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("The value {value}{source,choice,(?==null)''(else)' from \"'{source}'\"'} needs to be in the range from {min} to {max}.")
  NlsMessage errorValueOutOfRange(@Named("value") Object value, @Named("min") Object min, @Named("max") Object max,
      @Named("source") Object source);

  /**
   * @see net.sf.mmm.util.validation.base.ValidatorCompare
   *
   * @param value is the invalid value.
   * @param comparator is the {@link Comparator}.
   * @param value2 is the value to compare to (second argument).
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("The value ({value}) needs to be {comparator} \"{value2}\"!")
  NlsMessage errorValueComparison(@Named("value") Object value, @Named("comparator") Comparator comparator,
      @Named("value2") Object value2);

  /**
   * @see net.sf.mmm.util.validation.base.ValidatorCompare
   *
   * @param value is the invalid value.
   * @param comparator is the {@link Comparator}.
   * @param value2 is the value to compare to (second argument).
   * @param source is the source of the value or <code>null</code> if NOT available.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("The value ({value}) needs to be {comparator} the value from \"{source}\" ({value2})!")
  NlsMessage errorValueComparisonWithSource(@Named("value") Object value, @Named("comparator") Comparator comparator,
      @Named("value2") Object value2, @Named("source") Object source);

  /**
   * @see net.sf.mmm.util.value.api.ValueConvertException
   *
   * @param value is the value that could NOT be converted.
   * @param type is the type to convert to.
   * @param source is the source of the value.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("The value \"{value}\"{source,choice,(?==null)''(else)' from \"'{source}'\"'} could NOT be converted to \"{type}\"!")
  NlsMessage errorValueConvert(@Named("value") Object value, @Named("type") Type type, @Named("source") Object source);

  /**
   * @see net.sf.mmm.util.component.api.ResourceMissingException
   *
   * @param resource is the identifier (path, URL, etc.) of the missing resource.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("The required resource \"{resource}\" is missing!")
  NlsMessage errorResourceMissing(@Named("resource") String resource);

  /**
   * @see net.sf.mmm.util.component.api.ResourceAmbiguousException
   *
   * @param resource is the identifier (path, URL, etc.) of the ambiguous resource.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("The required resource \"{resource}\" is ambiguous!")
  NlsMessage errorResourceAmbiguous(@Named("resource") String resource);

  /**
   * @see net.sf.mmm.util.component.api.ResourceAmbiguousException
   *
   * @param resource is the identifier (path, URL, class, etc.) of the missing resource.
   * @param ids are the IDs of the ambiguous resources.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("The required resource \"{resource}\" is ambiguous!\n{ids}")
  NlsMessage errorResourceAmbiguousWithIds(@Named("resource") String resource, @Named("ids") String... ids);

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
   * @see net.sf.mmm.util.exception.api.NlsIllegalArgumentException
   *
   * @param value is the illegal value of the argument.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("The given argument \"{value}\" is illegal!")
  NlsMessage errorIllegalArgument(@Named("value") Object value);

  /**
   * @see net.sf.mmm.util.exception.api.NlsIllegalArgumentException
   *
   * @param value is the illegal value of the argument.
   * @param name is the name of the argument.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("The given value \"{value}\" is illegal for the argument \"{name}\"!")
  NlsMessage errorIllegalArgumentWithName(@Named("value") Object value, @Named("name") String name);

  /**
   * @see net.sf.mmm.util.date.api.IllegalDateFormatException
   *
   * @param value is the illegal date {@link String}.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("Illegal date \"{value}\"!")
  NlsMessage errorIllegalDate(@Named("value") String value);

  /**
   * @see net.sf.mmm.util.math.api.NumberConversionException
   *
   * @param value is the value that could NOT be converted.
   * @param type is the type the value should be converted to.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("Can not convert number \"{value}\" to \"{type}\"!")
  NlsMessage errorNumberConversion(@Named("value") Object value, @Named("type") Object type);

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
  @NlsBundleMessage("An unexpetected error has occurred while reading data!")
  NlsMessage errorIoRead();

  /**
   * @see net.sf.mmm.util.io.api.RuntimeIoException
   *
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("An unexpetected error has occurred while writing data!")
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
  @NlsBundleMessage("An unexpetected error has occurred while copying data!")
  NlsMessage errorIoCopy();

  /**
   * @see net.sf.mmm.util.exception.api.NlsNullPointerException
   *
   * @param object is the name of the object that is <code>null</code>.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("The object \"{object}\" is null!")
  NlsMessage errorArgumentNull(@Named("object") Object object);

  /**
   * @see net.sf.mmm.util.exception.api.TechnicalErrorUserException
   *
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("An unexpected error has occurred! We apologize any inconvenience. Please try again later.")
  NlsMessage errorTechnical();

  /**
   * @see net.sf.mmm.util.validation.api.ValidationErrorUserException
   *
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("Validation failed - please ensure to provide complete and correct data.")
  NlsMessage errorValidation();

  /**
   * @see net.sf.mmm.util.security.api.SecurityErrorUserException
   *
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("The operation failed due to security restrictions. Please contact the support in case of a permission problem.")
  NlsMessage errorSecurityRestriction();

  /**
   * @see net.sf.mmm.util.exception.api.DuplicateObjectException
   *
   * @param object is the duplicate object.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("Duplicate object \"{object}\"!")
  NlsMessage errorDuplicateObject(@Named("object") Object object);

  /**
   * @see net.sf.mmm.util.exception.api.DuplicateObjectException
   *
   * @param object is the duplicate object.
   * @param key is the key associated with the object (e.g. in a {@link java.util.Map}).
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("Duplicate object \"{object}\" for key \"{key}\"!")
  NlsMessage errorDuplicateObjectWithKey(@Named("object") Object object, @Named("key") Object key);

  /**
   * @see net.sf.mmm.util.exception.api.DuplicateObjectException
   *
   * @param object is the duplicate object.
   * @param key is the key associated with the object (e.g. in a {@link java.util.Map}).
   * @param existing is the object already associated with the given <code>key</code>.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("Duplicate object \"{object}\" for key \"{key}\" - already mapped to \"{existing}\"!")
  NlsMessage errorDuplicateObjectWithKeyAndExisting(@Named("object") Object object, @Named("key") Object key,
      @Named("existing") Object existing);

  /**
   * @see net.sf.mmm.util.exception.api.ObjectMismatchException
   *
   * @param object is the mismatching object.
   * @param expected is the expected object (e.g. type).
   * @param source is the source of the mismatching object or <code>null</code> if unknown.
   * @param property is the property holding the mismatching object or <code>null</code> if undefined.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("Mismatch detected{source,choice,(?==null)''(else)' in \"'{source}'\"'}"
      + "{property,choice,(?==null)''(else)' for \"'{property}'\"'}: found \"{object}\", but expected \"{expected}\"!")
  NlsMessage errorObjectMismatch(@Named("object") Object object, @Named("expected") Object expected,
      @Named("source") Object source, @Named("property") Object property);

  /**
   * @see net.sf.mmm.util.exception.api.ObjectNotFoundException
   *
   * @param object describes the missing object (e.g. the expected type).
   * @param key is the key associated with the object (e.g. in a {@link java.util.Map}).
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("Could NOT find object \"{object}\"{key,choice,(?==null)''(else)' for \"'{key}'\"'}!")
  NlsMessage errorObjectNotFound(@Named("object") Object object, @Named("key") Object key);

  /**
   * @see net.sf.mmm.util.exception.api.NlsIllegalStateException
   *
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("Illegal state!")
  NlsMessage errorIllegalState();

  /**
   * @see net.sf.mmm.util.exception.api.ComposedException
   *
   * @param error the error.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("The following errors have occurred!\n{error}")
  NlsMessage errorComposed(@Named("error") Object error);

  /**
   * @see net.sf.mmm.util.exception.api.IllegalCaseException
   *
   * @param illegalCase is the illegal case.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("The case \"{case}\" is NOT covered!")
  NlsMessage errorIllegalCase(@Named("case") Object illegalCase);

  /**
   * @see net.sf.mmm.util.exception.api.NlsUnsupportedOperationException
   *
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("An operation was invoked that is NOT supported!")
  NlsMessage errorUnsupportedOperation();

  /**
   * @see net.sf.mmm.util.exception.api.NlsUnsupportedOperationException
   *
   * @param operation is the unsupported operation.
   * @param source is the source object that does not support the operation.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("The operation \"{operation}\" was invoked{source,choice,(?==null)''(else)' on \"'{source}'\"'} but is NOT supported!")
  NlsMessage errorUnsupportedOperationWithName(@Named("operation") Object operation, @Named("source") Object source);

  /**
   * @see net.sf.mmm.util.reflect.base.IllegalWildcardSequenceException
   *
   * @param sequence is the illegal sequence that was used in a wildcard-type.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("Illegal sequence in wildcard type \"{sequence}\"!")
  NlsMessage errorTypeIllegalWildcard(@Named("sequence") Object sequence);

  /**
   * @see net.sf.mmm.util.reflect.api.AnnotationNotRuntimeException
   *
   * @param annotation is the invalid {@link java.lang.annotation.Annotation}.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("The given annotation \"{annotation}\" can NOT be resolved at runtime!")
  NlsMessage errorAnnotationNotRuntime(@Named("annotation") Object annotation);

  /**
   * @see net.sf.mmm.util.reflect.api.AnnotationNotForTargetException
   *
   * @param annotation is the invalid {@link java.lang.annotation.Annotation}.
   * @param target is the expected {@link java.lang.annotation.ElementType}.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("The given annotation \"{annotation}\" can NOT annotate the target \"{target}\"!")
  NlsMessage errorAnnotationNotForTarget(@Named("annotation") Object annotation, @Named("target") Object target);

  /**
   * @see net.sf.mmm.util.reflect.api.TypeNotFoundException
   *
   * @param type is the missing {@link java.lang.reflect.Type}.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("The type \"{type}\" could NOT be found!")
  NlsMessage errorTypeNotFound(@Named("type") Object type);

  /**
   * @see net.sf.mmm.util.reflect.base.ContainerGrowthException
   *
   * @param size is the size to increase.
   * @param max is the maximum allowed increase.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("Can not increase size of array or list by \"{size}\", because limit is \"{max}\"!")
  NlsMessage errorIncreaseExceedsMaxGrowth(@Named("size") int size, @Named("max") int max);

  /**
   * @see net.sf.mmm.util.reflect.base.UnknownCollectionInterfaceException
   *
   * @param type is the {@link Class} reflecting the unknown collection.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("Unknown collection interface \"{type}\"!")
  NlsMessage errorUnknownCollectionInterface(@Named("type") Object type);

  /**
   * @see net.sf.mmm.util.reflect.api.InstantiationFailedException
   *
   * @param type is the {@link java.lang.reflect.Type} that could NOT be instantiated.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("Failed to create an instance of \"{type}\"!")
  NlsMessage errorInstantiationFailed(@Named("type") Object type);

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
   * @param operation is the operation that failed.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("Invocation of \"{operation}\" failed!")
  NlsMessage errorInvocationFailedOf(@Named("operation") Object operation);

  /**
   * @see net.sf.mmm.util.reflect.api.InvocationFailedException
   *
   * @param object is the object on which the invocation failed.
   * @param accessible is the method or field that was invoked.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("Invocation of \"{accessible}\" failed on \"{object}\"!")
  NlsMessage errorInvocationFailedOn(@Named("object") Object object, @Named("accessible") Object accessible);

  /**
   * @see net.sf.mmm.util.reflect.api.AccessFailedException
   *
   * @param accessible is the method, constructor or field that could not be accessed.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("Reflective access for \"{accessible}\" failed!")
  NlsMessage errorAccessFailed(@Named("accessible") Object accessible);

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
  NlsMessage errorXmlInvalid(@Named("source") Object source);

  /**
   * @see net.sf.mmm.util.collection.base.NodeCycleException
   *
   * @param cycle the {@link net.sf.mmm.util.collection.base.NodeCycle}.
   * @param type the type of the nodes.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("A cyclic dependency of {type}-nodes has been detected [{cycle}]!")
  NlsMessage errorNodeCycle(@Named("cycle") Object cycle, @Named("type") Object type);

  /**
   * @see net.sf.mmm.util.exception.api.ReadOnlyException
   *
   * @param object is the object that is read-only and can not be modified.
   * @param attribute is the attribute that is read-only or <code>null</code> for the entire object.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("Failed to modify{attribute,choice,(?==null)''(else)' attribute \"'{attribute}'\" of'} \"{object}\" as it is read-only!")
  NlsMessage errorReadOnly(@Named("object") Object object, @Named("attribute") Object attribute);

  /**
   * @see net.sf.mmm.util.pojo.descriptor.api.PojoPropertyNotFoundException
   *
   * @param property is the property that was not found (typically the name of the property).
   * @param type is the type that was expected to contain the property.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("Property \"{property}\" not found in \"{type}\"!")
  NlsMessage errorPojoPropertyNotFound(@Named("property") Object property, @Named("type") Object type);

  /**
   * @see net.sf.mmm.util.pojo.descriptor.api.PojoPropertyNotFoundException
   *
   * @param property is the property that could not be accessed (typically the name of the property).
   * @param type is the type containing the property.
   * @param mode is the mode of access.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("Property \"{property}\" not accessible for the mode \"{mode}\" in \"{type}\"!")
  NlsMessage errorPojoPropertyNotAccessible(@Named("property") Object property, @Named("type") Object type,
      @Named("mode") Object mode);

  /**
   * @see net.sf.mmm.util.pojo.path.api.PojoPathUnsafeException
   *
   * @param path is the {@link net.sf.mmm.util.pojo.path.api.PojoPath}.
   * @param type is the current type for which the path is unsafe.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("The pojo-path \"{path}\" is unsafe for type \"{type}\"!")
  NlsMessage errorPojoPathUnsafe(@Named("path") String path, @Named("type") Object type);

  /**
   * @see net.sf.mmm.util.pojo.path.api.PojoPathSegmentIsNullException
   *
   * @param path is the {@link net.sf.mmm.util.pojo.path.api.PojoPath}.
   * @param object is the initial {@link Object} the path was invoked on resulting null.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("The pojo-path \"{path}\" for object \"{object}\" evaluates to null!")
  NlsMessage errorPojoPathSegmentIsNull(@Named("path") String path, @Named("object") Object object);

  /**
   * @see net.sf.mmm.util.pojo.path.api.PojoPathCreationException
   *
   * @param path is the {@link net.sf.mmm.util.pojo.path.api.PojoPath}.
   * @param object is the current object at the path.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("Failed to create the object at the pojo-path \"{path}\" for object \"{object}\"!")
  NlsMessage errorPojoPathCreation(@Named("path") String path, @Named("object") Object object);

  /**
   * @see net.sf.mmm.util.pojo.path.api.PojoPathAccessException
   *
   * @param path is the {@link net.sf.mmm.util.pojo.path.api.PojoPath}.
   * @param type is the current type that does not support the path.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("Failed to access the pojo-path \"{path}\" for current object of type \"{type}\"!")
  NlsMessage errorPojoPathAccess(@Named("path") String path, @Named("type") Object type);

  /**
   * @see net.sf.mmm.util.pojo.path.api.IllegalPojoPathException
   *
   * @param path is the {@link net.sf.mmm.util.pojo.path.api.PojoPath}.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("Illegal pojo-path \"{path}\"!")
  NlsMessage errorPojoPathIllegal(@Named("path") String path);

  /**
   * @see net.sf.mmm.util.pojo.path.base.PojoPathCachingDisabledException
   *
   * @param path is the {@link net.sf.mmm.util.pojo.path.api.PojoPath}.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("Caching was required for pojo-path \"{path}\" but is disabled!")
  NlsMessage errorPojoPathCachingDisabled(@Named("path") String path);

  /**
   * @see net.sf.mmm.util.pojo.path.api.PojoPathConversionException
   *
   * @param path is the {@link net.sf.mmm.util.pojo.path.api.PojoPath}.
   * @param type is the actual type.
   * @param targetType is the type to convert to.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("Can NOT convert from \"{type}\" to \"{targetType}\" for pojo-path \"{path}\"!")
  NlsMessage errorPojoPathConversion(@Named("path") String path, @Named("type") Object type,
      @Named("targetType") Object targetType);

  /**
   * @see net.sf.mmm.util.exception.api.NlsClassCastException
   *
   * @param object is the object that should be cased.
   * @param source is the actual type of the <code>object</code>.
   * @param target is the type the <code>object</code> should be casted to.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("Can NOT cast \"{object}\" from \"{source}\" to \"{target}\"!")
  NlsMessage errorCast(@Named("object") Object object, @Named("source") Type source, @Named("target") Type target);

  /**
   * @see net.sf.mmm.util.exception.api.ObjectDisposedException
   *
   * @param object is the disposed object.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("The object \"{object}\" has already been disposed!")
  NlsMessage errorObjectDisposed(@Named("object") Object object);

  /**
   * @return the {@link NlsMessage}.
   */
  @NlsBundleMessage("The value has to be filled.")
  NlsMessage errorMandatory();

  /**
   * @see net.sf.mmm.util.search.api.SearchTimeoutException
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("Your search query was canceled because it exceeded a given timeout! Please try to simplify, "
      + "specialize to match less hits, or try again later.")
  NlsMessage errorSearchTimeout();

  /**
   * @param value is the invalid value.
   * @return the {@link NlsMessage}.
   */
  @NlsBundleMessage("The value has to be in the past.")
  NlsMessage errorValueNotInPast(@Named("value") Date value);

  /**
   * @param value is the invalid value.
   * @return the {@link NlsMessage}.
   */
  @NlsBundleMessage("The value has to be in the future.")
  NlsMessage errorValueNotInFuture(@Named("value") Date value);

}
