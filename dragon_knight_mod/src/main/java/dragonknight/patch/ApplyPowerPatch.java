package dragonknight.patch;

import static dragonknight.DragonKnightMod.*;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.powers.AbstractPower;

import dragonknight.DragonKnightMod;
import dragonknight.powers.BarrierPower;

public class ApplyPowerPatch {

    @SpirePatch(clz = ApplyPowerAction.class, method = "update")
    public static class useCardPatch {
        @SpireInsertPatch(loc = 213)
        public static void Insert(ApplyPowerAction _instance, AbstractPower ___powerToApply) {
            if (_instance.target.hasPower(makeID("BarrierPower"))) {
                BarrierPower barrierPower = (BarrierPower) _instance.target.getPower(makeID("BarrierPower"));
                barrierPower.onAddPower(___powerToApply);
            }
            if (!_instance.target.isPlayer) {
                if (_instance.amount > 0) {
                    DragonKnightMod.enemyPowerCountThisTurn += _instance.amount;
                }
            }
        }
    }
}
