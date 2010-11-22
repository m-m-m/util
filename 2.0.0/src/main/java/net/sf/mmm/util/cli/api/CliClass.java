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
 * A {@link CliClass} is used to annotate a Java-class that holds the parameters
 * of a main-program that are parsed from the commandline arguments.<br>
 * It declares the {@link #name() name of the program} and the additional
 * {@link #usage() usage information}. This annotation is optional, however it
 * is recommended to declare it for the reason of documentation and maintenance.
 * If it is not present, the defaults will apply.
 * 
 * @see CliStyle
 * @see CliOption
 * @see CliArgument
 * @see AbstractMain
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface CliClass {

  /**
   * The name of the main-program for the usage. By default the
   * {@link Class#getName() qualified classname} of the program is used. You can
   * set this explicitly if your main-program is always run from a front-end
   * shell-script or you do NOT want to annotate you main-program class but use
   * an externalized state object.
   */
  String name() default "";

  /**
   * A brief description of what this program actually does. This is added to
   * the generated {@link CliParser#printHelp(Appendable) usage help} of the
   * program. If not set no additional custom description will be
   * {@link CliParser#printHelp(Appendable) printed}.
   */
  String usage() default "";

}
