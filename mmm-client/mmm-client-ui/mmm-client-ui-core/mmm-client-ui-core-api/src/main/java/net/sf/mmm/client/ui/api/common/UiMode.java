/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.common;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * This interface represents the available modes of a UI object. It can be either in the mode {@link #VIEW} or
 * in the mode {@link #EDIT}. <br>
 * <b>NOTE:</b><br>
 * This is intentionally NOT realized as an enum type in order to allow extension for custom needs.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiMode {

  /**
   * The mode to show content in a read-only view.
   */
  UiMode VIEW = new UiMode() {

    @Override
    public boolean isEditable() {

      return false;
    }

    @Override
    public String toString() {

      return "VIEW";
    }
  };

  /**
   * The mode to edit content.
   */
  UiMode EDIT = new UiMode() {

    @Override
    public boolean isEditable() {

      return true;
    }

    @Override
    public String toString() {

      return "EDIT";
    }
  };

  /**
   * The {@link Set} containing only {@link #VIEW}.
   */
  Set<UiMode> SET_VIEW = Collections.unmodifiableSet(new HashSet<UiMode>(Arrays.asList(UiMode.VIEW)));

  /**
   * The {@link Set} containing only {@link #VIEW}.
   */
  Set<UiMode> SET_EDIT = Collections.unmodifiableSet(new HashSet<UiMode>(Arrays.asList(UiMode.EDIT)));

  /**
   * The {@link Set} containing {@link #VIEW} and {@link #EDIT}.
   */
  Set<UiMode> SET_VIEW_AND_EDIT = Collections.unmodifiableSet(new HashSet<UiMode>(Arrays.asList(UiMode.VIEW,
      UiMode.EDIT)));

  /**
   * This method determines if this mode allows the end-user to modify values.
   * 
   * @return <code>true</code> if this {@link UiMode} allows editing, <code>false</code> otherwise (if the
   *         end-user can not modify values in this mode).
   */
  boolean isEditable();

}
