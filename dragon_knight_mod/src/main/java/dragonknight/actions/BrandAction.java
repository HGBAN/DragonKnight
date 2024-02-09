package dragonknight.actions;

import static dragonknight.DragonKnightMod.*;

import java.util.ArrayList;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import dragonknight.DragonKnightMod.Enums;

public class BrandAction extends AbstractGameAction {
    private boolean canChoose;

    public BrandAction(boolean canChoose) {
        this.canChoose = canChoose;
        this.actionType = ActionType.CARD_MANIPULATION;
    }

    @Override
    public void update() {
        AbstractPlayer player = AbstractDungeon.player;
        if (player.drawPile.isEmpty()) {
            isDone = true;
            return;
        }
        if (!canChoose) {
            AbstractCard brandCard = getRandomCardThatCanBrand(player.drawPile);
            if (brandCard != null)
                brandCard(brandCard);
        } else {
            ArrayList<AbstractCard> group = new ArrayList<>();
            for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
                if (!c.hasTag(Enums.ANTI_BRAND)) {
                    group.add(c);
                }
            }
            AbstractDungeon.actionManager
                    .addToBottom(new SelectCardsAction(group, "选择一张牌消耗", cards -> {
                        for (AbstractCard brandCard : cards) {
                            brandCard(brandCard);
                        }
                    }));
        }
        isDone = true;
    }

}
