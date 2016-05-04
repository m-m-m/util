/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.filter.base;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import net.sf.mmm.util.filter.api.FilterRule;
import net.sf.mmm.util.pattern.base.RegexInfixPatternCompiler;
import net.sf.mmm.util.xml.base.jaxb.XmlAdapterInfixPattern;

/**
 * This is an implementation of the {@link FilterRule} interface that matches using a regex {@link Pattern}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.2
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class PatternFilterRule implements FilterRule<String> {

  @XmlAttribute(name = "pattern", required = true)
  @XmlJavaTypeAdapter(value = XmlAdapterInfixPattern.class)
  private Pattern pattern;

  @XmlAttribute(name = "include", required = true)
  private Boolean result;

  /**
   * The non-arg constructor. <br>
   * <b>NOTE:</b><br>
   * This constructor should not be called directly! It is only intended for reflective access (e.g. for JAXB).
   */
  public PatternFilterRule() {

    super();
  }

  /**
   * The constructor.
   *
   * @param pattern is the pattern a file has to match in order to activate this rule. Before this given string is
   *        compiled via {@link Pattern#compile(String)} the following manipulation is performed: If the pattern string
   *        does NOT start with the character {@code ^} the implicit prefix {@code .*} is added. If the pattern does NOT
   *        end with the character {@code $} the implicit suffix {@code .*} is appended.
   * @param resultOnMatch is the result {@link #accept(String) returned} if the pattern matches.
   */
  public PatternFilterRule(String pattern, boolean resultOnMatch) {

    this(RegexInfixPatternCompiler.INSTANCE.compile(pattern), resultOnMatch);
  }

  /**
   * The constructor.
   *
   * @param pattern is the pattern a file has to match in order to activate this rule.
   * @param resultOnMatch is the result {@link #accept(String) returned} if the pattern matches.
   */
  public PatternFilterRule(Pattern pattern, boolean resultOnMatch) {

    super();
    this.pattern = pattern;
    this.result = Boolean.valueOf(resultOnMatch);
  }

  @Override
  public Boolean accept(String string) {

    Matcher matcher = this.pattern.matcher(string);
    if (matcher.matches()) {
      return this.result;
    }
    return null;
  }

}
