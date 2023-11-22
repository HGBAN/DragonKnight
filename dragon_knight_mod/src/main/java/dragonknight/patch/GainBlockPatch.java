package dragonknight.patch;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.core.AbstractCreature;

import dragonknight.DragonKnightMod;

@SpirePatch(clz = GainBlockAction.class, method = SpirePatch.CONSTRUCTOR, paramtypez = { AbstractCreature.class,
        int.class })
public class GainBlockPatch {
    public static void Postfix(GainBlockAction _instance, AbstractCreature target, int amount) {
        if (target.isPlayer)
            DragonKnightMod.blockGainedThisTurn += amount;
    }
}

@SpirePatch(clz = GainBlockAction.class, method = SpirePatch.CONSTRUCTOR, paramtypez = { AbstractCreature.class,
        AbstractCreature.class,
        int.class })
class GainBlockPatch2 {
    public static void Postfix(GainBlockAction _instance, AbstractCreature target, AbstractCreature source,
            int amount) {
        if (target.isPlayer)
            DragonKnightMod.blockGainedThisTurn += amount;
    }
}