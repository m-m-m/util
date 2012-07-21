/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.widget;

import net.sf.mmm.ui.toolkit.api.attribute.AttributeWriteEnabled;
import net.sf.mmm.ui.toolkit.api.attribute.AttributeWriteHtmlId;
import net.sf.mmm.ui.toolkit.api.attribute.AttributeWriteStyles;
import net.sf.mmm.ui.toolkit.api.attribute.AttributeWriteTooltip;
import net.sf.mmm.ui.toolkit.api.attribute.AttributeWriteVisible;

/**
 * This is the interface for an adapter to a physical <em>widget</em> (or potentially a composition of such
 * widgets) of the underlying native widget toolkit.<br/>
 * A widget is any object of the UI (user interface) including both {@link UiWidgetAtomic atomic widgets} as
 * well a {@link UiWidgetComposite composite widgets}. <br/>
 * Real {@link UiWidget}s can be {@link UiWidgetFactory#create(Class) created} via the {@link UiWidgetFactory}
 * so by choosing the implementation of {@link UiWidgetFactory} (via an IoC container framework like spring or
 * GIN) you can decide which native toolkit you like to use.<br/>
 * <b>ATTENTION:</b><br/>
 * A {@link UiWidget} can only be used once in a client application. Even if you want to have the exact same
 * button twice on the screen you need to create two distinct instances. <br/>
 * <br/>
 * For advanced usage see also <code>net.sf.mmm.ui.toolkit.api.element.UiElement</code>.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface UiWidget extends AttributeWriteHtmlId, AttributeWriteVisible, AttributeWriteTooltip,
    AttributeWriteEnabled, AttributeWriteStyles {

  /**
   * @return the parent of this widget or <code>null</code> if NOT attached to the UI or if this is a root
   *         widget (e.g. {@link net.sf.mmm.ui.toolkit.api.widget.window.UiWidgetMainWindow}).
   */
  UiWidgetComposite<?> getParent();

}
