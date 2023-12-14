package dragonknight.patch;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;

import dragonknight.DragonKnightMod;

public class CardGroupPatch {
    @SpirePatch(clz = CardGroup.class, method = "moveToExhaustPile")
    static class MoveToExhaustPilePatch {
        public static void Postfix(CardGroup __instance, AbstractCard c) {
            DragonKnightMod.exhaustCardsLastTurn.add(c);
        }
    }
}
