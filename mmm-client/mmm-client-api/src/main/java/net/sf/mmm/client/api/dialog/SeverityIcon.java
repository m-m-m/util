/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.api.dialog;

/**
 * This is the interface that represents an icon for the severity (in a {@link SimplePopupManager simple
 * popup}). We do NOT use an enum here to allow custom extension. However, we do NOT just use a {@link String}
 * instead to make signatures more explicit and for better documentation.<br/>
 * The following icons are predefined:
 * <ul>
 * <li>{@link #INFORMATION}</li>
 * <li>{@link #WARNING}</li>
 * <li>{@link #ERROR}</li>
 * <li>{@link #QUESTION}</li>
 * </ul>
 * Implementations should ignore unsupported icons or use a fallback.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface SeverityIcon {

  /**
   * @return the name identifying this icon. Depending on the implementation it may be mapped to an actual
   *         image URL or just concatenated with prefix and suffix.
   */
  String getName();

  /** {@link SeverityIcon} indicating an general purpose information (typically a balloon). */
  SeverityIcon INFORMATION = new SeverityIcon() {

    public String getName() {

      return "information";
    }
  };

  /** {@link SeverityIcon} indicating a warning (typically a yellow triangle with an exclamation mark). */
  SeverityIcon WARNING = new SeverityIcon() {

    public String getName() {

      return "warning";
    }
  };

  /** {@link SeverityIcon} indicating an error (typically a red cross). */
  SeverityIcon ERROR = new SeverityIcon() {

    public String getName() {

      return "error";
    }
  };

  /** {@link SeverityIcon} indicating a question (typically a question mark). */
  SeverityIcon QUESTION = new SeverityIcon() {

    public String getName() {

      return "question";
    }
  };

}
