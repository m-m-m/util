/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.regex.Pattern;

import net.sf.mmm.util.lang.base.SequenceCharIterator;

/**
 * Defines the different styles of lower-/upper-case usage such as {@link #CAML_CASE camlCase}, {@link #TRAIN_CASE
 * train-case}, etc. For further examples see the constants defined here in {@link CaseSyntax}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 7.4.0
 */
public class CaseSyntax {

  private static final List<CaseSyntax> CONSTANTS = new ArrayList<>();

  private static final String REGEX_SPECIAL_CHARS = "[^\\p{IsAlphabetic}^\\p{IsDigit}]+";

  private static final Pattern PATTERN_SPECIAL_CHARS = Pattern.compile(REGEX_SPECIAL_CHARS);

  /**
   * The {@link #getWordSeparator() word separator} used to keep any special (separator) characters untouched by
   * {@link #convert(String) convert}.
   */
  public static final Character KEEP_SPECIAL_CHARS = Character.valueOf('\0');

  /** {@link CaseSyntax} for {@code lowercase} (e.g. for Java package segments). */
  public static final CaseSyntax LOWERCASE = new CaseSyntax(null, CaseConversion.LOWER_CASE, "lowercase");

  /** {@link CaseSyntax} for {@code UPPERCASE}. */
  public static final CaseSyntax UPPERCASE = new CaseSyntax(null, CaseConversion.UPPER_CASE, "UPPERCASE");

  /** {@link CaseSyntax} for {@code camlCase} (e.g. for Java fields). */
  public static final CaseSyntax CAML_CASE = new CaseSyntax(null, CaseConversion.LOWER_CASE, CaseConversion.UPPER_CASE, CaseConversion.LOWER_CASE, "camlCase");

  /** {@link CaseSyntax} for {@code PascalCase} (e.g. for Java types). */
  public static final CaseSyntax PASCAL_CASE = new CaseSyntax(null, CaseConversion.UPPER_CASE, CaseConversion.UPPER_CASE, CaseConversion.LOWER_CASE,
      "PascalCase");

  /** {@link CaseSyntax} for {@code train-case} (e.g. for URLs or Angular). */
  public static final CaseSyntax TRAIN_CASE = new CaseSyntax(Character.valueOf('-'), CaseConversion.LOWER_CASE, "train-case");

  /** {@link CaseSyntax} for {@code UPPER-TRAIN-CASE}. */
  public static final CaseSyntax UPPER_TRAIN_CASE = new CaseSyntax(Character.valueOf('-'), CaseConversion.UPPER_CASE, "UPPER-TRAIN-CASE");

  /** {@link CaseSyntax} for {@code Pascal-Train-Case}. */
  public static final CaseSyntax PASCAL_TRAIN_CASE = new CaseSyntax(Character.valueOf('-'), CaseConversion.UPPER_CASE, CaseConversion.UPPER_CASE,
      CaseConversion.LOWER_CASE, "Pascal-Train-Case");

  /** {@link CaseSyntax} for {@code caml-Train-Case}. */
  public static final CaseSyntax CAML_TRAIN_CASE = new CaseSyntax(Character.valueOf('-'), CaseConversion.LOWER_CASE, CaseConversion.UPPER_CASE,
      CaseConversion.LOWER_CASE, "caml-Train-Case");

  /** {@link CaseSyntax} for {@code lower_snake_case} (e.g. for Ruby). */
  public static final CaseSyntax LOWER_SNAKE_CASE = new CaseSyntax(Character.valueOf('_'), CaseConversion.LOWER_CASE, "lower_snake_case");

  /** {@link CaseSyntax} for {@code UPPER_SNAKE_CASE} (e.g. for Java constants). */
  public static final CaseSyntax UPPER_SNAKE_CASE = new CaseSyntax(Character.valueOf('_'), CaseConversion.UPPER_CASE, "UPPER_SNAKE_CASE");

  /** {@link CaseSyntax} for {@code Pascal_Snake_Case}. */
  public static final CaseSyntax PASCAL_SNAKE_CASE = new CaseSyntax(Character.valueOf('_'), CaseConversion.UPPER_CASE, CaseConversion.UPPER_CASE,
      CaseConversion.LOWER_CASE, "Pascal_Snake_Case");

  /** {@link CaseSyntax} for {@code caml_Snake_Case}. */
  public static final CaseSyntax CAML_SNAKE_CASE = new CaseSyntax(Character.valueOf('_'), CaseConversion.LOWER_CASE, CaseConversion.UPPER_CASE,
      CaseConversion.LOWER_CASE, "caml_Snake_Case");

  /** {@link CaseSyntax} for {@code lower space case}. */
  public static final CaseSyntax LOWER_SPACE_CASE = new CaseSyntax(Character.valueOf(' '), CaseConversion.LOWER_CASE, "lower space case");

  /** {@link CaseSyntax} for {@code UPPER SPACE CASE}. */
  public static final CaseSyntax UPPER_SPACE_CASE = new CaseSyntax(Character.valueOf(' '), CaseConversion.UPPER_CASE, "UPPER SPACE CASE");

  /** {@link CaseSyntax} for {@code Pascal Space Case}. */
  public static final CaseSyntax PASCAL_SPACE_CASE = new CaseSyntax(Character.valueOf(' '), CaseConversion.UPPER_CASE, CaseConversion.UPPER_CASE,
      CaseConversion.LOWER_CASE, "Pascal Space Case");

  /** {@link CaseSyntax} for {@code caml Space Case}. */
  public static final CaseSyntax CAML_SPACE_CASE = new CaseSyntax(Character.valueOf(' '), CaseConversion.LOWER_CASE, CaseConversion.UPPER_CASE,
      CaseConversion.LOWER_CASE, "caml Space Case");

  /**
   * {@link CaseSyntax} for {@code $$$unmodified} (keep all characters untouched).
   */
  public static final CaseSyntax UNMODIFIED = new CaseSyntax(null, CaseConversion.ORIGINAL_CASE, "$$$unmodified");

  /**
   * {@link CaseSyntax} for {@code C$$apitalized} (only convert first char to upper case, keep other case untouched).
   */
  public static final CaseSyntax CAPITALIZED = new CaseSyntax(null, CaseConversion.UPPER_CASE, CaseConversion.ORIGINAL_CASE, "C$$apitalized");

  /**
   * {@link CaseSyntax} for {@code u$$capitalized} (only convert first char to lower case, keep other case untouched).
   */
  public static final CaseSyntax UNCAPITALIZED = new CaseSyntax(null, CaseConversion.UPPER_CASE, CaseConversion.ORIGINAL_CASE, "u$$capitalized");

  /** {@link CaseSyntax} for {@code Capitalizedlower}. */
  public static final CaseSyntax CAPITALIZED_LOWER = new CaseSyntax(null, CaseConversion.UPPER_CASE, CaseConversion.LOWER_CASE, CaseConversion.LOWER_CASE,
      "Capitalizedlower");

  /** {@link CaseSyntax} for {@code Capitalizedlower}. */
  public static final CaseSyntax UNCAPITALIZED_UPPER = new CaseSyntax(null, CaseConversion.LOWER_CASE, CaseConversion.UPPER_CASE, CaseConversion.UPPER_CASE,
      "uNCAPITALIZEDUPPER");

  private final Character wordSeparator;

  private final CaseConversion firstCase;

  private final CaseConversion wordStartCase;

  private final CaseConversion otherCase;

  private final String example;

  private CaseSyntax(Character separator, CaseConversion allCharCase, String example) {
    this(separator, allCharCase, allCharCase, allCharCase, example, true);
  }

  private CaseSyntax(Character separator, CaseConversion firstCharCase, CaseConversion wordStartCharCase, String example) {
    this(separator, firstCharCase, wordStartCharCase, CaseConversion.ORIGINAL_CASE, example, true);
  }

  private CaseSyntax(Character separator, CaseConversion firstCharCase, CaseConversion wordStartCharCase, CaseConversion otherCharCase, String example) {

    this(separator, firstCharCase, wordStartCharCase, otherCharCase, example, true);
  }

  private CaseSyntax(Character separator, CaseConversion firstCharCase, CaseConversion wordStartCharCase, CaseConversion otherCharCase, String example,
      boolean register) {
    super();
    this.wordSeparator = separator;
    assert (otherCharCase != null);
    this.otherCase = otherCharCase;
    assert (firstCharCase != null);
    this.firstCase = firstCharCase;
    assert (wordStartCharCase != null);
    this.wordStartCase = wordStartCharCase;
    if (example == null) {
      assert (!register);
      this.example = createExample();
    } else {
      this.example = example;
    }
    if (register) {
      CONSTANTS.add(this);
    }
  }

  /**
   * @return the word separator char (e.g. {@code '_','-' or '.'}) or {@code null} for no word separator. The value
   *         {@link #KEEP_SPECIAL_CHARS} can be used to keep special characters untouched.
   */
  public Character getWordSeparator() {

    return this.wordSeparator;
  }

  /**
   * @return {@code true} if the {@link #getWordSeparator() word separator} is {@code null} or
   */
  public boolean hasWordSeparator() {

    return (this.wordSeparator != null) && (!KEEP_SPECIAL_CHARS.equals(this.wordSeparator));
  }

  /**
   * @return the {@link CaseConversion} used by {@link #convert(String)} for the first character.
   */
  public CaseConversion getFirstCase() {

    return this.firstCase;
  }

  /**
   * @return the {@link CaseConversion} used by {@link #convert(String)} for word start characters (the first character
   *         of a new word except for the {@link #getFirstCase() first} character of the entire {@link String}).
   */
  public CaseConversion getWordStartCase() {

    return this.wordStartCase;
  }

  /**
   * @return the {@link CaseConversion} used by {@link #convert(String)} for all other characters (except
   *         {@link #getFirstCase() first} and {@link #getWordStartCase() word start} characters).
   */
  public CaseConversion getOtherCase() {

    return this.otherCase;
  }

  /**
   * Same as {@link #convert(String, Locale)} with a standard {@link Locale} to prevent accidental conversion.
   *
   * @param string the {@link String} to convert.
   * @return the String converted to this {@link CaseSyntax}.
   */
  public String convert(String string) {

    return convert(string, null);
  }

  /**
   * Converts to this {@link CaseSyntax}. The first character will be converted using {@link #getFirstCase()}.
   * Characters other than {@link Character#isLetterOrDigit(char) letters or digits} are considered as word separator.
   * In the most cases, they will be
   *
   * @param string the {@link String} to convert.
   * @param locale the explicit {@link Locale} to use. In most cases you want to use {@link #convert(String)} instead.
   * @return the String converted to this {@link CaseSyntax}.
   */
  public String convert(String string, Locale locale) {

    if ((string == null) || (string.isEmpty())) {
      return string;
    }
    // fast and simple cases first...
    if ((!hasWordSeparator()) && (this.wordStartCase == this.otherCase)) {
      String s = string;
      if (this.wordSeparator == null) {
        s = removeSpecialCharacters(s);
      }
      if (this.firstCase == this.wordStartCase) {
        s = this.firstCase.convert(s, locale);
      } else {
        String first = this.firstCase.convert(s.substring(0, 1), locale);
        String rest = this.otherCase.convert(s.substring(1), locale);
        s = first + rest;
      }
      return s;
    }
    CharIterator charIterator = new SequenceCharIterator(string);
    StringBuilder buffer = new StringBuilder(string.length() + 4);
    char c = charIterator.next();
    CharClass previousClass = CharClass.of(c);
    while (previousClass.isSeparatorOrDollar()) {
      if ((buffer.length() == 0) && hasWordSeparator()) {
        buffer.append(this.wordSeparator);
      }
      if (!charIterator.hasNext()) {
        return buffer.toString();
      }
      c = charIterator.next();
      previousClass = CharClass.of(c);
    }
    appendCasedChar(buffer, c, this.firstCase);
    CaseConversion previousCase = CaseConversion.ofExample(c, false);
    int start = 1;
    int end = start;
    while (charIterator.hasNext()) {
      c = charIterator.next();
      CharClass currentClass = CharClass.of(c);
      CaseConversion currentCase = CaseConversion.ofExample(c, false);
      switch (currentClass) {
        case LETTER:
          if (previousClass.isSeparatorOrDollar()) {
            appendCasedChar(buffer, c, this.wordStartCase);
            start++;
          } else if (currentCase != previousCase) {
            if ((currentCase == CaseConversion.UPPER_CASE) && (end > 1)) {
              assert (previousCase == CaseConversion.LOWER_CASE);
              start = appendOthers(string, buffer, start, end);
              if (hasWordSeparator()) {
                buffer.append(this.wordSeparator);
              }
              appendCasedChar(buffer, c, this.wordStartCase);
            }
          }
          break;
        case SEPARATOR:
        case DOLLAR:
          if (!previousClass.isSeparatorOrDollar()) {
            start = appendOthers(string, buffer, start, end);
            if (KEEP_SPECIAL_CHARS.equals(this.wordSeparator)) {
              buffer.append(c);
            } else if (this.wordSeparator != null) {
              buffer.append(this.wordSeparator);
            }
          } else {
            start++;
          }
          break;
        default :
          break;
      }
      if (currentClass != CharClass.DIGIT) {
        previousClass = currentClass;
        previousCase = currentCase;
      }
      end++;
    }
    if (start < end) {
      appendOthers(string, buffer, start, end);
    }
    return buffer.toString();
  }

  private int appendOthers(String string, StringBuilder buffer, int start, int end) {

    buffer.append(this.otherCase.convert(string.substring(start, end)));
    return end + 1;
  }

  private void appendCasedChar(StringBuilder buffer, char c, CaseConversion targetCase) {

    if (targetCase == CaseConversion.ORIGINAL_CASE) {
      buffer.append(c);
    } else {
      CaseConversion currentCase = CaseConversion.ofExample(c, false);
      if (currentCase == targetCase) {
        buffer.append(c);
      } else {
        String string = targetCase.convert(Character.toString(c));
        if ((this.otherCase != targetCase) && (string.length() > 1)) {
          buffer.append(string.charAt(0));
          CaseConversion restCase;
          if (this.otherCase == CaseConversion.ORIGINAL_CASE) {
            restCase = currentCase;
          } else {
            restCase = this.otherCase;
          }
          buffer.append(restCase.convert(string.charAt(0)));
        } else {
          buffer.append(string);
        }
      }
    }
  }

  @Override
  public boolean equals(Object obj) {

    if (obj == this) {
      return true;
    }
    if ((obj == null) || (obj.getClass() != getClass())) {
      return false;
    }
    CaseSyntax other = (CaseSyntax) obj;
    if (!Objects.equals(this.wordSeparator, other.wordSeparator)) {
      return false;
    }
    if (this.firstCase != other.firstCase) {
      return false;
    }
    if (this.wordStartCase != other.wordStartCase) {
      return false;
    }
    if (this.otherCase != other.otherCase) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {

    return Objects.hash(this.wordSeparator, this.firstCase, this.wordStartCase, this.otherCase);
  }

  @Override
  public String toString() {

    return this.example;
  }

  /**
   * The constructor.
   *
   * @param separator - see {@link #getWordSeparator()}.
   * @param allCharCase the {@link CaseConversion} used for {@link #getFirstCase() first}, {@link #getWordStartCase()
   *        word-start}, and all {@link #getOtherCase() other} alphabetic characters.
   * @return the requested {@link CaseSyntax}.
   */
  public static CaseSyntax of(Character separator, CaseConversion allCharCase) {

    return of(separator, allCharCase, allCharCase, allCharCase);
  }

  /**
   * The constructor. Will use {@link CaseConversion#LOWER_CASE} for {@link #getOtherCase() other} characters.
   *
   * @param separator - see {@link #getWordSeparator()}.
   * @param firstCharCase - see {@link #getFirstCase()}.
   * @param wordStartCharCase - see {@link #getWordStartCase()}.
   * @return the requested {@link CaseSyntax}.
   */
  public static CaseSyntax of(Character separator, CaseConversion firstCharCase, CaseConversion wordStartCharCase) {

    return of(separator, firstCharCase, wordStartCharCase, CaseConversion.LOWER_CASE);
  }

  /**
   * @param separator - see {@link #getWordSeparator()}.
   * @param firstCharCase - see {@link #getFirstCase()}.
   * @param wordStartCharCase - see {@link #getWordStartCase()}.
   * @param otherCharCase - see {@link #getOtherCase()}.
   * @return the requested {@link CaseSyntax}.
   */
  public static CaseSyntax of(Character separator, CaseConversion firstCharCase, CaseConversion wordStartCharCase, CaseConversion otherCharCase) {

    CaseSyntax syntax = ofStandardized(separator, firstCharCase, wordStartCharCase, otherCharCase);
    if (syntax == null) {
      syntax = new CaseSyntax(separator, firstCharCase, wordStartCharCase, otherCharCase, null, false);
    }
    return syntax;
  }

  private String createExample() {

    // "CustomCase"
    StringBuilder buffer = new StringBuilder(11);
    buffer.append(this.firstCase.convert('C'));
    int i = 0;
    if (this.firstCase == CaseConversion.ORIGINAL_CASE) {
      buffer.insert(i, CaseConversion.EXAMPLE_CHAR_FOR_ORIGINAL_CASE);
    } else {
      i++;
    }
    if (this.wordStartCase == CaseConversion.ORIGINAL_CASE) {
      buffer.insert(i, CaseConversion.EXAMPLE_CHAR_FOR_ORIGINAL_CASE);
    }
    if (this.otherCase == CaseConversion.ORIGINAL_CASE) {
      buffer.insert(i, CaseConversion.EXAMPLE_CHAR_FOR_ORIGINAL_CASE);
    }
    buffer.append(this.otherCase.convert("ustom"));
    if (this.wordSeparator != null) {
      buffer.append(this.wordSeparator);
    }
    buffer.append(this.wordStartCase.convert('C'));
    buffer.append(this.otherCase.convert("ase"));
    String string = buffer.toString();
    return string;
  }

  private static CaseSyntax ofStandardized(Character separator, CaseConversion first, CaseConversion wordStart, CaseConversion other) {

    for (CaseSyntax syntax : CONSTANTS) {
      if (Objects.equals(separator, syntax.wordSeparator) && (first == syntax.firstCase) && (wordStart == syntax.wordStartCase)
          && (other == syntax.otherCase)) {
        return syntax;
      }
    }
    return null;
  }

  /**
   * @param example the {@link #ofExample(String, boolean) example} to normalize.
   * @return the normalized {@code example} (in lower case with all special characters removed).
   */
  public static String normalizeExample(String example) {

    if (example == null) {
      return null;
    }
    return CaseConversion.LOWER_CASE.convert(removeSpecialCharacters(example));
  }

  private static String removeSpecialCharacters(String string) {

    return replaceSpecialCharacters(string, "");
  }

  private static String replaceSpecialCharacters(String string, String replacement) {

    return PATTERN_SPECIAL_CHARS.matcher(string).replaceAll(replacement);
  }

  /**
   * @param example an example for the requested {@link CaseSyntax} such as {@code PascalCase} or {@code MyVariableName}
   *        for {@link #PASCAL_CASE} and {@code train-case} or {@code my-variable-name} for {@link #TRAIN_CASE}. See
   *        other {@link CaseSyntax} constants for more examples. The given {@code example} {@link String} has to start
   *        with a {@link Character#isLetter(char) letter} (or {@link CaseConversion#EXAMPLE_CHAR_FOR_ORIGINAL_CASE
   *        $-Sign}) and contain at least two more such characters (letter or $-sign) next to each other. Digits are
   *        more or less ignored. If you use variable names as example consider naming such variables with more than one
   *        word and no characters other than {@link Character#isLetterOrDigit(char) letters or digits}. Otherwise you
   *        would have to write things such as {@code ComPonent} or {@code com-ponent} to indicate PascalCase or
   *        train-case for the variable {@code component}. Also when using variables as {@link CaseSyntax} example you
   *        should {@link #normalizeExample(String)} normalize} them before resolving.
   * @param standardize - {@code true} if existing constants such as {@link #PASCAL_CASE} or {@link #TRAIN_CASE} shall
   *        be returned if {@link #equals(Object) equal} to the requested {@link CaseSyntax}, {@code false} otherwise.
   * @return the {@link CaseSyntax} for the given {@code example} as described above.
   */
  public static CaseSyntax ofExample(String example, boolean standardize) {

    Objects.requireNonNull(example, "example");
    CharIterator charIterator = new SequenceCharIterator(example);
    Character separator = null;
    CaseConversion other = null;
    CaseConversion wordStart = null;
    char c = charIterator.next();
    CaseConversion first = CaseConversion.ofExample(c, false);
    CharClass previousClass = CharClass.of(c);
    CaseConversion previousCase = first;
    char previousChar = c;
    int index = 0;
    int letterOrDollarCount = previousClass.isLetterOrDollar() ? 1 : 0;
    while (charIterator.hasNext()) {
      index++;
      c = charIterator.next();
      CharClass currentClass = CharClass.of(c);
      CaseConversion currentCase = CaseConversion.ofExample(c, false);
      switch (currentClass) {
        case DOLLAR:
          letterOrDollarCount++;
          if ((other == null) && (index < 3)) { // $$ or $A$
            other = CaseConversion.ORIGINAL_CASE;
          } else if ((wordStart == null) && (index < 3)) { // $$$ or $A$$ or AB$
            wordStart = CaseConversion.ORIGINAL_CASE;
          } else {
            throw new IllegalArgumentException(example + ": the special character '" + CaseConversion.EXAMPLE_CHAR_FOR_ORIGINAL_CASE
                + "' is only allowed twice within the first three characters or three times at the beginning!");
          }
          break;
        case LETTER:
          letterOrDollarCount++;
          boolean wordStarted = false;
          if (previousClass == CharClass.SEPARATOR) {
            if (wordStart == null) {
              wordStart = currentCase;
            }
            wordStarted = true;
          } else if ((other == null) && (wordStart == null)) {
            other = currentCase;
          } else if ((currentCase != previousCase) && (currentCase != other)) { // Case change (Aa or aA) from other
            if (wordStart == null) {
              wordStart = currentCase;
            }
            wordStarted = true;
          } else if (CaseConversion.areIncompatible(currentCase, other)) {
            throw new IllegalArgumentException(
                example + ": the two characters '" + previousChar + c + "' both have case " + currentCase + " but other case was detected as " + other + "!");
          }
          if (wordStarted && CaseConversion.areIncompatible(currentCase, wordStart)) {
            throw new IllegalArgumentException(example + ": different word start cases " + wordStart + " and " + currentCase + "!");
          }
          break;
        case SEPARATOR:
          if (separator == null) {
            if (wordStart != null) {
              throw new IllegalArgumentException(example + ": word separator '" + c + "' detected but word start was already detected via case change!");
            }
            separator = Character.valueOf(c);
          } else if (c != separator.charValue()) {
            throw new IllegalArgumentException(example + ": different word separators '" + separator + "' and '" + c + "'!");
          } else if (previousClass == CharClass.SEPARATOR) {
            throw new IllegalArgumentException(example + ": duplicate word separators '" + separator + separator + "'!");
          }
          break;
        case DIGIT:
        default :
          break;
      }
      if (currentClass != CharClass.DIGIT) {
        previousCase = currentCase;
        previousClass = currentClass;
      }
      previousChar = c;
    }
    if (letterOrDollarCount < 3) {
      throw new IllegalArgumentException(example + ": at least 3 letters are required!");
    }
    if (wordStart == null) {
      wordStart = other;
    } else if (other == null) {
      other = wordStart;
    }
    CaseSyntax syntax = null;
    if (standardize) {
      syntax = ofStandardized(separator, first, wordStart, other);
    }
    if (syntax == null) {
      syntax = new CaseSyntax(separator, first, wordStart, other, example, false);
    }
    return syntax;
  }

  private enum CharClass {

    LETTER,

    DIGIT,

    DOLLAR,

    SEPARATOR;

    private boolean isSeparatorOrDollar() {

      return (this == SEPARATOR) || (this == DOLLAR);
    }

    private boolean isLetterOrDollar() {

      return (this == CharClass.LETTER) || (this == DOLLAR);
    }

    private static CharClass of(char c) {

      if (Character.isLetter(c)) {
        return LETTER;
      } else if (Character.isDigit(c)) {
        return DIGIT;
      } else if (c == CaseConversion.EXAMPLE_CHAR_FOR_ORIGINAL_CASE) {
        return DOLLAR;
      } else {
        return SEPARATOR;
      }
    }
  }

}
