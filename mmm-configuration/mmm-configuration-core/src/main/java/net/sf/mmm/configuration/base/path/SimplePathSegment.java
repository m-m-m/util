/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.configuration.base.path;

import java.util.regex.Pattern;

import net.sf.mmm.util.StringUtil;

/**
 * This class represents a simple segment of a
 * {@link net.sf.mmm.configuration.api.Configuration#getDescendants(String, String) descendant-path}.
 * A segment is a substring of the descendant-path that represents a query for a
 * {@link net.sf.mmm.configuration.base.AbstractConfiguration#getChild(String) child}
 * configuration. It is build out of a name (-pattern).<br>
 * Examples are "childName", "*", "@a?tr*"
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class SimplePathSegment {

  /**
   * the segment string ({@link net.sf.mmm.configuration.api.Configuration#getName() name})
   */
  private String string;

  /** the {@link #string} as pattern, or <code>null</code> if no pattern. */
  private Pattern pattern;

  /**
   * The constructor.
   * 
   * @param name
   */
  public SimplePathSegment(String name) {

    super();
    this.string = name;
    this.pattern = StringUtil.compileGlobPattern(name, true);
  }

  /**
   * This method determines if the {@link #getString() string} is a
   * {@link net.sf.mmm.util.StringUtil#compileGlobPattern(String) glob-pattern}.
   * 
   * @return <code>true</code> if the {@link #getString() string} is a pattern
   *         and <code>false</code> if it is a regular
   *         {@link net.sf.mmm.configuration.api.Configuration#getName() name}.
   */
  public boolean isPattern() {

    return (this.pattern != null);
  }

  /**
   * This method gets the string. It is eigther a regular
   * {@link net.sf.mmm.configuration.api.Configuration#getName() name} of the
   * according {@link net.sf.mmm.configuration.api.Configuration configuration}
   * or a {@link #getPattern() glob-pattern} the
   * {@link net.sf.mmm.configuration.api.Configuration#getName() name} must
   * match.
   * 
   * @return the string.
   */
  public String getString() {

    return this.string;
  }

  /**
   * This method gets the {@link #getString() string} compiled as
   * {@link net.sf.mmm.util.StringUtil#compileGlobPattern(String, boolean) glob-pattern}
   * or <code>null</code> if NOT a pattern.
   * 
   * @return the pattern or <code>null</code>.
   */
  public Pattern getPattern() {

    return this.pattern;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    return this.string;
  }

}
