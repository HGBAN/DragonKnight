package dragonknight.powers;

import static dragonknight.DragonKnightMod.*;

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;

import dragonknight.DragonKnightMod;

public class IceDevilsHeartPower extends BasePower {
    public static final String POWER_ID = makeID("IceDevilsHeartPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public IceDevilsHeartPower(AbstractCreature owner, int amount) {
        super(POWER_ID, PowerType.BUFF, true, owner, owner, amount);
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.hasTag(DragonKnightMod.Enums.ANTI_BRAND)) {
            this.amount--;
            this.flash();
            if (this.amount <= 0) {
                addToBot(new RemoveSpecificPowerAction(owner, owner, this));
            }
        }
    }

    @Override
    public void atStartOfTurn() {
        addToBot(new RemoveSpecificPowerAction(owner, owner, this));
    }
}
