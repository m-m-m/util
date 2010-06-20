/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.cli.api;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import net.sf.mmm.util.filter.api.Filter;

/**
 * A {@link CliStyle} is used to annotated a Java-class that holds the
 * parameters of a main-program that are parsed from the commandline arguments.
 * It declares the style of the CLI (command-line-interface) and therefore can
 * ensure conventions and common behavior of the CLI.<br>
 * This annotation is optional. If it is not present, the defaults will apply.<br>
 * The defaults are rather strict and tries to follow GNU-like conventions.
 * 
 * @see CliClass
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface CliStyle {

  /** @see #collectionValueSeparator() */
  char COLLECTION_VALUE_SEPARATOR_NONE = '\0';

  /**
   * Determines how to {@link CliStyleHandling handle} a {@link CliOption} that
   * is supplied as separate CLI parameter (e.g.
   * <code>--file=/foo/bar.txt</code>). The default is
   * {@link CliStyleHandling#EXCEPTION}.
   * 
   * @see #optionSyntaxSeparated()
   */
  CliStyleHandling optionSyntaxAssignment() default CliStyleHandling.EXCEPTION;

  /**
   * Determines how to {@link CliStyleHandling handle} a {@link CliOption} that
   * is supplied as separate CLI parameter (e.g.
   * <code>--file /foo/bar.txt<code>). The default is {@link CliStyleHandling#OK}.
   * 
   * @see #optionSyntaxAssignment()
   */
  CliStyleHandling optionSyntaxSeparated() default CliStyleHandling.OK;

  /**
   * Determines how to {@link CliStyleHandling handle} a {@link CliOption} with
   * property-type {@link Boolean} that is not followed by a value (true/false)
   * as CLI parameter. The default is {@link CliStyleHandling#EXCEPTION}. If
   * allowed by this {@link CliStyleHandling handling}, the value defaults to
   * {@link Boolean#TRUE}.
   */
  CliStyleHandling optionMissingBooleanValue() default CliStyleHandling.EXCEPTION;

  /**
   * Determines how to {@link CliStyleHandling handle} a boolean
   * {@link CliOption} that is supplied as CLI parameter multiple times (e.g.
   * "--force --update --force" or "-fuf"). However this is generally forbidden
   * for values {@link CliOption options}. The default is
   * {@link CliStyleHandling#EXCEPTION}.
   */
  CliStyleHandling optionDuplicated() default CliStyleHandling.EXCEPTION;

  /**
   * A {@link Class} with a public non-arg constructor that implements a
   * {@link Filter} of Strings in order to decide if the
   * {@link CliOption#name() name} or {@link CliOption#aliases() alias} of a
   * {@link CliOption} is {@link Filter#accept(Object) acceptable}.<br>
   * The default is {@link CliOptionNameDefaultFilter}.
   */
  Class<? extends Filter<String>> optionNameFilter() default CliOptionNameDefaultFilter.class;

  /**
   * Determines how to {@link CliStyleHandling handle} a {@link CliOption} with
   * a {@link CliOption#name() name} or {@link CliOption#aliases() alias} that
   * is NOT {@link Filter#accept(Object) accepted} by
   * {@link #optionNameFilter()}. The default is
   * {@link CliStyleHandling#EXCEPTION}.
   */
  CliStyleHandling optionNameNotAcceptedByFilter() default CliStyleHandling.EXCEPTION;

  /**
   * Determines how to {@link CliStyleHandling handle} an undefined
   * {@link CliMode mode}. The default is {@link CliStyleHandling#EXCEPTION}.
   * 
   * @see CliOption#mode()
   * @see CliArgument#mode()
   */
  CliStyleHandling modeUndefined() default CliStyleHandling.EXCEPTION;

  /**
   * The character used to separate collection values (e.g. ',' or ';'). The
   * value {@link #COLLECTION_VALUE_SEPARATOR_NONE} can be used to disable
   * separator and enable multi-parameter submission of collection values. This
   * is the default. In multi-parameter style an {@link CliOption option} or
   * {@link CliArgument argument} with property-type collection may occur
   * multiple times. The collection gets filled with all these values in the
   * order of their occurrence as CLI parameters. For {@link CliArgument
   * arguments} this style is only allowed for the last {@link CliArgument}.<br>
   * Example:<br>
   * Assuming we have a CLI program called <code>Foo</code> with a property
   * <code>private List&lt;String&gt; listProperty</code> annotated with a
   * {@link CliOption} {@link CliOption#name() named} <code>--list</code> and
   * {@link #collectionValueSeparator()} is
   * {@link #COLLECTION_VALUE_SEPARATOR_NONE}. Further it also has a boolean
   * option named <code>--bar</code>. Now we call the program like this:
   * 
   * <pre>
   * Foo --list first --bar --list "second value" --list 3
   * </pre>
   * 
   * So <code>listProperty</code> will be filled with a List containing
   * {"first", "second value", "3"}.
   */
  char collectionValueSeparator() default COLLECTION_VALUE_SEPARATOR_NONE;

  /**
   * Determines how to {@link CliStyleHandling handle} if the property-type
   * {@link CliOption} or {@link CliArgument} is a Map and two entries with the
   * same key are supplied on the commandline. The default is
   * {@link CliStyleHandling#EXCEPTION}.
   */
  CliStyleHandling valueDuplicateMapKey() default CliStyleHandling.EXCEPTION;

  /**
   * Determines how to {@link CliStyleHandling handle} if boolean
   * {@link CliOption options} that have a short-form (see
   * {@link CliParser#PREFIX_SHORT_OPTION}) are mixed as a single CLI parameter
   * (e.g. "-xfv" instead of "-x -f -v"). The default is
   * {@link CliStyleHandling#OK}.
   */
  CliStyleHandling optionMixedShortForm() default CliStyleHandling.OK;

}
