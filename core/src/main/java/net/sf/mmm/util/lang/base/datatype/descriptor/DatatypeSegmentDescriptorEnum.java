/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.base.datatype.descriptor;

import java.util.Locale;

import net.sf.mmm.util.lang.api.StringUtil;

/**
 * This is the implementation of {@link net.sf.mmm.util.lang.api.DatatypeSegmentDescriptor} for an
 * {@link Enum}.
 *
 * @param <T> is the generic type of the described {@link Enum}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 6.0.0
 */
public class DatatypeSegmentDescriptorEnum<T extends Enum<T>> extends AbstractDatatypeSegmentDescriptor<T, String> {

  /** The {@link StringUtil} instance. */
  private final StringUtil stringUtil;

  /**
   * The constructor.
   *
   * @param name - see {@link #getName()}.
   * @param stringUtil is the {@link StringUtil} to use.
   */
  protected DatatypeSegmentDescriptorEnum(String name, StringUtil stringUtil) {

    super(name, String.class);
    this.stringUtil = stringUtil;
  }

  @Override
  protected String doGetSegment(T datatype) {

    String name = datatype.name();
    if (this.stringUtil.isAllUpperCase(name)) {
      name = name.charAt(0) + name.substring(1).toLowerCase(Locale.US);
      name = this.stringUtil.toCamlCase(name);
    }
    return name;
  }

}
