/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.text.base;

import java.util.Locale;

import junit.framework.Assert;
import net.sf.mmm.util.text.api.Hyphenator;
import net.sf.mmm.util.text.api.HyphenatorBuilder;

import org.junit.Ignore;
import org.junit.Test;

/**
 * This is the test-case for {@link HyphenatorBuilderImpl}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public class HyphenatorBuilderImplTest extends AbstractHyphenatorTest {

  /**
   * This method gets the {@link HyphenatorBuilder} instance to test.
   * 
   * @return the {@link HyphenatorBuilder}.
   */
  protected HyphenatorBuilder getHyphenatorBuilder() {

    return HyphenatorBuilderImpl.getInstance();
  }

  /**
   * This method gets the hyphenator for the given {@link Locale}.
   * 
   * @see HyphenatorBuilder#getHyphenator(Locale)
   * 
   * @param locale is the {@link Locale}.
   * @return the requested {@link Hyphenator}.
   */
  protected Hyphenator getHyphenator(Locale locale) {

    Hyphenator hyphenator = getHyphenatorBuilder().getHyphenator(locale);
    Assert.assertNotNull(hyphenator);
    // load again to test caching...
    hyphenator = getHyphenatorBuilder().getHyphenator(locale);
    Assert.assertNotNull(hyphenator);
    return hyphenator;
  }

  /**
   * This method tests the {@link Hyphenator} for {@link Locale#GERMAN} and {@link Locale#GERMANY}.
   */
  @Test
  @Ignore("TODO: clarify license of de rules")
  public void testDe() {

    // hyphenation for "de" represents "de_DE"
    Hyphenator hyphenatorGermany = getHyphenator(Locale.GERMANY);
    Locale locale = Locale.GERMAN;
    Hyphenator hyphenator = getHyphenator(locale);
    Assert.assertEquals(locale, hyphenator.getLocale());
    // test proper caching
    Assert.assertSame(hyphenatorGermany, hyphenator);
    checkHyphenations(hyphenator, "Zer-streu-ung");
  }

  /**
   * This method tests the {@link Hyphenator} for {@link Locale#US}.
   */
  @Test
  public void testEnUs() {

    Hyphenator hyphenator = getHyphenator(Locale.US);
    Assert.assertEquals(Locale.ROOT, hyphenator.getLocale());

    checkHyphenations(hyphenator, "word", "this", "a",
        "con-struc-tion",
        // "spec-tro-elec-tro-chem-is-try",
        "spec-tro-elec-tro-chem-istry", "hy-phen-ation", "hyp-not-ic", "care-tak-er", "as-pi-rin", "as-pir-ing",
        "in-de-pen-dent", "tri-bune", "pro-gress", "even-ing", "pe-ri-od-ic", "as-so-ciate", "squirmed", "bio-rhythm",
        "ho-mol-o-gous", "be-tray-al", "de-spair", "per-fect-ly");
    // , "Af-ghan-i-stan"
  }
}
