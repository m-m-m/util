/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.cli.api;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import net.sf.mmm.util.filter.api.Filter;

/**
 * A {@link CliStyle} is used to annotated a Java-class that holds the parameters of a main-program that are
 * parsed from the commandline arguments. It declares the style of the CLI (command-line-interface) and
 * therefore can ensure conventions and common behavior of the CLI. <br>
 * This annotation is optional. If it is not present, the defaults will apply. <br>
 * The defaults are rather strict and tries to follow GNU-like conventions.
 * 
 * @see CliClass
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface CliStyle {

  /**
   * Determines how to {@link CliStyleHandling handle} a {@link CliOption} that is supplied as separate CLI
   * parameter (e.g. {@code --file=/foo/bar.txt}). The default is {@link CliStyleHandling#EXCEPTION}.
   * 
   * @see #optionSyntaxSeparated()
   */
  CliStyleHandling optionSyntaxAssignment() default CliStyleHandling.EXCEPTION;

  /**
   * Determines how to {@link CliStyleHandling handle} a {@link CliOption} that is supplied as separate CLI
   * parameter (e.g. {@code --file /foo/bar.txt}). The default is {@link CliStyleHandling#OK}.
   * 
   * @see #optionSyntaxAssignment()
   */
  CliStyleHandling optionSyntaxSeparated() default CliStyleHandling.OK;

  /**
   * Determines how to {@link CliStyleHandling handle} a {@link CliOption} with property-type {@link Boolean}
   * that is not followed by a value (true/false) as CLI parameter. The default is
   * {@link CliStyleHandling#EXCEPTION}. If allowed by this {@link CliStyleHandling handling}, the value
   * defaults to {@link Boolean#TRUE}.
   */
  CliStyleHandling optionMissingBooleanValue() default CliStyleHandling.EXCEPTION;

  /**
   * Determines how to {@link CliStyleHandling handle} a boolean {@link CliOption} that is supplied as CLI
   * parameter multiple times (e.g. "--force --update --force" or "-fuf"). However this is generally forbidden
   * for values {@link CliOption options}. The default is {@link CliStyleHandling#EXCEPTION}.
   */
  CliStyleHandling optionDuplicated() default CliStyleHandling.EXCEPTION;

  /**
   * A {@link Class} with a public non-arg constructor that implements a {@link Filter} of Strings in order to
   * decide if the {@link CliOption#name() name} or {@link CliOption#aliases() alias} of a {@link CliOption}
   * is {@link Filter#accept(Object) acceptable}. <br>
   * The default is {@link CliOptionNameDefaultFilter}.
   */
  Class<? extends Filter<String>> optionNameFilter() default CliOptionNameDefaultFilter.class;

  /**
   * Determines how to {@link CliStyleHandling handle} a {@link CliOption} with a {@link CliOption#name()
   * name} or {@link CliOption#aliases() alias} that is NOT {@link Filter#accept(Object) accepted} by
   * {@link #optionNameFilter()}. The default is {@link CliStyleHandling#EXCEPTION}.
   */
  CliStyleHandling optionNameNotAcceptedByFilter() default CliStyleHandling.EXCEPTION;

  /**
   * Determines how to {@link CliStyleHandling handle} an undefined {@link CliMode mode}. The default is
   * {@link CliStyleHandling#EXCEPTION}.
   * 
   * @see CliOption#mode()
   * @see CliArgument#mode()
   */
  CliStyleHandling modeUndefined() default CliStyleHandling.EXCEPTION;

  /**
   * Determines how to {@link CliStyleHandling handle} a {@link CliMode mode} that is declared more than once.
   * If accepted, the deepest {@link CliMode} annotation is selected from the {@link Class}
   * {@link Class#getSuperclass() hierarchy} of the {@link net.sf.mmm.util.cli.base.CliState#getStateClass()
   * state-class}. The default is {@link CliStyleHandling#DEBUG}. <br>
   * <b>ATTENTION:</b><br>
   * Please note that it can make sense to override a {@link CliMode}.
   * 
   * @see CliOption#mode()
   * @see CliArgument#mode()
   */
  CliStyleHandling modeDuplicated() default CliStyleHandling.DEBUG;

  /**
   * The {@link CliContainerStyle style} of container values. It shall NOT be
   * {@link CliContainerStyle#DEFAULT}. The default is {@link CliContainerStyle#MULTIPLE_OCCURRENCE}. This
   * setting can be overridden via {@link CliOption#containerStyle()} or {@link CliArgument#containerStyle()}.
   */
  CliContainerStyle containerStyle() default CliContainerStyle.MULTIPLE_OCCURRENCE;

  /**
   * Determines how to {@link CliStyleHandling handle} if the property-type {@link CliOption} or
   * {@link CliArgument} is a Map and two entries with the same key are supplied on the commandline. The
   * default is {@link CliStyleHandling#EXCEPTION}.
   */
  CliStyleHandling valueDuplicateMapKey() default CliStyleHandling.EXCEPTION;

  /**
   * Determines how to {@link CliStyleHandling handle} if boolean {@link CliOption options} that have a
   * short-form (see {@link CliParser#PREFIX_SHORT_OPTION}) are mixed as a single CLI parameter (e.g. "-xfv"
   * instead of "-x -f -v"). The default is {@link CliStyleHandling#OK}.
   */
  CliStyleHandling optionMixedShortForm() default CliStyleHandling.OK;

}
