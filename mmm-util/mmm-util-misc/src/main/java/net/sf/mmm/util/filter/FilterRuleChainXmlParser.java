/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.filter;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import net.sf.mmm.util.StringUtil;

/**
 * This class allows to parse a list of including and excluding regex
 * {@link PatternFilterRule}s from XML and build an according
 * {@link FilterRuleChain}. The rules (include/exclude patterns) are proceeded
 * in the order of their appearance in the list.<br>
 * Here is an example of a configuration (rule list) parsed by this class:
 * 
 * <pre>
 * &lt;chain default"true"&gt;
 * &lt;!-- 1. rule says that all strings that start (^) with "/doc/" will be accepted --&gt;
 * &lt;include pattern="^/doc/"/&gt;
 * &lt;!-- 2. rule says that all strings that end ($) with ".pdf" ignoring the case  
 * of the characters will be rejected --&gt;
 * &lt;exclude pattern="(?i)\.pdf$"/&gt;
 * &lt;!-- 3. rule says that all string that start with "/data/" will be accepted --&gt;
 * &lt;include pattern="^/data/"/&gt;
 * &lt;!-- 4. rule says that all string that end ($) with ".xml" or ".xsl" ignoring 
 * the case (?i) of the characters will be rejected: --&gt;
 * &lt;exclude pattern="(?i)\.(xml|xsl)$"/&gt;
 * &lt;/chain&gt;
 * </pre>
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class FilterRuleChainXmlParser {

  /** the name of the XML element for an including rule */
  public static final String XML_TAG_INCLUDE = "include";

  /** the name of the XML element for an excluding rule */
  public static final String XML_TAG_EXCLUDE = "exclude";

  /**
   * the name of the XML attribute for the pattern of a (include or exclude)
   * rule
   */
  public static final String XML_ATR_PATTERN = "pattern";

  /** the name of the XML attribute for the default-result */
  public static final String XML_ATR_DEFAULT = "default";

  /**
   * The constructor.
   * 
   */
  public FilterRuleChainXmlParser() {

    super();
  }

  /**
   * This method parses the chain from the given <code>inStream</code>. The
   * XML contained in <code>inStream</code> needs to contain the chain rules
   * as child-nodes of the {@link Document#getDocumentElement() root-element}.
   * The name of the root-element is ignored (use e.g. "chain").
   * 
   * @param inStream
   *        is the stream containing the XML to parse. It will be closed by this
   *        method (on success as well as in an exceptional state).
   * @return the parsed filter rule.
   * @throws IOException
   *         if an input/output error occurred.
   * @throws SAXException
   *         if the <code>inStream</code> contains invalid XML.
   */
  public FilterRuleChain parse(InputStream inStream) throws IOException, SAXException {

    try {
      Document xmlDoc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(inStream);
      return parse(xmlDoc.getDocumentElement());
    } catch (ParserConfigurationException e) {
      throw new IllegalStateException("XML configuration error!", e);
    } finally {
      inStream.close();
    }
  }

  /**
   * This method parses the chain given as XML via <code>xmlElement</code>.
   * 
   * @param xmlElement
   *        is the XML element containing the filter-rules (see
   *        {@link #XML_TAG_INCLUDE} and {@link #XML_TAG_EXCLUDE}) as children.
   *        Unknown child elements or attributes are simply ignored.
   * @return the parsed filter-chain.
   */
  public FilterRuleChain parse(Element xmlElement) {

    boolean defaultResult = true;
    if (xmlElement.hasAttribute(XML_ATR_DEFAULT)) {
      String defaultAttribute = xmlElement.getAttribute(XML_ATR_DEFAULT);
      Boolean def = StringUtil.parseBoolean(defaultAttribute);
      if (def == null) {
        throw new IllegalArgumentException("No boolean value: " + defaultAttribute);
      }
      defaultResult = def.booleanValue();
    }
    List<FilterRule> rules = new ArrayList<FilterRule>();
    NodeList childList = xmlElement.getChildNodes();
    for (int childIndex = 0; childIndex < childList.getLength(); childIndex++) {
      Node node = childList.item(childIndex);
      if (node.getNodeType() == Node.ELEMENT_NODE) {
        Element child = (Element) node;
        Boolean include = null;
        if (child.getTagName().equals(XML_TAG_INCLUDE)) {
          include = Boolean.TRUE;
        } else if (child.getTagName().equals(XML_TAG_EXCLUDE)) {
          include = Boolean.FALSE;
        } else {
          // ignore unknown tag...
        }
        if (include != null) {
          FilterRule rule = null;
          if (child.hasAttribute(XML_ATR_PATTERN)) {
            String pattern = child.getAttribute(XML_ATR_PATTERN);
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
    FilterRule[] ruleArray = rules.toArray(new FilterRule[rules.size()]);
    FilterRuleChain chain = new FilterRuleChain(defaultResult, ruleArray);
    return chain;
  }

}
