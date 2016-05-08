/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.io.base;

import javax.inject.Inject;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import net.sf.mmm.util.io.api.StreamUtil;
import net.sf.mmm.util.io.impl.spring.UtilIoSpringConfig;

/**
 * This is the test-case for {@link StreamUtil} configured using spring.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { UtilIoSpringConfig.class })
public class StreamUtilSpringTest extends StreamUtilTest {

  @Inject
  private StreamUtil streamUtil;

  @Override
  protected StreamUtil getStreamUtil() {

    return this.streamUtil;
  }

}
