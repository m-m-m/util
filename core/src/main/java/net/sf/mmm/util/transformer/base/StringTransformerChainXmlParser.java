/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.transformer.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import net.sf.mmm.util.pattern.api.PatternCompiler;
import net.sf.mmm.util.pattern.base.RegexPatternCompiler;
import net.sf.mmm.util.transformer.api.StringTransformerRule;
import net.sf.mmm.util.xml.api.DomUtil;
import net.sf.mmm.util.xml.base.DomUtilImpl;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * This class allows to parse a list of {@link StringTransformerRule}s from XML and build an according
 * {@link StringTransformerChain}. The rules are proceeded in the order of their appearance in the list. <br>
 * Here is an example of a configuration (rule list) parsed by this class:
 *
 * <pre>
 * &lt;transformer-chain id="default-transformer"&gt;
 * &lt;regex pattern="\.txt" replacement=""/&gt;
 * &lt;regex pattern="/.foo/" replacement="" replace-all="true" stop-on-match="true"/&gt;
 * &lt;/transformer-chain&gt;
 * </pre>
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.2
 * @deprecated - use JAXB directly:
 *
 *             <pre>
 * JAXBContext context = JAXBContext.newInstance(StringTransformerList.class);
 * 
 * StringTransformerList chains = (StringTransformerList) context.createUnmarshaller().unmarshal(source);
 * </pre>
 */
@Deprecated
public class StringTransformerChainXmlParser {

  /** The name of the XML element for a {@link StringTransformerChain}. */
  public static final String XML_TAG_CHAIN = "transformer-chain";

  /**
   * The name of the XML attribute for the ID of a {@link #XML_TAG_CHAIN chain}.
   */
  public static final String XML_ATR_CHAIN_ID = "id";

  /**
   * The name of the XML attribute for the parent of a {@link #XML_TAG_CHAIN chain}.
   */
  public static final String XML_ATR_CHAIN_PARENT = "parent";

  /** The name of the XML element for a {@link RegexStringTransformerRule}. */
  public static final String XML_TAG_RULE = "regex";

  /**
   * The name of the XML attribute for {@link RegexStringTransformerRule#getPattern()}.
   */
  public static final String XML_ATR_RULE_PATTERN = "pattern";

  /**
   * The name of the XML attribute for {@link RegexStringTransformerRule#getReplacement()}.
   */
  public static final String XML_ATR_RULE_REPLACEMENT = "replacement";

  /**
   * The name of the XML attribute for {@link RegexStringTransformerRule#isReplaceAll()}. Default value is
   * {@code false}.
   */
  public static final String XML_ATR_RULE_REPLACEALL = "replace-all";

  /**
   * The name of the XML attribute for {@link RegexStringTransformerRule#isStopOnMatch()}. Default value is
   * {@code false}.
   */
  public static final String XML_ATR_RULE_STOPONMATCH = "stop-on-match";

  private  final DomUtil domUtil;

  /** @see #StringTransformerChainXmlParser(DomUtil, PatternCompiler) */
  private PatternCompiler patternCompiler;

  /**
   * The constructor.
   */
  public StringTransformerChainXmlParser() {

    this(DomUtilImpl.getInstance());
  }

  /**
   * The constructor.
   *
   * @param domUtil is the {@link DomUtil} to use.
   */
  public StringTransformerChainXmlParser(DomUtil domUtil) {

    this(domUtil, new RegexPatternCompiler());
  }

  /**
   * The constructor.
   *
   * @param domUtil is the {@link DomUtil} to use.
   * @param patternCompiler is the {@link PatternCompiler} to use.
   */
  public StringTransformerChainXmlParser(DomUtil domUtil, PatternCompiler patternCompiler) {

    super();
    this.domUtil = domUtil;
    this.patternCompiler = patternCompiler;
  }

  /**
   * This method parses a {@link StringTransformerRule rule} given by {@code xmlElement}.
   *
   * @see #XML_TAG_RULE
   *
   * @param xmlElement is the XML element with the transformer-rule.
   * @return the parsed rule.
   */
  public StringTransformerRule parseRule(Element xmlElement) {

    if (XML_TAG_RULE.equals(xmlElement.getTagName())) {
      boolean replaceAll = this.domUtil.getAttributeAsBoolean(xmlElement, XML_ATR_RULE_REPLACEALL, false);
      boolean stopOnMatch = this.domUtil.getAttributeAsBoolean(xmlElement, XML_ATR_RULE_STOPONMATCH, false);
      String patternString = xmlElement.getAttribute(XML_ATR_RULE_PATTERN);
      Pattern pattern = this.patternCompiler.compile(patternString);
      String replacement = xmlElement.getAttribute(XML_ATR_RULE_REPLACEMENT);
      return new RegexStringTransformerRule(pattern, replacement, replaceAll, stopOnMatch);
    } else {
      throw new IllegalArgumentException("Unknown tag for rule: " + xmlElement.getTagName());
    }
  }

  /**
   * This method parses a {@link StringTransformerChain chain} given by {@code xmlElement}.
   *
   * @see #XML_TAG_CHAIN
   *
   * @param xmlElement is the XML element containing the transformer-rules (see {@link #XML_TAG_RULE}) as children.
   * @param parent is the parent chain to extend or {@code null} if no rules should be inherited.
   * @return the parsed filter-chain.
   */
  public StringTransformerChain parseChain(Element xmlElement, StringTransformerChain parent) {

    List<StringTransformerRule> rules = new ArrayList<>();
    NodeList childList = xmlElement.getChildNodes();
    for (int childIndex = 0; childIndex < childList.getLength(); childIndex++) {
      Node node = childList.item(childIndex);
      if (node.getNodeType() == Node.ELEMENT_NODE) {
        Element element = (Element) node;
        rules.add(parseRule(element));
      }
    }
    StringTransformerRule[] rulesArray = rules.toArray(new StringTransformerRule[rules.size()]);
    if (parent == null) {
      return new StringTransformerChain(rulesArray);
    } else {
      return parent.extend(rulesArray);
    }
  }

  /**
   * This method parses a map of {@link StringTransformerChain chain}s given by {@code xmlElement}.
   *
   * @param xmlElement is the XML element containing the transformer-chains (see {@link #XML_TAG_CHAIN}) as children and
   *        puts them into a map with the {@link #XML_ATR_CHAIN_ID ID} as key. Unknown child elements or attributes are
   *        simply ignored.
   * @return the map of all parsed chains.
   */
  public Map<String, StringTransformerChain> parseChains(Element xmlElement) {

    Map<String, StringTransformerChain> chainMap = new HashMap<>();
    NodeList childNodes = xmlElement.getChildNodes();
    for (int i = 0; i < childNodes.getLength(); i++) {
      Node child = childNodes.item(i);
      if (child.getNodeType() == Node.ELEMENT_NODE) {
        Element element = (Element) child;
        if (XML_TAG_CHAIN.equals(element.getTagName())) {
          String id = element.getAttribute(XML_ATR_CHAIN_ID);
          StringTransformerChain parent = null;
          if (element.hasAttribute(XML_ATR_CHAIN_PARENT)) {
            String parentId = element.getAttribute(XML_ATR_CHAIN_PARENT);
            parent = chainMap.get(parentId);
            if (parent == null) {
              throw new IllegalArgumentException("Illegal parent (" + parentId + ") in chain (" + id
                  + "): parent chain has to be defined before being referenced!");
            }
          }
          StringTransformerChain chain = parseChain(element, parent);
          StringTransformerChain old = chainMap.put(id, chain);
          if (old != null) {
            throw new IllegalArgumentException("Duplicate chain id: " + id);
          }
        }
      }
    }
    return chainMap;
  }

}
