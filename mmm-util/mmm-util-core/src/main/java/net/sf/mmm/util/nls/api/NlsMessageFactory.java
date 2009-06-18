/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.api;

/**
 * This is the interface for a factory used to create instances of
 * {@link NlsMessage}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface NlsMessageFactory {

  /**
   * This method creates a new instance of {@link NlsMessage} from the given
   * arguments.
   * 
   * @param internationalizedMessage is the
   *        {@link NlsMessage#getInternationalizedMessage() internationalized
   *        message}.
   * @param messageArguments are the {@link NlsMessage#getArgument(int)
   *        arguments} filled into the message after nationalization.
   * @return the new {@link NlsMessage} instance.
   */
  NlsMessage create(String internationalizedMessage, Object... messageArguments);

  /**
   * This method creates a new instance of {@link NlsMessage} from the given
   * arguments.
   * 
   * @param text is the bundled text of the message.
   * @param messageArguments are the {@link NlsMessage#getArgument(int)
   *        arguments} filled into the message after nationalization.
   * 
   * @return the new {@link NlsMessage} instance.
   */
  NlsMessage create(NlsTemplate text, Object... messageArguments);

}
