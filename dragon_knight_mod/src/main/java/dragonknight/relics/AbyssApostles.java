package dragonknight.relics;

import static dragonknight.DragonKnightMod.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;

import dragonknight.DragonKnightMod;
import dragonknight.actions.CopyCardInHandAction;
import dragonknight.character.DragonPrince;
import dragonknight.patch.CardPatch;

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
        // int count = Math.min(DragonKnightMod.brandCards.size(), 3);
        List<AbstractCard> cards = DragonKnightMod.brandCards.stream().filter(x -> x.type.equals(CardType.ATTACK))
                .collect(Collectors.toList());
        if (cards.size() > 0) {
            AbstractCard card = cards.get(0);
            AbstractCard newCard = card.makeStatEquivalentCopy();
            newCard.tags.clear();
            newCard.tags.addAll(card.tags);
            if (!newCard.exhaust)
                newCard.tags.add(DragonKnightMod.Enums.EXHAUST);
            // if (!newCard.isEthereal)
            // newCard.tags.add(DragonKnightMod.Enums.ETHEREAL);
            newCard.exhaust = true;
            if (newCard.cost > 0) {
                newCard.cost--;
                newCard.setCostForTurn(newCard.cost);
            }
            // newCard.(newCard.cost - 1);
            CardPatch.Field.reduceEnergy.set(newCard, true);
            // newCard.isEthereal = true;
            // newCard.rawDescription += " NL " + GameDictionary.EXHAUST.NAMES[0] + " NL "
            // + GameDictionary.ETHEREAL.NAMES[0];
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

    @Override
    public void atBattleStartPreDraw() {
        newCards.clear();
    }

}
