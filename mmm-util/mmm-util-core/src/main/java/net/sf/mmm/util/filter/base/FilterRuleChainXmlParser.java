/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.filter.base;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import net.sf.mmm.util.filter.api.FilterRule;
import net.sf.mmm.util.xml.api.DomUtil;
import net.sf.mmm.util.xml.base.DomUtilImpl;

/**
 * This class allows to parse a list of including and excluding regex
 * {@link PatternFilterRule}s from XML and build an according
 * {@link FilterRuleChain}. The rules (include/exclude patterns) are proceeded
 * in the order of their appearance in the list.<br>
 * Here is an example of a configuration (rule list) parsed by this class:
 * 
 * <pre>
 * &lt;filter-chain id="default-filter" default-result="true"&gt;
 * &lt;!-- 1. rule says that all strings that start (^) with "/doc/" will be accepted --&gt;
 * &lt;include pattern="^/doc/"/&gt;
 * &lt;!-- 2. rule says that all strings that end ($) with ".pdf" ignoring the case  
 * of the characters will be rejected --&gt;
 * &lt;exclude pattern="(?i)\.pdf$"/&gt;
 * &lt;!-- 3. rule says that all strings that start with "/data/" will be accepted --&gt;
 * &lt;include pattern="^/data/"/&gt;
 * &lt;!-- 4. rule says that all string that end ($) with ".xml" or ".xsl" ignoring 
 * the case (?i) of the characters will be rejected: --&gt;
 * &lt;exclude pattern="(?i)\.(xml|xsl)$"/&gt;
 * &lt;/filter-chain&gt;
 * </pre>
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.2
 */
public class FilterRuleChainXmlParser {

  /** The name of the XML element for a {@link FilterRuleChain}. */
  public static final String XML_TAG_CHAIN = "filter-chain";

  /**
   * The name of the XML attribute for the ID of a {@link #XML_TAG_CHAIN chain}.
   */
  public static final String XML_ATR_CHAIN_ID = "id";

  /**
   * The name of the XML attribute for the parent of a {@link #XML_TAG_CHAIN
   * chain}.
   */
  public static final String XML_ATR_CHAIN_PARENT = "parent";

  /** The name of the XML attribute for the default-result. */
  public static final String XML_ATR_CHAIN_DEFAULT = "default-result";

  /** The name of the XML element for an including rule. */
  public static final String XML_TAG_RULE_INCLUDE = "include";

  /** The name of the XML element for an excluding rule. */
  public static final String XML_TAG_RULE_EXCLUDE = "exclude";

  /**
   * The name of the XML attribute for the pattern of a (include or exclude)
   * rule.
   */
  public static final String XML_ATR_RULE_PATTERN = "pattern";

  /** @see #FilterRuleChainXmlParser(DomUtil) */
  private final DomUtil domUtil;

  /**
   * The constructor.
   */
  public FilterRuleChainXmlParser() {

    this(DomUtilImpl.getInstance());
  }

  /**
   * The constructor.
   * 
   * @param domUtil is the {@link DomUtil} to use.
   */
  public FilterRuleChainXmlParser(DomUtil domUtil) {

    super();
    this.domUtil = domUtil;
  }

  /**
   * This method parses the chain from the given <code>inStream</code>. The XML
   * contained in <code>inStream</code> needs to contain the chain rules as
   * child-nodes of the {@link Document#getDocumentElement() root-element}. The
   * name of the root-element is ignored (use e.g. "chain").
   * 
   * @param inStream is the stream containing the XML to parse. It will be
   *        closed by this method (on success as well as in an exceptional
   *        state).
   * @return the parsed filter rule.
   * @throws IOException if an input/output error occurred.
   * @throws SAXException if the <code>inStream</code> contains invalid XML.
   */
  public FilterRuleChain<String> parseChain(InputStream inStream) throws IOException, SAXException {

    try {
      Document xmlDoc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(inStream);
      return parseChain(xmlDoc.getDocumentElement());
    } catch (ParserConfigurationException e) {
      throw new IllegalStateException("XML configuration error!", e);
    } finally {
      inStream.close();
    }
  }

  /**
   * This method parses a map of {@link FilterRuleChain chain}s given by
   * <code>xmlElement</code>.
   * 
   * @param xmlElement is the XML element containing the filter-chains (see
   *        {@link #XML_TAG_CHAIN}) as children and puts them into a map with
   *        the {@link #XML_ATR_CHAIN_ID ID} as key. Unknown child elements or
   *        attributes are simply ignored.
   * @return the map of all parsed chains.
   */
  public Map<String, FilterRuleChain<String>> parseChains(Element xmlElement) {

    Map<String, FilterRuleChain<String>> chainMap = new HashMap<String, FilterRuleChain<String>>();
    NodeList childNodes = xmlElement.getChildNodes();
    for (int i = 0; i < childNodes.getLength(); i++) {
      Node child = childNodes.item(i);
      if (child.getNodeType() == Node.ELEMENT_NODE) {
        Element element = (Element) child;
        if (XML_TAG_CHAIN.equals(element.getTagName())) {
          String id = element.getAttribute(XML_ATR_CHAIN_ID);
          FilterRuleChain<String> parent = null;
          if (element.hasAttribute(XML_ATR_CHAIN_PARENT)) {
            String parentId = element.getAttribute(XML_ATR_CHAIN_PARENT);
            parent = chainMap.get(parentId);
            if (parent == null) {
              throw new IllegalArgumentException("Illegal parent (" + parentId + ") in chain ("
                  + id + "): parent chain has to be defined before being referenced!");
            }
          }
          FilterRuleChain<String> chain = parseChain(element, parent);
          FilterRuleChain<String> old = chainMap.put(id, chain);
          if (old != null) {
            throw new IllegalArgumentException("Duplicate chain id: " + id);
          }
        }
      }
    }
    return chainMap;
  }

  /**
   * This method parses a {@link FilterRuleChain chain} given by
   * <code>xmlElement</code>.
   * 
   * @see #XML_TAG_CHAIN
   * 
   * @param xmlElement is the XML element containing the filter-rules (see
   *        {@link #XML_TAG_RULE_INCLUDE} and {@link #XML_TAG_RULE_EXCLUDE}) as
   *        children.
   * @return the parsed filter-chain.
   */
  public FilterRuleChain<String> parseChain(Element xmlElement) {

    return parseChain(xmlElement, null);
  }

  /**
   * This method parses a {@link FilterRuleChain chain} given as XML via
   * <code>xmlElement</code>.
   * 
   * @param xmlElement is the XML element containing the filter-rules (see
   *        {@link #XML_TAG_RULE_INCLUDE} and {@link #XML_TAG_RULE_EXCLUDE}) as
   *        children. Unknown child elements or attributes are simply ignored.
   * @param parent is the parent chain that is to be extended by the chain to
   *        parse.
   * @return the parsed filter-chain.
   */
  @SuppressWarnings("unchecked")
  public FilterRuleChain<String> parseChain(Element xmlElement, FilterRuleChain<String> parent) {

    boolean defaultResult = this.domUtil.getAttributeAsBoolean(xmlElement, XML_ATR_CHAIN_DEFAULT,
        true);
    List<FilterRule<String>> rules = new ArrayList<FilterRule<String>>();
    NodeList childList = xmlElement.getChildNodes();
    for (int childIndex = 0; childIndex < childList.getLength(); childIndex++) {
      Node node = childList.item(childIndex);
      if (node.getNodeType() == Node.ELEMENT_NODE) {
        Element child = (Element) node;
        Boolean include = null;
        if (child.getTagName().equals(XML_TAG_RULE_INCLUDE)) {
          include = Boolean.TRUE;
        } else if (child.getTagName().equals(XML_TAG_RULE_EXCLUDE)) {
          include = Boolean.FALSE;
        } else {
          // ignore unknown tag...
        }
        if (include != null) {
          FilterRule<String> rule = null;
          if (child.hasAttribute(XML_ATR_RULE_PATTERN)) {
            String pattern = child.getAttribute(XML_ATR_RULE_PATTERN);
            rule = new PatternFilterRule(pattern, include.booleanValue());
          }
          if (rule != null) {
            rules.add(rule);
          }
        }
      }
    }
    if (rules.size() == 0) {
      throw new IllegalArgumentException("No rule found in element '" + xmlElement.getTagName()
          + "'!");
    }
    FilterRule<String>[] ruleArray = rules.toArray(new FilterRule[rules.size()]);
    FilterRuleChain<String> chain;
    if (parent == null) {
      chain = new FilterRuleChain<String>(defaultResult, ruleArray);
    } else {
      chain = parent.extend(defaultResult, ruleArray);
    }
    return chain;
  }

}
