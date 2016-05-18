/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.validation;

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
public interface NlsBundleUtilValidationRoot extends NlsBundle {

  /**
   * @see net.sf.mmm.util.validation.api.ValidationErrorUserException
   *
   * @return the {@link NlsMessage}
   * @deprecated - use {@link net.sf.mmm.util.NlsBundleUtilCoreRoot#errorValidation()}
   */
  @Deprecated
  @NlsBundleMessage("Validation failed - please ensure to provide complete and correct data.")
  NlsMessage errorValidation();

  /**
   * @see net.sf.mmm.util.validation.base.text.ValidatorPattern
   *
   * @param value is the value that does NOT match the expected format.
   * @param format is the expected format.
   * @param source is the source of the value.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("The value has to match the format \"{format}\"!")
  NlsMessage errorValueFormat(@Named("value") Object value, @Named("format") Object format);

  /**
   * @return the {@link NlsMessage}.
   */
  @NlsBundleMessage("The value has to be filled.")
  NlsMessage errorMandatory();

  /**
   * @see net.sf.mmm.util.validation.base.ValidatorCompare
   *
   * @param value is the invalid value.
   * @param comparator is the {@link net.sf.mmm.util.lang.api.CompareOperator}.
   * @param value2 is the value to compare to (second argument).
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("The value needs to be {comparator} \"{value2}\"!")
  NlsMessage errorValueComparison(@Named("value") Object value, @Named("comparator") Object comparator,
      @Named("value2") Object value2);

  /**
   * @see net.sf.mmm.util.validation.base.ValidatorCompare
   *
   * @param value is the invalid value.
   * @param comparator is the {@link net.sf.mmm.util.lang.api.CompareOperator}.
   * @param value2 is the value to compare to (second argument).
   * @param source is the source of the value or {@code null} if NOT available.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("The value needs to be {comparator} the value from \"{source}\" ({value2})!")
  NlsMessage errorValueComparisonWithSource(@Named("value") Object value, @Named("comparator") Object comparator,
      @Named("value2") Object value2, @Named("source") Object source);

  /**
   * @param value is the invalid value.
   * @return the {@link NlsMessage}.
   */
  @NlsBundleMessage("The value has to be in the past.")
  NlsMessage errorValueNotInPast(@Named("value") Object value);

  /**
   * @param value is the invalid value.
   * @return the {@link NlsMessage}.
   */
  @NlsBundleMessage("The value has to be in the future.")
  NlsMessage errorValueNotInFuture(@Named("value") Object value);

  /**
   * @param value is the invalid value.
   * @param other the upper bound.
   * @return the {@link NlsMessage}.
   */
  @NlsBundleMessage("The value has to be after \"{other}\".")
  NlsMessage errorValueNotAfter(@Named("value") Object value, @Named("other") Object other);

  /**
   * @param value is the invalid value.
   * @param other the lower bound.
   * @return the {@link NlsMessage}.
   */
  @NlsBundleMessage("The value has to be before \"{other}\".")
  NlsMessage errorValueNotBefore(@Named("value") Object value, @Named("other") Object other);

}
