/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.configuration.api;

import java.util.Collection;

import net.sf.mmm.configuration.api.event.ConfigurationChangeEvent;
import net.sf.mmm.configuration.api.event.ConfigurationChangeListener;
import net.sf.mmm.util.event.EventSource;
import net.sf.mmm.value.api.GenericValue;

/**
 * This is the interface used to access configuration. It actually represents a
 * node of a configuration tree. If you are familiar with the XML DOM API, you
 * can think of this interface as a {@link org.w3c.dom.Node} - only that it is a
 * lot easier to deal with.<br>
 * Here a little example:
 * 
 * <pre>
 * Configuration conf = getConfigurationFromSomewhere();
 * Configuration srvConf = conf.{@link #getDescendant(String) getDescendant}("server");
 * int port = srvConf.{@link #getDescendant(String) getDescendant}("@port").{@link #getValue() getValue}().{@link GenericValue#getInteger(Integer) getInteger}(80);
 * String host = srvConf.{@link #getDescendant(String) getDescendant}("@host").{@link #getValue() getValue}().{@link GenericValue#getString(String) getString}("localhost");
 * Runnable initializer = conf.{@link #getDescendant(String) getDescendant}("client/component[@name='initializer']/implementation").{@link #getValue() getValue}().{@link GenericValue#getJavaClassInstance(Class, Class) getJavaClassInstance}(Runnable.class, MyDefaultInitializer.class);
 * Iterator&lt;Configuration&gt; srvCompIt = srvConf.{@link #getDescendants(String) getDescendants}("components");
 * while (srvCompIt.hasNext()) {
 *   Configuration srvCompConf = srvCompIt.next();
 *   Iterator&lt;Configuration&gt; allChildIt = srvCompIt.{@link #getDescendants(String) getDescendants}("*|@*");
 *   ...
 * }
 * </pre>
 * 
 * <br>
 * This configuration API has the following goals:
 * <ul>
 * <li>easy to use</li>
 * <li>support for type conversions</li>
 * <li>read and write access</li>
 * <li>no access to parent configurations</li>
 * <li>access complex configurations</li>
 * <li>access configurations in different formats (XML, properties)</li>
 * <li>access configurations from different data sources</li>
 * <li>including other configurations without problems when saving</li>
 * <li>support for namespaces</li>
 * <li>auto-complete configuration from defaults in the accessing code</li>
 * </ul>
 * <br>
 * If you have struggled with the {@link org.w3c.dom DOM} API you will love the
 * easy approach of this configuration API. To keep it simple, there is just
 * this interface for the {@link #getType() types}
 * {@link Type#ATTRIBUTE element} and {@link Type#ATTRIBUTE attribute}. Else we
 * would have at least 3 interfaces instead and you would need to do casting.
 * The trade-of is that you have the method to get a
 * {@link #getDescendant(String, String) descendant} that does NOT make sense on
 * {@link Type#ATTRIBUTE attributes}.<br>
 * You may have experienced an update of a software without an update of the
 * documentation. The user may end up with incompatible configuration and does
 * not know how to fix. Or the configuration still works but the user does not
 * know how to configure and activate the cool new features. This configuration
 * does NOT help you to write your documentation. But if you read your
 * configuration as suggested by this API, you will help the user to find new
 * options in the configuration.
 * 
 * Look at the following example:
 * 
 * <pre>
 * {@link Configuration} conf1 = ...;
 * {@link Configuration} conf2 = conf1.{@link #getDescendant(String) getDescendant}("foo");
 * {@link Configuration} conf3 = conf2.{@link #getDescendant(String) getDescendant}("@bar");
 * {@link GenericValue} value1 = conf3.{@link #getValue()};
 * int intValue = value1.{@link GenericValue#getInteger(Integer) getInteger}(42);
 * {@link GenericValue} value2 = node2.getValue();
 * String stringValue = value2.{@link GenericValue#getString(String) getString}("hello");
 * </pre>
 * 
 * If this code is run first time while <code>conf1</code> is empty, the
 * following configuration is automatically created inside conf1 (notated in XML
 * syntax):
 * 
 * <pre>
 * &lt;foo bar="42"&gt;hello&lt;/foo&gt;
 * </pre>
 * 
 * If you modify the XML and change the values ("42" and "hello") then
 * <code>intValue</code> and <code>stringValue</code> will change
 * accordingly. Additionally the value of the attribute can be accessed faster
 * but still safe from
 * {@link java.lang.NullPointerException NullPointerException}s using:
 * 
 * <pre>
 * int intValue = conf1.{@link #getDescendant(String) getDescendant}("foo/@bar").{@link #getValue()}.{@link GenericValue#getInteger(Integer) getInteger}(42)
 * </pre>
 * 
 * Please note that the char constants defined in this interface will never
 * change. Therefore it does NOT make sense to write something like
 * <code>{@link #getDescendant(String) getDescendant}("foo"+{@link Configuration}.{@link #PATH_SEPARATOR}+{@link Configuration}.{@link #NAME_PREFIX_ATTRIBUTE}+"bar")</code>.
 * 
 * ATTENTION: Operations on a {@link Configuration} are NOT thread-safe. Anyways
 * it does not make sense to share the same configuration over multiple threads.
 * Different {@link #getDescendant(String, String) sub-trees} of the
 * configuration can be accessed in a concurrent way.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface Configuration extends
    EventSource<ConfigurationChangeEvent, ConfigurationChangeListener> {

  /**
   * This enum contains the {@link #getType() types} of configuration
   */
  enum Type {

    /**
     * This type represents a {@link Configuration configuration} that has a
     * {@link #getValue() value} and can have {@link #ELEMENT elements} and
     * {@link #ATTRIBUTE attributes} as children. It can have multiple child
     * {@link #ELEMENT elements} with the same {@link #getName() name} and
     * {@link #getNamespaceUri() namespace} what is not possible for child
     * {@link #ATTRIBUTE attributes}.<br>
     * It corresponds to a {@link org.w3c.dom.Element}.
     */
    ELEMENT,

    /**
     * This type represents a {@link Configuration configuration} that only has
     * a {@link #getValue() value} but can never have
     * {@link #getDescendants(String) descendants}. The {@link #getName() name}
     * of an attribute starts with {@link #NAME_PREFIX_ATTRIBUTE}.<br>
     * It corresponds to a {@link org.w3c.dom.Attr}.
     */
    ATTRIBUTE
  }

  /**
   * The {@link #getNamespaceUri() namespace URI} for no namespace.
   */
  String NAMESPACE_URI_NONE = "";

  /**
   * The separator character used to for the {@link #getPath() path}.
   */
  char PATH_SEPARATOR = '/';

  /**
   * The character used to identify the {@link Configuration configuration}
   * itself (<code>this</code>) as
   * {@link #getDescendant(String, String) descendant-path}.
   */
  char PATH_THIS = '.';

  /** @see #PATH_THIS */
  String PATH_THIS_STRING = String.valueOf(PATH_THIS);

  /**
   * The character used in as wildcard for
   * {@link #getDescendants(String, String) descendants}.
   */
  char PATH_WILDCARD = '*';

  /**
   * The character used in as any-char for
   * {@link #getDescendants(String, String) descendants}.
   */
  char PATH_ANYCHAR = '?';

  /**
   * The prefix for the {@link #getName() name} of a
   * {@link Configuration configuration} {@link Type#ATTRIBUTE attribute}.
   */
  char NAME_PREFIX_ATTRIBUTE = '@';

  /**
   * The character used for a union in the path for
   * {@link #getDescendants(String, String) descendants}.
   */
  char PATH_UNION = '|';

  /**
   * The character used for a intersection in the path for
   * {@link #getDescendants(String, String) descendants}. ATTENTION: this is
   * currently NOT supported.
   */
  char PATH_INTERSECTION = '&';

  /**
   * The character used for the start of a condition in the path for a
   * {@link #getDescendant(String, String) descendant}. For example the
   * descendant-path "node[@foor='bar']" selected the child
   * {@link Type#ELEMENT element} with the {@link #getName() name} "node" that
   * has a child {@link Type#ATTRIBUTE attribute} with the
   * {@link #getName() name} "foo" and the {@link #getValue() value} "bar".
   */
  char PATH_CONDITION_START = '[';

  /**
   * The character used for the end of a condition in the path for a
   * {@link #getDescendant(String, String) descendant}.
   */
  char PATH_CONDITION_END = ']';

  /**
   * The character used for the start of a value in a compare condition.
   */
  char PATH_VALUE_START = '\'';

  /**
   * The character used for the end of a value in a compare condition.
   */
  char PATH_VALUE_END = '\'';

  /**
   * This method gets the name of this configuration. If this configuration has
   * the {@link #getType() type} {@link Type#ATTRIBUTE attribute}, the name
   * will start with {@link #NAME_PREFIX_ATTRIBUTE}.
   * 
   * @return the name of this configuration.
   */
  String getName();

  /**
   * This method gets the namespace of this configuration.
   * 
   * @see org.w3c.dom.Node#getNamespaceURI()
   * 
   * @return the URI uniquely identifying the namespace of this configuration or
   *         {@link #NAMESPACE_URI_NONE} if this configuration has NO namespace.
   */
  String getNamespaceUri();

  /**
   * This method gets the absolute path of this configuration in the
   * configuration tree. The path is build by appending the
   * {@link #PATH_SEPARATOR} and the {@link #getName() name} of this
   * configuration to the {@link #getPath() path} of the parent configuration or
   * the empty string if this is the root of the configuration. This method is
   * intended to be used for debugging.
   * 
   * @return the path of this node in the configuration tree.
   */
  String getPath();

  /**
   * This method gets the relative path from the given <code>ancestor</code>
   * to <code>this</code> configuration, that must be a
   * {@link #isDescendantOf(Configuration) descendant} of the
   * <code>ancestor</code>.<br>
   * In other words the following equation is satisfied (if <code>path</code>
   * is legal):
   * 
   * <pre>
   * ancestor.{@link #getDescendant(String) getDescendant}(path).{@link #getRelativePath(Configuration) getRelativePath}(ancestor) == path
   * </pre>
   * 
   * If the given <code>ancestor</code> is the root-node, this method returns
   * the {@link #getPath() "absolute path"} without the leading
   * {@link #PATH_SEPARATOR "/"}.
   * 
   * @param ancestor
   *        is the {@link Configuration configuration} where the relative path
   *        should start.
   * @return the path relative from <code>ancestor</code> to <code>this</code>
   *         configuration.
   * @throws ConfigurationException
   *         if <code>this</code> configuration is NOT a
   *         {@link #isDescendantOf(Configuration) descendant} of the given
   *         <code>ancestor</code>.
   */
  String getRelativePath(Configuration ancestor) throws ConfigurationException;

  /**
   * This method determines if this configuration is automatically adding
   * defaults. This means that if a {@link #getValue() value} is requested with
   * a {@link GenericValue#getString(String) default} supplied but the value is
   * currently {@link GenericValue#isEmpty() empty}, the default will
   * automatically assigned as value. Additionally if a
   * {@link #getDescendant(String, String) descendant} is requested that does
   * not yet exist, the created {@link Configuration configuration}(s) will
   * automatically assigned as child.<br>
   * By default this flag is <code>true</code>. It is inherited by the
   * {@link #getValue() value} and {@link #getDescendant(String) descendants}
   * but can be {@link ConfigurationDocument#NAME_ADD_DEFAULTS overridden}.
   * 
   * @return <code>true</code> if this configuration is automatically adding
   *         defaults, <code>false</code> otherwise.
   */
  boolean isAddDefaults();

  /**
   * This method gets the type of this configuration.
   * 
   * @return the type of this configuration.
   */
  Type getType();

  /**
   * This method gets the value of this configuration.
   * 
   * @return the value of this configuration.
   */
  GenericValue getValue();

  /**
   * This method gets the {@link #getDescendant(String, String) descendant} for
   * the given <code>path</code> in the same
   * {@link #getNamespaceUri() namespace} as this configuration. This is a
   * shortcut for
   * 
   * <pre>
   * {@link #getDescendant(String, String) getDescendant}(path, {@link #getNamespaceUri()})
   * </pre>
   * 
   * See {@link #getDescendant(String, String)} for more details.
   * 
   * @param path
   *        is the relative {@link Configuration#getPath() path} from this
   *        configuration to the requested descendant.
   * @return the descendant for the given path.
   */
  Configuration getDescendant(String path);

  /**
   * This method gets a descendant of this configuration with the given
   * <code>path</code> and <code>namespaceUri</code>.<br>
   * A descendant of this configuration is a direct child ({@link Type#ATTRIBUTE attribute}
   * or {@link Type#ELEMENT element}) or a descendant of a child. Since an
   * {@link Type#ATTRIBUTE attribute} can NOT have children, this method only
   * makes sense for configurations of the {@link #getType() type}
   * {@link Type#ELEMENT element}.<br>
   * The given <code>path</code> identifies the requested descendant relative
   * to this configuration in a way similar to XPath.
   * <ul>
   * <li><code>element.{@link #getDescendant(String) getDescendant}({@link #PATH_THIS "."})</code>
   * is the same as <code>element</code>.</li>
   * <li><code>element.{@link #getDescendant(String, String) getDescendant}("child", ns)</code>
   * gets the first child with the {@link #getType() type}
   * {@link Type#ELEMENT element} that has the {@link #getName() name}
   * <code>child</code> and {@link #getNamespaceUri() namespace}
   * <code>ns</code>.</li>
   * <li><code>element.{@link #getDescendant(String) getDescendant}("@attribute", ns)</code>
   * gets the child with the {@link #getType() type}
   * {@link Type#ATTRIBUTE attribute} that has the {@link #getName() name}
   * <code>&#64;attribute</code> and {@link #getNamespaceUri() namespace}
   * <code>ns</code>.</li>
   * <li><code>element.{@link #getDescendant(String, String) getDescendant}("child[&lt;condition&gt;]", ns)</code>
   * gets the (first) child that matches <code>&lt;condition&gt;</code> that
   * has the {@link #getName() name} <code>child</code>,
   * {@link #getNamespaceUri() namespace} <code>ns</code> and has an
   * {@link Type#ATTRIBUTE attribute} named <code>&#64;attribute</code> that
   * has the value <code>value</code>. The <code>&lt;condition&gt;</code>
   * is eigther a positive integer identifying the number of the child if there
   * are multiple (only makes sense for {@link Type#ELEMENT elements}) or again
   * a <code>path</code> (without conditions) that may be followed by
   * <code>='&lt;value&gt;'</code>. In the latter case, the <code>path</code>
   * needs to exist and its {@link #getValue() value} has to be equal to the
   * <code>&lt;value&gt;</code> if present.</li>
   * <li><code>element.{@link #getDescendant(String, String) getDescendant}(name+"{@link #PATH_SEPARATOR /}"+path, ns)</code>
   * is the same as
   * <code>element.{@link #getDescendant(String, String) getDescendant}(name, ns).{@link #getDescendant(String) getDescendant}(path)</code>.</li>
   * </ul>
   * Examples for <code>path</code> expressions:
   * <ul>
   * <li><code>foo/bar[2]</code></li>
   * <li><code>foo[bar/@attribute='value']</code></li>
   * </ul>
   * Examples for expressions that are NOT accepted by this method:
   * <ul>
   * <li><code>foo/bar[count()=2]</code></li>
   * <li><code>foo[bar/@attribute!='value']</code></li>
   * <li><code>fo?</code></li>
   * <li><code>*</code></li>
   * <li><code>./.</code></li>
   * <li><code>foo/</code></li>
   * <li><code>foo//bar</code></li>
   * </ul>
   * 
   * <br>
   * <b>ATTENTION:</b><br>
   * The <code>path</code> must follow the specification above. It can NOT be
   * any XPath expression.<br>
   * <b>WARNING:</b><br>
   * This method will always return a configuration. If this method is applied
   * to an {@link Type#ELEMENT element} where the given <code>path</code> does
   * NOT point to an existing configuration, the according configurations will
   * be created similar to an {@link java.io.File#mkdirs() mkdirs} operation. Be
   * careful when using index-conditions (e.g. <code>foo[10]</code>) because
   * they can create many nodes.<br>
   * 
   * @see #getDescendants(String, String)
   * 
   * @param path
   *        is the relative {@link Configuration#getPath() path} from this
   *        configuration to the requested descendant.
   * @param namespaceUri
   *        is the {@link #getNamespaceUri() namespace} used to resolve the
   *        descendant. May be <code>null</code> to match any namespace. Then,
   *        if the requested descendant does NOT exists anyways, the created
   *        descendant(s) will inherit the {@link #getNamespaceUri() namespace}
   *        from the existing parent. To resolve a descendant with multiple
   *        {@link #getNamespaceUri() namespaces} on its <code>path</code>
   *        explicitly you have to use multiple calls of this method. If the
   *        requested descendant is an {@link Type#ATTRIBUTE attribute} and the
   *        given <code>namespaceUri</code> equals to the
   *        {@link #getNamespaceUri() namespace} of <code>this</code>
   *        configuration, but no {@link Type#ATTRIBUTE attribute} as requested
   *        exists, the {@link #getNamespaceUri() namespace} defaults to
   *        {@link #NAMESPACE_URI_NONE NO namespace}. If no such attribute
   *        exists either, it will be created with
   *        {@link #NAMESPACE_URI_NONE NO namespace}.
   * @return the descendant for the given path.
   */
  Configuration getDescendant(String path, String namespaceUri);

  /**
   * This method gets the {@link #getDescendants(String, String) descendants}
   * for the same {@link #getNamespaceUri() namespace} as this configuration.<br>
   * See {@link #getDescendants(String, String)} for more details.
   * 
   * @param path
   *        is the relative {@link Configuration#getPath() path} from this
   *        configuration to the requested descendants.
   * @return all descendants for the given path.
   */
  Collection<? extends Configuration> getDescendants(String path);

  /**
   * This method gets all {@link #getDescendant(String, String) descendants}
   * that exist for the given path. While {@link #getDescendant(String, String)}
   * will create missing configurations, this method will NOT modify
   * configurations in any way. It only returns what is already there.<br>
   * In addition to {@link #getDescendant(String, String)} you can use
   * glob-patterns in segments/condition-values and use other conditions
   * operators ('<', '>', '<=', '>=', '!=').<br>
   * Further you combine multiple paths with '|' and '&' in the
   * <code>path</code> argument of this method with the following meaning:
   * <ul>
   * <li><code>path1|path2</code> returns the union set that contains all
   * matches of <code>path1</code> and <code>path2</code>.</li>
   * <li><code>path1&path2</code> returns the intersection set that contains
   * only the matches found by <code>path1</code> and <code>path2</code>.</li>
   * </ul>
   * Examples:<br>
   * <ul>
   * <li><code>*[@*='foo*']</code> leads to all elements that have an
   * attribute whose value starts with <code>foo</code>.</li>
   * <li><code>foo[@bar>5]</code> leads to all elements named
   * <code>foo</code> that have an attribute <code>bar</code> whose value is
   * a number greater than <code>5</code>.</li>
   * </ul>
   * <b>IMPORTANT:</b><br>
   * When using glob-patterns the first character has to be
   * <code>{@link #NAME_PREFIX_ATTRIBUTE '@'}</code> to match
   * {@link Type#ATTRIBUTE attributes}. To match all direct children you need
   * to use the path <code>"*|@*"</code>.<br>
   * 
   * The ordering of the returned configurations in general is unspecified. Only
   * {@link Type#ELEMENT elements} with the same {@link #getName() name} should
   * be iterated in the order of their occurrence in the underlying
   * configuration format (e.g. XML-document) and according to the order of the
   * matching expression in the given <code>path</code>.<br>
   * <b>ATTENTION:</b><br>
   * The <code>path</code> must follow the specification above. It can NOT be
   * any XPath expression. Especially there is no support for <code>//</code>
   * or functions (such as <code>count()</code>).
   * 
   * @param path
   *        is the relative {@link Configuration#getPath() path} from this
   *        configuration to the requested descendants.
   * @param namespaceUri
   *        is the {@link #getNamespaceUri() namespace} used to resolve the
   *        descendants. May be <code>null</code> to match any namespace. To
   *        resolve descendants explicitly with multiple
   *        {@link #getNamespaceUri() namespaces} on their <code>path</code>
   *        you have to use multiple calls of this method.
   * @return all descendants for the given path.
   */
  Collection<? extends Configuration> getDescendants(String path, String namespaceUri);

  /**
   * This method determines if this configuration is a
   * {@link #getDescendant(String, String) descendant} of the given
   * {@link Configuration configuration}. In other words it is checked if the
   * given <code>ancestor</code> is a super-node of this node, meaning that it
   * is in the list of parent-nodes up to the root-node.<br>
   * This is the same as:
   * 
   * <pre>
   * {@link #getAncestorDistance(Configuration) getDescendantDistance}(ancestor) > 0
   * </pre>
   * 
   * @see #getAncestorDistance(Configuration)
   * 
   * @param ancestor
   *        is the potential super-node of this node.
   * @return <code>true</code> if the given <code>ancestor</code> is a
   *         super-node of this node, <code>false</code> otherwise.
   */
  boolean isDescendantOf(Configuration ancestor);

  /**
   * This method determines the distance of this node to the given
   * <code>ancestor</code>.<br>
   * Examples:
   * <ul>
   * <li><code>config.{@link #getAncestorDistance(Configuration) getAncestorDistance}(config)</code>
   * is <code>0</code>.</li>
   * <li><code>config.{@link #getDescendant(String) getDescendant}("foo/bar").{@link #getAncestorDistance(Configuration) getAncestorDistance}(config)</code>
   * is <code>2</code>.</li>
   * </ul>
   * 
   * @param ancestor
   *        is the node potential super-node of this node.
   * @return
   *        <ul>
   *        <li><code>0</code> if the node given by <code>ancestor</code>
   *        is the same as this node.</li>
   *        <li>a positive integer <code>N</code> if this node is the
   *        <code>N</code>.th child of the given <code>ancestor</code>.</li>
   *        <li><code>-1</code> if this node is no
   *        {@link #getDescendant(String, String) descendant} of the node given
   *        by <code>ancestor</code>.</li>
   *        </ul>
   */
  int getAncestorDistance(Configuration ancestor);

}
