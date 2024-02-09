package dragonknight.patch;

import static dragonknight.DragonKnightMod.*;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class PlayerPatch {

    @SpirePatch(clz = AbstractPlayer.class, method = "useCard")
    public static class useCardPatch {
        private static boolean changed = false;

        public static void Prefix(AbstractPlayer _instance, AbstractCard c, AbstractMonster monster, int energyOnUse) {
            AbstractPlayer player = AbstractDungeon.player;
            if (player != null) {
                if ((player.hasPower(makeID("TrueEyePower")) && !AbstractDungeon.getCurrRoom().isBattleOver
                        && c.costForTurn > 0)) {
                    c.setCostForTurn(c.costForTurn - 1);
                    changed = true;
                }
            }
        }

        public static void Postfix(AbstractPlayer _instance, AbstractCard c, AbstractMonster monster, int energyOnUse) {
            if (changed) {
                c.setCostForTurn(c.costForTurn + 1);
                if (c.costForTurn == c.cost) {
                    c.isCostModifiedForTurn = false;
                }
                changed = false;
            }
        }
    }
}
