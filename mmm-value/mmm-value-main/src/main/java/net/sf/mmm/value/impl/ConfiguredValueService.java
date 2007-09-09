/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.value.impl;

import java.io.InputStream;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;

import net.sf.mmm.util.resource.ClasspathResource;
import net.sf.mmm.value.NlsBundleValueMain;
import net.sf.mmm.value.api.ValueException;
import net.sf.mmm.value.api.ValueManager;

/**
 * This is an implementation of the {@link net.sf.mmm.value.api.ValueService}
 * that is configured via XML. The XML configuration is loaded from
 * <code>net.sf.mmm.value.ValueService.xml</code>.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ConfiguredValueService extends ValueServiceImpl {

  /**
   * The constructor.
   * 
   * @throws Exception if an error occurred when configuring the service.
   */
  public ConfiguredValueService() throws Exception {

    super();
    XMLInputFactory factory = XMLInputFactory.newInstance();
    ClasspathResource resource = new ClasspathResource(NlsBundleValueMain.class.getPackage(),
        "ValueService.xml");
    InputStream in = resource.openStream();
    try {
      XMLStreamReader xmlReader = factory.createXMLStreamReader(in);
      int eventType = xmlReader.nextTag();
      assert (eventType == XMLStreamConstants.START_ELEMENT);
      assert ("ValueService".equals(xmlReader.getLocalName()));
      eventType = xmlReader.nextTag();
      while (eventType == XMLStreamConstants.START_ELEMENT) {
        if (!"value".equals(xmlReader.getLocalName())) {
          // TODO: NLS
          throw new ValueException("Illegal tag \"{0}\" - expected 'value'!", xmlReader
              .getLocalName());
        }
        String manager = xmlReader.getAttributeValue(null, "manager");
        if (manager != null) {
          try {
            Class managerClass = Class.forName(manager);
            if (ValueManager.class.isAssignableFrom(managerClass)) {
              ValueManager managerInstance = (ValueManager) managerClass.newInstance();
              addValueManager(managerInstance);
            } else {
              // TODO: NLS
              throw new ValueException(
                  "Value-manager \"{0}\" does NOT implement ValueManager interface!", manager);
            }
          } catch (ClassNotFoundException e) {
            // TODO: NLS
            throw new ValueException(e, "Value-manager \"{0}\" not found!", manager);
          }
        } else {
          String type = xmlReader.getAttributeValue(null, "class");
          try {
            Class typeClass = Class.forName(type);
            addValueType(typeClass);
          } catch (ClassNotFoundException e) {
            // TODO: NLS
            throw new ValueException(e, "Value-class \"{0}\" not found!", type);
          }
        }
        eventType = xmlReader.nextTag();
        if (eventType == XMLStreamConstants.END_ELEMENT) {
          eventType = xmlReader.nextTag();
        } else {
          // TODO: NLS
          throw new ValueException("value tag has to be empty!");
        }
      }
      xmlReader.close();
    } finally {
      in.close();
    }
  }

}
