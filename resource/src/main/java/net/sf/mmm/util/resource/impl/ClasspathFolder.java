/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.resource.impl;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.sf.mmm.util.resource.api.BrowsableResource;
import net.sf.mmm.util.resource.api.DataResource;
import net.sf.mmm.util.resource.api.ResourceNotAvailableException;
import net.sf.mmm.util.resource.api.ResourcePathNode;
import net.sf.mmm.util.resource.api.ResourceUriUndefinedException;
import net.sf.mmm.util.resource.base.ClasspathResource;

/**
 * A folder (package) of the classpath.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 7.3.0 (moved from 7.0.0)
 */
class ClasspathFolder extends AbstractBrowsableClasspathResource {

  List<AbstractBrowsableClasspathResource> children;

  /**
   * The constructor.
   *
   * @param parent - see {@link #getParent()}.
   * @param name - see {@link #getName()}.
   */
  ClasspathFolder(ClasspathFolder parent, String name) {

    super(parent, name);
    this.children = new ArrayList<>();
  }

  @Override
  protected void init() {

    this.children = Collections.unmodifiableList(this.children);
    super.init();
  }

  /**
   * @param childName is the name of the requested {@link #getChildResources() child resource}.
   * @return the existing {@link #getChildResources() child} folder or a new one created and added otherwise.
   */
  AbstractBrowsableClasspathResource getChildResource(String childName) {

    for (AbstractBrowsableClasspathResource child : this.children) {
      if (child.name.equals(childName)) {
        return child;
      }
    }
    return null;
  }

  /**
   * @param childName is the name of the requested {@link #getChildResources() child resource}.
   * @return the existing {@link #getChildResources() child} folder or a new one created and added otherwise.
   */
  ClasspathFolder getOrCreateChildFolder(String childName) {

    AbstractBrowsableClasspathResource child = getChildResource(childName);
    if (child != null) {
      if (child.isFolder()) {
        return (ClasspathFolder) child;
      }
    }
    ClasspathFolder newChild = new ClasspathFolder(this, childName);
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
   * @param create - {@code true} if non existing ressources should be created as fake resources,
   *        {@code false} oterhwise.
   * @return the {@link AbstractBrowsableClasspathResource}. May be {@code null} if the given {@code path}
   *         does not exist and {@code create} is {@code false}.
   */
  AbstractBrowsableClasspathResource navigate(ResourcePathNode<Void> path, boolean create) {

    ClasspathFolder folder = this;
    List<ResourcePathNode<Void>> pathList = path.asList();
    for (ResourcePathNode<Void> node : pathList) {
      String nodeName = node.getName();
      if (node.isRoot()) {
        if (node.isAbsolute()) {
          if (node == ResourcePathNode.ROOT_ABSOLUTE) {
            folder = getRoot();
          } else {
            throw new IllegalArgumentException(nodeName);
          }
        }
      } else if (node.isParentDirectory()) {
        if (!folder.isRoot()) {
          folder = folder.getParent();
        }
      } else {
        AbstractBrowsableClasspathResource childResource = folder.getChildResource(nodeName);
        if (childResource == null) {
          if (!create) {
            return null;
          }
          if ((node == path) && (nodeName.indexOf('.') >= 0)) {
            childResource = new ClasspathFile(folder, nodeName);
          } else {
            childResource = new ClasspathFolder(folder, nodeName);
          }
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

  @Override
  public BrowsableResource cd(String path) {

    ResourcePathNode<Void> pathNode = ResourcePathNode.create(path);
    BrowsableResource result = navigate(pathNode, true);
    return result;
  }

  /**
   * @see #navigate(ResourcePathNode)
   *
   * @param resourcePath the path to navigate to.
   * @param returnNullIfNotExists - if {@code true} then {@code null} is returned for non-existent resources,
   *        otherwise a {@link ClasspathResource} is created and returned.
   * @return the requested {@link DataResource}.
   */
  DataResource navigate(String resourcePath, boolean returnNullIfNotExists) {

    ResourcePathNode<Void> path = ResourcePathNode.create(resourcePath);
    ClasspathFolder parent = getParent();
    if (parent == null) {
      // in case of root...
      parent = this;
    }
    DataResource result = parent.navigate(path, false);
    if ((result == null) && !returnNullIfNotExists) {
      String parentPath = "";
      if (!isRoot()) {
        parentPath = parent.getPath();
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
