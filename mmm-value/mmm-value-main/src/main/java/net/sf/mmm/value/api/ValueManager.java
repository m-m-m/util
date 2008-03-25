/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.value.api;

import java.lang.reflect.Type;

import org.w3c.dom.Element;

import net.sf.mmm.util.value.api.ValueParseException;
import net.sf.mmm.util.xml.stream.XmlSerializer;

/**
 * This interface is used for generic creation and management of value objects.
 * <br>
 * Each managed value must bring an implementation of this interface that has to
 * be registered via the configuration of
 * {@link net.sf.mmm.value.api.ValueService}. A legal implementation of this
 * interface must have a non-arg constructor. <br>
 * Anyways it is legal to bypass this manager if you know the value
 * implementation (this is not a classic factory design pattern). <br>
 * 
 * 
 * @see net.sf.mmm.value.api.ValueService
 * 
 * @param <V> is the templated type of the managed value type. See also
 *        {@link #getValueClass()}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface ValueManager<V> extends XmlSerializer<V> {

  /** the tag name used to represent a value as XML */
  String XML_TAG_VALUE = "value";

  /**
   * the attribute name used to represent the {@link #getName() name} of the
   * value in XML
   */
  String XML_ATR_VALUE_NAME = "type";

  /**
   * The value of the attribute {@link #XML_ATR_VALUE_NAME} for the
   * <code>null</code> value.
   */
  String NULL_VALUE_NAME = "null";

  /**
   * the {@link #toString(Object) string representation} of <code>null</code>
   * value.
   */
  String NULL_STRING = "null";

  /**
   * This method gets the logical name of the managed value type.<br>
   * To avoid using implementation class-paths as reference for a logical value
   * type (e.g. in the content-model) the value type can be represented and
   * identified by this name. Typically this will be the
   * {@link Class#getSimpleName() simple-name} of the
   * {@link #getValueClass() value-class}.
   * 
   * @see ValueService#getManager(String)
   * 
   * @return the name.
   */
  String getName();

  /**
   * This method gets the class reflecting the type of the managed value.<br>
   * If the implementing class can NOT be determined or is NOT unique for some
   * reason the interface of the type should be used. A typical example for that
   * case is the {@link Element "XML DOM element"}.
   * 
   * @see #getValueType()
   * 
   * @return the value class.
   */
  Class<? extends V> getValueClass();

  /**
   * This method gets the type of the managed value. Typically this should be
   * the {@link #getValueClass() value-class}.
   * 
   * @return the value type.
   */
  Type getValueType();

  /**
   * This method creates an instance of the managed value encoded as string. It
   * is the inverse operation of the {@link #toString(Object)} method.
   * 
   * @param valueAsString is the string representation of the value in the
   *        format as produced by the <code>toString()</code> method. The
   *        given string representation must represent a value of the type
   *        managed by this value manager.
   * @return the created value.
   * @throws ValueParseException if the string representation is invalid for
   *         this value (e.g. "foo" is invalid for an integer value while a
   *         string value will accept any string and never throw this
   *         exception).
   */
  V fromString(String valueAsString) throws ValueParseException;

  /**
   * This method creates a string representation of the given value. The result
   * has to be understood by the {@link #fromString(String)} method.
   * 
   * @param value is the value to get a string. It may be <code>null</code>.
   * @return the string representation of the given value.
   */
  String toString(V value);

  /**
   * This method checks if the given values <code>value1</code> and
   * <code>value2</code> or {@link Object#equals(Object) equal} to each other.
   * This method accepts <code>null</code> values. If both values are NOT
   * <code>null</code>, it returns
   * <code>value1.{@link Object#equals(Object) equals}(value2)</code> by
   * default. In specific situations the semantic of equals can be changed by
   * the implementation of this method.
   * 
   * @param value1 is the first value to compare. May be <code>null</code>.
   * @param value2 is the second value to compare. May be <code>null</code>.
   * @return <code>true</code> if both values are <code>null</code>, or
   *         both are NOT <code>null</code> and are equal to each other.
   */
  boolean isEqual(V value1, V value2);

}
