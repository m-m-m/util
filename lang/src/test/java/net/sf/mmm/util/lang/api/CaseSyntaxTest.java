/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.api;

import static net.sf.mmm.util.lang.api.CaseConversion.LOWER_CASE;
import static net.sf.mmm.util.lang.api.CaseConversion.ORIGINAL_CASE;
import static net.sf.mmm.util.lang.api.CaseConversion.UPPER_CASE;

import java.util.Locale;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import net.sf.mmm.test.ObjectHelper;
import net.sf.mmm.test.TestValues;

/**
 * Test of {@link CaseSyntax}.
 */
public class CaseSyntaxTest extends Assertions {

  /**
   * Test of {@link CaseSyntax#of(Character, CaseConversion, CaseConversion, CaseConversion)} with standardized
   * {@link CaseSyntax} constants.
   */
  @Test
  public void testOfStandardized() {

    Character separator = null;
    assertThat(CaseSyntax.of(separator, LOWER_CASE)).isSameAs(CaseSyntax.LOWERCASE);
    assertThat(CaseSyntax.of(separator, UPPER_CASE)).isSameAs(CaseSyntax.UPPERCASE);
    assertThat(CaseSyntax.of(separator, UPPER_CASE, UPPER_CASE, LOWER_CASE)).isSameAs(CaseSyntax.PASCAL_CASE);
    assertThat(CaseSyntax.of(separator, LOWER_CASE, UPPER_CASE, LOWER_CASE)).isSameAs(CaseSyntax.CAML_CASE);

    assertThat(CaseSyntax.of(separator, UPPER_CASE, ORIGINAL_CASE, ORIGINAL_CASE)).isSameAs(CaseSyntax.CAPITALIZED);
    assertThat(CaseSyntax.of(separator, UPPER_CASE, LOWER_CASE)).isSameAs(CaseSyntax.CAPITALIZED_LOWER);
    assertThat(CaseSyntax.of(separator, LOWER_CASE, UPPER_CASE, UPPER_CASE)).isSameAs(CaseSyntax.UNCAPITALIZED_UPPER);

    separator = Character.valueOf('-');
    assertThat(CaseSyntax.of(separator, LOWER_CASE)).isSameAs(CaseSyntax.TRAIN_CASE);
    assertThat(CaseSyntax.of(separator, UPPER_CASE)).isSameAs(CaseSyntax.UPPER_TRAIN_CASE);
    assertThat(CaseSyntax.of(separator, UPPER_CASE, UPPER_CASE)).isSameAs(CaseSyntax.PASCAL_TRAIN_CASE);
    assertThat(CaseSyntax.of(separator, LOWER_CASE, UPPER_CASE)).isSameAs(CaseSyntax.CAML_TRAIN_CASE);

    separator = Character.valueOf('_');
    assertThat(CaseSyntax.of(separator, LOWER_CASE)).isSameAs(CaseSyntax.LOWER_SNAKE_CASE);
    assertThat(CaseSyntax.of(separator, UPPER_CASE)).isSameAs(CaseSyntax.UPPER_SNAKE_CASE);
    assertThat(CaseSyntax.of(separator, UPPER_CASE, UPPER_CASE)).isSameAs(CaseSyntax.PASCAL_SNAKE_CASE);
    assertThat(CaseSyntax.of(separator, LOWER_CASE, UPPER_CASE)).isSameAs(CaseSyntax.CAML_SNAKE_CASE);

    separator = Character.valueOf(' ');
    assertThat(CaseSyntax.of(separator, LOWER_CASE)).isSameAs(CaseSyntax.LOWER_SPACE_CASE);
    assertThat(CaseSyntax.of(separator, UPPER_CASE)).isSameAs(CaseSyntax.UPPER_SPACE_CASE);
    assertThat(CaseSyntax.of(separator, UPPER_CASE, UPPER_CASE)).isSameAs(CaseSyntax.PASCAL_SPACE_CASE);
    assertThat(CaseSyntax.of(separator, LOWER_CASE, UPPER_CASE)).isSameAs(CaseSyntax.CAML_SPACE_CASE);
  }

  /**
   * Test of {@link CaseSyntax#of(Character, CaseConversion, CaseConversion, CaseConversion)} with customized variants.
   */
  @Test
  public void testOfCustomized() {

    Character separator = Character.valueOf('~');
    assertThat(CaseSyntax.of(separator, UPPER_CASE).toString()).isEqualTo("CUSTOM~CASE");
    assertThat(CaseSyntax.of(separator, LOWER_CASE).toString()).isEqualTo("custom~case");
    assertThat(CaseSyntax.of(separator, UPPER_CASE, UPPER_CASE, LOWER_CASE).toString()).isEqualTo("Custom~Case");
    assertThat(CaseSyntax.of(separator, LOWER_CASE, UPPER_CASE, LOWER_CASE).toString()).isEqualTo("custom~Case");
    assertThat(CaseSyntax.of(separator, ORIGINAL_CASE, UPPER_CASE, ORIGINAL_CASE).toString()).isEqualTo("$$Custom~Case");
  }

  /**
   * Test of {@link CaseSyntax#ofExample(String, boolean)} with {@code standardize} parameter set to {@code true}.
   */
  @Test
  public void testOfExampleStandardized() {

    verifyOfExampleStandardized("variablename", CaseSyntax.LOWERCASE);
    verifyOfExampleStandardized("VARIABLENAME", CaseSyntax.UPPERCASE);
    verifyOfExampleStandardized("VariableName", CaseSyntax.PASCAL_CASE);
    verifyOfExampleStandardized("variableName", CaseSyntax.CAML_CASE);
    verifyOfExampleStandardized("variable-name", CaseSyntax.TRAIN_CASE);
    verifyOfExampleStandardized("VARIABLE-NAME", CaseSyntax.UPPER_TRAIN_CASE);
    verifyOfExampleStandardized("variable-Name", CaseSyntax.CAML_TRAIN_CASE);
    verifyOfExampleStandardized("Variable-Name", CaseSyntax.PASCAL_TRAIN_CASE);
    verifyOfExampleStandardized("VARIABLE_NAME", CaseSyntax.UPPER_SNAKE_CASE);
    verifyOfExampleStandardized("variable_name", CaseSyntax.LOWER_SNAKE_CASE);
    verifyOfExampleStandardized("variable name", CaseSyntax.LOWER_SPACE_CASE);
    verifyOfExampleStandardized("VARIABLE NAME", CaseSyntax.UPPER_SPACE_CASE);
    verifyOfExampleStandardized("Variable Name", CaseSyntax.PASCAL_SPACE_CASE);
    verifyOfExampleStandardized("variable Name", CaseSyntax.CAML_SPACE_CASE);
    verifyOfExampleStandardized("Variablename", CaseSyntax.CAPITALIZED_LOWER);
    // edge cases...
    verifyOfExampleStandardized("V$$ariablename", CaseSyntax.CAPITALIZED);
    verifyOfExampleStandardized("vARIABLENAME", CaseSyntax.UNCAPITALIZED_UPPER);
  }

  private void verifyOfExampleStandardized(String example, CaseSyntax constant) {

    boolean standardize = true;
    assertThat(CaseSyntax.ofExample(example, standardize)).isSameAs(constant);
    assertThat(CaseSyntax.ofExample(constant.toString(), standardize)).isSameAs(constant);
    standardize = false;
    assertThat(CaseSyntax.ofExample(example, standardize).toString()).isSameAs(example);
    if (constant.getOtherCase() != CaseConversion.ORIGINAL_CASE) {
      assertThat(constant.convert("VariableName")).isEqualTo(example.replace("" + CaseConversion.EXAMPLE_CHAR_FOR_ORIGINAL_CASE, ""));
    }
  }

  /**
   * Test of {@link CaseSyntax#ofExample(String, boolean)} with examples of minimal length.
   */
  @Test
  public void testOfExampleMinimal() {

    assertThat(CaseSyntax.ofExample("abc", true)).isSameAs(CaseSyntax.LOWERCASE);
    assertThat(CaseSyntax.ofExample("ABC", true)).isSameAs(CaseSyntax.UPPERCASE);
    assertThat(CaseSyntax.ofExample("AbC", true)).isSameAs(CaseSyntax.PASCAL_CASE);
    assertThat(CaseSyntax.ofExample("abC", true)).isSameAs(CaseSyntax.CAML_CASE);
    assertThat(CaseSyntax.ofExample("ab-c", true)).isSameAs(CaseSyntax.TRAIN_CASE);
    assertThat(CaseSyntax.ofExample("a-bc", true)).isSameAs(CaseSyntax.TRAIN_CASE);
    assertThat(CaseSyntax.ofExample("$$$", true)).isSameAs(CaseSyntax.UNMODIFIED);
    assertThat(CaseSyntax.ofExample("$$A", true)).isEqualTo(CaseSyntax.of(null, ORIGINAL_CASE, UPPER_CASE, ORIGINAL_CASE));
    assertThat(CaseSyntax.ofExample("$$a", true)).isEqualTo(CaseSyntax.of(null, ORIGINAL_CASE, LOWER_CASE, ORIGINAL_CASE));
    assertThat(CaseSyntax.ofExample("$A$", true)).isEqualTo(CaseSyntax.of(null, ORIGINAL_CASE, ORIGINAL_CASE, UPPER_CASE));
    assertThat(CaseSyntax.ofExample("$a$", true)).isEqualTo(CaseSyntax.of(null, ORIGINAL_CASE, ORIGINAL_CASE, LOWER_CASE));
    assertThat(CaseSyntax.ofExample("A$$", true)).isSameAs(CaseSyntax.CAPITALIZED);
    assertThat(CaseSyntax.ofExample("a$$", true)).isEqualTo(CaseSyntax.of(null, LOWER_CASE, ORIGINAL_CASE, ORIGINAL_CASE));
  }

  /**
   * Test of {@link CaseSyntax#convert(String)} with some specific cases not covered else where.
   */
  @Test
  public void testConvert() {

    assertThat(CaseSyntax.PASCAL_CASE.convert(null)).isNull();
    assertThat(CaseSyntax.PASCAL_CASE.convert("")).isEmpty();
    assertThat(CaseSyntax.PASCAL_CASE.convert(" ")).isEmpty();
    assertThat(CaseSyntax.PASCAL_CASE.convert("a0b-cd")).isEqualTo("A0bCd");
    assertThat(CaseSyntax.PASCAL_CASE.convert("aBC DE")).isEqualTo("AbcDe");
    assertThat(CaseSyntax.CAPITALIZED.convert("aBc-DEfg")).isEqualTo("ABcDEfg");
    assertThat(CaseSyntax.TRAIN_CASE.convert(" ")).isEqualTo("-");
    assertThat(CaseSyntax.TRAIN_CASE.convert("._")).isEqualTo("-");
    assertThat(CaseSyntax.PASCAL_CASE.convert("InConSISTENTCase")).isEqualTo("InConSistentcase");
    assertThat(CaseSyntax.CAML_SNAKE_CASE.convert("inconSistentcASE")).isEqualTo("incon_Sistentc_Ase");
    assertThat(CaseSyntax.of(CaseSyntax.KEEP_SPECIAL_CHARS, UPPER_CASE).convert("ab-_c. d")).isEqualTo("AB-_C. D");
    assertThat(CaseSyntax.CAML_CASE.convert(TestValues.THAI_SENTENCE)).isEqualTo("เดกทมปญหาทางการเรยนรบางคนสามารถเรยนรวมกบเดกปกตได");
  }

  /**
   * Test of {@link CaseSyntax#ofExample(String, boolean)} in combination with {@link CaseSyntax#convert(String)}.
   */
  @Test
  public void testCombined() {

    String[] VARIANTS = { "MyVariableName", "myVariableName", "my-variable-name", "my variable name", "my variable-name", "my.variable_name",
        "my~#,variable§$name" };
    verifyCombined("snake-case", "my-variable-name", VARIANTS);
    verifyCombined("PascalCase", "MyVariableName", VARIANTS);
    verifyCombined("camlCase", "myVariableName", VARIANTS);
    verifyCombined("Pascal Space Case", "My Variable Name", VARIANTS);
    verifyCombined("caml Space Case", "my Variable Name", VARIANTS);
    verifyCombined("UPPER SPACE CASE", "MY VARIABLE NAME", VARIANTS);
    verifyCombined("lower space case", "my variable name", VARIANTS);
    verifyCombined("Pascal.Dot", "My.Variable.Name", VARIANTS);
    verifyCombined("caml.Dot", "my.Variable.Name", VARIANTS);

    // edge cases
    assertThat(CaseSyntax.ofExample("_package", false).convert("mY.PacKagE_Name.With.Some_seGmentS")).isEqualTo("my.package_name.with.some_segments");
    assertThat(CaseSyntax.ofExample("UPPER", false).convert("Ruß")).isEqualTo("RUSS");
    assertThat(CaseSyntax.ofExample("PascalCase", false).convert("ßv")).isEqualTo("Ssv");
    CaseSyntax syntax = CaseSyntax.ofExample("Capitalizedlower", false);
    String value = "hI";
    assertThat(syntax.convert(value)).isEqualTo("Hi");
    assertThat(syntax.convert(value, new Locale("TR", "tr"))).isEqualTo("Hı");
  }

  private void verifyCombined(String example, String expected, String... values) {

    boolean standardize = false;
    for (String value : values) {
      assertThat(CaseSyntax.ofExample(example, standardize).convert(value)).isEqualTo(expected);
      assertThat(CaseSyntax.ofExample(expected, standardize).convert(value)).isEqualTo(expected);
    }
  }

  /**
   * Test of {@link CaseSyntax#ofExample(String, boolean)} with invalid values.
   */
  @Test
  public void testOfExampleInvalidValues() {

    verifyExampleInvalid(null, "example", NullPointerException.class);
    String atLeast3Letters = ": at least 3 letters are required!";
    verifyExampleInvalid("", atLeast3Letters);
    verifyExampleInvalid("a", atLeast3Letters);
    verifyExampleInvalid("ab", atLeast3Letters);
    verifyExampleInvalid("a000", atLeast3Letters);
    verifyExampleInvalid("a0B0", atLeast3Letters);

    String dollarCount = ": the special character '$' is only allowed twice within the first three characters or three times at the beginning!";
    verifyExampleInvalid("A$$$", dollarCount);
    verifyExampleInvalid("$A$$", dollarCount);
    verifyExampleInvalid("$$$$", dollarCount);
    verifyExampleInvalid("AB$$", dollarCount);
    verifyExampleInvalid("ABC$", dollarCount);
    verifyExampleInvalid("$AB$", dollarCount);
    verifyExampleInvalid("A$B$", dollarCount);

    verifyExampleInvalid("a-b.c", ": different word separators '-' and '.'!");
    verifyExampleInvalid("_ab.c", ": different word separators '\0' (_) and '.'!");
    verifyExampleInvalid("a--c", ": duplicate word separators '--'!");

    verifyExampleInvalid("AbC-d", ": word separator '-' detected but word start was already detected via case change!");

    verifyExampleInvalid("InConSISTENTCase", ": the two characters 'SI' both have case UPPER_CASE but other case was detected as LOWER_CASE!");
    verifyExampleInvalid("INCONsistentcASE", ": the two characters 'si' both have case LOWER_CASE but other case was detected as UPPER_CASE!");

    verifyExampleInvalid("in-Con-sistent", ": different word start cases UPPER_CASE and LOWER_CASE!");
    verifyExampleInvalid("in-con-Sistent", ": different word start cases LOWER_CASE and UPPER_CASE!");
  }

  private void verifyExampleInvalid(String example, String messageSuffix) {

    verifyExampleInvalid(example, example + messageSuffix, IllegalArgumentException.class);
  }

  private void verifyExampleInvalid(String example, String message, Class<? extends RuntimeException> exception) {

    try {
      CaseSyntax.ofExample(example, false);
      failBecauseExceptionWasNotThrown(exception);
    } catch (RuntimeException e) {
      assertThat(e).isInstanceOf(exception); // .hasMessage(message);
      assertThat(e.getMessage()).as("message").isEqualTo(message);
    }
  }

  /**
   * Test of {@link CaseSyntax#normalizeExample(String)}.
   */
  @Test
  public void testNormalizeExample() {

    assertThat(CaseSyntax.normalizeExample(null)).isNull();
    assertThat(CaseSyntax.normalizeExample("")).isEmpty();
    assertThat(CaseSyntax.normalizeExample(" ")).isEmpty();
    assertThat(CaseSyntax.normalizeExample("-")).isEmpty();
    assertThat(CaseSyntax.normalizeExample("aBcDeFg")).isEqualTo("abcdefg");
    assertThat(CaseSyntax.normalizeExample("A-B-C-D")).isEqualTo("abcd");
  }

  /**
   * Test of {@link CaseSyntax#equals(Object)} and {@link CaseSyntax#hashCode()}.
   */
  @Test
  public void testEqualsAndHashCode() {

    ObjectHelper.checkEqualsAndHashCode(CaseSyntax.CAML_CASE, CaseSyntax.ofExample("myCase", false), true);
    ObjectHelper.checkEqualsAndHashCode(CaseSyntax.CAML_CASE, CaseSyntax.PASCAL_CASE, false);
    ObjectHelper.checkEqualsAndHashCode(CaseSyntax.CAML_CASE, CaseSyntax.TRAIN_CASE, false);
    ObjectHelper.checkEqualsAndHashCode(CaseSyntax.CAML_CASE, CaseSyntax.UPPERCASE, false);
    ObjectHelper.checkEqualsAndHashCode(CaseSyntax.CAML_CASE, CaseSyntax.LOWERCASE, false);
    ObjectHelper.checkEqualsAndHashCode(CaseSyntax.CAML_CASE, CaseSyntax.UNCAPITALIZED_UPPER, false);
  }

}
