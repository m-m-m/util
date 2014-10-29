/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.api;

import java.util.Map;

/**
 * This interface provides a {@link #getMessage(String, Map) generic lookup} of a {@link NlsMessage}. <br>
 * 
 * @see NlsBundleWithLookup
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 4.0.0
 */
public abstract interface NlsMessageLookup {

  /**
   * This method create the {@link NlsMessage} for the given <code>key</code> with the given <code>nlsArguments</code>.
   * 
   * @param key is the {@link java.lang.reflect.Method#getName() method name}.
   * @param nlsArguments are the {@link NlsMessage#getArgument(String) arguments}. May be <code>null</code> for no
   *        arguments.
   * @return the {@link NlsMessage} or <code>null</code> if no message exists for the given <code>methodName</code> (no
   *         such method exists).
   */
  NlsMessage getMessage(String key, Map<String, Object> nlsArguments);

}
