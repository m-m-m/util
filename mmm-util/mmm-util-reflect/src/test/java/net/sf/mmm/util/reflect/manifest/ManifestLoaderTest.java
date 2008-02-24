/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.manifest;

import java.io.IOException;
import java.util.List;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * This is the {@link TestCase} for {@link ManifestLoader}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class ManifestLoaderTest {

  @Test
  public void testLoader() throws IOException {

    ManifestLoader loader = new ManifestLoader();
    List<Manifest> manifests = loader.getManifests();
    assertNotNull(manifests);
    assertTrue(manifests.size() > 0);
    Manifest log4jManifest = null;
    for (Manifest manifest : manifests) {
      if ("log4j".equals(manifest.getImplementationTitle())) {
        log4jManifest = manifest;
      }
    }
    assertNotNull(log4jManifest);
    assertEquals("1.2.14", log4jManifest.getImplementationVersion());
    assertEquals("\"Apache Software Foundation\"", log4jManifest.getImplementationVendor());
    assertEquals("1.0", log4jManifest.getManifestVersion());
  }

}
