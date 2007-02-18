/* $Id$ */
package net.sf.mmm.configuration.base.path.condition;

import net.sf.mmm.configuration.api.ConfigurationException;
import net.sf.mmm.configuration.base.AbstractConfiguration;

/**
 * This is the interface for a condition that
 * {@link #accept(AbstractConfiguration, String) filters}
 * {@link net.sf.mmm.configuration.api.Configuration configurations}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface Condition {

  /** a condition that always accepts */
  Condition EMPTY_CONDITION = new NoCondition();

  /**
   * This method determines if the given <code>configuration</code> is
   * accepted for this condition.
   * 
   * @param configuration
   *        is the configuration to check.
   * @param namespaceUri
   *        is the namespace-URI to use or <code>null</code> if namespaces
   *        should be ignored.
   * @return <code>true</code> if the given <code>configuration</code> is
   *         acceptable, <code>false</code> otherwise.
   */
  boolean accept(AbstractConfiguration configuration, String namespaceUri);

  /**
   * This method ensures that this condition is
   * {@link #accept(AbstractConfiguration, String) fulfilled} for the given
   * <code>configuration</code>. To do so, the given
   * <code>configuration</code> is modified accordingly. Please note, that
   * this is NOT always possible.
   * 
   * @param configuration
   *        is the configuration that should
   *        {@link #accept(AbstractConfiguration, String) fulfill} this condition.
   * @param namespaceUri
   *        is the namespace-URI to use or <code>null</code> if namespaces
   *        should be ignored.
   * @return the configuration that
   *         {@link #accept(AbstractConfiguration, String) fulfill} this condition. This
   *         will typically be the given <code>configuration</code> but also
   *         may be a created sibling.
   * @throws ConfigurationException
   *         if this conditions can NOT be established in general or specificly
   *         for the given <code>configuration</code>.
   */
  AbstractConfiguration establish(AbstractConfiguration configuration, String namespaceUri)
      throws ConfigurationException;

}
