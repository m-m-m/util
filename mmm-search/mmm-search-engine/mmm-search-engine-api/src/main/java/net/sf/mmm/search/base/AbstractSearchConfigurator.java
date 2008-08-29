/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.base;

import org.w3c.dom.Element;

import net.sf.mmm.search.engine.api.ManagedSearchEngine;
import net.sf.mmm.util.xml.base.DomUtilImpl;

/**
 * This is an abstract base implementation of the {@link SearchConfigurator}
 * interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractSearchConfigurator implements SearchConfigurator {

  /**
   * The name of the XML element for a the configuration of the search-index.
   */
  public static final String XML_TAG_INDEX = "index";

  /**
   * The name of the XML element for a the configuration of the
   * {@link net.sf.mmm.search.engine.api.SearchEngine search-engine}.
   */
  public static final String XML_TAG_SEARCH = "search";

  /**
   * The name of the XML attribute for the <code>refresh-delay</code>. This
   * optional attribute specifies the time-period in seconds in which a
   * daemon-thread will
   * {@link net.sf.mmm.search.engine.api.ManagedSearchEngine#refresh() refresh}
   * the search-engine.
   */
  public static final String XML_ATR_SEARCH_REFRESHDELAY = "refresh-delay";

  /**
   * The constructor.
   */
  public AbstractSearchConfigurator() {

    super();
  }

  /**
   * This method checks for the XML attribute
   * {@link #XML_ATR_SEARCH_REFRESHDELAY refresh-delay} and if set it starts an
   * according refresh-thread for the given <code>searchEngine</code>.
   * 
   * @param element is the XML-element containing the configuration for the
   *        search-engine.
   * @param searchEngine is the search-engine that has already been created from
   *        the configuration.
   */
  protected void setupRefreshThread(Element element, final ManagedSearchEngine searchEngine) {

    Element searchElement = DomUtilImpl.getInstance().getFirstChildElement(element, XML_TAG_SEARCH);
    if (searchElement != null) {
      if (searchElement.hasAttribute(XML_ATR_SEARCH_REFRESHDELAY)) {
        String refreshDelayString = searchElement.getAttribute(XML_ATR_SEARCH_REFRESHDELAY);
        final long refreshDelay = Long.parseLong(refreshDelayString) * 1000L;
        Thread refreshThread = new Thread() {

          @Override
          public void run() {

            while (true) {
              try {
                Thread.sleep(refreshDelay);
              } catch (InterruptedException e) {
                // should never happen, however
              }
              searchEngine.refresh();
            }
          }
        };
        refreshThread.setName("Search-Engine-Refresh-Thread");
        refreshThread.setDaemon(true);
        refreshThread.start();
      }
    }

  }

}
