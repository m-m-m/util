/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.contenttype.base;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.sf.mmm.util.contenttype.api.ContentType;
import net.sf.mmm.util.exception.api.DuplicateObjectException;
import net.sf.mmm.util.xml.base.jaxb.JaxbObject;

/**
 * This is the container with all {@link ContentTypeBean}s. It allows to read
 * them from an XML configuration via JAXB.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@XmlRootElement(name = "content-types")
@XmlAccessorType(XmlAccessType.NONE)
public class ContentTypeList implements JaxbObject {

  /** @see #getContentTypes() */
  @XmlElement(name = "content-type")
  private List<ContentTypeBean> contentTypes;

  /** */
  private transient ContentTypeBean root;

  /**
   * The constructor.
   */
  public ContentTypeList() {

    super();
  }

  /**
   * @return the contentTypes
   */
  public List<ContentTypeBean> getContentTypes() {

    return this.contentTypes;
  }

  /**
   * @param contentTypes is the contentTypes to set
   */
  public void setContentTypes(List<ContentTypeBean> contentTypes) {

    this.contentTypes = contentTypes;
  }

  /**
   * {@inheritDoc}
   */
  public void afterUnmarshal(Unmarshaller unmarshaller, Object parent) {

    for (ContentTypeBean contentType : this.contentTypes) {
      ContentTypeBean parentType = contentType.getParent();
      if (parentType == null) {
        if (this.root != null) {
          throw new DuplicateObjectException(contentType, "[root]", this.root);
        }
        this.root = contentType;
      } else {
        parentType.addChild(contentType);
      }
    }
  }

  /**
   * This method gets the root node of the tree of {@link ContentTypeBean}s.
   * 
   * @return the root node.
   */
  public ContentTypeBean getRoot() {

    return this.root;
  }

  /**
   * @param root is the root to set
   */
  public void setRoot(ContentTypeBean root) {

    this.root = root;
    List<ContentTypeBean> collection = new ArrayList<ContentTypeBean>();
    collectNodes(root, collection);
    this.contentTypes = collection;
  }

  /**
   * This method walks down the tree of {@link ContentTypeBean}s recursively
   * adding them to the given {@link Collection}.
   * 
   * @param node is the current node to traverse.
   * @param collection is where to {@link Collection#add(Object) add} the
   *        {@link ContentTypeBean} objects.
   */
  private void collectNodes(ContentTypeBean node, Collection<ContentTypeBean> collection) {

    collection.add(node);
    for (ContentType child : node.getChildren()) {
      collectNodes((ContentTypeBean) child, collection);
    }
  }
}
