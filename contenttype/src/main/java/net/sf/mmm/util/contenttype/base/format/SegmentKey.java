/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.contenttype.base.format;

/**
 * A {@link SegmentKey} is used to define a {@link java.util.Map#get(Object) key} for a {@link SegmentValue} that
 * depends on the payload in the stream.
 *
 * @see SegmentValue#getKey()
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class SegmentKey extends SegmentVariableLength {

  /** The XML tag name for this object. */
  public static final String XML_TAG = "key";

  private String prefix;

  /**
   * The constructor.
   */
  public SegmentKey() {

    super();
    this.prefix = "";
  }

  @Override
  protected String getTagName() {

    return XML_TAG;
  }

  /**
   * This method gets the prefix for the metadata key. You can also use this as entire key if the
   * {@link #getMinimumLength() length} is set to {@code 0}.
   *
   * @return the prefix.
   */
  public String getPrefix() {

    return this.prefix;
  }

}
