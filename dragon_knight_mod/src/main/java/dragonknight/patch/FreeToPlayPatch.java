package dragonknight.patch;

import static dragonknight.DragonKnightMod.*;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

@SpirePatch(clz = AbstractCard.class, method = "freeToPlay")
public class FreeToPlayPatch {
    public static boolean Postfix(boolean __result, AbstractCard __instance) {
        AbstractPlayer player = AbstractDungeon.player;
        if (player != null) {
            if (player.hasPower(makeID("NextCardFreePower")) && !AbstractDungeon.getCurrRoom().isBattleOver) {
                return true;
            }
        }
        return __result;
    }
}
