package dragonknight.patch;

import static dragonknight.DragonKnightMod.makeID;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.screens.select.BossRelicSelectScreen;

@SpirePatch(clz = BossRelicSelectScreen.class, method = "relicObtainLogic")
public class BossRelicSelectPatch {
    public static void Postfix(BossRelicSelectScreen _instance, AbstractRelic r) {
        if (r.relicId.equals(makeID("AbyssDragon"))) {
            r.instantObtain(AbstractDungeon.player, 0, true);
            (AbstractDungeon.getCurrRoom()).rewardPopOutTimer = 0.25F;
        }
    }
}