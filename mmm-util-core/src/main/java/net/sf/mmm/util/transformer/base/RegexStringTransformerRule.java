/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.transformer.base;

import java.util.regex.Pattern;

import javax.xml.bind.annotation.XmlAttribute;

import net.sf.mmm.util.transformer.api.StringTransformerRule;

/**
 * This class is a {@link RegexStringTransformer} that implements the {@link StringTransformerRule} interface
 * by adding the {@link #isStopOnMatch() stop-on-match} flag.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.2
 */
public class RegexStringTransformerRule extends RegexStringTransformer implements StringTransformerRule {

  /** @see #isStopOnMatch() */
  @XmlAttribute(name = "stop-on-match")
  private boolean stopOnMatch;

  /**
   * The non-arg constructor. <br>
   * <b>NOTE:</b><br>
   * This constructor should not be called directly! It is only intended for reflective access (e.g. for
   * JAXB).
   */
  public RegexStringTransformerRule() {

    super();
  }

  /**
   * The constructor.
   * 
   * @param pattern is the pattern used for conversion.
   * @param replacement is the replacement to fill in the string to convert.
   * @param replaceAll - if <code>true</code> {@link java.util.regex.Matcher#replaceAll(String)} will be used,
   *        else if <code>false</code> {@link java.util.regex.Matcher#replaceFirst(String)}.
   * @param stopOnMatch if <code>true</code> and the {@link #getPattern() pattern} of this rule matches no
   *        further rules will be executed.
   */
  public RegexStringTransformerRule(Pattern pattern, String replacement, boolean replaceAll, boolean stopOnMatch) {

    super(pattern, replacement, replaceAll);
    this.stopOnMatch = stopOnMatch;
  }

  /**
   * @return the lastRule
   */
  public boolean isStopOnMatch() {

    return this.stopOnMatch;
  }

}
