/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.filter;

import java.io.InputStream;

import org.junit.Test;

import junit.framework.TestCase;

/**
 * This is the {@link TestCase} for {@link FilterRuleChainXmlParser}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class FilterRuleChainXmlParserTest extends TestCase {

  @Test
  public void test() throws Exception {

    FilterRuleChainXmlParser parser = new FilterRuleChainXmlParser();
    String resourceName = FilterRuleChainXmlParserTest.class.getName().replace('.', '/') + ".xml";
    InputStream inStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(
        resourceName);
    FilterRuleChain chain = parser.parseChain(inStream);
    assertTrue(chain.accept("/doc/manual.pdf"));
    assertFalse(chain.accept("/data/manual.pdf"));
    assertTrue(chain.accept("/data/config.xml"));
    assertTrue(chain.accept("/data/config.xsl"));
    assertFalse(chain.accept("/foo/config.xml"));
    assertFalse(chain.accept("/foo/config.XSL"));
    assertFalse(chain.accept("/foo/bar/file.pdf"));
    assertFalse(chain.accept("/foo/bar/file.xml"));
    assertFalse(chain.accept("/foo/bar/file.XsL"));
    assertTrue(chain.accept("/foo/bar/file.bar"));
  }

}
