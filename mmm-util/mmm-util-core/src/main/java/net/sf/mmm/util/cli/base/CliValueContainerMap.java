/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.cli.base;

import java.util.Map;

import net.sf.mmm.util.cli.api.CliConstraintCollection;
import net.sf.mmm.util.cli.api.CliConstraintInvalidException;
import net.sf.mmm.util.cli.api.CliStyle;
import net.sf.mmm.util.cli.api.CliStyleHandling;
import net.sf.mmm.util.nls.api.DuplicateObjectException;
import net.sf.mmm.util.nls.api.NlsParseException;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorOneArg;
import net.sf.mmm.util.reflect.api.GenericType;
import net.sf.mmm.util.scanner.base.CharSequenceScanner;
import net.sf.mmm.util.value.api.GenericValueConverter;
import net.sf.mmm.util.value.api.ValueOutOfRangeException;

import org.slf4j.Logger;

/**
 * This is an implementation of {@link CliValueContainer} for a {@link Map}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public class CliValueContainerMap extends AbstractCliValueContainer {

  /** @see #getValue() */
  private final Map<Object, Object> map;

  /**
   * The constructor.
   * 
   * @param parameterContainer is the {@link #getParameterContainer()
   *        parameter-container}.
   * @param cliState is the {@link #getCliState() state}.
   * @param configuraiton is the {@link #getConfiguration() configuration}.
   * @param logger is the {@link #getLogger() logger}.
   * @param value is the initial, empty {@link Map}-{@link #getValue() value}.
   */
  public CliValueContainerMap(CliParameterContainer parameterContainer, CliState cliState,
      CliParserConfiguration configuraiton, Logger logger, Map<Object, Object> value) {

    super(parameterContainer, cliState, configuraiton, logger);
    assert (value != null);
    this.map = value;
  }

  /**
   * {@inheritDoc}
   */
  public Object getValue() {

    return this.map;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void validate() throws CliConstraintInvalidException {

    super.validate();
    CliParameterContainer parameterContainer = getParameterContainer();
    CliConstraintCollection constraint = parameterContainer
        .getConstraint(CliConstraintCollection.class);
    if (constraint != null) {
      ValueOutOfRangeException.checkRange(Integer.valueOf(this.map.size()),
          Integer.valueOf(constraint.min()), Integer.valueOf(constraint.max()), parameterContainer);
    }
  }

  /**
   * This method is like {@link #setValue(String)} but for a single entry.
   * 
   * @param entry is a single map-entry in the form "key=value".
   * @param propertyType is the {@link GenericType} of the {@link Map}.
   */
  protected void setValueEntry(String entry, GenericType<?> propertyType) {

    int splitIndex = entry.indexOf('=');
    if (splitIndex < 0) {
      throw new NlsParseException(entry, "key=value", "MapEntry");
    }
    // key
    String keyString = entry.substring(0, splitIndex);
    GenericType<?> keyType = propertyType.getKeyType();
    GenericValueConverter<Object> converter = getConfiguration().getConverter();
    Object key = converter.convertValue(keyString, getParameterContainer(),
        keyType.getAssignmentClass(), keyType);
    // value
    String valueString = entry.substring(splitIndex + 1);
    GenericType<?> valueType = propertyType.getComponentType();
    Object value = converter.convertValue(valueString, getParameterContainer(),
        valueType.getAssignmentClass(), valueType);
    Object old = this.map.put(key, value);
    if (old != null) {
      CliStyleHandling handling = getCliState().getCliStyle().valueDuplicateMapKey();
      if (handling != CliStyleHandling.OK) {
        DuplicateObjectException exception = new DuplicateObjectException(valueString, keyString);
        handling.handle(getLogger(), exception);
      }
    }
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
