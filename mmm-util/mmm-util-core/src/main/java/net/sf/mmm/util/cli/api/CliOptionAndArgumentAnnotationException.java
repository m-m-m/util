/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.cli.api;

import net.sf.mmm.util.NlsBundleUtilCore;

/**
 * A {@link CliOptionAndArgumentAnnotationException} is thrown if a property is annotated with both
 * {@link CliOption} and {@link CliArgument}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public class CliOptionAndArgumentAnnotationException extends CliException {

  /** UID for serialization. */
  private static final long serialVersionUID = -3589470378509687716L;

  /**
   * The constructor.
   * 
   * @param property is the according property.
   */
  public CliOptionAndArgumentAnnotationException(String property) {

    super(NlsBundleUtilCore.ERR_CLI_OPTION_AND_ARGUMENT_ANNOTATION, toMap(KEY_PROPERTY, property));
  }

}
