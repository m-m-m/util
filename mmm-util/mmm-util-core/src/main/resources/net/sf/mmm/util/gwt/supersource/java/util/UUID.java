/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package java.util;

import java.io.Serializable;

/**
 * This is a very limited variant of {@link java.util.UUID} to allow access in GWT clients.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public final class UUID implements Serializable, Comparable<UUID> {

  /** UID for serialization. */
  private static final long serialVersionUID = -4856846361193249489L;

  /** Value that indicates that a field is NOT yet initialized. */
  private static final int NOT_INITIALIZED = -1;

  /** @see #getMostSignificantBits() */
  private long mostSignificantBits;

  /** @see #getLeastSignificantBits() */
  private long leastSignificantBits;

  /** @see #version() */
  private transient int version;

  /** @see #variant() */
  private transient int variant;

  /** @see #timestamp */
  private transient volatile long timestamp;

  /** @see #clockSequence() */
  private transient int clockSequence;

  /** @see #node() */
  private transient long node;

  /** @see #hashCode() */
  private transient int hashCode;

  /**
   * The constructor for de-serialization in GWT.
   */
  protected UUID() {

    super();
  }

  /**
   * The constructor.
   *
   * @param mostSignificantBits - see {@link #getMostSignificantBits()}.
   * @param leastSignificantBits - see {@link #getLeastSignificantBits()}.
   */
  public UUID(long mostSignificantBits, long leastSignificantBits) {

    this.mostSignificantBits = mostSignificantBits;
    this.leastSignificantBits = leastSignificantBits;
    this.version = NOT_INITIALIZED;
    this.variant = NOT_INITIALIZED;
    this.timestamp = NOT_INITIALIZED;
    this.clockSequence = NOT_INITIALIZED;
    this.node = NOT_INITIALIZED;
    this.hashCode = NOT_INITIALIZED;
  }

  /**
   * <b>ATTENTION:</b><br/>
   * This method is not supported and returns <code>null</code>.
   *
   * @see java.util.UUID#randomUUID()
   *
   * @return <code>null</code>.
   */
  public static UUID randomUUID() {

    return null;
  }

  /**
   * @param name - see {@link java.util.UUID#fromString(String)}.
   * @return see {@link java.util.UUID#fromString(String)}.
   */
  public static UUID fromString(String name) {

    String[] components = name.split("-");
    if (components.length != 5) {
      throw new IllegalArgumentException("Invalid UUID string: " + name);
    }
    long msb = Long.parseLong(components[0], 16);
    msb = msb << 16;
    msb = msb | Long.parseLong(components[1], 16);
    msb = msb << 16;
    msb = msb | Long.parseLong(components[2], 16);

    long lsb = Long.parseLong(components[3], 16);
    lsb = lsb << 48;
    lsb = lsb | Long.parseLong(components[4], 16);

    return new UUID(msb, lsb);
  }

  /**
   * @return see {@link java.util.UUID#getLeastSignificantBits()}.
   */
  public long getLeastSignificantBits() {

    return this.leastSignificantBits;
  }

  /**
   * @return see {@link java.util.UUID#getMostSignificantBits()}.
   */
  public long getMostSignificantBits() {

    return this.mostSignificantBits;
  }

  /**
   * @return see {@link java.util.UUID#version()}
   */
  public int version() {

    if (this.version == NOT_INITIALIZED) {
      this.version = (int) ((this.mostSignificantBits >> 12) & 0x0f);
    }
    return this.version;
  }

  /**
   * @return see {@link java.util.UUID#variant()}
   */
  public int variant() {

    if (this.variant == NOT_INITIALIZED) {
      if ((this.leastSignificantBits >>> 63) == 0) {
        this.variant = 0;
      } else if ((this.leastSignificantBits >>> 62) == 2) {
        this.variant = 2;
      } else {
        this.variant = (int) (this.leastSignificantBits >>> 61);
      }
    }
    return this.variant;
  }

  /**
   * @return see {@link java.util.UUID#timestamp()}
   */
  public long timestamp() {

    if (version() != 1) {
      throw new UnsupportedOperationException("Not a time-based UUID");
    }
    long result = this.timestamp;
    if (result == NOT_INITIALIZED) {
      result = (this.mostSignificantBits & 0x0000000000000FFFL) << 48;
      result = result | ((this.mostSignificantBits >> 16) & 0xFFFFL) << 32;
      result = result | this.mostSignificantBits >>> 32;
      this.timestamp = result;
    }
    return result;
  }

  /**
   * @return see {@link java.util.UUID#clockSequence()}
   */
  public int clockSequence() {

    if (version() != 1) {
      throw new UnsupportedOperationException("Not a time-based UUID");
    }
    if (this.clockSequence == NOT_INITIALIZED) {
      this.clockSequence = (int) ((this.leastSignificantBits & 0x3FFF000000000000L) >>> 48);
    }
    return this.clockSequence;
  }

  /**
   * @return see {@link java.util.UUID#node()}
   */
  public long node() {

    if (version() != 1) {
      throw new UnsupportedOperationException("Not a time-based UUID");
    }
    if (this.node == NOT_INITIALIZED) {
      this.node = this.leastSignificantBits & 0x0000FFFFFFFFFFFFL;
    }
    return this.node;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    return (digits(this.mostSignificantBits >> 32, 8) + "-" + digits(this.mostSignificantBits >> 16, 4) + "-"
        + digits(this.mostSignificantBits, 4) + "-" + digits(this.leastSignificantBits >> 48, 4) + "-" + digits(
          this.leastSignificantBits, 12));
  }

  /**
   * Returns the given <code>value</code> represented by the specified number of hex <code>digits</code>.
   *
   * @param value is the number to format.
   * @param digits are the number of digits requested.
   * @return the given <code>value</code> as hex {@link String} with the given number of digits.
   */
  private static String digits(long value, int digits) {

    long hi = 1L << (digits * 4);
    return Long.toHexString(hi | (value & (hi - 1))).substring(1);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {

    if (this.hashCode == NOT_INITIALIZED) {
      this.hashCode = (int) ((this.mostSignificantBits >> 32) ^ this.mostSignificantBits
          ^ (this.leastSignificantBits >> 32) ^ this.leastSignificantBits);
    }
    return this.hashCode;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean equals(Object obj) {

    if (obj == null) {
      return false;
    }
    if (!(obj instanceof UUID)) {
      return false;
    }
    if (((UUID) obj).variant() != this.variant()) {
      return false;
    }
    UUID id = (UUID) obj;
    return ((this.mostSignificantBits == id.mostSignificantBits) && (this.leastSignificantBits == id.leastSignificantBits));
  }

  /**
   * {@inheritDoc}
   */
  public int compareTo(UUID val) {

    if (this.mostSignificantBits < val.mostSignificantBits) {
      return -1;
    }
    if (this.mostSignificantBits > val.mostSignificantBits) {
      return 1;
    }
    if (this.leastSignificantBits < val.leastSignificantBits) {
      return -1;
    }
    if (this.leastSignificantBits > val.leastSignificantBits) {
      return 1;
    }
    return 0;
  }

}
