/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.resource.base;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import net.sf.mmm.util.resource.api.DataResource;

/**
 * This is the test-case for the class {@link ClasspathUtil}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class ClasspathResourceTest extends Assertions {

  public static void verifyResource(DataResource resource) throws Exception {

    assertThat(resource.isAvailable()).isTrue();
    assertThat(resource.getSize()).isEqualTo(41);
    InputStream in = in = resource.openStream();
    try {
      BufferedReader reader = new BufferedReader(new InputStreamReader(in));
      String line = reader.readLine();
      assertThat(line).isEqualTo("This is a resource loaded from classpath.");
      line = reader.readLine();
      assertThat(line).isNull();
    } finally {
      in.close();
    }
  }

  @Test
  public void testClasspathResource() throws Exception {

    ClasspathResource resource = new ClasspathResource(ClasspathResource.class, ".txt", true);
    verifyResource(resource);
    verifyResource(new ClasspathResource(ClasspathResource.class, //
        ClasspathResource.class.getSimpleName() + ".txt", false));
    verifyResource(new ClasspathResource(ClasspathResource.class.getPackage(), ClasspathResource.class.getSimpleName() + ".txt"));
    String name = ClasspathResource.class.getSimpleName() + ".txt";
    String absoluteClasspath = ClasspathResource.class.getPackage().getName().replace('.', '/') + "/" + name;
    verifyResource(new ClasspathResource(absoluteClasspath));
    assertThat(resource.getName()).isEqualTo(name);
    assertThat(resource.getPath()).isEqualTo(absoluteClasspath);
    assertThat(resource.getUri()).isEqualTo(ClasspathResource.SCHEME_PREFIX + absoluteClasspath);
  }
}
