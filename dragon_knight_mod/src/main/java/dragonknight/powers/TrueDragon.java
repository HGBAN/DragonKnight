package dragonknight.powers;

import static dragonknight.DragonKnightMod.*;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;

public class TrueDragon extends BeDragonPower {
    public static final String POWER_ID = makeID("TrueDragon");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public TrueDragon(AbstractCreature owner) {
        super(POWER_ID, PowerType.BUFF, true, owner, owner, -1);
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    @Override
    protected void initializeTemplate() {
        if (owner.hasPower(makeID("BlackDragon"))) {
            addToBot(new RemoveSpecificPowerAction(owner, owner, makeID("BlackDragon")));
        }
        if (owner.hasPower(makeID("WhiteDragon"))) {
            addToBot(new RemoveSpecificPowerAction(owner, owner, makeID("WhiteDragon")));
        }
        if (!owner.isPlayer)
            return;
        // AbstractPlayer player = (AbstractPlayer) owner;
        addToBot(new GainEnergyAction(6));
        addToBot(new DrawCardAction(6));
        addToBot(new ApplyPowerAction(owner, owner, new TrueEyePower(owner, 6)));
    }
}
