/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.impl.link;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.inject.Named;

import net.sf.mmm.data.base.link.AbstractLinkManager;
import net.sf.mmm.util.io.api.IoMode;
import net.sf.mmm.util.io.api.RuntimeIoException;
import net.sf.mmm.util.nls.api.DuplicateObjectException;
import net.sf.mmm.util.resource.base.ClasspathResource;

/**
 * This is the implementation of {@link net.sf.mmm.data.api.link.LinkManager}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@Named
public class LinkManagerImpl extends AbstractLinkManager {

  /** @see #createClassifierMap() */
  private static final String CLASSPATH_PROPERTIES_LINK_CLASSIFIERS = "net/sf/mmm/data/link/classifier.properties";

  /** @see #getInverseClassifierStrict(String) */
  private Map<String, String> classifierMap;

  /**
   * The constructor.
   */
  public LinkManagerImpl() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doInitialize() {

    super.doInitialize();
    if (this.classifierMap == null) {
      this.classifierMap = createClassifierMap();
    }
  }

  /**
   * This method creates the default {@link Map} for {@link #getInverseClassifierStrict(String)}.
   * 
   * @return the {@link Map} for {@link #getInverseClassifierStrict(String)}.
   */
  protected Map<String, String> createClassifierMap() {

    Properties properties = new Properties();
    InputStream inStream = new ClasspathResource(CLASSPATH_PROPERTIES_LINK_CLASSIFIERS).openStream();
    try {
      properties.load(inStream);
    } catch (IOException e) {
      throw new RuntimeIoException(e, IoMode.READ);
    } finally {
      try {
        inStream.close();
      } catch (IOException e) {
        getLogger().error("Exception while closing stream:", e);
      }
    }
    Map<String, String> result = new HashMap<String, String>();
    for (Object keyObject : properties.keySet()) {
      String key = (String) keyObject;
      String value = properties.getProperty(key);
      DuplicateObjectException.put(result, key, value);
      if (!key.equals(value)) {
        DuplicateObjectException.put(result, value, key);
      }
    }
    return result;
  }

  /**
   * @return the classifierMap
   */
  protected Map<String, String> getClassifierMap() {

    return this.classifierMap;
  }

  /**
   * @param classifierMap is the classifierMap to set
   */
  public void setClassifierMap(Map<String, String> classifierMap) {

    getInitializationState().requireNotInitilized();
    this.classifierMap = classifierMap;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getInverseClassifierStrict(String classifier) {

    return this.classifierMap.get(classifier);
  }

}
