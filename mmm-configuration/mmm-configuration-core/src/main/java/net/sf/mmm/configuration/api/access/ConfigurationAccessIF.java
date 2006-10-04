/* $ Id: $ */
package net.sf.mmm.configuration.api.access;

import java.io.InputStream;
import java.io.OutputStream;

import net.sf.mmm.configuration.api.ConfigurationIF;
import net.sf.mmm.configuration.api.ConfigurationException;
import net.sf.mmm.configuration.api.MutableConfigurationIF;

/**
 * This is the interface for a class that gives access to a
 * {@link net.sf.mmm.configuration.api.ConfigurationDocumentIF configuration-document}.
 * The typical implementation accesses the configuration via the local
 * filesystem. Various other targets e.g. a database, etc. are possible.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface ConfigurationAccessIF {

  /**
   * This method gets the context prefix this access was
   * {@link ConfigurationAccessFactoryIF#configure(String, net.sf.mmm.context.api.ContextIF, ConfigurationIF) configured}
   * with.
   * 
   * @see ConfigurationAccessFactoryIF#CONTEXT_VARIABLE_PREFIX
   * 
   * @return the context prefix.
   */
  String getContextPrefix();

  /**
   * This method gets the normalized and unique URI of the resource to
   * read/write via this accessor.<br>
   * E.g. for a file this would be the
   * {@link java.io.File#getCanonicalPath() "canonical path"}.
   * 
   * @return the normalized resource URI.
   */
  String getUniqueUri();

  /**
   * This method gets the name of the resource to read/write via this accessor.<br>
   * E.g. "myplugin.xml" or "server.properties".
   * 
   * @return a name describing this access.
   */
  String getName();

  /**
   * This method gets read access to the configuration data.
   * 
   * @return an input stream to read the configuration.
   * @throws ConfigurationException
   *         on access error (e.g. I/O problems).
   */
  InputStream getReadAccess() throws ConfigurationException;

  /**
   * This method gets write access to the configuration data. It is only
   * applicable if this accessor is NOT {@link #isReadOnly() read-only}.
   * 
   * @return an output stream to write the configuration.
   * @throws ConfigurationException
   *         if this accessor is {@link #isReadOnly() read-only} or on access
   *         error (e.g. I/O problems) occured.
   */
  OutputStream getWriteAccess() throws ConfigurationException;

  /**
   * This method determines if this accessor is read-only so only
   * {@link #getReadAccess() read-access} is available but no
   * {@link #getWriteAccess() write-access}. In that case the
   * {@link net.sf.mmm.configuration.api.ConfigurationIF configurations} read
   * via this accessor will be have the flags
   * {@link ConfigurationIF#isAddDefaults()} and
   * {@link MutableConfigurationIF#isEditable()} set to <code>false</code>.
   * 
   * @return <code>true</code> if this accessor is read-only,
   *         <code>false</code> otherwise.
   */
  boolean isReadOnly();

}
