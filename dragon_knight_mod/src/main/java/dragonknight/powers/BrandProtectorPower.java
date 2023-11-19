package dragonknight.powers;

import static dragonknight.DragonKnightMod.logger;
import static dragonknight.DragonKnightMod.makeID;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;

import dragonknight.DragonKnightMod;

public class BrandProtectorPower extends BasePower {
    public static final String POWER_ID = makeID("BrandProtectorPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public BrandProtectorPower(AbstractCreature owner, int amount) {
        super(POWER_ID, PowerType.BUFF, true, owner, owner, amount);
        priority = 3;
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if (!isPlayer)
            return;
        AbstractPlayer p = AbstractDungeon.player;
        int block = DragonKnightMod.brandCards.size() * this.amount;
        logger.info(DragonKnightMod.brandCards.size());
        this.addToBot(new GainBlockAction(p, p, block));
    }
}
