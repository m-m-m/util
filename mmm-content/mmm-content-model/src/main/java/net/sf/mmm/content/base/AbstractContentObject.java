/* $Id$ */
package net.sf.mmm.content.base;

import net.sf.mmm.content.api.ContentException;
import net.sf.mmm.content.api.ContentObject;
import net.sf.mmm.content.security.PermissionDeniedException;
import net.sf.mmm.util.xml.XmlException;
import net.sf.mmm.util.xml.api.XmlWriter;

/**
 * This is the abstract base implementation of the {@link ContentObject}
 * interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractContentObject implements ContentObject {

  /** @see #getName() */
  private String name;

  /** the deleted-flag */
  private boolean deletedFlag;

  /**
   * The constructor.
   * 
   * @param objectName
   *        is the {@link #getName() name} of the object to create.
   */
  public AbstractContentObject(String objectName) {

    super();
    this.name = objectName;
    this.deletedFlag = false;
  }

  /**
   * @see net.sf.mmm.content.api.ContentObject#getName()
   */
  public String getName() {

    return this.name;
  }

  /**
   * This method gets the deleted flag of this object. This method gives direct
   * access to the deleted flag and can be used if the method
   * {@link AbstractContentObject#isDeleted()}has been overriden.
   * 
   * @see ContentObject#isDeleted()
   * 
   * @return the deleted flag of this object.
   */
  protected final boolean getDeletedFlag() {

    return this.deletedFlag;
  }

  /**
   * @see net.sf.mmm.content.api.ContentObject#isDeleted()
   */
  public boolean isDeleted() {

    return this.deletedFlag;
  }

  /**
   * This method sets the deleted flag to the given value.
   * 
   * @param deleted
   *        is the new value of the deleted flag.
   */
  protected void setDeleted(boolean deleted) {

    this.deletedFlag = deleted;
  }

  /**
   * @see net.sf.mmm.content.api.ContentObject#setFieldValue(java.lang.String,
   *      java.lang.Object)
   */
  public void setFieldValue(String fieldName, Object value) throws NoSuchFieldException,
      PermissionDeniedException, ContentException {

  // TODO Auto-generated method stub

  }

  /**
   * @see net.sf.mmm.content.api.ContentObject#getFieldValue(java.lang.String)
   */
  public Object getFieldValue(String fieldName) throws NoSuchFieldException,
      PermissionDeniedException, ContentException {

    // TODO Auto-generated method stub
    return null;
  }

  /**
   * @see net.sf.mmm.util.xml.api.XmlSerializable#toXml(net.sf.mmm.util.xml.api.XmlWriter)
   */
  public void toXml(XmlWriter xmlWriter) throws XmlException {

  // TODO Auto-generated method stub

  }

  /**
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {

    // TODO Auto-generated method stub
    return super.toString();
  }

  /**
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public final boolean equals(Object other) {

    if ((other != null) && (other instanceof AbstractContentObject)) {
      return getId().equals(((ContentObject) other).getId());
    }
    return false;
  }

  /**
   * @see java.lang.Object#hashCode()
   */
  @Override
  public final int hashCode() {

    return ~getId().hashCode();
  }

}
