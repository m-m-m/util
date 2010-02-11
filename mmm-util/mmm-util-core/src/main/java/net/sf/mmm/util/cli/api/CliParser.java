/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.cli.api;

/**
 * This is the interface used to {@link #parseArguments(String...) parse} the
 * command-line arguments of a main-program.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.2
 */
public interface CliParser {

  /**
   * This method prints the help (program usage) generated from the annotations
   * to the given {@link Appendable} including the
   * {@link #printHelp(Appendable, boolean) main-program usage}.
   * 
   * @param target is the {@link Appendable} where to print to.
   */
  void printHelp(Appendable target);

  /**
   * This method prints the help (program usage) generated from the annotations
   * to the given {@link Appendable} including the
   * {@link #printHelp(Appendable, boolean) main-program usage}.
   * 
   * @param target is the {@link Appendable} where to print to.
   * @param settings are the {@link CliOutputSettings} used to configure the
   *        output.
   */
  void printHelp(Appendable target, CliOutputSettings settings);

  /**
   * This method parses the given command-line arguments and applies the parsed
   * {@link CliOption options} and {@link CliArgument arguments} to the
   * {@link net.sf.mmm.util.pojo.api.Pojo} supplied when
   * {@link CliParserBuilder#build(Object) building} this {@link CliParser
   * parser}.
   * 
   * @param arguments are the command-line arguments of the main-program.
   * @return the {@link CliOption#mode() mode} that was triggered.
   * @throws CliException if the given <code>arguments</code> are invalid.
   */
  CliModeObject parseArguments(String... arguments) throws CliException;

}
