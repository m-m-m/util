/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swt.model;

import org.eclipse.swt.widgets.Slider;

import net.sf.mmm.ui.toolkit.api.event.UIListModelEvent;
import net.sf.mmm.ui.toolkit.api.event.UIListModelListener;
import net.sf.mmm.ui.toolkit.api.model.data.UiListMvcModel;
import net.sf.mmm.ui.toolkit.impl.swt.view.sync.SyncSliderAccess;
import net.sf.mmm.util.event.api.ChangeType;

/**
 * This class adapts from {@link net.sf.mmm.ui.toolkit.api.model.data.UiListMvcModel} to
 * a {@link org.eclipse.swt.widgets.Slider}. It is the controler of the MVC
 * pattern.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class SlideBarModelAdapter implements UIListModelListener, Runnable {

  /** sync access to the slider */
  private final SyncSliderAccess syncAccess;

  /** the list model to adapt */
  private UiListMvcModel model;

  /** the current event to handle */
  private UIListModelEvent event;

  /**
   * The constructor.
   * 
   * @param sliderAccess gives synchron access on the slider.
   */
  public SlideBarModelAdapter(SyncSliderAccess sliderAccess) {

    super();
    this.syncAccess = sliderAccess;
  }

  /**
   * 
   * @param newModel
   */
  public void setModel(UiListMvcModel newModel) {

    if (this.model != null) {
      this.model.removeListener(this);
    }
    this.model = newModel;
    initialize();
  }

  /**
   * This method initializes the slider after it has been creation or the model
   * changed.
   */
  public void initialize() {

    if (this.syncAccess.getDelegate() != null) {
      this.event = null;
      this.syncAccess.getDisplay().invokeSynchron(this);
      this.model.addListener(this);
    }
  }

  /**
   * {@inheritDoc}
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
      Slider slider = this.syncAccess.getDelegate();
      slider.setMaximum(this.model.getElementCount());
    } else {
      if (this.event.getType() != ChangeType.UPDATE) {
        Slider slider = this.syncAccess.getDelegate();
        slider.setMaximum(this.model.getElementCount());
      }
    }
  }

}
