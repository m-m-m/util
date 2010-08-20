/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.cli.api;

/**
 * This enum contains the available styles for arrays and collections.
 * 
 * @see CliConstraintContainer
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public enum CliCollectionStyle {

  /**
   * The {@link CliOption} or {@link CliArgument} may occur multiple times. The
   * collection gets filled with all these values in the order of their
   * occurrence. This style is the default even if no
   * {@link CliConstraintContainer} is annotated. For {@link CliArgument
   * arguments} this style is only allowed for the last {@link CliArgument}.<br>
   * Example:<br>
   * Assuming we have a CLI program called <code>Foo</code> with a property
   * <code>private List<String> listProperty</code> annotated with a
   * {@link CliOption} {@link CliOption#name() named} <code>--list</code> and
   * also annotated with this style via {@link CliConstraintContainer}. It may
   * also have another boolean option named <code>--bar</code>. Now we call the
   * program like this:
   * 
   * <pre>
   * Foo --list first --bar --list "second value" --list 3
   * </pre>
   * 
   * So <code>listProperty</code> will be filled with a List containing
   * {"first", "second value", "3"}.
   */
  MULTIPLE_OCCURRENCE,

  /**
   * The {@link CliOption} or {@link CliArgument} may occur only once. Multiple
   * values are supplied as comma separated list. In the above example call the
   * program as following:
   * 
   * <pre>
   * Foo --list "first,second value,3" --bar
   * </pre>
   */
  COMMA_SEPARATED,

  /**
   * The {@link CliOption} or {@link CliArgument} may occur only once. Multiple
   * values are supplied as semicolon separated list. In the above example call
   * the program as following:
   * 
   * <pre>
   * Foo --list "first;second value;3" --bar
   * </pre>
   */
  SEMICOLON_SEPARATED,

}
