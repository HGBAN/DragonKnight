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
import com.megacrit.cardcrawl.powers.AbstractPower;

public class BarrierPower extends BasePower {
    public static final String POWER_ID = makeID("BarrierPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public AbstractCreature source;

    public BarrierPower(AbstractCreature owner, AbstractCreature source, int amount) {
        super(POWER_ID, PowerType.DEBUFF, true, owner, owner, amount);
        this.source = source;
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        addToBot(new RemoveSpecificPowerAction(owner, owner, this));
    }

    public void onAddPower(AbstractPower power) {
        int totalAmount = power.amount;
        if (owner.hasPower(power.ID)) {
            totalAmount += owner.getPower(power.ID).amount;
        }

        this.flash();
        addToBot(new DamageAction(owner, new DamageInfo(source, totalAmount * amount, DamageType.HP_LOSS),
                AttackEffect.FIRE));
    }
}
