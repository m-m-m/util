/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.base.model;

import net.sf.mmm.ui.toolkit.api.event.UIListModelEvent;
import net.sf.mmm.ui.toolkit.api.model.UIMutableListModel;
import net.sf.mmm.util.event.api.ChangeEventType;

/**
 * This is the abstract base implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.model.UIMutableListModel} interface.
 * 
 * @param <E> is the templated type of the elements in the list.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractUIMutableListModel<E> extends AbstractUIListModel<E> implements
    UIMutableListModel<E> {

  /**
   * The constructor.
   */
  public AbstractUIMutableListModel() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  public void removeAll() {

    if (getElementCount() > 0) {
      int lastIndex;
      synchronized (this) {
        lastIndex = getElementCount() - 1;
        for (int i = lastIndex; i >= 0; i--) {
          removeElement(i);
        }
      }
      fireChangeEvent(new UIListModelEvent(ChangeEventType.REMOVE, 0, lastIndex));
    }
  }

}
