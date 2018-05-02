package net.sf.mmm.util.lang.api;

import java.util.Arrays;
import java.util.Base64;
import java.util.Objects;

/**
 * Datatype for binary data or BLOB (binary large object).
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 7.6.0
 */
public class BinaryType implements Binary {

  private static final char[] HEX = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

  /** @see #getData() */
  protected final byte[] data;

  /**
   * The constructor.
   *
   * @param data the raw binary {@link #getData() data}.
   */
  public BinaryType(byte[] data) {

    super();
    if (data == null) {
      Objects.requireNonNull(data, getClass().getSimpleName() + ".data");
    }
    if (data.length < getMinLength()) {
      throw new IllegalArgumentException(getClass().getSimpleName() + ".data.length = " + data.length + " < " + getMinLength());
    }
    if (data.length > getMaxLength()) {
      throw new IllegalArgumentException(getClass().getSimpleName() + ".data.length = " + data.length + " > " + getMaxLength());
    }
    this.data = data;
  }

  /**
   * The constructor (mainly for testing).
   *
   * @param base64 the {@link #getData() data} as {@link #getBase64() base64}.
   */
  public BinaryType(String base64) {

    this(parseBase64(base64));
  }

  /**
   * @return the minimum allowed length of the {@link #getData() data}. Implementation has to be static and return the
   *         same value for each class with each call.
   */
  protected int getMinLength() {

    return 0;
  }

  /**
   * @return the maximum allowed length of the {@link #getData() data}. Implementation has to be static and return the
   *         same value for each class with each call.
   */
  protected int getMaxLength() {

    return Integer.MAX_VALUE;
  }

  @Override
  public byte[] getData() {

    return this.data.clone();
  }

  @Override
  public void getData(byte[] buffer, int offset) {

    assert (this.data.length + offset <= buffer.length);
    System.arraycopy(this.data, 0, buffer, offset, this.data.length);
  }

  /**
   * @return the length of this BLOB in bytes (array length of {@link #getData()}).
   */
  @Override
  public int getLength() {

    return this.data.length;
  }

  @Override
  public String getHex() {

    return formatHex(this.data);
  }

  @Override
  public String getBase64() {

    return formatBase64(this.data);
  }

  @Override
  public boolean isZeros() {

    for (byte b : this.data) {
      if (b != 0) {
        return false;
      }
    }
    return true;
  }

  @Override
  public int hashCode() {

    // for transparency and stability we do not rely on any external function.
    // Arrays.hashCode(Object[]) might most probably never change but you will never know...
    int hash = 1;
    for (byte b : this.data) {
      hash = 31 * hash + b;
    }
    return hash;
  }

  @Override
  public boolean equals(Object obj) {

    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    BinaryType other = (BinaryType) obj;
    if (!Arrays.equals(this.data, other.data)) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {

    return getBase64();
  }

  /**
   * @param hex a {@link #getHex() hex representation} of {@link #getData() BLOB data}.
   * @return the parsed {@code hex} String.
   */
  public static byte[] parseHex(String hex) {

    int length = hex.length();
    if ((length % 2) != 0) {
      throw new IllegalArgumentException("Hex has odd length: " + length);
    }
    try {
      byte[] bytes = new byte[length / 2];
      int charIndex = 0;
      for (int byteIndex = 0; byteIndex < bytes.length; byteIndex++) {
        byte high = parseHexChar(hex.charAt(charIndex++));
        byte low = parseHexChar(hex.charAt(charIndex++));
        bytes[byteIndex] = (byte) ((high << 4) + low);
      }
      return bytes;
    } catch (Exception e) {
      throw new IllegalArgumentException("Invalid hexadecimal value: " + hex, e);
    }
  }

  private static byte parseHexChar(char c) {

    int value = Character.digit(c, 16);
    if ((value < 0) || (value >= 16)) {
      throw new IllegalArgumentException("'" + c + "' is not a valid hexadecimal character.");
    }
    return (byte) value;
  }

  /**
   * @param data the {@link #getData() data} to format.
   * @return the given {@code data} formatted as hex {@link String}.
   */
  public static String formatHex(byte[] data) {

    char[] buffer = new char[data.length * 2];
    int i = 0;
    for (byte b : data) {
      buffer[i++] = HEX[(b & 0xF0) >> 4];
      buffer[i++] = HEX[b & 0x0F];
    }
    return new String(buffer);
  }

  /**
   * @param base64 the BLOB to parse as {@link java.util.Base64} encoded {@link String}.
   * @return the parsed {@code base64} String.
   */
  public static byte[] parseBase64(String base64) {

    return Base64.getDecoder().decode(base64);
  }

  /**
   * @param data the {@link #getData() data} to format.
   * @return the given {@code data} formatted as {@link java.util.Base64} encoded {@link String} representation.
   * @see #getBase64()
   */
  public static String formatBase64(byte[] data) {

    return Base64.getEncoder().encodeToString(data);
  }

  /**
   * @param value the {@code long} value.
   * @return the value as array of 8 {@code byte}s.
   */
  public static byte[] toBytes(long value) {

    long v = value;
    byte[] bytes = new byte[8];
    for (int i = 7; i >= 0; i--) {
      bytes[i] = (byte) v;
      v >>= 8;
    }
    return bytes;
  }

  /**
   * @param value the {@code int} value.
   * @return the value as array of 4 {@code byte}s.
   */
  public static byte[] toBytes(int value) {

    int v = value;
    byte[] bytes = new byte[4];
    for (int i = 3; i >= 0; i--) {
      bytes[i] = (byte) v;
      v >>= 8;
    }
    return bytes;
  }

  /**
   * @param data the long value as {@code byte[]} with a maximum length of 8 (see {@link #toBytes(long)}).
   * @return the value {@code long}.
   */
  public static long toLong(byte[] data) {

    assert (data.length <= 8);
    long v = 0;
    for (byte b : data) {
      v <<= 8;
      v = v | (b & 0x0FF);
    }
    return v;
  }

  /**
   * @param data the long value as {@code byte[]} with a maximum length of 8 (see {@link #toBytes(long)}).
   * @return the value {@code long}.
   */
  public static long toInt(byte[] data) {

    assert (data.length <= 4);
    int v = 0;
    for (byte b : data) {
      v <<= 8;
      v = v | (b & 0x0FF);
    }
    return v;
  }

}
