package dragonknight.relics;

import static dragonknight.DragonKnightMod.makeID;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

import dragonknight.DragonKnightMod;
import dragonknight.character.DragonPrince;

public class BrandedCyclone extends BaseRelic {
    public static final String ID = makeID("BrandedCyclone");

    public boolean isUsed = false;

    public BrandedCyclone() {
        super(ID, "BrandedCyclone", RelicTier.COMMON, LandingSound.MAGICAL);
        pool = DragonPrince.Enums.CARD_COLOR;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void onUseCard(AbstractCard targetCard, UseCardAction useCardAction) {
        if (!isUsed) {
            if (targetCard.hasTag(DragonKnightMod.Enums.BRAND) || targetCard.hasTag(DragonKnightMod.Enums.BRAND2)) {
                addToBot(new DrawCardAction(1));
                this.flash();
                this.grayscale = true;
                this.isUsed = true;
            }
        }
    }

    @Override
    public void atBattleStart() {
        this.grayscale = false;
        this.isUsed = false;
    }

    @Override
    public void atTurnStart() {
        this.grayscale = false;
        this.isUsed = false;
    }

    @Override
    public void onVictory() {
        this.grayscale = false;
        this.isUsed = false;
    }
}
