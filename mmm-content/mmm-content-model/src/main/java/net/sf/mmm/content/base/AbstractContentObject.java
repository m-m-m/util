/* $Id$ */
package net.sf.mmm.content.base;

import net.sf.mmm.content.api.ContentException;
import net.sf.mmm.content.api.ContentObjectIF;
import net.sf.mmm.content.security.PermissionDeniedException;
import net.sf.mmm.util.xml.XmlException;
import net.sf.mmm.util.xml.api.XmlWriter;

/**
 * This is the abstract base implementation of the {@link ContentObjectIF}
 * interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractContentObject implements ContentObjectIF {

  /** @see #getName() */
  private final String name;

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
   * @see net.sf.mmm.content.api.ContentObjectIF#getName()
   */
  public String getName() {

    return this.name;
  }

  /**
   * @see net.sf.mmm.content.api.ContentObjectIF#getPath()
   */
  /*
   * public String getPath() {
   * 
   * if (getParent() == null) { // root folder return
   * ContentObjectIF.PATH_SEPARATOR; } else { String parentPath =
   * getParent().getPath(); StringBuffer path = new
   * StringBuffer(parentPath.length() + this.name.length() + 1);
   * path.append(parentPath); // only add separator if parent is NOT the root
   * folder if (parentPath.length() > 1) {
   * path.append(ContentObjectIF.PATH_SEPARATOR); } path.append(this.name);
   * return path.toString(); } }
   */

  /**
   * This method gets the deleted flag of this object. This method gives direct
   * access to the deleted flag and can be used if the method
   * {@link AbstractContentObject#isDeleted()}has been overriden.
   * 
   * @see ContentObjectIF#isDeleted()
   * 
   * @return the deleted flag of this object.
   */
  protected final boolean getDeletedFlag() {

    return this.deletedFlag;
  }

  /**
   * @see net.sf.mmm.content.api.ContentObjectIF#isDeleted()
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
   * @see net.sf.mmm.content.api.ContentObjectIF#setFieldValue(java.lang.String,
   *      java.lang.Object)
   */
  public void setFieldValue(String fieldName, Object value) throws NoSuchFieldException,
      PermissionDeniedException, ContentException {

  // TODO Auto-generated method stub

  }

  /**
   * @see net.sf.mmm.content.api.ContentObjectIF#getFieldValue(java.lang.String)
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
      return getId().equals(((ContentObjectIF) other).getId());
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
