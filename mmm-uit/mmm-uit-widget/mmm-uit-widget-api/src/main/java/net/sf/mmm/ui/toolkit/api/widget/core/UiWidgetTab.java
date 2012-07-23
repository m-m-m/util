/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.widget.core;

import net.sf.mmm.ui.toolkit.api.attribute.AttributeWriteImage;
import net.sf.mmm.ui.toolkit.api.attribute.AttributeWriteLabel;
import net.sf.mmm.ui.toolkit.api.widget.UiWidgetReal;
import net.sf.mmm.ui.toolkit.api.widget.UiWidgetRegular;
import net.sf.mmm.ui.toolkit.api.widget.UiWidgetSingleComposite;

/**
 * This is the interface for a {@link UiWidgetSingleComposite single composite widget} that represents a
 * single <code>tab</code>. In order to
 * {@link net.sf.mmm.ui.toolkit.api.widget.panel.UiWidgetTabPanel#addChild(UiWidgetTab) add} a something like
 * a {@link net.sf.mmm.ui.toolkit.api.widget.panel.UiWidgetPanel panel} to a
 * {@link net.sf.mmm.ui.toolkit.api.widget.panel.UiWidgetTabPanel tab panel}, you need to create an
 * {@link UiWidgetTab} {@link #getChild() containing} that
 * {@link net.sf.mmm.ui.toolkit.api.widget.panel.UiWidgetTabPanel panel} and add it to the
 * {@link net.sf.mmm.ui.toolkit.api.widget.panel.UiWidgetTabPanel tab panel}.
 * 
 * @see net.sf.mmm.ui.toolkit.api.widget.panel.UiWidgetTabPanel
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiWidgetTab extends UiWidgetSingleComposite<UiWidgetRegular>, AttributeWriteLabel,
    AttributeWriteImage<UiWidgetImage>, UiWidgetReal {

  // nothing to add

}
