/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.impl.formatter;

import net.sf.mmm.util.NlsMessagesBundleUtilCore;
import net.sf.mmm.util.nls.api.NlsRuntimeException;

/**
 * The {@link NlsFormatterChoiceNoElseConditionException} is thrown if a {@link NlsFormatterChoice
 * choice-format} has no (else)-condition.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public class NlsFormatterChoiceNoElseConditionException extends NlsRuntimeException {

  /** UID for serialization. */
  private static final long serialVersionUID = -6417256932637230995L;

  /**
   * The constructor.
   */
  public NlsFormatterChoiceNoElseConditionException() {

    super(createBundle(NlsMessagesBundleUtilCore.class).errorNlsChoiceNoElse());
  }

}
