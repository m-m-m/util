/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.configuration.base;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import net.sf.mmm.configuration.api.Configuration;
import net.sf.mmm.configuration.api.ConfigurationDocument;
import net.sf.mmm.configuration.api.MutableConfiguration;
import net.sf.mmm.configuration.api.access.ConfigurationAccess;
import net.sf.mmm.configuration.api.event.ConfigurationChangeEvent;
import net.sf.mmm.configuration.api.event.ConfigurationChangeListener;
import net.sf.mmm.configuration.base.access.ConfigurationFactory;
import net.sf.mmm.configuration.impl.access.resource.ResourceAccess;
import net.sf.mmm.configuration.impl.format.xml.dom.XmlConfigurationFormatTest;
import net.sf.mmm.configuration.impl.format.xml.dom.XmlFactory;
import net.sf.mmm.util.event.ChangeEvent;

import junit.framework.TestCase;

/**
 * This is the {@link TestCase} for the {@link ConfigurationChangeEvent event}
 * support of the
 * {@link net.sf.mmm.configuration.api.Configuration configuration}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class ConfigurationEventTest extends TestCase {

  @Test
  public void testEvents() {

    String href = XmlConfigurationFormatTest.class.getName().replace('.', '/') + ".xml";
    ConfigurationAccess access = new ResourceAccess(href);
    ConfigurationFactory factory = new XmlFactory();
    ConfigurationDocument doc = factory.create(access);
    MutableConfiguration config = doc.getConfiguration();
    Listener listener = new Listener();
    config.addListener(listener);
    config.getDescendant("server/@port");
    assertEquals(0, listener.eventQueue.size());
    MutableConfiguration barAttr = config.getDescendant("foo/@bar");
    assertEquals(2, listener.eventQueue.size());
    // check first event
    ConfigurationChangeEvent event = listener.eventQueue.get(0);
    assertEquals(ChangeEvent.Type.ADD, event.getType());
    assertEquals(config.getName(), event.getTopCause().getName());
    assertEquals("foo", event.getCause().getName());
    // check second event
    event = listener.eventQueue.get(1);
    assertEquals(ChangeEvent.Type.ADD, event.getType());
    assertEquals("foo", event.getTopCause().getName());
    assertEquals("@bar", event.getCause().getName());
    listener.eventQueue.clear();
    // update event
    barAttr.getValue().setString("hello");
    assertEquals(1, listener.eventQueue.size());
    event = listener.eventQueue.get(0);
    assertEquals(ChangeEvent.Type.UPDATE, event.getType());
    assertSame(barAttr, event.getTopCause());
    assertEquals(barAttr, event.getCause());
    listener.eventQueue.clear();
    // remove event
    barAttr.remove();
    assertEquals(1, listener.eventQueue.size());
    event = listener.eventQueue.get(0);
    assertEquals(ChangeEvent.Type.REMOVE, event.getType());
    assertEquals("foo", event.getTopCause().getName());
    assertEquals(barAttr, event.getCause());
    listener.eventQueue.clear();
  }

  private static class Listener implements ConfigurationChangeListener {

    private List<ConfigurationChangeEvent> eventQueue;

    public Listener() {

      super();
      this.eventQueue = new ArrayList<ConfigurationChangeEvent>();
    }

    /**
     * {@inheritDoc}
     */
    public void handleEvent(ConfigurationChangeEvent event) {

      this.eventQueue.add(event);
    }

  }

}
