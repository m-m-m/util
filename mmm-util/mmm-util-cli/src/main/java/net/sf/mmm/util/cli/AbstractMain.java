/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.cli;

import java.io.PrintStream;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

/**
 * This is the abstract base class for a main-program using args4j.
 * 
 * TODO: NLS for args4j???
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractMain {

  /** Option to show the {@link #printUsage(CmdLineParser) usage}. */
  @Option(name = "--help", aliases = "-h", required = false, usage = "Print this help.")
  private boolean help;

  /**
   * The constructor.
   */
  public AbstractMain() {

    super();
  }

  /**
   * This method prints the usage.
   * 
   * @param parser is the {@link CmdLineParser}.
   */
  private void printUsage(CmdLineParser parser) {

    // CHECKSTYLE:OFF
    PrintStream printStream = System.out;
    // CHECKSTYLE:ON
    printStream.println("Verwendung: " + getClass().getName() + " [Optionen]");
    printStream.println();
    printStream.println("Optionen:");
    parser.printUsage(printStream);

  }

  /**
   * This method is called after the options are parsed and injected. It has to
   * be implemented and should do the actual job.
   * 
   * @return the error-code or <code>0</code> on success.
   * @throws CmdLineException in case a command-line option is invalid.
   * @throws Exception on any other error.
   */
  protected abstract int run() throws CmdLineException, Exception;

  /**
   * This method is invoked if an {@link Exception} occurred. It implements how
   * to handle the error. You may override to add a custom handling.
   * 
   * @param exception is the actual error that occurred.
   * @param parser is the {@link CmdLineParser}.
   * @return the error-code (NOT <code>0</code>).
   */
  protected int handleError(Exception exception, CmdLineParser parser) {

    // TODO: NLS
    PrintStream printStream = System.err;
    if (exception instanceof CmdLineException) {
      printStream.println("Error: " + exception.getMessage());
      printStream.println();
      printUsage(parser);
      return 1;
    } else {
      printStream.println("An unexpected error has occurred:");
      exception.printStackTrace(printStream);
      return -1;
    }
  }

  /**
   * This method should be invoked from the static main-method.
   * 
   * @param args are the commandline-arguments.
   * @return the exit code or <code>0</code> on success.
   */
  public int run(String[] args) {

    CmdLineParser parser = new CmdLineParser(this);
    try {
      parser.parseArgument(args);
      if (this.help) {
        printUsage(parser);
        return 0;
      }
      return run();
    } catch (Exception e) {
      int exitCode;
      if (this.help) {
        // if "--help" was given but a required option is not set, we do not
        // want to raise such error.
        printUsage(parser);
        exitCode = 0;
      } else {
        exitCode = handleError(e, parser);
      }
      return exitCode;
    }

  }

}
