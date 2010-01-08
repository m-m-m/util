/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.transformer.base;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

import net.sf.mmm.util.transformer.api.Transformer;

/**
 * This class converts a string by {@link Pattern#matcher(CharSequence)
 * matching} a given regular expression {@link Pattern} and if it
 * {@link Matcher#find() partially matches} replacing the match(es) with a given
 * replacement.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.2
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class RegexStringTransformer implements Transformer<String> {

  /** @see #getPattern() */
  @XmlAttribute(name = "pattern", required = true)
  private String patternString;

  /** @see #getPattern() */
  private transient Pattern pattern;

  /** @see #getReplacement() */
  @XmlAttribute(name = "replacement")
  private String replacement;

  /** @see #isReplaceAll() */
  @XmlAttribute(name = "replace-all")
  private boolean replaceAll;

  /**
   * The non-arg constructor.<br>
   * <b>NOTE:</b><br>
   * This constructor should not be called directly! It is only intended for
   * reflective access (e.g. for JAXB).
   */
  public RegexStringTransformer() {

    super();
  }

  /**
   * The constructor.
   * 
   * @param pattern is the pattern used for conversion.
   * @param replacement is the replacement to fill in the string to convert.
   * @param replaceAll - if <code>true</code> {@link Matcher#replaceAll(String)}
   *        will be used, else if <code>false</code>
   *        {@link Matcher#replaceFirst(String)}.
   */
  public RegexStringTransformer(Pattern pattern, String replacement, boolean replaceAll) {

    super();
    this.pattern = pattern;
    this.replacement = replacement;
    this.replaceAll = replaceAll;
    this.patternString = this.pattern.pattern();
  }

  /**
   * @return the pattern
   */
  public Pattern getPattern() {

    if (this.pattern == null) {
      this.pattern = Pattern.compile(this.patternString);
    }
    return this.pattern;
  }

  /**
   * @return the replaceAll
   */
  public boolean isReplaceAll() {

    return this.replaceAll;
  }

  /**
   * @return the replacement
   */
  public String getReplacement() {

    return this.replacement;
  }

  /**
   * {@inheritDoc}
   */
  public String transform(String original) {

    Matcher matcher = getPattern().matcher(original);
    if (matcher.find()) {
      if (this.replaceAll) {
        return matcher.replaceAll(this.replacement);
      } else {
        return matcher.replaceFirst(this.replacement);
      }
    }
    return original;
  }

}
