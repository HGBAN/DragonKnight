package dragonknight.patch;

import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.GameDictionary;

import dragonknight.DragonKnightMod;

public class CardPatch {
    @SpirePatch(clz = AbstractCard.class, method = SpirePatch.CLASS)
    public static class Field {
        public static SpireField<String> tempDescription = new SpireField<>(() -> null);
    }

    // private static String temp = "";
    @SpirePatch(clz = AbstractCard.class, method = "initializeDescription")
    public static class DescriptionPatch {
        // private static ConcurrentHashMap<AbstractCard, String> temp = new
        // ConcurrentHashMap<>();

        public static void Prefix(AbstractCard _instance) {
            // // temp = _instance.rawDescription;
            // if (_instance.rawDescription == null){
            // // _instance.rawDescription="";
            // logger.info("null");
            // return;
            // }
            // logger.info(_instance.name);
            // for (AbstractCard card : temp.keySet()) {
            // logger.info(card.name);
            // }
            // logger.info("--------------");
            // synchronized (_instance) {
            // while (temp.containsKey(_instance)) {
            // try {
            // _instance.wait();
            // } catch (InterruptedException e) {
            // e.printStackTrace();
            // }
            // }
            // temp.put(_instance, _instance.rawDescription);
            // }
            String temp = Field.tempDescription.get(_instance);
            if (temp != null) {
                _instance.rawDescription = temp;
            }
            Field.tempDescription.set(_instance, _instance.rawDescription);

            // logger.info(_instance.name);

            if (_instance.hasTag(DragonKnightMod.Enums.ANTI_BRAND)) {
                _instance.rawDescription += " NL dragonknight:" + DragonKnightMod.keywords.get("AntiBrand").PROPER_NAME;
            }
            if (_instance.hasTag(DragonKnightMod.Enums.BRAND) || _instance.hasTag(DragonKnightMod.Enums.BRAND2)) {
                _instance.rawDescription += " NL dragonknight:" + DragonKnightMod.keywords.get("Brand").PROPER_NAME;
            }
            if (_instance.hasTag(DragonKnightMod.Enums.EXHAUST)) {
                _instance.rawDescription += " NL " + GameDictionary.EXHAUST.NAMES[0];
            }
            if (_instance.hasTag(DragonKnightMod.Enums.ETHEREAL)) {
                _instance.rawDescription += " NL " + GameDictionary.ETHEREAL.NAMES[0];
            }
            if (_instance.hasTag(DragonKnightMod.Enums.TEMP_BRAND)) {
                _instance.rawDescription += " NL dragonknight:" + DragonKnightMod.keywords.get("TempBrand").PROPER_NAME;
            }
            if (_instance.hasTag(DragonKnightMod.Enums.BE_DRAGON)) {
                _instance.rawDescription += " NL dragonknight:" + DragonKnightMod.keywords.get("BeDragon").PROPER_NAME;
            }
        }

        // public static void Postfix(AbstractCard _instance) {
        //     _instance.rawDescription = Field.tempDescription.get(_instance);
        //     // if (!temp.contains(_instance))
        //     // return;
        //     // _instance.rawDescription = temp.get(_instance);

        //     // logger.info("fdf");
        //     // synchronized (_instance) {
        //     // temp.remove(_instance);
        //     // _instance.notifyAll();
        //     // }
        // }
    }

    @SpirePatch(clz = AbstractCard.class, method = "makeStatEquivalentCopy")
    public static class CopyCardPatch {
        public static AbstractCard Postfix(AbstractCard __result, AbstractCard __instance) {
            if (__instance.hasTag(DragonKnightMod.Enums.ANTI_BRAND)) {
                __result.tags.add(DragonKnightMod.Enums.ANTI_BRAND);
                __result.initializeDescription();
            }
            return __result;
        }
    }
}
