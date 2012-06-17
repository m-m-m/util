/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.base.datatype;

import java.io.FileInputStream;
import java.io.InputStream;
import java.security.DigestInputStream;
import java.security.MessageDigest;

import javax.persistence.Embeddable;
import javax.persistence.Transient;

import net.sf.mmm.data.api.datatype.Checksum;

/**
 * This is the implementation of {@link Checksum}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@Embeddable
public class ChecksumImpl implements Checksum {

  /** @see #getChecksumSha1() */
  private String checksumSha1;

  /** @see #getChecksumMd5() */
  private String checksumMd5;

  /**
   * The constructor.
   */
  public ChecksumImpl() {

    super();
  }

  private static void example() throws Exception {

    MessageDigest md = MessageDigest.getInstance("MD5");
    InputStream is = new FileInputStream("file.txt");
    try {
      is = new DigestInputStream(is, md);
      // read stream to EOF as normal...
    } finally {
      is.close();
    }
    byte[] digest = md.digest();
    StringBuilder buffer = new StringBuilder(digest.length * 2);
    for (byte b : digest) {
      buffer.append(Integer.toString(b, 16));
    }
    String md5 = buffer.toString();
  }

  /**
   * {@inheritDoc}
   */
  @Transient
  public String getChecksum(String key) {

    if (KEY_MD5.equals(key)) {
      return this.checksumMd5;
    } else if (KEY_SHA1.equals(key)) {
      return this.checksumSha1;
    } else {
      return null;
    }
  }

  /**
   * @return the sha1
   */
  public String getChecksumSha1() {

    return this.checksumSha1;
  }

  /**
   * @param sha1 is the sha1 to set
   */
  public void setChecksumSha1(String sha1) {

    this.checksumSha1 = sha1;
  }

  /**
   * @return the md5
   */
  public String getChecksumMd5() {

    return this.checksumMd5;
  }

  /**
   * @param md5 is the md5 to set
   */
  public void setChecksumMd5(String md5) {

    this.checksumMd5 = md5;
  }

}
