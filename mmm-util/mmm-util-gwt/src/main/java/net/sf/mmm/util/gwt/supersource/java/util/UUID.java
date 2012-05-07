/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.gwt.supersource.java.util;

import java.io.Serializable;

/**
 * TODO: this class ...
 * 
 * @author hohwille
 * @since 1.0.0
 */
public final class UUID implements Serializable, Comparable<UUID> {

  /** UID for serialization. */
  private static final long serialVersionUID = -4856846361193249489L;

  /** @see #getMostSignificantBits() */
  private final long mostSignificantBits;

  /** @see #getLeastSignificantBits() */
  private final long leastSignificantBits;

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
   * The constructor.
   * 
   * @param mostSignificantBits - see {@link #getMostSignificantBits()}.
   * @param leastSignificantBits - see {@link #getLeastSignificantBits()}.
   */
  public UUID(long mostSignificantBits, long leastSignificantBits) {

    this.mostSignificantBits = mostSignificantBits;
    this.leastSignificantBits = leastSignificantBits;
    this.version = -1;
    this.variant = -1;
    this.timestamp = -1;
    this.clockSequence = -1;
    this.node = -1;
    this.hashCode = -1;
  }

  /**
   * Static factory to retrieve a type 4 (pseudo randomly generated) UUID.
   * 
   * The <code>UUID</code> is generated using a cryptographically strong pseudo random number generator.
   * 
   * @return a randomly generated <tt>UUID</tt>.
   */
  public static UUID randomUUID() {

    return null;
  }

  /**
   * Creates a <tt>UUID</tt> from the string standard representation as described in the {@link #toString}
   * method.
   * 
   * @param name a string that specifies a <tt>UUID</tt>.
   * @return a <tt>UUID</tt> with the specified value.
   * @throws IllegalArgumentException if name does not conform to the string representation as described in
   *         {@link #toString}.
   */
  public static UUID fromString(String name) {

    String[] components = name.split("-");
    if (components.length != 5)
      throw new IllegalArgumentException("Invalid UUID string: " + name);
    for (int i = 0; i < 5; i++)
      components[i] = "0x" + components[i];

    long mostSigBits = Long.decode(components[0]).longValue();
    mostSigBits <<= 16;
    mostSigBits |= Long.decode(components[1]).longValue();
    mostSigBits <<= 16;
    mostSigBits |= Long.decode(components[2]).longValue();

    long leastSigBits = Long.decode(components[3]).longValue();
    leastSigBits <<= 48;
    leastSigBits |= Long.decode(components[4]).longValue();

    return new UUID(mostSigBits, leastSigBits);
  }

  // Field Accessor Methods

  /**
   * Returns the least significant 64 bits of this UUID's 128 bit value.
   * 
   * @return the least significant 64 bits of this UUID's 128 bit value.
   */
  public long getLeastSignificantBits() {

    return this.leastSignificantBits;
  }

  /**
   * Returns the most significant 64 bits of this UUID's 128 bit value.
   * 
   * @return the most significant 64 bits of this UUID's 128 bit value.
   */
  public long getMostSignificantBits() {

    return this.mostSignificantBits;
  }

  /**
   * The version number associated with this <tt>UUID</tt>. The version number describes how this
   * <tt>UUID</tt> was generated.
   * 
   * The version number has the following meaning:
   * <p>
   * <ul>
   * <li>1 Time-based UUID
   * <li>2 DCE security UUID
   * <li>3 Name-based UUID
   * <li>4 Randomly generated UUID
   * </ul>
   * 
   * @return the version number of this <tt>UUID</tt>.
   */
  public int version() {

    if (this.version < 0) {
      // Version is bits masked by 0x000000000000F000 in MS long
      this.version = (int) ((this.mostSignificantBits >> 12) & 0x0f);
    }
    return this.version;
  }

  /**
   * The variant number associated with this <tt>UUID</tt>. The variant number describes the layout of the
   * <tt>UUID</tt>.
   * 
   * The variant number has the following meaning:
   * <p>
   * <ul>
   * <li>0 Reserved for NCS backward compatibility
   * <li>2 The Leach-Salz variant (used by this class)
   * <li>6 Reserved, Microsoft Corporation backward compatibility
   * <li>7 Reserved for future definition
   * </ul>
   * 
   * @return the variant number of this <tt>UUID</tt>.
   */
  public int variant() {

    if (this.variant < 0) {
      // This field is composed of a varying number of bits
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
   * The timestamp value associated with this UUID.
   * 
   * <p>
   * The 60 bit timestamp value is constructed from the time_low, time_mid, and time_hi fields of this
   * <tt>UUID</tt>. The resulting timestamp is measured in 100-nanosecond units since midnight, October 15,
   * 1582 UTC.
   * <p>
   * 
   * The timestamp value is only meaningful in a time-based UUID, which has version type 1. If this
   * <tt>UUID</tt> is not a time-based UUID then this method throws UnsupportedOperationException.
   * 
   * @throws UnsupportedOperationException if this UUID is not a version 1 UUID.
   */
  public long timestamp() {

    if (version() != 1) {
      throw new UnsupportedOperationException("Not a time-based UUID");
    }
    long result = this.timestamp;
    if (result < 0) {
      result = (this.mostSignificantBits & 0x0000000000000FFFL) << 48;
      result |= ((this.mostSignificantBits >> 16) & 0xFFFFL) << 32;
      result |= this.mostSignificantBits >>> 32;
      this.timestamp = result;
    }
    return result;
  }

  /**
   * The clock sequence value associated with this UUID.
   * 
   * <p>
   * The 14 bit clock sequence value is constructed from the clock sequence field of this UUID. The clock
   * sequence field is used to guarantee temporal uniqueness in a time-based UUID.
   * <p>
   * 
   * The clockSequence value is only meaningful in a time-based UUID, which has version type 1. If this UUID
   * is not a time-based UUID then this method throws UnsupportedOperationException.
   * 
   * @return the clock sequence of this <tt>UUID</tt>.
   * @throws UnsupportedOperationException if this UUID is not a version 1 UUID.
   */
  public int clockSequence() {

    if (version() != 1) {
      throw new UnsupportedOperationException("Not a time-based UUID");
    }
    if (this.clockSequence < 0) {
      this.clockSequence = (int) ((this.leastSignificantBits & 0x3FFF000000000000L) >>> 48);
    }
    return this.clockSequence;
  }

  /**
   * The node value associated with this UUID.
   * 
   * <p>
   * The 48 bit node value is constructed from the node field of this UUID. This field is intended to hold the
   * IEEE 802 address of the machine that generated this UUID to guarantee spatial uniqueness.
   * <p>
   * 
   * The node value is only meaningful in a time-based UUID, which has version type 1. If this UUID is not a
   * time-based UUID then this method throws UnsupportedOperationException.
   * 
   * @return the node value of this <tt>UUID</tt>.
   * @throws UnsupportedOperationException if this UUID is not a version 1 UUID.
   */
  public long node() {

    if (version() != 1) {
      throw new UnsupportedOperationException("Not a time-based UUID");
    }
    if (this.node < 0) {
      this.node = this.leastSignificantBits & 0x0000FFFFFFFFFFFFL;
    }
    return this.node;
  }

  // Object Inherited Methods

  /**
   * Returns a <code>String</code> object representing this <code>UUID</code>.
   * 
   * <p>
   * The UUID string representation is as described by this BNF : <blockquote>
   * 
   * <pre>
     * {@code
     * UUID                   = <time_low> "-" <time_mid> "-"
     *                          <time_high_and_version> "-"
     *                          <variant_and_sequence> "-"
     *                          <node>
     * time_low               = 4*<hexOctet>
     * time_mid               = 2*<hexOctet>
     * time_high_and_version  = 2*<hexOctet>
     * variant_and_sequence   = 2*<hexOctet>
     * node                   = 6*<hexOctet>
     * hexOctet               = <hexDigit><hexDigit>
     * hexDigit               =
     *       "0" | "1" | "2" | "3" | "4" | "5" | "6" | "7" | "8" | "9"
     *       | "a" | "b" | "c" | "d" | "e" | "f"
     *       | "A" | "B" | "C" | "D" | "E" | "F"
     * }</pre>
   * </blockquote>
   * 
   * @return a string representation of this <tt>UUID</tt>.
   */
  @Override
  public String toString() {

    return (digits(this.mostSignificantBits >> 32, 8) + "-" + digits(this.mostSignificantBits >> 16, 4) + "-"
        + digits(this.mostSignificantBits, 4) + "-" + digits(this.leastSignificantBits >> 48, 4) + "-" + digits(
          this.leastSignificantBits, 12));
  }

  /** Returns val represented by the specified number of hex digits. */
  private static String digits(long val, int digits) {

    long hi = 1L << (digits * 4);
    return Long.toHexString(hi | (val & (hi - 1))).substring(1);
  }

  /**
   * Returns a hash code for this <code>UUID</code>.
   * 
   * @return a hash code value for this <tt>UUID</tt>.
   */
  @Override
  public int hashCode() {

    if (this.hashCode == -1) {
      this.hashCode = (int) ((this.mostSignificantBits >> 32) ^ this.mostSignificantBits
          ^ (this.leastSignificantBits >> 32) ^ this.leastSignificantBits);
    }
    return this.hashCode;
  }

  /**
   * Compares this object to the specified object. The result is <tt>true</tt> if and only if the argument is
   * not <tt>null</tt>, is a <tt>UUID</tt> object, has the same variant, and contains the same value, bit for
   * bit, as this <tt>UUID</tt>.
   * 
   * @param obj the object to compare with.
   * @return <code>true</code> if the objects are the same; <code>false</code> otherwise.
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
    return (this.mostSignificantBits == id.mostSignificantBits && this.leastSignificantBits == id.leastSignificantBits);
  }

  // Comparison Operations

  /**
   * Compares this UUID with the specified UUID.
   * 
   * <p>
   * The first of two UUIDs follows the second if the most significant field in which the UUIDs differ is
   * greater for the first UUID.
   * 
   * @param val <tt>UUID</tt> to which this <tt>UUID</tt> is to be compared.
   * @return -1, 0 or 1 as this <tt>UUID</tt> is less than, equal to, or greater than <tt>val</tt>.
   */
  public int compareTo(UUID val) {

    // The ordering is intentionally set up so that the UUIDs
    // can simply be numerically compared as two numbers
    return (this.mostSignificantBits < val.mostSignificantBits
        ? -1
        : (this.mostSignificantBits > val.mostSignificantBits
            ? 1
            : (this.leastSignificantBits < val.leastSignificantBits
                ? -1
                : (this.leastSignificantBits > val.leastSignificantBits ? 1 : 0))));
  }

}
