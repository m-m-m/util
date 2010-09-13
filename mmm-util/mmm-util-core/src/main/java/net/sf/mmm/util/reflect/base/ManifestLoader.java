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
import java.util.List;

import net.sf.mmm.util.io.api.IoMode;
import net.sf.mmm.util.io.api.RuntimeIoException;
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

  /** The JAR suffix. */
  private static final String JAR_SUFFIX = ".jar!/" + Manifest.MANIFEST_PATH;

  /** the list of the manifests */
  private final List<Manifest> manifests;

  /**
   * The constructor.
   * 
   * @throws RuntimeIoException if a general I/O error occurred while
   *         reflectively reading the manifests.
   */
  public ManifestLoader() throws RuntimeIoException {

    this(Manifest.DEFAULT_ENCODING);
  }

  /**
   * The constructor.
   * 
   * @param encoding is the encoding used to read the manifest files.
   * @throws RuntimeIoException if a general I/O error occurred while
   *         reflectively reading the manifests.
   */
  public ManifestLoader(String encoding) throws RuntimeIoException {

    this(encoding, Thread.currentThread().getContextClassLoader());
  }

  /**
   * The constructor.
   * 
   * @param encoding is the encoding used to read the manifest files.
   * @param classloader is the {@link ClassLoader} used to find and load the
   *        {@link Manifest}s.
   * @throws RuntimeIoException if a general I/O error occurred while
   *         reflectively reading the manifests.
   */
  private ManifestLoader(String encoding, ClassLoader classloader) throws RuntimeIoException {

    super();
    try {
      List<Manifest> mutableList = new ArrayList<Manifest>();
      Enumeration<URL> urls = classloader.getResources(Manifest.MANIFEST_PATH);
      while (urls.hasMoreElements()) {
        URL url = urls.nextElement();
        String path = url.getPath();
        int start = 0;
        int end = path.length();
        if (path.endsWith(JAR_SUFFIX)) {
          // 4 for ".jar"
          end = end - JAR_SUFFIX.length() + 4;
          start = path.lastIndexOf('/', end) + 1;
        }
        String source = path.substring(start, end);
        InputStream inputStream = url.openStream();
        try {
          InputStreamReader reader = new InputStreamReader(inputStream, encoding);
          Manifest manifest = new Manifest(reader, source);
          mutableList.add(manifest);
        } finally {
          inputStream.close();
        }
      }
      this.manifests = Collections.unmodifiableList(mutableList);
    } catch (IOException e) {
      throw new RuntimeIoException(e, IoMode.READ);
    }
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
