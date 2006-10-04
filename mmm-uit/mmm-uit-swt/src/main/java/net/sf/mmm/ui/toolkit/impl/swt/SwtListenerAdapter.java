/* $Id$ */
package net.sf.mmm.ui.toolkit.impl.swt;

import net.sf.mmm.ui.toolkit.api.event.ActionType;
import net.sf.mmm.ui.toolkit.base.UIAbstractNode;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

/**
 * This class is the SWT listener implementation that 
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class SwtListenerAdapter implements Listener {

    /** the component that triggers the event */
    private UIAbstractNode object;

    /**
     * The constructor.
     * 
     * @param uiObject 
     *          is the UI object that registers this listener and will send the
     *          events to registered listeners.
     */
    public SwtListenerAdapter(UIAbstractNode uiObject) {
        super();
        this.object = uiObject;
    }

    /**
     * @see org.eclipse.swt.widgets.Listener#handleEvent(org.eclipse.swt.widgets.Event)
     * 
     */
    public void handleEvent(Event event) {
        switch (event.type) {
            case SWT.Selection :
                this.object.invoke(ActionType.SELECT);
                break;
            case SWT.Hide :
                this.object.invoke(ActionType.HIDE);
                break;
            case SWT.Show :
                this.object.invoke(ActionType.SHOW);
                break;
            case SWT.Iconify :
                this.object.invoke(ActionType.ICONIFY);
                break;
            case SWT.Deiconify :
                this.object.invoke(ActionType.DEICONIFY);
                break;
            case SWT.Activate :
                this.object.invoke(ActionType.ACTIVATE);
                break;
            case SWT.Deactivate :
                this.object.invoke(ActionType.DEACTIVATE);
                break;
            case SWT.Dispose :
                this.object.invoke(ActionType.DISPOSE);
                break;
            default :
                return;
        }
    }

}
