/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.exception.api;

import java.util.Locale;
import java.util.UUID;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import net.sf.mmm.util.nls.api.NlsMessage;

/**
 * Test of {@link ServiceInvocationFailedException}.
 *
 * @author hohwille
 */
public class ServiceInvocationFailedExceptionTest extends Assertions {

  /** Test of {@link ServiceInvocationFailedException}. */
  @Test
  public void testExceptionWithL10n() {

    UUID uuid = UUID.randomUUID();
    String code = "502";
    String message = "Bad Gateway";
    String service = "com.foo.bar.MyRestService";
    ServiceInvocationFailedException error = new ServiceInvocationFailedException(message, code, uuid, service);
    assertThat(error.getUuid()).isSameAs(uuid);
    assertThat(error.getCode()).isSameAs(code);
    NlsMessage nlsMessage = error.getNlsMessage();
    assertThat(nlsMessage.getArgument("service")).isSameAs(service);
    assertThat(nlsMessage.getArgument("message")).isSameAs(message);
    assertThat(nlsMessage.getMessage()).isEqualTo("While invoking the service " + service + " the following error occurred: " + message
        + ". Probably the service is temporary unavailable. Please try again later. If the problem persists contact your system administrator.");
    assertThat(nlsMessage.getLocalizedMessage(Locale.GERMAN)).isEqualTo("Beim Aufruf des Service " + service + " ist folgender Fehler aufgetreten: " + message
        + ". Möglicherweise ist der Service vorübergehend nicht verfügbar. Bitte versuchen Sie es später noch einmal. Wenn das Problem bestehen bleibt, kontaktieren Sie Ihren System-Administrator.");
    assertThat(nlsMessage.getLocalizedMessage(Locale.FRENCH)).isEqualTo("Lors de l'appel du service " + service + ", l'erreur suivante s'est produite: "
        + message
        + ". Probablement le service est indisponible temporairement. Veuillez réessayer plus tard. Si le problème persiste, contactez votre administrateur système.");
    assertThat(nlsMessage.getLocalizedMessage(new Locale("es"))).isEqualTo("Al invocar el servicio " + service + ", se produjo el siguiente error: " + message
        + ". Probablemente el servicio no esté disponible temporalmente. Por favor, inténtelo de nuevo más tarde. Si el problema persiste, póngase en contacto con el administrador del sistema.");
  }

}
