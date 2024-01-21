package dragonknight.powers;

import static dragonknight.DragonKnightMod.*;

import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;

import dragonknight.DragonKnightMod;
import dragonknight.actions.DiscardPileToDrawPileAction;
import dragonknight.actions.DrawBrandCardAction;

public class HeavenlyRevelationPower extends BasePower {
    public static final String POWER_ID = makeID("HeavenlyRevelationPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private boolean isUsed = false;

    public HeavenlyRevelationPower(AbstractCreature owner) {
        super(POWER_ID, PowerType.BUFF, true, owner, owner, 1);
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2];
    }

    @Override
    public void atStartOfTurn() {
        isUsed = false;
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (!owner.isPlayer)
            return;
        // AbstractPlayer player = AbstractDungeon.player;
        if ((card.hasTag(DragonKnightMod.Enums.BRAND) || card.hasTag(DragonKnightMod.Enums.BRAND2)
                || card.hasTag(DragonKnightMod.Enums.TEMP_BRAND)) && !isUsed) {
            this.flash();
            addToBot(new DrawBrandCardAction(amount, c -> c.name.contains("烙印")));
            addToBot(new DiscardPileToDrawPileAction(amount));
            isUsed = true;
        }
    }


}
