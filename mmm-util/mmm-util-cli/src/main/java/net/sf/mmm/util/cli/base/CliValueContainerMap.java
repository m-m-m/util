/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.cli.base;

import java.util.Map;

import net.sf.mmm.util.cli.api.CliStyleHandling;
import net.sf.mmm.util.exception.api.DuplicateObjectException;
import net.sf.mmm.util.exception.api.NlsParseException;
import net.sf.mmm.util.reflect.api.GenericType;
import net.sf.mmm.util.value.api.GenericValueConverter;

import org.slf4j.Logger;

/**
 * This is an implementation of {@link CliValueContainer} for a {@link Map}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class CliValueContainerMap extends AbstractCliValueContainerContainer {

  /** @see #getValue() */
  private Map<Object, Object> map;

  /**
   * The constructor.
   *
   * @param parameterContainer is the {@link #getParameterContainer() parameter-container}.
   * @param cliState is the {@link #getCliState() state}.
   * @param dependencies are the {@link #getDependencies() dependencies}.
   * @param logger is the {@link #getLogger() logger}.
   * @param value is the initial, empty {@link Map}-{@link #getValue() value}.
   */
  public CliValueContainerMap(CliParameterContainer parameterContainer, CliState cliState,
      CliParserDependencies dependencies, Logger logger, Map<Object, Object> value) {

    super(parameterContainer, cliState, dependencies, logger);
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
  @SuppressWarnings("unchecked")
  @Override
  protected void setValueInternal(Object containerValue) {

    this.map = (Map<Object, Object>) containerValue;
  }

  /**
   * {@inheritDoc}
   *
   * @param entry is a single map-entry in the form "key=value".
   */
  @Override
  protected void setValueEntry(String entry, GenericType<?> propertyType) {

    int splitIndex = entry.indexOf('=');
    if (splitIndex < 0) {
      throw new NlsParseException(entry, "key=value", "MapEntry");
    }
    // key
    String keyString = entry.substring(0, splitIndex);
    GenericType<?> keyType = propertyType.getKeyType();
    GenericValueConverter<Object> converter = getDependencies().getConverter();
    Object key = converter.convertValue(keyString, getParameterContainer(), keyType.getAssignmentClass(), keyType);
    // value
    String valueString = entry.substring(splitIndex + 1);
    GenericType<?> valueType = propertyType.getComponentType();
    Object value = converter.convertValue(valueString, getParameterContainer(), valueType.getAssignmentClass(),
        valueType);
    Object old = this.map.put(key, value);
    if (old != null) {
      CliStyleHandling handling = getCliState().getCliStyle().valueDuplicateMapKey();
      if (handling != CliStyleHandling.OK) {
        DuplicateObjectException exception = new DuplicateObjectException(valueString, keyString);
        handling.handle(getLogger(), exception);
      }
    }
  }

}
