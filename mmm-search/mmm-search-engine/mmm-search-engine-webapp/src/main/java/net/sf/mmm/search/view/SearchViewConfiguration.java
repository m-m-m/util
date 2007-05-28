/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.view;

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * This class contains the configuration for the search-engine webapplication.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class SearchViewConfiguration {

  /** The name of the XML element for a list of {@link #XML_TAG_SOURCE sources}. */
  public static final String XML_TAG_SOURCES = "sources";

  /**
   * The name of the XML element for a source.
   * 
   * @see net.sf.mmm.search.api.SearchEntry#getSource()
   */
  public static final String XML_TAG_SOURCE = "source";

  /** The name of the XML attribute for the <code>ID</code> of a source. */
  public static final String XML_ATR_SOURCE_ID = "id";

  /**
   * The name of the XML attribute for the <code>display-name</code> of a
   * source.
   */
  public static final String XML_ATR_SOURCE_NAME = "name";

  /**
   * The name of the XML attribute for the <code>url-prefix</code> of a
   * source.
   */
  public static final String XML_ATR_SOURCE_URLPREFIX = "url-prefix";

  /** The name of the XML element for a list of {@link #XML_TAG_FILETYPE types}. */
  public static final String XML_TAG_FILETYPES = "filetypes";

  /**
   * The name of the XML attribute for the <code>default icon</code> used if a
   * filetype is NOT mapped explicitly.
   */
  public static final String XML_ATR_FILETYPES_DEFAULTICON = "default-icon";

  /**
   * The name of the XML element for a filetype.
   * 
   * @see net.sf.mmm.search.api.SearchEntry#getType()
   */
  public static final String XML_TAG_FILETYPE = "type";

  /** The name of the XML attribute for the <code>ID</code> of a filetype. */
  public static final String XML_ATR_FILETYPE_ID = "id";

  /**
   * The name of the XML attribute for the <code>display-name</code> of a
   * filetype.
   */
  public static final String XML_ATR_FILETYPE_NAME = "name";

  /** The name of the XML attribute for the <code>icon</code> of a filetype. */
  public static final String XML_ATR_FILETYPE_ICON = "icon";

  /** @see #getIconName(String) */
  private Map<String, String> type2iconMap;

  /** @see #getTypeByName(String) */
  private Map<String, String> typeName2typeMap;

  /** @see #getSourceByName(String) */
  private Map<String, String> sourceName2sourceMap;

  /** @see #getUrlPrefixBySource(String) */
  private Map<String, String> source2urlMap;

  /** @see #getIconName(String) */
  private String defaultIcon;

  /**
   * The constructor.
   * 
   * @param xmlConfiguration
   */
  public SearchViewConfiguration(Element xmlConfiguration) {

    super();
    this.type2iconMap = new HashMap<String, String>();
    this.typeName2typeMap = new HashMap<String, String>();
    this.sourceName2sourceMap = new HashMap<String, String>();
    NodeList childNodes = xmlConfiguration.getChildNodes();
    for (int i = 0; i < childNodes.getLength(); i++) {
      Node child = childNodes.item(i);
      if (child.getNodeType() == Node.ELEMENT_NODE) {
        Element element = (Element) child;
        if (XML_TAG_SOURCES.equals(element.getTagName())) {
          parseSources(element);
        } else if (XML_TAG_FILETYPES.equals(element.getTagName())) {
          this.defaultIcon = element.getAttribute(XML_ATR_FILETYPES_DEFAULTICON);
          parseFiletypes(element);
        }
      }
    }
  }

  /**
   * This method parses the {@link #XML_TAG_SOURCES sources}.
   * 
   * @param sourcesElement
   *        is the {@link #XML_TAG_SOURCES sources-element}.
   */
  protected void parseSources(Element sourcesElement) {

    NodeList childNodes = sourcesElement.getChildNodes();
    for (int i = 0; i < childNodes.getLength(); i++) {
      Node child = childNodes.item(i);
      if (child.getNodeType() == Node.ELEMENT_NODE) {
        Element element = (Element) child;
        if (XML_TAG_SOURCES.equals(element.getTagName())) {
          String id = element.getAttribute(XML_ATR_SOURCE_ID);
          if (element.hasAttribute(XML_ATR_SOURCE_NAME)) {
            String name = element.getAttribute(XML_ATR_SOURCE_NAME);
            this.sourceName2sourceMap.put(name, id);
          }
          if (element.hasAttribute(XML_ATR_SOURCE_URLPREFIX)) {
            String urlPrefix = element.getAttribute(XML_ATR_SOURCE_URLPREFIX);
            this.source2urlMap.put(id, urlPrefix);
          }
        }
      }
    }
  }

  /**
   * This method parses the {@link #XML_TAG_FILETYPES filetypes}
   * 
   * @param filetypesElement
   *        is the {@link #XML_TAG_FILETYPES filetypes-element}.
   */
  protected void parseFiletypes(Element filetypesElement) {

    NodeList childNodes = filetypesElement.getChildNodes();
    for (int i = 0; i < childNodes.getLength(); i++) {
      Node child = childNodes.item(i);
      if (child.getNodeType() == Node.ELEMENT_NODE) {
        Element element = (Element) child;
        if (XML_TAG_FILETYPE.equals(element.getTagName())) {
          String id = element.getAttribute(XML_ATR_FILETYPE_ID);
          if (element.hasAttribute(XML_ATR_FILETYPE_NAME)) {
            String name = element.getAttribute(XML_ATR_FILETYPE_NAME);
            this.typeName2typeMap.put(name, id);
          }
          if (element.hasAttribute(XML_ATR_FILETYPE_ICON)) {
            String icon = element.getAttribute(XML_ATR_FILETYPE_ICON);
            this.type2iconMap.put(id, icon);
          }
        }
      }
    }
  }

  /**
   * This method gets the
   * {@link net.sf.mmm.search.api.SearchEntry#getSource() source} for the given
   * <code>sourceName</code>.
   * 
   * @param sourceName
   *        is the display-name of the requested source.
   * @return the {@link net.sf.mmm.search.api.SearchEntry#getSource() source}
   *         for the given <code>sourceName</code> or <code>null</code> if
   *         undefined.
   */
  public String getSourceByName(String sourceName) {

    return this.sourceName2sourceMap.get(sourceName);
  }

  /**
   * This method gets the URL-prefix for the given
   * <code>{@link net.sf.mmm.search.api.SearchEntry#getSource() source}</code>
   * 
   * @param source
   *        is the {@link net.sf.mmm.search.api.SearchEntry#getSource() source}
   *        for which the requested URL-prefix is requested.
   * @return the requested URL-prefix or <code>null</code> if undefined.
   */
  public String getUrlPrefixBySource(String source) {

    return this.source2urlMap.get(source);
  }

  /**
   * This method gets the
   * {@link net.sf.mmm.search.api.SearchEntry#getType() type} for the given
   * <code>filetypeName</code>.
   * 
   * @param filetypeName
   *        is the display-name of a filetype.
   * @return the type (typically extension without dot) for the given
   *         <code>filetypeName</code> or <code>null</code> if undefined.
   */
  public String getTypeByName(String filetypeName) {

    return this.typeName2typeMap.get(filetypeName);
  }

  /**
   * This method gets the name of the icon representing the filetype associated
   * with the given {@link net.sf.mmm.search.api.SearchEntry#getType() type}.
   * 
   * @param type
   *        is the filetype for which the icon name is requested (e.g. "doc",
   *        "htm", "html", etc.). The type is typically the file extension (in
   *        {@link String#toLowerCase() lower-case} excluding the dot)
   * @return the name of the icon representing the filetype with the given
   *         <code>extension</code> or a default icon if no according icon
   *         could be found.
   */
  public String getIconName(String type) {

    String iconName = this.type2iconMap.get(type);
    if (iconName == null) {
      iconName = this.defaultIcon;
    }
    return iconName;
  }

}
