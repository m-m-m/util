/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.xml;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.xml.namespace.NamespaceContext;

import net.sf.mmm.util.collection.base.AbstractIterator;

/**
 * This is a public implementation of the {@link NamespaceContext} interface. It can store
 * {@link #setNamespace(String, String) own namespace declarations} and also inherit declarations from a parent
 * {@link NamespaceContext}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class NamespaceContextImpl implements NamespaceContext {

  /** the parent context to inherit from or {@code null}. */
  private final NamespaceContext parentContext;

  /** maps prefix to namespace-URI. */
  private final Map<String, String> prefix2namespace;

  /** maps namespace-URI to prefix. */
  private final Map<String, String> namespace2prefix;

  /**
   * The constructor.
   */
  public NamespaceContextImpl() {

    this(null);
  }

  /**
   * The constructor.
   *
   * @param parentContext is the parent context to inherit from.
   */
  public NamespaceContextImpl(NamespaceContext parentContext) {

    super();
    this.parentContext = parentContext;
    this.prefix2namespace = new HashMap<String, String>();
    this.namespace2prefix = new HashMap<String, String>();
  }

  @Override
  public String getNamespaceURI(String prefix) {

    String uri = this.prefix2namespace.get(prefix);
    if ((uri == null) && (this.parentContext != null)) {
      uri = this.parentContext.getNamespaceURI(prefix);
    }
    return uri;
  }

  @Override
  public String getPrefix(String namespaceURI) {

    String prefix = this.namespace2prefix.get(namespaceURI);
    if ((prefix == null) && (this.parentContext != null)) {
      prefix = this.parentContext.getPrefix(namespaceURI);
    }
    return prefix;
  }

  @Override
  public Iterator getPrefixes(String namespaceURI) {

    String prefix = this.namespace2prefix.get(namespaceURI);
    Iterator<String> parentIterator = null;
    if (this.parentContext != null) {
      parentIterator = this.parentContext.getPrefixes(namespaceURI);
    }
    return new PrefixIterator(parentIterator, prefix);
  }

  /**
   * This method is used to declare a namespace in this context.
   *
   * @param prefix is the prefix for the namespace.
   * @param uri is the URI of the namespace.
   */
  public void setNamespace(String prefix, String uri) {

    this.prefix2namespace.put(prefix, uri);
    this.namespace2prefix.put(uri, prefix);
  }

  /**
   * @see NamespaceContextImpl#getPrefixes(String)
   */
  protected static class PrefixIterator extends AbstractIterator<String> {

    private final Iterator<String> parentIterator;

    private final String prefix;

    /**
     * The constructor.
     *
     * @param parentIterator
     * @param prefix
     */
    public PrefixIterator(final Iterator<String> parentIterator, String prefix) {

      super();
      this.parentIterator = parentIterator;
      this.prefix = prefix;
      findFirst();
    }

    @Override
    protected String findNext() {

      if ((this.parentIterator != null) && (this.parentIterator.hasNext())) {
        return this.parentIterator.next();
      }
      return this.prefix;
    }

  }

}
