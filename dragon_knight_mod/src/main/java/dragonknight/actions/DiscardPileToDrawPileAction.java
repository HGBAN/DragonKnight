package dragonknight.actions;

import java.util.ArrayList;
import java.util.List;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class DiscardPileToDrawPileAction extends AbstractGameAction {
    private AbstractPlayer p;
    private int amount = 0;

    public DiscardPileToDrawPileAction(int amount) {
        this.p = AbstractDungeon.player;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.amount = amount;
    }

    @Override
    public void update() {
        ArrayList<AbstractCard> selectCards = selectCardsRandomly(p.discardPile.group, amount);
        for (AbstractCard card : selectCards) {
            p.discardPile.removeCard(card);
            p.discardPile.moveToDeck(card, true);
            // DiscardPileToTopOfDeckAction
        }
        this.isDone = true;
    }

    private static ArrayList<AbstractCard> selectCardsRandomly(List<AbstractCard> group, int num) {
        if (num >= group.size())
            return new ArrayList<>(group);
        ArrayList<AbstractCard> result = new ArrayList<>();
        ArrayList<AbstractCard> groupCopy = new ArrayList<>(group);
        for (int i = 0; i < num; i++) {
            int index = AbstractDungeon.cardRng.random(groupCopy.size() - 1);
            result.add(groupCopy.get(index));
            groupCopy.remove(index);
        }
        return result;
    }
}
