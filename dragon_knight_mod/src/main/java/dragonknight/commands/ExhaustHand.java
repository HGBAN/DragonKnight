package dragonknight.commands;


import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import basemod.devcommands.ConsoleCommand;

public class ExhaustHand extends ConsoleCommand {

    public ExhaustHand() {
        minExtraTokens = 0;
        maxExtraTokens = 0;
        requiresPlayer = true;
        simpleCheck = true;
    }

    @Override
    protected void execute(String[] tokens, int depth) {
        // int index = Integer.parseInt(tokens[depth]);
        AbstractDungeon.actionManager.addToBottom(new ExhaustAction(1, true));
    }

    // public ArrayList<String> extraOptions(String[] tokens, int depth) {
    // ArrayList<String> result = new ArrayList<>();
    // result.add("add");
    // result.add("lose");

    // if (tokens[depth].equals("add") || tokens[depth].equals("lose")) {
    // complete = true;
    // /**
    // * Setting complete to true displays "Command is complete" in the autocomplete
    // * window.
    // * This is not necessary if "simplecheck = true" in the constructor and you
    // * don't have additional logic for it!
    // */
    // }

    // return result;
    // }
}
