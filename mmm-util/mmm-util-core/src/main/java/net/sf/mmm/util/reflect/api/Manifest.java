/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.api;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import net.sf.mmm.util.io.api.IoMode;
import net.sf.mmm.util.io.api.RuntimeIoException;

/**
 * This class represents a manifest file. Such file is typically located at
 * <code>META-INF/MANIFEST.MF</code> inside a java artifact file such as a JAR
 * or WAR file. A manifest contains various properties that give meta
 * information about the containing artifact.
 * 
 * @see net.sf.mmm.util.reflect.base.ManifestLoader
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.1
 */
public class Manifest {

  /** the class-path to the manifest. */
  public static final String MANIFEST_PATH = "META-INF/MANIFEST.MF";

  /** the default encoding. */
  public static final String DEFAULT_ENCODING = "UTF-8";

  /** The manifest property <code>{@value}</code>. */
  public static final String MANIFEST_VERSION = "Manifest-Version";

  /** The manifest property <code>{@value}</code>. */
  public static final String ARCHIVER_VERSION = "Archiver-Version";

  /** The manifest property <code>{@value}</code>. */
  public static final String CREATED_BY = "Created-By";

  /** The manifest property <code>{@value}</code>. */
  public static final String BUILD_BY = "Built-By";

  /** The manifest property <code>{@value}</code>. */
  public static final String IMPLEMENTATION_TITLE = "Implementation-Title";

  /** The manifest property <code>{@value}</code>. */
  public static final String IMPLEMENTATION_VERSION = "Implementation-Version";

  /** The manifest property <code>{@value}</code>. */
  public static final String IMPLEMENTATION_VENDOR = "Implementation-Vendor";

  /** The manifest property <code>{@value}</code>. */
  public static final String IMPLEMENTATION_VENDOR_ID = "Implementation-Vendor-Id";

  /** The manifest property <code>{@value}</code>. */
  public static final String SPECIFICATION_TITLE = "Specification-Title";

  /** The manifest property <code>{@value}</code>. */
  public static final String SPECIFICATION_VERSION = "Specification-Version";

  /** The manifest property <code>{@value}</code>. */
  public static final String SPECIFICATION_VENDOR = "Specification-Vendor";

  /** The manifest property <code>{@value}</code>. */
  public static final String SPECIFICATION_VENDOR_ID = "Specification-Vendor-Id";

  /**
   * The manifest property <code>{@value}</code>. This is a property that is NOT
   * intended to be defined in the manifest file itself but is set dynamically
   * to the source of the manifest (e.g. the name of the JAR-file).
   */
  public static final String MANIFEST_SOURCE = "Manifest-Source";

  /** the properties from the manifest file. */
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
   * The constructor.
   * 
   * @param reader is the {@link Reader} where to read the manifest from.
   * @throws RuntimeIoException if the {@link Reader} caused an
   *         {@link IOException}
   */
  public Manifest(Reader reader) throws RuntimeIoException {

    this(reader, null);
  }

  /**
   * The constructor.
   * 
   * @param reader is the {@link Reader} where to read the manifest from.
   * @param source is the source of the manifest (e.g. the name of the
   *        JAR-file).
   * @throws RuntimeIoException if the {@link Reader} caused an
   *         {@link IOException}
   */
  public Manifest(Reader reader, String source) throws RuntimeIoException {

    super();
    try {
      try {
        Properties p = new Properties();
        p.load(reader);
        Map<String, String> map = new HashMap<String, String>();
        for (Object key : p.keySet()) {
          String keyString = key.toString();
          String value = p.getProperty(keyString);
          map.put(keyString, value);
        }
        if (source != null) {
          map.put(MANIFEST_SOURCE, source);
        }
        this.properties = Collections.unmodifiableMap(map);
      } finally {
        reader.close();
      }
    } catch (IOException e) {
      throw new RuntimeIoException(e, IoMode.READ);
    }
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
   * This method gets the value of the property {@link #MANIFEST_SOURCE}.
   * 
   * @return the property value or <code>null</code> if NOT set.
   */
  public String getManifestSource() {

    return this.properties.get(MANIFEST_SOURCE);
  }

  /**
   * This method gets the value of the property {@link #MANIFEST_VERSION}.
   * 
   * @return the property value or <code>null</code> if NOT set.
   */
  public String getManifestVersion() {

    return this.properties.get(MANIFEST_VERSION);
  }

  /**
   * This method loads the {@link Manifest} for the given <code>type</code> if
   * available.
   * 
   * @param type is the {@link Class} for which the {@link Manifest} is
   *        requested. E.g. if that {@link Class} was loaded from a JAR-file,
   *        then the {@link Manifest} of that JAR is loaded if present.
   * @return the according {@link Manifest} or <code>null</code> if NOT
   *         available.
   */
  public static final Manifest load(Class<?> type) {

    InputStream inputStream = type.getClassLoader().getResourceAsStream(MANIFEST_PATH);
    if (inputStream == null) {
      return null;
    }
    try {
      InputStreamReader reader = new InputStreamReader(inputStream);
      return new Manifest(reader);
    } finally {
      try {
        inputStream.close();
      } catch (IOException e) {
        throw new RuntimeIoException(e, IoMode.CLOSE);
      }
    }
  }

}
