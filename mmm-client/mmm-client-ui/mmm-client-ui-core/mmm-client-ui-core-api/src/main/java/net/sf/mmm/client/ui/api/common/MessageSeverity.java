/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.common;

/**
 * This is the interface that represents the <em>severity</em> of a simple message popup. The severity defines
 * the default title and the icon to use. We do NOT use an enum here to allow custom extension. However, we do
 * NOT just use a {@link String} instead to make signatures more explicit and for better documentation.<br/>
 * The following severities are predefined:
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
public interface MessageSeverity {

  /**
   * @return the name identifying this icon. Depending on the implementation it may be mapped to an actual
   *         image URL or just concatenated with prefix and suffix.
   */
  String getName();

  /** {@link MessageSeverity} indicating an general purpose information. */
  MessageSeverity INFORMATION = new MessageSeverity() {

    public String getName() {

      return "information";
    }
  };

  /** {@link MessageSeverity} indicating a warning. */
  MessageSeverity WARNING = new MessageSeverity() {

    public String getName() {

      return "warning";
    }
  };

  /** {@link MessageSeverity} indicating an error. */
  MessageSeverity ERROR = new MessageSeverity() {

    public String getName() {

      return "error";
    }
  };

  /** {@link MessageSeverity} indicating a question. */
  MessageSeverity QUESTION = new MessageSeverity() {

    public String getName() {

      return "question";
    }
  };

}
