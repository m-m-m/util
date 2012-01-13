/* $Id: $
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.contenttype.base;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlRootElement;

import net.sf.mmm.util.collection.base.AbstractTreeNode;
import net.sf.mmm.util.contenttype.api.ContentType;
import net.sf.mmm.util.contenttype.base.format.SegmentContainerSequence;
import net.sf.mmm.util.nls.api.NlsNullPointerException;

/**
 * This is the abstract base implementation of the {@link ContentType}
 * interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@XmlRootElement(name = "content-type")
@XmlAccessorType(XmlAccessType.NONE)
public class ContentTypeBean extends AbstractTreeNode<ContentType> implements ContentType {

  /** @see #getId() */
  @XmlID
  @XmlAttribute(name = "id")
  private String id;

  /** @see #addExtension(String) */
  @XmlElementWrapper(name = "extensions")
  @XmlElement(name = "extension")
  private final Set<String> mutableExtensions;

  /** @see #getExtensions() */
  private final transient Collection<String> extensions;

  /** @see #getTechnicalParent() */
  @XmlIDREF
  private ContentTypeBean technicalParent;

  /** @see #getDefaultExtension() */
  private String defaultExtension;

  /** @see #getTitle() */
  @XmlAttribute(name = "title")
  private String title;

  /** @see #getMimetype() */
  @XmlAttribute(name = "mimetype")
  private String mimetype;

  /** @see #isAbstract() */
  @XmlAttribute(name = "abstract")
  private boolean isAbstract;

  /** @see #getFormat() */
  @XmlElement(name = "format")
  private SegmentContainerSequence format;

  /**
   * The constructor.
   */
  public ContentTypeBean() {

    this(null);
  }

  /**
   * The constructor.
   * 
   * @param parent is the {@link #getParent() parent node}.
   */
  public ContentTypeBean(ContentType parent) {

    super(parent);
    this.mutableExtensions = new HashSet<String>();
    this.extensions = Collections.unmodifiableCollection(this.mutableExtensions);
  }

  /**
   * {@inheritDoc}
   */
  public ContentTypeBean getTechnicalParent() {

    return this.technicalParent;
  }

  /**
   * This method sets the {@link #getTechnicalParent() technical parent} of this
   * {@link ContentType}.
   * 
   * @param technicalParent is the {@link #getTechnicalParent() technical
   *        parent} to set.
   */
  protected void setTechnicalParent(ContentTypeBean technicalParent) {

    this.technicalParent = technicalParent;
  }

  /**
   * {@inheritDoc}
   */
  public boolean isTechnicalAncestor(ContentType technicalType) {

    if (technicalType == null) {
      throw new NlsNullPointerException("node");
    }
    ContentType ancestor = technicalType.getTechnicalParent();
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
  public void setId(String id) {

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
   * @param abstractFlag is the new {@link #isAbstract() abstract} flag.
   */
  protected void setAbstract(boolean abstractFlag) {

    this.isAbstract = abstractFlag;
  }

  /**
   * This method gets the {@link SegmentContainerSequence} representing the actual
   * format of the {@link ContentType}.
   * 
   * @return the format.
   */
  public SegmentContainerSequence getFormat() {

    return this.format;
  }

  /**
   * @param format is the {@link #getFormat() format} to set.
   */
  protected void setFormat(SegmentContainerSequence format) {

    this.format = format;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @XmlIDREF
  @XmlAttribute(name = "parent")
  public ContentTypeBean getParent() {

    return (ContentTypeBean) super.getParent();
  }

  /**
   * This method is required for JAXB. As there is no way than the return type
   * to specify the type of a {@link XmlIDREF} of a getter, we also need a
   * specialized setter with that type to satisfy JAXB.
   * 
   * @see #setParent(ContentType)
   * 
   * @param parent is the new {@link #getParent() parent}.
   */
  protected void setParent(ContentTypeBean parent) {

    super.setParent(parent);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void addChild(ContentType child) {

    // make visible
    super.addChild(child);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    return super.toString();
  }

}
