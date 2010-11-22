/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.io.api;

import java.io.Reader;

/**
 * This class represents a {@link Reader} that automatically detects an encoding
 * while reading the data. Beside being a regular {@link Reader} this class acts
 * as API that additionally allows to {@link #getEncoding() get} the detected
 * encoding in case you need this information (e.g. to save it as meta-data).
 * 
 * @see EncodingUtil#createUtfDetectionReader(java.io.InputStream, String)
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.1
 */
public abstract class EncodingDetectionReader extends Reader {

  /**
   * This method gets the encoding if already detected. The detection may last
   * until the end of this {@link Reader} has been reached.
   * 
   * @return the encoding or <code>null</code> if NOT yet detected (then
   *         typically
   *         {@link net.sf.mmm.util.io.base.EncodingUtilImpl#ENCODING_US_ASCII
   *         ASCII} is assumed so far).
   */
  public abstract String getEncoding();

}
