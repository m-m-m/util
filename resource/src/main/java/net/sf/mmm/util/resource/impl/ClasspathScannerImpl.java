/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.resource.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import net.sf.mmm.util.filter.api.Filter;
import net.sf.mmm.util.filter.base.ConstantFilter;
import net.sf.mmm.util.reflect.api.ReflectionUtil;
import net.sf.mmm.util.reflect.base.ReflectionUtilImpl;
import net.sf.mmm.util.resource.api.BrowsableResource;
import net.sf.mmm.util.resource.api.ClasspathScanner;
import net.sf.mmm.util.resource.api.DataResource;
import net.sf.mmm.util.resource.api.ResourcePathNode;

/**
 * Implementation of {@link ClasspathScanner}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 7.0.0
 */
public class ClasspathScannerImpl extends AbstractClasspathScanner {

  private ReflectionUtil reflectionUtil;

  private Cache cache;

  /**
   * The constructor.
   */
  public ClasspathScannerImpl() {

    super();
  }

  /**
   * @return the {@link ReflectionUtil} to use.
   */
  public ReflectionUtil getReflectionUtil() {

    return this.reflectionUtil;
  }

  /**
   * @param reflectionUtil is the {@link ReflectionUtil} to {@link Inject}.
   */
  @Inject
  public void setReflectionUtil(ReflectionUtil reflectionUtil) {

    getInitializationState().requireNotInitilized();
    this.reflectionUtil = reflectionUtil;
  }

  @Override
  protected void doInitialize() {

    super.doInitialize();
    if (this.reflectionUtil == null) {
      this.reflectionUtil = ReflectionUtilImpl.getInstance();
    }
  }

  /**
   * @return the {@link Cache}. Will be created lazily.
   */
  protected final Cache getCache() {

    Cache currentCache = this.cache;
    if (currentCache == null) {
      initCache();
      currentCache = this.cache;
    }
    return currentCache;
  }

  /**
   * Scans the entire classpath and initializes the {@link #getReflectionUtil() root}.
   */
  private synchronized void initCache() {

    if (this.cache == null) {
      ClasspathFolder rootFolder = new ClasspathFolder(null, "");
      Set<String> resourceNames = this.reflectionUtil.findResourceNames("", true, ConstantFilter.getInstance(true));
      List<ClasspathFile> fileList = new ArrayList<>(resourceNames.size());
      for (String resource : resourceNames) {
        ResourcePathNode<Void> path = ResourcePathNode.create(resource);
        ClasspathFolder parent = createFolderRecursive(path.getParent(), rootFolder);
        ClasspathFile file = new ClasspathFile(parent, path.getName());
        parent.children.add(file);
        fileList.add(file);
      }
      this.cache = new Cache(rootFolder, fileList);
    }
  }

  @Override
  public synchronized void clearCaches() {

    this.cache = null;
  }

  /**
   * @param path the path to create.
   * @param rootFolder the root folder.
   * @return the folder for the given path.
   */
  private ClasspathFolder createFolderRecursive(ResourcePathNode<Void> path, ClasspathFolder rootFolder) {

    if (path.isRoot()) {
      return rootFolder;
    }
    ClasspathFolder parent = createFolderRecursive(path.getParent(), rootFolder);
    return parent.getOrCreateChildFolder(path.getName());
  }

  @Override
  public ClasspathFolder getClasspathResource() {

    return getCache().getRoot();
  }

  @Override
  public BrowsableResource getClasspathResource(String classpath) {

    return (BrowsableResource) getClasspathResource().navigate(classpath, true);
  }

  @Override
  public BrowsableResource getClasspathResource(Package pkg) {

    return getClasspathResource(pkg.getName().replace('.', '/'));
  }

  @Override
  public Iterable<? extends BrowsableResource> getClasspathResourceFiles() {

    return getCache().getClasspathResourceFiles();
  }

  @Override
  public Iterable<? extends BrowsableResource> getClasspathResourceFiles(Filter<? super BrowsableResource> filter) {

    return AbstractBrowsableClasspathResource.getResources(getCache().getClasspathResourceFiles(), filter);
  }

  @Override
  public Iterable<Class<?>> getClasspathResourceClasses(Filter<String> classnameFilter, Filter<Class<?>> classFilter) {

    return new ClassIterable(getCache().getClasspathResourceFiles(), classnameFilter, classFilter);
  }

  @Override
  public String getQualifiedName(DataResource classResource) throws IllegalArgumentException {

    if (classResource instanceof AbstractBrowsableClasspathResource) {
      String qualifiedName = ((AbstractBrowsableClasspathResource) classResource).getQualifiedName();
      if (qualifiedName != null) {
        return qualifiedName;
      }
    }
    return null;
  }

  @SuppressWarnings("unchecked")
  @Override
  public <T> Class<T> loadClass(BrowsableResource classResource) throws IllegalArgumentException {

    if (classResource instanceof ClasspathFile) {
      return (Class<T>) ((ClasspathFile) classResource).getJavaClass();
    }
    throw new IllegalArgumentException(classResource.getUri());
  }

  /**
   * Container with all cached data.
   */
  protected static class Cache {

    private final ClasspathFolder root;

    private final List<ClasspathFile> classpathResourceFiles;

    /**
     * The constructor.
     *
     * @param root - see {@link #getRoot()}.
     * @param fileList - see {@link #getClasspathResourceFiles()}.
     */
    public Cache(ClasspathFolder root, List<ClasspathFile> fileList) {

      super();
      root.init();
      this.root = root;
      this.classpathResourceFiles = Collections.unmodifiableList(fileList);
    }

    /**
     * @return the {@link ClasspathScanner#getClasspathResource() root folder}.
     */
    public ClasspathFolder getRoot() {

      return this.root;
    }

    /**
     * @return the {@link ClasspathScanner#getClasspathResourceFiles() classpath files}.
     */
    public List<ClasspathFile> getClasspathResourceFiles() {

      return this.classpathResourceFiles;
    }
  }

}
