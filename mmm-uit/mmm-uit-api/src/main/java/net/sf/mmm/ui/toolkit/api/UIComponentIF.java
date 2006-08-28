/* $Id: UIComponentIF.java 191 2006-07-24 21:00:49Z hohwille $ */
package net.sf.mmm.ui.toolkit.api;

import net.sf.mmm.ui.toolkit.api.state.UIReadPreferredSizeIF;
import net.sf.mmm.ui.toolkit.api.state.UIWriteDisposedIF;
import net.sf.mmm.ui.toolkit.api.state.UIWriteEnabledIF;
import net.sf.mmm.ui.toolkit.api.state.UIWriteSizeIF;
import net.sf.mmm.ui.toolkit.api.state.UIWriteTooltipIF;

/**
 * This is the interface for a UI component. Such object is eigther a
 * {@link net.sf.mmm.ui.toolkit.api.widget.UIWidgetIF widget} or a
 * {@link net.sf.mmm.ui.toolkit.api.composite.UICompositeIF composite}
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface UIComponentIF extends UINodeIF, UIWriteTooltipIF, UIWriteEnabledIF, UIWriteSizeIF,
        UIReadPreferredSizeIF, UIWriteDisposedIF {

    // get/set cursor type

}
