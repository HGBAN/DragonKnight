package dragonknight.actions;

import static dragonknight.DragonKnightMod.*;

import java.util.ArrayList;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import dragonknight.DragonKnightMod;
import dragonknight.DragonKnightMod.Enums;
import dragonknight.relics.IceDevilsHeart;

public class BrandAction extends AbstractGameAction {
    private boolean canChoose;

    public BrandAction(boolean canChoose) {
        this.canChoose = canChoose;
        this.actionType = ActionType.CARD_MANIPULATION;
    }

    @Override
    public void update() {
        AbstractPlayer player = AbstractDungeon.player;
        if (player.drawPile.isEmpty() || player.hasRelic(IceDevilsHeart.ID)) {
            isDone = true;
            return;
        }
        if (!canChoose) {
            AbstractCard brandCard = getRandomCardThatCanBrand(player.drawPile);
            if (brandCard != null)
                brandCard(brandCard);
        } else {

            addToBot(new AbstractGameAction() {

                @Override
                public void update() {
                    ArrayList<AbstractCard> group = new ArrayList<>();
                    for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
                        if (!c.hasTag(Enums.ANTI_BRAND)) {
                            group.add(c);
                        }
                    }
                    addToTop((new SelectCardsAction(group, DragonKnightMod.selectCardTips.TEXT_DICT.get("Brand"),
                            cards -> {
                                for (AbstractCard brandCard : cards) {
                                    brandCard(brandCard);
                                }
                            })));
                    isDone = true;
                }

            });
        }
        isDone = true;
    }

}
