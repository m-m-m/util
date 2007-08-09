/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.model.impl;

import net.sf.mmm.content.value.api.MutableBlob;
import net.sf.mmm.content.value.api.Version;


/**
 * TODO: this class ...
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class AdvancedDummyClassResolver extends DummyClassResolver {

  /**
   * {@inheritDoc}
   */
  @Override
  public void initialize() {
  
    super.initialize();
    register(MutableBlob.VALUE_NAME, MutableBlob.class);
    register(Version.VALUE_NAME, Version.class);
  }
  
}
