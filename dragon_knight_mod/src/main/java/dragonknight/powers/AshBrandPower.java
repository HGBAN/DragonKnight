package dragonknight.powers;

import static dragonknight.DragonKnightMod.copyCard;
import static dragonknight.DragonKnightMod.makeID;

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

    public AshBrandPower(AbstractCreature owner) {
        super(POWER_ID, PowerType.BUFF, true, owner, owner, -1);
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    @Override
    public void atStartOfTurn() {
        this.addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                if (DragonKnightMod.brandCardsLastTurn.size() > 0) {
                    int index = AbstractDungeon.cardRng.random(DragonKnightMod.brandCardsLastTurn.size() - 1);
                    AbstractCard randomCard = DragonKnightMod.brandCardsLastTurn.get(index);

                    AbstractCard newCard = copyCard(randomCard);
                    if (!newCard.exhaust)
                        newCard.tags.add(DragonKnightMod.Enums.EXHAUST);
                    if (!newCard.isEthereal)
                        newCard.tags.add(DragonKnightMod.Enums.ETHEREAL);
                    newCard.isEthereal = true;
                    newCard.exhaust = true;
                    // newCard.rawDescription += " NL " + GameDictionary.ETHEREAL.NAMES[0] + " NL "
                    // + GameDictionary.EXHAUST.NAMES[0];
                    newCard.initializeDescription();
                    addToBot(new CopyCardInHandAction(newCard));
                }
                isDone = true;
            }
        });
    }

}
