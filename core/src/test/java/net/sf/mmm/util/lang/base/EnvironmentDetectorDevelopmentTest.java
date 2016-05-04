/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.base;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import net.sf.mmm.test.AbstractSpringTest;
import net.sf.mmm.util.lang.api.EnvironmentDetector;

/**
 * This is the test-case for {@link EnvironmentDetector} in simulated production mode.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 4.0.0
 */
@ContextConfiguration(AbstractSpringTest.SPRING_CONFIG_UTIL_CORE)
@ActiveProfiles({ "default", "devLocal" })
public class EnvironmentDetectorDevelopmentTest extends AbstractSpringTest {

  /** The {@link EnvironmentDetector} instance. */
  @Inject
  private EnvironmentDetector environmentDetector;

  /**
   * Tests {@link EnvironmentDetector#isProductionEnvironment()} and related behavior.
   */
  @Test
  public void testEnvironmentDetected() {

    Assert.assertEquals(EnvironmentDetector.ENVIRONMENT_TYPE_DEVELOPMENT,
        this.environmentDetector.getEnvironmentType());
    Assert.assertTrue(this.environmentDetector.isDevelopmentEnvironment());
    Assert.assertFalse(this.environmentDetector.isProductionEnvironment());
  }

}
