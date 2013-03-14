/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.base;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.sf.mmm.util.lang.api.EnumDefinition;
import net.sf.mmm.util.lang.api.EnumProvider;
import net.sf.mmm.util.lang.api.EnumTypeWithCategory;
import net.sf.mmm.util.lang.api.Formatter;
import net.sf.mmm.util.lang.api.attribute.AttributeReadDeprecated;

import org.junit.Assert;
import org.junit.Test;

/**
 * This is the test-case for {@link SimpleEnumProvider}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.1.0
 */
public class SimpleEnumProviderTest extends Assert {

  /**
   * @return the {@link EnumProvider} instance to test.
   */
  protected EnumProvider getEnumProvider() {

    SimpleEnumProvider provider = new SimpleEnumProvider();
    provider.initialize();
    return provider;
  }

  /**
   * Tests {@link #getEnumProvider()} using {@link Boolean}.
   */
  @Test
  public void testBoolean() {

    EnumProvider provider = getEnumProvider();

    assertNotNull(provider);
    EnumDefinition<Boolean, ?> booleanDefinition = provider.getEnumDefinition(Boolean.class);
    assertNotNull(booleanDefinition);
    assertEquals(Boolean.class, booleanDefinition.getEnumType());
    assertNull(booleanDefinition.getCategory());
    assertEquals(Boolean.class.getName(), booleanDefinition.getValue());
    Formatter<Boolean> formatter = booleanDefinition.getFormatter();
    assertNotNull(formatter);
    // might break with different locale if l10n stuff is in classpath
    // in that case we would need to use the NlsBundleUtilCoreRoot
    assertEquals("Yes", formatter.format(Boolean.TRUE));
    assertEquals("No", formatter.format(Boolean.FALSE));
    List<Boolean> enumValues = provider.getEnumValues(booleanDefinition);
    assertNotNull(enumValues);
    assertEquals(2, enumValues.size());
    assertEquals(Arrays.asList(Boolean.TRUE, Boolean.FALSE), enumValues);
  }

  /**
   * Tests {@link #getEnumProvider()} using {@link IncompleteCountry}.
   */
  @Test
  public void testCustomEnum() {

    EnumProvider provider = getEnumProvider();
    assertNotNull(provider);
    EnumDefinition<IncompleteCountry, Continent> testDefinition = provider
        .getEnumDefinitionWithCategory(IncompleteCountry.class);
    assertNotNull(testDefinition);
    assertEquals(IncompleteCountry.class, testDefinition.getEnumType());
    EnumDefinition<?, ?> categoryDefinition = testDefinition.getCategory();
    assertNotNull(categoryDefinition);
    assertEquals(Continent.class, categoryDefinition.getEnumType());
    assertEquals(IncompleteCountry.class.getName(), testDefinition.getValue());
    Formatter<IncompleteCountry> formatter = testDefinition.getFormatter();
    assertNotNull(formatter);
    assertEquals(IncompleteCountry.GERMANY.toString(), formatter.format(IncompleteCountry.GERMANY));

    List<IncompleteCountry> enumValues = provider.getEnumValues(testDefinition);
    assertNotNull(enumValues);
    List<IncompleteCountry> expected = new ArrayList<IncompleteCountry>();
    for (IncompleteCountry country : IncompleteCountry.values()) {
      if (!country.isDeprecated()) {
        expected.add(country);
      }
    }
    assertEquals(expected, enumValues);

    enumValues = provider.getEnumValues(testDefinition, Continent.AMERICA);
    assertNotNull(enumValues);
    assertEquals(Arrays.asList(IncompleteCountry.USA, IncompleteCountry.CANADA), enumValues);

    enumValues = provider.getEnumValues(testDefinition, Continent.AMERICA, Continent.AUSTRALIA);
    assertNotNull(enumValues);
    assertEquals(Arrays.asList(IncompleteCountry.USA, IncompleteCountry.CANADA, IncompleteCountry.AUSTRALIA),
        enumValues);
  }

  @SuppressWarnings("all")
  public static enum Continent {

    EUROPE,

    ASIA,

    AMERICA,

    AFRICA,

    AUSTRALIA

  }

  // totally incomplete - just for testing...
  @SuppressWarnings("all")
  public static enum IncompleteCountry implements EnumTypeWithCategory<String, Continent>, AttributeReadDeprecated {

    GERMANY(Continent.EUROPE, "DE"),

    FRANCE(Continent.EUROPE, "FR"),

    @Deprecated
    YUGOSLAVIA(Continent.EUROPE, "IE"),

    USA(Continent.AMERICA, "US"),

    CANADA(Continent.AMERICA, "CA"),

    CAMEROON(Continent.AFRICA, "CM"),

    AUSTRALIA(Continent.AUSTRALIA, "AU");

    /** @see #getValue() */
    private final String isoCode;

    /** @see #getCategory() */
    private final Continent continent;

    /**
     * The constructor.
     * 
     * @param continent - see {@link #getCategory()}.
     * @param isoCode - see {@link #getValue()}.
     */
    IncompleteCountry(Continent continent, String isoCode) {

      this.continent = continent;
      this.isoCode = isoCode;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getValue() {

      return toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getTitle() {

      return toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Continent getCategory() {

      return this.continent;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isDeprecated() {

      return (this == YUGOSLAVIA);
    }
  }

}
