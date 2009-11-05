package org.multiply.mecommands;

import org.multiply.mecommands.MeCommandsReplacer;

import junit.framework.TestCase;

public class MeCommandsReplacerTest extends TestCase {
  private static String creator = "jason@googlewave.com";
  private static String creatorWithoutDomain = "jason";

  public void testReplaceMeCommands_noMeCommand() {
    String contents = "Foo bar baz.";
	assertEquals(contents, MeCommandsReplacer.replaceMeCommands(creator, contents));
  }

  public void testReplaceMeCommands_onlyMeCommand() {
    assertEquals("*" + creatorWithoutDomain + " eats a sandwich*",
        MeCommandsReplacer.replaceMeCommands(creator, "/me eats a sandwich."));
  }

  public void testReplaceMeCommands_onlyMeCommandEndsWithNewline() {
    assertEquals("*" + creatorWithoutDomain + " eats a sandwich*",
        MeCommandsReplacer.replaceMeCommands(creator, "/me eats a sandwich\n"));
  }

  public void testReplaceMeCommands_onlyMeCommandEndsWithCarriageReturn() {
    assertEquals("*" + creatorWithoutDomain + " eats a sandwich*",
        MeCommandsReplacer.replaceMeCommands(creator, "/me eats a sandwich\r"));
  }

  public void testReplaceMeCommands_meCommandWithOtherStuff() {
    assertEquals("*" + creatorWithoutDomain + " eats a sandwich*\nNow I am done.",
        MeCommandsReplacer.replaceMeCommands(creator, "/me eats a sandwich.\nNow I am done."));
  }

  public void testReplaceMeCommands_multipleMeCommands() {
    assertEquals("*" + creatorWithoutDomain +
    	" eats a sandwich*\nNow I am done.\n*jason lied just now*",
        MeCommandsReplacer.replaceMeCommands(creator,
            "/me eats a sandwich.\nNow I am done.\n/me lied just now."));
  }
}
