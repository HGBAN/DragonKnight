package dragonknight.commands;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import basemod.devcommands.ConsoleCommand;
import dragonknight.DragonKnightMod;

public class BrandCommand extends ConsoleCommand {

    public BrandCommand() {
        minExtraTokens = 0;
        maxExtraTokens = 0;
        requiresPlayer = true;
        simpleCheck = true;
    }

    @Override
    protected void execute(String[] tokens, int depth) {
        AbstractDungeon.actionManager
                .addToBottom(new SelectCardsAction(AbstractDungeon.player.drawPile.group, "", cards -> {
                    for (AbstractCard brandCard : cards) {
                        DragonKnightMod.brandCard(brandCard);
                    }
                }));
    }
}
