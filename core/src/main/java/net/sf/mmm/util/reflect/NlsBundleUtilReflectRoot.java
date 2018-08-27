/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect;

import javax.inject.Named;

import net.sf.mmm.util.nls.api.NlsBundle;
import net.sf.mmm.util.nls.api.NlsBundleMessage;
import net.sf.mmm.util.nls.api.NlsMessage;

/**
 * This interface holds the {@link NlsBundle internationalized messages} for this module.
 *
 * @deprecated not used any more as {@code mmm-util-reflect} was moved to a separate module and had to be
 *             decoupled from {{@code mmm-util-nls}.
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.0.0
 */
@Deprecated
public interface NlsBundleUtilReflectRoot extends NlsBundle {

  /**
   * @see net.sf.mmm.util.reflect.base.IllegalWildcardSequenceException
   *
   * @param sequence is the illegal sequence that was used in a wildcard-type.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("Illegal sequence in wildcard type \"{sequence}\"!")
  NlsMessage errorTypeIllegalWildcard(@Named("sequence") Object sequence);

  /**
   * @see net.sf.mmm.util.reflect.api.AnnotationNotRuntimeException
   *
   * @param annotation is the invalid {@link java.lang.annotation.Annotation}.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("The given annotation \"{annotation}\" can NOT be resolved at runtime!")
  NlsMessage errorAnnotationNotRuntime(@Named("annotation") Object annotation);

  /**
   * @see net.sf.mmm.util.reflect.api.AnnotationNotForTargetException
   *
   * @param annotation is the invalid {@link java.lang.annotation.Annotation}.
   * @param target is the expected {@link java.lang.annotation.ElementType}.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("The given annotation \"{annotation}\" can NOT annotate the target \"{target}\"!")
  NlsMessage errorAnnotationNotForTarget(@Named("annotation") Object annotation, @Named("target") Object target);

  /**
   * @see net.sf.mmm.util.reflect.api.TypeNotFoundException
   *
   * @param type is the missing {@link java.lang.reflect.Type}.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("The type \"{type}\" could NOT be found!")
  NlsMessage errorTypeNotFound(@Named("type") Object type);

  /**
   * @see net.sf.mmm.util.reflect.base.ContainerGrowthException
   *
   * @param size is the size to increase.
   * @param max is the maximum allowed increase.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("Can not increase size of array or list by \"{size}\", because limit is \"{max}\"!")
  NlsMessage errorIncreaseExceedsMaxGrowth(@Named("size") int size, @Named("max") int max);

  /**
   * @see net.sf.mmm.util.reflect.base.UnknownCollectionInterfaceException
   *
   * @param type is the {@link Class} reflecting the unknown collection.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("Unknown collection interface \"{type}\"!")
  NlsMessage errorUnknownCollectionInterface(@Named("type") Object type);

  /**
   * @see net.sf.mmm.util.reflect.api.InstantiationFailedException
   *
   * @param type is the {@link java.lang.reflect.Type} that could NOT be instantiated.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("Failed to create an instance of \"{type}\"!")
  NlsMessage errorInstantiationFailed(@Named("type") Object type);

  /**
   * @see net.sf.mmm.util.reflect.api.InvocationFailedException
   *
   * @param accessible is the method or field that was invoked.
   * @param object is the object on which the invocation failed.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("Invocation{accessible,choice,(?==null)''(else)' of \"'{accessible}'\"'} failed{object,choice,(?==null)''(else)' on \"'{object}'\"'}!")
  NlsMessage errorInvocationFailed(@Named("accessible") Object accessible, @Named("object") Object object);

  /**
   * @see net.sf.mmm.util.reflect.api.AccessFailedException
   *
   * @param accessible is the method, constructor or field that could not be accessed.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("Reflective access for \"{accessible}\" failed!")
  NlsMessage errorAccessFailed(@Named("accessible") Object accessible);

}
