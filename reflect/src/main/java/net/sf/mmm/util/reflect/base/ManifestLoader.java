/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

/**
 * This class loads all {@link Manifest}s from your classpath. After construction an instance of this class
 * allows you to {@link #getManifests() get} the list of {@link Manifest}s. This allows you to determine
 * details (e.g. the name and version) about the libraries in your classpath.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.1
 */
public class ManifestLoader {

  /**
   * The {@link java.util.jar.Attributes.Name} of the source of the {@link Manifest}. This is a property that
   * is NOT intended to be defined in the manifest file itself but is set dynamically as
   * {@link Manifest#getMainAttributes() main-property} to the source of the manifest (e.g. the name of the
   * JAR-file) if loaded via {@link ManifestLoader}.
   */
  public static final Attributes.Name MANIFEST_SOURCE = new Attributes.Name("Manifest-Source");

  /** The JAR suffix. */
  private static final String JAR_SUFFIX = ".jar!/" + JarFile.MANIFEST_NAME;

  /** the list of the manifests */
  private final List<Manifest> manifests;

  /**
   * The constructor.
   */
  public ManifestLoader() {

    this(Thread.currentThread().getContextClassLoader());
  }

  /**
   * The constructor.
   *
   * @param classloader is the {@link ClassLoader} used to find and load the {@link Manifest}s.
   */
  private ManifestLoader(ClassLoader classloader) {

    super();
    try {
      List<Manifest> mutableList = new ArrayList<>();
      Enumeration<URL> urls = classloader.getResources(JarFile.MANIFEST_NAME);
      while (urls.hasMoreElements()) {
        URL url = urls.nextElement();
        InputStream inputStream = url.openStream();
        try {
          Manifest manifest = new Manifest();
          manifest.read(inputStream);
          completeManifest(manifest, url);
          mutableList.add(manifest);
        } finally {
          inputStream.close();
        }
      }
      this.manifests = Collections.unmodifiableList(mutableList);
    } catch (IOException e) {
      throw new IllegalStateException("Failed to read manifest.", e);
    }
  }

  /**
   * This method adds dynamic attributes to the given {@code manifest}.
   *
   * @param manifest is the {@link Manifest} to modify.
   * @param url is the {@link URL} with the source of the manifest.
   */
  private static void completeManifest(Manifest manifest, URL url) {

    String path = url.getPath();
    int start = 0;
    int end = path.length();
    if (path.endsWith(JAR_SUFFIX)) {
      // 4 for ".jar"
      end = end - JAR_SUFFIX.length() + 4;
      start = path.lastIndexOf('/', end) + 1;
    }
    String source = path.substring(start, end);
    manifest.getMainAttributes().put(MANIFEST_SOURCE, source);
  }

  /**
   * This method gets an unmodifiable list with all available {@link Manifest}s. On a {@link Manifest} you may
   * call something like this:
   *
   * <pre>
   * manifest.{@link Manifest#getMainAttributes()
   * getMainAttributes()}.{@link java.util.jar.Attributes#getValue(java.util.jar.Attributes.Name)
   * getValue}({@link java.util.jar.Attributes.Name#IMPLEMENTATION_VERSION})
   * </pre>
   *
   * @see Manifest#getMainAttributes()
   * @see java.util.jar.Attributes.Name
   * @see #MANIFEST_SOURCE
   *
   * @return the list with the manifests.
   */
  public List<Manifest> getManifests() {

    return this.manifests;
  }

  /**
   * This method tries to load the {@link Manifest} for the given {@link Class}. E.g. if the given
   * {@link Class} comes from a {@link JarFile} the {@link JarFile#getManifest() Manifest} of that
   * {@link JarFile} is returned.
   *
   * @param type is the {@link Class} for which the according {@link Manifest} is requested.
   * @return the according {@link Manifest} or {@code null} if NOT available.
   * @since 2.0.0
   */
  public static Manifest loadManifest(Class<?> type) {

    try {
      Manifest manifest = null;
      String classpath = type.getName().replace('.', '/') + ".class";
      URL classUrl = type.getClassLoader().getResource(classpath);
      if (classUrl != null) {
        String protocol = classUrl.getProtocol().toLowerCase(Locale.US);
        if ("jar".equals(protocol)) {
          JarURLConnection connection = (JarURLConnection) classUrl.openConnection();
          JarFile jarFile = connection.getJarFile();
          // create a copy to avoid modification of the original manifest...
          manifest = new Manifest(jarFile.getManifest());
        } else if ("file".equals(protocol)) {
          String urlString = URLDecoder.decode(classUrl.getFile(), "UTF-8");
          if (urlString.endsWith(classpath)) {
            String rootpath = urlString.substring(0, urlString.length() - classpath.length());
            File manifestFile = new File(rootpath, JarFile.MANIFEST_NAME);
            if (manifestFile.exists()) {
              InputStream inputStream = new FileInputStream(manifestFile);
              try {
                manifest = new Manifest(inputStream);
              } finally {
                inputStream.close();
              }
            }
          }
        }
      }
      return manifest;
    } catch (IOException e) {
      throw new IllegalStateException("Failed to read manifest.", e);
    }
  }

  /**
   * This method gets an attribute-value from a {@link Manifest} in a pragmatic way. It tries to
   * {@link Attributes#getValue(java.util.jar.Attributes.Name) get} the value from the
   * {@link Manifest#getMainAttributes() main-attributes}. If NOT available it searches all other
   * {@link Manifest#getEntries() available attribute entries} for the value and returns the first one found
   * in deterministic but unspecified order.
   *
   * @param manifest is the {@link Manifest} where to get the attribute-value from.
   * @param name is the {@link java.util.jar.Attributes.Name} of the requested attribute.
   * @return the requested value or {@code null} if NOT available.
   * @since 2.0.0
   */
  public static String getValue(Manifest manifest, Attributes.Name name) {

    String value = manifest.getMainAttributes().getValue(name);
    if (value == null) {
      for (Attributes attributes : manifest.getEntries().values()) {
        value = attributes.getValue(name);
      }
    }
    return value;
  }
}
