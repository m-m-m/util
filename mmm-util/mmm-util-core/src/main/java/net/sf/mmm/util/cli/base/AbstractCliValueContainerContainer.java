/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.cli.base;

import net.sf.mmm.util.cli.api.CliContainerStyle;
import net.sf.mmm.util.cli.api.CliOptionDuplicateException;
import net.sf.mmm.util.cli.api.CliStyle;
import net.sf.mmm.util.cli.api.CliStyleHandling;
import net.sf.mmm.util.nls.api.IllegalCaseException;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorOneArg;
import net.sf.mmm.util.reflect.api.GenericType;

import org.slf4j.Logger;

/**
 * This is the abstract base class for a {@link CliValueContainer} that holds a {@link CliContainerStyle
 * container-value}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public abstract class AbstractCliValueContainerContainer extends AbstractCliValueContainer {

  /** @see #setValueInternal(String, char, GenericType) */
  private boolean valueAlreadySet;

  /**
   * The constructor.
   * 
   * @param parameterContainer is the {@link #getParameterContainer() parameter-container}.
   * @param cliState is the {@link #getCliState() state}.
   * @param dependencies are the {@link #getDependencies() dependencies}.
   * @param logger is the {@link #getLogger() logger}.
   */
  public AbstractCliValueContainerContainer(CliParameterContainer parameterContainer, CliState cliState,
      CliParserDependencies dependencies, Logger logger) {

    super(parameterContainer, cliState, dependencies, logger);
    this.valueAlreadySet = false;
  }

  /**
   * {@inheritDoc}
   */
  public void setValue(String argument) {

    PojoPropertyAccessorOneArg setter = getParameterContainer().getSetter();
    GenericType<?> propertyType = setter.getPropertyType();
    CliStyle cliStyle = getCliState().getCliStyle();
    CliContainerStyle style = getParameterContainer().getContainerStyle(cliStyle);
    switch (style) {
      case MULTIPLE_OCCURRENCE:
        setValueEntry(argument, propertyType);
        break;
      case COMMA_SEPARATED:
        setValueInternal(argument, ',', propertyType);
        break;
      default :
        throw new IllegalCaseException(CliContainerStyle.class, style);
    }
  }

  /**
   * This method parses container value as from a single argument and sets it as new object.
   * 
   * @param argument is the argument representing the container as string.
   * @param separator is the character used as separator for the container values.
   * @param propertyType is the {@link GenericType} of the property.
   * 
   */
  protected void setValueInternal(String argument, char separator, GenericType<?> propertyType) {

    if (this.valueAlreadySet) {
      CliOptionDuplicateException exception = new CliOptionDuplicateException(getParameterContainer().getName());
      CliStyleHandling.EXCEPTION.handle(getLogger(), exception);
    }
    // TODO: separator currently ignored!
    Object value = getDependencies().getConverter().convertValue(argument, getParameterContainer(),
        propertyType.getAssignmentClass(), propertyType);
    setValueInternal(value);
    this.valueAlreadySet = true;
  }

  /**
   * This method sets the container value as new object.
   * 
   * @param containerValue is the container value to set.
   */
  protected abstract void setValueInternal(Object containerValue);

  /**
   * This method is like {@link #setValue(String)} but for a single entry.
   * 
   * @see CliContainerStyle#MULTIPLE_OCCURRENCE
   * 
   * @param entry is a single collection-entry given as string.
   * @param propertyType is the {@link GenericType} of the container.
   */
  protected abstract void setValueEntry(String entry, GenericType<?> propertyType);

  /**
   * {@inheritDoc}
   */
  @Override
  public final boolean isArrayMapOrCollection() {

    return true;
  }

}
