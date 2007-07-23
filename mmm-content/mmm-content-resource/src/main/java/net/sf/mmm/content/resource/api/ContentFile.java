/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.resource.api;

import net.sf.mmm.content.value.api.MutableBlob;

/**
 * This is the interface for a resource that contains raw data. It is like a
 * file in the local filesystem.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface ContentFile extends ContentResource {

  /**
   * the name of the class reflecting {@link ContentFolder}.
   */
  String CLASS_NAME = "File";

  /** the id of the {@link #getContentClass() class} reflecting this type. */
  short CLASS_ID = 22;

  /**
   * This method gets the BLOB (Binary Large OBject) representing the data of
   * this file.
   * 
   * @return the BLOB.
   */
  MutableBlob getBlob();

}
