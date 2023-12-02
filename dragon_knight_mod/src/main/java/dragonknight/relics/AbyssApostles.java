package dragonknight.relics;

import static dragonknight.DragonKnightMod.makeID;

import java.util.ArrayList;

import com.megacrit.cardcrawl.cards.AbstractCard;

import dragonknight.DragonKnightMod;
import dragonknight.actions.CopyCardInHandAction;
import dragonknight.character.DragonPrince;

public class AbyssApostles extends BaseRelic {
    public static final String ID = makeID("AbyssApostles");

    private ArrayList<AbstractCard> newCards = new ArrayList<>();

    public AbyssApostles() {
        super(ID, "AbyssApostles", RelicTier.BOSS, LandingSound.MAGICAL);
        pool = DragonPrince.Enums.CARD_COLOR;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void onPlayerEndTurn() {
        int count = Math.min(DragonKnightMod.brandCards.size(), 3);
        for (int i = 0; i < count; i++) {
            AbstractCard card = DragonKnightMod.brandCards.get(i);
            AbstractCard newCard = card.makeStatEquivalentCopy();
            newCard.tags.clear();
            newCard.tags.addAll(card.tags);
            newCard.exhaust = true;
            newCard.isEthereal = true;
            newCard.tags.add(DragonKnightMod.Enums.EXHAUST);
            newCard.tags.add(DragonKnightMod.Enums.ETHEREAL);
            // newCard.rawDescription += " NL " + GameDictionary.EXHAUST.NAMES[0] + " NL "
            //         + GameDictionary.ETHEREAL.NAMES[0];
            newCard.initializeDescription();

            newCards.add(newCard);
        }
    }

    @Override
    public void atTurnStartPostDraw() {
        if (!newCards.isEmpty()) {
            this.flash();
            for (AbstractCard card : newCards) {
                addToBot(new CopyCardInHandAction(card));
            }
            newCards.clear();
        }
    }
}
