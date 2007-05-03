/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.filter;

import org.junit.Test;

import junit.framework.TestCase;

/**
 * This is the {@link TestCase} for {@link FilterRuleChain}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class FilterRuleChainTest extends TestCase {

  @Test
  public void test() {

    FilterRule[] rules = new FilterRule[] {
        new PatternFilterRule("^/doc/", true),
        new PatternFilterRule("(?i)\\.pdf$", false),
        new PatternFilterRule("^/data/", true),
        new PatternFilterRule("(?i)\\.(xml|xsl)$", false),
        new PatternFilterRule(".*", true),
    };
    FilterRuleChain chain = new FilterRuleChain(rules);
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
