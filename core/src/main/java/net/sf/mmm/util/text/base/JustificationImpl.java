/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.text.base;

import java.io.IOException;

import net.sf.mmm.util.exception.api.IllegalCaseException;
import net.sf.mmm.util.exception.api.NlsIllegalStateException;
import net.sf.mmm.util.exception.api.NlsParseException;
import net.sf.mmm.util.lang.api.HorizontalAlignment;
import net.sf.mmm.util.text.api.Justification;

/**
 * This is the implementation of a {@link Justification}. <br>
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.1
 */
public class JustificationImpl implements Justification {

  /** The character used to identify the truncate mode. */
  private static final char MODE_TRUNCATE = '|';

  /** The character used to identify the default mode. */
  private static final char MODE_DEFAULT = '\0';

  /** The pattern for the format. */
  private static final String FORMAT_PATTERN = ".[-+~][0-9]+[|]?";

  /** The alignment. */
  private final HorizontalAlignment alignment;

  /**
   * The character used to fill up.
   */
  private final char filler;

  /**
   * The width of the justified string.
   */
  private final int width;

  /**
   * The mode of the {@link Justification}.
   */
  private final char mode;

  /**
   * The constructor.
   *
   * @param format is the justification-format.
   */
  public JustificationImpl(String format) {

    super();
    if (format.length() < 2) {
      throw new NlsParseException(format, FORMAT_PATTERN, Justification.class.getSimpleName());
    }
    this.filler = format.charAt(0);
    this.alignment = HorizontalAlignment.fromValue(Character.toString(format.charAt(1)));
    if (this.alignment == null) {
      throw new NlsParseException(format, FORMAT_PATTERN, Justification.class.getSimpleName());
    }
    int endIndex = format.length();
    char modeChar = format.charAt(format.length() - 1);
    if ((modeChar >= '0') && (modeChar <= '9')) {
      this.mode = MODE_DEFAULT;
    } else {
      this.mode = modeChar;
      endIndex--;
      if (this.mode != MODE_TRUNCATE) {
        throw new NlsParseException(format, FORMAT_PATTERN, Justification.class.getSimpleName());
      }
    }
    this.width = Integer.parseInt(format.substring(2, endIndex));
  }

  @Override
  public String justify(CharSequence value) {

    try {
      StringBuilder sb = new StringBuilder();
      justify(value, sb);
      return sb.toString();
    } catch (IOException e) {
      throw new NlsIllegalStateException(e);
    }
  }

  @Override
  public void justify(CharSequence value, Appendable target) throws IOException {

    int space = this.width - value.length();
    int leftSpace = 0;
    int rightSpace = 0;
    if (space > 0) {
      switch (this.alignment) {
        case CENTER:
          leftSpace = space / 2;
          rightSpace = space - leftSpace;
          break;
        case LEFT:
          rightSpace = space;
          break;
        case RIGHT:
          leftSpace = space;
          break;
        default:
          throw new IllegalCaseException(HorizontalAlignment.class, this.alignment);
      }
    }
    for (int i = 0; i < leftSpace; i++) {
      target.append(this.filler);
    }
    if ((space < 0) && (this.mode == MODE_TRUNCATE)) {
      target.append(value.subSequence(0, this.width));
    } else {
      target.append(value);
    }
    for (int i = 0; i < rightSpace; i++) {
      target.append(this.filler);
    }
  }

  @Override
  public String toString() {

    StringBuilder sb = new StringBuilder(8);
    sb.append(this.filler);
    sb.append(this.alignment);
    sb.append(this.width);
    return sb.toString();
  }
}
