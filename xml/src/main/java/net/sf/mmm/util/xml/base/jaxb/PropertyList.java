/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.xml.base.jaxb;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * This class represents {@link java.util.Properties} as JAXB bean.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class PropertyList {

  @XmlElement(name = "property")
  private List<Property> propertyList;

  /**
   * The constructor.
   */
  public PropertyList() {

    super();
    this.propertyList = new ArrayList<>();
  }

  /**
   * This method gets the {@link List} of {@link Property properties}.
   *
   * @return the the {@link List} of {@link Property properties}.
   */
  public List<Property> getPropertyList() {

    return this.propertyList;
  }

  /**
   * @param propertyList is the propertyList to set
   */
  public void setPropertyList(List<Property> propertyList) {

    this.propertyList = propertyList;
  }

}
