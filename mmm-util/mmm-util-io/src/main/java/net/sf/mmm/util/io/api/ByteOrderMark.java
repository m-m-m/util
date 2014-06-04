/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.io.api;

/**
 * This type represents a Byte-Order-Mark (<a
 * href="http://www.unicode.org/unicode/faq/utf_bom.html#BOM">BOM</a>) of an Unicode-Transformation-Format
 * (UTF).
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.1
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
    public byte[] getBytes() {

      return MAGIC_BYTES_UTF8;
    }

  },

  /**
   * The {@link ByteOrderMark} for {@link EncodingUtil#ENCODING_UTF_16_BE UTF-16BE}:<br>
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
    public byte[] getBytes() {

      return MAGIC_BYTES_UTF16_BE;
    }

  },

  /**
   * The {@link ByteOrderMark} for {@link EncodingUtil#ENCODING_UTF_16_LE UTF16-LE}:<br>
   * <code>0xff 0xfe</code>
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
    public byte[] getBytes() {

      return MAGIC_BYTES_UTF16_LE;
    }

  },

  /**
   * The {@link ByteOrderMark} for {@link EncodingUtil#ENCODING_UTF_32_BE UTF-32BE}:<br>
   * <code>0x00 0x00 0xfe 0xff</code>
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
    public byte[] getBytes() {

      return MAGIC_BYTES_UTF32_BE;
    }

  },

  /**
   * The {@link ByteOrderMark} for {@link EncodingUtil#ENCODING_UTF_32_LE UTF-32LE}:<br>
   * <code>0xff 0xfe 0x00 0x00</code>
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
    public byte[] getBytes() {

      return MAGIC_BYTES_UTF32_LE;
    }

  };

  /** @see #UTF_8 */
  private static final byte[] MAGIC_BYTES_UTF8 = new byte[] { (byte) 0xef, (byte) 0xbb, (byte) 0xbf };

  /** @see #UTF_16_BE */
  private static final byte[] MAGIC_BYTES_UTF16_BE = new byte[] { (byte) 0xfe, (byte) 0xff };

  /** @see #UTF_16_LE */
  private static final byte[] MAGIC_BYTES_UTF16_LE = new byte[] { (byte) 0xff, (byte) 0xfe };

  /** @see #UTF_32_BE */
  private static final byte[] MAGIC_BYTES_UTF32_BE = new byte[] { 0x00, 0x00, (byte) 0xfe, (byte) 0xff };

  /** @see #UTF_32_LE */
  private static final byte[] MAGIC_BYTES_UTF32_LE = new byte[] { (byte) 0xff, (byte) 0xfe, 0x00, 0x00 };

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
  public final int getLength() {

    return getBytes().length;
  }

  /**
   * This method detects if this {@link ByteOrderMark} is present in the given <code>bytes</code>.<br>
   * <b>NOTE:</b><br>
   * A BOM may only occur at the head of your data (file, payload, etc.).<br>
   * <b>ATTENTION:</b><br>
   * Please note that binary data may accidently have header bytes that represent this {@link ByteOrderMark}.
   * This method can NOT know this and will return <code>true</code> even if the data is NOT encoded with the
   * {@link #getEncoding() according encoding}. Therefore you should only use this method for the header of
   * textual data.
   * 
   * @param bytes is the buffer with the bytes to check.
   * @param offset is the index of the first data-byte in <code>bytes</code>. Will typically be <code>0</code>
   *        .
   * @return <code>true</code> if this {@link ByteOrderMark BOM} was detected in the
   */
  public final boolean isPresent(byte[] bytes, int offset) {

    byte[] bom = getBytes();
    if (offset + bom.length <= bytes.length) {
      for (int i = 0; i < bom.length; i++) {
        if (bytes[offset + i] != bom[i]) {
          return false;
        }
      }
      return true;
    }
    return false;
  }

  /**
   * This method gets the bytes of this BOM.
   * 
   * @return the magic bytes of this BOM.
   */
  protected abstract byte[] getBytes();

  /**
   * This method detects the {@link ByteOrderMark} that may be {@link #isPresent(byte[], int) present} in the
   * given <code>bytes</code> starting at <code>offset</code>.<br>
   * <b>ATTENTION:</b><br>
   * Please note that binary data may accidently have header bytes that represent a {@link ByteOrderMark}.
   * This method can NOT know this and will return that {@link ByteOrderMark} even if the data is NOT encoded
   * with the {@link #getEncoding() according encoding}. Therefore you should only use this method for the
   * header of textual data.
   * 
   * @param bytes is the buffer with the bytes to check.
   * @param offset is the index of the first data-byte in <code>bytes</code>. Will typically be <code>0</code>
   *        .
   * @return the detected {@link ByteOrderMark} or <code>null</code> if the given <code>bytes</code> have no
   *         BOM.
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
