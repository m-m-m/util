/* $ Id: $ */
package net.sf.mmm.content.value.api;

import java.util.Date;

import net.sf.mmm.content.api.security.ContentActionIF;
import net.sf.mmm.content.api.security.ContentUserIF;

/**
 * This is the interface for the history of a specific
 * {@link net.sf.mmm.content.api.resource.ContentResourceIF content-resource}
 * revision.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface RevisionHistoryIF {

  /**
   * The {@link net.sf.mmm.value.api.ValueManager#getName() name} of this
   * value type.
   */
  String VALUE_NAME = "History";

  /**
   * This method gets the date when this version/revision was created.
   * 
   * @return the creation date of this version (=
   *         {@link net.sf.mmm.content.api.resource.ContentResourceIF resource}
   *         revision). If this resource has the revision <code>0</code>
   *         (latest revision), this method gets the initial creation date of
   *         the resource.
   */
  Date getDateOfCreation();

  /**
   * This method gets the user that created this version/revision.
   * 
   * @return the creator of this version. If this resource has the revision
   *         <code>0</code> (latest revision), this method gets the creator
   *         of the resource.
   */
  ContentUserIF getSubjectOfCreation();

  /**
   * This method gets the date when the given action was performed on the
   * associated resource revision.
   * 
   * @param action
   *        is the action for that the date of performing is requested.
   * @return the date when the given action was performed on this revision or
   *         <code>null</code> if the actions was not (yet) performed on
   *         this revision.
   */
  Date getDateOfAction(ContentActionIF action);

  /**
   * This method gets the user that performed the given action on the
   * associated resource revision.
   * 
   * @param action
   *        is the action for that the performing user is requested.
   * @return the user that performed the given action on this revision or
   *         <code>null</code> if the actions was not (yet) performed on
   *         this revision.
   */
  ContentUserIF getSubjectOfAction(ContentActionIF action);

}
