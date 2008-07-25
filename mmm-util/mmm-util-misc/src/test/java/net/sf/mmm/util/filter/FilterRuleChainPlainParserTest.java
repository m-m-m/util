/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.filter;

import java.io.InputStream;
import java.io.InputStreamReader;

import org.junit.Test;

import net.sf.mmm.util.filter.base.FilterRuleChain;

import static org.junit.Assert.*;

/**
 * This is the test-case for {@link FilterRuleChainPlainParser}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class FilterRuleChainPlainParserTest {

  @Test
  public void test() throws Exception {

    FilterRuleChainPlainParser parser = new FilterRuleChainPlainParser();
    String resourceName = FilterRuleChainPlainParserTest.class.getName().replace('.', '/') + ".txt";
    InputStream inStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(
        resourceName);
    FilterRuleChain chain = parser.parse(new InputStreamReader(inStream, "UTF-8"), true);
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
