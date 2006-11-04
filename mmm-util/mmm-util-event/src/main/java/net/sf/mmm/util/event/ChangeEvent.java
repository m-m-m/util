/* $Id$ */
package net.sf.mmm.util.event;

/**
 * A {@link ChangeEvent} is an {@link Event} that informs about something that
 * has been {@link Type#ADD added}, {@link Type#UPDATE updated} or
 * {@link Type#REMOVE removed}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface ChangeEvent extends Event {

  /**
   * This enum contains the available {@link #getType() types} of a change event
   */
  enum Type {
    /**
     * A {@link ChangeEvent change-event} of this type indicates, that something
     * new has been added. E.g. one or multiple item(s) have been inserted into
     * a list.
     */
    ADD,

    /**
     * A {@link ChangeEvent change-event} of this type indicates, that something
     * that already exists has been updated. E.g. an existing list item has
     * changed its value or been replaced by a new item.
     */
    UPDATE,

    /**
     * A {@link ChangeEvent change-event} of this type indicates, that something
     * that has been removed. E.g. one or mutliple existing item(s) have been
     * removed from a list.
     */
    REMOVE
  }

  /**
   * This method gets the type of this event.
   * 
   * @return the type of the change.
   */
  Type getType();

}
