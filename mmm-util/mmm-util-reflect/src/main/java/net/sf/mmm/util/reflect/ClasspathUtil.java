/* $Id$ */
package net.sf.mmm.util.reflect;

import java.net.URL;

/**
 * This class is a collection of utility functions for dealing with classpaths.
 * <br>
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
 * what is done by this utility. A proper version of the example above is:
 * 
 * <pre>
 * {@link ClasspathUtil}.{@link #getClasspathResource(Package, String) getClasspathResource}(MyClass.class.getPackage(), "config.xml")
 * </pre>
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ClasspathUtil {

  /**
   * Forbidden constructor.
   */
  private ClasspathUtil() {

  }

  /**
   * This method gets the resource specified by <code>absolutePath</code> from
   * the classpath.
   * 
   * @param absolutePath
   *        is the absolute path to the requested resource. E.g.
   *        "net/sf/mmm/util/reflect/relection.properties".
   * @return an URL pointing to the requested resource.
   * @throws ResourceNotAvailableException
   *         if the requested resource is NOT available.
   */
  public static URL getClasspathResource(String absolutePath) throws ResourceNotAvailableException {

    URL resourceUrl = Thread.currentThread().getContextClassLoader().getResource(absolutePath);
    if (resourceUrl == null) {
      throw new ResourceNotAvailableException(absolutePath);
    }
    return resourceUrl;
  }

  /**
   * This method gets the
   * {@link #getClasspathResource(String) classpath-resource} identified by
   * <code>someClass</code> and the given <code>suffix</code>.<br>
   * E.g. the following code would get a resource name "ClasspathUtil.xml" from
   * the same package where this class is located:
   * 
   * <pre>
   * getClasspathResource({@link net.sf.mmm.util.reflect.ClasspathUtil}.class, ".xml")
   * </pre>
   * 
   * This is the same as:
   * 
   * <pre>
   * getClasspathResource("net/sf/mmm/util/reflect/ClasspathUtil.xml")
   * </pre>
   * 
   * @see #getClasspathResource(Package, String)
   * 
   * @param someClass
   * @param suffix
   * @return an URL pointing to the requested resource.
   * @throws ResourceNotAvailableException
   *         if the requested resource is NOT available.
   */
  public static URL getClasspathResource(Class<?> someClass, String suffix)
      throws ResourceNotAvailableException {

    String absolutePath = someClass.getName().replace('.', '/') + suffix;
    return getClasspathResource(absolutePath);
  }

  /**
   * This method gets the
   * {@link #getClasspathResource(String) classpath-resource} identified by
   * <code>somePackage</code> and the given <code>filename</code>.<br>
   * E.g. the following code would get a resource name "relection.properties"
   * from the same package where this class is located:
   * 
   * <pre>
   * getClasspathResource({@link ClasspathUtil}.class.{@link Class#getPackage() getPackage}(), "relection.properties")
   * </pre>
   * 
   * @param somePackage
   *        is the package identifying the path where the requested resource is
   *        located.
   * @param filename
   *        is the name of the requested resource.
   * @return an URL pointing to the requested resource.
   * @throws ResourceNotAvailableException
   *         if the requested resource is NOT available.
   */
  public static URL getClasspathResource(Package somePackage, String filename)
      throws ResourceNotAvailableException {

    String absolutePath = somePackage.getName().replace('.', '/') + '/' + filename;
    return getClasspathResource(absolutePath);
  }

}
