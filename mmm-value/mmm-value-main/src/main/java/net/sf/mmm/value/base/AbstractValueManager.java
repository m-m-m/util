/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.value.base;

import java.lang.reflect.Type;

import org.w3c.dom.Element;

import net.sf.mmm.nls.api.NlsMessage;
import net.sf.mmm.nls.base.NlsMessageImpl;
import net.sf.mmm.util.xml.DomUtil;
import net.sf.mmm.util.xml.XmlException;
import net.sf.mmm.util.xml.api.XmlWriter;
import net.sf.mmm.value.NlsBundleValueMain;
import net.sf.mmm.value.api.GenericValue;
import net.sf.mmm.value.api.ValueManager;
import net.sf.mmm.value.api.ValueParseException;

/**
 * This is the abstract base implementation of the
 * {@link net.sf.mmm.value.api.ValueManager} interface. It is recommended to
 * extend this class rather than implementing the interface from scratch.
 * 
 * @see net.sf.mmm.value.base.BasicValueManager
 * 
 * @param <V>
 *        is the templated type of the managed value type.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractValueManager<V> implements ValueManager<V> {

  /** the to string message */
  private NlsMessage toStringMessage;

  /**
   * The constructor.
   */
  public AbstractValueManager() {

    super();
    this.toStringMessage = null;
  }

  /**
   * This method gets the NLS message used for {@link #toString()}.
   * 
   * @return the NLS message.
   */
  public final synchronized NlsMessage getToStringMessage() {

    if (this.toStringMessage == null) {
      this.toStringMessage = new NlsMessageImpl(NlsBundleValueMain.MSG_MANAGER_TO_STRING, getName(),
          getValueClass());
    }
    return this.toStringMessage;
  }

  /**
   * The default implementation expects the {@link #toString(Object) string}
   * representation of the value encoded as text in the XML element.
   * 
   * @see net.sf.mmm.value.api.ValueManager#parse(org.w3c.dom.Element)
   */
  public V parse(Element valueAsXml) throws ValueParseException {

    checkXml(valueAsXml);
    String valueAsString = DomUtil.getNodeText(valueAsXml);
    if (valueAsString.equals(getNullString())) {
      return null;
    }
    return parse(valueAsString);
  }

  /**
   * This method creates the content of the XML representation for the given
   * value. This default implementation uses {@link #toString(Object)} to
   * convert the value to string and then writes it as
   * {@link XmlWriter#writeCharacters(String) text} content.<br>
   * Override this method to implement specific custom XML. If you override
   * this method you should also override {@link #parse(Element)}.
   * 
   * @see #toXml(XmlWriter, Object)
   * 
   * @param xmlWriter
   *        is where the XML is written to.
   * @param value
   *        is the value to serialize. It may be <code>null</code>.
   * @throws XmlException
   *         if the XML serialization fails.
   */
  protected void toXmlValue(XmlWriter xmlWriter, V value) throws XmlException {

    xmlWriter.writeCharacters(toString(value));
  }

  /**
   * This method creates a default XML representation of the given value. It
   * will have the following form:
   * 
   * <pre>
   * &lt;value type=&quot;{@link ValueManager#getName() manager.getName()}&quot;&gt;
   * {@link #toXml(XmlWriter, Object) toXmlValue(value)}
   * &lt;/value&gt;
   * </pre>
   * 
   * @see net.sf.mmm.value.api.ValueManager#toXml(XmlWriter, Object)
   */
  public final void toXml(XmlWriter xmlWriter, V value) throws XmlException {

    xmlWriter.writeStartElement(XML_TAG_VALUE);
    xmlWriter.writeAttribute(XML_ATR_VALUE_NAME, getName());
    toXmlValue(xmlWriter, value);
    xmlWriter.writeEndElement(XML_TAG_VALUE);
  }

  /**
   * This method validates the top-level element of the given XML encoded
   * value.
   * 
   * @param valueAsXml
   *        is the XML encoded value to validate.
   * @throws ValueParseException
   *         if the given XML is invalid.
   */
  protected void checkXml(Element valueAsXml) throws ValueParseException {

    if (!XML_TAG_VALUE.equals(valueAsXml.getTagName())) {
      // TODO
      throw new ValueParseException(null);
    }
    if (valueAsXml.hasAttribute(XML_ATR_VALUE_NAME)) {
      if (!valueAsXml.getAttribute(XML_ATR_VALUE_NAME).equals(getName())) {
        // TODO
        throw new ValueParseException(null);
      }
    } else if (valueAsXml.hasAttribute(XML_ATR_VALUE_CLASS)) {
      String className = valueAsXml.getAttribute(XML_ATR_VALUE_CLASS);
      if (!getValueClass().getName().equals(className)) {
        try {
          Class clazz = Class.forName(className);
          if (!getValueClass().isAssignableFrom(clazz)) {
            // TODO
            throw new ValueParseException(null);
          }
        } catch (ClassNotFoundException e) {
          // TODO
          throw new ValueParseException(null, e);
        }
        // TODO
        throw new ValueParseException(null);
      }
    } else {
      // TODO
      throw new ValueParseException(null);
    }
  }
  
  /**
   * {@inheritDoc}
   */
  public Type getValueType() {
  
    return getValueClass();
  }
  
  /**
   * {@inheritDoc}
   */
  public boolean isEqual(V value1, V value2) {
  
    if (value1 == null) {
      return (value2 == null);
    } else {
      if (value2 == null) {
        return false;
      } else {
        return value1.equals(value2);        
      }
    }
  }

  /**
   * This method gets the String that represents the <code>null</code>
   * value. This method always returns the same (==) object ({@link GenericValue#NULL_STRING}).
   * It can be overridden to change the <code>null</code> string.
   * 
   * @return the string that represents the <code>null</code> value.
   */
  protected String getNullString() {

    return GenericValue.NULL_STRING;
  }

  /**
   * {@inheritDoc}
   */
  public String toString(V value) {

    if (value == null) {
      return getNullString();
    }
    return value.toString();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    return getToStringMessage().getMessage();
  }

}
