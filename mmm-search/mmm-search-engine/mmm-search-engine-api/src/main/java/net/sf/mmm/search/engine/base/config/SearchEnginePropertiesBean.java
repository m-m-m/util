/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.engine.base.config;

import java.util.Properties;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import net.sf.mmm.search.engine.api.config.SearchEngineProperties;
import net.sf.mmm.util.xml.base.jaxb.XmlAdapterProperties;

/**
 * This is the implementation of {@link SearchEngineProperties} as simple java
 * bean.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class SearchEnginePropertiesBean implements SearchEngineProperties {

  /** @see #getProperty(String) */
  @XmlElement(name = "properties")
  @XmlJavaTypeAdapter(value = XmlAdapterProperties.class)
  private Properties properties;

  /**
   * The constructor.
   */
  public SearchEnginePropertiesBean() {

    super();
    this.properties = new Properties();
  }

  /**
   * {@inheritDoc}
   */
  public String getProperty(String key) {

    return this.properties.getProperty(key);
  }

  /**
   * This method sets the property associated with <code>key</code> to the given
   * <code>value</code>.
   * 
   * @see #getProperty(String)
   * 
   * @param key is the key of the property to set.
   * @param value is the value of the property to set.
   */
  public void setProperty(String key, String value) {

    this.properties.setProperty(key, value);
  }
}
