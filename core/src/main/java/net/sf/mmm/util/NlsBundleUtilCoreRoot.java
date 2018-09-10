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
