package dragonknight.patch;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.CardGroup.CardGroupType;

import dragonknight.DragonKnightMod;

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
}
