/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.test.widget.panel.adapter;

import net.sf.mmm.client.ui.api.widget.core.UiWidgetTab;
import net.sf.mmm.client.ui.base.widget.panel.adapter.UiWidgetAdapterTabPanel;
import net.sf.mmm.client.ui.impl.test.widget.adapter.UiWidgetAdapterTestComposite;

/**
 * This is the implementation of {@link UiWidgetAdapterTabPanel} for testing without a native toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetAdapterTestTabPanel extends UiWidgetAdapterTestComposite<UiWidgetTab> implements
    UiWidgetAdapterTabPanel {

  /**
   * The constructor.
   */
  public UiWidgetAdapterTestTabPanel() {

    super();
  }

}
