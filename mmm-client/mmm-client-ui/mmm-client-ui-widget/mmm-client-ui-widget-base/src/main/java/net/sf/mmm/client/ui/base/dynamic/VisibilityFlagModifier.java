/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.dynamic;

import net.sf.mmm.client.ui.api.common.FlagModifier;

/**
 * This class provides the {@link FlagModifier}s for visibility of
 * {@link net.sf.mmm.client.ui.api.widget.UiWidget widgets}.
 * 
 * @see net.sf.mmm.client.ui.api.widget.UiWidget#getVisibleFlag()
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public final class VisibilityFlagModifier implements FlagModifier {

  /**
   * The {@link VisibilityFlagModifier} for visibility changes triggered by a change of
   * {@link net.sf.mmm.client.ui.api.common.UiMode}.
   */
  public static final FlagModifier MODIFIER_MODE = new VisibilityFlagModifier("UiMode");

  /**
   * The {@link VisibilityFlagModifier} for visibility changes triggered for security reasons (e.g. if a
   * button should be hidden because the user has no permissions for it). For usability the recommended
   * pattern is to hide widgets that the end-user could never invoke due to his permissions.<br/>
   * <b>ATTENTION:</b><br/>
   * You always have to be aware that security happens on the server. On the client this is just done for
   * usability. A end-user can always manipulate the client to trigger the operation on the server.
   */
  public static final FlagModifier MODIFIER_SECURITY = new VisibilityFlagModifier("Security");

  /**
   * The {@link VisibilityFlagModifier} for visibility changes triggered by
   * {@link net.sf.mmm.client.ui.api.attribute.AttributeWriteCollapsed#setCollapsed(boolean)}.
   */
  public static final FlagModifier MODIFIER_COLLAPSE = new VisibilityFlagModifier("Collapse");

  /** The {@link #toString() string representation}. */
  private final String title;

  /**
   * The constructor.
   * 
   * @param title is the {@link #toString() string representation}.
   */
  private VisibilityFlagModifier(String title) {

    super();
    this.title = title;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    return this.title;
  }

}
