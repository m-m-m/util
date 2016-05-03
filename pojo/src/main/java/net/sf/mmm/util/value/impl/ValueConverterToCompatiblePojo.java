/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.value.impl;

import javax.inject.Named;
import javax.inject.Singleton;

/**
 * This class extends {@link AbstractValueConverterToCompatiblePojo} so it is used as fallback converter for
 * any {@link Object} if no more specific converter is matching.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.0
 */
@Singleton
@Named
public class ValueConverterToCompatiblePojo extends AbstractValueConverterToCompatiblePojo<Object, Object> {

  /**
   * The constructor.
   */
  public ValueConverterToCompatiblePojo() {

    super();
  }

  @Override
  public Class<Object> getSourceType() {

    return Object.class;
  }

  @Override
  public Class<Object> getTargetType() {

    return Object.class;
  }

}
