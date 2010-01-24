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
 * A {@link CliOption} is used to annotate a field (member variable of some
 * class) that should be set from a main-program via a commandline option.<br>
 * The annotated field should NOT be {@link java.lang.reflect.Modifier#STATIC
 * static} or {@link java.lang.reflect.Modifier#FINAL final} and by convention
 * it should be {@link java.lang.reflect.Modifier#PRIVATE private}.
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
  String[] aliases();

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
   * The {@link CliOption options} of a program can be split into groups. The
   * options are ordered in groups in the help-usage-output and groups allow to
   * express that an {@link CliOption option} is required only in a specific
   * mode.<br>
   * Options of different groups can not be mixed. If a group-name
   * {@link String#startsWith(String) starts with} the name of another group, it
   * automatically extends that group (e.g. the group "import-strict" extends
   * the group "import".
   */
  String mode() default CliMode.MODE_DEFAULT;

}
