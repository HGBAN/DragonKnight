package dragonknight.powers;

import static dragonknight.DragonKnightMod.*;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;

import dragonknight.DragonKnightMod;

public class VassagoBrandPower extends BasePower {
    public static final String POWER_ID = makeID("VassagoBrandPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public VassagoBrandPower(AbstractCreature owner, AbstractCreature sourceAbstractCreature) {
        super(POWER_ID, PowerType.BUFF, true, owner, owner, DragonKnightMod.cardsUsedThisTurn);
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    @Override
    public void atStartOfTurn() {
        amount = 0;
    }

}
