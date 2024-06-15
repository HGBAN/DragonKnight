package dragonknight.patch;

import static dragonknight.DragonKnightMod.*;

import java.util.WeakHashMap;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.city.TheLibrary;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import dragonknight.DragonKnightMod;
import dragonknight.powers.AbyssalBeastFormPower;
import dragonknight.powers.BrandsCallPower;
import dragonknight.powers.DevouringBrandPower;
import dragonknight.powers.HeavenlyLinkPower;
import dragonknight.powers.IceDevilsHeartPower;
import dragonknight.powers.OriasBrandPower;

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
        public static SpireField<AbstractCard> masterCard = new SpireField<>(() -> null);
        public static SpireField<Boolean> reduceEnergy = new SpireField<>(() -> false);
    }

    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(makeID("Description"));

    public static void addKeywordText(AbstractCard card) {
        String period = uiStrings.TEXT[0];
        if (card.hasTag(DragonKnightMod.Enums.DRAW_CARD)) {
            card.rawDescription += " NL " + uiStrings.TEXT[1];
        }
        if (card.hasTag(DragonKnightMod.Enums.BLACK_DRAGON)) {
            card.rawDescription += " NL " + DragonKnightMod.cardNameKeywords.TEXT_DICT.get("BlackDragon");
        }
        if (card.hasTag(DragonKnightMod.Enums.ANTI_BRAND)) {
            card.rawDescription += " NL " + toUpper(DragonKnightMod.keywords.get("AntiBrand").NAMES[0]) + period;
        }
        if (card.hasTag(DragonKnightMod.Enums.BRAND) || card.hasTag(DragonKnightMod.Enums.BRAND2)) {
            if (card.hasTag(DragonKnightMod.Enums.TEMP_BRAND)) {
                card.rawDescription += " NL " + toUpper(DragonKnightMod.keywords.get("TempBrand").NAMES[1])
                        + period;
            } else {
                card.rawDescription += " NL " + toUpper(DragonKnightMod.keywords.get("Brand").NAMES[0]) + period;
            }
        }
        if (card.hasTag(DragonKnightMod.Enums.EXHAUST)) {
            card.rawDescription += " NL " + toUpper(GameDictionary.EXHAUST.NAMES[0], false) + period;
        }
        if (card.hasTag(DragonKnightMod.Enums.ETHEREAL)) {
            card.rawDescription += " NL " + toUpper(GameDictionary.ETHEREAL.NAMES[0], false) + period;
        }
        if (card.hasTag(DragonKnightMod.Enums.BE_DRAGON)) {
            card.rawDescription += " NL " + toUpper(DragonKnightMod.keywords.get("BeDragon").NAMES[1]) + period;
        }
    }

    private static String toUpper(String str) {
        return toUpper(str, true);
    }

    private static String toUpper(String str, boolean ignoreFirst) {
        String[] words = str.split("[_:]"); // 使用正则表达式分割字符串为单词数组
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < words.length; i++) {
            String word = words[i];
            if (!word.isEmpty()) { // 确保单词非空
                if (i == 0 && ignoreFirst)
                    result.append(word.charAt(0));
                else
                    result.append(Character.toUpperCase(word.charAt(0))); // 将首字母大写
                if (word.length() > 1) {
                    result.append(word.substring(1)); // 添加单词剩余部分
                }
                if (i < words.length - 1) {
                    result.append(str.charAt(str.indexOf(word) + word.length())); // 添加分隔符
                }
            }
        }

        return result.toString();
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

    private static boolean rec = false;

    @SpirePatch(clz = AbstractCard.class, method = "renderEnergy")
    public static class renderEnergyPatch {
        private static int change = 0;

        @SpireInsertPatch(loc = 2725, localvars = { "costColor" })
        public static void Insert(AbstractCard _instance, SpriteBatch sb, @ByRef Color[] costColor) {
            if (Field.reduceEnergy.get(_instance)) {
                costColor[0] = new Color(0x66cc00ff);
            }
        }

        // 显示减费的能力
        public static void Prefix(AbstractCard _instance, SpriteBatch sb) {
            AbstractPlayer player = AbstractDungeon.player;
            if (player != null) {
                if (!AbstractDungeon.getCurrRoom().isBattleOver
                        && _instance.costForTurn > 0) {
                    int tmp = _instance.costForTurn;
                    if (player.hasPower(makeID("TrueEyePower"))) {
                        _instance.setCostForTurn(_instance.costForTurn - 1);
                        rec = true;
                    }
                    if (player.hasPower(BrandsCallPower.POWER_ID) && isBrandCard(_instance)) {
                        _instance.setCostForTurn(_instance.costForTurn - 1);
                        rec = true;
                    }
                    if (player.hasPower(IceDevilsHeartPower.POWER_ID)
                            && _instance.hasTag(DragonKnightMod.Enums.ANTI_BRAND)) {
                        _instance.setCostForTurn(_instance.costForTurn - 1);
                        rec = true;
                    }
                    if (player.hasPower(HeavenlyLinkPower.POWER_ID)
                            && _instance.name.contains(DragonKnightMod.cardNameKeywords.TEXT_DICT.get("Heavenly"))) {
                        _instance.setCostForTurn(_instance.costForTurn - 1);
                        rec = true;
                    }
                    if (player.hasPower(makeID("DevouringBrandPower"))) {
                        if (((DevouringBrandPower) player.getPower(makeID("DevouringBrandPower"))).existInExhaustPile
                                .contains(_instance.cardID)) {
                            _instance.setCostForTurn(_instance.costForTurn - 1);
                        }
                        rec = true;
                    }
                    change += tmp - _instance.costForTurn;
                }
            }
        }

        public static void Postfix(AbstractCard _instance, SpriteBatch sb) {
            if (change > 0) {
                _instance.setCostForTurn(_instance.costForTurn + change);
                if (_instance.costForTurn == _instance.cost) {
                    _instance.isCostModifiedForTurn = false;
                }
                change = 0;
                rec = false;
            }
        }
    }

    @SpirePatch(clz = AbstractCard.class, method = "hasEnoughEnergy")
    public static class hasEnoughEnergyPatch {
        private static int change = 0;

        // 减费的判断
        public static void Prefix(AbstractCard _instance) {
            AbstractPlayer player = AbstractDungeon.player;
            if (player != null) {
                if (!AbstractDungeon.getCurrRoom().isBattleOver
                        && _instance.costForTurn > 0 && !rec) {
                    int tmp = _instance.costForTurn;
                    if (player.hasPower(makeID("TrueEyePower"))) {
                        _instance.setCostForTurn(_instance.costForTurn - 1);
                    }
                    if (player.hasPower(BrandsCallPower.POWER_ID) && isBrandCard(_instance)) {
                        _instance.setCostForTurn(_instance.costForTurn - 1);
                    }
                    if (player.hasPower(IceDevilsHeartPower.POWER_ID)
                            && _instance.hasTag(DragonKnightMod.Enums.ANTI_BRAND)) {
                        _instance.setCostForTurn(_instance.costForTurn - 1);
                    }
                    if (player.hasPower(HeavenlyLinkPower.POWER_ID)
                            && _instance.name.contains(DragonKnightMod.cardNameKeywords.TEXT_DICT.get("Heavenly"))) {
                        _instance.setCostForTurn(_instance.costForTurn - 1);
                    }
                    if (player.hasPower(makeID("DevouringBrandPower"))) {
                        if (((DevouringBrandPower) player.getPower(makeID("DevouringBrandPower"))).existInExhaustPile
                                .contains(_instance.cardID)) {
                            _instance.setCostForTurn(_instance.costForTurn - 1);
                        }
                    }
                    change += tmp - _instance.costForTurn;
                }
            }
        }

        public static void Postfix(AbstractCard _instance) {
            if (change > 0) {
                _instance.setCostForTurn(_instance.costForTurn + change);
                change = 0;
            }
        }
    }

    public static WeakHashMap<AbstractCard, Integer> randoms = new WeakHashMap<>();

    private static int getRandom(AbstractCard c) {
        if (!randoms.containsKey(c))
            randoms.put(c, AbstractDungeon.cardRng.random(7, 14));
        return randoms.get(c);
    }

    @SpirePatch(clz = AbstractCard.class, method = "calculateCardDamage")
    public static class DamagePatch {
        @SpireInsertPatch(loc = 3235, localvars = { "tmp" })
        public static void Insert(AbstractCard _instance, AbstractMonster mo, @ByRef float[] tmp) {
            AbstractPlayer player = AbstractDungeon.player;
            if (player != null && _instance.type.equals(CardType.ATTACK)) {
                if (player.hasPower(AbyssalBeastFormPower.POWER_ID) && !AbstractDungeon.getCurrRoom().isBattleOver) {
                    tmp[0] += getRandom(_instance);
                }
                if (player.hasPower(OriasBrandPower.POWER_ID) && !AbstractDungeon.getCurrRoom().isBattleOver) {
                    tmp[0] /= 2;
                }
            }
        }

        @SpireInsertPatch(loc = 3291, localvars = { "tmp" })
        public static void Insert2(AbstractCard _instance, AbstractMonster mo, float[] tmp) {
            AbstractPlayer player = AbstractDungeon.player;
            if (player != null && _instance.type.equals(CardType.ATTACK)) {
                if (player.hasPower(AbyssalBeastFormPower.POWER_ID) && !AbstractDungeon.getCurrRoom().isBattleOver) {
                    for (int i = 0; i < tmp.length; i++) {
                        tmp[i] += getRandom(_instance);
                    }
                }
                if (player.hasPower(OriasBrandPower.POWER_ID) && !AbstractDungeon.getCurrRoom().isBattleOver) {
                    for (int i = 0; i < tmp.length; i++) {
                        tmp[i] /= 2;
                    }
                }
            }
        }

        // public static void Postfix(AbstractCard _instance, AbstractMonster mo) {
        // AbstractPlayer player = AbstractDungeon.player;
        // if (player != null) {
        // if (player.hasPower(OriasBrandPower.NAME) &&
        // !AbstractDungeon.getCurrRoom().isBattleOver) {
        // _instance.damage /= 2;
        // }
        // }
        // }
    }

    @SpirePatch(clz = AbstractCard.class, method = "applyPowers")
    public static class ApplyPowersPatch {

        @SpireInsertPatch(loc = 3106, localvars = { "tmp" })
        public static void Insert(AbstractCard _instance, @ByRef float[] tmp) {
            AbstractPlayer player = AbstractDungeon.player;
            if (player != null && _instance.type.equals(CardType.ATTACK)) {
                if (player.hasPower(AbyssalBeastFormPower.POWER_ID) && !AbstractDungeon.getCurrRoom().isBattleOver) {
                    tmp[0] += getRandom(_instance);
                }
            }
        }

        @SpireInsertPatch(loc = 3152, localvars = { "tmp" })
        public static void Insert2(AbstractCard _instance, float[] tmp) {
            AbstractPlayer player = AbstractDungeon.player;
            if (player != null && _instance.type.equals(CardType.ATTACK)) {
                if (player.hasPower(AbyssalBeastFormPower.POWER_ID) && !AbstractDungeon.getCurrRoom().isBattleOver) {
                    for (int i = 0; i < tmp.length; i++) {
                        tmp[i] += getRandom(_instance);
                    }
                }
            }
        }
    }
}
