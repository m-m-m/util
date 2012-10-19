/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.base.widget.adapter;

/**
 * This is a simple manager that generates unique IDs for radio-button-groups.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class RadioGroupIdManager {

  /** @see #getInstance() */
  private static RadioGroupIdManager instance;

  /** The ID counter. */
  private int counter;

  /**
   * The constructor.
   */
  public RadioGroupIdManager() {

    super();
  }

  /**
   * @return the next unique radio-button-group ID.
   */
  public String getUniqueId() {

    return "radiogroup" + this.counter++;
  }

  /**
   * @return the instance
   */
  public static RadioGroupIdManager getInstance() {

    if (instance == null) {
      instance = new RadioGroupIdManager();
    }
    return instance;
  }

  /**
   * @param instance is the instance to set
   */
  protected static void setInstance(RadioGroupIdManager instance) {

    RadioGroupIdManager.instance = instance;
  }

}
