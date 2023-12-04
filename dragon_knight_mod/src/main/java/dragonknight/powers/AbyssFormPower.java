package dragonknight.powers;

import static dragonknight.DragonKnightMod.makeID;

import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;

import dragonknight.DragonKnightMod;

public class AbyssFormPower extends BasePower {
    public static final String POWER_ID = makeID("AbyssFormPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private boolean isUsed = false;

    public AbyssFormPower(AbstractCreature owner) {
        super(POWER_ID, PowerType.BUFF, true, owner, owner, -1);
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    @Override
    public void atStartOfTurn() {
        isUsed = false;
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if ((card.hasTag(DragonKnightMod.Enums.BRAND) || card.hasTag(DragonKnightMod.Enums.BRAND2)) && !isUsed) {
            this.flash();
            DragonKnightMod.beDragon();
            isUsed = true;
        }
    }

}
