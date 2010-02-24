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
 * A {@link CliOption} is used to annotate a property (member variable of some
 * class or setter-method) that should be set from a main-program via a
 * commandline option.<br>
 * If the annotated field is a property it may NOT be
 * {@link java.lang.reflect.Modifier#STATIC static} or
 * {@link java.lang.reflect.Modifier#FINAL final} and by convention it should be
 * {@link java.lang.reflect.Modifier#PRIVATE private}.<br>
 * The annotated property should be initialized properly at construction in
 * order to determine whether the option was triggered or not. For non-primitive
 * types the property should be initialized with <code>null</code>.<br>
 * Options with a type other than <code>boolean</code> need to be followed by a
 * value. This value has to be quoted in the commandline if it contains
 * whitespaces or other characters that are interpreted by the shell (e.g.
 * backslash in Unix).<br>
 * The type of the annotated property has to be supported by the CLI
 * implementation so the value can be converted properly. The followng types are
 * guaranteed to be supported:
 * <table border="1">
 * <tr>
 * <th>Type</th>
 * <th>Comment</th>
 * </tr>
 * <tr>
 * <td>{@link String}</td>
 * <td>The given text as is.</td>
 * </tr>
 * <tr>
 * <td>boolean</td>
 * <td>Set to <code>true</code> if {@link CliOption} is present. Should be
 * initialized with <code>false</code> at construction.</td>
 * </tr>
 * <tr>
 * <td>{@link Boolean}</td>
 * <td>If the option is present with a value of <code>true</code> or
 * <code>false</code> the according {@link Boolean} is applied. Otherwise values
 * are treated as invalid. According to {@link CliStyle}, <code>true</code> may
 * be omitted.</td>
 * </tr>
 * <tr>
 * <td>{@link Number}</td>
 * <td>The subclasses of {@link Number} from the package <code>java.lang</code>
 * are always supported. See {@link CliConstraintNumber}.</td>
 * </tr>
 * <tr>
 * <td>{@link java.io.File}</td>
 * <td>A file {@link java.io.File#File(String) created} from the value of the
 * option. See {@link CliConstraintFile}.</td>
 * </tr>
 * <tr>
 * <td>{@link Enum}</td>
 * <td>A {@link Class#isEnum() concrete subclass} of {@link Enum}. The value
 * needs to match the {@link Enum#name()} but case is ignored and underscores
 * may also be replaced by hyphen ("-") or whitespace (" ").</td>
 * </tr>
 * </table>
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.2
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target(value = { ElementType.FIELD, ElementType.METHOD })
public @interface CliOption {

  /** The default {@link #operand() operand}. */
  String PARAMETER_DEFAULT = "arg";

  /** The {@link #name()} of the option to get help/usage. */
  String NAME_HELP = "--help";

  /** The {@link #aliases() alias} of the option to get help/usage. */
  String ALIAS_HELP = "-h";

  /** The {@link #name()} of the option to get the program version. */
  String NAME_VERSION = "--version";

  /** The {@link #aliases() alias} of the option to get the program version. */
  String ALIAS_VERSION = "-v";

  /** The {@link #name()} of the option for verbose output. */
  String NAME_VERBOSE = "--verbose";

  /**
   * The actual option (e.g. "--help").<br>
   * By convention this should be a GNU long-named option starting with "--"
   * followed by a self-explanatory name where terms may with separated with
   * hyphens (e.g. "--with-extra-option").
   */
  String name();

  /**
   * The name of the operand for the {@link #usage() usage}. A {@link CliOption}
   * can either be a switch (if type of annotated field is boolean/Boolean) or
   * it takes an operand (e.g. "--delay 5"). The name of this operand for the
   * {@link #usage() usage} output can be configured here.
   */
  String operand() default PARAMETER_DEFAULT;

  /**
   * The list of optional aliases that can be used instead of the
   * {@link #name()}. E.g. for {@link #name()} "--help" an alias could be "-h".
   */
  String[] aliases() default { };

  /** The description of the option. */
  String usage();

  /**
   * The flag that indicates if this option is required. Use a value of
   * <code>true</code> to make this option required within it's {@link #mode()
   * mode}.
   * 
   * @see CliMode#parentIds()
   */
  boolean required() default false;

  /**
   * A typical main-program has different modes how it can be invoked.<br>
   * The {@link CliOption options} of a program can be split into groups that
   * represent such mode. The options are ordered by their mode in the
   * help-usage-output and modes allow to express that an {@link CliOption
   * option} is {@link #required() required} only in a specific mode.<br>
   * {@link CliOption Options} can only be combined as command-line arguments if
   * their {@link #mode() modes} are compatible. This means that the modes have
   * to be identical or one mode {@link CliMode#parentIds() extends} the other.
   * In the latter case the most special mode is triggered. For each
   * {@link #mode() mode} that is used in an {@link CliOption option} of an
   * {@link CliClass CLI annotated class} an according {@link CliMode}
   * annotation has to be present in order to define the mode.
   */
  String mode() default CliMode.MODE_DEFAULT;

}
