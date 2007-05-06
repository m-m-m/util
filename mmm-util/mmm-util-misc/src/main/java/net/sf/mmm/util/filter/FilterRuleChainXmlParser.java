/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.filter;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * This class allows to parse a list of including and excluding regex
 * {@link PatternFilterRule}s from XML and build an according
 * {@link FilterRuleChain}. The rules (include/exclude patterns) are proceeded
 * in the order of their appearance in the list.<br>
 * Here is an example of a configuration (rule list) parsed by this class:
 * 
 * <pre>
 * &lt;chain&gt;
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
 * &lt;!-- 5. rule says that everything else is accepted --&gt;
 * &lt;include pattern=".*"/&gt;
 * &lt;/chain&gt;
 * </pre>
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class FilterRuleChainXmlParser {

  public static final String XML_TAG_INCLUDE = "include";

  public static final String XML_TAG_EXCLUDE = "exclude";

  public static final String XML_ATR_PATTERN = "pattern";

  /**
   * The constructor.
   * 
   */
  public FilterRuleChainXmlParser() {

    super();
  }

  public FilterRuleChain parse(Element xmlElement) {

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
    FilterRuleChain chain = new FilterRuleChain(ruleArray);
    return chain;
  }

}
