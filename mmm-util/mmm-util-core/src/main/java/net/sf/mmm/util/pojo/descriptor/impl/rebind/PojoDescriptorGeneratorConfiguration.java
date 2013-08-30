/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.descriptor.impl.rebind;

import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.TypeOracle;

/**
 * This is the configuration for {@link PojoDescriptorGenerator} and {@link PojoDescriptorBuilderGenerator}.
 * If you want to define your own custom implementation, have a look at
 * {@link AbstractPojoDescriptorGenerator#createConfiguration()} and
 * {@link PojoDescriptorGeneratorConfigurationImpl#getMarkerType()}.
 * 
 * @see #isPojoTypeSupported(JClassType, TypeOracle)
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface PojoDescriptorGeneratorConfiguration {

  /**
   * This method determines if the given <code>type</code> will be supported and a
   * {@link net.sf.mmm.util.pojo.descriptor.api.PojoDescriptor} implementation shall be generated.<br/>
   * <b>ATTENTION:</b><br/>
   * You need to require some base class or marker-interface for your {@link net.sf.mmm.util.pojo.api.Pojo}s.
   * Otherwise there is way too much clutter generated and your web-application will grow too big. So never
   * consider to return <code>true</code> for every given type.
   * 
   * @param type is the {@link net.sf.mmm.util.pojo.api.Pojo} to check.
   * @param typeOracle is the {@link TypeOracle} that may be used to {@link TypeOracle#findType(String)
   *        resolve} additional types (e.g. a marker interface).
   * @return <code>true</code> if the given <code>type</code> is supported, <code>false</code> otherwise.
   */
  boolean isPojoTypeSupported(JClassType type, TypeOracle typeOracle);

  /**
   * Determines the super-type that is {@link #isPojoTypeSupported(JClassType, TypeOracle) supported}.
   * 
   * @param type a {@link #isPojoTypeSupported(JClassType, TypeOracle) supported type}.
   * @param typeOracle is the {@link TypeOracle} that may be used to {@link TypeOracle#findType(String)
   *        resolve} additional types (e.g. a marker interface).
   * @return the direct {@link JClassType#isAssignableFrom(JClassType) super-type} (typically
   *         {@link JClassType#getSuperclass()}) that is still
   *         {@link #isPojoTypeSupported(JClassType, TypeOracle) supported} or <code>null</code> if no such
   *         type exists.
   */
  JClassType getSupportedSuperType(JClassType type, TypeOracle typeOracle);

  /**
   * @return a description of the criteria for the {@link #isPojoTypeSupported(JClassType, TypeOracle)
   *         supported} {@link net.sf.mmm.util.pojo.api.Pojo}s. Only used for debugging.
   */
  String getPojoTypeDescription();

}
