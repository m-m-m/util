/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.base;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import net.sf.mmm.util.NlsBundleUtilCoreRoot;
import net.sf.mmm.util.lang.api.EnumDefinition;
import net.sf.mmm.util.lang.api.EnumProvider;
import net.sf.mmm.util.lang.api.EnumTypeWithCategory;
import net.sf.mmm.util.lang.api.Formatter;
import net.sf.mmm.util.lang.api.attribute.AttributeReadDeprecated;
import net.sf.mmm.util.nls.api.NlsAccess;

/**
 * This is the test-case for {@link SimpleEnumProvider}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.1.0
 */
public class SimpleEnumProviderTest extends Assertions {

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

    assertThat(provider).isNotNull();
    EnumDefinition<Boolean, ?> booleanDefinition = provider.getEnumDefinition(Boolean.class);
    assertThat(booleanDefinition).isNotNull();
    assertThat(booleanDefinition.getEnumType()).isEqualTo(Boolean.class);
    assertThat(booleanDefinition.getCategory()).isNull();
    assertThat(booleanDefinition.getValue()).isEqualTo(Boolean.class.getName());
    Formatter<Boolean> formatter = booleanDefinition.getFormatter();
    assertThat(formatter).isNotNull();
    // we do not know the default locale for sure...
    NlsBundleUtilCoreRoot bundle = NlsAccess.getBundleFactory().createBundle(NlsBundleUtilCoreRoot.class);
    assertThat(formatter.format(Boolean.TRUE)).isEqualTo(bundle.infoYes().getLocalizedMessage());
    assertThat(formatter.format(Boolean.FALSE)).isEqualTo(bundle.infoNo().getLocalizedMessage());
    List<Boolean> enumValues = provider.getEnumValues(booleanDefinition);
    assertThat(enumValues).isNotNull().hasSize(2).containsExactly(Boolean.TRUE, Boolean.FALSE);
  }

  /**
   * Tests {@link #getEnumProvider()} using {@link IncompleteCountry}.
   */
  @Test
  public void testCustomEnum() {

    EnumProvider provider = getEnumProvider();
    assertThat(provider).isNotNull();
    EnumDefinition<IncompleteCountry, Continent> testDefinition = provider
        .getEnumDefinitionWithCategory(IncompleteCountry.class);
    assertThat(testDefinition).isNotNull();
    assertThat(testDefinition.getEnumType()).isEqualTo(IncompleteCountry.class);
    EnumDefinition<?, ?> categoryDefinition = testDefinition.getCategory();
    assertThat(categoryDefinition).isNotNull();
    assertThat(categoryDefinition.getEnumType()).isEqualTo(Continent.class);
    assertThat(testDefinition.getValue()).isEqualTo(IncompleteCountry.class.getName());
    Formatter<IncompleteCountry> formatter = testDefinition.getFormatter();
    assertThat(formatter).isNotNull();
    assertThat(formatter.format(IncompleteCountry.GERMANY)).isEqualTo(IncompleteCountry.GERMANY.toString());

    List<IncompleteCountry> enumValues = provider.getEnumValues(testDefinition);
    assertThat(enumValues).isNotNull();
    List<IncompleteCountry> expected = new ArrayList<>();
    for (IncompleteCountry country : IncompleteCountry.values()) {
      if (!country.isDeprecated()) {
        expected.add(country);
      }
    }
    assertThat(enumValues).isEqualTo(expected);

    enumValues = provider.getEnumValues(testDefinition, Continent.AMERICA);
    assertThat(enumValues).isNotNull().containsExactly(IncompleteCountry.USA, IncompleteCountry.CANADA);

    enumValues = provider.getEnumValues(testDefinition, Continent.AMERICA, Continent.AUSTRALIA);
    assertThat(enumValues).isNotNull().containsExactly(IncompleteCountry.USA, IncompleteCountry.CANADA,
        IncompleteCountry.AUSTRALIA);
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

    @Deprecated YUGOSLAVIA(Continent.EUROPE, "IE"),

    USA(Continent.AMERICA, "US"),

    CANADA(Continent.AMERICA, "CA"),

    CAMEROON(Continent.AFRICA, "CM"),

    AUSTRALIA(Continent.AUSTRALIA, "AU");

    private final String isoCode;

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

    @Override
    public String getValue() {

      return toString();
    }

    @Override
    public Continent getCategory() {

      return this.continent;
    }

    @Override
    public boolean isDeprecated() {

      return (this == YUGOSLAVIA);
    }
  }

}
