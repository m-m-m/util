/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.cli.api;

import java.io.PrintStream;

import net.sf.mmm.util.NlsBundleUtilCore;

/**
 * This is the abstract base class for a main-program.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@CliClass(style = CliStyle.STRICT)
@CliModes(//
{ @CliMode(id = CliMode.MODE_HELP, title = NlsBundleUtilCore.INF_MAIN_HELP),
    @CliMode(id = CliMode.MODE_DEFAULT, title = NlsBundleUtilCore.INF_MAIN_DEFAULT) })
public abstract class AbstractMain {

  /** The {@link #run(CliModeObject) exit-code} on success. */
  protected static final int EXIT_CODE_OK = 0;

  /**
   * The {@link #run(CliModeObject) exit-code} on syntax error (illegal
   * command-line arguments).
   */
  protected static final int EXIT_CODE_ILLEGAL_SYNTAX = 1;

  /**
   * The {@link #run(CliModeObject) exit-code} if an unexpected
   * {@link Exception error} ocurred.
   */
  protected static final int EXIT_CODE_UNEXPECTED = -1;

  /** Option to show the {@link #printHelp(CliParser) usage}. */
  @CliOption(name = "--help", aliases = "-h", required = true, usage = NlsBundleUtilCore.INF_MAIN_HELP_USAGE, mode = CliMode.MODE_HELP)
  private boolean help;

  /**
   * The constructor.
   */
  public AbstractMain() {

    super();
  }

  /**
   * This method is called after the options are parsed and injected. It has to
   * be implemented and should do the actual job.
   * 
   * @param mode is the {@link CliModeObject mode} of the invocation.
   * @return the error-code or {@link #EXIT_CODE_OK} on success.
   * @throws Exception in case of an unexpected error.
   */
  protected abstract int run(CliModeObject mode) throws Exception;

  /**
   * This method is invoked if an {@link Exception} occurred. It implements how
   * to handle the error. You may override to add a custom handling.
   * 
   * @param exception is the actual error that occurred.
   * @param parser is the {@link CliParser}.
   * @return the error-code (NOT <code>0</code>).
   */
  protected int handleError(Exception exception, CliParser parser) {

    // TODO: NLS
    PrintStream printStream = getErrorStream();
    if (exception instanceof CliException) {
      printStream.println("Error: " + exception.getMessage());
      printStream.println();
      printHelp(parser);
      return 1;
    } else {
      printStream.println("An unexpected error has occurred:");
      exception.printStackTrace(printStream);
      return -1;
    }
  }

  /**
   * This method gets the {@link CliParserBuilder} used to
   * {@link CliParserBuilder#build(Object) build} the {@link CliParser}.<br>
   * Override to extend with custom functionality.
   * 
   * @return the {@link CliParserBuilder}.
   */
  protected CliParserBuilder getParserBuilder() {

    // TODO
    return null;
  }

  /**
   * This method gets the output stream where to
   * {@link PrintStream#print(String) print} information for the end-user.<br>
   * Default is {@link System#out}.
   * 
   * @return the output stream.
   */
  protected PrintStream getOutputStream() {

    // CHECKSTYLE:OFF (legal for main-program)
    return System.out;
    // CHECKSTYLE:ON
  }

  /**
   * This method gets the output stream where to
   * {@link PrintStream#print(String) print} information for the end-user.<br>
   * Default is {@link System#out}.
   * 
   * @return the output stream.
   */
  protected PrintStream getErrorStream() {

    // CHECKSTYLE:OFF (legal for main-program)
    return System.err;
    // CHECKSTYLE:ON
  }

  /**
   * This method prints the help output with the program usage.
   * 
   * @param parser is the {@link CliParser}.
   */
  protected void printHelp(CliParser parser) {

    parser.printHelp(getOutputStream());
  }

  /**
   * This method is called after the command-line arguments are checked for
   * syntactically correctness and applied to this class in order to perform
   * complex validations.
   * 
   * @param mode is the {@link CliModeObject mode} of the invocation.
   * @throws RuntimeException if the state is invalid.
   */
  protected void validate(CliModeObject mode) throws RuntimeException {

  }

  /**
   * This method should be invoked from the static main-method.
   * 
   * @param args are the commandline-arguments.
   * @return the exit code or <code>0</code> on success.
   */
  public int run(String[] args) {

    CliParserBuilder parserBuilder = getParserBuilder();
    CliParser parser = parserBuilder.build(this);
    try {
      CliModeObject mode = parser.parseArguments(args);
      if (this.help) {
        assert (mode.getMode().id() == CliMode.MODE_HELP);
        printHelp(parser);
        return 0;
      }
      validate(mode);
      return run(mode);
    } catch (Exception e) {
      return handleError(e, parser);
    }

  }

}
