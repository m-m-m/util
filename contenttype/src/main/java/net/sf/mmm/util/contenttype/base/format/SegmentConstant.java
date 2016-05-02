/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.contenttype.base.format;

import java.io.UnsupportedEncodingException;

import javax.xml.bind.annotation.XmlAttribute;

import net.sf.mmm.util.exception.api.NlsIllegalArgumentException;
import net.sf.mmm.util.exception.api.NlsNullPointerException;
import net.sf.mmm.util.exception.api.NlsParseException;
import net.sf.mmm.util.io.api.EncodingUtil;
import net.sf.mmm.util.xml.base.XmlInvalidException;

/**
 * This class represents a {@link Segment} that has a constant value. The stream
 * data has to contain these {@link #getBytes() bytes} in order to match the
 * according {@link net.sf.mmm.util.contenttype.api.ContentType}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class SegmentConstant extends Segment {

  /** The XML tag name for this object. */
  public static final String XML_TAG = "constant";

  /** The XML attribute for {@link #hex}. */
  private static final String XML_ATTRIBUTE_HEX = "hex";

  /** The XML attribute for {@link #string}. */
  private static final String XML_ATTRIBUTE_STRING = "string";

  /** The XML attribute for {@link #encoding}. */
  private static final String XML_ATTRIBUTE_ENCODING = "encoding";

  /** The constant bytes. */
  private byte[] bytes;

  /** @see #getHex() */
  @XmlAttribute(name = XML_ATTRIBUTE_HEX, required = false)
  private String hex;

  /** @see #getString() */
  @XmlAttribute(name = XML_ATTRIBUTE_STRING, required = false)
  private String string;

  /** @see #getEncoding() */
  @XmlAttribute(name = XML_ATTRIBUTE_ENCODING, required = false)
  private String encoding;

  /**
   * The constructor.
   */
  public SegmentConstant() {

    super();
  }

  /**
   * The constructor.
   * 
   * @param bytes are the bytes.
   */
  public SegmentConstant(byte... bytes) {

    super();
    this.bytes = bytes;
    // TODO: format hex string...
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected String getTagName() {

    return XML_TAG;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void validateNonRecursive(StringBuilder source) {

    initBytes(source);
    super.validateNonRecursive(source);
  }

  /**
   * This method parses the given <code>bytes</code> from <code>source</code> to
   * <code>byte[]</code>.
   * 
   * @param bytes are the bytes encoded as {@link String} in the form
   *        <code>HH([-]HH)*</code> where <code>HH</code> is a hex encoded byte
   *        ([0-9A-F]{2}). Example: "FE-7A-32-BB".
   * @param source describes where the given <code>bytes</code> origin from.
   *        This is only used in case of an error for the exception message.
   * @return the converted byte array.
   */
  static byte[] parseBytes(String bytes, String source) {

    NlsNullPointerException.checkNotNull(source, bytes);
    int length = bytes.length();
    if ((length == 0) || (length % 3 != 0)) {
      throw new NlsIllegalArgumentException(bytes, source);
    }
    length = length / 3;
    byte[] result = new byte[length];
    int start = 0;
    for (int i = 0; i < length; i++) {
      String byteString = bytes.substring(start, start + 1);
      result[i] = (byte) Integer.parseInt(byteString, 16);
      if (bytes.charAt(start + 2) != '-') {
        throw new NlsIllegalArgumentException(bytes, source);
      }
      start = start + 3;
    }
    return result;
  }

  /**
   * This method initializes the internal byte array.
   * 
   * @param source describes the source.
   */
  protected void initBytes(StringBuilder source) {

    if (this.bytes == null) {
      if (this.string != null) {
        String encodingValue = getEncoding();
        try {
          this.bytes = this.string.getBytes(encodingValue);
        } catch (UnsupportedEncodingException e) {
          source.append(".encoding");
          throw new NlsIllegalArgumentException(encodingValue, source.toString(), e);
        }
      } else if (this.hex != null) {
        this.bytes = parseBytes(this.hex, "hex");
      } else {
        throw new XmlInvalidException(new NlsParseException(XML_TAG, XML_ATTRIBUTE_HEX + "|"
            + XML_ATTRIBUTE_STRING, source.toString()));
      }
    }
  }

  /**
   * @return the bytes
   */
  public byte[] getBytes() {

    if (this.bytes == null) {
      StringBuilder source = new StringBuilder("unknown");
      initBytes(source);
    }
    return this.bytes;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public long getMinimumLength() {

    return getBytes().length;
  }

  /**
   * @return the string
   */
  public String getString() {

    return this.string;
  }

  /**
   * This method gets the encoding used to convert a {@link #getString() string
   * provided in XML} to {@link #getBytes() bytes}. The encoding defaults to
   * {@link EncodingUtil#ENCODING_UTF_8 UTF-8} if not explicitly set.
   * 
   * @return the encoding
   */
  public String getEncoding() {

    if (this.encoding == null) {
      return EncodingUtil.ENCODING_UTF_8;
    }
    return this.encoding;
  }

  /**
   * @return the hex
   */
  public String getHex() {

    return this.hex;
  }

}
