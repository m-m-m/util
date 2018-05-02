package net.sf.mmm.util.lang.api;

import org.assertj.core.api.Assertions;
import org.junit.Test;

/**
 * This is the test-case for {@link BinaryType}.
 */
public class BinaryTypeTest extends Assertions {

  private static final String SAMPLE_BLOB_HEX = "0123456789abcdef";

  private static final byte[] SAMPLE_BLOB_DATA = new byte[] { 0x01, 0x23, 0x45, 0x67, (byte) 0x089, (byte) 0x0ab, (byte) 0x0cd, (byte) 0x0ef };

  private static final String SAMPLE_BLOB_BASE64 = "ASNFZ4mrze8=";

  private static final int LENGTH_MIN = 1;

  private static final int LENGTH_MAX = 10;

  /** Test {@link BinaryType#BinaryType(byte[])} with {@code null}. */
  @Test(expected = NullPointerException.class)
  public void testByteConstructorWithNull() {

    new Blob((byte[]) null);
  }

  /** Test {@link BinaryType#BinaryType(byte[])} with too short data (too few bytes). */
  @Test(expected = IllegalArgumentException.class)
  public void testByteConstructorTooShort() {

    new Blob(new byte[0]);
  }

  /** Test {@link BinaryType#BinaryType(byte[])} with too long data (too much bytes). */
  @Test(expected = IllegalArgumentException.class)
  public void testByteConstructorTooLong() {

    new Blob(new byte[LENGTH_MAX + 1]);
  }

  /** Test {@link BinaryType#BinaryType(String)} with {@code null}. */
  @Test(expected = NullPointerException.class)
  public void testStringConstructorWithNull() {

    new Blob((String) null);
  }

  /** Test {@link BinaryType#BinaryType(String)} with too short data (too few bytes). */
  @Test(expected = IllegalArgumentException.class)
  public void testStringConstructorTooShort() {

    new Blob("");
  }

  /** Test {@link BinaryType#BinaryType(String)} with too long data (too much bytes). */
  @Test(expected = IllegalArgumentException.class)
  public void testStringConstructorTooLong() {

    StringBuilder buffer = new StringBuilder(LENGTH_MAX * 2);
    for (int i = 0; i <= LENGTH_MAX; i++) {
      String hex = Integer.toHexString(i);
      if (hex.length() < 2) {
        hex = "0" + hex;
      }
      buffer.append(hex);
    }
    new Blob(buffer.toString());
  }

  /** Test {@link BinaryType#BinaryType(String)} with an odd number of hex characters. */
  @Test(expected = IllegalArgumentException.class)
  public void testStringConstructorOdd() {

    new Blob("1");
  }

  /** Test of {@link BinaryType#BinaryType(String)} with valid data. */
  @Test
  public void testStringConstructor() {

    BinaryType blob = new Blob(SAMPLE_BLOB_BASE64);
    verifyBlob8ByteHexAndBase64(blob);
    assertThat(blob.isZeros()).isFalse();
  }

  /** Test of {@link BinaryType#parseHex(String)} with valid data. */
  @Test
  public void testParseHex() {

    BinaryType blob = Blob.ofHex(SAMPLE_BLOB_HEX);
    verifyBlob8ByteHexAndBase64(blob);
    assertThat(blob.isZeros()).isFalse();
  }

  /** Test of {@link BinaryType#BinaryType(byte[])} with valid data. */
  @Test
  public void testByteConstructor() {

    BinaryType blob = new Blob(SAMPLE_BLOB_DATA);
    verifyBlob8ByteHexAndBase64(blob);
    assertThat(blob.isZeros()).isFalse();
  }

  /** Test of {@link BinaryType} for equals and hashCode. */
  @Test
  public void testEqualsAndHashCode() {

    BinaryType byteBlob = new Blob(SAMPLE_BLOB_DATA);
    BinaryType base64Blob = new Blob(SAMPLE_BLOB_BASE64);
    assertThat(byteBlob).isEqualTo(base64Blob).isEqualTo(byteBlob);
    assertThat(byteBlob.hashCode()).isEqualTo(base64Blob.hashCode());
    assertThat(byteBlob).isNotEqualTo(null).isNotEqualTo("").isNotEqualTo(new Blob("12345678"));
  }

  /** Test of {@link BinaryType#getMinLength()} and {@link BinaryType#getMaxLength()}. */
  @Test
  public void testLengthDefaults() {

    BinaryType blob = new BinaryType(new byte[0]) {};
    assertThat(blob.getMinLength()).isEqualTo(0);
    assertThat(blob.getMaxLength()).isEqualTo(Integer.MAX_VALUE);
  }

  /** Test of {@link BinaryType#toBytes(int)} and {@link BinaryType#toBytes(long)}. */
  @Test
  public void testToBytes() {

    assertThat(BinaryType.toBytes(0x0FEDCBA98)).containsExactly(0x0FE, 0x0DC, 0x0BA, 0x098);
    assertThat(BinaryType.toBytes(0x0FEDCBA9876543210L)).containsExactly(0x0FE, 0x0DC, 0x0BA, 0x098, 0x076, 0x054, 0x032, 0x010);
  }

  /** Test of {@link BinaryType#toInt(byte[])}. */
  @Test
  public void testToInt() {

    assertThat(BinaryType.toInt(new byte[] { (byte) 0x0FE, (byte) 0x0DC, (byte) 0x0BA, (byte) 0x098 })).isEqualTo(0x0FEDCBA98);
  }

  /** Test of {@link BinaryType#toLong(byte[])}. */
  @Test
  public void testToLong() {

    assertThat(BinaryType.toLong(new byte[] { (byte) 0x0FE, (byte) 0x0DC, (byte) 0x0BA, (byte) 0x098, 0x076, 0x054, 0x032, 0x010 }))
        .isEqualTo(0x0FEDCBA9876543210L);
  }

  private void verifyBlob8ByteHexAndBase64(BinaryType blob) {

    assertThat(blob.getHex()).isEqualTo(SAMPLE_BLOB_HEX);
    assertThat(blob.getBase64()).isEqualTo(SAMPLE_BLOB_BASE64);
    assertThat(blob.toString()).isEqualTo(SAMPLE_BLOB_BASE64);
    assertThat(blob.getData()).isEqualTo(SAMPLE_BLOB_DATA);
    byte[] buffer = new byte[SAMPLE_BLOB_DATA.length];
    blob.getData(buffer, 0);
    assertThat(buffer).isEqualTo(SAMPLE_BLOB_DATA);
    assertThat(blob.getLength()).isEqualTo(8);
  }

  private static class Blob extends BinaryType {

    public Blob(byte[] data) {

      super(data);
    }

    public Blob(String base64) {

      super(base64);
    }

    static Blob ofHex(String hex) {

      return new Blob(parseHex(hex));
    }

    @Override
    protected int getMinLength() {

      return LENGTH_MIN;
    }

    @Override
    protected int getMaxLength() {

      return LENGTH_MAX;
    }

  }

}
