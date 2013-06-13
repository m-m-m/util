/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.descriptor.base;

import java.lang.reflect.Field;
import java.util.Iterator;

/**
 * This is the interface used to find {@link Field fields} of a given {@link net.sf.mmm.util.pojo.api.Pojo}.<br/>
 * <b>ATTENTION:</b><br/>
 * Since version 3.1.0 of mmm-util-core no inherited {@link Field fields} are iterated by default
 * implementations (see issue # 55).
 * 
 * @see #findFields(Class)
 * 
 * @see net.sf.mmm.util.pojo.descriptor.api.PojoDescriptor
 * @see net.sf.mmm.util.pojo.descriptor.api.PojoPropertyDescriptor
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.0
 */
public interface PojoFieldIntrospector {

  /**
   * This method finds the {@link Field fields} of the given <code>pojoType</code>.
   * 
   * @param pojoType is the type reflecting the POJO for which the {@link Field fields} are requested.
   * @return a read-only iterator of all the {@link Field}s.
   */
  Iterator<Field> findFields(Class<?> pojoType);

}
