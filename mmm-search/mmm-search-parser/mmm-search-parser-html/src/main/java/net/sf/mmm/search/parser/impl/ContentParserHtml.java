/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.parser.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Properties;
import java.util.regex.Pattern;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.tidy.Tidy;

/**
 * This is the implementation of the
 * {@link net.sf.mmm.search.parser.api.ContentParser} interface for HTML
 * documents (content with the mimetype "text/html").
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ContentParserHtml extends ContentParserText {

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
      .compile(".*<meta name=[\"']author[\"'] content=[\"']([^\"']*)[\"']");

  /** pattern to extract the title */
  private static final Pattern KEYWORDS_PATTERN = Pattern
      .compile(".*<meta name=[\"']keywords[\"'] content=[\"']([^\"']*)[\"']");

  /**
   * The constructor
   */
  public ContentParserHtml() {

    super();
  }

  /**
   * @see net.sf.mmm.search.parser.impl.ContentParserText#parse(java.io.InputStream,
   *      long)
   * 
   * @param inputStream
   * @param filesize
   * @return the parsed properties.
   * @throws Exception
   */
  protected Properties parseJtidy(InputStream inputStream, long filesize) throws Exception {

    Properties properties = new Properties();
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
              properties.setProperty(PROPERTY_KEY_KEYWORDS, childElement
                  .getAttribute(ATR_META_CONTENT));
            } else if (metaName.equalsIgnoreCase("author")) {
              properties.setProperty(PROPERTY_KEY_AUTHOR, childElement
                  .getAttribute(ATR_META_CONTENT));
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
    return properties;
  }

  /**
   * {@inheritDoc}
   */
  public Properties parse(InputStream inputStream, long filesize) throws Exception {

    if ((filesize > 0) && (filesize < getMaximumBufferSize())) {
      return parseJtidy(inputStream, filesize);
    } else {
      return super.parse(inputStream, filesize);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected String parseLine(Properties properties, String line) {

    parseProperty(properties, line, TITLE_PATTERN, PROPERTY_KEY_TITLE);
    parseProperty(properties, line, AUTHOR_PATTERN, PROPERTY_KEY_AUTHOR);
    parseProperty(properties, line, KEYWORDS_PATTERN, PROPERTY_KEY_KEYWORDS);
    int capacity = line.length() / 2;
    StringBuffer buffer = new StringBuffer(capacity);
    char[] chars = line.toCharArray();
    boolean inTag = false;
    char inAttribute = 0;
    for (int i = 0; i < chars.length; i++) {
      char c = chars[i];
      if (c == '<') {
        inTag = true;
      } else if (c == '>') {
        if (inAttribute == 0) {
          inTag = false;
          inAttribute = 0;
        }
      } else if (inTag && ((c == '"') || (c == '\''))) {
        if (inAttribute == 0) {
          inAttribute = c;
        } else {
          inAttribute = 0;
        }
      } else if (!inTag) {
        if (c == '&') {
          // TODO: unescaping of enities...
          buffer.append(c);
        } else {
          buffer.append(c);
        }
      }

    }
    return buffer.toString();
  }

  /**
   * This method gets the first {@link Node#getChildNodes() child} element of
   * <code>element</code> with the given
   * <code>{@link Element#getTagName() tagname}</code>.
   * 
   * @param element
   *        is the element where the child is requested from.
   * @param tagname
   *        is the tagname of the requested child element.
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
   * @param element
   *        is the element for which the text is requested.
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
   * @param element
   *        is the element for which the text is requested.
   * @param buffer
   *        is the buffer where the text will be appended to.
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
        buffer.append(' ');
      }
    }
  }

  /**
   * This is a writer that does nothing.
   */
  @SuppressWarnings("all")
  private static class NullWriter extends Writer {

    public void close() throws IOException {

    }

    public void flush() throws IOException {

    }

    public void write(char[] cbuf, int off, int len) throws IOException {

    }

  }

}
