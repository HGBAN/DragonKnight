package dragonknight.patch;

import static dragonknight.DragonKnightMod.*;

import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.events.city.TheLibrary;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.localization.UIStrings;

import dragonknight.DragonKnightMod;

public class CardPatch {
    // @SpirePatch(clz = TheLibrary.class, method = "buttonEffect")
    public static class TestPatch {
        @SpireInsertPatch(loc = 87, localvars = { "card" })
        public static void Insert(TheLibrary _instance, AbstractCard card) {
            logger.info(card.name);
        }
    }

    @SpirePatch(clz = AbstractCard.class, method = SpirePatch.CLASS)
    public static class Field {
        public static SpireField<String> tempDescription = new SpireField<>(() -> null);
    }

    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(makeID("Period"));

    public static void addKeywordText(AbstractCard card) {
        String period = " " + uiStrings.TEXT[0];
        if (card.hasTag(DragonKnightMod.Enums.ANTI_BRAND)) {
            card.rawDescription += " NL dragonknight:" + DragonKnightMod.keywords.get("AntiBrand").PROPER_NAME + period;
        }
        if (card.hasTag(DragonKnightMod.Enums.BRAND) || card.hasTag(DragonKnightMod.Enums.BRAND2)) {
            if (card.hasTag(DragonKnightMod.Enums.TEMP_BRAND)) {
                card.rawDescription += " NL dragonknight:" + DragonKnightMod.keywords.get("TempBrand").PROPER_NAME
                        + period;
            } else {
                card.rawDescription += " NL dragonknight:" + DragonKnightMod.keywords.get("Brand").PROPER_NAME + period;
            }
        }
        if (card.hasTag(DragonKnightMod.Enums.EXHAUST)) {
            card.rawDescription += " NL " + GameDictionary.EXHAUST.NAMES[0] + period;
        }
        if (card.hasTag(DragonKnightMod.Enums.ETHEREAL)) {
            card.rawDescription += " NL " + GameDictionary.ETHEREAL.NAMES[0] + period;
        }
        if (card.hasTag(DragonKnightMod.Enums.BE_DRAGON)) {
            card.rawDescription += " NL dragonknight:" + DragonKnightMod.keywords.get("BeDragon").PROPER_NAME + period;
        }
    }

    @SpirePatch(clz = AbstractCard.class, method = "initializeDescription")
    public static class DescriptionPatch {

        public static void Prefix(AbstractCard _instance) {
            Field.tempDescription.set(_instance, _instance.rawDescription);

            addKeywordText(_instance);
        }

        @SpireInsertPatch(loc = 587)
        public static void Insert(AbstractCard _instance) {
            _instance.rawDescription = Field.tempDescription.get(_instance);
        }
    }

    @SpirePatch(clz = AbstractCard.class, method = "initializeDescriptionCN")
    public static class DescriptionCNPatch {

        @SpireInsertPatch(loc = 776)
        public static void Insert(AbstractCard _instance) {
            _instance.rawDescription = Field.tempDescription.get(_instance);
        }
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
