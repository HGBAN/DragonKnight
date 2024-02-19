package dragonknight.actions;

import java.util.Iterator;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import dragonknight.DragonKnightMod;

public class DiscardAntiBrandCardAction extends AbstractGameAction {
    private AbstractPlayer p;

    public static int count = 0;
    private boolean resetCount = false;

    public DiscardAntiBrandCardAction() {
        this(true);
    }

    public DiscardAntiBrandCardAction(boolean resetCount) {
        this.p = AbstractDungeon.player;
        this.actionType = ActionType.DISCARD;
        this.resetCount = resetCount;
    }

    @Override
    public void update() {
        Iterator<AbstractCard> itr = p.hand.group.iterator();
        while (itr.hasNext()) {
            AbstractCard c = itr.next();
            if (c.hasTag(DragonKnightMod.Enums.ANTI_BRAND)) {
                itr.remove();
                p.hand.moveToDiscardPile(c);
                c.triggerOnManualDiscard();
                count++;
            }
        }

        if (resetCount)
            count = 0;
        this.isDone = true;
    }
}
