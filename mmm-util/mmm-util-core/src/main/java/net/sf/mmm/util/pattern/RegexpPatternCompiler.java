/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pattern;

import java.util.regex.Pattern;

/**
 * This is the canonical implementation of the {@link PatternCompiler}
 * interface. It simply delegates to {@link Pattern#compile(String, int)}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class RegexpPatternCompiler implements PatternCompiler {

  /** @see #RegexpCompiler(int) */
  private final int flags;

  /**
   * The constructor.
   */
  public RegexpPatternCompiler() {

    this(0);
  }

  /**
   * The constructor.
   * 
   * @param flags are the {@link Pattern#compile(String, int) compiler flags}.
   */
  public RegexpPatternCompiler(int flags) {

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
