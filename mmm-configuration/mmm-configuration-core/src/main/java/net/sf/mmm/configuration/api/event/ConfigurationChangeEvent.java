/* $Id$ */
package net.sf.mmm.configuration.api.event;

import net.sf.mmm.configuration.api.Configuration;
import net.sf.mmm.util.event.Event;

/**
 * This class represents an {@link net.sf.mmm.util.event.Event event} that notifies
 * a
 * {@link net.sf.mmm.configuration.api.event.ConfigurationChangeListener listener}
 * about a {@link net.sf.mmm.configuration.api.Configuration configuration}
 * that has changed.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ConfigurationChangeEvent implements Event {

  /** is the configuration that changed */
  private final Configuration configuration;

  /**
   * The constructor.
   * 
   * @param conf
   *        is the configuration that has changed.
   */
  public ConfigurationChangeEvent(Configuration conf) {

    super();
    this.configuration = conf;
  }

  /**
   * This method gets the configuration that has changed (e.g. child was added
   * or removed).
   * 
   * @return the changed configuration.
   */
  public Configuration getConfiguration() {

    return this.configuration;
  }

}
