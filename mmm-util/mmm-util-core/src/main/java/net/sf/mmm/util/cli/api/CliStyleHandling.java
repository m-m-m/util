/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.cli.api;

import org.slf4j.Logger;

/**
 * This enum contains the available ways to handle a specific CLI-style.
 * 
 * @see CliStyle#optionNameNotAcceptedByFilter()
 * @see CliStyle#optionSyntaxAssignment()
 * @see CliStyle#optionDuplicated()
 * @see CliStyle#modeUndefined()
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public enum CliStyleHandling {

  /** Accept the according style as normal. */
  OK {

    /**
     * {@inheritDoc}
     */
    @Override
    public void handle(Logger logger, RuntimeException exception) {

      // nothing to do...
    }
  },

  /** Log a debug message on occurrence. */
  DEBUG {

    /**
     * {@inheritDoc}
     */
    @Override
    public void handle(Logger logger, RuntimeException exception) {

      if (logger.isDebugEnabled()) {
        logger.debug(exception.getLocalizedMessage());
      }
    }
  },

  /** Log a warning on occurrence. */
  WARNING {

    /**
     * {@inheritDoc}
     */
    @Override
    public void handle(Logger logger, RuntimeException exception) {

      logger.warn(exception.getLocalizedMessage());
    }
  },

  /** Cause an exception on occurrence. */
  EXCEPTION {

    /**
     * {@inheritDoc}
     */
    @Override
    public void handle(Logger logger, RuntimeException exception) {

      throw exception;
    }
  };

  /**
   * This method performs the handling of the anomaly represented by the given <code>exception</code>.<br>
   * You should avoid creating the exception and call this method for the handling {@link #OK}.
   * 
   * @param logger is the {@link Logger} potentially used to log the exception (-message).
   * @param exception informs about the anomaly.
   */
  public abstract void handle(Logger logger, RuntimeException exception);

}
