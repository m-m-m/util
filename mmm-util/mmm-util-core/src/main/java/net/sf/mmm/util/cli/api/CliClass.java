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
 * A {@link CliClass} is used to annotate a Java-Class that holds the parameters
 * of a main-program that are parsed from the commandline arguments. This
 * annotation is optional. If it is not present, the defaults will apply.
 * 
 * @see CliOption
 * @see CliArgument
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.2
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface CliClass {

  /**
   * The {@link CliStyle style} of the options. If you do not want to follow
   * common conventions (e.g. for legacy commandline-argument compatibility) you
   * can use a different {@link CliStyle style} to prevent warnings. Further it
   * is possible to influence the parser
   */
  CliStyle style() default CliStyle.INHERIT;

  /**
   * The name of the main-program for the usage. By default the
   * {@link Class#getName() qualified classname} of the program. You can set
   * this explicitly if your main-program is always run from a front-end
   * shell-script or you do NOT want to annotate you main-program class but use
   * an externalized state object.
   */
  String name() default "";

  /**
   * The brief description of what this program actually does. If not set it
   * will be inherited. If not present at all no explicit description will be
   * {@link CliParser#printHelp(Appendable) printed}.
   */
  String usage() default "";

}
