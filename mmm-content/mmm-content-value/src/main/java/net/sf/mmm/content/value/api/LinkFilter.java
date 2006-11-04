/* $ Id: $ */
package net.sf.mmm.content.value.api;

/**
 * This is the interface for a filter that only accepts specific links.
 * 
 * @see net.sf.mmm.content.value.api.LinkList
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface LinkFilter {

  /**
   * This method determines if the given link is acceptable or should be
   * filtered.
   * 
   * @param link
   *        is the link to check.
   * @return <code>true</code> if the given link will be accepted,
   *         <code>false</code> if the given link should be filtered.
   */
  boolean acceptLink(Link link);

}
