/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang;

import net.sf.mmm.util.nls.api.NlsBundle;
import net.sf.mmm.util.nls.api.NlsBundleMessage;
import net.sf.mmm.util.nls.api.NlsMessage;

/**
 * This interface holds the {@link NlsBundle internationalized messages} for this module.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 7.6.0
 */
public interface NlsBundleUtilLangRoot extends NlsBundle {

  /**
   * @see net.sf.mmm.util.lang.api.HorizontalAlignment#LEFT
   *
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("left")
  NlsMessage infoLeft();

  /**
   * @see net.sf.mmm.util.lang.api.HorizontalAlignment#RIGHT
   *
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("right")
  NlsMessage infoRight();

  /**
   * @see net.sf.mmm.util.lang.api.HorizontalAlignment#CENTER
   *
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("center")
  NlsMessage infoCenter();

  /**
   * @see net.sf.mmm.util.lang.api.VerticalAlignment#TOP
   *
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("top")
  NlsMessage infoTop();

  /**
   * @see net.sf.mmm.util.lang.api.Alignment#TOP_LEFT
   *
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("top left")
  NlsMessage infoTopLeft();

  /**
   * @see net.sf.mmm.util.lang.api.Alignment#TOP_RIGHT
   *
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("top right")
  NlsMessage infoTopRight();

  /**
   * @see net.sf.mmm.util.lang.api.VerticalAlignment#BOTTOM
   *
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("bottom")
  NlsMessage infoBottom();

  /**
   * @see net.sf.mmm.util.lang.api.Alignment#BOTTOM_LEFT
   *
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("bottom left")
  NlsMessage infoBottomLeft();

  /**
   * @see net.sf.mmm.util.lang.api.Alignment#BOTTOM_RIGHT
   *
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("bottom right")
  NlsMessage infoBottomRight();

  /**
   * @see net.sf.mmm.util.lang.api.Orientation#HORIZONTAL
   *
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("horizontal")
  NlsMessage infoHorizontal();

  /**
   * @see net.sf.mmm.util.lang.api.Orientation#VERTICAL
   *
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("vertical")
  NlsMessage infoVertical();

  /**
   * @see net.sf.mmm.util.lang.api.Direction#SOUTH
   *
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("south")
  NlsMessage infoSouth();

  /**
   * @see net.sf.mmm.util.lang.api.Direction#EAST
   *
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("east")
  NlsMessage infoEast();

  /**
   * @see net.sf.mmm.util.lang.api.Direction#WEST
   *
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("west")
  NlsMessage infoWest();

  /**
   * @see net.sf.mmm.util.lang.api.Direction#NORTH
   *
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("north")
  NlsMessage infoNorth();

  /**
   * @see net.sf.mmm.util.lang.api.Direction#SOUTH_EAST
   *
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("south-east")
  NlsMessage infoSouthEast();

  /**
   * @see net.sf.mmm.util.lang.api.Direction#SOUTH_WEST
   *
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("south-west")
  NlsMessage infoSouthWest();

  /**
   * @see net.sf.mmm.util.lang.api.Direction#NORTH_EAST
   *
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("north-east")
  NlsMessage infoNorthEast();

  /**
   * @see net.sf.mmm.util.lang.api.Direction#NORTH_WEST
   *
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("north-west")
  NlsMessage infoNorthWest();

  /**
   * @see net.sf.mmm.util.lang.api.Conjunction#AND
   *
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("and")
  NlsMessage infoAnd();

  /**
   * @see net.sf.mmm.util.lang.api.Conjunction#OR
   *
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("or")
  NlsMessage infoOr();

  /**
   * @see net.sf.mmm.util.lang.api.Conjunction#NAND
   *
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("nand")
  NlsMessage infoNand();

  /**
   * @see net.sf.mmm.util.lang.api.Conjunction#NOR
   *
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("nor")
  NlsMessage infoNor();

  /**
   * @see net.sf.mmm.util.lang.api.CompareOperator#GREATER_THAN
   *
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("greater than")
  NlsMessage infoGreaterThan();

  /**
   * @see net.sf.mmm.util.lang.api.CompareOperator#GREATER_OR_EQUAL
   *
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("greater or equal to")
  NlsMessage infoGreaterOrEqual();

  /**
   * @see net.sf.mmm.util.lang.api.CompareOperator#EQUAL
   *
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("equal to")
  NlsMessage infoEqual();

  /**
   * @see net.sf.mmm.util.lang.api.CompareOperator#NOT_EQUAL
   *
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("not equal to")
  NlsMessage infoNotEqual();

  /**
   * @see net.sf.mmm.util.lang.api.CompareOperator#LESS_THAN
   *
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("less than")
  NlsMessage infoLessThan();

  /**
   * @see net.sf.mmm.util.lang.api.CompareOperator#LESS_OR_EQUAL
   *
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("less or equal to")
  NlsMessage infoLessOrEqual();

  /**
   * @return the {@link NlsMessage} for the label "OK".
   */
  @NlsBundleMessage("OK")
  NlsMessage infoOk();

  /**
   * @return the {@link NlsMessage} for the text "Yes".
   */
  @NlsBundleMessage("Yes")
  NlsMessage infoYes();

  /**
   * @return the {@link NlsMessage} for the text "No".
   */
  @NlsBundleMessage("No")
  NlsMessage infoNo();

  /**
   * @return the {@link NlsMessage} for the text "Undefined".
   */
  @NlsBundleMessage("Undefined")
  NlsMessage infoUndefined();

  /**
   * @return the {@link NlsMessage} for the text "Information".
   */
  @NlsBundleMessage("Information")
  NlsMessage infoInformation();

  /**
   * @return the {@link NlsMessage} for the text "Warning".
   */
  @NlsBundleMessage("Warning")
  NlsMessage infoWarning();

  /**
   * @return the {@link NlsMessage} for the text "Error".
   */
  @NlsBundleMessage("Error")
  NlsMessage infoError();

  /**
   * @return the {@link NlsMessage} for the text "Confirmation".
   */
  @NlsBundleMessage("Confirmation")
  NlsMessage infoConfirmation();

  /**
   * @return the {@link NlsMessage} for the text "validation failure".
   */
  @NlsBundleMessage("validation failure")
  NlsMessage infoValidationFailure();

}
