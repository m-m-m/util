/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.parser.impl.html;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Properties;
import java.util.regex.Pattern;

import javax.inject.Named;
import javax.inject.Singleton;

import net.sf.mmm.content.parser.impl.text.AbstractContentParserTextMarkupAware;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.tidy.Tidy;

/**
 * This is the implementation of the
 * {@link net.sf.mmm.content.parser.api.ContentParser} interface for HTML
 * documents (content with the mimetype "text/html").<br/>
 * It uses JTidy for HTML-parsing but falls back to raw parsing for files that
 * are {@link #getMaximumBufferSize() large} or have unpredictable size to avoid
 * memory problems.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@Singleton
@Named
public class ContentParserHtml extends AbstractContentParserTextMarkupAware {

  /** The mimetype. */
  public static final String KEY_MIMETYPE = "text/html";

  /** The default extension. */
  public static final String KEY_EXTENSION = "html";

  /** the head tag */
  private static final String TAG_HEAD = "head";

  /** the title tag */
  private static final String TAG_TITLE = "title";

  /** the meta tag */
  private static final String TAG_META = "meta";

  /** the name attribute of the meta tag */
  private static final String ATR_META_NAME = "name";

  /** the content attribute of the meta tag */
  private static final String ATR_META_CONTENT = "content";

  /** the body tag */
  private static final String TAG_BODY = "body";

  /** pattern to extract the title */
  private static final Pattern TITLE_PATTERN = Pattern.compile(".*<title>([^<]*)</title>");

  /** pattern to extract the author */
  private static final Pattern AUTHOR_PATTERN = Pattern
      .compile(".*<meta name=[\"']author[\"'] content=[\"']([^\"']*)[\"'].*");

  /** pattern to extract the title */
  private static final Pattern KEYWORDS_PATTERN = Pattern
      .compile(".*<meta name=[\"']keywords[\"'] content=[\"']([^\"']*)[\"'].*");

  /**
   * The constructor.
   */
  public ContentParserHtml() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String[] getRegistryKeysPrimary() {

    return new String[] { KEY_EXTENSION, KEY_MIMETYPE };
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String[] getRegistryKeysSecondary() {

    return new String[] { "htm", "php", "jsp", "hta" };
  }

  /**
   * @see #parse(InputStream, long)
   * 
   * @param inputStream is the fresh input stream of the content to parse. It
   *        will be {@link InputStream#close() closed} by this method (on
   *        success and in exceptional state).
   * @param filesize is the size (content-length) of the content to parse in
   *        bytes or <code>0</code> if NOT available (unknown). If available,
   *        the parser may use this value for optimized allocations.
   * @param properties are the {@link Properties} where metadata can be added.
   * @throws Exception on error.
   */
  protected void parseJtidy(InputStream inputStream, long filesize, Properties properties)
      throws Exception {

    Tidy tidy = new Tidy();
    tidy.setErrout(new PrintWriter(new NullWriter()));
    Document xmlDoc = tidy.parseDOM(inputStream, null);
    Element htmlElement = xmlDoc.getDocumentElement();
    // read header data...
    Element headElement = getFirstChildElement(htmlElement, TAG_HEAD);
    String title = null;
    if (headElement != null) {
      NodeList nodeList = headElement.getChildNodes();
      for (int nodeIndex = 0; nodeIndex < nodeList.getLength(); nodeIndex++) {
        Node node = nodeList.item(nodeIndex);
        if (node.getNodeType() == Node.ELEMENT_NODE) {
          Element childElement = (Element) node;
          String tagName = childElement.getTagName();
          if (tagName.equals(TAG_TITLE)) {
            title = getTextContent(childElement);
          } else if (tagName.equals(TAG_META)) {
            String metaName = childElement.getAttribute(ATR_META_NAME);
            if (metaName.equalsIgnoreCase("keywords")) {
              properties.setProperty(PROPERTY_KEY_KEYWORDS,
                  childElement.getAttribute(ATR_META_CONTENT));
            } else if (metaName.equalsIgnoreCase("author")) {
              properties.setProperty(PROPERTY_KEY_AUTHOR,
                  childElement.getAttribute(ATR_META_CONTENT));
            }
          }
        }
      }
    }
    if (title != null) {
      properties.setProperty(PROPERTY_KEY_TITLE, title);
    }
    // read body data...
    Element bodyElement = getFirstChildElement(htmlElement, TAG_BODY);
    if (bodyElement != null) {
      properties.setProperty(PROPERTY_KEY_TEXT, getTextContent(bodyElement));
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void parse(InputStream inputStream, long filesize, String encoding, Properties properties)
      throws Exception {

    if ((filesize > 0) && (filesize < getMaximumBufferSize())) {
      parseJtidy(inputStream, filesize, properties);
    } else {
      super.parse(inputStream, filesize, encoding, properties);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void parseLine(Properties properties, String line) {

    parseProperty(properties, line, TITLE_PATTERN, PROPERTY_KEY_TITLE, 1);
    parseProperty(properties, line, AUTHOR_PATTERN, PROPERTY_KEY_AUTHOR, 1);
    parseProperty(properties, line, KEYWORDS_PATTERN, PROPERTY_KEY_KEYWORDS, 1);
  }

  /**
   * This method gets the first {@link Node#getChildNodes() child} element of
   * <code>element</code> with the given
   * <code>{@link Element#getTagName() tagname}</code>.
   * 
   * @param element is the element where the child is requested from.
   * @param tagname is the tagname of the requested child element.
   * @return the first child-element with the given <code>tagname</code> or
   *         <code>null</code> if no such child exists.
   */
  private Element getFirstChildElement(Element element, String tagname) {

    NodeList nodeList = element.getChildNodes();
    for (int nodeIndex = 0; nodeIndex < nodeList.getLength(); nodeIndex++) {
      Node node = nodeList.item(nodeIndex);
      if (node.getNodeType() == Node.ELEMENT_NODE) {
        Element childElement = (Element) node;
        if (childElement.getTagName().equals(tagname)) {
          return childElement;
        }
      }
    }
    return null;
  }

  /**
   * This method gets all text content from the given <code>element</code>
   * recursively including the text of all children.
   * 
   * @param element is the element for which the text is requested.
   * @return the requested text.
   */
  private String getTextContent(Element element) {

    StringBuffer buffer = new StringBuffer();
    collectTextContent(element, buffer);
    return buffer.toString();
  }

  /**
   * This method recursively collects the text from the given
   * <code>element</code> and appends it to the given <code>buffer</code>.
   * 
   * @param element is the element for which the text is requested.
   * @param buffer is the buffer where the text will be appended to.
   */
  private void collectTextContent(Element element, StringBuffer buffer) {

    NodeList nodeList = element.getChildNodes();
    for (int nodeIndex = 0; nodeIndex < nodeList.getLength(); nodeIndex++) {
      Node childNode = nodeList.item(nodeIndex);
      short nodeType = childNode.getNodeType();
      if (nodeType == Node.ELEMENT_NODE) {
        collectTextContent((Element) childNode, buffer);
      } else if ((nodeType == Node.TEXT_NODE) || (nodeType == Node.CDATA_SECTION_NODE)) {
        buffer.append(childNode.getNodeValue());
        // buffer.append(' ');
      }
    }
  }

  /**
   * This is a writer that does nothing.
   */
  @SuppressWarnings("all")
  private static class NullWriter extends Writer {

    /**
     * {@inheritDoc}
     */
    public void close() throws IOException {

    }

    /**
     * {@inheritDoc}
     */
    public void flush() throws IOException {

    }

    /**
     * {@inheritDoc}
     */
    public void write(char[] cbuf, int off, int len) throws IOException {

    }

  }

}
