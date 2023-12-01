package dragonknight.commands;

import java.util.ArrayList;

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
        ArrayList<AbstractCard> group = new ArrayList<>();
        for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
            if (!c.hasTag(DragonKnightMod.Enums.ANTI_BRAND)) {
                group.add(c);
            }
        }
        AbstractDungeon.actionManager
                .addToBottom(new SelectCardsAction(group, "", cards -> {
                    for (AbstractCard brandCard : cards) {
                        DragonKnightMod.brandCard(brandCard);
                    }
                }));
    }
}
