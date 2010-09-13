/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.base;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;

import junit.framework.TestCase;
import net.sf.mmm.util.reflect.api.Manifest;

import org.junit.Test;

/**
 * This is the {@link TestCase} for {@link ManifestLoader}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class ManifestLoaderTest {

  /**
   * Tests the content of the manifest from the servlet-api that is added as
   * test-dependency especially for this test.
   * 
   * @throws IOException on error.
   */
  @Test
  public void testLoader() throws IOException {

    ManifestLoader loader = new ManifestLoader();
    List<Manifest> manifests = loader.getManifests();
    assertNotNull(manifests);
    assertTrue(manifests.size() > 0);
    Manifest servletManifest = null;
    for (Manifest manifest : manifests) {
      if ("javax.servlet".equals(manifest.getImplementationTitle())) {
        servletManifest = manifest;
      }
    }
    assertNotNull(servletManifest);
    assertEquals("1.0", servletManifest.getManifestVersion());

    assertEquals("Java API for Servlets", servletManifest.getSpecificationTitle());
    assertEquals("Sun Microsystems, Inc.", servletManifest.getSpecificationVendor());
    assertEquals("2.4", servletManifest.getSpecificationVersion());

    assertEquals("javax.servlet", servletManifest.getImplementationTitle());
    assertEquals("2.4.public_draft", servletManifest.getImplementationVersion());
    assertEquals("Apache Software Foundation", servletManifest.getImplementationVendor());
    assertNull(servletManifest.getImplementationVendorId());
    assertEquals("Apache Ant 1.6.2", servletManifest.getProperties().get("Ant-Version"));
    assertEquals("servlet-api-2.4.jar", servletManifest.getManifestSource());
  }

}
