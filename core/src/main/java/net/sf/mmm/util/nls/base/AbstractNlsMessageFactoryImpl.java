/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.base;

import java.util.Map;

import net.sf.mmm.util.nls.api.NlsAccess;
import net.sf.mmm.util.nls.api.NlsMessage;
import net.sf.mmm.util.nls.api.NlsTemplate;
import net.sf.mmm.util.nls.impl.NlsMessageImpl;

/**
 * This is the abstract but almost complete implementation of {@link net.sf.mmm.util.nls.api.NlsMessageFactory}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.1.0
 */
public abstract class AbstractNlsMessageFactoryImpl extends AbstractNlsMessageFactory {

  /**
   * The constructor.
   */
  public AbstractNlsMessageFactoryImpl() {

    super();
  }

  @Override
  public NlsMessage create(NlsTemplate template, Map<String, Object> messageArguments) {

    return new NlsMessageImpl(template, messageArguments);
  }

  @Override
  public NlsMessage create(String internationalizedMessage, Map<String, Object> messageArguments) {

    return new NlsMessageImpl(internationalizedMessage, messageArguments);
  }

  @Override
  protected void doInitialized() {

    super.doInitialized();
    NlsAccess.setFactory(this);
  }

}
