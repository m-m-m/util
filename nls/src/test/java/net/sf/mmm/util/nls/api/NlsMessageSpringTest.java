/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.api;

import javax.inject.Inject;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import net.sf.mmm.util.nls.impl.spring.UtilNlsSpringConfig;

/**
 * This is the test-case for {@link NlsMessage} and {@link NlsMessageFactory} configured using spring.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { UtilNlsSpringConfig.class })
public class NlsMessageSpringTest extends NlsMessageTest {

  @Inject
  private NlsMessageFactory nlsMessageFactory;

  @Override
  protected NlsMessageFactory getMessageFactory() {

    return this.nlsMessageFactory;
  }

}
