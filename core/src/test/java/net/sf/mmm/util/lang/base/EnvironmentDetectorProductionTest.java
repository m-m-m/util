/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.base;

import javax.inject.Inject;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import net.sf.mmm.util.lang.api.EnvironmentDetector;
import net.sf.mmm.util.lang.impl.spring.UtilLangSpringConfig;

/**
 * This is the test-case for {@link EnvironmentDetector} in simulated production mode.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 4.0.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { UtilLangSpringConfig.class })
@ActiveProfiles({ "default", "productionEnvironment2" })
public class EnvironmentDetectorProductionTest extends Assertions {

  /** The {@link EnvironmentDetector} instance. */
  @Inject
  private EnvironmentDetector environmentDetector;

  /**
   * Tests {@link EnvironmentDetector#isProductionEnvironment()} and related behavior.
   */
  @Test
  public void testEnvironmentDetected() {

    assertThat(this.environmentDetector.getEnvironmentType())
        .isEqualTo(EnvironmentDetector.ENVIRONMENT_TYPE_PRODUCTION);
    assertThat(this.environmentDetector.isProductionEnvironment()).isTrue();
    assertThat(this.environmentDetector.isDevelopmentEnvironment()).isFalse();
  }

}
