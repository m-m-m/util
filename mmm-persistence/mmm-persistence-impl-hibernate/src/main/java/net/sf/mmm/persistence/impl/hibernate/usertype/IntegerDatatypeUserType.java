/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.impl.hibernate.usertype;

import java.sql.Types;

import net.sf.mmm.util.lang.api.SimpleDatatype;

/**
 * This is the abstract base implementation of {@link org.hibernate.usertype.UserType} to map a
 * {@link Integer} based {@link SimpleDatatype}.
 *
 * @param <T> the generic for the adapted {@link SimpleDatatype}.
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 0.9.0
 *
 * @deprecated use
 *             {@link net.sf.mmm.util.lang.base.datatype.adapter.jpa.AbstractSimpleDatatypeAttributeConverter}
 *             instead.
 */
@Deprecated
public class IntegerDatatypeUserType<T extends SimpleDatatype<Integer>> extends DatatypeUserType<Integer, T> {

  /**
   * The constructor.
   *
   * @param datatype is the {@link #returnedClass() java class} representing the adapted
   *        {@link SimpleDatatype}.
   */
  public IntegerDatatypeUserType(Class<T> datatype) {

    super(Types.NUMERIC, datatype, Integer.class);
  }

}
