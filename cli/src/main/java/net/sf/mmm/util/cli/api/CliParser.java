/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.cli.api;

/**
 * This is the interface used to {@link #parseParameters(String...) parse} the command-line arguments of a
 * main-program.
 * 
 * @see CliClass
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface CliParser {

  /**
   * The option prefix character.
   * 
   * @see #PREFIX_SHORT_OPTION
   * @see #PREFIX_LONG_OPTION
   */
  char CHAR_OPTION = '-';

  /**
   * The prefix for {@link CliOption#name() name} or {@link CliOption#aliases() alias} of a short option.
   * Value is {@value} (e.g. "-h").
   */
  String PREFIX_SHORT_OPTION = "-";

  /**
   * The prefix for {@link CliOption#name() name} or {@link CliOption#aliases() alias} of a long option. Value
   * is {@value} (e.g. "--help").
   */
  String PREFIX_LONG_OPTION = "--";

  /**
   * The argument used to indicate that {@link CliOption options} are complete and {@link CliArgument
   * arguments} will follow. This allows to specify an argument that is equal to an option (e.g.
   * "cat -- --help" would print the contents of a file named "--help" rather that printing the help usage
   * output).
   */
  String END_OPTIONS = "--";

  /**
   * This method prints the help (program usage) generated from the annotations to the given
   * {@link Appendable} using default {@link CliOutputSettings settings}.
   * 
   * @see #printHelp(Appendable, CliOutputSettings)
   * 
   * @param target is the {@link Appendable} where to print to.
   */
  void printHelp(Appendable target);

  /**
   * This method prints the help (program usage) generated from the annotations to the given
   * {@link Appendable} using the given {@code settings}.
   * 
   * @param target is the {@link Appendable} where to print to.
   * @param settings are the {@link CliOutputSettings} used to configure the output.
   */
  void printHelp(Appendable target, CliOutputSettings settings);

  /**
   * This method parses the given command-line parameters and applies the parsed {@link CliOption options} and
   * {@link CliArgument arguments} to the {@link net.sf.mmm.util.pojo.api.Pojo} that was supplied when
   * {@link CliParserBuilder#build(Object) building} this {@link CliParser parser}.
   * 
   * @param parameters are the command-line arguments of the main-program.
   * @return the {@link CliOption#mode() mode} that was triggered.
   * @throws CliException if the given {@code arguments} are invalid.
   */
  CliModeObject parseParameters(String... parameters) throws CliException;

}
