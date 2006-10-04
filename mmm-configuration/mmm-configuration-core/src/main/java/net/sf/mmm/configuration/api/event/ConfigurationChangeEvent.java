/* $Id$ */
package net.sf.mmm.configuration.api.event;

import net.sf.mmm.configuration.api.ConfigurationIF;
import net.sf.mmm.event.EventIF;

/**
 * This class represents an {@link net.sf.mmm.event.EventIF event} that notifies
 * a
 * {@link net.sf.mmm.configuration.api.event.ConfigurationChangeListenerIF listener}
 * about a {@link net.sf.mmm.configuration.api.ConfigurationIF configuration}
 * that has changed.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ConfigurationChangeEvent implements EventIF {

  /** is the configuration that changed */
  private final ConfigurationIF configuration;

  /**
   * The constructor.
   * 
   * @param conf
   *        is the configuration that has changed.
   */
  public ConfigurationChangeEvent(ConfigurationIF conf) {

    super();
    this.configuration = conf;
  }

  /**
   * This method gets the configuration that has changed (e.g. child was added
   * or removed).
   * 
   * @return the changed configuration.
   */
  public ConfigurationIF getConfiguration() {

    return this.configuration;
  }

}
