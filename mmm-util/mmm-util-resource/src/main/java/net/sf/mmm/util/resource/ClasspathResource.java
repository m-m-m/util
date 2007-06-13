/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.resource;

import java.net.URL;

/**
 * This is the implementation of the {@link DataResource} interface for a
 * resource that comes from the
 * {@link ClassLoader#getResource(String) classpath}. <br>
 * A very nice feature of Java is to load resources from the classpath. This
 * allows that these resource are deployed within a jar-file. Adding a directory
 * to the beginning of the classpath still allows to override such a resource.<br>
 * Anyways a typical mistake is illustrated by the following code example:
 * 
 * <pre>
 * MyClass.class.{@link Class#getResource(String) getResource}("config.xml")
 * </pre>
 * 
 * This will NOT allow to override resources in other classpath entries and
 * especially NOT work in situations where there are specific classloaders, what
 * is a typical situation in environments of applications servers or IoC
 * frameworks.<br>
 * The solution is to use the
 * {@link Thread#getContextClassLoader() context-class-loader} to get resources
 * what is done by this implementation. A proper version of the example above
 * is:
 * 
 * <pre>
 * new {@link ClasspathResource#ClasspathResource(String)}((MyClass.class.getPackage(), "config.xml")).{@link #getUrl()}
 * </pre>
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ClasspathResource extends AbstractResource {

  /** @see #getUrl() */
  private final String path;

  /** @see #getUrl() */
  private final URL url;

  /**
   * The constructor.
   * 
   * @param absolutePath is the absolute path to the resource. E.g.
   *        "net/sf/mmm/util/resource/ClasspathResource.txt".
   */
  public ClasspathResource(String absolutePath) {

    super();
    this.path = absolutePath;
    this.url = Thread.currentThread().getContextClassLoader().getResource(absolutePath);
  }

  /**
   * The constructor for a classpath-resource identified by
   * <code>someClass</code> and the given <code>nameOrSuffix</code>.<br>
   * E.g. the following code would get a resource named "{@linkplain ClasspathResource}.xml"
   * from the same package where this class is located:
   * 
   * <pre>
   * new {@link ClasspathResource}({@link net.sf.mmm.util.resource.ClasspathResource}.class, 
   * ".xml", true)
   * </pre>
   * 
   * This is the same as:
   * 
   * <pre>
   * new {@link ClasspathResource}({@link net.sf.mmm.util.resource.ClasspathResource}.class, 
   * "{@linkplain ClasspathResource}.xml", false)
   * </pre>
   * 
   * This is the same as:
   * 
   * <pre>
   * new {@link ClasspathResource}("net/sf/mmm/util/resource/{@linkplain ClasspathResource}.xml")
   * </pre>
   * 
   * @see #ClasspathResource(Package, String)
   * 
   * @param someClass is the class identifying the path where the resource is
   *        located and the prefix of its filename.
   * @param nameOrSuffix is the filename of the resource or a suffix (e.g.
   *        ".properties" or "-test.xml") for it depending on
   *        <code>append</code>.
   * @param append - if <code>true</code> the <code>nameOrSuffix</code>
   */
  public ClasspathResource(Class<?> someClass, String nameOrSuffix, boolean append) {

    this(getAbsolutePath(someClass, nameOrSuffix, append));
  }

  /**
   * The constructor. for a classpath-resource identified by
   * <code>somePackage</code> and the given <code>filename</code>.<br>
   * E.g. the following code would create a resource named
   * "relection.properties" from the same package where this class is located:
   * 
   * <pre>
   * new {@link ClasspathResource}({@link ClasspathResource}.class.{@link Class#getPackage() getPackage}(), 
   * "relection.properties")
   * </pre>
   * 
   * This is the same as:
   * 
   * <pre>
   * new {@link ClasspathResource}({@link ClasspathResource}.class, "relection.properties", false)
   * </pre>
   * 
   * @see #ClasspathResource(Class, String, boolean)
   * 
   * @param somePackage is the package identifying the path where the resource
   *        is located.
   * @param filename is the name of the resource.
   */
  public ClasspathResource(Package somePackage, String filename) {

    this(somePackage.getName().replace('.', '/') + '/' + filename);
  }

  /**
   * @see #ClasspathResource(Class, String, boolean)
   * 
   * @param someClass is the class identifying the path where the resource is
   *        located and the prefix of its filename.
   * @param nameOrSuffix is the filename of the resource or a suffix (e.g.
   *        ".properties" or "-test.xml") for it depending on
   *        <code>append</code>.
   * @param append - if <code>true</code> the <code>nameOrSuffix</code>
   * @return the absolute path.
   */
  private static String getAbsolutePath(Class<?> someClass, String nameOrSuffix, boolean append) {

    if (append) {
      return someClass.getName().replace('.', '/') + nameOrSuffix;
    } else {
      return someClass.getPackage().getName().replace('.', '/') + '/' + nameOrSuffix;
    }
  }

  /**
   * {@inheritDoc}
   */
  public boolean isAvailable() {

    return (this.url != null);
  }

  /**
   * {@inheritDoc}
   */
  public String getPath() {

    return this.path;
  }

  /**
   * {@inheritDoc}
   */
  public URL getUrl() throws ResourceNotAvailableException {

    if (this.url == null) {
      throw new ResourceNotAvailableException(this.path);
    }
    return this.url;
  }

  /**
   * {@inheritDoc}
   */
  public DataResource navigate(String relativePath) {

    int lastSlash = this.path.lastIndexOf('/');
    String newPath = "";
    if (lastSlash > 0) {
      newPath = this.path.substring(lastSlash);
    }
    newPath = newPath + relativePath;
    // TODO: normalize path (remove . and ..)
    return new ClasspathResource(newPath);
  }

}
