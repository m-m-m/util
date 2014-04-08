/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.common;

import net.sf.mmm.util.lang.api.concern.Accessibility;


/**
 * This interface is used as collection of constants with the names of central image icons.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @deprecated we have switched to using styles instead of images and render these in CSS via font-awesome.
 *             This gives way more flexibility then {@literal <img>} elements with hard-coded image URLs and
 *             names (including file type extensions). The only disadvantage is that we currently have no way
 *             to define {@link net.sf.mmm.client.ui.api.attribute.AttributeReadAltText#getAltText() alt
 *             texts} for {@link Accessibility}.
 */
@Deprecated
public interface IconConstants {

  /** The name of the icon to close something (e.g. a window). */
  String CLOSE = "Close";

  /** The name of the icon to maximize something (e.g. a window). */
  String MAXIMIZE = "Fullscreen";

  /** The name of the icon to undo the last change. */
  String UNDO = "Undo";

  /** The name of the icon to redo the last {@link #UNDO undone} change. */
  String REDO = "Redo";

  /** The name of the icon to move something up. */
  String MOVE_UP = "MoveUp";

  /** The name of the icon to move something down. */
  String MOVE_DOWN = "MoveDown";

}
