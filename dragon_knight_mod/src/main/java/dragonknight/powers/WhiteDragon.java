package dragonknight.powers;

import static dragonknight.DragonKnightMod.*;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.StrengthPower;

import dragonknight.DragonKnightMod;
import dragonknight.actions.CopyCardInHandAction;

public class WhiteDragon extends BeDragonPower {
    public static final String POWER_ID = makeID("WhiteDragon");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public WhiteDragon(AbstractCreature owner) {
        super(POWER_ID, PowerType.BUFF, true, owner, owner, -1);
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    @Override
    protected void initializeTemplate() {
        if (owner.hasPower(makeID("BlackDragon")) || owner.hasPower(makeID("TrueDragon"))) {
            addToBot(new DrawCardAction(1));
            addToBot(new RemoveSpecificPowerAction(owner, owner, this));
            done = true;
            return;
        }
        addToBot(new SelectCardsInHandAction(1, DragonKnightMod.selectCardTips.TEXT_DICT.get("WhiteDragon"),
                (card) -> !card.hasTag(DragonKnightMod.Enums.ANTI_BRAND) && canUseCard(card),
                (cards) -> {
                    for (AbstractCard card : cards) {
                        AbstractCard newCard = card.makeSameInstanceOf();

                        addBrandToCard(newCard, true);
                        newCard.initializeDescription();
                        addToBot(new CopyCardInHandAction(newCard));
                    }
                }));

        if (owner.hasPower(makeID("WhiteBrandPower"))) {
            owner.getPower(makeID("WhiteBrandPower")).flash();
            addToBot(new ApplyPowerAction(owner, owner,
                    new StrengthPower(owner, owner.getPower(makeID("WhiteBrandPower")).amount)));
        }

        if (owner.hasPower(AbyssalBeastFormPower.POWER_ID)) {
            owner.getPower(AbyssalBeastFormPower.POWER_ID).flash();
            addToBot(new ApplyPowerAction(owner, owner,
                    new StrengthPower(owner, 1)));
        }
    }

    @Override
    public void stackPower(int stackAmount) {
        addToBot(new DrawCardAction(1));
    }
}
