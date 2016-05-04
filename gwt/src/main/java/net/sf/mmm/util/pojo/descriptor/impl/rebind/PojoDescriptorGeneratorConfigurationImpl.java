/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.descriptor.impl.rebind;

import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.TypeOracle;

import net.sf.mmm.util.pojo.api.Pojo;

/**
 * This is the configuration for {@link PojoDescriptorGenerator} and {@link PojoDescriptorBuilderGenerator}. It defines
 * the {@link #getMarkerType() marker class or interface}
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class PojoDescriptorGeneratorConfigurationImpl implements PojoDescriptorGeneratorConfiguration {

  /** @see #isPojoTypeSupported(JClassType, TypeOracle) */
  private JClassType markerType;

  /**
   * The constructor.
   */
  public PojoDescriptorGeneratorConfigurationImpl() {

    super();
  }

  @Override
  public boolean isPojoTypeSupported(JClassType type, TypeOracle typeOracle) {

    if (!type.isAssignableTo(getMarkerClassType(typeOracle))) {
      return false;
    }
    if (!isInterfaceSupported() && (type.isInterface() != null) && !type.equals(getMarkerClassType(typeOracle))) {
      return false;
    }
    return true;
  }

  /**
   * @param typeOracle is the {@link TypeOracle}.
   * @return the {@link JClassType} for {@link #getMarkerType()}.
   */
  protected final JClassType getMarkerClassType(TypeOracle typeOracle) {

    if (this.markerType == null) {
      this.markerType = typeOracle.findType(getMarkerType().getName());
    }
    return this.markerType;
  }

  @Override
  public JClassType getSupportedSuperType(JClassType type, TypeOracle typeOracle) {

    JClassType superclass = type.getSuperclass();
    if (superclass != null) {
      if (isPojoTypeSupported(superclass, typeOracle)) {
        return superclass;
      } else {
        JClassType markerClassType = getMarkerClassType(typeOracle);
        if (!type.equals(markerClassType)) {
          // marker-interface...
          return markerClassType;
        }
      }
    }
    return null;
  }

  /**
   * Determines if if {@link Class#isInterface() interfaces} are {@link #isPojoTypeSupported(JClassType, TypeOracle)
   * supported}. This method does NOT affect the {@link #getMarkerType() marker type} that may also be an interface.
   *
   * @return {@code true} if {@link Class#isInterface() interfaces} are
   *         {@link #isPojoTypeSupported(JClassType, TypeOracle) supported}, {@code false} otherwise.
   */
  protected boolean isInterfaceSupported() {

    return false;
  }

  @Override
  public String getPojoTypeDescription() {

    if (isInterfaceSupported()) {
      return "sub-type of " + getMarkerType().toString();
    } else {
      return "sub-class of " + getMarkerType().toString();
    }
  }

  /**
   * This method gets the {@link Class} reflecting the class or interface used as markers for objects for
   * {@link net.sf.mmm.util.pojo.api.Pojo POJOs} where reflection should be supported via
   * {@link net.sf.mmm.util.pojo.descriptor.api.PojoDescriptorBuilder}.
   *
   * @return the marker {@link Class}.
   */
  protected Class<?> getMarkerType() {

    return Pojo.class;
  }

}
