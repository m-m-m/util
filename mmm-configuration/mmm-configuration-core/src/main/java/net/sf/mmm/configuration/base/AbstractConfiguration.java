/* $Id$ */
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
import net.sf.mmm.util.ListCharFilter;
import net.sf.mmm.util.StringParser;

/**
 * This is the abstract base implementation of the
 * {@link net.sf.mmm.configuration.api.MutableConfiguration} interface. It is
 * the "interal API" for a
 * {@link net.sf.mmm.configuration.api.MutableConfiguration configuration} and
 * even the complete {@link net.sf.mmm.configuration.base base-implementation}
 * relies on this abstract implementation. The interface is used to make life
 * easier for the end user and to be able to do internal refactorings without
 * affecting the API user.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractConfiguration implements MutableConfiguration {

  /** filter that excepts all chars except those reserved for descendent path */
  private static final ListCharFilter SEGMENT_FILTER = new ListCharFilter(false, PATH_SEPARATOR,
      PATH_CONDITION_START, PATH_UNION, PATH_INTERSECTION);

  /** filter that excepts all chars except those reserved for conditions */
  private static final ListCharFilter CONDITION_FILTER = new ListCharFilter(false,
      PATH_CONDITION_START, PATH_CONDITION_END, '=', '<', '>');

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
   * This method is called to recursively initilialize the configuration tree.
   * It creates the child configurations of this configuration and
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
   * @param name
   *        is the
   *        {@link net.sf.mmm.configuration.api.Configuration#getName() name}.
   * @param namespace
   *        is the namespace (URI or prefix) and may be <code>null</code> for
   *        NO namespace.
   * @return the qualified name.
   */
  public static QName createQualifiedName(String name, String namespace) {

    return new QName(name, namespace);
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
   * @param index
   *        is the index of the requested sibling.
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
   * @param element
   *        is the element to add.
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
   * @param element
   *        is the element to remove.
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
   * @param name
   *        is the {@link #getName() name} of the requested child. If the
   *        <code>name</code> starts with the
   *        {@link #NAME_PREFIX_ATTRIBUTE "attribute prefix"}, the requested
   *        child has the {@link #getType() type}
   *        {@link Configuration.Type#ATTRIBUTE attribute}, else
   *        {@link Configuration.Type#ELEMENT element}.
   * @return the requested child or <code>null</code> if no such child exists.
   * @throws ConfigurationException
   *         if applied to a configuration of the {@link #getType() type}
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
   * @param name
   *        is the {@link #getName() name} of the requested child. If the
   *        <code>name</code> starts with the
   *        {@link #NAME_PREFIX_ATTRIBUTE "attribute prefix"}, the requested
   *        child has the {@link #getType() type}
   *        {@link Configuration.Type#ATTRIBUTE attribute}, else
   *        {@link Configuration.Type#ELEMENT element}.
   * @param namespaceUri
   *        is the {@link #getNamespaceUri() namespace} of the requested child.
   * @return the requested child or <code>null</code> if no such child exists.
   * @throws ConfigurationException
   *         if applied to a configuration of the {@link #getType() type}
   *         {@link Configuration.Type#ATTRIBUTE attribute} or the given
   *         <code>name</code> or <code>namespace</code> has illegal syntax.
   */
  public abstract AbstractConfiguration getChild(String name, String namespaceUri)
      throws ConfigurationException;

  /**
   * @see net.sf.mmm.configuration.api.MutableConfiguration#createChild(java.lang.String,
   *      java.lang.String)
   */
  public AbstractConfiguration createChild(String name, String namespaceUri)
      throws ConfigurationException {

    if (!isEditable()) {
      throw new ConfigurationNotEditableException(this);
    }
    if (name.length() == 0) {
      throw new ConfigurationException("Child name must not be empty!");
    }
    if ((name.length() == 1) && (name.charAt(0) == NAME_PREFIX_ATTRIBUTE)) {
      throw new ConfigurationException("Child name must not be empty!");
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
   * @param name
   *        is the {@link #getName() name} of the child to create. If the
   *        <code>name</code> starts with the
   *        {@link #NAME_PREFIX_ATTRIBUTE "attribute prefix"}, the requested
   *        child has the {@link #getType() type}
   *        {@link Configuration.Type#ATTRIBUTE attribute}, else
   *        {@link Configuration.Type#ELEMENT element}.
   * @param namespaceUri
   *        is the {@link #getNamespaceUri() namespace} of the child to create
   *        or <code>null</code> for default namespace.
   * @return the created child.
   * @throws ConfigurationException
   *         if the operation failed (e.g. called on attribute).
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
   * @param childName
   *        is the {@link #getName() name} of the requested child. If the
   *        <code>name</code> starts with the
   *        {@link #NAME_PREFIX_ATTRIBUTE "attribute prefix"}, the requested
   *        child has the {@link #getType() type}
   *        {@link Configuration.Type#ATTRIBUTE attribute}, else
   *        {@link Configuration.Type#ELEMENT element}.
   * @param namespaceUri
   *        is the {@link #getNamespaceUri() namespace} of the requested child.
   * @return the requested child.
   */
  public AbstractConfiguration requireChild(String childName, String namespaceUri) {

    AbstractConfiguration child = getChild(childName, namespaceUri);
    if (child == null) {
      child = doCreateChild(childName, namespaceUri);
    }
    return child;
  }

  /**
   * This method gets an iterator of the child
   * {@link Configuration configurations} with the given {@link #getType() type}.
   * 
   * @param childType
   *        is the {@link #getType() type} of the requested children or
   *        <code>null</code> if the children of any type are requested.
   * @return an iterator containing the requested children.
   */
  public abstract Iterator<AbstractConfiguration> getChildren(Type childType);

  /**
   * This method gets an iterator of the child
   * {@link Configuration configurations} whos {@link #getName() name} matches
   * the given <code>namePattern</code> in the
   * {@link #getNamespaceUri() namespace} of this configuration..
   * 
   * @param namePattern
   *        is a pattern that needs to be matched by the {@link #getName() name}
   *        of the requested children.
   * @return an iterator containing the requested children.
   */
  public Iterator<AbstractConfiguration> getChildren(Pattern namePattern) {

    return getChildren(namePattern, getNamespaceUri());
  }

  /**
   * This method gets an iterator of the child
   * {@link Configuration configurations} whos {@link #getName() name} matches
   * the given <code>namePattern</code> and whos
   * {@link #getNamespaceUri() namespace-URI}
   * {@link String#equals(Object) equals} the given <code>namespaceUri</code>.
   * 
   * @param namePattern
   *        is a pattern that needs to be matched by the {@link #getName() name}
   *        of the requested children.
   * @param namespaceUri
   *        is the {@link #getNamespaceUri() namespace} of the requested
   *        children or <code>null</code> if the namespace should be ignored.
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
   * @param name
   *        is the {@link #getName() name} of the requested child. If the
   *        <code>name</code> starts with the
   *        {@link #NAME_PREFIX_ATTRIBUTE "attribute prefix"}, the requested
   *        child has the {@link #getType() type}
   *        {@link Configuration.Type#ATTRIBUTE attribute}, else
   *        {@link Configuration.Type#ELEMENT element}.
   * @return an iterator containing the requested children.
   */
  public Iterator<AbstractConfiguration> getChildren(String name) {

    // TODO
    // return getChildren(name, getNamespaceUri());
    if (name.length() == 0) {
      throw new IllegalArgumentException("Child name empty!");
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
   * @param name
   *        is the {@link #getName() name} of the requested child. If the
   *        <code>name</code> starts with the
   *        {@link #NAME_PREFIX_ATTRIBUTE "attribute prefix"}, the requested
   *        child has the {@link #getType() type}
   *        {@link Configuration.Type#ATTRIBUTE attribute}, else
   *        {@link Configuration.Type#ELEMENT element}.
   * @param namespaceUri
   *        is the {@link #getNamespaceUri() namespace} of the requested child.
   * @return an iterator containing the requested children.
   */
  public Iterator<AbstractConfiguration> getChildren(String name, String namespaceUri) {

    return new SiblingIterator(getChild(name, namespaceUri));
  }

  /**
   * @see net.sf.mmm.configuration.api.Configuration#getDescendant(java.lang.String)
   */
  public AbstractConfiguration getDescendant(String path) {

    return getDescendant(path, getNamespaceUri());
  }

  /**
   * @see net.sf.mmm.configuration.api.MutableConfiguration#getDescendant(java.lang.String,
   *      java.lang.String)
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
    StringParser parser = new StringParser(path);
    PathSegment[] pathSegments = DescendantPathParser.parsePath(parser, true);
    if (parser.hasNext() || pathSegments.length == 0) {
      throw new IllegalDescendantPathException(path);
    }
    return DescendantPathWalker.getDescendant(this, namespaceUri, pathSegments);
    /*
     * AbstractConfiguration descendant = this; for (int segmentIndex = 0;
     * segmentIndex < pathSegments.length; segmentIndex++) { PathSegment segment =
     * pathSegments[segmentIndex]; Iterator<AbstractConfiguration>
     * childIterator = descendant.getChildren(segment.getString(),
     * namespaceUri); int bestIndex = -1; AbstractConfiguration bestChild =
     * null; while (childIterator.hasNext()) { AbstractConfiguration child =
     * childIterator.next(); if (segment.getCondition().accept(child)) { //
     * forecast for (int i = segmentIndex + 1; i < pathSegments.length; i++) {
     * PathSegment forecastSegment = pathSegments[i];
     * 
     * AbstractConfiguration lookahead = childIterator.next(); } } } } return
     * descendant;
     */
  }

  public AbstractConfiguration getDescendantOld(String path, String namespaceUri) {

    if (path.length() == 0) {
      throw new ConfigurationException("Illegal path!" + path);
    }
    StringParser parser = new StringParser(path);
    AbstractConfiguration node = this;
    while (true) {
      String childName = parser.readWhile(SEGMENT_FILTER);
      char special = parser.forceNext();
      if ((special == PATH_SEPARATOR) || (special == PATH_CONDITION_START) || (special == 0)) {
        AbstractConfiguration descendant = node.requireChild(childName, namespaceUri);
        if (special == PATH_CONDITION_START) {
          // TODO: condition parser...
          String conditionName = parser.readWhile(CONDITION_FILTER);
          char c = parser.forceNext();
          // only [@name=value] supported
          if (c == '=') {
            String conditionValue = parser.readUntil(PATH_CONDITION_END, false);
            // TODO: parser does NOT need to be done here!!!
            if ((!parser.hasNext()) && (conditionValue != null)) {
              // condition syntax okay
              AbstractConfiguration child;
              Iterator<? extends AbstractConfiguration> it = descendant.getChildren(conditionName,
                  namespaceUri);
              while (it.hasNext()) {
                child = it.next();
                String value = child.getValue().getString(conditionValue);
                if (conditionValue.equals(value)) {
                  return descendant;
                }
              }
              descendant = node.createChild(childName, namespaceUri);
              child = descendant.createChild(conditionName, namespaceUri);
              child.getValue().getString(conditionValue);
              return descendant;
            }
          }
          // condition syntax was NOT correct...
          throw new ConfigurationException("Illegal path!" + path);
        } else if (special == 0) {
          return descendant;
        }
        node = descendant;
      } else {
        // special was illegal...
        throw new ConfigurationException("Illegal path!" + path);
      }
    }
  }

  /**
   * @see net.sf.mmm.configuration.api.Configuration#getDescendants(java.lang.String)
   */
  public Collection<AbstractConfiguration> getDescendants(String path) {

    return getDescendants(path, getNamespaceUri());
  }

  /**
   * @see net.sf.mmm.configuration.api.MutableConfiguration#getDescendants(java.lang.String,
   *      java.lang.String)
   */
  public Collection<AbstractConfiguration> getDescendants(String path, String namespaceUri) {

    StringParser parser = new StringParser(path);
    List<PathSegment> segmentList = new ArrayList<PathSegment>();
    List<SimplePathSegment> conditionSegments = new ArrayList<SimplePathSegment>();
    // TODO: use collection that keeps the ordering of insertion!!!
    Set<AbstractConfiguration> resultSet = new LinkedHashSet<AbstractConfiguration>();
    char state = Configuration.PATH_UNION;
    // TODO: also support intersections!
    while (state != 0) {
      DescendantPathParser.parsePath(parser, segmentList, false, conditionSegments);
      DescendantPathWalker.addDescendants(this, namespaceUri, segmentList, 0, resultSet);
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
   * @see net.sf.mmm.configuration.api.Configuration#getPath()
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
   * @param buffer
   *        is the string buffer where the path will be appended to.
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
   * This method gets the parent of this configuration.
   * 
   * @return the parent configuration or <code>null</code> if this
   *         configuration is the root-node.
   */
  public abstract AbstractConfiguration getParent();

  /**
   * @see net.sf.mmm.configuration.api.Configuration#isDescendantOf(net.sf.mmm.configuration.api.Configuration)
   */
  public final boolean isDescendantOf(Configuration superNode) {

    AbstractConfiguration parent = getParent();
    if (parent == null) {
      return false;
    } else if (parent == superNode) {
      return true;
    } else {
      return parent.isDescendantOf(superNode);
    }
  }

  /**
   * @see net.sf.mmm.configuration.api.Configuration#getRelativePath(net.sf.mmm.configuration.api.Configuration)
   */
  public final String getRelativePath(Configuration ancestor) {

    AbstractConfiguration parent = getParent();
    if (parent == null) {
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
   * @param child
   *        is the child to add.
   */
  protected abstract void addChild(AbstractConfiguration child);

  /**
   * This method removes the given child from this configuration.
   * 
   * @param child
   *        is the {@link #getChildren(Type) child} configuration of this
   *        configuration that should be removed.
   */
  protected abstract void removeChild(AbstractConfiguration child);

  /**
   * This method removes this node including all its
   * {@link #getDescendant(String, String) descendants} from the underlying
   * configuration representation (e.g. properties, DOM tree, etc.). After
   * {@link net.sf.mmm.configuration.api.ConfigurationDocument#save() saving}
   * this information is lost.
   * 
   * @see #remove()
   * 
   * @throws ConfigurationException
   *         is something goes wrong.
   */
  protected abstract void doRemove() throws ConfigurationException;

  /**
   * @see net.sf.mmm.configuration.api.MutableConfiguration#remove()
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
   * {@link net.sf.mmm.configuration.api.ConfigurationDocument#NAME_EXCLUDE exlcude configuration}.
   * 
   * @see #disable()
   * 
   * @return the
   *         {@link net.sf.mmm.configuration.api.ConfigurationDocument#NAME_EXCLUDE exlcude configuration}
   *         containing this node as child.
   * @throws ConfigurationException
   *         is something goes wrong.
   */
  protected abstract AbstractConfiguration doDisable() throws ConfigurationException;

  /**
   * @see net.sf.mmm.configuration.api.MutableConfiguration#disable()
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
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {

    return getPath();
  }

}
