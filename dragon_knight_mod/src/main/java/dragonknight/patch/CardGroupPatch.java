package dragonknight.patch;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.CardGroup.CardGroupType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import dragonknight.DragonKnightMod;
import dragonknight.powers.DivineFlameFormPower;

public class CardGroupPatch {
    @SpirePatch(clz = CardGroup.class, method = "moveToExhaustPile")
    static class MoveToExhaustPilePatch {
        public static void Postfix(CardGroup __instance, AbstractCard c) {
            DragonKnightMod.exhaustCardsThisTurn.add(c);
            DragonKnightMod.exhaustCount++;
        }
    }

    @SpirePatch(clz = CardGroup.class, method = SpirePatch.CONSTRUCTOR, paramtypez = {
            CardGroup.class,
            CardGroupType.class
    })
    static class ConstructorPatch {
        public static void Postfix(CardGroup __instance, CardGroup g, CardGroupType type) {
            if (type.equals(CardGroupType.DRAW_PILE)) {
                for (int i = 0; i < g.group.size(); i++) {
                    CardPatch.Field.masterCard.set(__instance.group.get(i), g.group.get(i));
                }
            }
        }
    }

    @SpirePatch(clz = DiscardAction.class, method = "update")
    static class MoveToDiscardPilePatch {
        // public static void Postfix(CardGroup __instance, AbstractCard c) {
        // AbstractPlayer p = AbstractDungeon.player;
        // if (p.hasPower(DivineFlameFormPower.POWER_ID)) {
        // ((DivineFlameFormPower)
        // p.getPower(DivineFlameFormPower.POWER_ID)).onDiscard(c);
        // }
        // }
        @SpireInsertPatch(locs = { 57, 71, 98 }, localvars = { "c" })
        public static void Insert(DiscardAction __instance, AbstractCard c) {
            AbstractPlayer p = AbstractDungeon.player;
            if (p.hasPower(DivineFlameFormPower.POWER_ID)) {
                ((DivineFlameFormPower) p.getPower(DivineFlameFormPower.POWER_ID)).onDiscard(c);
            }
        }
    }

    @SpirePatch(clz = DiscardSpecificCardAction.class, method = "update")
    static class DiscardSpecificCardPatch {

        @SpireInsertPatch(loc = 38)
        public static void Insert(DiscardSpecificCardAction __instance, AbstractCard ___targetCard) {
            AbstractPlayer p = AbstractDungeon.player;
            if (p.hasPower(DivineFlameFormPower.POWER_ID)) {
                ((DivineFlameFormPower) p.getPower(DivineFlameFormPower.POWER_ID)).onDiscard(___targetCard);
            }
        }
    }
}
