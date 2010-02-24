/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.cli.api;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * A {@link CliMode} indicates a {@link CliOption#mode() mode} of a
 * {@link CliOption}. It allows a main-program to group its {@link CliOption
 * options} in such mode and to prevent mixing {@link CliOption options} that
 * should not go together.<br>
 * E.g. the options "--help" does NOT make sense to be mixed with "--shutdown".
 * Within some mode, {@link CliOption options} may be
 * {@link CliOption#required() required} but in another mode they should NOT be
 * present.
 * 
 * @see CliModes
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.2
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface CliMode {

  /** The default {@link CliMode mode}. */
  String MODE_DEFAULT = "default";

  /** The {@link CliMode group} for getting help (the usage). */
  String MODE_HELP = "help";

  /** The {@link CliMode group} for getting the program-version. */
  String MODE_VERSION = "version";

  /**
   * The unique id of this {@link CliMode}. Should match to
   * {@link CliOption#mode() mode} of {@link CliOption options}.
   */
  String id();

  /**
   * The title of this {@link CliMode} for displaying in help-usage for the
   * end-user.
   * 
   * @see net.sf.mmm.util.nls.api.NlsMessage
   */
  String title();

  /**
   * The {@link #id() IDs} of the {@link CliMode modes} to extend.<br>
   * If you have two {@link CliOption options} with different
   * {@link CliOption#mode() modes} and the first extends the second, then the
   * two {@link CliOption options} can be used together and the mode of the
   * extended {@link CliMode mode} is chosen. For the chosen {@link CliMode
   * mode} and all {@link CliMode modes} inherited (recursively) all
   * {@link CliOption#required() required} {@link CliOption options} have to be
   * present.
   */
  String[] parentIds() default { };

}
