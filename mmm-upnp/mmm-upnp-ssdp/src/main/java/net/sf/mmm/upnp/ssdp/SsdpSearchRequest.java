/* $Id$ */
package net.sf.mmm.upnp.ssdp;

/**
 * This class represents an SSDP search request.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class SsdpSearchRequest extends SsdpRequest {

  /** proper man header */
  public static final String MAN_SSDP_DISCOVER = "\"ssdp:discover\"";

  /** st header */
  public static final String ST_SSDP_ALL = "ssdp:all";

  /**
   * The constructor
   */
  public SsdpSearchRequest() {

    super();
  }

  /**
   * This method sets the {@link #HEADER_PROPERTY_SEARCH_TARGET search-target}.
   * 
   * @param st
   *        is the search-target to set.
   */
  public void setSearchTarget(String st) {

    setHeaderProperty(HEADER_PROPERTY_SEARCH_TARGET, st);
  }

  /**
   * This method gets the {@link #HEADER_PROPERTY_SEARCH_TARGET search-target}.
   * 
   * @return the search-target or <code>null</code> if NOT set.
   */
  public String getSearchTarget() {

    return getHeaderProperty(HEADER_PROPERTY_SEARCH_TARGET);
  }

}
