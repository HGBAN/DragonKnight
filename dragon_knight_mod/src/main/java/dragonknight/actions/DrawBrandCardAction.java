package dragonknight.actions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.Predicate;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.CardGroup.CardGroupType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import dragonknight.DragonKnightMod;

public class DrawBrandCardAction extends AbstractGameAction {
    private AbstractPlayer p;
    private Predicate<AbstractCard> predicate;
    public static ArrayList<AbstractCard> cards = new ArrayList<>();

    public DrawBrandCardAction(int amount, Predicate<AbstractCard> predicate) {
        this.p = AbstractDungeon.player;
        this.amount = amount;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_MED;
        this.predicate = predicate;
    }

    public DrawBrandCardAction(int amount) {
        this(amount, (c) -> c.hasTag(DragonKnightMod.Enums.BRAND) || c.hasTag(DragonKnightMod.Enums.BRAND2)
                || c.hasTag(DragonKnightMod.Enums.TEMP_BRAND));
    }

    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_MED) {
            if (this.p.drawPile.isEmpty()) {
                this.isDone = true;
                return;
            }

            CardGroup tmp = new CardGroup(CardGroupType.UNSPECIFIED);
            Iterator<AbstractCard> var = this.p.drawPile.group.iterator();

            AbstractCard card;
            while (var.hasNext()) {
                card = var.next();
                if (predicate.test(card)) {
                    tmp.addToRandomSpot(card);
                }
            }

            if (tmp.size() == 0) {
                this.isDone = true;
                return;
            }

            for (int i = 0; i < this.amount; ++i) {
                if (!tmp.isEmpty()) {
                    tmp.shuffle();
                    card = tmp.getBottomCard();
                    tmp.removeCard(card);
                    if (this.p.hand.size() == 10) {
                        this.p.drawPile.moveToDiscardPile(card);
                        this.p.createHandIsFullDialog();
                    } else {
                        card.unhover();
                        card.lighten(true);
                        card.setAngle(0.0F);
                        card.drawScale = 0.12F;
                        card.targetDrawScale = 0.75F;
                        card.current_x = CardGroup.DRAW_PILE_X;
                        card.current_y = CardGroup.DRAW_PILE_Y;
                        this.p.drawPile.removeCard(card);
                        AbstractDungeon.player.hand.addToTop(card);
                        AbstractDungeon.player.hand.refreshHandLayout();
                        AbstractDungeon.player.hand.applyPowers();
                    }
                    cards.add(card);
                }
            }

            this.isDone = true;
        }

        this.tickDuration();
    }
}
