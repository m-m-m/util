/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pattern.base;

import java.util.regex.Pattern;

import net.sf.mmm.util.pattern.api.PatternCompiler;

/**
 * This is the canonical implementation of the {@link PatternCompiler}
 * interface. It simply delegates to {@link Pattern#compile(String, int)}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class RegexPatternCompiler implements PatternCompiler {

  /** @see #RegexPatternCompiler(int) */
  private final int flags;

  /**
   * The constructor.
   */
  public RegexPatternCompiler() {

    this(0);
  }

  /**
   * The constructor.
   * 
   * @param flags are the {@link Pattern#compile(String, int) compiler flags}.
   */
  public RegexPatternCompiler(int flags) {

    super();
    this.flags = flags;
  }

  /**
   * {@inheritDoc}
   */
  public Pattern compile(String pattern) throws IllegalArgumentException {

    return Pattern.compile(pattern, this.flags);
  }

}
