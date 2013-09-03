/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.widget.field;

import net.sf.mmm.client.ui.NlsBundleClientUiRoot;
import net.sf.mmm.util.nls.api.NlsAccess;
import net.sf.mmm.util.nls.api.NlsMessage;
import net.sf.mmm.util.nls.api.NlsObject;

/**
 * The {@link Enum} with the available features of rich-text formatting options.
 */
public enum RichTextFeature implements NlsObject {

  // TODO: Horizontal Rule

  /** The {@link RichTextFeature} to toggle <b>bold</b> text. */
  BOLD("Bold") {

    @Override
    public NlsMessage toNlsMessage() {

      return getBundle().labelRichTextBold();
    }
  },

  /** The {@link RichTextFeature} to toggle <i>italic</i> text. */
  ITALIC("Italic") {

    @Override
    public NlsMessage toNlsMessage() {

      return getBundle().labelRichTextItalic();
    }
  },

  /** The {@link RichTextFeature} to toggle <u>underlined</u> text. */
  UNDERLINE("Underline") {

    @Override
    public NlsMessage toNlsMessage() {

      return getBundle().labelRichTextUnderline();
    }
  },

  /** The {@link RichTextFeature} to toggle <sub>subscript</sub> text. */
  SUBSCRIPT("Subscript") {

    @Override
    public NlsMessage toNlsMessage() {

      return getBundle().labelRichTextSubscript();
    }
  },

  /** The {@link RichTextFeature} to toggle <sup>superscript</sup> text. */
  SUPERSCRIPT("Superscript") {

    @Override
    public NlsMessage toNlsMessage() {

      return getBundle().labelRichTextSuperscript();
    }
  },

  /** The {@link RichTextFeature} to toggle <strike>strikethrough</strike> text. */
  STRIKETHROUGH("Strikethrough") {

    @Override
    public NlsMessage toNlsMessage() {

      return getBundle().labelRichTextStrikethrough();
    }
  },

  /**
   * The {@link RichTextFeature} to change the text {@link net.sf.mmm.util.text.api.Justification} to
   * {@link net.sf.mmm.util.lang.api.HorizontalAlignment#LEFT}.
   */
  ALIGN_LEFT("AlignLeft") {

    @Override
    public NlsMessage toNlsMessage() {

      return getBundle().labelRichTextJustifyLeft();
    }
  },

  /**
   * The {@link RichTextFeature} to change the text {@link net.sf.mmm.util.text.api.Justification} to
   * {@link net.sf.mmm.util.lang.api.HorizontalAlignment#CENTER}.
   */
  ALIGN_CENTER("AlignCenter") {

    /**
     * {@inheritDoc}
     */
    @Override
    public NlsMessage toNlsMessage() {

      return getBundle().labelRichTextJustifyCenter();
    }
  },

  /**
   * The {@link RichTextFeature} to change the text {@link net.sf.mmm.util.text.api.Justification} to
   * {@link net.sf.mmm.util.lang.api.HorizontalAlignment#RIGHT}.
   */
  ALIGN_RIGHT("AlignRight") {

    @Override
    public NlsMessage toNlsMessage() {

      return getBundle().labelRichTextJustifyRight();
    }
  },

  /**
   * The {@link RichTextFeature} to toggle an ordered list. E.g.:
   * <ol>
   * <li>fist item</li>
   * <li>second item</li>
   * </ol>
   */
  ORDERED_LIST("OrderedList") {

    @Override
    public NlsMessage toNlsMessage() {

      return getBundle().labelRichTextOrderedList();
    }
  },

  /**
   * The {@link RichTextFeature} to toggle an unordered list. E.g.:
   * <ul>
   * <li>fist item</li>
   * <li>second item</li>
   * </ul>
   */
  UNORDERED_LIST("UnorderedList") {

    @Override
    public NlsMessage toNlsMessage() {

      return getBundle().labelRichTextUnorderedList();
    }
  },

  /** The {@link RichTextFeature} to insert <img src="broken">images</img>. */
  INSERT_IMAGE("InsertImage") {

    @Override
    public NlsMessage toNlsMessage() {

      return getBundle().labelRichTextInsertImage();
    }
  },

  /** The {@link RichTextFeature} to insert <a href="http://m-m-m.sourceforge.net">links</a>. */
  INSERT_LINK("InsertLink") {

    @Override
    public NlsMessage toNlsMessage() {

      return getBundle().labelRichTextInsertLink();
    }
  },

  /**
   * The {@link RichTextFeature} to change the <span style="font-family:Monospace">font</span> <span
   * style="font-family:Verdana">family</span> of the text.
   */
  FONT_FAMILY("FontFamily") {

    /**
     * {@inheritDoc}
     */
    @Override
    public NlsMessage toNlsMessage() {

      return getBundle().labelRichTextFontFamily();
    }
  },

  /**
   * The {@link RichTextFeature} to change the <span style="font-size:8px">font</span> <span
   * style="font-size:16px">size</span> of the text.
   */
  FONT_SIZE("FontSize") {

    @Override
    public NlsMessage toNlsMessage() {

      return getBundle().labelRichTextFontSize();
    }
  },

  /** The {@link RichTextFeature} to change the <span style="color: red">text color</span>. */
  TEXT_COLOR("TextColor") {

    @Override
    public NlsMessage toNlsMessage() {

      return getBundle().labelRichTextFontColor();
    }
  },

  /** The {@link RichTextFeature} to change the <span style="background: cyan">background color</span>. */
  BACKGROUND_COLOR("BackgroundColor") {

    @Override
    public NlsMessage toNlsMessage() {

      return getBundle().labelRichTextBackgroundColor();
    }
  },

  /** The {@link RichTextFeature} to change remove all formatting (styling markup) from the text. */
  REMOVE_FORMAT("RemoveFormat") {

    @Override
    public NlsMessage toNlsMessage() {

      return getBundle().labelRichTextRemoveFormat();
    }
  },

  /** The {@link RichTextFeature} to indent text. */
  INDENT("Indent") {

    @Override
    public NlsMessage toNlsMessage() {

      return getBundle().labelRichTextIndent();
    }
  },

  /** The {@link RichTextFeature} to outdent text. */
  OUTDENT("Outdent") {

    @Override
    public NlsMessage toNlsMessage() {

      return getBundle().labelRichTextOutdent();
    }
  },

  /** The {@link RichTextFeature} to undo the last change. */
  UNDO("Undo") {

    @Override
    public NlsMessage toNlsMessage() {

      return getBundle().labelRichTextUndo();
    }
  },

  /** The {@link RichTextFeature} to undo the re-do a change after an {@link #UNDO undo}. */
  REDO("Redo") {

    @Override
    public NlsMessage toNlsMessage() {

      return getBundle().labelRichTextRedo();
    }
  };

  /** @see #getBundle() */
  private static NlsBundleClientUiRoot bundle;

  /** @see #getStyle() */
  private final String style;

  /**
   * The constructor.
   * 
   * @param style is the {@link #getStyle() style}.
   */
  private RichTextFeature(String style) {

    this.style = style;
  }

  /**
   * @return the CSS style name of the button for this feature.
   */
  public String getStyle() {

    return this.style;
  }

  /**
   * @return the instance of {@link NlsBundleClientUiRoot}.
   */
  private static NlsBundleClientUiRoot getBundle() {

    if (bundle == null) {
      bundle = NlsAccess.getBundleFactory().createBundle(NlsBundleClientUiRoot.class);
    }
    return bundle;
  }

}
