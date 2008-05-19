/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pattern.api;

import java.util.regex.Pattern;

/**
 * This is the interface for a compiler that can
 * {@link #compile(String) compile} an pattern-expression given as string into a
 * {@link Pattern}.<br>
 * The intention is to abstract from {@link Pattern#compile(String, int)} and
 * therefore also allow other dialects like glob-patterns.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface PatternCompiler {

  /**
   * @see Pattern#compile(String)
   * 
   * @param pattern is the expression to be compiled as pattern.
   * @return the compiled pattern. May only return <code>null</code> if
   *         explicitly documented by the underlying implementation. E.g. if the
   *         given <code>pattern</code> is just a plain string without
   *         wildcards.
   * @throws IllegalArgumentException if the given <code>pattern</code> has
   *         illegal syntax for the underlying compiler.
   */
  Pattern compile(String pattern) throws IllegalArgumentException;

}
