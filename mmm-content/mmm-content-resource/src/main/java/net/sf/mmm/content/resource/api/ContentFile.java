/* $ Id: ContentFileIF.java $ */
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

  /**
   * This method gets the BLOB (Binary Larget OBject) representing the data of
   * this file.
   * 
   * @return the BLOB.
   */
  MutableBlob getBlob();

}
