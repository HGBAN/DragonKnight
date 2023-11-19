package dragonknight.powers;

import static dragonknight.DragonKnightMod.makeID;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;

public class BlackBrandPower extends BasePower  {
    public static final String POWER_ID = makeID("BlackBrandPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public BlackBrandPower(AbstractCreature owner, int amount) {
        super(POWER_ID, PowerType.BUFF, true, owner, owner, amount);

    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }
}
