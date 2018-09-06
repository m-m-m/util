/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.transferobject.base;

import net.sf.mmm.util.component.base.AbstractComponent;
import net.sf.mmm.util.exception.api.NlsNullPointerException;
import net.sf.mmm.util.reflect.api.AccessFailedException;
import net.sf.mmm.util.reflect.api.InstantiationFailedException;
import net.sf.mmm.util.transferobject.api.AbstractTransferObject;
import net.sf.mmm.util.transferobject.api.TransferObject;
import net.sf.mmm.util.transferobject.api.TransferObjectUtilLimited;

/**
 * This is the implementation of {@link TransferObjectUtilLimited}.
 *
 * @deprecated see {@link TransferObjectUtilLimited}
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.1.0
 */
@Deprecated
public class TransferObjectUtilLimitedImpl extends AbstractComponent implements TransferObjectUtilLimited {

  /**
   * The constructor.
   */
  public TransferObjectUtilLimitedImpl() {

    super();
  }

  @Override
  @SuppressWarnings("unchecked")
  public <TO extends AbstractTransferObject> TO clone(TO template) {

    NlsNullPointerException.checkNotNull(TransferObject.class.getSimpleName(), template);
    return (TO) template.clone();
  }

  @SuppressWarnings("unchecked")
  @Override
  public <TO extends AbstractTransferObject> TO newInstance(TO template) {

    NlsNullPointerException.checkNotNull(AbstractTransferObject.class.getSimpleName(), template);
    Class<? extends AbstractTransferObject> toClass = template.getClass();
    TO newInstance;
    try {
      newInstance = (TO) toClass.newInstance();
    } catch (InstantiationException e) {
      throw new InstantiationFailedException(e, toClass);
    } catch (IllegalAccessException e) {
      throw new AccessFailedException(e, toClass);
    }
    return newInstance;
  }

}
