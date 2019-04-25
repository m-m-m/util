/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.exception;

import java.lang.reflect.Type;

import javax.inject.Named;

import net.sf.mmm.util.nls.api.NlsBundle;
import net.sf.mmm.util.nls.api.NlsBundleMessage;
import net.sf.mmm.util.nls.api.NlsMessage;

/**
 * This interface holds the {@link NlsBundle internationalized messages} for this module.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.0.0
 */
public interface NlsBundleUtilExceptionRoot extends NlsBundle {

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
  NlsMessage errorParseFormat(@Named("value") Object value, @Named("format") Object format, @Named("type") Object type, @Named("source") Object source);

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
   * @see net.sf.mmm.util.exception.api.NlsNullPointerException
   *
   * @param object is the name of the object that is {@code null}.
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
   * @param existing is the object already associated with the given {@code key}.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("Duplicate object \"{object}\" for key \"{key}\" - already mapped to \"{existing}\"!")
  NlsMessage errorDuplicateObjectWithKeyAndExisting(@Named("object") Object object, @Named("key") Object key, @Named("existing") Object existing);

  /**
   * @see net.sf.mmm.util.exception.api.ObjectMismatchException
   *
   * @param object is the mismatching object.
   * @param expected is the expected object (e.g. type).
   * @param source is the source of the mismatching object or {@code null} if unknown.
   * @param property is the property holding the mismatching object or {@code null} if undefined.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("Mismatch detected{source,choice,(?==null)''(else)' in \"'{source}'\"'}"
      + "{property,choice,(?==null)''(else)' for \"'{property}'\"'}: found \"{object}\", but expected \"{expected}\"!")
  NlsMessage errorObjectMismatch(@Named("object") Object object, @Named("expected") Object expected, @Named("source") Object source,
      @Named("property") Object property);

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
   * @see net.sf.mmm.util.exception.api.ReadOnlyException
   *
   * @param object is the object that is read-only and can not be modified.
   * @param attribute is the attribute that is read-only or {@code null} for the entire object.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("Failed to modify{attribute,choice,(?==null)''(else)' attribute \"'{attribute}'\" of'} \"{object}\" as it is read-only!")
  NlsMessage errorReadOnly(@Named("object") Object object, @Named("attribute") Object attribute);

  /**
   * @see net.sf.mmm.util.exception.api.NlsClassCastException
   *
   * @param object is the object that should be cased.
   * @param source is the actual type of the {@code object}.
   * @param target is the type the {@code object} should be casted to.
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
   * @see net.sf.mmm.util.exception.api.ValidationErrorUserException
   *
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("Validation failed - please ensure to provide complete and correct data.")
  NlsMessage errorValidation();

  /**
   * @see net.sf.mmm.util.exception.api.WrongValueTypeException
   *
   * @param value is the invalid value.
   * @param valueType is the actual type of the value.
   * @param targetType is the expected type of the value.
   * @param source is the source of the value.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("The value \"{value}\"{source,choice,(?==null)''(else)' from \"'{source}'\"'} with the type "
      + "\"{valueType}\" can NOT be converted to the requested type \"{targetType}\"!")
  NlsMessage errorValueWrongType(@Named("value") Object value, @Named("valueType") Object valueType, @Named("targetType") Object targetType,
      @Named("source") Object source);

  /**
   * @see net.sf.mmm.util.exception.api.ValueNotSetException
   *
   * @param value is the invalid value.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("The value from \"{source}\" is not set!")
  NlsMessage errorValueNotSet(@Named("source") Object value);

  /**
   * @see net.sf.mmm.util.exception.api.ValueOutOfRangeException
   *
   * @param value is the invalid value.
   * @param min is the minimum value.
   * @param max is the maximum value.
   * @param source is the source of the value or {@code null} if NOT available.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("The value {value}{source,choice,(?==null)''(else)' from \"'{source}'\"'} needs to be in the range from {min} to {max}.")
  NlsMessage errorValueOutOfRange(@Named("value") Object value, @Named("min") Object min, @Named("max") Object max, @Named("source") Object source);

  /**
   * @see net.sf.mmm.util.exception.api.ValueConvertException
   *
   * @param value is the value that could NOT be converted.
   * @param type is the type to convert to.
   * @param source is the source of the value.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("The value \"{value}\"{source,choice,(?==null)''(else)' from \"'{source}'\"'} could NOT be converted to \"{type}\"!")
  NlsMessage errorValueConvert(@Named("value") Object value, @Named("type") Type type, @Named("source") Object source);

  /**
   * @see net.sf.mmm.util.exception.api.OptimisticLockingException
   *
   * @param entity the entity representation (e.g. simple name).
   * @param id the ID of the entity.
   * @return the {@link NlsMessage}.
   */
  @NlsBundleMessage("The operation failed due to a conflict of concurrent modification. "
      + "Please save your changes via copy and paste or screenshot, reject your changes and repeat the operation. "
      + "The conflict concerns the entity {entity} with primary key {id}.")
  NlsMessage errorOptimisticLocking(Object entity, Object id);

  /**
   * @param service the service that failed (e.g. qualified name of service interface).
   * @param message the actual error message.
   * @return the {@link NlsMessage}.
   */
  @NlsBundleMessage("While invoking the service {service} the following error occurred: {message}. Probably the service is temporary unavailable. "
      + "Please try again later. If the problem persists contact your system administrator.")
  NlsMessage errorServiceInvocationFailed(@Named("service") String service, @Named("message") String message);

  /**
   * @see net.sf.mmm.util.exception.api.SecurityErrorUserException
   *
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("The operation failed due to security restrictions. Please contact the support in case of a permission problem.")
  NlsMessage errorSecurityRestriction();

}
