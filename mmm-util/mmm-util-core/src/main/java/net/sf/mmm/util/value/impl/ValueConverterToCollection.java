/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.value.impl;

import java.util.Collection;

import javax.inject.Named;
import javax.inject.Singleton;

import net.sf.mmm.util.collection.api.CollectionFactoryManager;
import net.sf.mmm.util.nls.api.NlsParseException;
import net.sf.mmm.util.reflect.api.GenericType;
import net.sf.mmm.util.value.api.ComposedValueConverter;

/**
 * This is an implementation of the {@link net.sf.mmm.util.value.api.ValueConverter} interface that converts
 * an {@link Object} to a {@link Collection}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.1
 */
@Singleton
@Named
@SuppressWarnings("rawtypes")
public class ValueConverterToCollection extends AbstractValueConverterToContainer<Collection> {

  /**
   * The constructor.
   */
  public ValueConverterToCollection() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  public Class<Collection> getTargetType() {

    return Collection.class;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void convertContainerEntry(Object element, int index, Collection container, Object valueSource,
      GenericType<? extends Collection> targetType, Object value) {

    ComposedValueConverter parentConverter = getComposedValueConverter();
    GenericType<?> componentType = targetType.getComponentType();
    Object resultElement = parentConverter.convert(element, valueSource, componentType);
    if ((resultElement == null) && (element != null)) {
      Exception cause = new NlsParseException(element, componentType, valueSource);
      throw new NlsParseException(cause, value, targetType, valueSource);
    }
    assert (index == container.size());
    container.add(resultElement);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected <T extends Collection> T createContainer(GenericType<T> targetType, int length) {

    CollectionFactoryManager collectionFactoryManager = getCollectionReflectionUtil().getCollectionFactoryManager();
    Class<T> collectionType = targetType.getRetrievalClass();
    return (T) collectionFactoryManager.getCollectionFactory(collectionType).create(length);
  }

}
