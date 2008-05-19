/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.io.api;

import net.sf.mmm.util.io.EncodingUtil;

/**
 * This type represents a Byte-Order-Mark (<a
 * href="http://www.unicode.org/unicode/faq/utf_bom.html#BOM">BOM</a>) of an
 * Unicode-Transformation-Format (UTF).
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public enum ByteOrderMark {

  /**
   * The {@link ByteOrderMark} for {@link EncodingUtil#ENCODING_UTF_8 UTF-8}:<br>
   * <code>0xef 0xbb 0xbf</code>
   */
  UTF_8() {

    /**
     * {@inheritDoc}
     */
    @Override
    public String getEncoding() {

      return EncodingUtil.ENCODING_UTF_8;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getLength() {

      return 3;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean checkPresent(byte[] bytes, int offset) {

      if ((bytes[offset] == 0xef) && (bytes[offset + 1] == 0xbb) && (bytes[offset + 2] == 0xbf)) {
        return true;
      }
      return false;
    }

  },

  /**
   * The {@link ByteOrderMark} for
   * {@link EncodingUtil#ENCODING_UTF_16_BE UTF-16BE}:<br>
   * <code>0xfe 0xff</code>
   */
  UTF_16_BE() {

    /**
     * {@inheritDoc}
     */
    @Override
    public String getEncoding() {

      return EncodingUtil.ENCODING_UTF_16_BE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getLength() {

      return 2;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean checkPresent(byte[] bytes, int offset) {

      if ((bytes[offset] == 0xfe) && (bytes[offset + 1] == 0xff)) {
        return true;
      }
      return false;
    }

  },

  /**
   * The {@link ByteOrderMark} for
   * {@link EncodingUtil#ENCODING_UTF_16_LE UTF16-LE}:<br>
   * <code>0xfe 0xff</code>
   */
  UTF_16_LE() {

    /**
     * {@inheritDoc}
     */
    @Override
    public String getEncoding() {

      return EncodingUtil.ENCODING_UTF_16_LE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getLength() {

      return 2;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean checkPresent(byte[] bytes, int offset) {

      if ((bytes[offset] == 0xff) && (bytes[offset + 1] == 0xfe)) {
        return true;
      }
      return false;
    }

  },

  /**
   * The {@link ByteOrderMark} for
   * {@link EncodingUtil#ENCODING_UTF_32_BE UTF-32BE}:<br>
   * <code>0xfe 0xff</code>
   */
  UTF_32_BE() {

    /**
     * {@inheritDoc}
     */
    @Override
    public String getEncoding() {

      return EncodingUtil.ENCODING_UTF_32_BE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getLength() {

      return 4;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean checkPresent(byte[] bytes, int offset) {

      if ((bytes[offset] == 0x00) && (bytes[offset + 1] == 0x00) && (bytes[offset + 2] == 0xfe)
          && (bytes[offset + 3] == 0xff)) {
        return true;
      }
      return false;
    }

  },

  /**
   * The {@link ByteOrderMark} for
   * {@link EncodingUtil#ENCODING_UTF_32_LE UTF-32LE}:<br>
   * <code>0xfe 0xff</code>
   */
  UTF_32_LE() {

    /**
     * {@inheritDoc}
     */
    @Override
    public String getEncoding() {

      return EncodingUtil.ENCODING_UTF_32_LE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getLength() {

      return 4;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean checkPresent(byte[] bytes, int offset) {

      if ((bytes[offset] == 0xff) && (bytes[offset + 1] == 0xfe) && (bytes[offset + 2] == 0x00)
          && (bytes[offset + 3] == 0x00)) {
        return true;
      }
      return false;
    }

  };

  /**
   * This method gets the encoding indicated by this {@link ByteOrderMark}.
   * 
   * @return the encoding.
   */
  public abstract String getEncoding();

  /**
   * This method gets the number of bytes of this {@link ByteOrderMark}.
   * 
   * @return the length.
   */
  public abstract int getLength();

  /**
   * This method detects if this {@link ByteOrderMark} is present in the given
   * <code>bytes</code>.<br>
   * <b>NOTE:</b><br>
   * A BOM may only occur at the head of your data (file, payload, etc.).<br>
   * <b>ATTENTION:</b><br>
   * Please note that binary data may accidently have header bytes that
   * represent this {@link ByteOrderMark}. This method can NOT know this and
   * will return <code>true</code> even if the data is NOT encoded with the
   * {@link #getEncoding() according encoding}. Therefore you should only use
   * this method for the header of textual data.
   * 
   * @param bytes is the buffer with the bytes to check.
   * @param offset is the index of the first data-byte in <code>bytes</code>.
   *        Will typically be <code>0</code>.
   * @return <code>true</code> if this {@link ByteOrderMark BOM} was detected
   *         in the
   */
  public final boolean isPresent(byte[] bytes, int offset) {

    if (offset + getLength() <= bytes.length) {
      return checkPresent(bytes, offset);
    }
    return false;
  }

  /**
   * This is an internal variant of {@link #isPresent(byte[], int)}.
   * 
   * @param bytes is the buffer with the bytes to check.
   * @param offset is the index of the first data-byte in <code>bytes</code>.
   *        Will typically be <code>0</code>.
   * @return <code>true</code> if this {@link ByteOrderMark BOM} was detected
   *         in the
   */
  protected abstract boolean checkPresent(byte[] bytes, int offset);

  /**
   * This method detects the {@link ByteOrderMark} that may be
   * {@link #isPresent(byte[], int) present} in the given <code>bytes</code>
   * starting at <code>offset</code>.<br>
   * <b>ATTENTION:</b><br>
   * Please note that binary data may accidently have header bytes that
   * represent a {@link ByteOrderMark}. This method can NOT know this and will
   * return that {@link ByteOrderMark} even if the data is NOT encoded with the
   * {@link #getEncoding() according encoding}. Therefore you should only use
   * this method for the header of textual data.
   * 
   * @param bytes is the buffer with the bytes to check.
   * @param offset is the index of the first data-byte in <code>bytes</code>.
   *        Will typically be <code>0</code>.
   * @return the detected {@link ByteOrderMark} or <code>null</code> if the
   *         given <code>bytes</code> have no BOM.
   */
  public static ByteOrderMark detect(byte[] bytes, int offset) {

    for (ByteOrderMark bom : values()) {
      if (bom.isPresent(bytes, offset)) {
        return bom;
      }
    }
    return null;
  }

}
