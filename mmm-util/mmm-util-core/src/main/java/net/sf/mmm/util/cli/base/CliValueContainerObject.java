/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.cli.base;

import java.io.File;

import net.sf.mmm.util.cli.api.CliConstraintFile;
import net.sf.mmm.util.cli.api.CliConstraintInvalidException;
import net.sf.mmm.util.cli.api.CliOptionDuplicateException;
import net.sf.mmm.util.cli.api.CliStyleHandling;
import net.sf.mmm.util.file.api.FileAlreadyExistsException;
import net.sf.mmm.util.file.api.FileNotExistsException;
import net.sf.mmm.util.file.api.FileType;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorOneArg;

import org.slf4j.Logger;

/**
 * This is an implementation of {@link CliValueContainer} for simple objects (no
 * arrays, collections, maps).
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public class CliValueContainerObject extends AbstractCliValueContainer {

  /** @see #getValue() */
  private Object value;

  /**
   * The constructor.
   * 
   * @param parameterContainer is the {@link #getParameterContainer()
   *        parameter-container}.
   * @param cliState is the {@link #getCliState() state}.
   * @param configuration is the {@link #getConfiguration() configuration}.
   * @param logger is the {@link #getLogger() logger}.
   */
  public CliValueContainerObject(CliParameterContainer parameterContainer, CliState cliState,
      CliParserConfiguration configuration, Logger logger) {

    super(parameterContainer, cliState, configuration, logger);
    this.value = null;
  }

  /**
   * @return the value
   */
  public Object getValue() {

    return this.value;
  }

  /**
   * {@inheritDoc}
   */
  public void setValue(String argument) {

    CliParameterContainer parameterContainer = getParameterContainer();
    PojoPropertyAccessorOneArg setter = parameterContainer.getSetter();
    Object newValue = getConfiguration().getConverter().convertValue(argument, parameterContainer,
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
        CliOptionDuplicateException exception = new CliOptionDuplicateException(
            parameterContainer.getName());
        handling.handle(getLogger(), exception);
      }
    }
    this.value = newValue;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void validate() throws CliConstraintInvalidException {

    CliParameterContainer parameterContainer = getParameterContainer();
    if (File.class.isAssignableFrom(parameterContainer.getSetter().getPropertyClass())) {
      CliConstraintFile constraint = parameterContainer.getConstraint(CliConstraintFile.class);
      if (constraint != null) {
        File file = (File) this.value;
        FileType fileType = FileType.getType(file);
        if (fileType != null) {
          if (!fileType.equals(constraint.type())) {
            // throw new WrongFileTypeException();
          }
        }
        if (!constraint.ignoreExists()) {
          if (constraint.exists()) {
            // if (!file.exists()) {
            if (fileType == null) {
              throw new FileNotExistsException(file);
            }
          } else {
            // if (file.exists()) {
            if (fileType != null) {
              throw new FileAlreadyExistsException(file);
            }
          }
        }
      }
    }
    super.validate();
  }
}
