/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.impl.formatter;

import net.sf.mmm.util.lang.api.attribute.AttributeReadMessage;
import net.sf.mmm.util.lang.api.attribute.AttributeReadMessageCode;

/**
 * The {@link NlsFormatterChoiceNoElseConditionException} is thrown if a {@link NlsFormatterChoice
 * choice-format} has no (else)-condition.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public class NlsFormatterChoiceNoElseConditionException extends RuntimeException implements AttributeReadMessage, AttributeReadMessageCode {

  private static final long serialVersionUID = 1L;

  /** @see #getCode() */
  public static final String MESSAGE_CODE = "ChoiceNoElse";

  /**
   * The constructor.
   */
  public NlsFormatterChoiceNoElseConditionException() {

    super("A choice format needs to end with an (else)-condition!");
  }

  @Override
  public String getCode() {

    return MESSAGE_CODE;
  }

}
