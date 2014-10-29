/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.gwt.widgets;

import net.sf.mmm.client.ui.api.common.CssStyles;

/**
 * This is a {@link HorizontalFlowPanel horizontal panel} for buttons. <br>
 * <b>ATTENTION:</b><br>
 * Technically you can add any kind of {@link com.google.gwt.user.client.ui.Widget} to this panel. However,
 * only {@link com.google.gwt.user.client.ui.Button}s, {@link ButtonGroup}s and related widgets shall be
 * added.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class ButtonPanel extends HorizontalFlowPanel {

  /**
   * The constructor.
   */
  public ButtonPanel() {

    super();
    addStyleName(CssStyles.BUTTON_PANEL);
  }

}
