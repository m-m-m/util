/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.base.datatype.adapter.hibernate;

import java.sql.Types;

import net.sf.mmm.util.lang.api.SimpleDatatype;

/**
 * This is the abstract base implementation of {@link org.hibernate.usertype.UserType} to map a {@link String}
 * based {@link SimpleDatatype}.
 *
 * @param <T> the generic for the adapted {@link SimpleDatatype}.
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 6.0.0
 */
public class StringDatatypeUserType<T extends SimpleDatatype<String>> extends DatatypeUserType<String, T> {

  /**
   * The constructor.
   *
   * @param datatype is the {@link #returnedClass() java class} representing the adapted
   *        {@link SimpleDatatype}.
   */
  public StringDatatypeUserType(Class<T> datatype) {

    super(Types.VARCHAR, datatype, String.class);
  }

}
