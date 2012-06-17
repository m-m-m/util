/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.text.api;

import java.util.Locale;

import net.sf.mmm.util.component.base.ComponentSpecification;

/**
 * This is the interface used to {@link #getHyphenator(Locale) retrieve} a {@link Hyphenator} for a given
 * {@link Locale}.<br>
 * An implementation has to be thread-safe.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
@ComponentSpecification
public interface HyphenatorBuilder {

  /**
   * This method {@link #getHyphenator(Locale) gets} the {@link Hyphenator} for the
   * {@link Locale#getDefault() default locale}.
   * 
   * @return the according {@link Hyphenator}.
   */
  Hyphenator getHyphenator();

  /**
   * This method gets the {@link Hyphenator} for the given {@link Locale}. If no hyphenation-rules are
   * available for the given {@link Locale}, a {@link Hyphenator} for a more general {@link Locale} is build.
   * 
   * @param locale is the {@link Locale} used to determine the hyphenation-rules for the according country and
   *        region.
   * @return the according {@link Hyphenator}.
   */
  Hyphenator getHyphenator(Locale locale);

}
