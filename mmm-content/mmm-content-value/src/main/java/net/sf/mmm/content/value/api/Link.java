/* $Id$ */
package net.sf.mmm.content.value.api;

/**
 * This is the interface for a link pointing from one content-object to another.
 * Therefore it contains the {@link #getTargetId() id} of the linked
 * content-object. A link is an immutable object - it can not be modified.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface Link {

  /**
   * The {@link net.sf.mmm.value.api.ValueManager#getName() name} of this value
   * type.
   */
  String VALUE_NAME = "Link";

  /**
   * This method gets the unqiue ID of the content-object that is linked.
   * 
   * @return the ID of the linked content-object.
   */
  IdIF getTargetId();

  /**
   * This method gets the classification of the link.
   * 
   * @return the link classifier.
   */
  String getClassifier();

  /**
   * This method determines if this link is weak. If a link is weak, the linked
   * target content-object can be deleted and also the link will be removed
   * (cascade on delete). A strong link (link that is NOT weak) will NOT allow
   * the linked content-object to be deleted. All strong links pointing on a
   * content-object have to be removed before it can be deleted.<br>
   * Typically links are strong.
   * 
   * @return <code>true</code> if this is a weak link.
   */
  boolean isWeak();

}
