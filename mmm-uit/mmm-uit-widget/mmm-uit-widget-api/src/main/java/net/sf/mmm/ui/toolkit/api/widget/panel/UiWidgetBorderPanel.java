/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.widget.panel;

import net.sf.mmm.ui.toolkit.api.widget.UiWidgetReal;
import net.sf.mmm.ui.toolkit.api.widget.UiWidgetRegular;
import net.sf.mmm.ui.toolkit.api.widget.UiWidgetSingleComposite;
import net.sf.mmm.util.lang.api.attribute.AttributeWriteTitle;

/**
 * This is the interface for a {@link UiWidgetPanel panel widget} that shows a single {@link #getChild()
 * child} that is surrounded by a {@link #getTitle() titled} border.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiWidgetBorderPanel extends UiWidgetPanel<UiWidgetRegular>, UiWidgetSingleComposite<UiWidgetRegular>,
    AttributeWriteTitle<String>, UiWidgetReal {

  // nothing to add

}
