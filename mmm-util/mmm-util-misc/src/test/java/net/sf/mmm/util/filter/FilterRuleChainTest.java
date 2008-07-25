/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.filter;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import net.sf.mmm.util.filter.api.FilterRule;
import net.sf.mmm.util.filter.base.FilterRuleChain;

/**
 * This is the test-case for {@link FilterRuleChain}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class FilterRuleChainTest {

  private void check(FilterRuleChain chain) {

    assertTrue(chain.accept("/doc/manual.pdf"));
    assertFalse(chain.accept("/data/manual.pdf"));
    assertTrue(chain.accept("/data/config.xml"));
    assertTrue(chain.accept("/data/config.xsl"));
    assertFalse(chain.accept("/foo/config.xml"));
    assertFalse(chain.accept("/foo/config.XSL"));
    assertFalse(chain.accept("/foo/bar/file.pdf"));
    assertFalse(chain.accept("/foo/bar/file.xml"));
    assertFalse(chain.accept("/foo/bar/file.XsL"));
  }

  @Test
  public void test() {

    FilterRule<String>[] rules = new FilterRule[] { new PatternFilterRule("^/doc/", true),
        new PatternFilterRule("(?i)\\.pdf$", false), new PatternFilterRule("^/data/", true),
        new PatternFilterRule("(?i)\\.(xml|xsl)$", false), };
    FilterRuleChain chain = new FilterRuleChain(true, rules);
    check(chain);
    assertTrue(chain.accept("/foo/bar/file.bar"));
    FilterRuleChain extendedChain = chain.extend(true, new PatternFilterRule("(?i)\\.bar$", false));
    check(extendedChain);
    assertFalse(extendedChain.accept("/foo/bar/file.bar"));
  }

}
