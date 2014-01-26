/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.gwtwidgets;

import net.sf.mmm.client.ui.api.common.CssStyles;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.InlineLabel;

/**
 * This simple {@link com.google.gwt.user.client.ui.Widget} represents a <em>section</em>. A section is a
 * widget that displays a short text in a single line. The text is supposed to be a caption that explains the
 * content below.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class Section extends FlowPanel {

  /** The label of this section containing the title caption. */
  private final InlineLabel label;

  /**
   * The constructor.
   */
  public Section() {

    super();
    setStyleName(CssStyles.SECTION);
    this.label = new InlineLabel();
    add(this.label);
  }

  /**
   * The constructor.
   * 
   * @param caption is the title caption.
   */
  public Section(String caption) {

    this();
    this.label.setText(caption);
  }

  /**
   * @return the label with the title caption of this {@link Section}.
   */
  public InlineLabel getLabel() {

    return this.label;
  }

}
