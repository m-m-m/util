/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.base;

import java.util.Map;

import net.sf.mmm.util.nls.api.NlsAccess;
import net.sf.mmm.util.nls.api.NlsBundleWithLookup;
import net.sf.mmm.util.nls.api.NlsMessage;
import net.sf.mmm.util.nls.api.NlsMessageLookup;

/**
 * This class combines multiple instances of {@link NlsMessageLookup} to a single instance. It will call the delegate
 * instances in the given order and return the first result that is not <code>null</code>.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 4.0.0
 */
public class NlsMessageLookupProxy implements NlsMessageLookup {

  /** @see #getMessage(String, Map) */
  private final NlsMessageLookup[] delegates;

  /**
   * The constructor.
   * 
   * @param delegates are the instances of {@link NlsMessageLookup} to delegate to. Typically instances of
   *        {@link net.sf.mmm.util.nls.api.NlsBundleWithLookup}.
   */
  public NlsMessageLookupProxy(NlsMessageLookup... delegates) {

    super();
    this.delegates = delegates;
  }

  /**
   * The constructor.
   * 
   * @param bundles are the {@link NlsBundleWithLookup} to delegate to.
   */
  @SafeVarargs
  public NlsMessageLookupProxy(Class<? extends NlsBundleWithLookup>... bundles) {

    super();
    this.delegates = new NlsBundleWithLookup[bundles.length];
    for (int i = 0; i < this.delegates.length; i++) {
      this.delegates[i] = NlsAccess.getBundleFactory().createBundle(bundles[i]);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public NlsMessage getMessage(String key, Map<String, Object> nlsArguments) {

    for (NlsMessageLookup lookup : this.delegates) {
      NlsMessage message = lookup.getMessage(key, nlsArguments);
      if (message != null) {
        return message;
      }
    }
    return null;
  }

}
