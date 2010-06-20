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
 * A {@link CliMode} is used to annotate a {@link CliClass CLI annotated class}
 * in order to define a operational mode. Such {@link CliMode mode} is
 * {@link CliOption#mode() referred} by a {@link CliOption} or
 * {@link CliArgument} in order to group them. This allows to express which
 * {@link CliOption options} and {@link CliArgument arguments} can be mixed and
 * which of them should NOT go together.<br>
 * E.g. the options "--help" does NOT make sense to be mixed with "--shutdown".
 * Within some mode, {@link CliOption options} may be
 * {@link CliOption#required() required} but in another mode they should NOT be
 * present.<br>
 * When commandline parameters are {@link CliParser#parseParameters(String...)
 * parsed} the {@link CliMode} is automatically detected and returned. This
 * makes it even easier to implement your main-program and decide what to do.<br>
 * A {@link CliMode} can also be {@link CliMode#isAbstract() abstract}. Such
 * mode can NOT be triggered so an {@link CliOption option} (or
 * {@link CliArgument argument}) {@link CliOption#mode() with} an
 * {@link CliMode#isAbstract() abstract} mode can only be used together with
 * another one that {@link CliOption#mode() has} a mode that is NOT
 * {@link CliMode#isAbstract() abstract} and {@link #parentIds() extends} the
 * {@link CliMode#isAbstract() abstract} mode.
 * 
 * @see CliModes
 * @see CliOption#mode()
 * @see CliParser#parseParameters(String...)
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
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
   * A brief description of what the program actually does in this mode. If not
   * present no explicit description will be
   * {@link CliParser#printHelp(Appendable) printed}.
   */
  String usage() default "";

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

  /**
   * <code>true</code> if this mode is <em>abstract</em>, <code>false</code>
   * otherwise (default).<br>
   * An abstract mode needs to have one or multiple child modes (that
   * {@link #parentIds() extend} the abstract mode). If an {@link CliOption
   * option} {@link CliOption#mode() has a mode}, that is {@link #isAbstract()
   * abstract}, then this {@link CliOption option} can only be used together
   * with another {@link CliOption option} that {@link CliOption#mode() has a
   * mode} that is NOT {@link #isAbstract() abstract} and {@link #parentIds()
   * extends} the {@link #isAbstract() abstract} mode.
   */
  boolean isAbstract() default false;

}
