/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.gwtwidgets;

import net.sf.mmm.client.ui.api.common.CssStyles;

import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.client.ui.Button;

/**
 * This is a variant of {@link Button} with custom settings (using {@link CssStyles#BUTTON}). For security the
 * {@link #ButtonWidget(String) string constructor} treats the input as text instead of HTML.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class ButtonWidget extends Button {

  /**
   * The constructor.
   */
  public ButtonWidget() {

    super();
    setStyleName(CssStyles.BUTTON);
  }

  /**
   * The constructor.
   * 
   * @param html is the {@link #setHTML(SafeHtml) HTML} used to label this button.
   */
  public ButtonWidget(SafeHtml html) {

    super(html);
    setStyleName(CssStyles.BUTTON);
  }

  /**
   * The constructor.
   * 
   * @param text is the {@link #setText(String) text} used to label this button.
   */
  public ButtonWidget(String text) {

    this();
    setText(text);
  }

}
