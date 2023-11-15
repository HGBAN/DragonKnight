package dragonknight.powers;

import static dragonknight.DragonKnightMod.makeID;

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class WhiteRealmPower extends AbstractPower {
    public static final String POWER_ID = makeID("WhiteRealmPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public WhiteRealmPower(AbstractCreature owner) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = -1;
        this.type = PowerType.BUFF;
        this.isTurnBased = true;
        this.description = DESCRIPTIONS[0];

        loadRegion("demonForm");
        // com.megacrit.cardcrawl.powers.DemonFormPower
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
