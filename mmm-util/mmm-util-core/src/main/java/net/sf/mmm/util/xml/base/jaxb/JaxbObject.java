/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.xml.base.jaxb;

import javax.xml.bind.Unmarshaller;

/**
 * This is the interface for a JAXB serializable object that needs to
 * {@link #afterUnmarshal(Unmarshaller, Object) perform custom initialization logic after un-marshaling}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.0.0
 */
public interface JaxbObject {

  /**
   * This method is automatically invoked by JAXB after un-marshaling this bean. It allows to link the parent
   * object.
   * 
   * @param unmarshaller is the {@link Unmarshaller}.
   * @param parent is the java-object representing the parent node in XML.
   */
  void afterUnmarshal(Unmarshaller unmarshaller, Object parent);

}
