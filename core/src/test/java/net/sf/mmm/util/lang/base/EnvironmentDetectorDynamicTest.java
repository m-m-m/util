/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.base;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import net.sf.mmm.test.AbstractSpringTest;
import net.sf.mmm.test.ExceptionHelper;
import net.sf.mmm.util.lang.api.EnvironmentDetector;

/**
 * This is the test-case for {@link EnvironmentDetector} in simulated production mode.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 4.0.0
 */
public class EnvironmentDetectorDynamicTest {

  /**
   * @param expectedEnvironmentType is the expected {@link EnvironmentDetector#getEnvironmentType() environment type}.
   * @param activeProfiles are the profiles to activate.
   * @return the {@link EnvironmentDetector} instance for further assertions.
   */
  protected EnvironmentDetector checkEnvironmentDetector(String expectedEnvironmentType,
      String... activeProfiles) {

    ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
        AbstractSpringTest.SPRING_CONFIG_UTIL_CORE);
    context.getEnvironment().setActiveProfiles(activeProfiles);
    EnvironmentDetector environmentDetector = context.getBean(EnvironmentDetector.class);
    Assert.assertEquals(expectedEnvironmentType, environmentDetector.getEnvironmentType());
    context.close();
    return environmentDetector;
  }

  /**
   * Tests {@link EnvironmentDetector} for {@link EnvironmentDetector#ENVIRONMENT_TYPE_ACCEPTANCE}.
   */
  @Test
  public void testEnvironmentAcceptance() {

    checkEnvironmentDetector(EnvironmentDetector.ENVIRONMENT_TYPE_ACCEPTANCE, "acceptance");
    checkEnvironmentDetector(EnvironmentDetector.ENVIRONMENT_TYPE_ACCEPTANCE, "acceptanceTest");
    checkEnvironmentDetector(EnvironmentDetector.ENVIRONMENT_TYPE_ACCEPTANCE, "foo", "accept4", "bar");
  }

  /**
   * Tests {@link EnvironmentDetector} for {@link EnvironmentDetector#ENVIRONMENT_TYPE_TEST}.
   */
  @Test
  public void testEnvironmentTest() {

    checkEnvironmentDetector(EnvironmentDetector.ENVIRONMENT_TYPE_TEST, "test");
    checkEnvironmentDetector(EnvironmentDetector.ENVIRONMENT_TYPE_TEST, "foo", "testEnvironment4", "bar");
  }

  /**
   * Tests {@link EnvironmentDetector} for <em>staging</em> environment.
   */
  @Test
  public void testEnvironmentStaging() {

    checkEnvironmentDetector("staging", "staging");
    checkEnvironmentDetector("staging", "foo", "stagingEnvironment4", "bar");
  }

  /**
   * Tests {@link EnvironmentDetector} for {@link EnvironmentDetector#ENVIRONMENT_TYPE_DEVELOPMENT}.
   */
  @Test
  public void testEnvironmentDevelopment() {

    checkEnvironmentDetector(EnvironmentDetector.ENVIRONMENT_TYPE_DEVELOPMENT);
    checkEnvironmentDetector(EnvironmentDetector.ENVIRONMENT_TYPE_DEVELOPMENT, "dev");
    checkEnvironmentDetector(EnvironmentDetector.ENVIRONMENT_TYPE_DEVELOPMENT, "development");
    checkEnvironmentDetector(EnvironmentDetector.ENVIRONMENT_TYPE_DEVELOPMENT, "foo", "dev1", "bar");
  }

  /**
   * Tests {@link EnvironmentDetector} for {@link EnvironmentDetector#ENVIRONMENT_TYPE_PRODUCTION}.
   */
  @Test
  public void testEnvironmentProduction() {

    checkEnvironmentDetector(EnvironmentDetector.ENVIRONMENT_TYPE_PRODUCTION, "production");
    checkEnvironmentDetector(EnvironmentDetector.ENVIRONMENT_TYPE_PRODUCTION, "prod");
    checkEnvironmentDetector(EnvironmentDetector.ENVIRONMENT_TYPE_PRODUCTION, "foo", "prod1", "bar");
  }

  /**
   * Tests {@link EnvironmentDetector} for {@link EnvironmentDetector#ENVIRONMENT_TYPE_PRODUCTION}.
   */
  @Test
  public void testEnvironmentIllegalProfiles() {

    try {
      checkEnvironmentDetector(EnvironmentDetector.ENVIRONMENT_TYPE_PRODUCTION, "dev", "prod");
      ExceptionHelper.failExceptionExpected();
    } catch (Exception e) {
      ExceptionHelper.assertCause(e, IllegalStateException.class);
    }
  }

}
