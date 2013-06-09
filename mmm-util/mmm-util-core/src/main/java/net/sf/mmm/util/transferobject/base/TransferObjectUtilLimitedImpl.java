/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.transferobject.base;

import net.sf.mmm.util.nls.api.NlsNullPointerException;
import net.sf.mmm.util.transferobject.api.AbstractTransferObject;
import net.sf.mmm.util.transferobject.api.AbstractTransferObject.AbstractTransferObjectUtilLimited;

/**
 * This is the implementation of {@link net.sf.mmm.util.transferobject.api.TransferObjectUtilLimited}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.1.0
 */
public class TransferObjectUtilLimitedImpl extends AbstractTransferObjectUtilLimited {

  /**
   * The constructor.
   */
  public TransferObjectUtilLimitedImpl() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <TO extends AbstractTransferObject> TO copy(TO template) {

    NlsNullPointerException.checkNotNull(AbstractTransferObject.class.getSimpleName(), template);
    TO copy = newInstance(template);
    copyProperties(template, copy);
    return copy;
  }

}
