/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.cli.base;

import net.sf.mmm.util.cli.api.CliOptionDuplicateException;
import net.sf.mmm.util.cli.api.CliStyleHandling;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorOneArg;

import org.slf4j.Logger;

/**
 * This is an implementation of {@link CliValueContainer} for simple objects (no arrays, collections, maps).
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class CliValueContainerObject extends AbstractCliValueContainer {

  /** @see #getValue() */
  private Object value;

  /**
   * The constructor.
   *
   * @param parameterContainer is the {@link #getParameterContainer() parameter-container}.
   * @param cliState is the {@link #getCliState() state}.
   * @param dependencies are the {@link #getDependencies() dependencies}.
   * @param logger is the {@link #getLogger() logger}.
   */
  public CliValueContainerObject(CliParameterContainer parameterContainer, CliState cliState,
      CliParserDependencies dependencies, Logger logger) {

    super(parameterContainer, cliState, dependencies, logger);
    this.value = null;
  }

  /**
   * @return the value
   */
  public Object getValue() {

    return this.value;
  }

  @Override
  public void setValue(String argument) {

    CliParameterContainer parameterContainer = getParameterContainer();
    PojoPropertyAccessorOneArg setter = parameterContainer.getSetter();
    Object newValue = getDependencies().getConverter().convertValue(argument, parameterContainer,
        setter.getPropertyClass(), setter.getPropertyType());
    if (this.value != null) {
      CliStyleHandling handling;
      if (Boolean.TRUE.equals(newValue) && Boolean.TRUE.equals(this.value)) {
        // trigger might be duplicated according to CLI style
        handling = getCliState().getCliStyle().optionDuplicated();
      } else {
        handling = CliStyleHandling.EXCEPTION;
      }
      if (handling != CliStyleHandling.OK) {
        CliOptionDuplicateException exception = new CliOptionDuplicateException(parameterContainer.getName());
        handling.handle(getLogger(), exception);
      }
    }
    this.value = newValue;
  }

}
