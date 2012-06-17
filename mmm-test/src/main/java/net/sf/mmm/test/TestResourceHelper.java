/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.test;

/**
 * This is a utility-class with static methods that help to write unit-tests
 * that read test-resources from filesystem or classpath.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public final class TestResourceHelper {

  /** The path to the base-dir of test-resources of the current module. */
  public static final String TEST_RESOURCES_PATH = "src/test/resources";

  /**
   * Construction forbidden.
   */
  private TestResourceHelper() {

  }

  /**
   * This method gets the filesystem path to some test-resource named after the
   * given <code>testClass</code>.
   * 
   * @param testClass is the {@link Class} reflecting the path to the
   *        test-resource.
   * @param suffix is the suffix to append after the <code>testClass</code>
   *        (e.g. ".xml").
   * @return the filesystem path to the test-resource.
   */
  public static String getTestPath(Class<?> testClass, String suffix) {

    return TEST_RESOURCES_PATH + "/" + testClass.getName().replace('.', '/') + suffix;
  }

  /**
   * This method gets the filesystem path to some test-resource located in the
   * given <code>testPackage</code>.
   * 
   * @param testPackage is the {@link Package} reflecting the path to the
   *        test-resource.
   * @param suffix is the suffix to append after the <code>testPackage</code>
   *        (e.g. ".xml").
   * @return the filesystem path to the test-resource.
   */
  public static String getTestPath(Package testPackage, String suffix) {

    return TEST_RESOURCES_PATH + "/" + testPackage.getName().replace('.', '/') + suffix;
  }

}
