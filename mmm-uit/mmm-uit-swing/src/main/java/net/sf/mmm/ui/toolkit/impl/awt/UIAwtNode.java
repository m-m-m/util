/* $Id$ */
package net.sf.mmm.ui.toolkit.impl.awt;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import net.sf.mmm.ui.toolkit.api.UIFactoryIF;
import net.sf.mmm.ui.toolkit.api.UINodeIF;
import net.sf.mmm.ui.toolkit.api.event.ActionType;
import net.sf.mmm.ui.toolkit.base.UIAbstractNode;

/**
 * This is the abstract base implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.UINodeIF} interface for AWT/Swing
 * implementations.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class UIAwtNode extends UIAbstractNode {

    /**
     * The constructor.
     * 
     * @param uiFactory
     *        is the UIFactory instance.
     * @param parentObject
     *        is the parent of this object (may be <code>null</code>).
     */
    public UIAwtNode(UIFactoryIF uiFactory, UINodeIF parentObject) {

        super(uiFactory, parentObject);
    }

    /**
     * This method creates a default AWT action-listener that adapts the
     * events.
     * 
     * @return the new listener.
     */
    protected ActionListener createActionListener() {

        ActionListener listener = new ActionListener() {

            /**
             * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
             * 
     */
            public void actionPerformed(ActionEvent e) {

                invoke(ActionType.SELECT);
            }

        };
        return listener;
    }

    /**
     * This method creates a default AWT change-listener that adapts the
     * events.
     * 
     * @return the new listener.
     */
    protected ChangeListener createChangeListener() {
        
        ChangeListener listener = new ChangeListener() {

            /**
             * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
             * 
     */
            public void stateChanged(ChangeEvent e) {

                invoke(ActionType.SELECT);
            }
            
        };
        return listener;
    }
    
}
