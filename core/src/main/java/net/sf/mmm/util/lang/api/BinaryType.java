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
   * @return the minimum allowed length of the {@link #getData() data}. Implementation has to be static and
   *         return the same value for each class with each call.
   */
  protected int getMinLength() {

    return 0;
  }

  /**
   * @return the maximum allowed length of the {@link #getData() data}. Implementation has to be static and
   *         return the same value for each class with each call.
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
    byte[] bytes = new byte[length / 2];
    int charIndex = 0;
    for (int byteIndex = 0; byteIndex < bytes.length; byteIndex++) {
      int high = Character.digit(hex.charAt(charIndex++), 16);
      int low = Character.digit(hex.charAt(charIndex++), 16);
      bytes[byteIndex] = (byte) ((high << 4) + low);
    }
    return bytes;
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
   * @return the given {@code data} formatted as {@link java.util.Base64} encoded {@link String}
   *         representation.
   * @see #getBase64()
   */
  public static String formatBase64(byte[] data) {

    return Base64.getEncoder().encodeToString(data);
  }

}
