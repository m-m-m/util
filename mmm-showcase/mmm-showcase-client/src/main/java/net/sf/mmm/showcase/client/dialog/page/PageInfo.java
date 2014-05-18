/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.showcase.client.dialog.page;

import net.sf.mmm.client.ui.api.dialog.DialogPlace;

/**
 * This is a simple container for the information about a page of the showcase. It is used to display the
 * various features and being able to navigate to their {@link #getPlace() place}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class PageInfo {

  /** @see #getTitle() */
  private String title;

  /** @see #getPlace() */
  private DialogPlace place;

  /**
   * The constructor.
   *
   * @param title is the {@link #getTitle() title}.
   * @param place is the {@link #getPlace() place}.
   */
  public PageInfo(String title, DialogPlace place) {

    super();
    this.title = title;
    this.place = place;
  }

  /**
   * @return the title used to display this {@link PageInfo}.
   */
  public String getTitle() {

    return this.title;
  }

  /**
   * @return the {@link DialogPlace} identifying the associated page. You can
   *         {@link net.sf.mmm.client.ui.api.dialog.DialogManager#navigateTo(DialogPlace) navigate to} this
   *         place when this {@link PageInfo} is selected.
   */
  public DialogPlace getPlace() {

    return this.place;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    return this.title;
  }

}
