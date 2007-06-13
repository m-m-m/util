/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.configuration.base;

import org.junit.Test;

import net.sf.mmm.configuration.api.Configuration;
import net.sf.mmm.configuration.api.ConfigurationDocument;
import net.sf.mmm.configuration.api.MutableConfiguration;
import net.sf.mmm.configuration.api.access.ConfigurationAccess;
import net.sf.mmm.configuration.base.access.ConfigurationFactory;
import net.sf.mmm.configuration.impl.access.resource.ResourceAccess;
import net.sf.mmm.configuration.impl.access.url.UrlAccess;
import net.sf.mmm.configuration.impl.format.xml.dom.XmlFactory;
import net.sf.mmm.util.resource.ClasspathResource;

import junit.framework.TestCase;

/**
 * This is the {@link TestCase} for immutable configurations.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class ImmutableConfigurationTest extends TestCase {

  @Test
  public void testImmutableConfiguration() {

    ClasspathResource resource = new ClasspathResource(ImmutableConfigurationTest.class, ".xml",
        true);
    ConfigurationAccess access = new UrlAccess(resource.getUrl());
    ConfigurationFactory factory = new XmlFactory();
    ConfigurationDocument doc = factory.create(access);
    assertTrue(doc.isImmutable());
    MutableConfiguration config = doc.getConfiguration();
    assertFalse(config.isAddDefaults());
    assertFalse(config.isEditable());
    assertEquals(42, config.getDescendant("child/@attribute").getValue().getInteger());
    assertEquals("EMPTY", config.getDescendant("child[@foo='bar']").getName());
    assertEquals("EMPTY", config.getDescendant("foo/bar[ding/@dong]").getName());
    assertEquals("@EMPTY", config.getDescendant("foo/@bar").getName());
  }

}
