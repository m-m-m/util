/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.cli.base;

import java.util.Collection;

import net.sf.mmm.util.cli.api.CliStyle;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorOneArg;
import net.sf.mmm.util.reflect.api.GenericType;
import net.sf.mmm.util.scanner.base.CharSequenceScanner;

import org.slf4j.Logger;

/**
 * This is an implementation of {@link CliValueContainer} for a
 * {@link Collection}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public class CliValueContainerCollection extends AbstractCliValueContainer {

  /** @see #getValue() */
  private final Collection<Object> collection;

  /**
   * The constructor.
   * 
   * @param parameterContainer is the {@link #getParameterContainer()
   *        parameter-container}.
   * @param cliState is the {@link #getCliState() state}.
   * @param configuraiton is the {@link #getConfiguration() configuration}.
   * @param logger is the {@link #getLogger() logger}.
   * @param value is the initial, empty {@link Collection}-{@link #getValue()
   *        value}.
   */
  public CliValueContainerCollection(CliParameterContainer parameterContainer, CliState cliState,
      CliParserConfiguration configuraiton, Logger logger, Collection<Object> value) {

    super(parameterContainer, cliState, configuraiton, logger);
    assert (value != null);
    this.collection = value;
  }

  /**
   * {@inheritDoc}
   */
  public Object getValue() {

    return this.collection;
  }

  /**
   * @return the collection
   */
  public Collection<Object> getCollection() {

    return this.collection;
  }

  /**
   * This method is like {@link #setValue(String)} but for a single entry.
   * 
   * @param entry is a single collection-entry given as string.
   * @param propertyType is the {@link GenericType} of the {@link Collection}.
   */
  protected void setValueEntry(String entry, GenericType<?> propertyType) {

    // value
    GenericType<?> valueType = propertyType.getComponentType();
    Object value = getConfiguration().getConverter().convertValue(entry, getParameterContainer(),
        valueType.getAssignmentClass(), valueType);
    this.collection.add(value);
  }

  /**
   * {@inheritDoc}
   */
  public void setValue(String argument) {

    PojoPropertyAccessorOneArg setter = getParameterContainer().getSetter();
    char collectionValueSeparator = getCliState().getCliStyle().collectionValueSeparator();
    if (collectionValueSeparator == CliStyle.COLLECTION_VALUE_SEPARATOR_NONE) {
      // multi-value style
      setValueEntry(argument, setter.getPropertyType());
    } else {
      CharSequenceScanner scanner = new CharSequenceScanner(argument);
      while (scanner.hasNext()) {
        String entry = scanner.readUntil(collectionValueSeparator, true);
        setValueEntry(entry, setter.getPropertyType());
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isArrayMapOrCollection() {

    return true;
  }
}
