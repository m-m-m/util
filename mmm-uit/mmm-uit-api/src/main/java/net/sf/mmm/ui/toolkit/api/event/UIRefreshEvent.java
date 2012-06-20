/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.event;

/**
 * This is the event send internally from the {@link net.sf.mmm.ui.toolkit.api.UiFactory factory} to all
 * {@link net.sf.mmm.ui.toolkit.base.view.AbstractUiNode UI-elements} in order to cause a refresh.
 * 
 * @see net.sf.mmm.ui.toolkit.base.view.AbstractUiNode#refresh(UIRefreshEvent)
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UIRefreshEvent {

  /** the event for a regular refresh */
  public static final UIRefreshEvent DEFAULT = new UIRefreshEvent();

  /** the event for a orientation change */
  public static final UIRefreshEvent ORIENTATION_MODIFIED = new UIRefreshEvent(true, false);

  /** the event for a locale change */
  public static final UIRefreshEvent LOCALE_MODIFIED = new UIRefreshEvent(true, false);

  /** @see #isOrientationModified() */
  private boolean orientationModified;

  /** @see #isOrientationModified() */
  private boolean localeModified;

  /**
   * The constructor.
   */
  public UIRefreshEvent() {

    super();
  }

  /**
   * The constructor.
   * 
   * @param orientationModified if <code>true</code> the
   *        {@link net.sf.mmm.ui.toolkit.api.UiFactory#getScriptOrientation() orientation} has been modified.
   * @param localeModified if <code>true</code> the {@link net.sf.mmm.ui.toolkit.api.UiFactory#getLocale()
   *        locale} has been modified.
   */
  public UIRefreshEvent(boolean orientationModified, boolean localeModified) {

    super();
    this.orientationModified = orientationModified;
    this.localeModified = localeModified;
  }

  /**
   * This method determines if the {@link net.sf.mmm.ui.toolkit.api.UiFactory#getScriptOrientation()
   * orientation} has been modified since the last refresh-event was send.
   * 
   * @return <code>true</code> if the {@link net.sf.mmm.ui.toolkit.api.UiFactory#getScriptOrientation()
   *         orientation} has been modified, <code>false</code> otherwise.
   */
  public boolean isOrientationModified() {

    return this.orientationModified;
  }

  /**
   * This method determines if the {@link net.sf.mmm.ui.toolkit.api.UiFactory#getLocale() locale} has been
   * modified since the last refresh-event was send.
   * 
   * @return <code>true</code> if the {@link net.sf.mmm.ui.toolkit.api.UiFactory#getLocale() locale} has been
   *         modified, <code>false</code> otherwise.
   */
  public boolean isLocaleModified() {

    return this.localeModified;
  }

}
