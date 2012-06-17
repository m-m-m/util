/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.base;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
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

    // URL url =
    // Thread.currentThread().getContextClassLoader().getResource(JarFile.MANIFEST_NAME);
    // URLConnection connection = url.openConnection();
    // if (connection instanceof JarURLConnection) {
    // JarURLConnection jarConnection = (JarURLConnection) connection;
    // java.util.jar.Manifest mf = jarConnection.getJarFile().getManifest();
    // for (Map.Entry<Object, Object> entry : mf.getMainAttributes().entrySet())
    // {
    // System.out.println(entry.getKey() + "=" + entry.getValue());
    // }
    // }

    ManifestLoader loader = new ManifestLoader();
    List<Manifest> manifests = loader.getManifests();
    assertNotNull(manifests);
    assertTrue(manifests.size() > 0);
    Manifest servletManifest = null;
    for (Manifest manifest : manifests) {
      if ("javax.servlet".equals(ManifestLoader.getValue(manifest, Attributes.Name.IMPLEMENTATION_TITLE))) {
        // if (manifest.getAttributes("javax/servlet/") != null) {
        servletManifest = manifest;
      }
    }
    assertNotNull(servletManifest);

    Attributes mainAttributes = servletManifest.getMainAttributes();
    assertEquals("1.0", mainAttributes.getValue(Attributes.Name.MANIFEST_VERSION));
    assertEquals("Apache Ant 1.6.2", mainAttributes.getValue("Ant-Version"));
    assertEquals("1.4.2_06-b03 (Sun Microsystems Inc.)", mainAttributes.getValue("Created-By"));
    assertEquals("servlet-api-2.4.jar", mainAttributes.getValue(ManifestLoader.MANIFEST_SOURCE));

    Attributes servletAttributes = servletManifest.getAttributes("javax/servlet/");
    assertEquals("Java API for Servlets", servletAttributes.getValue(Attributes.Name.SPECIFICATION_TITLE));
    assertEquals("Sun Microsystems, Inc.", servletAttributes.getValue(Attributes.Name.SPECIFICATION_VENDOR));
    assertEquals("2.4", servletAttributes.getValue(Attributes.Name.SPECIFICATION_VERSION));

    assertEquals("javax.servlet", servletAttributes.getValue(Attributes.Name.IMPLEMENTATION_TITLE));
    assertEquals("2.4.public_draft", servletAttributes.getValue(Attributes.Name.IMPLEMENTATION_VERSION));
    assertEquals("Apache Software Foundation", servletAttributes.getValue(Attributes.Name.IMPLEMENTATION_VENDOR));
    assertNull(servletAttributes.getValue(Attributes.Name.IMPLEMENTATION_VENDOR_ID));
  }
}
