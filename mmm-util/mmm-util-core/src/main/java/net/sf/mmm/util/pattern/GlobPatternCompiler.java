/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pattern;

/**
 * This is an implementation of the {@link PatternCompiler} interface that
 * {@link #compile(String) compiles} <em>glob-patterns</em>. A glob-pattern
 * is a pattern, where only the wildcard characters <code>'*'</code> and
 * <code>'?'</code> are treated special. The asterisk (<code>'*'</code>)
 * can match any string including the empty string and the questionmark (<code>'?'</code>)
 * can match any single character.<br>
 * Examples:
 * <ul>
 * <li><code>A*bc?e</code> matches <code>Abcde</code> or
 * <code>AFOObarbcxe</code> but NOT <code>abcde</code> or <code>Abce</code>.</li>
 * </ul>
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class GlobPatternCompiler extends AbstractGlobPatternCompiler {

  /** A singleton instance of this implementation. */
  public static final PatternCompiler INSTANCE = new GlobPatternCompiler();

  /**
   * The constructor.
   */
  public GlobPatternCompiler() {

    super();
  }

}
