/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.cli.base;

import java.util.HashSet;
import java.util.Set;

import net.sf.mmm.util.cli.api.CliMode;
import net.sf.mmm.util.cli.api.CliModeObject;

/**
 * This is a container for a {@link CliMode} together with additional associated
 * information.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.2
 */
public class CliModeContainer implements CliModeObject {

  /** @see #getMode() */
  private final CliMode mode;

  /** @see #getAnnotatedClass() */
  private final Class<?> annotatedClass;

  /** @see #getExtendedModes() */
  private final Set<CliModeContainer> extendedModes;

  /**
   * The constructor.
   * 
   * @param mode is the {@link #getMode() mode}.
   * @param annotatedClass is the {@link #getAnnotatedClass() annotated class}.
   */
  public CliModeContainer(CliMode mode, Class<?> annotatedClass) {

    super();
    this.mode = mode;
    this.annotatedClass = annotatedClass;
    this.extendedModes = new HashSet<CliModeContainer>();
  }

  /**
   * {@inheritDoc}
   */
  public CliMode getMode() {

    return this.mode;
  }

  /**
   * {@inheritDoc}
   */
  public Class<?> getAnnotatedClass() {

    return this.annotatedClass;
  }

  /**
   * {@inheritDoc}
   */
  public Set<CliModeContainer> getExtendedModes() {

    return this.extendedModes;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    StringBuilder sb = new StringBuilder();
    sb.append(this.annotatedClass.getSimpleName());
    sb.append('@');
    sb.append(CliMode.class.getSimpleName());
    sb.append("(id=\"");
    sb.append(this.mode.id());
    sb.append("\")");
    return sb.toString();
  }

}
