/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.model.impl;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import net.sf.mmm.configuration.api.Configuration;
import net.sf.mmm.content.model.api.ContentClass;
import net.sf.mmm.content.model.api.MutableContentModelService;
import net.sf.mmm.content.value.impl.IdImpl;

/**
 * This is an implementation of the {@link MutableContentModelService} that
 * persists the content-model in an XML file.<br>
 * <b>ATTENTION:</b><br/> This implementation is only
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ConfiguredModelService extends AbstractMutableContentModelService {

  /** @see #createClassId() */
  private int nextClassId;

  /** @see #createFieldId() */
  private int nextFieldId;

  /** @see #setConfiguration(Configuration) */
  private Configuration config;

  /**
   * The constructor
   */
  public ConfiguredModelService() {

    super();
    this.nextClassId = IdImpl.MINIMUM_CUSTOM_CLASS_ID;
    this.nextFieldId = IdImpl.MINIMUM_CUSTOM_FIELD_ID;
    this.config = null;
  }

  /**
   * This method injects the configuration.
   * 
   * @param configuration
   *        is the configuration to set.
   */
  @Resource
  public void setConfiguration(Configuration configuration) {

    this.config = configuration;
  }

  /**
   * This method initializes this class. It has to be called after construction
   * and injection is completed.
   * 
   * @see net.sf.mmm.content.model.impl.AbstractMutableContentModelService#initialize()
   */
  @PostConstruct
  public void initialize() throws Exception {

    super.initialize();
    if (this.config == null) {
      // throw new MissingParameterException
      throw new IllegalStateException();
    }
    importClass(this.config.getDescendant(ContentClass.CLASS_NAME), null);
  }

  /**
   * @see net.sf.mmm.content.model.impl.AbstractMutableContentModelService#createClassId()
   */
  @Override
  protected IdImpl createClassId() {

    return new IdImpl(IdImpl.OID_CLASS, this.nextClassId++);
  }

  /**
   * @see net.sf.mmm.content.model.impl.AbstractMutableContentModelService#createFieldId()
   */
  @Override
  protected IdImpl createFieldId() {

    return new IdImpl(IdImpl.OID_FIELD, this.nextFieldId++);
  }

}
