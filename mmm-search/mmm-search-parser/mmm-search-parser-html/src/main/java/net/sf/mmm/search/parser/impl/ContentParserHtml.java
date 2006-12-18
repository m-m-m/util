/* $Id$ */
package net.sf.mmm.search.parser.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Properties;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.tidy.Tidy;

import net.sf.mmm.search.parser.api.ContentParser;

/**
 * This is the implementation of the
 * {@link net.sf.mmm.search.parser.api.ContentParser} interface for HTML
 * documents (content with the mimetype "text/html").
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ContentParserHtml implements ContentParser {

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

  /**
   * The constructor
   */
  public ContentParserHtml() {

    super();
  }

  /**
   * @see net.sf.mmm.search.parser.api.ContentParser#parse(java.io.InputStream,
   *      java.lang.String)
   */
  public Properties parse(InputStream inputStream, String filename) throws Exception {

    Properties properties = new Properties();
    Tidy tidy = new Tidy();
    Writer writer = new Writer() {

      public void close() throws IOException {

      }

      public void flush() throws IOException {

      }

      public void write(char[] cbuf, int off, int len) throws IOException {

      }

    };
    tidy.setErrout(new PrintWriter(writer));
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
    if (title == null) {
      if (filename != null) {
        int lastDot = filename.lastIndexOf('.');
        if (lastDot > 0) {
          title = filename.substring(0, lastDot);
        } else {
          title = filename;
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

  private String getTextContent(Element element) {

    StringBuffer buffer = new StringBuffer();
    collectTextContent(element, buffer);
    return buffer.toString();
  }

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

}
