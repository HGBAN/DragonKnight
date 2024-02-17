package dragonknight.relics;

import static dragonknight.DragonKnightMod.*;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;

import dragonknight.DragonKnightMod;
import dragonknight.actions.DrawBrandCardAction;
import dragonknight.character.DragonPrince;

public class AbyssalSpectator extends BaseRelic {
    public static final String ID = makeID("AbyssalSpectator");

    private boolean isUsed = false;

    public AbyssalSpectator() {
        super(ID, "AbyssalSpectator", RelicTier.COMMON, LandingSound.MAGICAL);
        pool = DragonPrince.Enums.CARD_COLOR;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void onUseCard(AbstractCard targetCard, UseCardAction useCardAction) {
        if (!isUsed) {
            if (targetCard.hasTag(DragonKnightMod.Enums.ANTI_BRAND)) {
                this.flash();
                addToBot(new DrawBrandCardAction(1, x -> x.type.equals(CardType.POWER)));
                addToBot(new AbstractGameAction() {

                    @Override
                    public void update() {
                        for (AbstractCard c : DrawBrandCardAction.cards) {
                            c.tags.add(DragonKnightMod.Enums.BLACK_DRAGON);
                            c.initializeDescription();
                        }
                        DrawBrandCardAction.cards.clear();
                        isDone = true;
                    }

                });

                isUsed = true;
            }
        }
    }

    @Override
    public void atTurnStart() {
        isUsed = false;
    }

}
