/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.version.impl;

import javax.inject.Inject;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import net.sf.mmm.util.version.api.VersionUtil;
import net.sf.mmm.util.version.impl.spring.UtilVersionSpringConfig;

/**
 * This is the test-case for {@link VersionUtil} configured using spring.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { UtilVersionSpringConfig.class })
public class VersionUtilSpringTest extends VersionUtilTest {

  @Inject
  private VersionUtil versionUtil;

  @Override
  public VersionUtil getVersionUtil() {

    return this.versionUtil;
  }

}
