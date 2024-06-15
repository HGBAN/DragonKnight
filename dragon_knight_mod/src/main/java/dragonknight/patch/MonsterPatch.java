package dragonknight.patch;

import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import dragonknight.DragonKnightMod;
import dragonknight.powers.OriasBrandPower;

public class MonsterPatch {
    @SpirePatch(clz = AbstractMonster.class, method = "damage")
    public static class DamagePatch {

        @SpireInsertPatch(loc = 804)
        public static void Insert(AbstractMonster _instance, DamageInfo info) {
            DragonKnightMod.isEnemyDamagedThisTurn = true;
        }
    }

    @SpirePatch(clz = AbstractMonster.class, method = "calculateDamage")
    public static class CalculateDamagePatch {
        public static void Postfix(AbstractMonster _instance, int dmg, @ByRef int[] ___intentDmg) {
            AbstractPlayer player = AbstractDungeon.player;
            if (player != null) {
                if (player.hasPower(OriasBrandPower.POWER_ID) && !AbstractDungeon.getCurrRoom().isBattleOver) {

                    ___intentDmg[0] /= 2;
                }
            }
        }
    }

    @SpirePatch(clz = AbstractMonster.class, method = "applyPowers")
    public static class ApplyPowersPatch {
        @SpireInsertPatch(loc = 1352, localvars = { "dmg" })
        public static void Insert(AbstractMonster _instance, DamageInfo dmg) {
            dmg.output /= 2;
        }
    }
}
