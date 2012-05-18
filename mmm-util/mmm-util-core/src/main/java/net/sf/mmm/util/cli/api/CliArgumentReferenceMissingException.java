/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.cli.api;

import net.sf.mmm.util.NlsBundleUtilCore;
import net.sf.mmm.util.cli.base.CliArgumentContainer;

/**
 * A {@link CliArgumentReferenceMissingException} is thrown if a {@link CliArgument#addCloseTo() referenced}
 * {@link CliArgument argument} is missing.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public class CliArgumentReferenceMissingException extends CliException {

  /** UID for serialization. */
  private static final long serialVersionUID = -4351735003857434632L;

  /**
   * The constructor.
   * 
   * @param argumentContainer is the {@link CliArgumentContainer} with the unresolved
   *        {@link CliArgument#addCloseTo() reference}.
   */
  public CliArgumentReferenceMissingException(CliArgumentContainer argumentContainer) {

    super(createBundle(NlsBundleUtilCore.class).errorCliArgumentReferenceMissing(
        argumentContainer.getArgument().addCloseTo(), argumentContainer));
  }

}
