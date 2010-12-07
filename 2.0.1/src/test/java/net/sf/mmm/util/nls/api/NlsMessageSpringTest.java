/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.api;

import net.sf.mmm.util.component.impl.SpringContainerPool;

/**
 * This is the test-case for {@link NlsMessage} and {@link NlsMessageFactory}
 * configured using spring.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public class NlsMessageSpringTest extends NlsMessageTest {

  /**
   * {@inheritDoc}
   */
  @Override
  protected NlsMessageFactory getMessageFactory() {

    return SpringContainerPool.getInstance().getComponent(NlsMessageFactory.class);
  }

}
