/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.cli.api;

import java.io.PrintWriter;

import net.sf.mmm.util.NlsBundleUtilCore;
import net.sf.mmm.util.cli.base.DefaultCliParserBuilder;
import net.sf.mmm.util.io.api.StreamUtil;
import net.sf.mmm.util.io.base.AppendableWriter;
import net.sf.mmm.util.io.base.StreamUtilImpl;

/**
 * This is the abstract base class for a main-program.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@CliClass
@CliModes(//
{
    @CliMode(id = CliMode.MODE_HELP, title = NlsBundleUtilCore.INF_MAIN_MODE_HELP, // 
    usage = NlsBundleUtilCore.MSG_MAIN_MODE_HELP_USAGE),
    @CliMode(id = CliMode.MODE_DEFAULT, title = NlsBundleUtilCore.INF_MAIN_MODE_DEFAULT) })
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

  /** @see #getStandardOutput() */
  private PrintWriter standardOutput;

  /** @see #getStandardError() */
  private PrintWriter standardError;

  /** @see #getOutputSettings() */
  private final CliOutputSettings outputSettings;

  /** Option to show the {@link #printHelp(CliParser) usage}. */
  @CliOption(name = CliOption.NAME_HELP, aliases = CliOption.ALIAS_HELP, //
  required = true, usage = NlsBundleUtilCore.MSG_MAIN_OPTION_HELP_USAGE, mode = CliMode.MODE_HELP)
  private boolean help;

  /**
   * The constructor.
   */
  public AbstractMain() {

    super();
    this.outputSettings = new CliOutputSettings();
    // CHECKSTYLE:OFF (legal for main-program)
    this.standardOutput = new PrintWriter(System.out);
    this.standardError = new PrintWriter(System.err);
    // CHECKSTYLE:ON
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
    AppendableWriter writer = new AppendableWriter(this.standardError);
    PrintWriter printStream = new PrintWriter(writer);
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

    DefaultCliParserBuilder parserBuilder = new DefaultCliParserBuilder();
    parserBuilder.initialize();
    return parserBuilder;
  }

  /**
   * This method gets the {@link StreamUtil} to use.
   * 
   * @return the {@link StreamUtil} to use.
   */
  public StreamUtil getStreamUtil() {

    return StreamUtilImpl.getInstance();
  }

  /**
   * This method gets the {@link CliOutputSettings output settings} used for
   * information and error messages.
   * 
   * @return the outputSettings.
   */
  public CliOutputSettings getOutputSettings() {

    return this.outputSettings;
  }

  /**
   * This method gets the standard output where to {@link PrintWriter#println()
   * print} information for the end-user.<br>
   * Default is {@link System#out}.
   * 
   * @return the standard output.
   */
  public final PrintWriter getStandardOutput() {

    return this.standardOutput;
  }

  /**
   * This method sets the {@link #getStandardOutput() standard output}. It may
   * be used by sub-classes or tests to redirect output.
   * 
   * @param output is the output to set
   */
  public final void setStandardOutput(Appendable output) {

    this.standardOutput = getStreamUtil().toPrintWriter(output);
  }

  /**
   * This method gets the standard error where to
   * {@link Appendable#append(CharSequence) print} errors for the end-user.<br>
   * Default is {@link System#err}.
   * 
   * @return the output stream.
   */
  public final Appendable getStandardError() {

    return this.standardError;
  }

  /**
   * This method sets the {@link #getStandardError() standard error}. It may be
   * used by sub-classes or tests to redirect errors.
   * 
   * @param error is the error to set.
   */
  public final void setStandardError(Appendable error) {

    this.standardError = getStreamUtil().toPrintWriter(error);
  }

  /**
   * This method prints the help output with the program usage.
   * 
   * @param parser is the {@link CliParser}.
   */
  protected void printHelp(CliParser parser) {

    parser.printHelp(getStandardOutput(), getOutputSettings());
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
      CliModeObject mode = parser.parseParameters(args);
      if (this.help) {
        assert (mode.getId().equals(CliMode.MODE_HELP));
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
