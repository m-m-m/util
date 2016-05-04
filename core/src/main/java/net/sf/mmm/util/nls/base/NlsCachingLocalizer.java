/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.base;

import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.mmm.util.collection.api.MapFactory;
import net.sf.mmm.util.collection.base.ConcurrentHashMapFactory;
import net.sf.mmm.util.nls.api.NlsAccess;
import net.sf.mmm.util.nls.api.NlsLocalizer;
import net.sf.mmm.util.nls.api.NlsMessage;
import net.sf.mmm.util.nls.api.NlsMessageFactory;
import net.sf.mmm.util.nls.api.NlsTemplate;
import net.sf.mmm.util.nls.api.NlsTemplateResolver;

/**
 * This is a class that stores {@link NlsMessage messages} and simplifies to resolve them. <br>
 * <b>ATTENTION:</b><br>
 * This class collects instances {@link NlsMessage} and {@link NlsTemplate} to speed up repetitive translations. So only
 * use this class for a dedicated set of constant {@link #localize(Locale, String) internationalized messages} to avoid
 * memory leaks or supply an according {@link MapFactory} at construction.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.1
 */
public class NlsCachingLocalizer implements NlsLocalizer {

  /** The pattern. */
  private static final Pattern TEMPLATE_PATTERN = Pattern.compile("\\{(([^}]*)[#])?([^}]*)\\}");

  /** The {@link NlsMessageFactory} instance. */
  private final NlsMessageFactory messageFactory;

  /** The {@link NlsTemplateResolver} instance. */
  private final NlsTemplateResolver templateResolver;

  /** The {@link Map} used to cache {@link #localize(Locale, String) messages}. */
  private final Map<String, NlsMessage> messagesMap;

  /**
   * The {@link Map} used to cache {@link #localize(Locale, String, Map) templates}.
   */
  private final Map<String, NlsTemplate> templatesMap;

  /**
   * The {@link net.sf.mmm.util.nls.base.NlsTemplateImpl#getName() bundle-name}.
   */
  private final String bundleName;

  /**
   * The constructor.
   *
   * @param bundleName is the default {@link net.sf.mmm.util.nls.base.NlsTemplateImpl#getName() bundle-name}.
   */
  public NlsCachingLocalizer(String bundleName) {

    this(bundleName, NlsAccess.getFactory(), NlsAccess.getTemplateResolver());
  }

  /**
   * The constructor.
   *
   * @param bundleName is the default {@link net.sf.mmm.util.nls.base.NlsTemplateImpl#getName() bundle-name}.
   * @param messageFactory is the {@link NlsMessageFactory} instance.
   * @param templateResolver is the {@link NlsTemplateResolver} instance.
   */
  public NlsCachingLocalizer(String bundleName, NlsMessageFactory messageFactory,
      NlsTemplateResolver templateResolver) {

    this(bundleName, messageFactory, templateResolver, ConcurrentHashMapFactory.INSTANCE);
  }

  /**
   * The constructor.
   *
   * @param bundleName is the default {@link net.sf.mmm.util.nls.base.NlsTemplateImpl#getName() bundle-name}.
   * @param messageFactory is the {@link NlsMessageFactory} instance.
   * @param templateResolver is the {@link NlsTemplateResolver} instance.
   * @param mapFactory is the {@link MapFactory} used to create caches for the the {@link NlsMessage messages} and
   *        {@link NlsTemplate templates}.
   */
  @SuppressWarnings("rawtypes")
  public NlsCachingLocalizer(String bundleName, NlsMessageFactory messageFactory,
      NlsTemplateResolver templateResolver, MapFactory<? extends Map> mapFactory) {

    super();
    this.bundleName = bundleName;
    this.messageFactory = messageFactory;
    this.templateResolver = templateResolver;
    this.messagesMap = mapFactory.create();
    this.templatesMap = mapFactory.create();
  }

  /**
   * This method parses the given {@code internationalizedMessage} as {@link NlsTemplate} in case it is given in the
   * form {{@literal <BUNDLE>#<KEY>}}.
   *
   * @param internationalizedMessage is the template specified in the form described above or the
   *        {@link NlsMessage#getInternationalizedMessage() internationalized message}.
   * @return the {@link NlsTemplate} if the {@code internationalizedMessage} has the form described above or
   *         {@code null} otherwise.
   */
  protected NlsTemplate parseTemplate(String internationalizedMessage) {

    Matcher matcher = TEMPLATE_PATTERN.matcher(internationalizedMessage);
    if (matcher.matches()) {
      String name = matcher.group(2);
      if (name == null) {
        name = this.bundleName;
      }
      String key = matcher.group(3);
      return new NlsTemplateImpl(name, key);
    }
    return null;
  }

  @Override
  public String localize(Locale locale, String internationalizedMessage, Map<String, Object> arguments) {

    NlsTemplate template = this.templatesMap.get(internationalizedMessage);
    if (template == null) {
      template = parseTemplate(internationalizedMessage);
      if (template == null) {
        template = this.templateResolver.resolveTemplate(internationalizedMessage);
      }
      this.templatesMap.put(internationalizedMessage, template);
    }
    NlsMessage message = this.messageFactory.create(template, arguments);
    return message.getLocalizedMessage(locale);
  }

  @Override
  @SuppressWarnings("deprecation")
  public String localize(Locale locale, String internationalizedMessage) {

    NlsMessage message = this.messagesMap.get(internationalizedMessage);
    if (message == null) {
      NlsTemplate template = parseTemplate(internationalizedMessage);
      if (template == null) {
        message = this.messageFactory.create(internationalizedMessage);
      } else {
        message = this.messageFactory.create(template);
      }
      this.messagesMap.put(internationalizedMessage, message);
    }
    return message.getLocalizedMessage(locale, this.templateResolver);
  }

}
