/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.base;

import net.sf.mmm.ui.toolkit.api.UIFactory;
import net.sf.mmm.ui.toolkit.api.UIObject;

/**
 * This is the abstract base implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.UIObject} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractUIObject implements UIObject {

  /** the factory instance */
  private UIFactory factory;

  /** the {@link #getId() id} of the object */
  private String id;

  /** the static counter */
  private static int staticCounter = 0;

  /**
   * The constructor.
   * 
   * @param uiFactory
   *        is the
   *        {@link net.sf.mmm.ui.toolkit.api.UIObject#getFactory() factory}
   *        instance.
   */
  public AbstractUIObject(UIFactory uiFactory) {

    super();
    this.factory = uiFactory;
    this.id = "obj" + staticCounter++;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.UIObject#getFactory()
   */
  public UIFactory getFactory() {

    return this.factory;
  }

  /**
   * Override this method if you implement a window object.
   * 
   * @see net.sf.mmm.ui.toolkit.api.UIObject#isWindow()
   */
  public boolean isWindow() {

    return false;
  }

  /**
   * Override this method if you implement a widget object.
   * 
   * @see net.sf.mmm.ui.toolkit.api.UIObject#isWidget()
   */
  public boolean isWidget() {

    return false;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.UIObject#getId()
   */
  public String getId() {

    return this.id;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.UIObject#setId(java.lang.String)
   */
  public void setId(String newId) {

    this.id = newId;
  }

}
