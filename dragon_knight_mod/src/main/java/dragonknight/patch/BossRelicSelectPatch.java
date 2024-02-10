package dragonknight.patch;

import static dragonknight.DragonKnightMod.*;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.screens.select.BossRelicSelectScreen;
import com.megacrit.cardcrawl.vfx.FloatyEffect;

@SpirePatch(clz = BossRelicSelectScreen.class, method = "relicObtainLogic")
public class BossRelicSelectPatch {
    public static void Postfix(BossRelicSelectScreen _instance, AbstractRelic r) {
        if (r.relicId.equals(makeID("AbyssDragon"))) {
            r.instantObtain(AbstractDungeon.player, 0, true);
            (AbstractDungeon.getCurrRoom()).rewardPopOutTimer = 0.25F;
        }
        if (r.relicId.equals(makeID("IceDevilsHeart"))) {
            r.instantObtain(AbstractDungeon.player, 0, true);
            (AbstractDungeon.getCurrRoom()).rewardPopOutTimer = 0.25F;
        }
    }
}

@SpirePatch(clz = AbstractRelic.class, method = "bossObtainLogic")
class BossObtainPatch {
    public static SpireReturn<Void> Prefix(AbstractRelic _instance, FloatyEffect ___f_effect) {
        if (_instance.relicId.equals(makeID("AbyssDragon"))) {
            _instance.isObtained = true;
            ___f_effect.x = 0.0F;
            ___f_effect.y = 0.0F;
            return SpireReturn.Return();
        }
        if (_instance.relicId.equals(makeID("IceDevilsHeart"))) {
            _instance.isObtained = true;
            ___f_effect.x = 0.0F;
            ___f_effect.y = 0.0F;
            return SpireReturn.Return();
        }
        return SpireReturn.Continue();
    }
}