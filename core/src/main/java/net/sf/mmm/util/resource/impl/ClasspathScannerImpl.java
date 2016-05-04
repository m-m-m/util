/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.resource.impl;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;

import net.sf.mmm.util.collection.base.FilteredIterable;
import net.sf.mmm.util.component.base.AbstractLoggableComponent;
import net.sf.mmm.util.filter.api.Filter;
import net.sf.mmm.util.filter.base.ConstantFilter;
import net.sf.mmm.util.reflect.api.ReflectionUtil;
import net.sf.mmm.util.resource.api.BrowsableResource;
import net.sf.mmm.util.resource.api.ClasspathScanner;
import net.sf.mmm.util.resource.api.DataResource;
import net.sf.mmm.util.resource.api.ResourceNotAvailableException;
import net.sf.mmm.util.resource.api.ResourcePath;
import net.sf.mmm.util.resource.api.ResourcePathNode;
import net.sf.mmm.util.resource.api.ResourceUriUndefinedException;
import net.sf.mmm.util.resource.base.AbstractBrowsableResource;
import net.sf.mmm.util.resource.base.ClasspathResource;

/**
 * Implementation of {@link ClasspathScanner}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 7.0.0
 */
@Named
public class ClasspathScannerImpl extends AbstractLoggableComponent implements ClasspathScanner {

  private  ReflectionUtil reflectionUtil;

  private  Cache cache;

  /**
   * The constructor.
   */
  public ClasspathScannerImpl() {

    super();
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
      Set<String> resourceNames = this.reflectionUtil
          .findResourceNames("", true, ConstantFilter.getInstance(true));
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

    return new FilteredIterable<>(getCache().getClasspathResourceFiles(), filter);
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

  /**
   * Abstract base implementation of {@link BrowsableResource} for {@link ClasspathFile} or {@link ClasspathFolder}.
   */
  protected abstract static class AbstractBrowsableClasspathResource extends AbstractBrowsableResource implements
      ResourcePath {

    private  final ClasspathFolder parent;

    private  final String name;

    /**
     * The constructor.
     *
     * @param parent - see {@link #getParent()}.
     * @param name - see {@link #getName()}.
     */
    public AbstractBrowsableClasspathResource(ClasspathFolder parent, String name) {

      super();
      this.parent = parent;
      this.name = name;
    }

    @Override
    public String getName() {

      return this.name;
    }

    /**
     * @return the parent folder.
     */
    @Override
    public ClasspathFolder getParent() {

      return this.parent;
    }

    @Override
    public DataResource navigate(String resourcePath) throws ResourceUriUndefinedException {

      return this.parent.navigate(resourcePath);
    }

    @Override
    public Date getLastModificationDate() {

      // not supported
      return null;
    }

    @Override
    public String getSchemePrefix() {

      return ClasspathResource.SCHEME_PREFIX;
    }

    @Override
    public String getUri() {

      return getSchemePrefix() + getPath();
    }

    @Override
    public ClasspathFolder getRoot() {

      if (this.parent == null) {
        return (ClasspathFolder) this;
      }
      return this.parent.getRoot();
    }

    @Override
    public boolean isAbsolute() {

      return true;
    }

    @Override
    public boolean isRoot() {

      return this.parent == null;
    }

    @Override
    public String getPath() {

      StringBuilder buffer = new StringBuilder();
      getPath(buffer);
      return buffer.toString();
    }

    /**
     * @param buffer is the {@link StringBuilder} where to append the string representation to.
     */
    protected void getPath(StringBuilder buffer) {

      if (this.parent != null) {
        if (!this.parent.isRoot()) {
          this.parent.getPath(buffer);
          buffer.append('/');
        }
      }
      buffer.append(this.name);
    }

    @Override
    public abstract Iterable<? extends AbstractBrowsableClasspathResource> getChildResources();

    /**
     * Initializes this resource after it has been properly initialzed.
     */
    protected void init() {

      for (AbstractBrowsableClasspathResource child : getChildResources()) {
        child.init();
      }
    }

  }

  /**
   * A folder (package) of the classpath.
   */
  protected static class ClasspathFolder extends AbstractBrowsableClasspathResource {

    private  List<AbstractBrowsableClasspathResource> children;

    /**
     * The constructor.
     *
     * @param parent - see {@link #getParent()}.
     * @param name - see {@link #getName()}.
     */
    public ClasspathFolder(ClasspathFolder parent, String name) {

      super(parent, name);
      this.children = new ArrayList<>();
    }

    /**
     * Initializes this resource after it has been properly completed.
     */
    @Override
    protected void init() {

      this.children = Collections.unmodifiableList(this.children);
      super.init();
    }

    /**
     * @param name is the name of the requested {@link #getChildResources() child} folder.
     * @return the existing {@link #getChildResources() child} folder or a new one created and added otherwise.
     */
    public AbstractBrowsableClasspathResource getChildResource(String name) {

      for (AbstractBrowsableClasspathResource child : this.children) {
        if (child.name.equals(name)) {
          return child;
        }
      }
      return null;
    }

    /**
     * @param name is the name of the requested {@link #getChildResources() child} folder.
     * @return the existing {@link #getChildResources() child} folder or a new one created and added otherwise.
     */
    public ClasspathFolder getOrCreateChildFolder(String name) {

      AbstractBrowsableClasspathResource child = getChildResource(name);
      if (child != null) {
        if (child.isFolder()) {
          return (ClasspathFolder) child;
        }
      }
      ClasspathFolder newChild = new ClasspathFolder(this, name);
      this.children.add(newChild);
      return newChild;
    }

    @Override
    public Iterable<? extends AbstractBrowsableClasspathResource> getChildResources() {

      return this.children;
    }

    @Override
    public boolean isFolder() {

      return true;
    }

    @Override
    public boolean isData() {

      return false;
    }

    @Override
    public URL getUrl() throws ResourceNotAvailableException {

      throw new ResourceNotAvailableException(getPath());
    }

    /**
     * @see #navigate(String)
     *
     * @param path the {@link ResourcePathNode}.
     * @return the {@link AbstractBrowsableClasspathResource}.
     */
    public AbstractBrowsableClasspathResource navigate(ResourcePathNode<Void> path) {

      ClasspathFolder folder = getParent();
      if (folder == null) {
        // in case of root...
        folder = this;
      }
      List<ResourcePathNode<Void>> pathList = path.asList();
      for (ResourcePathNode<Void> node : pathList) {
        if (node.isRoot()) {
          if (node.isAbsolute()) {
            if (node == ResourcePathNode.ROOT_ABSOLUTE) {
              folder = getRoot();
            } else {
              throw new IllegalArgumentException(node.getName());
            }
          }
        } else if (node.isParentDirectory()) {
          if (!folder.isRoot()) {
            folder = folder.getParent();
          }
        } else {
          AbstractBrowsableClasspathResource childResource = folder.getChildResource(node.getName());
          if (childResource == null) {
            return null;
          }
          if (childResource.isFolder()) {
            folder = (ClasspathFolder) childResource;
          } else if (node == path) {
            return childResource;
          } else {
            // actually illegal classpath
            return null;
          }
        }
      }
      return folder;
    }

    @Override
    public DataResource navigate(String resourcePath) throws ResourceUriUndefinedException {

      return navigate(resourcePath, false);
    }

    /**
     * @see #navigate(ResourcePathNode)
     *
     * @param resourcePath the path to navigate to.
     * @param returnNullIfNotExists - if {@code true} then {@code null} is returned for non-existent
     *        resources, otherwise a {@link ClasspathResource} is created and returned.
     * @return the requested {@link DataResource}.
     */
    public DataResource navigate(String resourcePath, boolean returnNullIfNotExists) {

      ResourcePathNode<Void> path = ResourcePathNode.create(resourcePath);
      DataResource result = navigate(path);
      if ((result == null) && !returnNullIfNotExists) {
        String parentPath = "";
        if (!isRoot()) {
          parentPath = getParent().getPath();
        }
        ResourcePathNode<Void> targetPath = ResourcePathNode.create(parentPath).navigateTo(path);
        String classpath = targetPath.toString();
        if (targetPath.isAbsolute()) {
          classpath = classpath.substring(1);
        }
        return new ClasspathResource(classpath);
      }
      return result;
    }
  }

  /**
   * A file on the classpath. Like {@link ClasspathResource} but {@link BrowsableResource browsable}.
   */
  protected static class ClasspathFile extends AbstractBrowsableClasspathResource {

    private  URL url;

    /**
     * The constructor.
     *
     * @param parent - see {@link #getParent()}.
     * @param name - see {@link #getName()}.
     */
    public ClasspathFile(ClasspathFolder parent, String name) {

      super(parent, name);
    }

    @Override
    public Iterable<? extends AbstractBrowsableClasspathResource> getChildResources() {

      return Collections.emptyList();
    }

    @Override
    public boolean isFolder() {

      return false;
    }

    @Override
    public boolean isData() {

      return true;
    }

    @Override
    public URL getUrl() throws ResourceNotAvailableException {

      if (this.url == null) {
        this.url = Thread.currentThread().getContextClassLoader().getResource(getPath());
      }
      return this.url;
    }
  }

  /**
   * Container with all cached data.
   */
  protected static class Cache {

    private  final ClasspathFolder root;

    private  final List<ClasspathFile> classpathResourceFiles;

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
