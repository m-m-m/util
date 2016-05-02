/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.value;

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
public interface NlsBundleUtilValueRoot extends NlsBundle {

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
   * @see net.sf.mmm.util.value.api.ValueOutOfRangeException
   *
   * @param value is the invalid value.
   * @param min is the minimum value.
   * @param max is the maximum value.
   * @param source is the source of the value or {@code null} if NOT available.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("The value {value}{source,choice,(?==null)''(else)' from \"'{source}'\"'} needs to be in the range from {min} to {max}.")
  NlsMessage errorValueOutOfRange(@Named("value") Object value, @Named("min") Object min, @Named("max") Object max,
      @Named("source") Object source);

  /**
   * @param value is the value that could NOT be converted.
   * @param type is the type to convert to.
   * @param source is the source of the value.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("The value \"{value}\"{source,choice,(?==null)''(else)' from \"'{source}'\"'} could NOT be converted to \"{type}\"!")
  NlsMessage errorValueConvert(@Named("value") Object value, @Named("type") Type type,
      @Named("source") Object source);

}
