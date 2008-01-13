/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.base;

import net.sf.mmm.util.nls.api.NlsMessage;
import net.sf.mmm.util.nls.api.NlsMessageFactory;
import net.sf.mmm.util.nls.api.NlsTemplate;
import net.sf.mmm.util.nls.impl.NlsMessageImpl;

/**
 * This is an ugly factory pattern implementation used to create instances of
 * {@link NlsMessage} and allowing to exchange the default implementation.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class NlsMessageFactoryImpl implements NlsMessageFactory {

  /**
   * The constructor.
   */
  public NlsMessageFactoryImpl() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  public NlsMessage create(String internationalizedMessage, Object... messageArguments) {

    return new NlsMessageImpl(internationalizedMessage, messageArguments);
  }

  /**
   * {@inheritDoc}
   */
  public NlsMessage create(NlsTemplate template, Object... messageArguments) {

    return new NlsMessageImpl(template, messageArguments);
  }

}
