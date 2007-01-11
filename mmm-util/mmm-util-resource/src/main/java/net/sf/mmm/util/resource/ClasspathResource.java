/* $Id$ */
package net.sf.mmm.util.resource;

import java.net.URL;

/**
 * This is the implementation of the {@link Resource} interface for a resource
 * that comes from the {@link ClassLoader#getResource(String) classpath}. <br>
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
 * especially NOT work in situaitons where there are specific classloaders, what
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
   * The constructor
   * 
   * @param absolutePath
   *        is the absolute path to the resource. E.g.
   *        "net/sf/mmm/util/resource/ClasspathResource.txt".
   */
  public ClasspathResource(String absolutePath) {

    super();
    this.path = absolutePath;
    this.url = Thread.currentThread().getContextClassLoader().getResource(absolutePath);
  }

  /**
   * The constructor for a classpath-resource identified by
   * <code>somePackage</code> and the given <code>filename</code>.<br>
   * E.g. the following code would get a resource named "{@linkplain ClasspathResource}.xml"
   * from the same package where this class is located:
   * 
   * <pre>
   * getClasspathResource({@link net.sf.mmm.util.resource.ClasspathResource}.class, ".xml")
   * </pre>
   * 
   * This is the same as:
   * 
   * <pre>
   * getClasspathResource("net/sf/mmm/util/resource/{@linkplain ClasspathResource}.xml")
   * </pre>
   * 
   * @see #ClasspathResource(Package, String)
   * 
   * @param someClass
   *        is the class identifying the path where the resource is located and
   *        the prefix of its filename.
   * @param suffix
   *        is the suffix for the filename of the resource (e.g. ".properties"
   *        or "-test.xml").
   */
  public ClasspathResource(Class<?> someClass, String suffix) {

    this(someClass.getName().replace('.', '/') + suffix);
  }

  /**
   * The constructor for a classpath-resource identified by
   * <code>somePackage</code> and the given <code>filename</code>.<br>
   * E.g. the following code would create a resource named
   * "relection.properties" from the same package where this class is located:
   * 
   * <pre>
   * new {@link ClasspathResource}({@link ClasspathResource}.class.{@link Class#getPackage() getPackage}(), "relection.properties")
   * </pre>
   * 
   * @param somePackage
   *        is the package identifying the path where the resource is located.
   * @param filename
   *        is the name of the resource.
   */
  public ClasspathResource(Package somePackage, String filename) {

    this(somePackage.getName().replace('.', '/') + '/' + filename);
  }

  /**
   * @see net.sf.mmm.util.resource.Resource#isAvailable()
   */
  public boolean isAvailable() {

    return (this.url != null);
  }

  /**
   * @see net.sf.mmm.util.resource.Resource#getPath()
   */
  public String getPath() {

    return this.path;
  }

  /**
   * @see net.sf.mmm.util.resource.Resource#getUrl()
   */
  public URL getUrl() throws ResourceNotAvailableException {

    if (this.url == null) {
      throw new ResourceNotAvailableException(this.path);
    }
    return this.url;
  }

}
