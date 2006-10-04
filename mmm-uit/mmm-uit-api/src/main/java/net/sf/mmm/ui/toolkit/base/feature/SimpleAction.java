/* $Id$ */
package net.sf.mmm.ui.toolkit.base.feature;

import net.sf.mmm.ui.toolkit.api.UIPictureIF;
import net.sf.mmm.ui.toolkit.api.event.UIActionListenerIF;

/**
 * This is a simple implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.feature.ActionIF} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class SimpleAction extends AbstractAction {

    /** the {@link #getActionListener() listener} */
    private UIActionListenerIF listener;

    /**
     * The constructor.
     * 
     * @param actionListener
     *        is the {@link #getActionListener() listener}.
     * @param displayName
     *        is the {@link #getName() name}.
     */
    public SimpleAction(UIActionListenerIF actionListener, String displayName) {

        this(actionListener, displayName, null);
    }

    /**
     * The constructor.
     * 
     * @param actionListener
     *        is the {@link #getActionListener() listener}.
     * @param displayName
     *        is the {@link #getName() name}.
     * @param displayIcon
     *        is the {@link #getIcon() icon}.
     */
    public SimpleAction(UIActionListenerIF actionListener, String displayName,
            UIPictureIF displayIcon) {

        super(displayName);
        this.listener = actionListener;
    }


    /**
     * This method sets the {@link #getActionListener() listener}.
     * 
     * @param newListener
     *        is the new listener to set.
     */
    public void setActionListener(UIActionListenerIF newListener) {

        this.listener = newListener;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.feature.ActionIF#getActionListener()
     * 
     */
    public UIActionListenerIF getActionListener() {

        return this.listener;
    }

}
