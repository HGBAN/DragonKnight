package dragonknight.patch;

import static dragonknight.DragonKnightMod.*;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import dragonknight.DragonKnightMod;
import dragonknight.powers.AbyssalBeastFormPower;
import dragonknight.powers.BarrierPower;
import dragonknight.relics.DivineFlameRing;

public class ApplyPowerPatch {
    private static boolean added = false;

    @SpirePatch(clz = ApplyPowerAction.class, method = "update")
    public static class updatePatch {
        @SpireInsertPatch(loc = 213)
        public static void Insert(ApplyPowerAction _instance, AbstractPower ___powerToApply) {
            if (_instance.target.hasPower(makeID("BarrierPower"))) {
                BarrierPower barrierPower = (BarrierPower) _instance.target.getPower(makeID("BarrierPower"));
                barrierPower.onAddPower(___powerToApply);
            }
            if (!_instance.target.isPlayer && AbstractDungeon.player.hasRelic(DivineFlameRing.ID)) {
                DivineFlameRing divineFlameRing = (DivineFlameRing) AbstractDungeon.player.getRelic(DivineFlameRing.ID);
                divineFlameRing.onAddPower();
            }
            if (!_instance.target.isPlayer) {
                if (_instance.amount > 0) {
                    DragonKnightMod.enemyPowerCountThisTurn += _instance.amount;
                }
            }

            if (_instance.amount > 0) {
                AbstractPlayer player = AbstractDungeon.player;
                if (player.hasPower(AbyssalBeastFormPower.POWER_ID)) {
                    if (___powerToApply.ID.equals("Dexterity")) {
                        if (!added) {
                            player.getPower(AbyssalBeastFormPower.POWER_ID).flash();
                            AbstractDungeon.actionManager
                                    .addToBottom(new ApplyPowerAction(player, player,
                                            new StrengthPower(player, _instance.amount)));
                            added = true;
                        } else {
                            added = false;
                        }
                    } else if (___powerToApply.ID.equals("Strength")) {
                        if (!added) {
                            player.getPower(AbyssalBeastFormPower.POWER_ID).flash();
                            AbstractDungeon.actionManager
                                    .addToBottom(new ApplyPowerAction(player, player,
                                            new DexterityPower(player, _instance.amount)));
                            added = true;
                        } else {
                            added = false;
                        }
                    } else if (___powerToApply.ID.equals("Flex")) {
                        AbstractDungeon.actionManager
                                .addToBottom(new ApplyPowerAction(player, player,
                                        new DexterityPower(player, -_instance.amount)));
                    } else if (___powerToApply.ID.equals("DexLoss")) {
                        AbstractDungeon.actionManager
                                .addToBottom(new ApplyPowerAction(player, player,
                                        new StrengthPower(player, -_instance.amount)));
                    }
                }
            }
        }
    }
}
