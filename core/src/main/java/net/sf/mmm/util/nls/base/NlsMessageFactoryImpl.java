/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.base;

import java.util.Map;

import javax.inject.Named;
import javax.inject.Singleton;

import net.sf.mmm.util.nls.api.NlsMessage;
import net.sf.mmm.util.nls.api.NlsMessageFactory;
import net.sf.mmm.util.nls.api.NlsTemplate;

/**
 * This is the implementation of {@link NlsMessageFactory}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@Named(NlsMessageFactory.CDI_NAME)
@Singleton
public class NlsMessageFactoryImpl extends AbstractNlsMessageFactoryImpl {

  /**
   * The constructor.
   */
  public NlsMessageFactoryImpl() {

    super();
  }

  @Override
  public NlsMessage createDirect(String bundleName, String key, Map<String, Object> messageArguments) {

    NlsTemplate template = new NlsTemplateImplWithMessage(bundleName, key, key);
    return create(template, messageArguments);
  }

}
