/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.base;

import java.util.Locale;

import net.sf.mmm.util.nls.api.NlsResourceLocator;

import org.junit.Assert;
import org.junit.Test;

/**
 * This is the test-case for {@link DefaultNlsResourceLocator}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public class NlsResourceLocatorTest {

  /**
   * This method gets the {@link NlsResourceLocator} to test.
   * 
   * @return the {@link NlsResourceLocator}.
   */
  protected NlsResourceLocator getResourceLocator() {

    DefaultNlsResourceLocator locator = new DefaultNlsResourceLocator();
    locator.initialize();
    return locator;
  }

  /**
   * This method performs a single check of the method {@link NlsResourceLocator#getLocaleForInfix(String)}.
   * 
   * @param resourceLocator is the {@link NlsResourceLocator} to test.
   * @param localeInfix is the locale-infix.
   * @param expectedLocale the expected Locale.
   */
  private void checkLocaleForInfix(NlsResourceLocator resourceLocator, String localeInfix, Locale expectedLocale) {

    Locale locale = resourceLocator.getLocaleForInfix(localeInfix);
    Assert.assertEquals("language", expectedLocale.getLanguage(), locale.getLanguage());
    Assert.assertEquals("country", expectedLocale.getCountry(), locale.getCountry());
    Assert.assertEquals("variant", expectedLocale.getVariant(), locale.getVariant());
  }

  /**
   * Test for {@link NlsResourceLocator#getLocaleForInfix(String)}.
   */
  @Test
  public void testLocaleForInfix() {

    NlsResourceLocator resourceLocator = getResourceLocator();
    checkLocaleForInfix(resourceLocator, "_myLanguage", new Locale("myLanguage", "", ""));
    checkLocaleForInfix(resourceLocator, "__myCountry", new Locale("", "myCountry", ""));
    checkLocaleForInfix(resourceLocator, "___myVariant", new Locale("", "", "myVariant"));
    checkLocaleForInfix(resourceLocator, "_myLanguage_myCountry", new Locale("myLanguage", "myCountry", ""));
    checkLocaleForInfix(resourceLocator, "_myLanguage__myVariant", new Locale("myLanguage", "", "myVariant"));
    checkLocaleForInfix(resourceLocator, "__myCountry_myVariant", new Locale("", "myCountry", "myVariant"));
    checkLocaleForInfix(resourceLocator, "_myLanguage_myCountry_myVariant", new Locale("myLanguage", "myCountry",
        "myVariant"));
    checkLocaleForInfix(resourceLocator, "___", new Locale("", "", ""));
    checkLocaleForInfix(resourceLocator, null, AbstractNlsMessage.LOCALE_ROOT);
    checkLocaleForInfix(resourceLocator, "", AbstractNlsMessage.LOCALE_ROOT);
    checkLocaleForInfix(resourceLocator, "myInfo", AbstractNlsMessage.LOCALE_ROOT);
  }

}
