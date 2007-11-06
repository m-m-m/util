/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.configuration.base;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import net.sf.mmm.configuration.api.ConfigurationException;
import net.sf.mmm.configuration.api.Configuration;
import net.sf.mmm.configuration.api.ConfigurationNotEditableException;
import net.sf.mmm.configuration.api.IllegalDescendantPathException;
import net.sf.mmm.configuration.api.MutableConfiguration;
import net.sf.mmm.configuration.base.iterator.EmptyConfigurationIterator;
import net.sf.mmm.configuration.base.iterator.SiblingIterator;
import net.sf.mmm.configuration.base.path.DescendantPathParser;
import net.sf.mmm.configuration.base.path.DescendantPathWalker;
import net.sf.mmm.configuration.base.path.PathSegment;
import net.sf.mmm.configuration.base.path.SimplePathSegment;
import net.sf.mmm.term.api.TermParser;
import net.sf.mmm.term.impl.SimpleTermParser;
import net.sf.mmm.util.scanner.CharacterSequenceScanner;

/**
 * This is the abstract base implementation of the
 * {@link net.sf.mmm.configuration.api.MutableConfiguration} interface. It is
 * the "internal API" for a
 * {@link net.sf.mmm.configuration.api.MutableConfiguration configuration} and
 * even the complete {@link net.sf.mmm.configuration.base base-implementation}
 * relies on this abstract implementation. The interface is used to make life
 * easier for the end user and to be able to do internal refactoring without
 * affecting the API.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractConfiguration implements MutableConfiguration {

  /** the default parser to use */
  private static final TermParser BOOTSTRAP_PARSER = new SimpleTermParser();

  /** @see #getNextSibling() */
  private AbstractConfiguration next;

  /** @see #getPreviousSibling() */
  private AbstractConfiguration previous;

  /**
   * The constructor.
   */
  public AbstractConfiguration() {

    super();
    this.next = this;
    this.previous = this;
  }

  /**
   * This method is called to recursively initialize the configuration tree. It
   * creates the child configurations of this configuration and
   * {@link #initialize() initializes} them. This is NOT done at construction to
   * prevent problems (e.g .infinity loops).<br>
   * Override this method in your custom implementation to perform
   * initialization.
   */
  public void initialize() {

  }

  /**
   * This method creates a qualified name build of name and namespaceUri.
   * 
   * @param name is the
   *        {@link net.sf.mmm.configuration.api.Configuration#getName() name}.
   * @param namespace is the namespace (URI or prefix) and may be
   *        <code>null</code> for NO namespace.
   * @return the qualified name.
   */
  public static QName createQualifiedName(String name, String namespace) {

    return new QName(name, namespace);
  }

  /**
   * {@inheritDoc}
   */
  public final String getPath() {

    StringBuffer result = new StringBuffer();
    buildPath(result);
    return result.toString();
  }

  /**
   * This method builds the {@link #getPath() path} to the given
   * <code>buffer</code>.
   * 
   * @param buffer is the string buffer where the path will be appended to.
   */
  private void buildPath(StringBuffer buffer) {

    AbstractConfiguration parent = getParent();
    if (parent != null) {
      parent.buildPath(buffer);
    }
    buffer.append(Configuration.PATH_SEPARATOR);
    buffer.append(getName());
  }

  /**
   * This method gets the next configuration sibling with the same
   * {@link #getName() name} and {@link #getNamespaceUri() namespace}.<br>
   * ATTENTION: the siblings are connected cyclically. Be careful to avoid
   * infinity loops.
   * 
   * @return the next sibling or the <code>head</code> if this configuration
   *         is the tail.
   */
  public final AbstractConfiguration getNextSibling() {

    return this.next;
  }

  /**
   * This method gets the previous configuration sibling with the same
   * {@link #getName() name} and {@link #getNamespaceUri() namespace}.<br>
   * ATTENTION: the siblings are connected cyclically. Be careful to avoid
   * infinity loops.
   * 
   * @return the previous sibling or the <code>tail</code> if this
   *         configuration is the head.
   */
  public final AbstractConfiguration getPreviousSibling() {

    return this.previous;
  }

  /**
   * This method gets the index of this node in its sibling list.<br>
   * <b>ATTENTION:</b><br>
   * This is a relatively expensive operation because it needs to find the head
   * of the list and step through the linked list until it hits the head.
   * 
   * @return the sibling index.
   */
  public int getSiblingIndex() {

    AbstractConfiguration parent = getParent();
    int index = 0;
    if (parent != null) {
      AbstractConfiguration headSibling = parent.getChild(getName(), getNamespaceUri());
      AbstractConfiguration pointer = this;
      while (pointer != headSibling) {
        index++;
        pointer = pointer.previous;
        if (pointer == this) {
          // TODO: NLS
          throw new ConfigurationException("Internal error: sibling-structure broken!");
        }
      }
    }
    return index;
  }

  /**
   * This method gets the number of siblings of this node (including the node
   * itself).<br>
   * <b>ATTENTION:</b><br>
   * This operation is a little expensive because it needs to walk through the
   * complete linked-list of siblings.
   * 
   * @return the sibling index.
   */
  public int getSiblingCount() {

    int count = 1;
    AbstractConfiguration pointer = this;
    while (pointer != this) {
      count++;
      pointer = pointer.previous;
    }
    return count;
  }

  /**
   * This method gets the sibling at the given <code>index</code>.
   * 
   * @param index is the index of the requested sibling.
   * @return the requested sibling.
   */
  public AbstractConfiguration getSibling(int index) {

    AbstractConfiguration parent = getParent();
    if (parent == null) {
      if (index == 0) {
        return this;
      } else {
        throw new IndexOutOfBoundsException(String.valueOf(index));
      }
    } else {
      AbstractConfiguration headSibling = parent.getChild(getName(), getNamespaceUri());
      AbstractConfiguration pointer = headSibling;
      int position = 0;
      while (position != index) {
        position++;
        pointer = pointer.next;
        if (pointer == headSibling) {
          throw new IndexOutOfBoundsException(String.valueOf(index));
        }
      }
      return pointer;

    }
  }

  /**
   * This method adds the given <code>element</code> as sibling to the tail.
   * 
   * @param element is the element to add.
   */
  public void addSibling(AbstractConfiguration element) {

    element.previous = this.previous;
    element.next = this;
    this.previous.next = element;
    this.previous = element;
  }

  /**
   * This method removes the given <code>element</code> from the sibling list.
   * 
   * @param element is the element to remove.
   * @return <code>true</code> if the element was successfully removed from
   *         the list.
   */
  public boolean removeSibling(AbstractConfiguration element) {

    AbstractConfiguration pointer = this;
    while (pointer != element) {
      pointer = pointer.next;
      // did we reach the end of the sibling list?
      if (pointer == this) {
        break;
      }
    }
    if (pointer == element) {
      pointer.previous.next = pointer.next;
      pointer.next.previous = pointer.previous;
      return true;
    } else {
      return false;
    }
  }

  /**
   * This method gets the
   * {@link net.sf.mmm.configuration.api.ConfigurationDocument document} this
   * configuration belongs to.
   * 
   * @return the owner document.
   */
  protected abstract AbstractConfigurationDocument getOwnerDocument();

  /**
   * This method gets the child with the given {@link #getName() name} in the
   * {@link #getNamespaceUri() namespace} of this configuration. If the
   * requested child is to be an {@link Configuration.Type#ATTRIBUTE attribute}
   * and no attribute exists in the {@link #getNamespaceUri() namespace}, the
   * request defaults to {@link #NAMESPACE_URI_NONE NO}
   * {@link #getNamespaceUri() namespace} (<code>null</code>).<br>
   * The method is ONLY applicable for configurations of the
   * {@link #getType() type} {@link Configuration.Type#ELEMENT element}.
   * 
   * @param name is the {@link #getName() name} of the requested child. If the
   *        <code>name</code> starts with the
   *        {@link #NAME_PREFIX_ATTRIBUTE "attribute prefix"}, the requested
   *        child has the {@link #getType() type}
   *        {@link Configuration.Type#ATTRIBUTE attribute}, else
   *        {@link Configuration.Type#ELEMENT element}.
   * @return the requested child or <code>null</code> if no such child exists.
   * @throws ConfigurationException if applied to a configuration of the
   *         {@link #getType() type}
   *         {@link Configuration.Type#ATTRIBUTE attribute} or the given
   *         <code>name</code> or <code>namespace</code> has illegal syntax.
   */
  public AbstractConfiguration getChild(String name) {

    String namespace = getNamespaceUri();
    AbstractConfiguration result = getChild(name, namespace);
    if (result == null) {
      if ((name.charAt(0) == NAME_PREFIX_ATTRIBUTE) && !NAMESPACE_URI_NONE.equals(namespace)) {
        result = getChild(name, NAMESPACE_URI_NONE);
      }
    }
    return result;
  }

  /**
   * This method gets the child with the given {@link #getName() name} and
   * {@link #getNamespaceUri() namespace}. <br>
   * The method is ONLY applicable for configurations of the
   * {@link #getType() type} {@link Configuration.Type#ELEMENT element}.
   * 
   * @param name is the {@link #getName() name} of the requested child. If the
   *        <code>name</code> starts with the
   *        {@link #NAME_PREFIX_ATTRIBUTE "attribute prefix"}, the requested
   *        child has the {@link #getType() type}
   *        {@link Configuration.Type#ATTRIBUTE attribute}, else
   *        {@link Configuration.Type#ELEMENT element}.
   * @param namespaceUri is the {@link #getNamespaceUri() namespace} of the
   *        requested child.
   * @return the requested child or <code>null</code> if no such child exists.
   * @throws ConfigurationException if applied to a configuration of the
   *         {@link #getType() type}
   *         {@link Configuration.Type#ATTRIBUTE attribute} or the given
   *         <code>name</code> or <code>namespace</code> has illegal syntax.
   */
  public abstract AbstractConfiguration getChild(String name, String namespaceUri)
      throws ConfigurationException;

  /**
   * {@inheritDoc}
   */
  public AbstractConfiguration createChild(String name, String namespaceUri)
      throws ConfigurationException {

    if (!isEditable()) {
      throw new ConfigurationNotEditableException(this);
    }
    if (name.length() == 0) {
      throw new IllegalNameException(name, this);
    }
    if ((name.length() == 1) && (name.charAt(0) == NAME_PREFIX_ATTRIBUTE)) {
      throw new IllegalNameException(name, this);
    }
    return doCreateChild(name, namespaceUri);
  }

  /**
   * This method creates the child with the given {@link #getName() name} and
   * {@link #getNamespaceUri() namespace}.<br>
   * If the child to create is an {@link Configuration.Type#ATTRIBUTE attribute}
   * that already exists, this method will NOT create a new attribute and return
   * the existing one instead.
   * 
   * @see net.sf.mmm.configuration.api.MutableConfiguration#createChild(java.lang.String,
   *      java.lang.String)
   * 
   * @param name is the {@link #getName() name} of the child to create. If the
   *        <code>name</code> starts with the
   *        {@link #NAME_PREFIX_ATTRIBUTE "attribute prefix"}, the requested
   *        child has the {@link #getType() type}
   *        {@link Configuration.Type#ATTRIBUTE attribute}, else
   *        {@link Configuration.Type#ELEMENT element}.
   * @param namespaceUri is the {@link #getNamespaceUri() namespace} of the
   *        child to create or <code>null</code> for default namespace.
   * @return the created child.
   * @throws ConfigurationException if the operation failed (e.g. called on
   *         attribute).
   */
  abstract AbstractConfiguration doCreateChild(String name, String namespaceUri)
      throws ConfigurationException;

  /**
   * This method gets the child with the given {@link #getName() name} and
   * {@link #getNamespaceUri() namespace}. If no such child exists, it will be
   * created.<br>
   * The method is ONLY applicable for configurations of the
   * {@link #getType() type} {@link Configuration.Type#ELEMENT element}.
   * 
   * @see Configuration#getDescendant(String, String)
   * 
   * @param childName is the {@link #getName() name} of the requested child. If
   *        the <code>name</code> starts with the
   *        {@link #NAME_PREFIX_ATTRIBUTE "attribute prefix"}, the requested
   *        child has the {@link #getType() type}
   *        {@link Configuration.Type#ATTRIBUTE attribute}, else
   *        {@link Configuration.Type#ELEMENT element}.
   * @param namespaceUri is the {@link #getNamespaceUri() namespace} of the
   *        requested child.
   * @return the requested child.
   */
  public AbstractConfiguration requireChild(String childName, String namespaceUri) {

    AbstractConfiguration child = getChild(childName, namespaceUri);
    if (child == null) {
      if (isAddDefaults()) {
        child = doCreateChild(childName, namespaceUri);
      } else {
        Configuration.Type type;
        if (childName.charAt(0) == Configuration.NAME_PREFIX_ATTRIBUTE) {
          type = Configuration.Type.ATTRIBUTE;
        } else {
          type = Configuration.Type.ELEMENT;
        }
        child = EmptyConfiguration.getInstance(type);
      }
    }
    return child;
  }

  /**
   * This method gets an iterator of the child
   * {@link Configuration configurations} with the given {@link #getType() type}.
   * 
   * @param childType is the {@link #getType() type} of the requested children
   *        or <code>null</code> if the children of any type are requested.
   * @return an iterator containing the requested children.
   */
  public abstract Iterator<AbstractConfiguration> getChildren(Type childType);

  /**
   * This method gets an iterator of the child
   * {@link Configuration configurations} whose {@link #getName() name} matches
   * the given <code>namePattern</code> in the
   * {@link #getNamespaceUri() namespace} of this configuration..
   * 
   * @param namePattern is a pattern that needs to be matched by the
   *        {@link #getName() name} of the requested children.
   * @return an iterator containing the requested children.
   */
  public Iterator<AbstractConfiguration> getChildren(Pattern namePattern) {

    return getChildren(namePattern, getNamespaceUri());
  }

  /**
   * This method gets an iterator of the child
   * {@link Configuration configurations} whose {@link #getName() name} matches
   * the given <code>namePattern</code> and whose
   * {@link #getNamespaceUri() namespace-URI}
   * {@link String#equals(Object) equals} the given <code>namespaceUri</code>.
   * 
   * @param namePattern is a pattern that needs to be matched by the
   *        {@link #getName() name} of the requested children.
   * @param namespaceUri is the {@link #getNamespaceUri() namespace} of the
   *        requested children or <code>null</code> if the namespace should be
   *        ignored.
   * @return an iterator containing the requested children.
   */
  public abstract Iterator<AbstractConfiguration> getChildren(Pattern namePattern,
      String namespaceUri);

  /**
   * This method gets an iterator of the child
   * {@link Configuration configurations} with the given {@link #getName() name}
   * in the {@link #getNamespaceUri() namespace} of this configuration. If an
   * {@link Configuration.Type#ATTRIBUTE attribute} is requested and no
   * attribute exists in the {@link #getNamespaceUri() namespace}, the request
   * defaults to NO {@link #getNamespaceUri() namespace} (<code>null</code>).<br>
   * 
   * @param name is the {@link #getName() name} of the requested child. If the
   *        <code>name</code> starts with the
   *        {@link #NAME_PREFIX_ATTRIBUTE "attribute prefix"}, the requested
   *        child has the {@link #getType() type}
   *        {@link Configuration.Type#ATTRIBUTE attribute}, else
   *        {@link Configuration.Type#ELEMENT element}.
   * @return an iterator containing the requested children.
   */
  public Iterator<AbstractConfiguration> getChildren(String name) {

    // we do a special treatment here for attributes that have no namespace...
    if (name.length() == 0) {
      throw new IllegalNameException(name, this);
    }
    String namespace = getNamespaceUri();
    if (name.charAt(0) == NAME_PREFIX_ATTRIBUTE) {
      AbstractConfiguration child = getChild(name);
      if (child == null) {
        return EmptyConfigurationIterator.getInstance();
      } else {
        return new SingleConfigurationIterator(child);
      }
    } else {
      return getChildren(name, namespace);
    }
  }

  /**
   * This method gets an iterator of the child
   * {@link Configuration configurations} with the given <code>name</code> and
   * <code>namespaceUri</code>.
   * 
   * @param name is the {@link #getName() name} of the requested child. If the
   *        <code>name</code> starts with the
   *        {@link #NAME_PREFIX_ATTRIBUTE "attribute prefix"}, the requested
   *        child has the {@link #getType() type}
   *        {@link Configuration.Type#ATTRIBUTE attribute}, else
   *        {@link Configuration.Type#ELEMENT element}.
   * @param namespaceUri is the {@link #getNamespaceUri() namespace} of the
   *        requested child.
   * @return an iterator containing the requested children.
   */
  public Iterator<AbstractConfiguration> getChildren(String name, String namespaceUri) {

    return new SiblingIterator(getChild(name, namespaceUri));
  }

  /**
   * {@inheritDoc}
   */
  public AbstractConfiguration getDescendant(String path) {

    return getDescendant(path, getNamespaceUri());
  }

  /**
   * {@inheritDoc}
   */
  public AbstractConfiguration getDescendant(String path, String namespaceUri) {

    if (path.equals(PATH_THIS_STRING)) {
      return this;
    }
    // This one is a little tricky:
    // if the given path does NOT already exist we need to find the
    // deepest part of the path that does exist...

    // path: /foo/bar[x='5']/child
    // example config:
    // <foo id="1"/>
    // <foo id="2">
    // <bar id="3" x="4"/>
    // </foo>
    // <foo id="4">
    // <bar id="5" x="5"/>
    // <bar id="6" x="5"/>
    // </foo>

    // parse the given descendant path
    CharacterSequenceScanner parser = new CharacterSequenceScanner(path);
    PathSegment[] pathSegments = DescendantPathParser.parsePath(parser, true);
    if (parser.hasNext() || pathSegments.length == 0) {
      throw new IllegalDescendantPathException(path);
    }
    return DescendantPathWalker.getDescendant(this, namespaceUri, pathSegments);
  }

  /**
   * {@inheritDoc}
   */
  public Collection<AbstractConfiguration> getDescendants(String path) {

    return getDescendants(path, getNamespaceUri());
  }

  /**
   * {@inheritDoc}
   */
  public Collection<AbstractConfiguration> getDescendants(String path, String namespaceUri) {

    CharacterSequenceScanner parser = new CharacterSequenceScanner(path);
    List<PathSegment> segmentList = new ArrayList<PathSegment>();
    List<SimplePathSegment> conditionSegments = new ArrayList<SimplePathSegment>();
    Set<AbstractConfiguration> resultSet = new LinkedHashSet<AbstractConfiguration>();
    Set<AbstractConfiguration> intersectionSet = null;
    char state = Configuration.PATH_UNION;
    while (state != 0) {
      DescendantPathParser.parsePath(parser, segmentList, false, conditionSegments);
      if (state == PATH_INTERSECTION) {
        if (intersectionSet == null) {
          intersectionSet = new LinkedHashSet<AbstractConfiguration>();
        } else {
          intersectionSet.clear();
        }
        DescendantPathWalker.addDescendants(this, namespaceUri, segmentList, 0, intersectionSet);
        resultSet.retainAll(intersectionSet);
      } else {
        DescendantPathWalker.addDescendants(this, namespaceUri, segmentList, 0, resultSet);
      }
      segmentList.clear();
      if (parser.hasNext()) {
        char c = parser.next();
        if (c == PATH_UNION) {
          state = c;
        } else if (c == PATH_INTERSECTION) {
          state = c;
        } else {
          throw new IllegalDescendantPathException(path);
        }
      } else {
        state = 0;
      }
    }
    return resultSet;
  }

  /**
   * This method gets the parent of this configuration.
   * 
   * @return the parent configuration or <code>null</code> if this
   *         configuration is the root-node.
   */
  public abstract AbstractConfiguration getParent();

  /**
   * {@inheritDoc}
   */
  public final boolean isDescendantOf(Configuration ancestor) {

    return (getAncestorDistance(ancestor) > 0);
  }

  /**
   * {@inheritDoc}
   */
  public final int getAncestorDistance(Configuration ancestor) {

    if (ancestor == this) {
      return 0;
    }
    AbstractConfiguration parent = getParent();
    if (parent == null) {
      return -1;
    } else if (parent == ancestor) {
      return 1;
    } else {
      int distance = parent.getAncestorDistance(ancestor);
      if (distance < 0) {
        return distance;
      } else {
        return distance + 1;
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  public final String getRelativePath(Configuration ancestor) {

    AbstractConfiguration parent = getParent();
    if (parent == null) {
      // TODO: NLS
      throw new ConfigurationException("Given configuration \"{0}\" is no ancestor!");
    } else {
      if (parent == ancestor) {
        return getName();
      } else {
        return parent.getRelativePath(ancestor) + Configuration.PATH_SEPARATOR + getName();
      }
    }
  }

  /**
   * This method adds the given child to this configuration.
   * 
   * @param child is the child to add.
   */
  protected void addChild(AbstractConfiguration child) {

    addChild(child, new QName(child));
  }

  /**
   * This method adds the given child to this configuration.
   * 
   * @param child is the child to add.
   * @param qName is the qualified-name of the given <code>child</code>.
   */
  protected abstract void addChild(AbstractConfiguration child, QName qName);

  /**
   * This method removes the given child from this configuration.
   * 
   * @param child is the {@link #getChildren(Type) child} configuration of this
   *        configuration that should be removed.
   * @return <code>true</code> if the <code>child</code> has been
   *         successfully removed, <code>false</code> if the
   *         <code>child</code> is no child of this configuration.
   */
  protected abstract boolean removeChild(AbstractConfiguration child);

  /**
   * This method removes this node including all its
   * {@link #getDescendant(String, String) descendants} from the underlying
   * configuration representation (e.g. properties, DOM tree, etc.). After
   * {@link net.sf.mmm.configuration.api.ConfigurationDocument#save() saving}
   * this information is lost.
   * 
   * @see #remove()
   * 
   * @throws ConfigurationException is something goes wrong.
   */
  protected abstract void doRemove() throws ConfigurationException;

  /**
   * {@inheritDoc}
   */
  public void remove() throws ConfigurationException {

    if (!isEditable()) {
      throw new ConfigurationNotEditableException(this);
    }
    AbstractConfiguration parent = getParent();
    if (parent != null) {
      parent.removeChild(this);
    }
    doRemove();
  }

  /**
   * This method disables this node including all its
   * {@link #getDescendant(String, String) descendants} from the underlying
   * configuration representation (e.g. properties, DOM tree, etc.). This will
   * be done by moving the configuration sub-tree to an
   * {@link net.sf.mmm.configuration.api.ConfigurationDocument#NAME_EXCLUDE exclude configuration}.
   * 
   * @see #disable()
   * 
   * @return the
   *         {@link net.sf.mmm.configuration.api.ConfigurationDocument#NAME_EXCLUDE exclude configuration}
   *         containing this node as child.
   * @throws ConfigurationException is something goes wrong.
   */
  protected abstract AbstractConfiguration doDisable() throws ConfigurationException;

  /**
   * {@inheritDoc}
   */
  public void disable() throws ConfigurationException {

    if (!isEditable()) {
      throw new ConfigurationNotEditableException(this);
    }
    AbstractConfiguration parent = getParent();
    if (parent != null) {
      parent.removeChild(this);
      parent.addChild(doDisable());
    }
  }

  /**
   * This method gets the parser used for expression terms in
   * {@link #getValue() values}.
   * 
   * @return the term parser to use.
   */
  public TermParser getTermParser() {

    AbstractConfigurationDocument ownerDoc = getOwnerDocument();
    TermParser parser = null;
    if (ownerDoc != null) {
      parser = ownerDoc.getTermParser();
    }
    if (parser == null) {
      parser = BOOTSTRAP_PARSER;
    }
    return parser;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    return getPath();
  }

}
