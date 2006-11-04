/* $ Id: $ */
package net.sf.mmm.configuration.base.access;

import net.sf.mmm.configuration.api.ConfigurationDocumentIF;

/**
 * This is the interface of a container that collects
 * {@link net.sf.mmm.configuration.api.ConfigurationDocumentIF configuration-documents}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface ConfigurationDocumentCollector {

  /**
   * This method adds a document to this container.
   * 
   * @param document
   *        is the document to add.
   */
  void addDocument(ConfigurationDocumentIF document);

}
