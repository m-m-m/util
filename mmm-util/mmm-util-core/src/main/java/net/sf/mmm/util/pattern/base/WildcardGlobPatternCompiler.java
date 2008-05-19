/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pattern.base;

import net.sf.mmm.util.pattern.AbstractGlobPatternCompiler;
import net.sf.mmm.util.pattern.api.PatternCompiler;

/**
 * This class behaves like {@link GlobPatternCompiler} but requires wildcards in
 * the pattern.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class WildcardGlobPatternCompiler extends AbstractGlobPatternCompiler {

  /** A singleton instance of this implementation. */
  public static final PatternCompiler INSTANCE = new WildcardGlobPatternCompiler();

  /**
   * The constructor.
   */
  public WildcardGlobPatternCompiler() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected boolean isRequireWildcard() {

    return true;
  }

}
