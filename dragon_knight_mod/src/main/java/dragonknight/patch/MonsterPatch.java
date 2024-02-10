package dragonknight.patch;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import dragonknight.DragonKnightMod;

public class MonsterPatch {
    @SpirePatch(clz = AbstractMonster.class, method = "damage")
    public static class DamagePatch {

        @SpireInsertPatch(loc = 804)
        public static void Insert(AbstractMonster _instance, DamageInfo info) {
            DragonKnightMod.isEnemyDamagedThisTurn = true;
        }
    }
}
