/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.api;

import java.util.Map;

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
   * @param messageArguments are the {@link NlsMessage#getArgument(String)
   *        arguments} filled into the message after nationalization.
   * @return the new {@link NlsMessage} instance.
   * @deprecated use {@link #create(String, Map)} instead.
   */
  @Deprecated
  NlsMessage create(String internationalizedMessage, Object... messageArguments);

  /**
   * This method creates a new instance of {@link NlsMessage} from the given
   * arguments.
   * 
   * @param template is the {@link NlsTemplate} for the
   *        {@link NlsMessage#getInternationalizedMessage() raw message}.
   * @param messageArguments are the {@link NlsMessage#getArgument(String)
   *        arguments} filled into the message after nationalization.
   * 
   * @return the new {@link NlsMessage} instance.
   * @deprecated use {@link #create(NlsTemplate, Map)} instead.
   */
  @Deprecated
  NlsMessage create(NlsTemplate template, Object... messageArguments);

  /**
   * This method creates a new instance of {@link NlsMessage} from the given
   * arguments.
   * 
   * @param internationalizedMessage is the
   *        {@link NlsMessage#getInternationalizedMessage() internationalized
   *        message}.
   * @param key1 is the {@link NlsMessage#getArgument(String) key} of the first
   *        argument.
   * @param argument1 is the {@link NlsMessage#getArgument(String) value} of the
   *        first argument.
   * @return the new {@link NlsMessage} instance.
   */
  NlsMessage create(String internationalizedMessage, String key1, Object argument1);

  /**
   * This method creates a new instance of {@link NlsMessage} from the given
   * arguments.
   * 
   * @param template is the {@link NlsTemplate} for the
   *        {@link NlsMessage#getInternationalizedMessage() raw message}.
   * @param key1 is the {@link NlsMessage#getArgument(String) key} of the first
   *        argument.
   * @param argument1 is the {@link NlsMessage#getArgument(String) value} of the
   *        first argument.
   * @return the new {@link NlsMessage} instance.
   */
  NlsMessage create(NlsTemplate template, String key1, Object argument1);

  /**
   * This method creates a new instance of {@link NlsMessage} from the given
   * arguments.
   * 
   * @param internationalizedMessage is the
   *        {@link NlsMessage#getInternationalizedMessage() internationalized
   *        message}.
   * @param key1 is the {@link NlsMessage#getArgument(String) key} of the first
   *        argument.
   * @param argument1 is the {@link NlsMessage#getArgument(String) value} of the
   *        first argument.
   * @param key2 is the {@link NlsMessage#getArgument(String) key} of the second
   *        argument.
   * @param argument2 is the {@link NlsMessage#getArgument(String) value} of the
   *        second argument.
   * @return the new {@link NlsMessage} instance.
   */
  NlsMessage create(String internationalizedMessage, String key1, Object argument1, String key2,
      Object argument2);

  /**
   * This method creates a new instance of {@link NlsMessage} from the given
   * arguments.
   * 
   * @param template is the {@link NlsTemplate} for the
   *        {@link NlsMessage#getInternationalizedMessage() raw message}.
   * @param key1 is the {@link NlsMessage#getArgument(String) key} of the first
   *        argument.
   * @param argument1 is the {@link NlsMessage#getArgument(String) value} of the
   *        first argument.
   * @param key2 is the {@link NlsMessage#getArgument(String) key} of the second
   *        argument.
   * @param argument2 is the {@link NlsMessage#getArgument(String) value} of the
   *        second argument.
   * @return the new {@link NlsMessage} instance.
   */
  NlsMessage create(NlsTemplate template, String key1, Object argument1, String key2,
      Object argument2);

  /**
   * This method creates a new instance of {@link NlsMessage} from the given
   * arguments.
   * 
   * @param internationalizedMessage is the
   *        {@link NlsMessage#getInternationalizedMessage() internationalized
   *        message}.
   * @param key1 is the {@link NlsMessage#getArgument(String) key} of the first
   *        argument.
   * @param argument1 is the {@link NlsMessage#getArgument(String) value} of the
   *        first argument.
   * @param key2 is the {@link NlsMessage#getArgument(String) key} of the second
   *        argument.
   * @param argument2 is the {@link NlsMessage#getArgument(String) value} of the
   *        second argument.
   * @param key3 is the {@link NlsMessage#getArgument(String) key} of the third
   *        argument.
   * @param argument3 is the {@link NlsMessage#getArgument(String) value} of the
   *        third argument.
   * @return the new {@link NlsMessage} instance.
   */
  NlsMessage create(String internationalizedMessage, String key1, Object argument1, String key2,
      Object argument2, String key3, Object argument3);

  /**
   * This method creates a new instance of {@link NlsMessage} from the given
   * arguments.
   * 
   * @param template is the {@link NlsTemplate} for the
   *        {@link NlsMessage#getInternationalizedMessage() raw message}.
   * @param key1 is the {@link NlsMessage#getArgument(String) key} of the first
   *        argument.
   * @param argument1 is the {@link NlsMessage#getArgument(String) value} of the
   *        first argument.
   * @param key2 is the {@link NlsMessage#getArgument(String) key} of the second
   *        argument.
   * @param argument2 is the {@link NlsMessage#getArgument(String) value} of the
   *        second argument.
   * @param key3 is the {@link NlsMessage#getArgument(String) key} of the third
   *        argument.
   * @param argument3 is the {@link NlsMessage#getArgument(String) value} of the
   *        third argument.
   * @return the new {@link NlsMessage} instance.
   */
  NlsMessage create(NlsTemplate template, String key1, Object argument1, String key2,
      Object argument2, String key3, Object argument3);

  /**
   * This method creates a new instance of {@link NlsMessage} from the given
   * arguments.
   * 
   * @param internationalizedMessage is the
   *        {@link NlsMessage#getInternationalizedMessage() internationalized
   *        message}.
   * @param messageArguments are the {@link NlsMessage#getArgument(String)
   *        arguments} filled into the message after nationalization.
   * @return the new {@link NlsMessage} instance.
   */
  NlsMessage create(String internationalizedMessage, Map<String, Object> messageArguments);

  /**
   * This method creates a new instance of {@link NlsMessage} from the given
   * arguments.
   * 
   * @param template is the {@link NlsTemplate} for the
   *        {@link NlsMessage#getInternationalizedMessage() raw message}.
   * @param messageArguments are the {@link NlsMessage#getArgument(String)
   *        arguments} filled into the message after nationalization.
   * @return the new {@link NlsMessage} instance.
   */
  NlsMessage create(NlsTemplate template, Map<String, Object> messageArguments);

  /**
   * This method creates a new instance of {@link NlsMessage} from the given
   * arguments.
   * 
   * @param internationalizedMessage is the
   *        {@link NlsMessage#getInternationalizedMessage() internationalized
   *        message}.
   * @return the new {@link NlsMessage} instance.
   */
  NlsMessage create(String internationalizedMessage);

  /**
   * This method creates a new instance of {@link NlsMessage} from the given
   * arguments.
   * 
   * @param template is the {@link NlsTemplate} for the
   *        {@link NlsMessage#getInternationalizedMessage() raw message}.
   * @return the new {@link NlsMessage} instance.
   */
  NlsMessage create(NlsTemplate template);

}
