/* $Id: UIAbstractNode.java 191 2006-07-24 21:00:49Z hohwille $ */
package net.sf.mmm.ui.toolkit.base;

import java.util.ArrayList;
import java.util.List;

import net.sf.mmm.ui.toolkit.api.UIFactoryIF;
import net.sf.mmm.ui.toolkit.api.UINodeIF;
import net.sf.mmm.ui.toolkit.api.event.ActionType;
import net.sf.mmm.ui.toolkit.api.event.UIActionListenerIF;
import net.sf.mmm.ui.toolkit.api.window.UIFrameIF;
import net.sf.mmm.ui.toolkit.api.window.UIWindowIF;

/**
 * This is the abstract base implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.UINodeIF} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class UIAbstractNode extends UIAbstractObject implements UINodeIF {

    /** the parent object */
    private UINodeIF parent;

    /**
     * the registered listeners (or <code>null</code> if no listener is
     * registered)
     */
    private List<UIActionListenerIF> listeners;

    /**
     * The constructor.
     * 
     * @param uiFactory
     *        is the
     *        {@link net.sf.mmm.ui.toolkit.api.UIObjectIF#getFactory() factory}
     *        instance.
     * @param parentObject
     *        is the
     *        {@link net.sf.mmm.ui.toolkit.api.UINodeIF#getParent() parent} that
     *        created this object. It may be <code>null</code>.
     */
    public UIAbstractNode(UIFactoryIF uiFactory, UINodeIF parentObject) {

        super(uiFactory);
        this.parent = parentObject;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.UINodeIF#getParent()
     * {@inheritDoc}
     */
    public UINodeIF getParent() {

        return this.parent;
    }

    /**
     * This method sets the parent of this UI object. This is neccessary if an
     * object was constructed by this factory (parent is <code>null</code>)
     * and is then added to a UI composite (or whenever it moves from one
     * composite to another).
     * 
     * @param newParent
     *        is the new parent of this object.
     */
    public void setParent(UINodeIF newParent) {

        this.parent = newParent;
    }

    /**
     * This method sets the parent of the given <code>chhildObject</code> to
     * the given <code>parentObject</code>. The method only exists for
     * visibility reasons (so the <code>setParent</code> method can be
     * protected).
     * 
     * @param childObject
     *        is the UI object whos parent is to be set.
     * @param parentObject
     *        is the parent UI object to set.
     */
    protected void setParent(UIAbstractNode childObject, UINodeIF parentObject) {

        /*
         * UINodeIF oldParent = childObject.getParent(); if (oldParent != null) {
         * if (oldParent.isComposite()) { ((UIComposite)
         * oldParent).removeComponent(childObject); } }
         */
        childObject.setParent(parentObject);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.UINodeIF#getParentFrame()
     * {@inheritDoc}
     */
    public UIFrameIF getParentFrame() {

        if (getType() == UIFrameIF.TYPE) {
            if (getParent() == null) {
                return null;
            } else {
                // the only legal parent of frame is another frame.
                return ((UIFrameIF) getParent());
            }
        } else {
            if (getParent() == null) {
                return null;
            } else if (getParent().getType() == UIFrameIF.TYPE) {
                return ((UIFrameIF) getParent());
            } else {
                return getParent().getParentFrame();
            }
        }
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.UINodeIF#getParentWindow()
     * {@inheritDoc}
     */
    public UIWindowIF getParentWindow() {

        if (isWindow()) {
            if (getParent() == null) {
                return null;
            } else {
                return ((UIWindowIF) getParent());
            }
        } else {
            if (getParent() == null) {
                return null;
            } else if (getParent().isWindow()) {
                return ((UIWindowIF) getParent());
            } else {
                return getParent().getParentWindow();
            }
        }
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.UINodeIF#addActionListener(net.sf.mmm.ui.toolkit.api.event.UIActionListenerIF)
     * {@inheritDoc}
     */
    public void addActionListener(UIActionListenerIF listener) {

        synchronized (this) {
            if (this.listeners == null) {
                if (!doInitializeListener()) {
                    return;
                }
                this.listeners = new ArrayList<UIActionListenerIF>();
            }
            this.listeners.add(listener);
        }
    }

    /**
     * This method registers the listener adapter in the wrapped UI object. It
     * is called only once when the first listener is registered.
     * 
     * @return <code>false</code> if this UI object will never send any event
     *         therefore no listeners must be registered. If you override this
     *         method to initialize the listener adapter, you should return
     *         <code>true</code>.
     */
    protected boolean doInitializeListener() {

        return false;
    }

    /**
     * This method invokes an action by notifying all registered listeners.
     * 
     * @param action
     *        is the action that is invoked.
     */
    public void invoke(ActionType action) {

        if (this.listeners != null) {
            for (int i = 0; i < this.listeners.size(); i++) {
                try {
                    this.listeners.get(i).invoke(this, action);
                } catch (Throwable t) {
                    t.printStackTrace();
                }
            }
        }
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.UINodeIF#removeActionListener(net.sf.mmm.ui.toolkit.api.event.UIActionListenerIF)
     * {@inheritDoc}
     */
    public void removeActionListener(UIActionListenerIF listener) {

        synchronized (this) {
            if (this.listeners != null) {
                this.listeners.remove(listener);
            }
        }
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.UINodeIF#refresh()
     * {@inheritDoc}
     */
    public void refresh() {

        //do nothing by default...
    }

}
