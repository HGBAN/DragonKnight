package dragonknight.powers;

import static dragonknight.DragonKnightMod.*;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;

import dragonknight.DragonKnightMod;
import dragonknight.actions.CopyCardInHandAction;

public class AshBrandPower extends BasePower {
    public static final String POWER_ID = makeID("AshBrandPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public AshBrandPower(AbstractCreature owner, int amount) {
        super(POWER_ID, PowerType.BUFF, true, owner, owner, amount);
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    @Override
    public void atStartOfTurn() {
        if (!owner.isPlayer)
            return;
        this.addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                if (DragonKnightMod.brandCardsLastTurn.size() > 0) {
                    flash();
                    ArrayList<AbstractCard> candidates = new ArrayList<>(DragonKnightMod.brandCardsLastTurn);
                    for (int i = 0; i < AshBrandPower.this.amount; i++) {
                        if (candidates.size() <= 0)
                            break;
                        int index = AbstractDungeon.cardRng.random(candidates.size() - 1);
                        AbstractCard randomCard = candidates.get(index);
                        candidates.remove(index);

                        AbstractCard newCard = copyCard(randomCard);
                        if (!newCard.exhaust)
                            newCard.tags.add(DragonKnightMod.Enums.EXHAUST);
                        if (!newCard.isEthereal)
                            newCard.tags.add(DragonKnightMod.Enums.ETHEREAL);
                        newCard.isEthereal = true;
                        newCard.exhaust = true;

                        newCard.initializeDescription();
                        addToBot(new CopyCardInHandAction(newCard));
                    }
                }
                isDone = true;
            }
        });

    }

}
