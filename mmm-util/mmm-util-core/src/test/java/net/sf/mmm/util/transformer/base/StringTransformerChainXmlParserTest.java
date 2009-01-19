/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.transformer.base;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.InputStream;
import java.util.Map;

import javax.xml.parsers.DocumentBuilderFactory;

import junit.framework.TestCase;

import org.junit.Test;
import org.w3c.dom.Element;

import net.sf.mmm.util.resource.base.ClasspathResource;

/**
 * This is the {@link TestCase} for {@link StringTransformerChainXmlParser}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class StringTransformerChainXmlParserTest {

  @Test
  public void test() throws Exception {

    StringTransformerChainXmlParser parser = new StringTransformerChainXmlParser();
    InputStream inStream = new ClasspathResource(StringTransformerChainXmlParserTest.class, ".xml",
        true).openStream();
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
