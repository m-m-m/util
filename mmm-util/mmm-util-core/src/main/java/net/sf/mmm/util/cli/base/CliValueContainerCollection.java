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
public class CliValueContainerCollection implements CliValueContainer {

  /** @see #getValue() */
  private final Collection<Object> collection;

  /**
   * The constructor.
   * 
   * @param value is the initial, empty {@link Collection}-{@link #getValue()
   *        value}.
   */
  public CliValueContainerCollection(Collection<Object> value) {

    super();
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
   * This method is like
   * {@link #setValue(String, CliParameterContainer, CliStyle, CliParserConfiguration, Logger)}
   * but for a single entry.
   * 
   * @param entry is a single collection-entry given as string.
   * @param parameterContainer is the {@link CliParameterContainer}.
   * @param cliStyle is the {@link CliState}.
   * @param configuraiton is the {@link CliParserConfiguration}.
   * @param logger is the {@link Logger} used to log {@link CliStyle} anomalies.
   * @param propertyType is the {@link GenericType} of the {@link Collection}.
   */
  protected void setValueEntry(String entry, CliParameterContainer parameterContainer,
      CliStyle cliStyle, CliParserConfiguration configuraiton, Logger logger,
      GenericType<?> propertyType) {

    // value
    GenericType<?> valueType = propertyType.getComponentType();
    Object value = configuraiton.getConverter().convertValue(entry, parameterContainer,
        valueType.getAssignmentClass(), valueType);
    this.collection.add(value);
  }

  /**
   * {@inheritDoc}
   */
  public void setValue(String argument, CliParameterContainer parameterContainer,
      CliStyle cliStyle, CliParserConfiguration configuration, Logger logger) {

    PojoPropertyAccessorOneArg setter = parameterContainer.getSetter();
    char collectionValueSeparator = cliStyle.collectionValueSeparator();
    if (collectionValueSeparator == CliStyle.COLLECTION_VALUE_SEPARATOR_NONE) {
      // multi-value style
      setValueEntry(argument, parameterContainer, cliStyle, configuration, logger,
          setter.getPropertyType());
    } else {
      CharSequenceScanner scanner = new CharSequenceScanner(argument);
      while (scanner.hasNext()) {
        String entry = scanner.readUntil(collectionValueSeparator, true);
        setValueEntry(entry, parameterContainer, cliStyle, configuration, logger,
            setter.getPropertyType());
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  public boolean isArrayMapOrCollection() {

    return true;
  }
}
