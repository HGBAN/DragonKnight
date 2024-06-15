package dragonknight.powers;

import static dragonknight.DragonKnightMod.*;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;

import dragonknight.actions.DrawBrandCardAction;
import dragonknight.patch.CardPatch;

public class OriasKnowledgePower extends BasePower {
    public static final String POWER_ID = makeID("OriasKnowledgePower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public OriasKnowledgePower(AbstractCreature owner, int amount) {
        super(POWER_ID, PowerType.BUFF, true, owner, owner, amount);
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    @Override
    public void atStartOfTurn() {
        this.amount--;
        if (amount <= 0)
            addToBot(new RemoveSpecificPowerAction(owner, owner, this));
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.type.equals(CardType.SKILL)) {
            flash();
            addToBot(new DrawBrandCardAction(1, (c) -> c.type.equals(CardType.POWER)));
            addToBot(new AbstractGameAction() {
                @Override
                public void update() {
                    for (AbstractCard c : DrawBrandCardAction.cards) {
                        if (c.cost > 0) {
                            c.cost--;
                            c.setCostForTurn(card.cost);
                        }
                        c.setCostForTurn(c.costForTurn - 1);
                        CardPatch.Field.reduceEnergy.set(c, true);
                    }
                    DrawBrandCardAction.cards.clear();
                    isDone = true;
                }
            });
        }
    }

}
