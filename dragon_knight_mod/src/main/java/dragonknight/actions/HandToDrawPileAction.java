package dragonknight.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class HandToDrawPileAction extends AbstractGameAction {
    private AbstractPlayer p;

    public HandToDrawPileAction() {
        this.p = AbstractDungeon.player;
        this.actionType = ActionType.CARD_MANIPULATION;
    }

    @Override
    public void update() {
        while (p.hand.size() > 0) {
            AbstractCard card = p.hand.group.remove(0);
            p.hand.moveToDeck(card, true);
        }

        this.isDone = true;
    }
}
