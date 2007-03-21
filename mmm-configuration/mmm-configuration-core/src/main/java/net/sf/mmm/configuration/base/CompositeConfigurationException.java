/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.configuration.base;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.List;

import net.sf.mmm.configuration.api.ConfigurationException;

/**
 * This is a configuration exception that contains multiple nested configuration
 * exceptions. It is a summary of multiple errors that occurred during a chain of
 * operations that should be processed to the end.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class CompositeConfigurationException extends ConfigurationException {

  /** UID for serialization */
  private static final long serialVersionUID = -7509766690812596716L;

  /** The aggregated exceptions */
  private final List<ConfigurationException> trouble;

  /**
   * The constructor.
   * 
   * @param childExceptions
   */
  public CompositeConfigurationException(List<ConfigurationException> childExceptions) {

    // TODO
    super("");
    this.trouble = childExceptions;
  }

  /**
   * {@inheritDoc} 
   */
  public void printStackTrace(PrintStream s) {

    super.printStackTrace(s);
    for (int i = 0; i < this.trouble.size(); i++) {
      s.println("" + i + ". child exception:");
      this.trouble.get(i).printStackTrace(s);
    }
  }

  /**
   * {@inheritDoc} 
   */
  public void printStackTrace(PrintWriter s) {

    super.printStackTrace(s);
    for (int i = 0; i < this.trouble.size(); i++) {
      s.println("" + i + ". child exception:");
      this.trouble.get(i).printStackTrace(s);
    }
  }

}
