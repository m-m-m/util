/* $Id$ */
package net.sf.mmm.ui.toolkit.api.menu;

import net.sf.mmm.ui.toolkit.api.UINodeIF;
import net.sf.mmm.ui.toolkit.api.state.UIReadTextIF;
import net.sf.mmm.ui.toolkit.api.state.UIWriteSelectionFlagIF;
import net.sf.mmm.ui.toolkit.api.widget.ButtonStyle;

/**
 * This is the interface for a menu-item.<br>
 * Use
 * {@link net.sf.mmm.ui.toolkit.api.menu.UIMenuIF#addItem(String, net.sf.mmm.ui.toolkit.api.event.UIActionListenerIF, ButtonStyle)}
 * to create a menu-item.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface UIMenuItemIF extends UINodeIF, UIWriteSelectionFlagIF, UIReadTextIF {

    /** the type of this object */
    String TYPE = "MenuItem";

    /**
     * This method gets the style of this item.
     * 
     * @return the items style.
     */
    ButtonStyle getStyle();

}
