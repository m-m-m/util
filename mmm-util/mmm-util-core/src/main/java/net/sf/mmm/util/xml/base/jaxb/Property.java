/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.xml.base.jaxb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

/**
 * This class represents a simple {@link java.util.Properties#getProperty(String) property} as JAXB bean.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
@XmlRootElement(name = "property")
@XmlAccessorType(XmlAccessType.FIELD)
public class Property {

  /** @see #getKey() */
  @XmlAttribute(name = "key")
  private String key;

  /** @see #getValue() */
  @XmlValue
  private String value;

  /**
   * The constructor.
   */
  public Property() {

    super();
  }

  /**
   * The constructor.
   * 
   * @param key is the {@link #getKey() key}.
   * @param value is the {@link #getValue() value}.
   */
  public Property(String key, String value) {

    super();
    this.key = key;
    this.value = value;
  }

  /**
   * This method gets the {@link String}.
   * 
   * @return the {@link String}.
   */
  public String getKey() {

    return this.key;
  }

  /**
   * @param key is the key to set
   */
  public void setKey(String key) {

    this.key = key;
  }

  /**
   * This method gets the {@link String}.
   * 
   * @return the {@link String}.
   */
  public String getValue() {

    return this.value;
  }

  /**
   * @param value is the value to set
   */
  public void setValue(String value) {

    this.value = value;
  }

}
