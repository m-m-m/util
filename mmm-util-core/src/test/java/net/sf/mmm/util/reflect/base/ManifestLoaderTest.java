/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.base;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import junit.framework.TestCase;

import org.junit.Test;

/**
 * This is the {@link TestCase} for {@link ManifestLoader}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class ManifestLoaderTest {

  /**
   * Tests the content of the manifest from the servlet-api that is added as test-dependency especially for
   * this test.
   *
   * @throws IOException on error.
   */
  @Test
  public void testLoader() throws IOException {

    ManifestLoader loader = new ManifestLoader();
    List<Manifest> manifests = loader.getManifests();
    assertNotNull(manifests);
    assertTrue(manifests.size() > 0);
    Manifest slf4jApiManifest = null;
    for (Manifest manifest : manifests) {
      String implementationTitle = ManifestLoader.getValue(manifest, Attributes.Name.IMPLEMENTATION_TITLE);
      if ("slf4j-api".equals(implementationTitle)) {
        slf4jApiManifest = manifest;
      }
    }
    assertNotNull(slf4jApiManifest);

    Attributes mainAttributes = slf4jApiManifest.getMainAttributes();
    assertEquals("1.0", mainAttributes.getValue(Attributes.Name.MANIFEST_VERSION));
    assertEquals("1.7.6", mainAttributes.getValue(Attributes.Name.IMPLEMENTATION_VERSION));
    assertEquals("Apache Maven", mainAttributes.getValue("Created-By"));
    assertEquals("slf4j-api-1.7.6.jar", mainAttributes.getValue(ManifestLoader.MANIFEST_SOURCE));
  }
}
