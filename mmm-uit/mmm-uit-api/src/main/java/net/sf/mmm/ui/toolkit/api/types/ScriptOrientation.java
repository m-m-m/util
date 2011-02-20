/* $Id: ScriptOrientation.java 258 2007-05-10 21:24:53Z hohwille $
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.types;

/**
 * This enumeration represents the orientation of a script-system. It is rather
 * used to determine the orientation of the layout for widgets but better to
 * understand as the orientation of the script.<br>
 * When you read this javadoc you will be used to the fact that many scripts are
 * written {@link #isHorizontal() horizontally} in the direction from
 * {@link #isLeftToRight() left to right}. However many other scripts on the
 * world work different. Some scripts are written from the right to the left
 * others vertically from the top to the bottom while the vertical rows may be
 * ordered again from the left to the right. The user expects the orientation of
 * widgets ordered according to its script-system.<br>
 * Please note that there are also scripts that go bidirectional (bidi) so e.g.
 * normal text is going right-to-left while numbers are written left-to-right.
 * However the {@link ScriptOrientation} only specifies the main direction for
 * the layout.<br>
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public enum ScriptOrientation {

  /** Script and layout goes horizontal from the left to the right. */
  LEFT_TO_RIGHT {

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isLeftToRight() {

      return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isHorizontal() {

      return true;
    }

  },

  /** Script and layout goes horizontal from the right to the left. */
  RIGHT_TO_LEFT {

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isLeftToRight() {

      return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isHorizontal() {

      return true;
    }

  },

  /**
   * Script and layout goes vertical from the top to the bottom, horizontal
   * order goes from the left to the right.
   */
  VERTICAL_LEFT_TO_RIGHT {

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isLeftToRight() {

      return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isHorizontal() {

      return false;
    }

  },

  /**
   * Script and layout goes vertical from the top to the bottom, horizontal
   * order goes from the right to the left.
   */
  RIGHT_TO_LEFT_VERTICAL {

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isLeftToRight() {

      return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isHorizontal() {

      return false;
    }

  };

  /**
   * This method determines if the horizontal ordering of script and layout goes
   * from the left to the right or vice versa.
   * 
   * @return <code>true</code> if horizontal ordering is left-to-right,
   *         <code>false</code> for right-to-left.
   */
  public abstract boolean isLeftToRight();

  /**
   * This method determines if the primary ordering of script and layout goes
   * horizontally. Vertical ordering always goes from the top to the bottom.
   * 
   * @return <code>true</code> if the primary ordering is horizontal,
   *         <code>false</code> for vertical.
   */
  public abstract boolean isHorizontal();

}
