/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.api;

import java.util.Collections;
import java.util.Map;

/**
 * This class represents a manifest file. Such file is typically located at
 * <code>META-INF/MANIFEST.MF</code> inside a java artifact file such as a JAR
 * or WAR file. A manifest contains various properties that give meta
 * information about the containing artifact.
 * 
 * @see net.sf.mmm.util.reflect.base.ManifestLoader
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class Manifest {

  /** the property <code>{@value}</code> */
  public static final String MANIFEST_VERSION = "Manifest-Version";

  /** the property <code>{@value}</code> */
  public static final String ARCHIVER_VERSION = "Archiver-Version";

  /** the property <code>{@value}</code> */
  public static final String CREATED_BY = "Created-By";

  /** the property <code>{@value}</code> */
  public static final String BUILD_BY = "Built-By";

  /** the property <code>{@value}</code> */
  public static final String IMPLEMENTATION_TITLE = "Implementation-Title";

  /** the property <code>{@value}</code> */
  public static final String IMPLEMENTATION_VERSION = "Implementation-Version";

  /** the property <code>{@value}</code> */
  public static final String IMPLEMENTATION_VENDOR = "Implementation-Vendor";

  /** the property <code>{@value}</code> */
  public static final String IMPLEMENTATION_VENDOR_ID = "Implementation-Vendor-Id";

  /** the property <code>{@value}</code> */
  public static final String SPECIFICATION_TITLE = "Specification-Title";

  /** the property <code>{@value}</code> */
  public static final String SPECIFICATION_VERSION = "Specification-Version";

  /** the property <code>{@value}</code> */
  public static final String SPECIFICATION_VENDOR = "Specification-Vendor";

  /** the property <code>{@value}</code> */
  public static final String SPECIFICATION_VENDOR_ID = "Specification-Vendor-Id";

  /** the properties from the manifest file */
  private final Map<String, String> properties;

  /**
   * The constructor.
   * 
   * @param properties is the map with the properties.
   */
  public Manifest(Map<String, String> properties) {

    super();
    this.properties = Collections.unmodifiableMap(properties);
  }

  /**
   * This method gets an {@link Collections#unmodifiableMap(Map) unmodifiable}
   * {@link Map} containing the properties of the manifest.
   * 
   * @return the properties.
   */
  public Map<String, String> getProperties() {

    return this.properties;
  }

  /**
   * This method gets the value of the property {@link #IMPLEMENTATION_VERSION}.
   * 
   * @return the property value or <code>null</code> if NOT set.
   */
  public String getImplementationVersion() {

    return this.properties.get(IMPLEMENTATION_VERSION);
  }

  /**
   * This method gets the value of the property {@link #IMPLEMENTATION_TITLE}.
   * 
   * @return the property value or <code>null</code> if NOT set.
   */
  public String getImplementationTitle() {

    return this.properties.get(IMPLEMENTATION_TITLE);
  }

  /**
   * This method gets the value of the property {@link #IMPLEMENTATION_VENDOR}.
   * 
   * @return the property value or <code>null</code> if NOT set.
   */
  public String getImplementationVendor() {

    return this.properties.get(IMPLEMENTATION_VENDOR);
  }

  /**
   * This method gets the value of the property
   * {@link #IMPLEMENTATION_VENDOR_ID}.
   * 
   * @return the property value or <code>null</code> if NOT set.
   */
  public String getImplementationVendorId() {

    return this.properties.get(IMPLEMENTATION_VENDOR_ID);
  }

  /**
   * This method gets the value of the property {@link #SPECIFICATION_TITLE}.
   * 
   * @return the property value or <code>null</code> if NOT set.
   */
  public String getSpecificationTitle() {

    return this.properties.get(SPECIFICATION_TITLE);
  }

  /**
   * This method gets the value of the property {@link #SPECIFICATION_VERSION}.
   * 
   * @return the property value or <code>null</code> if NOT set.
   */
  public String getSpecificationVersion() {

    return this.properties.get(SPECIFICATION_VERSION);
  }

  /**
   * This method gets the value of the property {@link #SPECIFICATION_VENDOR}.
   * 
   * @return the property value or <code>null</code> if NOT set.
   */
  public String getSpecificationVendor() {

    return this.properties.get(SPECIFICATION_VENDOR);
  }

  /**
   * This method gets the value of the property {@link #MANIFEST_VERSION}.
   * 
   * @return the property value or <code>null</code> if NOT set.
   */
  public String getManifestVersion() {

    return this.properties.get(MANIFEST_VERSION);
  }

}
