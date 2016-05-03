/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.cli.base;

import java.util.Collection;

import net.sf.mmm.util.reflect.api.GenericType;

import org.slf4j.Logger;

/**
 * This is an implementation of {@link CliValueContainer} for a {@link Collection}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class CliValueContainerCollection extends AbstractCliValueContainerContainer {

  /** @see #getValue() */
  private Collection<Object> collection;

  /**
   * The constructor.
   *
   * @param parameterContainer is the {@link #getParameterContainer() parameter-container}.
   * @param cliState is the {@link #getCliState() state}.
   * @param dependencies are the {@link #getDependencies() dependencies}.
   * @param logger is the {@link #getLogger() logger}.
   * @param value is the initial, empty {@link Collection}-{@link #getValue() value}.
   */
  public CliValueContainerCollection(CliParameterContainer parameterContainer, CliState cliState,
      CliParserDependencies dependencies, Logger logger, Collection<Object> value) {

    super(parameterContainer, cliState, dependencies, logger);
    assert (value != null);
    this.collection = value;
  }

  @Override
  public Object getValue() {

    return this.collection;
  }

  /**
   * @return the collection
   */
  public Collection<Object> getCollection() {

    return this.collection;
  }

  @SuppressWarnings("unchecked")
  @Override
  protected void setValueInternal(Object containerValue) {

    this.collection = (Collection<Object>) containerValue;
  }

  @Override
  protected void setValueEntry(String entry, GenericType<?> propertyType) {

    GenericType<?> valueType = propertyType.getComponentType();
    Object value = getDependencies().getConverter().convertValue(entry, getParameterContainer(),
        valueType.getAssignmentClass(), valueType);
    this.collection.add(value);
  }
}
