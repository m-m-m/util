/* $Id$ */
package net.sf.mmm.content.model.api;

import java.util.Collection;

import net.sf.mmm.content.api.ContentObjectIF;
import net.sf.mmm.content.value.api.IdIF;

/**
 * This interface gives read access to the content model.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface ContentModelReadAccessIF {

  /**
   * This method gets the content class for the given name.
   * 
   * @param name
   *        is the name of the requested class.
   * @return the content class for the given name.
   * @throws ContentModelException
   *         if the requested class does not exist.
   */
  ContentClassIF getClass(String name) throws ContentModelException;

  /**
   * This method gets the content class for the given ID.
   * 
   * @param id
   *        is the ID of the requested class.
   * @return the content class for the given ID.
   * @throws ContentModelException
   *         if the requested class does not exist.
   */
  ContentClassIF getClass(IdIF id) throws ContentModelException;

  /**
   * This method gets the root content class that reflects the
   * {@link ContentObjectIF content-object}.
   * 
   * @return the root class.
   */
  ContentClassIF getRootClass();

  /**
   * This method gets the list of all registered content classes.
   * 
   * @return an enumeration of all content classes.
   */
  Collection<ContentClassIF> getClasses();

}
