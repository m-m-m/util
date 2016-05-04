/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.transformer.base;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * This is a JAXB-ready Container for {@link #getTransformers() Transformers}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net))
 * @since 2.0.0
 */
@XmlRootElement(name = "transformers")
@XmlAccessorType(XmlAccessType.FIELD)
public class StringTransformerList {

  @XmlElement(name = "transformer-chain")
  private List<StringTransformerChain> transformers;

  /**
   * The constructor.
   */
  public StringTransformerList() {

    super();
  }

  /**
   * @return the transformers
   */
  public List<StringTransformerChain> getTransformers() {

    return this.transformers;
  }

  /**
   * @param transformers is the transformers to set
   */
  public void setTransformers(List<StringTransformerChain> transformers) {

    this.transformers = transformers;
  }

}
