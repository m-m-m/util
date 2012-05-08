/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.value.impl;

import java.util.Map;

import javax.inject.Named;
import javax.inject.Singleton;

import net.sf.mmm.util.collection.api.CollectionFactoryManager;
import net.sf.mmm.util.nls.api.DuplicateObjectException;
import net.sf.mmm.util.nls.api.NlsParseException;
import net.sf.mmm.util.reflect.api.GenericType;
import net.sf.mmm.util.value.api.ComposedValueConverter;

/**
 * This is an implementation of the {@link net.sf.mmm.util.value.api.ValueConverter} interface that converts
 * an {@link Object} to a {@link Map}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
@Singleton
@Named
@SuppressWarnings("rawtypes")
public class ValueConverterToMap extends AbstractValueConverterToContainer<Map> {

  /**
   * The constructor.
   */
  public ValueConverterToMap() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  public Class<Map> getTargetType() {

    return Map.class;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void convertContainerEntry(Object element, int index, Map container, Object valueSource,
      GenericType<? extends Map> targetType, Object originalValue) {

    ComposedValueConverter parentConverter = getComposedValueConverter();
    Object key;
    Object value;
    if (element instanceof CharSequence) {
      String entry = element.toString();
      int splitIndex = entry.indexOf('=');
      if (splitIndex < 0) {
        throw new NlsParseException(entry, "key=value", "MapEntry");
      }
      // key
      String keyString = entry.substring(0, splitIndex);
      GenericType<?> keyType = targetType.getKeyType();
      key = parentConverter.convert(keyString, valueSource, keyType);
      // value
      String valueString = entry.substring(splitIndex + 1);
      GenericType<?> valueType = targetType.getComponentType();
      value = parentConverter.convert(valueString, valueSource, valueType);
    } else if (element instanceof Map.Entry) {
      Map.Entry entry = (Map.Entry) element;
      key = entry.getKey();
      value = entry.getValue();
    } else {
      Exception cause = new NlsParseException(element, Map.Entry.class, valueSource);
      throw new NlsParseException(cause, originalValue, targetType, valueSource);
    }
    Object old = container.put(key, value);
    if (old != null) {
      throw new DuplicateObjectException(value, key);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected <T extends Map> T createContainer(GenericType<T> targetType, int length) {

    CollectionFactoryManager collectionFactoryManager = getCollectionReflectionUtil().getCollectionFactoryManager();
    Class<T> mapType = targetType.getRetrievalClass();
    return (T) collectionFactoryManager.getMapFactory(mapType).create(length);
  }

}
