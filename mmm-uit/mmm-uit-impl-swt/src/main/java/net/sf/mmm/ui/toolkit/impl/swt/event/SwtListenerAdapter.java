/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swt.event;

import net.sf.mmm.ui.toolkit.api.event.UiEventType;
import net.sf.mmm.ui.toolkit.base.view.AbstractUiNode;

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
  private AbstractUiNode<?> object;

  /**
   * The constructor.
   * 
   * @param uiObject is the UI object that registers this listener and will send
   *        the events to registered listeners.
   */
  public SwtListenerAdapter(AbstractUiNode<?> uiObject) {

    super();
    this.object = uiObject;
  }

  /**
   * {@inheritDoc}
   */
  public void handleEvent(Event event) {

    switch (event.type) {
      case SWT.Selection:
        this.object.sendEvent(UiEventType.CLICK);
        break;
      case SWT.Hide:
        this.object.sendEvent(UiEventType.HIDE);
        break;
      case SWT.Show:
        this.object.sendEvent(UiEventType.SHOW);
        break;
      case SWT.Iconify:
        this.object.sendEvent(UiEventType.ICONIFY);
        break;
      case SWT.Deiconify:
        this.object.sendEvent(UiEventType.DEICONIFY);
        break;
      case SWT.Activate:
        this.object.sendEvent(UiEventType.ACTIVATE);
        break;
      case SWT.Deactivate:
        this.object.sendEvent(UiEventType.DEACTIVATE);
        break;
      case SWT.Dispose:
        this.object.sendEvent(UiEventType.DISPOSE);
        break;
      default :
        return;
    }
  }

}
