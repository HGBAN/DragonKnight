package dragonknight.patch;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardAtBottomOfDeckAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAndDeckAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import dragonknight.relics.KingsInsight;

public class MakeCardPatch {
    @SpirePatch(clz = MakeTempCardAtBottomOfDeckAction.class, method = "update")
    public static class BottomOfDeckPatch {
        @SpireInsertPatch(loc = 27, localvars = { "c" })
        public static void Insert(MakeTempCardAtBottomOfDeckAction _instance, AbstractCard c) {
            AbstractPlayer p = AbstractDungeon.player;
            if (c.type.equals(CardType.STATUS) && p.hasRelic(KingsInsight.ID)) {
                p.getRelic(KingsInsight.ID).flash();
                p.drawPile.moveToExhaustPile(c);
                c.exhaustOnUseOnce = false;
                c.freeToPlayOnce = false;
            }
        }
    }

    @SpirePatch(clz = MakeTempCardInDiscardAction.class, method = "makeNewCard")
    public static class DiscardPatch {
        // @SpireInsertPatch(loc = 45)
        // public static void Insert(MakeTempCardInDiscardAction _instance) {
        // AbstractPlayer p = AbstractDungeon.player;
        // AbstractCard c = AbstractDungeon.player.discardPile.getTopCard();
        // if (c.type.equals(CardType.STATUS) && p.hasRelic(KingsInsight.ID)) {
        // p.getRelic(KingsInsight.ID).flash();
        // AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(c,
        // AbstractDungeon.player.discardPile));
        // }
        // }
        public static AbstractCard Postfix(AbstractCard result, MakeTempCardInDiscardAction _instance) {
            AbstractDungeon.actionManager
                    .addToBottom(new AbstractGameAction() {
                        @Override
                        public void update() {
                            AbstractPlayer p = AbstractDungeon.player;
                            if (result.type.equals(CardType.STATUS) && p.hasRelic(KingsInsight.ID)) {
                                p.getRelic(KingsInsight.ID).flash();
                                p.discardPile.moveToExhaustPile(result);
                                result.exhaustOnUseOnce = false;
                                result.freeToPlayOnce = false;
                            }
                            isDone = true;
                        }
                    });
            return result;
        }
    }

    @SpirePatch(clz = MakeTempCardInDiscardAndDeckAction.class, method = "update")
    public static class DiscardAndDeckPatch {
        @SpireInsertPatch(loc = 28, localvars = { "tmp" })
        public static void Insert(MakeTempCardInDiscardAndDeckAction _instance, AbstractCard tmp) {
            AbstractPlayer p = AbstractDungeon.player;
            if (tmp.type.equals(CardType.STATUS) && p.hasRelic(KingsInsight.ID)) {
                p.getRelic(KingsInsight.ID).flash();
                p.drawPile.moveToExhaustPile(tmp);
                tmp.exhaustOnUseOnce = false;
                tmp.freeToPlayOnce = false;
            }
        }

        @SpireInsertPatch(loc = 38, localvars = { "tmp" })
        public static void Insert2(MakeTempCardInDiscardAndDeckAction _instance, AbstractCard tmp) {
            AbstractPlayer p = AbstractDungeon.player;
            if (tmp.type.equals(CardType.STATUS) && p.hasRelic(KingsInsight.ID)) {
                p.getRelic(KingsInsight.ID).flash();
                p.discardPile.moveToExhaustPile(tmp);
                tmp.exhaustOnUseOnce = false;
                tmp.freeToPlayOnce = false;
            }
        }
    }

    @SpirePatch(clz = MakeTempCardInDrawPileAction.class, method = "update")
    public static class DrawPilePatch {
        @SpireInsertPatch(loc = 82)
        public static void Insert(MakeTempCardInDrawPileAction _instance, AbstractCard ___cardToMake) {
            AbstractPlayer p = AbstractDungeon.player;
            if (___cardToMake.type.equals(CardType.STATUS) && p.hasRelic(KingsInsight.ID)) {
                p.getRelic(KingsInsight.ID).flash();
                // AbstractDungeon.actionManager
                // .addToBottom(new ExhaustSpecificCardAction(p.drawPile., p.drawPile));
                CardGroup cardsOfType = p.drawPile.getCardsOfType(CardType.STATUS);
                for (AbstractCard card : cardsOfType.group) {
                    // AbstractDungeon.actionManager
                    //         .addToBottom(new ExhaustSpecificCardAction(card, p.drawPile));
                    p.drawPile.moveToExhaustPile(card);
                    card.exhaustOnUseOnce = false;
                    card.freeToPlayOnce = false;
                }
            }
        }
    }

    @SpirePatch(clz = MakeTempCardInHandAction.class, method = "makeNewCard")
    public static class HandPatch {
        public static AbstractCard Postfix(AbstractCard result, MakeTempCardInHandAction _instance) {
            AbstractDungeon.actionManager
                    .addToBottom(new AbstractGameAction() {
                        @Override
                        public void update() {
                            AbstractPlayer p = AbstractDungeon.player;
                            if (result.type.equals(CardType.STATUS) && p.hasRelic(KingsInsight.ID)) {
                                if (p.hand.contains(result)) {
                                    p.getRelic(KingsInsight.ID).flash();
                                    p.hand.moveToExhaustPile(result);
                                    result.exhaustOnUseOnce = false;
                                    result.freeToPlayOnce = false;
                                } else if (p.discardPile.contains(result)) {
                                    p.getRelic(KingsInsight.ID).flash();
                                    p.hand.moveToExhaustPile(result);
                                    result.exhaustOnUseOnce = false;
                                    result.freeToPlayOnce = false;
                                }
                            }
                            isDone = true;
                        }
                    });
            return result;
        }
    }
}
