/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.text.base;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import net.sf.mmm.util.lang.api.LocaleHelper;
import net.sf.mmm.util.text.api.Hyphenator;
import net.sf.mmm.util.text.api.HyphenatorBuilder;
import net.sf.mmm.util.text.api.StringHasher;

/**
 * The implementation of {@link HyphenatorBuilder}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public class HyphenatorBuilderImpl extends AbstractHyphenatorBuilder {

  /** @see #createHyphenator(String) */
  public static final String HYPHENATION_XML_CONFIG_PREFIX = "net/sf/mmm/util/text/hyphenation";

  /** @see #createHyphenator(String) */
  public static final String HYPHENATION_XML_CONFIG_SUFFIX = ".xml";

  /** The XML root tag ({@code hyphenation}). */
  private static final String XML_TAG_HYPHENATION = "hyphenation";

  /** The XML attribute for the hyphen character ({@code hyphen}). */
  private static final String XML_ATR_HYPHEN = "hyphen";

  /** The XML tag for the list of exceptions to hyphenation rules ({@code exceptions}). */
  private static final String XML_TAG_EXCEPTION_LIST = "exceptions";

  /** The XML tag for the list of patterns ({@code patterns}). */
  private static final String XML_TAG_PATTERN_LIST = "patterns";

  /** The XML tag for an exception ({@code e}). */
  private static final String XML_TAG_EXCEPTION = "e";

  /** The XML tag for a pattern ({@code p}). */
  private static final String XML_TAG_PATTERN = "p";

  private static HyphenatorBuilder instance;

  private StringHasher stringHasher;

  /**
   * The constructor.
   */
  public HyphenatorBuilderImpl() {

    super();
  }

  @Override
  protected void doInitialize() {

    super.doInitialize();
    if (this.stringHasher == null) {
      this.stringHasher = new FastStringHasher();
    }
  }

  /**
   * This method gets the singleton instance of this {@link HyphenatorBuilderImpl}. <br>
   * <b>ATTENTION:</b><br>
   * Please prefer dependency-injection instead of using this method.
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

  @Override
  protected Hyphenator createHyphenator(String localeInfix) {

    String classpath = HYPHENATION_XML_CONFIG_PREFIX + localeInfix + HYPHENATION_XML_CONFIG_SUFFIX;
    URL url = Thread.currentThread().getContextClassLoader().getResource(classpath);
    if (url != null) {
      Locale locale = LocaleHelper.getLocale(localeInfix);
      return createHyphenator(locale, url);
    }
    return null;
  }

  /**
   * @param locale is the {@link Hyphenator#getLocale() locale}.
   * @param resource is the {@link URL} to the XML-configuration.
   * @return the {@link Hyphenator} instance.
   */
  protected Hyphenator createHyphenator(Locale locale, URL resource) {

    try {
      SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
      SAXParser saxParser = saxParserFactory.newSAXParser();
      XmlHandler handler = new XmlHandler();
      try (InputStream inputStream = resource.openStream()) {
        saxParser.parse(inputStream, handler);
        if (handler.patterns.isEmpty()) {
          throw new IllegalStateException("No patterns in XML config!");
        }
        HyphenatorImpl hyphenator = new HyphenatorImpl(locale, handler.hyphen, handler.patterns, handler.exceptions, this.stringHasher);
        hyphenator.initialize();
        return hyphenator;
      }
    } catch (Exception e) {
      throw new IllegalStateException("Error parsing XML from " + resource, e);
    }
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

  private static class XmlHandler extends DefaultHandler {

    private boolean rootPresent;

    private char hyphen;

    private List<String> patterns;

    private List<String> exceptions;

    private List<String> activeList;

    public XmlHandler() {

      super();
      this.hyphen = '-'; // default
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

      this.activeList = null;
      if (!this.rootPresent) {
        if (!qName.equals(XML_TAG_HYPHENATION)) {
          throw new IllegalStateException("Invalid root tag '" + qName + "'.");
        }
        this.rootPresent = true;
        String hyp = attributes.getValue(XML_ATR_HYPHEN);
        if (hyp != null) {
          if (hyp.length() == 1) {
            this.hyphen = hyp.charAt(0);
          } else {
            throw new IllegalStateException("Invalid hypen character '" + hyp + "'.");
          }
        }
      }
      if (qName.equals(XML_TAG_PATTERN)) {
        this.activeList = this.patterns;
      } else if (qName.equals(XML_TAG_EXCEPTION)) {
        this.activeList = this.exceptions;
      } else if (qName.equals(XML_TAG_PATTERN_LIST)) {
        if (this.patterns == null) {
          this.patterns = new ArrayList<>();
        }
      } else if (qName.equals(XML_TAG_EXCEPTION_LIST)) {
        if (this.exceptions == null) {
          this.exceptions = new ArrayList<>();
        }
      }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {

      this.activeList = null;
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {

      if (this.activeList != null) {
        this.activeList.add(new String(ch, start, length));
      }
    }

  }

}
