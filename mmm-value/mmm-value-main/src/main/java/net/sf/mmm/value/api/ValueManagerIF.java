/* $Id$ */
package net.sf.mmm.value.api;

import net.sf.mmm.xml.api.XmlException;
import net.sf.mmm.xml.api.XmlSerializerIF;
import net.sf.mmm.xml.api.XmlWriterIF;

import org.w3c.dom.Element;

/**
 * This interface is used for generic creation and management of value objects.
 * <br>
 * Each managed value must bring an implementation of this interface that has to
 * be registered via the configuration of
 * {@link net.sf.mmm.value.api.ValueServiceIF}. A legal implementation of this
 * interface must have a non-arg constructor. <br>
 * Anyways it is legal to bypass this manager if you know the value
 * implementation (this is not a classic factory design pattern). <br>
 * 
 * 
 * @see net.sf.mmm.value.api.ValueServiceIF
 * 
 * @param <V>
 *        is the templated type of the managed value type. See also
 *        {@link #getValueType()}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface ValueManagerIF<V> extends XmlSerializerIF<V> {

  /** the tag name used to represent a value as XML */
  String XML_TAG_VALUE = "value";

  /**
   * the attribute name used to represent the {@link #getName() name} of the
   * value in XML
   */
  String XML_ATR_VALUE_NAME = "type";

  /**
   * the attribute name used to represent the {@link Object#getClass() class} of
   * the value. ATTENTION: please prefer to use the {@link #XML_ATR_VALUE_NAME}
   * attribute prior to this attribute. Be also aware, that this attribute
   * contains may contain the class of a value implementation, while the
   * {@link #getValueType() "value type"} of the according value may be an
   * interface or abstract-superclass of the value.
   */
  String XML_ATR_VALUE_CLASS = "class";

  /**
   * This method gets the logical name of the managed value type.<br>
   * To avoid using implementation class-pathes as reference for a logical value
   * type (e.g. in the content-modell) the value type can be represented and
   * identified by this name.
   * 
   * @see ValueServiceIF#getManager(String)
   * 
   * @return the name.
   */
  String getName();

  /**
   * This method gets the type of the managed value. Typically this should be
   * the class implementing the value.<br>
   * If the implementing class can NOT be determined or is NOT unique for some
   * reason the interface of the type may be used. A typical example for that
   * case is the {@link Element "XML DOM element"}.
   * 
   * @return the value type.
   */
  Class<V> getValueType();

  /**
   * This method creates an instance of the managed value encoded as string. It
   * is the inverse operation of the values {@link Object#toString()} method.
   * 
   * @param valueAsString
   *        is the string representation of the value in the format as produced
   *        by the <code>toString()</code> method. The given string
   *        representation must represent a value of the type managed by this
   *        value manager.
   * @return the created value.
   * @throws ValueParseException
   *         if the string representation is invalid for this value (e.g. "abc"
   *         is invalid for an integer value while a string value will accept
   *         any string and never throw this exception).
   */
  V parse(String valueAsString) throws ValueParseException;

  /**
   * This method parses a value given as XML element. It is the inverse
   * operation of the {@link #toXml(XmlWriterIF, Object)} method. <br>
   * A more high-level version of this method is
   * {@link ValueServiceIF#xml2value(Element)}.
   * 
   * @param valueAsXml
   *        is the value in its XML representation. This method should only be
   *        called if the XML representation belongs to the value managed by
   *        this implementation. The implementation should only parse the child
   *        nodes of the given element.
   * @return the parsed value.
   * @throws ValueParseException
   *         if the XML representation is illegal for the managed value.
   */
  V parse(Element valueAsXml) throws ValueParseException;

  /**
   * This method creates an XML representation of the given value. <br>
   * E.g. the string "abc" may be represented as
   * <code>&lt;value type="String"&gt;abc&lt;/value&gt;</code>
   * 
   * @see XmlSerializerIF#toXml(XmlWriterIF, Object)
   * 
   * @param xmlWriter
   *        is the writer where to write the XML to.
   * @param value
   *        is the value to serialize. It may be <code>null</code>.
   * @throws XmlException
   *         if the serialization fails (I/O error, invalid XML, etc.).
   */
  void toXml(XmlWriterIF xmlWriter, V value) throws XmlException;

  /**
   * This method creates a String representation of the given value.
   * 
   * @param value
   *        is the value to get a string. It may be <code>null</code>.
   * @return the string representation of the given value.
   */
  String toString(V value);

  /**
   * This method checks if the given values <code>value1</code> and
   * <code>value2</code> or {@link Object#equals(Object) equal} to each other.
   * This method accepts <code>null</code> values. If both vaues are NOT
   * <code>null</code>, it returns
   * <code>value1.{@link Object#equals(Object) equals}(value2)</code> by
   * default. In specific situations the semantic of equals can be chaned by the
   * implementation of this method.
   * 
   * @param value1
   *        is the first value to compare. May be <code>null</code>.
   * @param value2
   *        is the second value to compare. May be <code>null</code>.
   * @return <code>true</code> if both values are <code>null</code>, or
   *         both are NOT <code>null</code> and are equal to each other.
   */
  boolean eqauls(V value1, V value2);

}
