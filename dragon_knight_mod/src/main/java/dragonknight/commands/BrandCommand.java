package dragonknight.commands;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import basemod.devcommands.ConsoleCommand;
import dragonknight.DragonKnightMod;
import dragonknight.powers.Brand;

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
                    AbstractPlayer player = AbstractDungeon.player;
                    for (AbstractCard brandCard : cards) {
                        DragonKnightMod.brandCards.add(brandCard);
                        AbstractDungeon.actionManager
                                .addToBottom(new ExhaustSpecificCardAction(brandCard, player.drawPile));
                        AbstractDungeon.actionManager
                                .addToBottom(new ApplyPowerAction(player, player, new Brand(player)));
                    }
                }));
    }
}
