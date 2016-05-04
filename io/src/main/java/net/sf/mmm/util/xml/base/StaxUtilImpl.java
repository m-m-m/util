/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.xml.base;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.stream.events.XMLEvent;

import net.sf.mmm.util.component.base.AbstractLoggableComponent;
import net.sf.mmm.util.exception.api.IllegalCaseException;
import net.sf.mmm.util.resource.api.DataResource;
import net.sf.mmm.util.value.api.StringValueConverter;
import net.sf.mmm.util.value.api.ValueException;
import net.sf.mmm.util.value.base.StringValueConverterImpl;
import net.sf.mmm.util.xml.api.StaxUtil;
import net.sf.mmm.util.xml.api.XmlException;
import net.sf.mmm.util.xml.api.XmlGenericException;
import net.sf.mmm.util.xml.impl.stax.XIncludeStreamReader;

/**
 * This utility class contains methods that help to work with the StAX API (JSR 173).
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.2
 */
@Singleton
@Named(StaxUtil.CDI_NAME)
public final class StaxUtilImpl extends AbstractLoggableComponent implements StaxUtil {

  private static StaxUtil instance;

  private XMLInputFactory xmlInputFactory;

  private XMLOutputFactory xmlOutputFactory;

  private StringValueConverter valueConverter;

  /**
   * The constructor.
   */
  public StaxUtilImpl() {

    super();
  }

  /**
   * This method gets the singleton instance of this {@link StaxUtilImpl}. <br>
   * <b>ATTENTION:</b><br>
   * Please read {@link net.sf.mmm.util.component.api.Cdi#GET_INSTANCE} before using.
   *
   * @return the singleton instance.
   */
  public static StaxUtil getInstance() {

    if (instance == null) {
      synchronized (StaxUtilImpl.class) {
        if (instance == null) {
          StaxUtilImpl util = new StaxUtilImpl();
          util.initialize();
          instance = util;
        }
      }
    }
    return instance;
  }

  /**
   * @return the valueConverter
   */
  protected StringValueConverter getValueConverter() {

    return this.valueConverter;
  }

  /**
   * @param valueConverter the valueConverter to set
   */
  @Inject
  public void setValueConverter(StringValueConverter valueConverter) {

    getInitializationState().requireNotInitilized();
    this.valueConverter = valueConverter;
  }

  /**
   * This method gets the {@link XMLOutputFactory} to use.
   *
   * @return the xmlOutputFactory
   */
  protected XMLOutputFactory getXmlOutputFactory() {

    return this.xmlOutputFactory;
  }

  /**
   * This method sets the {@link #getXmlOutputFactory() XML-output-factory}.
   *
   * @param xmlOutputFactory is the xmlOutputFactory to set.
   */
  public void setXmlOutputFactory(XMLOutputFactory xmlOutputFactory) {

    getInitializationState().requireNotInitilized();
    this.xmlOutputFactory = xmlOutputFactory;
  }

  /**
   * This method gets the {@link XMLInputFactory} to use.
   *
   * @return the xmlInputFactory
   */
  public XMLInputFactory getXmlInputFactory() {

    return this.xmlInputFactory;
  }

  /**
   * @param xmlInputFactory is the xmlInputFactory to set
   */
  public void setXmlInputFactory(XMLInputFactory xmlInputFactory) {

    getInitializationState().requireNotInitilized();
    this.xmlInputFactory = xmlInputFactory;
  }

  @Override
  protected void doInitialize() {

    super.doInitialize();
    if (this.valueConverter == null) {
      this.valueConverter = StringValueConverterImpl.getInstance();
    }
    if (this.xmlInputFactory == null) {
      this.xmlInputFactory = XMLInputFactory.newInstance();
    }
    if (this.xmlOutputFactory == null) {
      this.xmlOutputFactory = XMLOutputFactory.newInstance();
    }
  }

  @Override
  public XMLEventReader createXmlEventReader(InputStream inputStream) {

    try {
      return this.xmlInputFactory.createXMLEventReader(inputStream);
    } catch (XMLStreamException e) {
      throw new XmlGenericException(e);
    }
  }

  @Override
  public XMLStreamReader createXmlStreamReader(InputStream inputStream) {

    try {
      return this.xmlInputFactory.createXMLStreamReader(inputStream);
    } catch (XMLStreamException e) {
      throw new XmlGenericException(e);
    }
  }

  @Override
  public XMLStreamReader createXmlStreamReader(DataResource resource, boolean xIncludeAware) throws XmlException {

    if (xIncludeAware) {
      return new XIncludeStreamReader(this.xmlInputFactory, resource);
    } else {
      // closing of stream has to be performed via XMLStreamReader.close()
      // however the strange StAX specification prevents this...
      // TODO: create wrapper to ensure closing of stream...
      return createXmlStreamReader(resource.openStream());
    }
  }

  @Override
  public XMLStreamWriter createXmlStreamWriter(OutputStream out) {

    try {
      return getXmlOutputFactory().createXMLStreamWriter(out);
    } catch (XMLStreamException e) {
      throw new XmlGenericException(e);
    }
  }

  @Override
  public XMLStreamWriter createXmlStreamWriter(Writer writer) {

    try {
      return getXmlOutputFactory().createXMLStreamWriter(writer);
    } catch (XMLStreamException e) {
      throw new XmlGenericException(e);
    }
  }

  @Override
  public <V> V parseAttribute(XMLStreamReader xmlReader, String namespaceUri, String localAttributeName,
      Class<V> type) throws ValueException {

    String value = xmlReader.getAttributeValue(namespaceUri, localAttributeName);
    String valueSource = xmlReader.getLocalName() + "/@" + localAttributeName;
    return getValueConverter().convertValue(value, valueSource, type);
  }

  @Override
  public <V> V parseAttribute(XMLStreamReader xmlReader, String namespaceUri, String localAttributeName,
      Class<V> type, V defaultValue) throws ValueException {

    String value = xmlReader.getAttributeValue(namespaceUri, localAttributeName);
    return getValueConverter().convertValue(value, localAttributeName, type, type, defaultValue);
  }

  @Override
  public String readText(XMLStreamReader xmlReader) {

    try {
      int eventType = xmlReader.getEventType();
      if (eventType == XMLStreamConstants.START_ELEMENT) {
        eventType = xmlReader.next();
      }
      while (eventType == XMLStreamConstants.ATTRIBUTE) {
        eventType = xmlReader.next();
      }
      if (eventType == XMLStreamConstants.END_ELEMENT) {
        return "";
      }
      if ((eventType == XMLStreamConstants.CHARACTERS) || (eventType == XMLStreamConstants.CDATA)) {
        return xmlReader.getText();
      }
      throw new IllegalCaseException(getEventTypeName(eventType));
    } catch (XMLStreamException e) {
      throw new XmlGenericException(e);
    }
  }

  @Override
  public void skipOpenElement(XMLStreamReader xmlReader) {

    try {
      int depth = 1;
      while (depth != 0) {
        int eventType = xmlReader.nextTag();
        if (eventType == XMLStreamConstants.START_ELEMENT) {
          depth++;
          // } else if (eventType == XMLStreamConstants.END_ELEMENT) {
        } else {
          depth--;
        }
      }
    } catch (XMLStreamException e) {
      throw new XmlGenericException(e);
    }
  }

  @Override
  public void skipOpenElement(XMLEventReader xmlReader) {

    try {
      int depth = 1;
      while (depth != 0) {
        XMLEvent event = xmlReader.nextEvent();
        int eventType = event.getEventType();
        if (eventType == XMLStreamConstants.START_ELEMENT) {
          depth++;
        } else if (eventType == XMLStreamConstants.END_ELEMENT) {
          depth--;
        }
      }
    } catch (XMLStreamException e) {
      throw new XmlGenericException(e);
    }
  }

  @Override
  public XMLEvent nextElement(XMLEventReader xmlReader) {

    try {
      while (xmlReader.hasNext()) {
        XMLEvent event = xmlReader.nextEvent();
        int eventType = event.getEventType();
        if ((eventType == XMLStreamConstants.START_ELEMENT) || (eventType == XMLStreamConstants.END_ELEMENT)
            || (eventType == XMLStreamConstants.END_DOCUMENT)) {
          return event;
        }
      }
      return null;
    } catch (XMLStreamException e) {
      throw new XmlGenericException(e);
    }
  }

  @Override
  public String getEventTypeName(int eventType) {

    switch (eventType) {
      case XMLStreamConstants.ATTRIBUTE:
        return "ATTRIBUTE";
      case XMLStreamConstants.CDATA:
        return "CDATA";
      case XMLStreamConstants.CHARACTERS:
        return "CHARACTERS";
      case XMLStreamConstants.COMMENT:
        return "COMMENT";
      case XMLStreamConstants.DTD:
        return "DTD";
      case XMLStreamConstants.END_DOCUMENT:
        return "END_DOCUMENT";
      case XMLStreamConstants.END_ELEMENT:
        return "END_ELEMENT";
      case XMLStreamConstants.ENTITY_DECLARATION:
        return "ENTITY_DECLARATION";
      case XMLStreamConstants.ENTITY_REFERENCE:
        return "ENTITY_REFERENCE";
      case XMLStreamConstants.NAMESPACE:
        return "NAMESPACE";
      case XMLStreamConstants.NOTATION_DECLARATION:
        return "NOTATION_DECLARATION";
      case XMLStreamConstants.PROCESSING_INSTRUCTION:
        return "PROCESSING_INSTRUCTION";
      case XMLStreamConstants.SPACE:
        return "SPACE";
      case XMLStreamConstants.START_DOCUMENT:
        return "START_DOCUMENT";
      case XMLStreamConstants.START_ELEMENT:
        return "START_ELEMENT";
      default:
        return "UNKNOWN_EVENT_TYPE (" + String.valueOf(eventType) + ")";
    }
  }

}
