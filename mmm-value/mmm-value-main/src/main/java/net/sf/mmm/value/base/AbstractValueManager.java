/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.value.base;

import java.lang.reflect.Type;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

import net.sf.mmm.nls.api.NlsMessage;
import net.sf.mmm.nls.base.NlsMessageImpl;
import net.sf.mmm.util.xml.StaxUtil;
import net.sf.mmm.value.NlsBundleValueMain;
import net.sf.mmm.value.api.ValueManager;

/**
 * This is the abstract base implementation of the {@link ValueManager}
 * interface. It is recommended to extend this class rather than implementing
 * the interface from scratch.
 * 
 * @see net.sf.mmm.value.base.BasicValueManager
 * 
 * @param <V> is the templated type of the managed value type.
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
      this.toStringMessage = new NlsMessageImpl(NlsBundleValueMain.MSG_MANAGER_TO_STRING,
          getName(), getValueClass());
    }
    return this.toStringMessage;
  }

  /**
   * {@inheritDoc}
   */
  public V fromXml(XMLStreamReader xmlReader) throws XMLStreamException {

    assert (xmlReader.isStartElement());
    assert (XML_TAG_VALUE.equals(xmlReader.getLocalName()));
    String valueName = xmlReader.getAttributeValue(null, XML_ATR_VALUE_NAME);
    if (NULL_VALUE_NAME.equals(valueName)) {
      return null;
    }
    if (!getName().equals(valueName)) {
      // TODO:
      throw new IllegalArgumentException();
    }
    V value = fromXmlContent(xmlReader);
    StaxUtil.skipOpenElement(xmlReader);
    return value;
  }

  /**
   * @see #fromXml(XMLStreamReader)
   * 
   * @param xmlReader is where to read the XML from.
   * @return the parsed value.
   * @throws XMLStreamException if the de-serialization fails.
   */
  protected V fromXmlContent(XMLStreamReader xmlReader) throws XMLStreamException {

    String textContent = StaxUtil.readText(xmlReader);
    return fromString(textContent);
  }

  /**
   * {@inheritDoc}
   */
  public void toXml(XMLStreamWriter xmlWriter, V value) throws XMLStreamException {

    xmlWriter.writeStartElement(XML_TAG_VALUE);
    if (value == null) {
      xmlWriter.writeAttribute(XML_ATR_VALUE_NAME, NULL_VALUE_NAME);
    } else {
      xmlWriter.writeAttribute(XML_ATR_VALUE_NAME, getName());
      toXmlContent(xmlWriter, value);
    }
    xmlWriter.writeEndElement();
  }

  /**
   * @see #toXml(XMLStreamWriter, Object)
   * 
   * @param xmlWriter is where to write the XML to.
   * @param value is the value to serialize.
   * @throws XMLStreamException if the serialization fails.
   */
  protected void toXmlContent(XMLStreamWriter xmlWriter, V value) throws XMLStreamException {

    xmlWriter.writeCharacters(value.toString());
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
   * {@inheritDoc}
   * 
   * <b>INFORMATION:</b><br>
   * This method is final. You have to override the method
   * {@link #toStringNotNull(Object)}
   */
  public final String toString(V value) {

    if (value == null) {
      return NULL_STRING;
    }
    return toStringNotNull(value);
  }

  /**
   * This method is the internal version of {@link #toString(Object)} that is
   * called if the given <code>value</code> is NOT <code>null</code>.
   * 
   * @param value is the value to get a string.
   * @return the string representation of the given value.
   */
  protected String toStringNotNull(V value) {

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
