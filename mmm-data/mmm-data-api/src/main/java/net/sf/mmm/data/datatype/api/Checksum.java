/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.datatype.api;

/**
 * This is the interface for the checksum(s) of files.
 * 
 * @see net.sf.mmm.data.entity.api.ContentFile#getChecksum()
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface Checksum {

  String KEY_MD5 = "MD5";

  String KEY_SHA1 = "SHA-1";

  String getChecksum(String key);

}
