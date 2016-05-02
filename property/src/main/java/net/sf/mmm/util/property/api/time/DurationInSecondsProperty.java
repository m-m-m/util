/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.api.time;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

import net.sf.mmm.util.bean.api.Bean;
import net.sf.mmm.util.property.api.lang.LongProperty;
import net.sf.mmm.util.validation.base.AbstractValidator;

/**
 * Extends {@link LongProperty} to store a {@link Duration} in seconds.
 *
 * @author hohwille
 * @since 8.0.0
 */
public class DurationInSecondsProperty extends LongProperty {

  /**
   * The constructor.
   *
   * @param name - see {@link #getName()}.
   * @param bean - see {@link #getBean()}.
   */
  public DurationInSecondsProperty(String name, Bean bean) {
    super(name, bean);
  }

  /**
   * The constructor.
   *
   * @param name - see {@link #getName()}.
   * @param bean - see {@link #getBean()}.
   * @param validator - see {@link #validate()}.
   */
  public DurationInSecondsProperty(String name, Bean bean, AbstractValidator<? super Number> validator) {
    super(name, bean, validator);
  }

  /**
   * @return the {@link #getValue() value} as {@link Duration}.
   */
  public Duration getValueAsDuration() {

    Number value = getValue();
    if (value == null) {
      return null;
    }
    return Duration.ofSeconds(value.longValue());
  }

  /**
   * @param duration the new {@link #getValue() value} as {@link Duration}.
   */
  public void setValueAsDuration(Duration duration) {

    if (duration == null) {
      setValue(null);
    } else {
      setValue(Long.valueOf(duration.get(ChronoUnit.SECONDS)));
    }
  }

}
