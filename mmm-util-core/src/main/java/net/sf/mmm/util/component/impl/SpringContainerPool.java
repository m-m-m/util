/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.component.impl;

import java.util.HashMap;
import java.util.Map;

import net.sf.mmm.util.component.api.IocContainer;
import net.sf.mmm.util.component.base.SpringConfigs;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * This is just an ugly static pool used to simplify testing. It might cause memory holes and should never be used in
 * productive code.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public final class SpringContainerPool {

  /** @see #getInstance(String) */
  private static Map<String, SpringContainer> xml2containerMap;

  /**
   * The constructor.
   */
  private SpringContainerPool() {

    super();
  }

  /**
   * This method gets the singleton instance of the {@link IocContainer}.
   *
   * @return the requested container.
   */
  public static IocContainer getInstance() {

    return getInstance(SpringConfigs.SPRING_XML_UTIL_CORE);
  }

  /**
   * This method disposes the {@link #getInstance() singleton-instance} (if it exists).
   */
  public static void dispose() {

    if (xml2containerMap != null) {
      for (SpringContainer container : xml2containerMap.values()) {
        container.dispose();
      }
      xml2containerMap.clear();
    }
  }

  /**
   * This method gets the {@link IocContainer} for the given <code>xmlClasspath</code>.
   *
   * @param xmlClasspath is the classpath to the XML configuration.
   * @return the requested container.
   */
  public static IocContainer getInstance(String xmlClasspath) {

    if (xml2containerMap == null) {
      xml2containerMap = new HashMap<>();
    }
    SpringContainer container = xml2containerMap.get(xmlClasspath);
    if (container == null) {
      container = new SpringContainer(new ClassPathXmlApplicationContext(xmlClasspath));
      xml2containerMap.put(xmlClasspath, container);
    }
    return container;
  }

  /**
   * This method disposes the {@link #getInstance(String) instance} identified by the given <code>xmlClasspath</code>
   * (if it exists).
   *
   * @param xmlClasspath is the classpath to the XML configuration.
   */
  public static void dispose(String xmlClasspath) {

    if (xml2containerMap != null) {
      SpringContainer container = xml2containerMap.get(xmlClasspath);
      if (container != null) {
        xml2containerMap.remove(xmlClasspath);
        container.dispose();
      }
    }
  }

}
