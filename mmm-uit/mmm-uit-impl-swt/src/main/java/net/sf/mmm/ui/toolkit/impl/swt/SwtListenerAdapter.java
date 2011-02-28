/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swt;

import net.sf.mmm.ui.toolkit.api.event.UiEventType;
import net.sf.mmm.ui.toolkit.base.AbstractUiNode;

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
  private AbstractUiNode object;

  /**
   * The constructor.
   * 
   * @param uiObject is the UI object that registers this listener and will send
   *        the events to registered listeners.
   */
  public SwtListenerAdapter(AbstractUiNode uiObject) {

    super();
    this.object = uiObject;
  }

  /**
   * {@inheritDoc}
   */
  public void handleEvent(Event event) {

    switch (event.type) {
      case SWT.Selection:
        this.object.fireEvent(UiEventType.CLICK);
        break;
      case SWT.Hide:
        this.object.fireEvent(UiEventType.HIDE);
        break;
      case SWT.Show:
        this.object.fireEvent(UiEventType.SHOW);
        break;
      case SWT.Iconify:
        this.object.fireEvent(UiEventType.ICONIFY);
        break;
      case SWT.Deiconify:
        this.object.fireEvent(UiEventType.DEICONIFY);
        break;
      case SWT.Activate:
        this.object.fireEvent(UiEventType.ACTIVATE);
        break;
      case SWT.Deactivate:
        this.object.fireEvent(UiEventType.DEACTIVATE);
        break;
      case SWT.Dispose:
        this.object.fireEvent(UiEventType.DISPOSE);
        break;
      default :
        return;
    }
  }

}
