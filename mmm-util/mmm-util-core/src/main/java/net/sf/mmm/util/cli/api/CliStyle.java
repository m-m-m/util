/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.cli.api;

/**
 * The enum contains the available styles of a {@link CliClass}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.2
 */
public enum CliStyle {

  /**
   * Used to indicate that the style should be inherited (defaults to
   * {@link #STRICT}).
   */
  INHERIT,

  /**
   * Use this style if your {@link CliOption options} should strictly follow all
   * the conventions and fail otherwise.
   */
  STRICT,

  /**
   * Like {@link #STRICT} but causing warnings if you do NOT follow the
   * conventions.
   */
  TOLERANT,

  /**
   * Use this style if your {@link CliOption options} that do NOT follow
   * conventions for reason (legacy CLI support).
   */
  LEGACY,

  /**
   * This style is NOT used by the default implementation and is reserved for
   * customized extensions of <code>util-cli</code>.
   */
  CUSTOM,
}
