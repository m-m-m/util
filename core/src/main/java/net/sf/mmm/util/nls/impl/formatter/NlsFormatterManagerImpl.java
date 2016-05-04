/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.impl.formatter;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import net.sf.mmm.util.nls.api.NlsFormatter;
import net.sf.mmm.util.nls.base.AbstractNlsDependencies;
import net.sf.mmm.util.nls.base.MappedNlsFormatterManager;
import net.sf.mmm.util.nls.base.NlsDependencies;
import net.sf.mmm.util.nls.impl.ConfiguredNlsFormatterMap;
import net.sf.mmm.util.scanner.base.CharSequenceScanner;

/**
 * This is the implementation of the {@link net.sf.mmm.util.nls.api.NlsFormatterManager} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@Named
@Singleton
public class NlsFormatterManagerImpl extends MappedNlsFormatterManager {

  private  NlsDependencies nlsDependencies;

  /**
   * The constructor.
   */
  public NlsFormatterManagerImpl() {

    super();
  }

  @Override
  protected void doInitialize() {

    super.doInitialize();
    if (this.nlsDependencies == null) {
      this.nlsDependencies = AbstractNlsDependencies.getInstance();
    }
    if (getFormatterMap() == null) {
      ConfiguredNlsFormatterMap formatterMap = new ConfiguredNlsFormatterMap();
      formatterMap.initialize();
      setFormatterMap(formatterMap);
    }
  }

  @Override
  protected NlsFormatter<?> getSubFormatter(String formatType, CharSequenceScanner scanner) {

    if (TYPE_CHOICE.equals(formatType)) {
      return new NlsFormatterChoice(scanner, this.nlsDependencies);
    } else {
      return super.getSubFormatter(formatType, scanner);
    }
  }

  @Override
  protected NlsFormatter<Object> getSubFormatter(String formatType, String subformat) {

    if (TYPE_NUMBER.equals(formatType)) {
      return new NlsFormatterNumberPattern(subformat);
    } else if ((TYPE_DATE.equals(formatType)) || (TYPE_TIME.equals(formatType)) || (TYPE_DATETIME.equals(formatType))) {
      return new NlsFormatterDatePattern(subformat);
    } else {
      return super.getSubFormatter(formatType, subformat);
    }
  }

  /**
   * @return the nlsDependencies
   */
  public NlsDependencies getNlsDependencies() {

    return this.nlsDependencies;
  }

  /**
   * This method sets the {@link NlsDependencies} to use.
   * 
   * @param nlsDependencies are the {@link NlsDependencies}.
   */
  @Inject
  public void setNlsDependencies(NlsDependencies nlsDependencies) {

    getInitializationState().requireNotInitilized();
    this.nlsDependencies = nlsDependencies;
  }

}
