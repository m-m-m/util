package net.sf.mmm.util.lang.api;

/**
 * Interface for smaller binary data (e.g. a cryptographic signature). Implementations shall be immutable.
 * <br>
 * <b>Attention:</b><br>
 * This API is designed for smaller binary data and will cause it to be fully loaded into memory (Java heap).
 * For binary large objects (BLOB) (e.g. for generic files or documents) use other solutions like e.g.
 * {@link java.sql.Blob}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 7.6.0
 */
public interface Binary {

  /** An empty byte array (no data). */
  byte[] EMPTY_BYTE_ARRAY = new byte[0];

  /**
   * <b>ATTENTION</b>:<br/>
   * Sub-types often represent sensible data. Be careful to pass these data in raw form (unencrypted).
   *
   * @return the raw encoded data. Array will be copied to prevent manipulation. Hence, this method is
   *         expensive and subsequent calls shall be avoided.
   */
  byte[] getData();

  /**
   * Copies the {@link #getData() data} into the given buffer.
   *
   * @param buffer the byte array to copy the data to. Has to have enough capacity left for the
   *        {@link #getLength() length} of this BLOB.
   * @param offset the index where to start copying to {@code buffer}.
   */
  void getData(byte[] buffer, int offset);

  /**
   * @return the length of this BLOB in bytes (array length of {@link #getData()}).
   */
  int getLength();

  /**
   * <b>ATTENTION</b>:<br/>
   * Sub-types often represent sensible data. Be careful to pass these data in raw form (unencrypted).
   *
   * @return a hex-dump of this BLOB. This form is more transparent but less compact to {@link #getBase64()
   *         base 64 representation}.
   */
  String getHex();

  /**
   * <b>ATTENTION</b>:<br/>
   * Sub-types often represent sensible data. Be careful to pass these data in raw form (unencrypted).
   *
   * @return a {@link java.util.Base64} encoded {@link String} representation of this BLOB. Base64
   *         representation is more compact and therefore useful for larger BLOBs while {@link #getHex() hex}
   *         is more transparent to read for humans.
   */
  String getBase64();

  /**
   * @return {@code true} if the {@link #getData() data} contains only zeros ({@code 0} values), {@code false}
   *         otherwise.
   */
  boolean isZeros();

}
