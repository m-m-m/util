/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.base;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import net.sf.mmm.util.reflect.api.Manifest;

/**
 * This class loads all {@link Manifest}s from your classpath. After
 * construction an instance of this class allows you to {@link #getManifests()
 * get} the list of {@link Manifest}s. This allows you to determine details
 * (e.g. the name and version) about the libraries in your classpath.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.1
 */
public class ManifestLoader {

  /** the class-path to the manifest. */
  private static final String MANIFEST_PATH = "META-INF/MANIFEST.MF";

  /** the default encoding. */
  public static final String DEFAULT_ENCODING = "UTF-8";

  /** the list of the manifests */
  private final List<Manifest> manifests;

  /**
   * The constructor.
   * 
   * @throws IOException if a general I/O error occurred while reflectively
   *         reading the manifests.
   */
  public ManifestLoader() throws IOException {

    this(DEFAULT_ENCODING);
  }

  /**
   * The constructor.
   * 
   * @param encoding is the encoding used to read the manifest files.
   * @throws IOException if a general I/O error occurred while reflectively
   *         reading the manifests.
   */
  public ManifestLoader(String encoding) throws IOException {

    super();
    List<Manifest> mutableList = new ArrayList<Manifest>();
    Enumeration<URL> urls = Thread.currentThread().getContextClassLoader().getResources(
        MANIFEST_PATH);
    while (urls.hasMoreElements()) {
      URL url = urls.nextElement();
      InputStream inputStream = url.openStream();
      try {
        InputStreamReader reader = new InputStreamReader(inputStream, encoding);
        Properties properties = new Properties();
        properties.load(reader);
        Map<String, String> map = new HashMap<String, String>();
        for (Object key : properties.keySet()) {
          String keyString = key.toString();
          String value = properties.getProperty(keyString);
          map.put(keyString, value);
        }
        Manifest manifest = new Manifest(map);
        mutableList.add(manifest);
      } finally {
        inputStream.close();
      }
    }
    this.manifests = Collections.unmodifiableList(mutableList);
  }

  /**
   * This method gets an unmodifiable list with all available manifests.
   * 
   * @return the list with the manifests.
   */
  public List<Manifest> getManifests() {

    return this.manifests;
  }

}
