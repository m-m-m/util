/* $Id: $ */
package net.sf.mmm.content.resource.base;

import net.sf.mmm.content.base.AbstractContentObject;
import net.sf.mmm.content.model.api.ContentClass;
import net.sf.mmm.content.resource.api.ContentFolder;
import net.sf.mmm.content.resource.api.ContentResource;
import net.sf.mmm.content.value.api.Id;
import net.sf.mmm.content.value.api.RevisionHistory;
import net.sf.mmm.content.value.api.Version;

/**
 * This is the abstract base implementation of the {@link ContentResource}
 * interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractContentResource extends AbstractContentObject implements
    ContentResource {

  /** @see #getParent() */
  private ContentFolder parent;

  /** @see #getContentClass() */
  private final ContentClass type;

  /**
   * The constructor.
   * 
   * @param objectId 
   * @param objectName
   * @param parentFolder
   * @param contentClass 
   */
  public AbstractContentResource(Id objectId, String objectName, ContentFolder parentFolder,
      ContentClass contentClass) {

    super(objectId, objectName);
    this.parent = parentFolder;
    this.type = contentClass;
  }

  /**
   * @see net.sf.mmm.content.resource.api.ContentResource#getParent()
   */
  public ContentFolder getParent() {

    return this.parent;
  }

  /**
   * @see net.sf.mmm.content.resource.api.ContentResource#getPath()
   */
  public String getPath() {

    if (getParent() == null) {
      // root folder
      return PATH_SEPARATOR;
    } else {
      String parentPath = getParent().getPath();
      String name = getName();
      StringBuffer path = new StringBuffer(parentPath.length() + name.length() + 1);
      path.append(parentPath);
      // only add separator if parent is NOT the root folder
      if (parentPath.length() > 1) {
        path.append(PATH_SEPARATOR);
      }
      path.append(name);
      return path.toString();
    }
  }

  /**
   * @see net.sf.mmm.content.resource.api.ContentResource#getRevisionHistory()
   */
  public RevisionHistory getRevisionHistory() {

    // TODO Auto-generated method stub
    return null;
  }

  /**
   * @see net.sf.mmm.content.resource.api.ContentResource#getVersion()
   */
  public Version getVersion() {

    // TODO Auto-generated method stub
    return null;
  }

  /**
   * @see net.sf.mmm.content.api.ContentObject#getContentClass()
   */
  public ContentClass getContentClass() {

    return this.type;
  }

}
