package dragonknight.powers;

import static dragonknight.DragonKnightMod.*;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;

public class ScorchPower extends BasePower {
    public static final String POWER_ID = makeID("ScorchPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public ScorchPower(AbstractCreature owner, int amount) {
        super(POWER_ID, PowerType.DEBUFF, true, owner, owner, amount);
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        this.flash();
        addToBot(new DamageAction(owner, new DamageInfo(owner, this.amount, DamageType.HP_LOSS),
                AttackEffect.FIRE));
        this.amount /= 2;
        if (this.amount <= 0) {
            addToBot(new RemoveSpecificPowerAction(owner, owner, this));
        }
        updateDescription();
    }
}
