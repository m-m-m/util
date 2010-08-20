/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.text.base;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import net.sf.mmm.util.lang.api.StringUtil;
import net.sf.mmm.util.lang.base.StringUtilImpl;
import net.sf.mmm.util.nls.api.NlsIllegalStateException;
import net.sf.mmm.util.resource.api.DataResource;
import net.sf.mmm.util.resource.base.ClasspathResource;
import net.sf.mmm.util.text.api.Hyphenator;
import net.sf.mmm.util.text.api.HyphenatorBuilder;
import net.sf.mmm.util.text.api.StringHasher;
import net.sf.mmm.util.xml.api.StaxUtil;
import net.sf.mmm.util.xml.base.StaxUtilImpl;
import net.sf.mmm.util.xml.base.XmlInvalidException;

/**
 * The implementation of the {@link net.sf.mmm.util.text.api.HyphenatorBuilder}
 * interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
@Singleton
@Named
public class HyphenatorBuilderImpl extends AbstractHyphenatorBuilder {

  /** @see #createHyphenator(String) */
  public static final String HYPHENATION_XML_CONFIG_PREFIX = "net/sf/mmm/util/text/hyphenation";

  /** @see #createHyphenator(String) */
  public static final String HYPHENATION_XML_CONFIG_SUFFIX = ".xml";

  /** The XML root tag: {@value} . */
  private static final QName XML_TAG_HYPHENATION = new QName("hyphenation");

  /** The XML attribute: {@value} . */
  private static final QName XML_ATR_HYPHEN = new QName("hyphen");

  /** The XML tag: {@value} . */
  private static final QName XML_TAG_EXCEPTION_LIST = new QName("exceptions");

  /** The XML tag: {@value} . */
  private static final QName XML_TAG_PATTERN_LIST = new QName("patterns");

  /** The XML tag: {@value} . */
  private static final QName XML_TAG_EXCEPTION = new QName("e");

  /** The XML tag: {@value} . */
  private static final QName XML_TAG_PATTERN = new QName("p");

  /** @see #getInstance() */
  private static HyphenatorBuilder instance;

  /** @see #getStaxUtil() */
  private StaxUtil staxUtil;

  /** @see #getStringUtil() */
  private StringUtil stringUtil;

  /** @see #getStringHasher() */
  private StringHasher stringHasher;

  /**
   * The constructor.
   */
  public HyphenatorBuilderImpl() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doInitialize() {

    super.doInitialize();
    if (this.staxUtil == null) {
      this.staxUtil = StaxUtilImpl.getInstance();
    }
    if (this.stringUtil == null) {
      this.stringUtil = StringUtilImpl.getInstance();
    }
    if (this.stringHasher == null) {
      this.stringHasher = new FastStringHasher();
    }
  }

  /**
   * This method gets the singleton instance of this
   * {@link HyphenatorBuilderImpl}.<br>
   * This design is the best compromise between easy access (via this
   * indirection you have direct, static access to all offered functionality)
   * and IoC-style design which allows extension and customization.<br>
   * For IoC usage, simply ignore all static {@link #getInstance()} methods and
   * construct new instances via the container-framework of your choice (like
   * plexus, pico, springframework, etc.). To wire up the dependent components
   * everything is properly annotated using common-annotations (JSR-250). If
   * your container does NOT support this, you should consider using a better
   * one.
   * 
   * @return the singleton instance.
   */
  public static HyphenatorBuilder getInstance() {

    if (instance == null) {
      synchronized (HyphenatorBuilderImpl.class) {
        if (instance == null) {
          HyphenatorBuilderImpl impl = new HyphenatorBuilderImpl();
          impl.initialize();
          instance = impl;
        }
      }
    }
    return instance;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Hyphenator createHyphenator(String localeInfix) {

    String classpath = HYPHENATION_XML_CONFIG_PREFIX + localeInfix + HYPHENATION_XML_CONFIG_SUFFIX;
    ClasspathResource resource = new ClasspathResource(classpath);
    if (resource.isAvailable()) {
      Locale locale = getResourceLocator().getLocaleForInfix(localeInfix);
      return createHyphenator(locale, resource);
    }
    return null;
  }

  /**
   * This method creates a new {@link Hyphenator} instance from the
   * XML-configuration identified by the given {@link DataResource}.
   * 
   * @param locale is the {@link Hyphenator#getLocale() locale}.
   * @param resource is the {@link DataResource} with the XML-configuration.
   * @return the {@link Hyphenator} instance.
   */
  protected Hyphenator createHyphenator(Locale locale, DataResource resource) {

    try {
      // the default hyphen character
      char hyphen = '-';
      InputStream inputStream = resource.openStream();
      try {
        XMLEventReader reader = this.staxUtil.createXmlEventReader(inputStream);
        XMLEvent event = this.staxUtil.nextElement(reader);
        if (event.getEventType() != XMLStreamConstants.START_ELEMENT) {
          // TODO
          throw new NlsIllegalStateException();
        }
        StartElement startElementEvent = (StartElement) event;
        if (!XML_TAG_HYPHENATION.equals(startElementEvent.getName())) {
          // TODO
          throw new NlsIllegalStateException();
        }
        Attribute attribute = startElementEvent.getAttributeByName(XML_ATR_HYPHEN);
        if (attribute != null) {
          String value = attribute.getValue();
          if (value.length() == 1) {
            hyphen = value.charAt(0);
          } else {
            // TODO
            throw new NlsIllegalStateException();
          }
        }
        List<String> patterns = null;
        List<String> exceptions = null;
        event = this.staxUtil.nextElement(reader);
        while (event.getEventType() == XMLStreamConstants.START_ELEMENT) {
          startElementEvent = (StartElement) event;
          QName tag = startElementEvent.getName();
          if (XML_TAG_EXCEPTION_LIST.equals(tag)) {
            if (exceptions != null) {
              // TODO duplicate tag
              throw new NlsIllegalStateException();
            }
            exceptions = parseStringList(reader, XML_TAG_EXCEPTION);
          } else if (XML_TAG_PATTERN_LIST.equals(tag)) {
            if (patterns != null) {
              // TODO duplicate tag
              throw new NlsIllegalStateException();
            }
            patterns = parseStringList(reader, XML_TAG_PATTERN);
          } else {
            // ignore unknown elements...
            this.staxUtil.skipOpenElement(reader);
          }
          event = this.staxUtil.nextElement(reader);
        }
        if (patterns == null) {
          // TODO
          throw new NlsIllegalStateException();
        }
        reader.close();
        HyphenatorImpl hyphenator = new HyphenatorImpl(locale, hyphen, patterns, exceptions,
            this.stringHasher, this.stringUtil);
        hyphenator.initialize();
        return hyphenator;
      } finally {
        try {
          inputStream.close();
        } catch (IOException e) {
          // ignore...
        }
      }
    } catch (XMLStreamException e) {
      // TODO ...
      throw new XmlInvalidException(e);
    }
  }

  /**
   * This method parses a list of strings enclosed by elements with the given
   * <code>elementName</code> until the current element ends. E.g. if you have
   * this XML:
   * 
   * <pre>
   * &lt;foo>
   *   &lt;bar>text1&lt;/bar>
   *   &lt;bar>text2&lt;/bar>
   *   &lt;bar>text3&lt;/bar>
   * &lt;/foo>
   * </pre>
   * 
   * And your XML-reader is pointing after the opening &lt;foo> and you call
   * this method, you will get a list with "text1", "text2", and "text3".
   * 
   * @param reader is the XMLEventReader pointing after the {@link StartElement}
   *        of the surrounding element.
   * @param elementName is the name of the element containing a single string.
   * @return the List of strings encountered until the surrounding element was
   *         closed.
   * @throws XMLStreamException if caused by the reader.
   */
  private List<String> parseStringList(XMLEventReader reader, QName elementName)
      throws XMLStreamException {

    List<String> result = new ArrayList<String>();
    XMLEvent event = this.staxUtil.nextElement(reader);
    while (event.getEventType() == XMLStreamConstants.START_ELEMENT) {
      StartElement startElementEvent = (StartElement) event;
      if (elementName.equals(startElementEvent.getName())) {
        String text = reader.getElementText();
        result.add(text);
      } else {
        // TODO unexpected tag
        throw new NlsIllegalStateException();
      }
      event = this.staxUtil.nextElement(reader);
    }
    return result;
  }

  /**
   * @return the staxUtil
   */
  protected StaxUtil getStaxUtil() {

    return this.staxUtil;
  }

  /**
   * @param staxUtil is the staxUtil to set
   */
  @Inject
  public void setStaxUtil(StaxUtil staxUtil) {

    getInitializationState().requireNotInitilized();
    this.staxUtil = staxUtil;
  }

  /**
   * @return the stringUtil
   */
  protected StringUtil getStringUtil() {

    return this.stringUtil;
  }

  /**
   * @param stringUtil is the stringUtil to set
   */
  @Inject
  public void setStringUtil(StringUtil stringUtil) {

    getInitializationState().requireNotInitilized();
    this.stringUtil = stringUtil;
  }

  /**
   * @return the stringHasher
   */
  protected StringHasher getStringHasher() {

    return this.stringHasher;
  }

  /**
   * @param stringHasher is the stringHasher to set
   */
  public void setStringHasher(StringHasher stringHasher) {

    getInitializationState().requireNotInitilized();
    this.stringHasher = stringHasher;
  }

}
