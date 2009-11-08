package org.multiply.mecommands;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MeCommandsReplacer {
  private static final Logger logger = Logger.getLogger(MeCommandsReplacer.class.getName());
  private static final Pattern meCommand = Pattern.compile("\\/me\\s+(.*?)[\\.\\n\\r]");

  /**
   * Replace all /me commands with the username and action specified.
   *
   * @param contents the original text contents.
   * @return the original contents with the me commands replaced.
   */
  public static String replaceMeCommands(String username, String contents) {
    Matcher matcher = meCommand.matcher(contents);
    while (matcher.find()) {
      String replacement = String.format("*%s %s*", chompUsername(username), matcher.group(1));
      logger.info("Replacing with " + replacement);
      contents = matcher.replaceFirst(replacement);
      matcher = meCommand.matcher(contents);
    }
    return contents;
  }

  public static String chompUsername(String username) {
	  logger.info("Username: " + username);
    return username.contains("@") ? username.substring(0, username.indexOf("@")) : username;
  }
}
