/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.impl.datatype.usertype;

import net.sf.mmm.data.api.reflection.DataClassCachingStrategy;
import net.sf.mmm.persistence.impl.hibernate.usertype.StringDatatypeUserType;

/**
 * This is the implementation of the {@link org.hibernate.usertype.UserType} to
 * map the datatype {@link DataClassCachingStrategy}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class DataClassCachingStrategyUserType extends
    StringDatatypeUserType<DataClassCachingStrategy> {

  /**
   * The constructor.
   */
  public DataClassCachingStrategyUserType() {

    super(DataClassCachingStrategy.class);
  }

}
