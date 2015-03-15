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

import net.sf.mmm.util.component.base.AbstractLoggableComponent;
import net.sf.mmm.util.exception.api.IllegalCaseException;
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

  /** @see #getReflectionUtil() */
  private ReflectionUtil reflectionUtil;

  /** The {@link ClasspathFolder} for the top-level (default) package. */
  private ClasspathFolder root;

  /**
   * The constructor.
   */
  public ClasspathScannerImpl() {

    super();
  }

  /**
   * Scans the entire classpath and initializes the {@link #getReflectionUtil() root}.
   */
  private synchronized void initRoot() {

    if (this.root == null) {
      ClasspathFolder rootFolder = new ClasspathFolder(null, "");
      Set<String> resourceNames = this.reflectionUtil.findResourceNames("", true, ConstantFilter.getInstance(true));
      for (String resource : resourceNames) {
        ResourcePathNode<Void> path = ResourcePathNode.create(resource);
        ClasspathFolder parent = createFolderRecursive(path.getParent(), rootFolder);
        ClasspathFile file = new ClasspathFile(parent, path.getName());
        parent.children.add(file);
      }
      this.root = rootFolder;
    }
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

  /**
   * {@inheritDoc}
   */
  @Override
  public BrowsableResource getClasspathResource() {

    if (this.root == null) {
      initRoot();
    }
    return this.root;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public BrowsableResource getClasspathResource(String classpath) {

    return (BrowsableResource) getClasspathResource().navigate(classpath);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public BrowsableResource getClasspathResource(Package pkg) {

    return getClasspathResource(pkg.getName().replace('.', '/'));
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
   * Abstract base implementation of {@link BrowsableResource} for {@link ClasspathFile} or
   * {@link ClasspathFolder}.
   */
  protected abstract static class AbstractBrowsableClasspathResource extends AbstractBrowsableResource implements
      ResourcePath {

    /** @see #getParent() */
    private final ClasspathFolder parent;

    /** @see #getName() */
    private final String name;

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

    /**
     * {@inheritDoc}
     */
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

    /**
     * {@inheritDoc}
     */
    @Override
    public DataResource navigate(String resourcePath) throws ResourceUriUndefinedException {

      return this.parent.navigate(resourcePath);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Date getLastModificationDate() {

      // not supported
      return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getSchemePrefix() {

      return ClasspathResource.SCHEME_PREFIX;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getUri() {

      return getSchemePrefix() + getPath();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClasspathFolder getRoot() {

      if (this.parent == null) {
        return (ClasspathFolder) this;
      }
      return this.parent.getRoot();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isAbsolute() {

      return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isRoot() {

      return this.parent == null;
    }

    /**
     * {@inheritDoc}
     */
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
        this.parent.getPath(buffer);
        buffer.append('/');
      }
      buffer.append(this.name);
    }

  }

  /**
   * A folder (package) of the classpath.
   */
  protected static class ClasspathFolder extends AbstractBrowsableClasspathResource {

    /** @see #getChildResources() */
    private List<AbstractBrowsableClasspathResource> children;

    /**
     * The constructor.
     *
     * @param parent - see {@link #getParent()}.
     * @param name - see {@link #getName()}.
     */
    public ClasspathFolder(ClasspathFolder parent, String name) {

      super(parent, name);
      this.children = new ArrayList<AbstractBrowsableClasspathResource>();
    }

    /**
     * @param name is the name of the requested {@link #getChildResources() child} folder.
     * @return the existing {@link #getChildResources() child} folder or a new one created and added
     *         otherwise.
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
     * @return the existing {@link #getChildResources() child} folder or a new one created and added
     *         otherwise.
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

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterable<? extends BrowsableResource> getChildResources() {

      if (this.children == null) {
        return Collections.EMPTY_LIST;
      }
      return this.children;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isFolder() {

      return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isData() {

      return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public URL getUrl() throws ResourceNotAvailableException {

      throw new ResourceNotAvailableException("this.resourcePath");
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
              // TODO
              throw new IllegalCaseException("" + node);
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

    /**
     * {@inheritDoc}
     */
    @Override
    public DataResource navigate(String resourcePath) throws ResourceUriUndefinedException {

      ResourcePathNode<Void> path = ResourcePathNode.create(resourcePath);
      DataResource result = navigate(path);
      if (result == null) {
        String parentPath = "/";
        if (!isRoot()) {
          parentPath = getParent().getPath();
        }
        ResourcePathNode<Void> targetPath = ResourcePathNode.create(parentPath).navigateTo(path);
        return new ClasspathResource(targetPath.toString());
      }
      return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Date getLastModificationDate() {

      return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getSchemePrefix() {

      return ClasspathResource.SCHEME_PREFIX;
    }

  }

  /**
   * A file on the classpath. Like {@link ClasspathResource} but {@link BrowsableResource browsable}.
   */
  protected static class ClasspathFile extends AbstractBrowsableClasspathResource {

    /** @see #getUrl() */
    private URL url;

    /**
     * The constructor.
     *
     * @param parent - see {@link #getParent()}.
     * @param name - see {@link #getName()}.
     */
    public ClasspathFile(ClasspathFolder parent, String name) {

      super(parent, name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterable<? extends BrowsableResource> getChildResources() {

      return Collections.emptyList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isFolder() {

      return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isData() {

      return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public URL getUrl() throws ResourceNotAvailableException {

      if (this.url == null) {
        this.url = Thread.currentThread().getContextClassLoader().getResource(getPath());
      }
      return this.url;
    }

  }

}
