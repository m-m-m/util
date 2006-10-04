/* $Id$ */
package net.sf.mmm.ui.toolkit.impl.swt.model;

import org.eclipse.swt.widgets.Slider;

import net.sf.mmm.ui.toolkit.api.event.EventType;
import net.sf.mmm.ui.toolkit.api.event.UIListModelEvent;
import net.sf.mmm.ui.toolkit.api.event.UIListModelListenerIF;
import net.sf.mmm.ui.toolkit.api.model.UIListModelIF;
import net.sf.mmm.ui.toolkit.impl.swt.sync.SyncSliderAccess;

/**
 * This class adapts from {@link net.sf.mmm.ui.toolkit.api.model.UIListModelIF}
 * to a {@link org.eclipse.swt.widgets.Slider}. It is the controler of the MVC
 * pattern.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class SlideBarModelAdapter implements UIListModelListenerIF, Runnable {

    /** sync access to the slider */
    private final SyncSliderAccess syncAccess;

    /** the list model to adapt */
    private UIListModelIF model;

    /** the current event to handle */
    private UIListModelEvent event;

    /**
     * The constructor.
     * 
     * @param sliderAccess
     *        gives synchron access on the slider.
     */
    public SlideBarModelAdapter(SyncSliderAccess sliderAccess) {

        super();
        this.syncAccess = sliderAccess;
    }

    /**
     * 
     * @param newModel
     */
    public void setModel(UIListModelIF newModel) {

        if (this.model != null) {
            this.model.removeListener(this);
        }
        this.model = newModel;
        initialize();
    }

    /**
     * This method initializes the slider after it has been creation or the
     * model changed.
     */
    public void initialize() {

        if (this.syncAccess.getSwtObject() != null) {
            this.event = null;
            this.syncAccess.getDisplay().invokeSynchron(this);
            this.model.addListener(this);
        }
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.event.UIListModelListenerIF#listModelChanged(net.sf.mmm.ui.toolkit.api.event.UIListModelEvent)
     * 
     */
    public void listModelChanged(UIListModelEvent changeEvent) {

        this.event = changeEvent;
        this.syncAccess.getDisplay().invokeSynchron(this);
    }

    /**
     * This method is called synchron to handle an model update event.
     * 
     * @see java.lang.Runnable#run()
     */
    public void run() {

        if (this.event == null) {
            // initialize
            Slider slider = this.syncAccess.getSwtObject();
            slider.setMaximum(this.model.getElementCount());
        } else {
            if (this.event.getType() != EventType.UPDATE) {
                Slider slider = this.syncAccess.getSwtObject();
                slider.setMaximum(this.model.getElementCount());
            }
        }
    }

}
