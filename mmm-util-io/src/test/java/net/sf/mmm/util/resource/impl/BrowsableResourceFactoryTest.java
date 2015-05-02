/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.resource.impl;

import java.util.Date;

import net.sf.mmm.test.ExceptionHelper;
import net.sf.mmm.util.resource.api.BrowsableResource;
import net.sf.mmm.util.resource.api.BrowsableResourceFactory;
import net.sf.mmm.util.resource.api.DataResource;
import net.sf.mmm.util.resource.api.DataResourceFactory;
import net.sf.mmm.util.resource.api.ResourceNotAvailableException;

import org.assertj.core.api.Assertions;
import org.junit.Test;

/**
 * This is the test-case for the class {@link DataResourceFactory}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class BrowsableResourceFactoryTest extends Assertions {

  /**
   * This method gets the {@link BrowsableResourceFactory} instance to test.
   *
   * @return the {@link BrowsableResourceFactory}.
   */
  public BrowsableResourceFactory getBrowsableResourceFactory() {

    BrowsableResourceFactoryImpl impl = new BrowsableResourceFactoryImpl();
    impl.initialize();
    return impl;
  }

  @Test
  public void testFileResource() throws Exception {

    String filename = "non-existent-file";
    String path = "/" + filename;
    String resourceUri = "file://" + path;
    BrowsableResource resource = getBrowsableResourceFactory().createBrowsableResource(resourceUri);
    assertThat(resource.getName()).isEqualTo(filename);
    // will not work on windows by intention - see javadoc...
    // assertEquals(path, resource.getPath());
    // assertEquals(resourceUri, resource.getUri());
    assertThat(resource.isAvailable()).isFalse();
    assertThat(resource.isFolder()).isFalse();
    assertThat(resource.isModifiedSince(new Date())).isNull();
    try {
      resource.getSize();
      ExceptionHelper.failExceptionExpected();
    } catch (ResourceNotAvailableException e) {
      // OK
      assertThat(e).hasMessageContaining(filename);
    }
  }

  @Test
  public void testUrlResource() throws Exception {

    String filename = "page.html";
    String path = "/folder/subfolder/" + filename;
    String host = "foo.bar.xyz";
    String resourceUri = "http://" + host + path;
    DataResource resource = getBrowsableResourceFactory().createDataResource(resourceUri);
    assertThat(resource.getName()).isEqualTo(filename);
    assertThat(resource.getPath()).isEqualTo(path);
    assertThat(resource.getUri()).isEqualTo(resourceUri);
    assertThat(resource.isAvailable()).isFalse();
  }

  @Test
  public void testClasspathResource() throws Exception {

    BrowsableResourceFactory factory = getBrowsableResourceFactory();
    DataResource resource = factory.createDataResource("file:///test");
    assertThat(resource.getName()).isEqualTo("test");
    resource = resource.navigate("foo/bar.txt");
    assertThat(resource.getName()).isEqualTo("bar.txt");
    resource = resource.navigate("classpath:META-INF/maven/org.slf4j/slf4j-api/pom.properties");
    assertThat(resource.isAvailable()).isTrue();
    assertThat(resource.getSize()).isEqualTo(108);
  }
}
