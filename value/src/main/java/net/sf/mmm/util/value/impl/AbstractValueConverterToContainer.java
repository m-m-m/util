/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.value.impl;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import net.sf.mmm.util.collection.api.CollectionFactoryManager;
import net.sf.mmm.util.collection.impl.CollectionFactoryManagerImpl;
import net.sf.mmm.util.reflect.api.GenericType;
import net.sf.mmm.util.value.base.AbstractRecursiveValueConverter;

/**
 * This is the abstract base-implementation of a {@link net.sf.mmm.util.value.api.ValueConverter} that
 * converts an {@link Object} to a container type. A container type is an array, {@link java.util.Collection}
 * or {@link java.util.Map}. It supports objects given as {@link CharSequence} (e.g. {@link String}),
 * {@link Collection}, or array. If a value is given as {@link CharSequence} it will be parsed as
 * comma-separated values. An individual value can be escaped by enclosing it with "<{[" and "]}>" so it can
 * itself contain the separator character. <br>
 * Here are some examples:<br>
 * <table border="1">
 * <tr>
 * <th>value</th>
 * <th>{@link #getTargetType() target-type}</th>
 * <th>result</th>
 * </tr>
 * <tr>
 * <td>"123"</td>
 * <td>List&lt;Integer&gt;</td>
 * <td>{123}</td>
 * </tr>
 * <tr>
 * <td>"1, 2,3"</td>
 * <td>int[]</td>
 * <td>{1,2,3}</td>
 * </tr>
 * <tr>
 * <td>"a, <{[b,c,d]}>,e"</td>
 * <td>List&lt;List&lt;Character&gt;&gt;</td>
 * <td>{{'a'},{'b','c','d'},{'e'}}</td>
 * </tr>
 * <tr>
 * <td>"42=true,84=false"</td>
 * <td>Map&lt;Integer, Boolean&gt;</td>
 * <td>{42->true, 84->false}</td>
 * </tr>
 * </table>
 *
 * @param <CONTAINER> is the generic type of the container.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
@Singleton
@Named
@SuppressWarnings({ "rawtypes" })
public abstract class AbstractValueConverterToContainer<CONTAINER> extends AbstractRecursiveValueConverter<Object, CONTAINER> {

  /** The character used to separate the element of the collection. */
  protected static final char ELEMENT_SEPARATOR = ',';

  /**
   * The prefix used to escape an element (that may contain {@link #ELEMENT_SEPARATOR}).
   */
  protected static final String ELEMENT_ESCAPE_START = "<{[";

  /**
   * The suffix used to escape an element (that may contain {@link #ELEMENT_SEPARATOR}).
   */
  protected static final String ELEMENT_ESCAPE_END = "]}>";

  private CollectionFactoryManager collectionFactoryManager;

  /**
   * The constructor.
   */
  public AbstractValueConverterToContainer() {

    super();
  }

  /**
   * @return the instance of {@link CollectionFactoryManager} to use.
   */
  protected CollectionFactoryManager getCollectionFactoryManager() {

    return this.collectionFactoryManager;
  }

  /**
   * @param collectionFactoryManager is the {@link CollectionFactoryManager} to set
   */
  @Inject
  public void setCollectionFactoryManager(CollectionFactoryManager collectionFactoryManager) {

    getInitializationState().requireNotInitilized();
    this.collectionFactoryManager = collectionFactoryManager;
  }

  @Override
  protected void doInitialize() {

    super.doInitialize();
    if (this.collectionFactoryManager == null) {
      this.collectionFactoryManager = CollectionFactoryManagerImpl.getInstance();
    }
  }

  @Override
  public Class<Object> getSourceType() {

    return Object.class;
  }

  @Override
  public <T extends CONTAINER> T convert(Object value, Object valueSource, GenericType<T> targetType) {

    if (value == null) {
      return null;
    }
    T result = null;
    Class<?> valueClass = value.getClass();
    if (valueClass.isArray()) {
      result = convertFromArray(value, valueSource, targetType);
    } else if (value instanceof CharSequence) {
      result = convertFromString(value.toString(), valueSource, targetType);
    } else if (value instanceof Collection) {
      result = convertFromCollection((Collection) value, valueSource, targetType);
    }
    return result;
  }

  /**
   * This method performs the {@link #convert(Object, Object, GenericType) conversion} for {@link Collection}
   * values.
   *
   * @param <T> is the generic type of {@code targetType}.
   * @param collectionValue is the {@link Collection} value to convert.
   * @param valueSource describes the source of the value or {@code null} if NOT available.
   * @param targetType is the {@link #getTargetType() target-type} to convert to.
   * @return the converted container.
   */
  protected <T extends CONTAINER> T convertFromCollection(Collection collectionValue, Object valueSource, GenericType<T> targetType) {

    int size = collectionValue.size();
    T container = createContainer(targetType, size);
    int i = 0;
    for (Object element : collectionValue) {
      convertContainerEntry(element, i, container, valueSource, targetType, collectionValue);
      i++;
    }
    return container;
  }

  /**
   * This method performs the {@link #convert(Object, Object, GenericType) conversion} for {@link String}
   * values.
   *
   * @param <T> is the generic type of {@code targetType}.
   * @param stringValue is the {@link String} value to convert.
   * @param valueSource describes the source of the value or {@code null} if NOT available.
   * @param targetType is the {@link #getTargetType() target-type} to convert to.
   * @return the converted container.
   */
  protected <T extends CONTAINER> T convertFromString(String stringValue, Object valueSource, GenericType<T> targetType) {

    List<String> stringList = new ArrayList<>();
    int start = 0;
    int length = stringValue.length();
    while (start < length) {
      int end;
      int offset = 1;
      if (stringValue.startsWith(ELEMENT_ESCAPE_START, start)) {
        int newStart = start + ELEMENT_ESCAPE_START.length();
        end = stringValue.indexOf(ELEMENT_ESCAPE_END, newStart);
        if (end > 0) {
          start = newStart;
          offset = ELEMENT_ESCAPE_END.length();
        } else {
          // actually a syntax error, lets be tolerant and ignore it...
          end = stringValue.indexOf(ELEMENT_SEPARATOR, start);
        }
      } else {
        end = stringValue.indexOf(ELEMENT_SEPARATOR, start);
      }
      if (end < 0) {
        end = length;
      }
      String element = stringValue.substring(start, end).trim();
      stringList.add(element);
      if (offset > 1) {
        end = stringValue.indexOf(ELEMENT_SEPARATOR, end + offset);
        if (end < 0) {
          end = length;
        }
      }
      start = end + 1;
    }
    int size = stringList.size();
    T container = createContainer(targetType, size);
    for (int i = 0; i < size; i++) {
      convertContainerEntry(stringList.get(i), i, container, valueSource, targetType, stringValue);
    }
    return container;
  }

  /**
   * This method performs the {@link #convert(Object, Object, GenericType) conversion} for array values.
   *
   * @param <T> is the generic type of {@code targetType}.
   * @param arrayValue is the array value to convert.
   * @param valueSource describes the source of the value or {@code null} if NOT available.
   * @param targetType is the {@link #getTargetType() target-type} to convert to.
   * @return the converted container.
   */
  protected <T extends CONTAINER> T convertFromArray(Object arrayValue, Object valueSource, GenericType<T> targetType) {

    int len = Array.getLength(arrayValue);
    T container = createContainer(targetType, len);
    for (int i = 0; i < len; i++) {
      Object element = Array.get(arrayValue, i);
      convertContainerEntry(element, i, container, valueSource, targetType, arrayValue);
    }
    return container;
  }

  /**
   * This method converts a single entry of a container.
   *
   * @param element is the single entry (element) of the container to convert.
   * @param index is the index of the given {@code element} in the order of occurrence.
   * @param container is the current container where to add the given {@code element} as entry.
   * @param valueSource describes the source of the value or {@code null} if NOT available.
   * @param targetType is the {@link #getTargetType() target-type} to convert to.
   * @param value is the original value to convert.
   */
  protected abstract void convertContainerEntry(Object element, int index, CONTAINER container, Object valueSource, GenericType<? extends CONTAINER> targetType,
      Object value);

  // {
  // ComposedValueConverter parentConverter = getComposedValueConverter();
  // Object resultElement = parentConverter.convert(element, valueSource,
  // targetType.getComponentType());
  // if ((resultElement == null) && (element != null)) {
  // Exception cause = new NlsParseException(element, componentType,
  // valueSource);
  // throw new NlsParseException(cause, value, targetType, valueSource);
  // }
  // result.add(resultElement);
  // }

  /**
   * This method creates the according container for the given {@code containerType}.
   *
   * @param <T> is the generic type of {@code targetType}.
   * @param targetType is the {@link GenericType} of the container.
   * @param length is the length (or capacity) of the container to create.
   * @return the container instance.
   */
  protected abstract <T extends CONTAINER> T createContainer(GenericType<T> targetType, int length);

}
