/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.dynamic;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import net.sf.mmm.client.ui.api.attribute.AttributeReadVisible;
import net.sf.mmm.client.ui.api.common.UiMode;
import net.sf.mmm.client.ui.api.widget.UiWidget;

/**
 * This is the implementation of a {@link UiWidget#addVisibleFunction(AttributeReadVisible) visible-function}
 * that hides a {@link UiWidget} in specific {@link UiMode}s.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class VisibleFunctionModeSet implements AttributeReadVisible {

  /** @see #isVisible() */
  private final UiWidget widget;

  /** @see #isVisible() */
  private final Set<UiMode> hiddenModes;

  /**
   * The constructor.
   * 
   * @param widget is the {@link UiWidget} to show/hide when to its {@link UiWidget#getMode() mode} changes.
   * @param autoAdd - if <code>true</code> this new visible-function instance automatically
   *        {@link UiWidget#addVisibleFunction(AttributeReadVisible) adds} itself to the given
   *        <code>widget</code>. Otherwise, if <code>false</code>, this is omitted.
   * @param hiddenModes are the {@link UiMode}s which cause the <code>widget</code> to become hidden.
   */
  public VisibleFunctionModeSet(UiWidget widget, boolean autoAdd, UiMode... hiddenModes) {

    this(widget, autoAdd, new HashSet<UiMode>(Arrays.asList(hiddenModes)));
  }

  /**
   * The constructor.
   * 
   * @param widget is the {@link UiWidget} to show/hide when to its {@link UiWidget#getMode() mode} changes.
   * @param autoAdd - if <code>true</code> this new visible-function instance automatically
   *        {@link UiWidget#addVisibleFunction(AttributeReadVisible) adds} itself to the given
   *        <code>widget</code>. Otherwise, if <code>false</code>, this is omitted.
   * @param hiddenModes are the {@link UiMode}s which cause the <code>widget</code> to become hidden.
   */
  public VisibleFunctionModeSet(UiWidget widget, boolean autoAdd, Set<UiMode> hiddenModes) {

    super();
    this.widget = widget;
    this.hiddenModes = hiddenModes;
    if (autoAdd) {
      this.widget.addVisibleFunction(this);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isVisible() {

    UiMode mode = this.widget.getMode();
    return !this.hiddenModes.contains(mode);
  }

}
