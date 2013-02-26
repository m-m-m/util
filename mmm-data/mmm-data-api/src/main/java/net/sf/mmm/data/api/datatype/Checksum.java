/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.api.datatype;

/**
 * This is the interface for the checksum(s) of files.
 * 
 * @see net.sf.mmm.data.api.entity.resource.DataFile#getChecksum()
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface Checksum {

  /** The {@link #getChecksum(String) key} for the checksum of type {@value} . */
  String KEY_MD5 = "MD5";

  /** The {@link #getChecksum(String) key} for the checksum of type {@value} . */
  String KEY_SHA1 = "SHA-1";

  /**
   * This method gets the checksum of the type identified by the given <code>key</code>.
   * 
   * @param key identifies the type of the checksum to get.
   * @return the requested checksum or <code>null</code> if not defined.
   */
  String getChecksum(String key);

}
