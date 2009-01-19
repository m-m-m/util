/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.value.impl;

import java.lang.reflect.Array;
import java.util.Collection;

import javax.annotation.Resource;

import net.sf.mmm.util.collection.api.CollectionFactoryManager;
import net.sf.mmm.util.lang.api.StringTokenizer;
import net.sf.mmm.util.reflect.api.CollectionReflectionUtil;
import net.sf.mmm.util.reflect.api.GenericType;
import net.sf.mmm.util.reflect.base.CollectionReflectionUtilImpl;
import net.sf.mmm.util.value.api.ComposedValueConverter;
import net.sf.mmm.util.value.api.ValueParseGenericException;
import net.sf.mmm.util.value.base.AbstractRecursiveValueConverter;

/**
 * This is an implementation of the
 * {@link net.sf.mmm.util.value.api.ValueConverter} interface that converts an
 * {@link Object} to a {@link Number}. It supports objects given as
 * {@link CharSequence} (e.g. {@link String}) or {@link Number}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("unchecked")
public class ValueConverterToCollection extends AbstractRecursiveValueConverter<Object, Collection> {

  /** @see #getCollectionReflectionUtil() */
  private CollectionReflectionUtil collectionReflectionUtil;

  /**
   * The constructor.
   */
  public ValueConverterToCollection() {

    super();
  }

  /**
   * This method gets the {@link CollectionReflectionUtilImpl} instance to use.
   * 
   * @return the {@link CollectionReflectionUtilImpl} to use.
   */
  protected CollectionReflectionUtil getCollectionReflectionUtil() {

    return this.collectionReflectionUtil;
  }

  /**
   * @param collectionReflectionUtil is the collectionReflectionUtil to set
   */
  @Resource
  public void setCollectionReflectionUtil(CollectionReflectionUtil collectionReflectionUtil) {

    getInitializationState().requireNotInitilized();
    this.collectionReflectionUtil = collectionReflectionUtil;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doInitialize() {

    super.doInitialize();
    if (this.collectionReflectionUtil == null) {
      this.collectionReflectionUtil = CollectionReflectionUtilImpl.getInstance();
    }
  }

  /**
   * {@inheritDoc}
   */
  public Class<Object> getSourceType() {

    return Object.class;
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
  public Collection convert(Object value, Object valueSource,
      GenericType<? extends Collection> targetType) {

    if (value == null) {
      return null;
    }
    Collection result = null;
    GenericType<?> componentType = targetType.getComponentType();
    CollectionFactoryManager collectionFactoryManager = getCollectionReflectionUtil()
        .getCollectionFactoryManager();
    ComposedValueConverter parentConverter = getComposedValueConverter();
    Class<?> sourceClass = value.getClass();
    if (sourceClass.isArray()) {
      result = collectionFactoryManager.getCollectionFactory(targetType.getRetrievalClass()).create();
      int len = Array.getLength(value);
      for (int i = 0; i < len; i++) {
        Object element = Array.get(value, i);
        Object resultElement = parentConverter.convert(element, valueSource, componentType);
        if ((resultElement == null) && (element != null)) {
          Exception cause = new ValueParseGenericException(element, componentType, valueSource);
          throw new ValueParseGenericException(cause, value, targetType, valueSource);
        }
        result.add(resultElement);
      }
    } else if (CharSequence.class.isAssignableFrom(sourceClass)) {
      result = collectionFactoryManager.getCollectionFactory(targetType.getRetrievalClass()).create();
      StringTokenizer tokenizer = new StringTokenizer(value.toString(), ',');
      for (String element : tokenizer) {
        Object resultElement = parentConverter.convert(element, valueSource, componentType);
        if ((resultElement == null) && (element != null)) {
          Exception cause = new ValueParseGenericException(element, componentType, valueSource);
          throw new ValueParseGenericException(cause, value, targetType, valueSource);
        }
        result.add(resultElement);
      }
    } else if (Collection.class.isInstance(value)) {
      result = collectionFactoryManager.getCollectionFactory(targetType.getRetrievalClass()).create();
      Collection collection = (Collection) value;
      for (Object element : collection) {
        Object resultElement = parentConverter.convert(element, valueSource, componentType);
        if ((resultElement == null) && (element != null)) {
          Exception cause = new ValueParseGenericException(element, componentType, valueSource);
          throw new ValueParseGenericException(cause, value, targetType, valueSource);
        }
        result.add(resultElement);
      }
    }
    return result;
  }
}
