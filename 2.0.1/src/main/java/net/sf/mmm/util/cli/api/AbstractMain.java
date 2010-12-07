/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.cli.api;

import java.io.PrintWriter;

import net.sf.mmm.util.NlsBundleUtilCore;
import net.sf.mmm.util.cli.impl.DefaultCliParserBuilder;
import net.sf.mmm.util.component.api.IocContainer;
import net.sf.mmm.util.component.base.AbstractLoggableObject;
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
{ @CliMode(id = CliMode.ID_HELP, title = NlsBundleUtilCore.INF_MAIN_MODE_HELP, //
usage = NlsBundleUtilCore.MSG_MAIN_MODE_HELP_USAGE) })
public abstract class AbstractMain extends AbstractLoggableObject {

  /** The {@link #run(CliModeObject) exit-code} on success. */
  protected static final int EXIT_CODE_OK = 0;

  /**
   * The {@link #run(CliModeObject) exit-code} on syntax error (illegal
   * command-line arguments).
   */
  protected static final int EXIT_CODE_ILLEGAL_SYNTAX = 1;

  /**
   * The {@link #run(CliModeObject) exit-code} on constraint error (illegal
   * command-line value).
   * 
   * @see net.sf.mmm.util.cli.base.CliParameterContainer#getValidator()
   */
  protected static final int EXIT_CODE_CONSTRAINT_VIOLATION = 2;

  /**
   * The {@link #run(CliModeObject) exit-code} if an unexpected
   * {@link Exception error} occurred.
   */
  protected static final int EXIT_CODE_UNEXPECTED = -1;

  /** @see #getOutputSettings() */
  private final CliOutputSettings outputSettings;

  /** @see #getStandardOutput() */
  private PrintWriter standardOutput;

  /** @see #getStandardError() */
  private PrintWriter standardError;

  /** @see #getParserBuilder() */
  private CliParserBuilder parserBuilder;

  /** @see #getStreamUtil() */
  private StreamUtil streamUtil;

  /** Option to show the {@link #printHelp(CliParser) usage}. */
  @CliOption(name = CliOption.NAME_HELP, aliases = CliOption.ALIAS_HELP, //
  required = true, usage = NlsBundleUtilCore.MSG_MAIN_OPTION_HELP_USAGE, mode = CliMode.ID_HELP)
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

    int exitCode;
    AppendableWriter writer = new AppendableWriter(this.standardError);
    PrintWriter printStream = new PrintWriter(writer);
    if (exception instanceof CliException) {
      // TODO: NLS
      printStream.println("Error: " + exception.getMessage());
      printStream.println();
      printStream.flush();
      printHelp(parser);
      exitCode = 1;
    } else {
      printStream.println("An unexpected error has occurred:");
      exception.printStackTrace(printStream);
      exitCode = -1;
    }
    printStream.flush();
    return exitCode;
  }

  /**
   * This method gets the {@link IocContainer} used to manage components with
   * their implementation. It should be created and initialized on the first
   * call of this method.<br/>
   * This default implementation simply returns <code>null</code> to avoid
   * dependencies on a {@link IocContainer} implementation. Override this method
   * to use proper component management.
   * 
   * @see net.sf.mmm.util.component.impl.SpringContainer
   * 
   * @return the {@link IocContainer}.
   */
  protected IocContainer getIocContainer() {

    return null;
  }

  /**
   * This method gets the {@link CliParserBuilder} used to
   * {@link CliParserBuilder#build(Object) build} the {@link CliParser}.<br>
   * To extend with custom functionality you should use an
   * {@link #getIocContainer() IoC container} in advance to overriding this
   * method.
   * 
   * @return the {@link CliParserBuilder}.
   */
  protected CliParserBuilder getParserBuilder() {

    if (this.parserBuilder == null) {
      IocContainer container = getIocContainer();
      if (container == null) {
        DefaultCliParserBuilder impl = new DefaultCliParserBuilder();
        impl.initialize();
        this.parserBuilder = impl;
      } else {
        this.parserBuilder = container.getComponent(CliParserBuilder.class);
      }
    }
    return this.parserBuilder;
  }

  /**
   * This method gets the {@link StreamUtil} to use.
   * 
   * @return the {@link StreamUtil} to use.
   */
  protected StreamUtil getStreamUtil() {

    if (this.streamUtil == null) {
      IocContainer container = getIocContainer();
      if (container == null) {
        this.streamUtil = StreamUtilImpl.getInstance();
      } else {
        this.streamUtil = container.getComponent(StreamUtil.class);
      }
    }
    return this.streamUtil;
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
  public int run(String... args) {

    CliParser parser = getParserBuilder().build(this);
    try {
      CliModeObject mode = parser.parseParameters(args);
      if (this.help) {
        assert (mode.getId().equals(CliMode.ID_HELP));
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
