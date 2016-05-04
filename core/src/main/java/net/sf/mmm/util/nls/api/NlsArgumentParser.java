/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.api;

import net.sf.mmm.util.component.api.ComponentSpecification;
import net.sf.mmm.util.scanner.base.CharSequenceScanner;

/**
 * The {@link NlsArgumentParser} is used to {@link #parse(CharSequenceScanner) parse} an {@link NlsArgument}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
@ComponentSpecification
public interface NlsArgumentParser {

  /** The character used to start a variable expression: {@value} */
  char START_EXPRESSION = '{';

  /** The character used to end a variable expression: {@value} */
  char END_EXPRESSION = '}';

  /** The character used to separate format type and style: {@value} */
  char FORMAT_SEPARATOR = ',';

  /**
   * This method parses the {@link NlsMessage#getInternationalizedMessage() internationalized message} given as
   * {@link CharSequenceScanner} pointing the beginning of an argument (immediately after
   * {@link NlsArgumentParser#START_EXPRESSION}).
   *
   * @see NlsFormatterManager#getFormatter(String, String)
   *
   * @param scanner is the {@link CharSequenceScanner}.
   * @return the according {@link NlsFormatter} instance.
   */
  NlsArgument parse(CharSequenceScanner scanner);

}
