/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.transformer;

import java.io.InputStream;
import java.util.Map;

import javax.xml.parsers.DocumentBuilderFactory;

import org.junit.Test;
import org.w3c.dom.Element;

import junit.framework.TestCase;

/**
 * This is the {@link TestCase} for {@link StringTransformerChainXmlParser}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class StringTransformerChainXmlParserTest extends TestCase {

  @Test
  public void test() throws Exception {

    StringTransformerChainXmlParser parser = new StringTransformerChainXmlParser();
    String resourceName = StringTransformerChainXmlParserTest.class.getName().replace('.', '/')
        + ".xml";
    InputStream inStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(
        resourceName);
    Element xmlElement = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(inStream)
        .getDocumentElement();
    Map<String, StringTransformerChain> chainMap = parser.parseChains(xmlElement);
    StringTransformerChain defaultChain = chainMap.get("default-transformer");
    assertNotNull(defaultChain);
    assertEquals("/readme", defaultChain.transform("/readme.txt"));
    assertEquals("/readme.xml", defaultChain.transform("/readme.xml"));
    assertEquals("/trash", defaultChain.transform("/trash"));
    StringTransformerChain extendedChain = chainMap.get("extended-transformer");
    assertNotNull(extendedChain);
    assertEquals("/garbarge/foo", extendedChain.transform("/trash/foo"));
    assertEquals("/dummy/dummy/dummy", extendedChain.transform("/foobar/foo/bar"));
  }

}
