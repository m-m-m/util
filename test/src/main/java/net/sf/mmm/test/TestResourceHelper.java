/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.test;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is a utility-class with static methods that help to write unit-tests that read test-resources from
 * filesystem or classpath.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public final class TestResourceHelper {

  /** The {@link Logger} to use. */
  private static final Logger LOGGER = LoggerFactory.getLogger(TestResourceHelper.class);

  /** The path to the base-dir of test-resources of the current module. */
  public static final String TEST_RESOURCES_PATH = "src/test/resources/";

  private  static String testPath;

  /**
   * Construction forbidden.
   */
  private TestResourceHelper() {

  }

  /**
   * This method gets the filesystem path to test-resource of the current project (maven module).
   *
   * @return the path to the test resources.
   */
  public static String getTestPath() {

    if (testPath == null) {
      File testPathFile = new File(TEST_RESOURCES_PATH);
      if (!testPathFile.isDirectory()) {
        // workaround for maven-surefire "bug" with working directory...
        File basedir = new File("dummy").getAbsoluteFile().getParentFile().getParentFile();
        testPathFile = new File(basedir, TEST_RESOURCES_PATH);
        if (!testPathFile.isDirectory()) {
          LOGGER.warn("Could not find directory {}", TEST_RESOURCES_PATH);
        }
      }
      testPath = testPathFile.getAbsolutePath() + "/";
      LOGGER.debug("Initialized test resources directory to {}", testPath);
    }
    return testPath;
  }

  /**
   * This method gets the filesystem path to test-resource with the given {@code relativePath}.
   *
   * @param relativePath is the path relative to the {@link #getTestPath() test resources path}.
   * @return the path to the test resources.
   */
  public static String getTestPath(String relativePath) {

    return getTestPath() + relativePath;
  }

  /**
   * This method gets the filesystem path to some test-resource named after the given {@code testClass}.
   *
   * @param testClass is the {@link Class} reflecting the path to the test-resource.
   * @param suffix is the suffix to append after the {@code testClass} (e.g. ".xml").
   * @return the filesystem path to the test-resource.
   */
  public static String getTestPath(Class<?> testClass, String suffix) {

    return getTestPath() + testClass.getName().replace('.', '/') + suffix;
  }

  /**
   * This method gets the filesystem path to some test-resource located in the given {@code testPackage}.
   *
   * @param testPackage is the {@link Package} reflecting the path to the test-resource.
   * @param suffix is the suffix to append after the {@code testPackage} (e.g. ".xml").
   * @return the filesystem path to the test-resource.
   */
  public static String getTestPath(Package testPackage, String suffix) {

    return getTestPath() + testPackage.getName().replace('.', '/') + suffix;
  }

}
