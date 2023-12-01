package dragonknight.patch;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;

import dragonknight.DragonKnightMod;

@SpirePatch(clz = AbstractCard.class, method = "initializeDescription")
public class CardPatch {
    private static String temp = "";

    public static void Prefix(AbstractCard _instance) {
        temp = _instance.rawDescription;
        if (_instance.hasTag(DragonKnightMod.Enums.ANTI_BRAND)) {
            _instance.rawDescription += " NL dragonknight:" + DragonKnightMod.keywords.get("AntiBrand").PROPER_NAME;
        }
    }

    public static void Postfix(AbstractCard _instance) {
        _instance.rawDescription = temp;
    }
}

@SpirePatch(clz = AbstractCard.class, method = "makeStatEquivalentCopy")
class CardPatch2 {
    public static AbstractCard Postfix(AbstractCard __result, AbstractCard __instance) {
        if(__instance.hasTag(DragonKnightMod.Enums.ANTI_BRAND)){
            __result.tags.add(DragonKnightMod.Enums.ANTI_BRAND);
            __result.initializeDescription();
        }
        return __result;
    }
}