/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.cli.api;

import net.sf.mmm.util.NlsMessagesBundleUtilCore;

/**
 * A {@link CliParserExcepiton} is thrown if a property is annotated as {@link CliOption} or
 * {@link CliArgument} but the type of that property is not supported.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public class CliParserExcepiton extends CliException {

  /** UID for serialization. */
  private static final long serialVersionUID = 7576087965627428527L;

  /**
   * The constructor.
   * 
   * @param nested is the {@link #getCause() cause} of this exception.
   * @param type is the {@link net.sf.mmm.util.cli.base.CliState#getStateClass() state-class}.
   */
  public CliParserExcepiton(Exception nested, Class<?> type) {

    super(nested, createBundle(NlsMessagesBundleUtilCore.class).errorCliParser(type));
  }

}
