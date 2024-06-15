package dragonknight.powers;

import static dragonknight.DragonKnightMod.*;

import com.megacrit.cardcrawl.actions.common.BetterDrawPileToHandAction;
import com.megacrit.cardcrawl.actions.defect.DiscardPileToHandAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class BuerBrandPower extends BasePower {
    public static final String POWER_ID = makeID("BuerBrandPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private boolean usedSelf = false;
    private boolean usedEnemy = false;

    public BuerBrandPower(AbstractCreature owner) {
        super(POWER_ID, PowerType.BUFF, false, owner, owner, -1);
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    @Override
    public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        if (source.isPlayer) {
            if (target.isPlayer) {
                if (!usedSelf) {
                    usedSelf = true;
                    addToBot(new BetterDrawPileToHandAction(1));
                }
            }else{
                if (!usedEnemy) {
                    usedEnemy = true;
                    addToBot(new DiscardPileToHandAction(1));
                }
            }
        }
    }

    @Override
    public void atStartOfTurn() {
        usedSelf = usedEnemy = false;
    }

}
