package org.multiply.mecommands;

import com.google.wave.api.AbstractRobot;
import com.google.wave.api.Blip;
import com.google.wave.api.event.BlipSubmittedEvent;
import com.google.wave.api.event.WaveletParticipantsChangedEvent;
import com.google.wave.api.event.WaveletSelfAddedEvent;

/**
 * A simple wave robot that will replace all IRC-style me commands ({@code /me
 * eats a sandwich}) with text that says "{@code *author eats a sandwich*}
 *
 * @author jason@multiply.org (Jason Gessner)
 */
@SuppressWarnings("serial")
public class MeCommandsServlet extends AbstractRobot {

  // profile related methods
  @Override
  protected String getRobotName() {
    return "Me Commands";
  }

  @Override
  protected String getRobotAvatarUrl() {
    return "http://www.multiply.org/mecommands-icon.png";
  }

  @Override
  protected String getRobotProfilePageUrl() {
    return "http://mecommands.appspot.com/index.html";
  }

  // Events
  @Override
  public void onWaveletSelfAdded(WaveletSelfAddedEvent event) {
    event.getWavelet().reply("\nHi!  Thanks for adding mecommands.  I'll turn any IRC-style " +
        "me commands you type into a little action blurb.");
  }

  @Override
  public void onWaveletParticipantsChanged(WaveletParticipantsChangedEvent event) {
    for (String newParticipant: event.getParticipantsAdded()) {
      if (!newParticipant.equals("mecommands@appspot.com")) {
        event.getWavelet().reply("\nHi : " + newParticipant);
      }
    }
  }

  @Override
  public void onBlipSubmitted(BlipSubmittedEvent event) {
    String modifyingUser = event.getModifiedBy();
    Blip blip = event.getBlip();
    String contents = blip.all().value().getText();
    if (contents.contains("/me ")) {
      event.getBlip().all().delete().insert(
          MeCommandsReplacer.replaceMeCommands(modifyingUser, contents));
    }
  }
}
