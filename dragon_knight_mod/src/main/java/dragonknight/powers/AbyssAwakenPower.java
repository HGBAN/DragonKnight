package dragonknight.powers;

import static dragonknight.DragonKnightMod.*;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;

public class AbyssAwakenPower extends BasePower {
    public static final String POWER_ID = makeID("AbyssAwakenPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    // private List<AbstractCard> cards = new ArrayList<>();

    public AbyssAwakenPower(AbstractCreature owner) {
        super(POWER_ID, PowerType.BUFF, false, owner, owner, -1);
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    @Override
    public void atStartOfTurn() {
        if (owner.isPlayer) {
            flash();
            addToBot(new ApplyPowerAction(owner, owner, new AbyssalBeastFormPower(owner)));
        }
        addToBot(new RemoveSpecificPowerAction(owner, owner, this));
    }
}
