/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.cli.base;

import java.util.Map;

import net.sf.mmm.util.cli.api.CliStyle;
import net.sf.mmm.util.cli.api.CliStyleHandling;
import net.sf.mmm.util.nls.api.DuplicateObjectException;
import net.sf.mmm.util.nls.api.NlsParseException;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorOneArg;
import net.sf.mmm.util.reflect.api.GenericType;
import net.sf.mmm.util.scanner.base.CharSequenceScanner;
import net.sf.mmm.util.value.api.GenericValueConverter;

import org.slf4j.Logger;

/**
 * This is an implementation of {@link CliValueContainer} for a {@link Map}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public class CliValueContainerMap implements CliValueContainer {

  /** @see #getValue() */
  private final Map<Object, Object> map;

  /**
   * The constructor.
   * 
   * @param value is the initial, empty {@link Map}-{@link #getValue() value}.
   */
  public CliValueContainerMap(Map<Object, Object> value) {

    super();
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
   * This method is like
   * {@link #setValue(String, CliParameterContainer, CliStyle, CliParserConfiguration, Logger)}
   * but for a single entry.
   * 
   * @param entry is a single map-entry in the form "key=value".
   * @param parameterContainer is the {@link CliParameterContainer}.
   * @param cliStyle is the {@link CliState}.
   * @param configuration is the {@link CliParserConfiguration}.
   * @param logger is the {@link Logger} used to log {@link CliStyle} anomalies.
   * @param propertyType is the {@link GenericType} of the {@link Map}.
   */
  protected void setValueEntry(String entry, CliParameterContainer parameterContainer,
      CliStyle cliStyle, CliParserConfiguration configuration, Logger logger,
      GenericType<?> propertyType) {

    int splitIndex = entry.indexOf('=');
    if (splitIndex < 0) {
      throw new NlsParseException(entry, "key=value", "MapEntry");
    }
    // key
    String keyString = entry.substring(0, splitIndex);
    GenericType<?> keyType = propertyType.getKeyType();
    GenericValueConverter<Object> converter = configuration.getConverter();
    Object key = converter.convertValue(keyString, parameterContainer,
        keyType.getAssignmentClass(), keyType);
    // value
    String valueString = entry.substring(splitIndex + 1);
    GenericType<?> valueType = propertyType.getComponentType();
    Object value = converter.convertValue(valueString, parameterContainer, valueType
        .getAssignmentClass(), valueType);
    Object old = this.map.put(key, value);
    if (old != null) {
      CliStyleHandling handling = cliStyle.valueDuplicateMapKey();
      if (handling != CliStyleHandling.OK) {
        DuplicateObjectException exception = new DuplicateObjectException(valueString, keyString);
        handling.handle(logger, exception);
      }
    }
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
      setValueEntry(argument, parameterContainer, cliStyle, configuration, logger, setter
          .getPropertyType());
    } else {
      CharSequenceScanner scanner = new CharSequenceScanner(argument);
      while (scanner.hasNext()) {
        String entry = scanner.readUntil(collectionValueSeparator, true);
        setValueEntry(entry, parameterContainer, cliStyle, configuration, logger, setter
            .getPropertyType());
      }
    }
  }
}
