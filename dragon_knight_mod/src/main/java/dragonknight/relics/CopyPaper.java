package dragonknight.relics;

import static dragonknight.DragonKnightMod.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import dragonknight.character.DragonPrince;

public class CopyPaper extends BaseRelic {
    public static final String ID = makeID("CopyPaper");

    public CopyPaper() {
        super(ID, "CopyPaper", RelicTier.SHOP, LandingSound.MAGICAL);
        pool = DragonPrince.Enums.CARD_COLOR;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void atTurnStartPostDraw() {
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                AbstractPlayer p = AbstractDungeon.player;
                List<AbstractCard> brandCards = p.drawPile.group.stream().filter(x -> isBrandCard(x))
                        .collect(Collectors.toList());
                if (brandCards.size() > 0) {
                    CopyPaper.this.flash();
                    addToBot(new SelectCardsInHandAction(1, "返回抽牌堆", true, true, (x) -> true, (cards) -> {
                        ArrayList<AbstractCard> tmpCards = new ArrayList<>(cards);
                        addToBot(new AbstractGameAction() {

                            @Override
                            public void update() {
                                if (tmpCards.size() > 0) {
                                    p.hand.group.remove(tmpCards.get(0));
                                    p.hand.moveToDeck(tmpCards.get(0), true);

                                    AbstractCard brandCard = brandCards
                                            .get(AbstractDungeon.cardRng.random(brandCards.size() - 1));
                                    p.drawPile.group.remove(brandCard);
                                    p.drawPile.moveToHand(brandCard);

                                    // p.hand.refreshHandLayout();
                                }
                                isDone = true;
                            }
                        });
                    }));
                }
                isDone = true;
            }
        });

    }

}
