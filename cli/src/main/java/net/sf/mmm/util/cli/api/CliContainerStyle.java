/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.cli.api;

/**
 * This enum contains the available styles for container types. A container type is an array, {@link java.util.Map} or
 * {@link java.util.Collection}.
 *
 * @see CliStyle#containerStyle()
 * @see CliOption#containerStyle()
 * @see CliArgument#containerStyle()
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public enum CliContainerStyle {

  /**
   * The default style that indicates a fallback to the {@link CliStyle#containerStyle()}.
   */
  DEFAULT,

  /**
   * The {@link CliOption} or {@link CliArgument} may occur multiple times. The collection gets filled with all these
   * values in the order of their occurrence. This style is the default. For {@link CliArgument arguments} this style is
   * only allowed for the last {@link CliArgument}. <br>
   * Example:<br>
   * Assuming we have a CLI program called {@code Foo} with a property {@literal private List<String> listProperty}
   * annotated with a {@link CliOption} {@link CliOption#name() named} {@code --list} and with this style via
   * {@link CliOption#containerStyle()}. It may also have another boolean option named {@code --bar}. Now we call the
   * program like this:
   *
   * <pre>
   * Foo --list first --bar --list "second value" --list 3
   * </pre>
   *
   * So {@code listProperty} will be filled with a List containing {"first", "second value", "3"}.
   */
  MULTIPLE_OCCURRENCE,

  /**
   * The {@link CliOption} or {@link CliArgument} may occur only once. Multiple values are supplied as comma separated
   * list. In the above example call the program as following:
   *
   * <pre>
   * Foo --list "first,second value,3" --bar
   * </pre>
   *
   * @see net.sf.mmm.util.value.impl.AbstractValueConverterToContainer
   */
  COMMA_SEPARATED,

}
