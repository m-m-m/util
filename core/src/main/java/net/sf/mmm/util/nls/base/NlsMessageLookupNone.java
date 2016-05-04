/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.base;

import java.util.Map;

import net.sf.mmm.util.nls.api.NlsMessage;
import net.sf.mmm.util.nls.api.NlsMessageLookup;

/**
 * This is a dummy implementation of {@link NlsMessageLookup} that will always return {@code null}.
 *
 * @see #INSTANCE
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 4.0.0
 */
public class NlsMessageLookupNone implements NlsMessageLookup {

  /** The singleton instance. */
  public static final NlsMessageLookupNone INSTANCE = new NlsMessageLookupNone();

  /**
   * The constructor.
   */
  public NlsMessageLookupNone() {

    super();
  }

  @Override
  public NlsMessage getMessage(String key, Map<String, Object> nlsArguments) {

    return null;
  }

}
