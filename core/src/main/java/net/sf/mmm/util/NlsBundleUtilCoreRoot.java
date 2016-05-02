/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util;

import javax.inject.Named;

import net.sf.mmm.util.nls.api.NlsBundle;
import net.sf.mmm.util.nls.api.NlsBundleMessage;
import net.sf.mmm.util.nls.api.NlsMessage;

/**
 * This interface holds the {@link NlsBundle internationalized messages} for this module.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.0.0
 */
public interface NlsBundleUtilCoreRoot extends NlsBundle {

  /** @see #infoSouth() */
  String INF_SOUTH = "south";

  /** @see #infoEast() */
  String INF_EAST = "east";

  /** @see #infoWest() */
  String INF_WEST = "west";

  /** @see #infoNorth() */
  String INF_NORTH = "north";

  /** @see #infoSouthEast() */
  String INF_SOUTH_EAST = "south-east";

  /** @see #infoSouthWest() */
  String INF_SOUTH_WEST = "south-west";

  /** @see #infoNorthEast() */
  String INF_NORTH_EAST = "north-east";

  /** @see #infoNorthWest() */
  String INF_NORTH_WEST = "north-west";

  /** @see #infoLeft() */
  String INF_LEFT = "left";

  /** @see #infoRight() */
  String INF_RIGHT = "right";

  /** @see #infoCenter() */
  String INF_CENTER = "center";

  /** @see #infoTop() */
  String INF_TOP = "top";

  /** @see #infoTopLeft() */
  String INF_TOP_LEFT = "top left";

  /** @see #infoTopRight() */
  String INF_TOP_RIGHT = "top right";

  /** @see #infoBottom() */
  String INF_BOTTOM = "bottom";

  /** @see #infoBottomLeft() */
  String INF_BOTTOM_LEFT = "bottom left";

  /** @see #infoBottomRight() */
  String INF_BOTTOM_RIGHT = "bottom right";

  /** @see #infoHorizontal() */
  String INF_HORIZONTAL = "horizontal";

  /** @see #infoVertical() */
  String INF_VERTICAL = "vertical";

  /** @see #infoAnd() */
  String INF_AND = "and";

  /** @see #infoOr() */
  String INF_OR = "or";

  /** @see #infoNand() */
  String INF_NAND = "nand";

  /** @see #infoNor() */
  String INF_NOR = "nor";

  /** @see #infoGreaterThan() */
  String INF_GREATER_THAN = "greater than";

  /** @see #infoGreaterOrEqual() */
  String INF_GREATER_OR_EQUAL = "greater or equal to";

  /** @see #infoEqual() */
  String INF_EQUAL = "equal to";

  /** @see #infoNotEqual() */
  String INF_NOT_EQUAL = "not equal to";

  /** @see #infoLessThan() */
  String INF_LESS_THAN = "less than";

  /** @see #infoLessOrEqual() */
  String INF_LESS_OR_EQUAL = "less or equal";

  /**
   * @see net.sf.mmm.util.lang.api.HorizontalAlignment#LEFT
   *
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage(INF_LEFT)
  NlsMessage infoLeft();

  /**
   * @see net.sf.mmm.util.lang.api.HorizontalAlignment#RIGHT
   *
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage(INF_RIGHT)
  NlsMessage infoRight();

  /**
   * @see net.sf.mmm.util.lang.api.HorizontalAlignment#CENTER
   *
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage(INF_CENTER)
  NlsMessage infoCenter();

  /**
   * @see net.sf.mmm.util.lang.api.VerticalAlignment#TOP
   *
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage(INF_TOP)
  NlsMessage infoTop();

  /**
   * @see net.sf.mmm.util.lang.api.Alignment#TOP_LEFT
   *
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage(INF_TOP_LEFT)
  NlsMessage infoTopLeft();

  /**
   * @see net.sf.mmm.util.lang.api.Alignment#TOP_RIGHT
   *
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage(INF_TOP_RIGHT)
  NlsMessage infoTopRight();

  /**
   * @see net.sf.mmm.util.lang.api.VerticalAlignment#BOTTOM
   *
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage(INF_BOTTOM)
  NlsMessage infoBottom();

  /**
   * @see net.sf.mmm.util.lang.api.Alignment#BOTTOM_LEFT
   *
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage(INF_BOTTOM_LEFT)
  NlsMessage infoBottomLeft();

  /**
   * @see net.sf.mmm.util.lang.api.Alignment#BOTTOM_RIGHT
   *
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage(INF_BOTTOM_RIGHT)
  NlsMessage infoBottomRight();

  /**
   * @see net.sf.mmm.util.lang.api.Orientation#HORIZONTAL
   *
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage(INF_HORIZONTAL)
  NlsMessage infoHorizontal();

  /**
   * @see net.sf.mmm.util.lang.api.Orientation#VERTICAL
   *
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage(INF_VERTICAL)
  NlsMessage infoVertical();

  /**
   * @see net.sf.mmm.util.lang.api.Direction#SOUTH
   *
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage(INF_SOUTH)
  NlsMessage infoSouth();

  /**
   * @see net.sf.mmm.util.lang.api.Direction#EAST
   *
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage(INF_EAST)
  NlsMessage infoEast();

  /**
   * @see net.sf.mmm.util.lang.api.Direction#WEST
   *
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage(INF_WEST)
  NlsMessage infoWest();

  /**
   * @see net.sf.mmm.util.lang.api.Direction#NORTH
   *
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage(INF_NORTH)
  NlsMessage infoNorth();

  /**
   * @see net.sf.mmm.util.lang.api.Direction#SOUTH_EAST
   *
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage(INF_SOUTH_EAST)
  NlsMessage infoSouthEast();

  /**
   * @see net.sf.mmm.util.lang.api.Direction#SOUTH_WEST
   *
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage(INF_SOUTH_WEST)
  NlsMessage infoSouthWest();

  /**
   * @see net.sf.mmm.util.lang.api.Direction#NORTH_EAST
   *
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage(INF_NORTH_EAST)
  NlsMessage infoNorthEast();

  /**
   * @see net.sf.mmm.util.lang.api.Direction#NORTH_WEST
   *
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage(INF_NORTH_WEST)
  NlsMessage infoNorthWest();

  /**
   * @see net.sf.mmm.util.lang.api.Conjunction#AND
   *
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage(INF_AND)
  NlsMessage infoAnd();

  /**
   * @see net.sf.mmm.util.lang.api.Conjunction#OR
   *
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage(INF_OR)
  NlsMessage infoOr();

  /**
   * @see net.sf.mmm.util.lang.api.Conjunction#NAND
   *
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage(INF_NAND)
  NlsMessage infoNand();

  /**
   * @see net.sf.mmm.util.lang.api.Conjunction#NOR
   *
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage(INF_NOR)
  NlsMessage infoNor();

  /**
   * @see net.sf.mmm.util.lang.api.CompareOperator#GREATER_THAN
   *
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage(INF_GREATER_THAN)
  NlsMessage infoGreaterThan();

  /**
   * @see net.sf.mmm.util.lang.api.CompareOperator#GREATER_OR_EQUAL
   *
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage(INF_GREATER_OR_EQUAL)
  NlsMessage infoGreaterOrEqual();

  /**
   * @see net.sf.mmm.util.lang.api.CompareOperator#EQUAL
   *
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage(INF_EQUAL)
  NlsMessage infoEqual();

  /**
   * @see net.sf.mmm.util.lang.api.CompareOperator#NOT_EQUAL
   *
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage(INF_NOT_EQUAL)
  NlsMessage infoNotEqual();

  /**
   * @see net.sf.mmm.util.lang.api.CompareOperator#LESS_THAN
   *
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage(INF_LESS_THAN)
  NlsMessage infoLessThan();

  /**
   * @see net.sf.mmm.util.lang.api.CompareOperator#LESS_OR_EQUAL
   *
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage(INF_LESS_OR_EQUAL)
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

  /**
   * @see net.sf.mmm.util.date.api.IllegalDateFormatException
   *
   * @param value is the illegal date {@link String}.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("Illegal date \"{value}\"!")
  NlsMessage errorIllegalDate(@Named("value") String value);

  /**
   * @see net.sf.mmm.util.math.api.NumberConversionException
   *
   * @param value is the value that could NOT be converted.
   * @param type is the type the value should be converted to.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("Can not convert number \"{value}\" to \"{type}\"!")
  NlsMessage errorNumberConversion(@Named("value") Object value, @Named("type") Object type);

  /**
   * @see net.sf.mmm.util.security.api.SecurityErrorUserException
   *
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("The operation failed due to security restrictions. Please contact the support in case of a permission problem.")
  NlsMessage errorSecurityRestriction();

  /**
   * @see net.sf.mmm.util.nls.impl.formatter.NlsFormatterChoiceNoElseConditionException
   *
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("A choice format needs to end with an (else)-condition!")
  NlsMessage errorNlsChoiceNoElse();

  /**
   * @see net.sf.mmm.util.nls.impl.formatter.NlsFormatterChoiceOnlyElseConditionException
   *
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("A choice format needs to have at least one regular condition before (else)-condition!")
  NlsMessage errorNlsChoiceOnlyElse();

  /**
   * @see net.sf.mmm.util.xml.base.XmlInvalidException
   *
   * @param source is the source of the XML or {@code null} if unknown.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("The XML{source,choice,(?==null)''(else)' from \"'{source}'\"'} is invalid!")
  NlsMessage errorXmlInvalid(@Named("source") Object source);

  /**
   * @see net.sf.mmm.util.collection.base.NodeCycleException
   *
   * @param cycle the {@link net.sf.mmm.util.collection.base.NodeCycle}.
   * @param type the type of the nodes.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("A cyclic dependency of {type}-nodes has been detected [{cycle}]!")
  NlsMessage errorNodeCycle(@Named("cycle") Object cycle, @Named("type") Object type);

}
