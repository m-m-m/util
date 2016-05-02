/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo;

import javax.inject.Named;

import net.sf.mmm.util.nls.api.NlsBundle;
import net.sf.mmm.util.nls.api.NlsBundleMessage;
import net.sf.mmm.util.nls.api.NlsMessage;

/**
 * This interface holds the {@link NlsBundle internationalized messages} for this module.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 7.1.0
 */
public interface NlsBundleUtilPojoRoot extends NlsBundle {

  /**
   * @see net.sf.mmm.util.pojo.descriptor.api.PojoPropertyNotFoundException
   *
   * @param property is the property that was not found (typically the name of the property).
   * @param type is the type that was expected to contain the property.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("Property \"{property}\" not found in \"{type}\"!")
  NlsMessage errorPojoPropertyNotFound(@Named("property") Object property, @Named("type") Object type);

  /**
   * @see net.sf.mmm.util.pojo.descriptor.api.PojoPropertyNotFoundException
   *
   * @param property is the property that could not be accessed (typically the name of the property).
   * @param type is the type containing the property.
   * @param mode is the mode of access.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("Property \"{property}\" not accessible for the mode \"{mode}\" in \"{type}\"!")
  NlsMessage errorPojoPropertyNotAccessible(@Named("property") Object property, @Named("type") Object type,
      @Named("mode") Object mode);

  /**
   * @see net.sf.mmm.util.pojo.path.api.PojoPathUnsafeException
   *
   * @param path is the {@link net.sf.mmm.util.pojo.path.api.PojoPath}.
   * @param type is the current type for which the path is unsafe.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("The pojo-path \"{path}\" is unsafe for type \"{type}\"!")
  NlsMessage errorPojoPathUnsafe(@Named("path") String path, @Named("type") Object type);

  /**
   * @see net.sf.mmm.util.pojo.path.api.PojoPathSegmentIsNullException
   *
   * @param path is the {@link net.sf.mmm.util.pojo.path.api.PojoPath}.
   * @param object is the initial {@link Object} the path was invoked on resulting null.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("The pojo-path \"{path}\" for object \"{object}\" evaluates to null!")
  NlsMessage errorPojoPathSegmentIsNull(@Named("path") String path, @Named("object") Object object);

  /**
   * @see net.sf.mmm.util.pojo.path.api.PojoPathCreationException
   *
   * @param path is the {@link net.sf.mmm.util.pojo.path.api.PojoPath}.
   * @param object is the current object at the path.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("Failed to create the object at the pojo-path \"{path}\" for object \"{object}\"!")
  NlsMessage errorPojoPathCreation(@Named("path") String path, @Named("object") Object object);

  /**
   * @see net.sf.mmm.util.pojo.path.api.PojoPathAccessException
   *
   * @param path is the {@link net.sf.mmm.util.pojo.path.api.PojoPath}.
   * @param type is the current type that does not support the path.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("Failed to access the pojo-path \"{path}\" for current object of type \"{type}\"!")
  NlsMessage errorPojoPathAccess(@Named("path") String path, @Named("type") Object type);

  /**
   * @see net.sf.mmm.util.pojo.path.api.IllegalPojoPathException
   *
   * @param path is the {@link net.sf.mmm.util.pojo.path.api.PojoPath}.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("Illegal pojo-path \"{path}\"!")
  NlsMessage errorPojoPathIllegal(@Named("path") String path);

  /**
   * @see net.sf.mmm.util.pojo.path.base.PojoPathCachingDisabledException
   *
   * @param path is the {@link net.sf.mmm.util.pojo.path.api.PojoPath}.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("Caching was required for pojo-path \"{path}\" but is disabled!")
  NlsMessage errorPojoPathCachingDisabled(@Named("path") String path);

  /**
   * @see net.sf.mmm.util.pojo.path.api.PojoPathConversionException
   *
   * @param path is the {@link net.sf.mmm.util.pojo.path.api.PojoPath}.
   * @param type is the actual type.
   * @param targetType is the type to convert to.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("Can NOT convert from \"{type}\" to \"{targetType}\" for pojo-path \"{path}\"!")
  NlsMessage errorPojoPathConversion(@Named("path") String path, @Named("type") Object type,
      @Named("targetType") Object targetType);

}
