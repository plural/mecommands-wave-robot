package org.multiply.mecommands;

import com.google.wave.api.AbstractRobotServlet;
import com.google.wave.api.Blip;
import com.google.wave.api.Event;
import com.google.wave.api.EventType;
import com.google.wave.api.RobotMessageBundle;
import com.google.wave.api.TextView;
import com.google.wave.api.Wavelet;

/**
 * A simple wave robot that will replace all IRC-style me commands ({@code /me
 * eats a sandwich}) with text that says "{@code *author eats a sandwich*}
 *
 * @author jason@multiply.org (Jason Gessner)
 */
@SuppressWarnings("serial")
public class MeCommandsServlet extends AbstractRobotServlet {

  @Override
  public void processEvents(RobotMessageBundle messageBundle) {
    Wavelet wavelet = messageBundle.getWavelet();

    if (messageBundle.wasSelfAdded()) {
      Blip blip = wavelet.appendBlip();
      TextView textView = blip.getDocument();
      textView.append(String.format("Hi!  Thanks for adding medo.  I'll turn any IRC-style me "
          + "commands you type into a little action blurb."));
    }

    int blipNumber = 0;
    for (Event e : messageBundle.getEvents()) {
      if (e.getType() == EventType.BLIP_SUBMITTED) {
        String modifyingUser =
            messageBundle.getBlipSubmittedEvents().get(blipNumber).getModifiedBy();
        Blip blip = e.getBlip();
        TextView textView = blip.getDocument();
        String contents = textView.getText();
        if (contents.contains("/me ")) {
          String newContents = MeCommandsReplacer.replaceMeCommands(modifyingUser, textView.getText());
          textView.delete();
          textView.append(newContents);
        }
        blipNumber++;
      }
    }
  }
}
