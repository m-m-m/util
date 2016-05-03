/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.xml.base.jaxb;

import java.util.List;
import java.util.Properties;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import net.sf.mmm.util.exception.api.NlsClassCastException;

/**
 * This is an implementation of {@link XmlAdapter} for mapping {@link Properties}. It uses a
 * {@link PropertyList} as JAXB mappable object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public class XmlAdapterProperties extends XmlAdapter<PropertyList, Properties> {

  /**
   * The constructor.
   */
  public XmlAdapterProperties() {

    super();
  }

  @Override
  public PropertyList marshal(Properties properties) throws Exception {

    PropertyList result = null;
    if ((properties != null) && (!properties.isEmpty())) {
      result = new PropertyList();
      List<Property> propertyList = result.getPropertyList();
      for (Object keyObject : properties.keySet()) {
        String key;
        try {
          key = (String) keyObject;
        } catch (ClassCastException e) {
          throw new NlsClassCastException(keyObject, String.class);
        }
        String value = properties.getProperty(key);
        Property property = new Property(key, value);
        propertyList.add(property);
      }
    }
    return result;
  }

  @Override
  public Properties unmarshal(PropertyList propertyList) throws Exception {

    Properties result = new Properties();
    if (propertyList != null) {
      List<Property> list = propertyList.getPropertyList();
      if (list != null) {
        for (Property property : list) {
          result.setProperty(property.getKey(), property.getValue());
        }
      }
    }
    return result;
  }

}
