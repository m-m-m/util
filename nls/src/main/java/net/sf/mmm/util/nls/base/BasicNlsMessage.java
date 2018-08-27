/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.base;

import java.util.Collections;
import java.util.Map;

import net.sf.mmm.util.nls.api.NlsAccess;
import net.sf.mmm.util.nls.api.NlsTemplate;
import net.sf.mmm.util.nls.api.NlsTemplateResolver;

/**
 * This is the abstract implementation of {@link net.sf.mmm.util.nls.api.NlsMessage} with the basic features. <br>
 * You should extend this class whenever suitable to implement {@link net.sf.mmm.util.nls.api.NlsMessage}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.1
 */
public abstract class BasicNlsMessage extends AbstractNlsMessage {

  private static final long serialVersionUID = 7021159510028831610L;

  /** The {@link #message} as {@link NlsTemplate}. */
  private NlsTemplate template;

  private String message;

  private Map<String, Object> arguments;

  /**
   * The constructor for de-serialization in GWT.
   */
  protected BasicNlsMessage() {

    super();
  }

  /**
   * The constructor.
   *
   * @param template is the {@link NlsTemplate} for the {@link #getInternationalizedMessage() raw message}.
   * @param messageArguments are the {@link #getArgument(String) arguments} filled into the message after
   *        nationalization.
   */
  public BasicNlsMessage(NlsTemplate template, Map<String, Object> messageArguments) {

    this(template, null, messageArguments);
    assert (template != null);
  }

  /**
   * The constructor.
   *
   * @param internationalizedMessage is the {@link #getInternationalizedMessage() internationalized message}.
   * @param messageArguments are the {@link #getArgument(String) arguments} filled into the message after
   *        nationalization.
   */
  public BasicNlsMessage(String internationalizedMessage, Map<String, Object> messageArguments) {

    this(null, internationalizedMessage, messageArguments);
    assert (internationalizedMessage != null);
  }

  /**
   * The constructor.
   *
   * @param template is the {@link NlsTemplate} for the {@link #getInternationalizedMessage() raw message}.
   * @param internationalizedMessage is the {@link #getInternationalizedMessage() internationalized message}.
   * @param messageArguments are the {@link #getArgument(String) arguments} filled into the message after
   *        nationalization.
   */
  private BasicNlsMessage(NlsTemplate template, String internationalizedMessage,
      Map<String, Object> messageArguments) {

    super();
    this.template = template;
    this.message = internationalizedMessage;
    if (messageArguments == null) {
      this.arguments = Collections.emptyMap();
    } else {
      this.arguments = messageArguments;
    }
  }

  @Override
  public Object getArgument(String key) {

    return this.arguments.get(key);
  }

  @Override
  public String getInternationalizedMessage() {

    if (this.message == null) {
      this.message = this.template.translate(LOCALE_ROOT);
    }
    return this.message;
  }

  /**
   * This method gets the message {@link #getArgument(String) arguments}.
   *
   * @return the {@link Map} with the arguments.
   */
  protected final Map<String, Object> getArguments() {

    return this.arguments;
  }

  /**
   * This method gets the {@link NlsTemplate} of this message.
   *
   * @return the text the {@link NlsTemplate} or {@code null} if NOT yet
   *         {@link NlsTemplateResolver#resolveTemplate(String) resolved}.
   */
  public NlsTemplate getTemplate() {

    return this.template;
  }

  /**
   * This method gets the {@link NlsTemplate} of this message.
   *
   * @param resolver is the {@link NlsTemplateResolver} used to {@link NlsTemplateResolver#resolveTemplate(String)
   *        resolve} the {@link NlsTemplate} if NOT yet available.
   * @return the text the {@link NlsTemplate}.
   */
  public NlsTemplate getTemplate(NlsTemplateResolver resolver) {

    if (this.template == null) {
      synchronized (this) {
        if (this.template == null) {
          NlsTemplateResolver templateResolver;
          if (resolver == null) {
            templateResolver = NlsAccess.getTemplateResolver();
          } else {
            templateResolver = resolver;
          }
          this.template = templateResolver.resolveTemplate(this.message);
        }
      }
    }
    return this.template;
  }

}
