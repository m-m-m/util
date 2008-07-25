/* $Id: $
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.contenttype.base;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.sf.mmm.util.collection.base.AbstractTreeNode;
import net.sf.mmm.util.contenttype.api.ContentType;
import net.sf.mmm.util.nls.base.NlsNullPointerException;

/**
 * This is the abstract base implementation of the {@link ContentType}
 * interface.
 * 
 * @param <NODE> is the generic type for self-references. Each sub-type of this
 *        class should specialize this type to itself.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractContentType<NODE extends AbstractContentType<NODE>> extends
    AbstractTreeNode<NODE> implements ContentType<NODE> {

  /** @see #addExtension(String) */
  private final Set<String> mutableExtensions;

  /** @see #getExtensions() */
  private final Collection<String> extensions;

  /** @see #getTechnicalParent() */
  private NODE technicalParent;

  /** @see #getProperty(String) */
  private Map<String, String> properties;

  /** @see #getDefaultExtension() */
  private String defaultExtension;

  /** @see #getId() */
  private String id;

  /** @see #getTitle() */
  private String title;

  /** @see #getMimetype() */
  private String mimetype;

  /** @see #isAbstract() */
  private boolean isAbstract;

  /**
   * The constructor.
   */
  public AbstractContentType() {

    this(null);
  }

  /**
   * The constructor.
   * 
   * @param parent is the {@link #getParent() parent node}.
   */
  public AbstractContentType(NODE parent) {

    super(parent);
    this.mutableExtensions = new HashSet<String>();
    this.extensions = Collections.unmodifiableCollection(this.mutableExtensions);
  }

  /**
   * {@inheritDoc}
   */
  public NODE getTechnicalParent() {

    return this.technicalParent;
  }

  /**
   * This method sets the {@link #getTechnicalParent() technical parent} of this
   * {@link ContentType}.
   * 
   * @param technicalParent is the {@link #getTechnicalParent() technical
   *        parent} to set.
   */
  protected void setTechnicalParent(NODE technicalParent) {

    this.technicalParent = technicalParent;
  }

  /**
   * {@inheritDoc}
   */
  public boolean isTechnicalAncestor(ContentType<?> technicalType) {

    if (technicalType == null) {
      throw new NlsNullPointerException("node");
    }
    ContentType<?> ancestor = technicalType.getTechnicalParent();
    while (ancestor != null) {
      if (ancestor == this) {
        return true;
      }
      ancestor = ancestor.getTechnicalParent();
    }
    return false;
  }

  /**
   * {@inheritDoc}
   */
  public String getDefaultExtension() {

    return this.defaultExtension;
  }

  /**
   * This method sets the {@link #getDefaultExtension() default extension}.<br>
   * <b>ATTENTION:</b><br>
   * You should also {@link #addExtension(String) add} the
   * <code>defaultExtension</code> to the {@link #getExtensions() extensions}.
   * 
   * @param defaultExtension is the defaultExtension to set.
   */
  protected void setDefaultExtension(String defaultExtension) {

    this.defaultExtension = defaultExtension;
  }

  /**
   * {@inheritDoc}
   */
  public Collection<String> getExtensions() {

    return this.extensions;
  }

  /**
   * This method adds the given <code>extension</code> to the
   * {@link #getExtensions() extensions}.
   * 
   * @param extension is the extension to add.
   */
  protected void addExtension(String extension) {

    this.mutableExtensions.add(extension);
  }

  /**
   * {@inheritDoc}
   */
  public String getId() {

    return this.id;
  }

  /**
   * @param id is the id to set
   */
  protected void setId(String id) {

    this.id = id;
  }

  /**
   * {@inheritDoc}
   */
  public String getMimetype() {

    return this.mimetype;
  }

  /**
   * @param mimetype is the mimetype to set
   */
  protected void setMimetype(String mimetype) {

    this.mimetype = mimetype;
  }

  /**
   * {@inheritDoc}
   */
  public String getProperty(String key) {

    String value = null;
    if (this.properties != null) {
      this.properties.get(key);
    }
    if (value == null) {
      // TODO: should all properties be inherited?
      NODE parent = getParent();
      if (parent != null) {
        value = parent.getProperty(key);
      }
    }
    return value;
  }

  /**
   * This method sets the {@link #getProperty(String) property} for the given
   * <code>key</code> to the given <code>value</code>.
   * 
   * @see Map#put(Object, Object)
   * 
   * @param key is the key for the property to set.
   * @param value is the value of the property to set.
   * @return the old value of the property for the given <code>key</code> that
   *         has been replaced by <code>value</code> or <code>null</code> if the
   *         property was NOT set before.
   */
  protected String setProperty(String key, String value) {

    if (this.properties == null) {
      this.properties = new HashMap<String, String>();
    }
    if (value == null) {
      return this.properties.remove(key);
    } else {
      return this.properties.put(key, value);
    }
  }

  /**
   * {@inheritDoc}
   */
  public String getTitle() {

    if (this.title == null) {
      return this.id;
    } else {
      return this.title;
    }
  }

  /**
   * {@inheritDoc}
   */
  public boolean isAbstract() {

    return this.isAbstract;
  }

  /**
   * This method sets the {@link #isAbstract() abstract} flag.
   * 
   * @param isAbstract is the new {@link #isAbstract() abstract} flag.
   */
  protected void setAbstract(boolean isAbstract) {

    this.isAbstract = isAbstract;
  }

}
