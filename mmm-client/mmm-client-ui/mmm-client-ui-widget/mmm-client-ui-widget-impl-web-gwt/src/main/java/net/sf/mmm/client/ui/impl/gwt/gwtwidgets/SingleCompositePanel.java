/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.gwtwidgets;

import java.util.Iterator;

import net.sf.mmm.util.collection.base.SingleElementIterator;

import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

/**
 * This is a custom {@link Widget} that represents a {@link Panel} owing a single child.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class SingleCompositePanel extends Panel {

  /** @see #setChild(Widget) */
  private Widget childWidget;

  /**
   * The constructor.
   */
  public SingleCompositePanel() {

    super();
  }

  /**
   * @param child is the Widget to set as child. A potential previous child will be removed.
   */
  public void setChild(Widget child) {

    if (this.childWidget != null) {
      remove(this.childWidget);
    }
    if (child != null) {
      add(child);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Iterator<Widget> iterator() {

    return new SingleElementIterator<Widget>(this.childWidget);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void add(Widget child) {

    if (this.childWidget == null) {
      getElement().appendChild(child.getElement());
      this.childWidget = child;
      adopt(child);
    } else {
      throw new IllegalStateException("Multiple children not supported!");
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean remove(Widget child) {

    if (child == null) {
      return false;
    }
    if (this.childWidget == child) {
      orphan(child);
      getElement().removeChild(child.getElement());
      this.childWidget = null;
      return true;
    }
    return false;
  }

}
