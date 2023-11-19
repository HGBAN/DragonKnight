package dragonknight.powers;

import static dragonknight.DragonKnightMod.makeID;

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;

public class WhiteRealmPower extends BasePower {
    public static final String POWER_ID = makeID("WhiteRealmPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public WhiteRealmPower(AbstractCreature owner) {
        super(POWER_ID, PowerType.BUFF, true, owner, owner, -1);
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    @Override
    public void onInitialApplication() {
        Brand.triggerCount++;
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if (!isPlayer)
            return;
        Brand.triggerCount--;
        addToBot(new RemoveSpecificPowerAction(owner, owner, this));
    }
}
