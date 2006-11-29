/* $Id$ */
package net.sf.mmm.ui.toolkit.api;

import net.sf.mmm.ui.toolkit.api.state.UIReadPreferredSize;
import net.sf.mmm.ui.toolkit.api.state.UIWriteDisposed;
import net.sf.mmm.ui.toolkit.api.state.UIWriteEnabled;
import net.sf.mmm.ui.toolkit.api.state.UIWritePosition;
import net.sf.mmm.ui.toolkit.api.state.UIWriteSize;
import net.sf.mmm.ui.toolkit.api.state.UIWriteTooltip;

/**
 * This is the interface for a UI component. Such object is eigther a
 * {@link net.sf.mmm.ui.toolkit.api.widget.UIWidget widget} or a
 * {@link net.sf.mmm.ui.toolkit.api.composite.UIComposite composite}
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface UIComponent extends UINode, UIWriteTooltip, UIWriteEnabled, UIWriteSize,
    UIReadPreferredSize, UIWritePosition, UIWriteDisposed {

  // get/set cursor type

}
