/* $Id$ */
package net.sf.mmm.configuration.base;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import net.sf.mmm.configuration.api.ConfigurationException;
import net.sf.mmm.configuration.api.Configuration;
import net.sf.mmm.configuration.api.ConfigurationNotEditableException;
import net.sf.mmm.configuration.api.IllegalPathException;
import net.sf.mmm.configuration.api.MutableConfiguration;
import net.sf.mmm.configuration.base.path.ConditionIF;
import net.sf.mmm.configuration.base.path.PathParser;
import net.sf.mmm.configuration.base.path.PathSegment;

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

  /**
   * The constructor.
   */
  public AbstractConfiguration() {

    super();
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
    /*
     * if ((namespace == null) || (NAMESPACE_URI_NONE.equals(namespace))) {
     * return name; } else { return namespace + ":" + name; }
     */
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
   * requested child is to be an {@link Configuration.Type#ATTRIBUTE attribute} and no
   * attribute exists in the {@link #getNamespaceUri() namespace}, the request
   * defaults to NO {@link #getNamespaceUri() namespace} (<code>null</code>).<br>
   * The method is ONLY applicable for configurations of the
   * {@link #getType() type} {@link Configuration.Type#ELEMENT element}.
   * 
   * @param name
   *        is the {@link #getName() name} of the requested child. If the
   *        <code>name</code> starts with the
   *        {@link #NAME_PREFIX_ATTRIBUTE "attribute prefix"}, the requested
   *        child has the {@link #getType() type}
   *        {@link Configuration.Type#ATTRIBUTE attribute}, else {@link Configuration.Type#ELEMENT element}.
   * @return the requested child or <code>null</code> if no such child exists.
   * @throws ConfigurationException
   *         if applied to a configuration of the {@link #getType() type}
   *         {@link Configuration.Type#ATTRIBUTE attribute} or the given <code>name</code>
   *         or <code>namespace</code> has illegal syntax.
   */
  public AbstractConfiguration getChild(String name) {

    String namespace = getNamespaceUri();
    AbstractConfiguration result = getChild(name, namespace);
    if (result == null) {
      if ((name.charAt(0) == NAME_PREFIX_ATTRIBUTE) && !NAMESPACE_URI_NONE.equals(namespace)) {
        result = getChild(name, null);
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
   *        {@link Configuration.Type#ATTRIBUTE attribute}, else {@link Configuration.Type#ELEMENT element}.
   * @param namespaceUri
   *        is the {@link #getNamespaceUri() namespace} of the requested child.
   * @return the requested child or <code>null</code> if no such child exists.
   * @throws ConfigurationException
   *         if applied to a configuration of the {@link #getType() type}
   *         {@link Configuration.Type#ATTRIBUTE attribute} or the given <code>name</code>
   *         or <code>namespace</code> has illegal syntax.
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
   * If the child to create is an {@link Configuration.Type#ATTRIBUTE attribute} that already
   * exists, this method will NOT create a new attribute and return the existing
   * one instead.
   * 
   * @see net.sf.mmm.configuration.api.MutableConfiguration#createChild(java.lang.String,
   *      java.lang.String)
   * 
   * @param name
   *        is the {@link #getName() name} of the child to create. If the
   *        <code>name</code> starts with the
   *        {@link #NAME_PREFIX_ATTRIBUTE "attribute prefix"}, the requested
   *        child has the {@link #getType() type}
   *        {@link Configuration.Type#ATTRIBUTE attribute}, else {@link Configuration.Type#ELEMENT element}.
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
   *        {@link Configuration.Type#ATTRIBUTE attribute}, else {@link Configuration.Type#ELEMENT element}.
   * @param namespaceUri
   *        is the {@link #getNamespaceUri() namespace} of the requested child.
   * @return the requested child.
   */
  public AbstractConfiguration requireChild(String childName, String namespaceUri) {

    AbstractConfiguration child = getChild(childName, namespaceUri);
    if (child == null) {
      if ((namespaceUri != null)
          && (namespaceUri.equals(getNamespaceUri()) && (childName.charAt(0) == NAME_PREFIX_ATTRIBUTE))) {
        child = getChild(childName, NAMESPACE_URI_NONE);
      }
    }
    if (child == null) {
      child = doCreateChild(childName, namespaceUri);
    }
    return child;
  }

  /**
   * This method gets an iterator of the child
   * {@link Configuration configurations} with the given
   * {@link #getType() type}.
   * 
   * @param childType
   *        is the {@link #getType() type} of the requested children or
   *        <code>null</code> if the children of any type are requested.
   * @return an iterator containing the requested children.
   */
  public abstract Iterator<AbstractConfiguration> getChildren(Type childType);

  /**
   * This method gets an iterator of the child
   * {@link Configuration configurations} with the given <code>name</code>
   * and <code>namespaceUri</code>.
   * 
   * @param name
   *        is the {@link #getName() name} of the requested child. If the
   *        <code>name</code> starts with the
   *        {@link #NAME_PREFIX_ATTRIBUTE "attribute prefix"}, the requested
   *        child has the {@link #getType() type}
   *        {@link Configuration.Type#ATTRIBUTE attribute}, else {@link Configuration.Type#ELEMENT element}.
   * @param namespaceUri
   *        is the {@link #getNamespaceUri() namespace} of the requested child.
   * @return an iterator containing the requested children.
   */
  public abstract Iterator<AbstractConfiguration> getChildren(String name, String namespaceUri);

  /**
   * This method gets an iterator of the child
   * {@link Configuration configurations} with the given
   * {@link #getName() name} in the {@link #getNamespaceUri() namespace} of this
   * configuration. If an {@link Configuration.Type#ATTRIBUTE attribute} is requested and no
   * attribute exists in the {@link #getNamespaceUri() namespace}, the request
   * defaults to NO {@link #getNamespaceUri() namespace} (<code>null</code>).<br>
   * 
   * @param name
   *        is the {@link #getName() name} of the requested child. If the
   *        <code>name</code> starts with the
   *        {@link #NAME_PREFIX_ATTRIBUTE "attribute prefix"}, the requested
   *        child has the {@link #getType() type}
   *        {@link Configuration.Type#ATTRIBUTE attribute}, else {@link Configuration.Type#ELEMENT element}.
   * @return an iterator containing the requested children.
   */
  public Iterator<AbstractConfiguration> getChildren(String name) {

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

    char[] chars = path.toCharArray();
    if (chars.length == 0) {
      throw new ConfigurationException("Illegal path!" + path);
    }
    AbstractConfiguration node = this;
    int pos = 0;
    int nameStart = 0;
    char c = 0;
    boolean todo = true;
    while (todo) {
      if (pos < chars.length) {
        c = chars[pos++];
      } else {
        if (c == PATH_CONDITION_END) {

        } else {
          c = PATH_SEPARATOR;
        }
        pos++;
        todo = false;
      }
      switch (c) {
        case PATH_CONDITION_START:
        case PATH_SEPARATOR:
          int len = pos - nameStart - 1;
          String childName = new String(chars, nameStart, len);
          node = node.requireChild(childName, namespaceUri);
          if (c == PATH_CONDITION_START) {
            String conditionName = null;
            String conditionValue = null;
            int conditionStart = pos;
            int eqCount = 0;
            int valueStart = 0;
            while (conditionValue == null) {
              if (pos < chars.length) {
                c = chars[pos++];
              } else {
                throw new ConfigurationException("Illegal path!" + path);
              }
              switch (c) {
                case '=':
                  if (eqCount == 0) {
                    len = pos - conditionStart - 1;
                    conditionName = new String(chars, conditionStart, len);
                  } else if (eqCount > 1) {
                    throw new ConfigurationException("Illegal path!" + path);
                  }
                  eqCount++;
                  valueStart = pos;
                  break;
                case PATH_CONDITION_END:
                  if (conditionName == null) {
                    throw new ConfigurationException("Illegal path!" + path);
                  }
                  len = pos - valueStart - 1;
                  conditionValue = new String(chars, valueStart, len);
                  // TODO:
                  Iterator<? extends AbstractConfiguration> it = node.getChildren(childName,
                      namespaceUri);
                  while (it.hasNext()) {
                    AbstractConfiguration child = it.next();
                    String value = child.requireChild(childName, namespaceUri).getValue()
                        .getString(conditionValue);
                    if (conditionValue.equals(value)) {
                      node = child;
                      break;
                    }
                  }
                  break;
                case PATH_UNION:
                case PATH_INTERSECTION:
                case '*':
                case '?':
                case '<':
                case '>':
                case '!':
                  throw new ConfigurationException("Illegal path!" + path);
              }
            }
          }
          nameStart = pos;
          break;
        case PATH_CONDITION_END:
        case PATH_UNION:
        case PATH_INTERSECTION:
        case '*':
        case '?':
        case '=':
        case '<':
        case '>':
        case '!':
          throw new ConfigurationException("Illegal path!" + path);
      }
    }
    return node;
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

    PathParser parser = new PathParser(path);
    List<PathSegment> segmentList = new ArrayList<PathSegment>();
    Set<AbstractConfiguration> resultSet = new HashSet<AbstractConfiguration>();
    char state = Configuration.PATH_UNION;
    while (state != 0) {
      parser.parsePath(segmentList);
      addDescendants(segmentList, 0, resultSet);
      segmentList.clear();
      state = parser.getState();
      if ((state != PATH_UNION) && (state != 0)) {
        throw new IllegalPathException(path);
      }
    }
    return resultSet;
  }

  /**
   * This method adds all descendants that match the given <code>path</code>
   * to the given <code>set</code>.
   * 
   * @param path
   *        is the parsed descendant path (simple path part).
   * @param segmentIndex
   *        is the current index in the path.
   * @param set
   *        is the set where to add the matching descendants.
   */
  protected void addDescendants(List<PathSegment> path, int segmentIndex,
      Set<AbstractConfiguration> set) {

    Iterator<AbstractConfiguration> childIt;
    PathSegment segment = path.get(segmentIndex);
    String name = segment.getString();
    if (segment.isPattern()) {
      Type type;
      if (name.charAt(0) == NAME_PREFIX_ATTRIBUTE) {
        type = Type.ATTRIBUTE;
      } else {
        type = Type.ELEMENT;
      }
      childIt = getChildren(type);
    } else {
      childIt = getChildren(name);
    }
    int nextIndex = segmentIndex + 1;
    boolean lastSegment;
    if (nextIndex == path.size()) {
      lastSegment = true;
    } else {
      lastSegment = false;
    }
    ConditionIF condition = segment.getCondition();
    while (childIt.hasNext()) {
      AbstractConfiguration child = childIt.next();
      if ((condition == null) || (condition.accept(child))) {
        if (lastSegment) {
          set.add(child);
        } else {
          child.addDescendants(path, nextIndex, set);
        }
      }
    }
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
  protected abstract AbstractConfiguration getParent();

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
