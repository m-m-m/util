/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.value.base;

import net.sf.mmm.value.api.ValueService;

/**
 * This interface indicates that a specific implementation requires the
 * {@link ValueService}.<br>
 * E.g. it may be used for {@link net.sf.mmm.value.api.ValueManager manager}
 * implementations of container value-types.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface ValueServiceAware {

  /**
   * This method is called to inject the value-service instance into this
   * object.
   * 
   * @param valueService is the value-service instance.
   */
  void setValueService(ValueService valueService);

}
