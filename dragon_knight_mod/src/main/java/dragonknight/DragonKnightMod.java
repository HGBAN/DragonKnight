package dragonknight;

import basemod.AutoAdd;
import basemod.BaseMod;
import basemod.devcommands.ConsoleCommand;
import basemod.helpers.RelicType;
import basemod.interfaces.EditCardsSubscriber;
import basemod.interfaces.EditCharactersSubscriber;
import basemod.interfaces.EditKeywordsSubscriber;
import basemod.interfaces.EditRelicsSubscriber;
import basemod.interfaces.EditStringsSubscriber;
import basemod.interfaces.OnCardUseSubscriber;
import basemod.interfaces.OnStartBattleSubscriber;
import basemod.interfaces.PostInitializeSubscriber;
import dragonknight.util.GeneralUtils;
import dragonknight.util.KeywordInfo;
import dragonknight.util.TextureLoader;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.ModInfo;
import com.evacipated.cardcrawl.modthespire.Patcher;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTags;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.scannotation.AnnotationDB;

import java.nio.charset.StandardCharsets;
import java.util.*;

import dragonknight.character.DragonPrince;
import dragonknight.commands.BrandCommand;
import dragonknight.commands.ExhaustHand;
import dragonknight.potions.BrandPotion;
import dragonknight.powers.AbyssFormPower;
import dragonknight.powers.BlackDragon;
import dragonknight.powers.Brand;
import dragonknight.powers.WhiteDragon;
import dragonknight.powers.WhiteRealmPower;
import dragonknight.relics.BaseRelic;
import dragonknight.screens.SelectDragonScreen;

@SpireInitializer
public class DragonKnightMod implements
        EditStringsSubscriber,
        EditKeywordsSubscriber,
        PostInitializeSubscriber,
        EditCharactersSubscriber,
        EditCardsSubscriber,
        OnCardUseSubscriber,
        EditRelicsSubscriber,
        OnStartBattleSubscriber {
    public static ModInfo info;
    public static String modID; // Edit your pom.xml to change this
    static {
        loadModInfo();
    }
    public static final Logger logger = LogManager.getLogger(modID); // Used to output to the console.
    private static final String resourcesFolder = "dragonknight";

    private static final String BG_ATTACK = characterPath("dragonprince/cardback/bg_attack.png");
    private static final String BG_ATTACK_P = characterPath("dragonprince/cardback/bg_attack_p.png");
    private static final String BG_SKILL = characterPath("dragonprince/cardback/bg_skill.png");
    private static final String BG_SKILL_P = characterPath("dragonprince/cardback/bg_skill_p.png");
    private static final String BG_POWER = characterPath("dragonprince/cardback/bg_power.png");
    private static final String BG_POWER_P = characterPath("dragonprince/cardback/bg_power_p.png");
    private static final String ENERGY_ORB = characterPath("dragonprince/cardback/energy_orb.png");
    private static final String ENERGY_ORB_P = characterPath("dragonprince/cardback/energy_orb_p.png");
    private static final String SMALL_ORB = characterPath("dragonprince/cardback/small_orb.png");

    private static final String CHAR_SELECT_BUTTON = characterPath("dragonprince/select/button.png");
    private static final String CHAR_SELECT_PORTRAIT = characterPath("dragonprince/select/portrait.jpg");

    // private static final Texture ABYSS_SEAL=new Texture(powerPath("BG_ATTACK"));

    // This is used to prefix the IDs of various objects like cards and relics,
    // to avoid conflicts between different mods using the same name for things.
    public static String makeID(String id) {
        return modID + ":" + id;
    }

    // This will be called by ModTheSpire because of the @SpireInitializer
    // annotation at the top of the class.
    public static void initialize() {
        new DragonKnightMod();
        // new DragonPrince();
        // logger.info(CardCrawlGame.languagePack.getCharacterString(makeID("DragonPrince")).NAMES[0]);
        // logger.info("abc:" + getLangString());
    }

    public DragonKnightMod() {
        BaseMod.subscribe(this); // This will make BaseMod trigger all the subscribers at their appropriate
                                 // times.
        logger.info(modID + " subscribed to BaseMod.");
    }

    @Override
    public void receivePostInitialize() {
        // This loads the image used as an icon in the in-game mods menu.
        Texture badgeTexture = TextureLoader.getTexture(imagePath("badge.png"));
        // Set up the mod information displayed in the in-game mods menu.
        // The information used is taken from your pom.xml file.
        BaseMod.registerModBadge(badgeTexture, info.Name, GeneralUtils.arrToString(info.Authors), info.Description,
                null);

        ConsoleCommand.addCommand("exhaust", ExhaustHand.class);
        ConsoleCommand.addCommand("brand", BrandCommand.class);
    }

    /*----------Localization----------*/

    // This is used to load the appropriate localization files based on language.
    private static String getLangString() {
        return Settings.language.name().toLowerCase();
    }

    private static final String defaultLanguage = "eng";

    public static final Map<String, KeywordInfo> keywords = new HashMap<>();

    @Override
    public void receiveEditStrings() {
        /*
         * First, load the default localization.
         * Then, if the current language is different, attempt to load localization for
         * that language.
         * This results in the default localization being used for anything that might
         * be missing.
         * The same process is used to load keywords slightly below.
         */
        loadLocalization(defaultLanguage); // no exception catching for default localization; you better have at least
                                           // one that works.
        if (!defaultLanguage.equals(getLangString())) {
            try {
                loadLocalization(getLangString());
            } catch (GdxRuntimeException e) {
                e.printStackTrace();
            }
        }
    }

    private void loadLocalization(String lang) {
        // While this does load every type of localization, most of these files are just
        // outlines so that you can see how they're formatted.
        // Feel free to comment out/delete any that you don't end up using.
        BaseMod.loadCustomStringsFile(CardStrings.class,
                localizationPath(lang, "CardStrings.json"));
        BaseMod.loadCustomStringsFile(CharacterStrings.class,
                localizationPath(lang, "CharacterStrings.json"));
        BaseMod.loadCustomStringsFile(EventStrings.class,
                localizationPath(lang, "EventStrings.json"));
        BaseMod.loadCustomStringsFile(OrbStrings.class,
                localizationPath(lang, "OrbStrings.json"));
        BaseMod.loadCustomStringsFile(PotionStrings.class,
                localizationPath(lang, "PotionStrings.json"));
        BaseMod.loadCustomStringsFile(PowerStrings.class,
                localizationPath(lang, "PowerStrings.json"));
        BaseMod.loadCustomStringsFile(RelicStrings.class,
                localizationPath(lang, "RelicStrings.json"));
        BaseMod.loadCustomStringsFile(UIStrings.class,
                localizationPath(lang, "UIStrings.json"));
    }

    @Override
    public void receiveEditKeywords() {
        Gson gson = new Gson();
        String json = Gdx.files.internal(localizationPath(defaultLanguage, "Keywords.json"))
                .readString(String.valueOf(StandardCharsets.UTF_8));
        KeywordInfo[] keywords = gson.fromJson(json, KeywordInfo[].class);
        for (KeywordInfo keyword : keywords) {
            keyword.prep();
            registerKeyword(keyword);
        }

        if (!defaultLanguage.equals(getLangString())) {
            try {
                json = Gdx.files.internal(localizationPath(getLangString(), "Keywords.json"))
                        .readString(String.valueOf(StandardCharsets.UTF_8));
                keywords = gson.fromJson(json, KeywordInfo[].class);
                for (KeywordInfo keyword : keywords) {
                    keyword.prep();
                    registerKeyword(keyword);
                }
            } catch (Exception e) {
                logger.warn(modID + " does not support " + getLangString() + " keywords.");
            }
        }
    }

    private void registerKeyword(KeywordInfo info) {
        BaseMod.addKeyword(modID.toLowerCase(), info.PROPER_NAME, info.NAMES, info.DESCRIPTION);
        if (!info.ID.isEmpty()) {
            keywords.put(info.ID, info);
        }
    }

    // These methods are used to generate the correct filepaths to various parts of
    // the resources folder.
    public static String localizationPath(String lang, String file) {
        return resourcesFolder + "/localization/" + lang + "/" + file;
    }

    public static String imagePath(String file) {
        return resourcesFolder + "/images/" + file;
    }

    public static String characterPath(String file) {
        return resourcesFolder + "/images/characters/" + file;
    }

    public static String powerPath(String file) {
        return resourcesFolder + "/images/powers/" + file;
    }

    public static String relicPath(String file) {
        return resourcesFolder + "/images/relics/" + file;
    }

    // This determines the mod's ID based on information stored by ModTheSpire.
    private static void loadModInfo() {
        Optional<ModInfo> infos = Arrays.stream(Loader.MODINFOS).filter((modInfo) -> {
            AnnotationDB annotationDB = Patcher.annotationDBMap.get(modInfo.jarURL);
            if (annotationDB == null)
                return false;
            Set<String> initializers = annotationDB.getAnnotationIndex().getOrDefault(SpireInitializer.class.getName(),
                    Collections.emptySet());
            return initializers.contains(DragonKnightMod.class.getName());
        }).findFirst();
        if (infos.isPresent()) {
            info = infos.get();
            modID = info.ID;
        } else {
            throw new RuntimeException("Failed to determine mod info/ID based on initializer.");
        }
    }

    @Override
    public void receiveEditCharacters() {
        BaseMod.addCharacter(new DragonPrince(),
                CHAR_SELECT_BUTTON, CHAR_SELECT_PORTRAIT, DragonPrince.Enums.DRAGON_PRINCE);

        BaseMod.addCustomScreen(new SelectDragonScreen());
    }

    @Override
    public void receiveEditCards() {
        BaseMod.addColor(DragonPrince.Enums.CARD_COLOR, DragonPrince.CARD_COLOR,
                BG_ATTACK, BG_SKILL, BG_POWER, ENERGY_ORB,
                BG_ATTACK_P, BG_SKILL_P, BG_POWER_P, ENERGY_ORB_P,
                SMALL_ORB);

        BaseMod.addKeyword(new String[] { "烙印" }, "打出时，随机消耗抽牌堆中的一张牌，并在回合结束时将其打出");
        BaseMod.addKeyword(new String[] { "化龙" }, "选择一种形态进行变换");

        BaseMod.addPower(BlackDragon.class, BlackDragon.POWER_ID);
        BaseMod.addPower(WhiteDragon.class, WhiteDragon.POWER_ID);
        BaseMod.addPower(Brand.class, Brand.POWER_ID);
        BaseMod.addPower(AbyssFormPower.class, AbyssFormPower.POWER_ID);
        BaseMod.addPower(WhiteRealmPower.class, WhiteRealmPower.POWER_ID);

        BaseMod.addPotion(BrandPotion.class, Color.BROWN, Color.CYAN, Color.BLUE, BrandPotion.ID);
        new AutoAdd(modID)
                .packageFilter("dragonknight.cards")
                .setDefaultSeen(true)
                .cards();
    }

    public static ArrayList<AbstractCard> brandCards = new ArrayList<>();
    public static int brandCount = 0;

    public static class Enums {
        @SpireEnum
        public static CardTags BRAND;
    }

    @Override
    public void receiveCardUsed(AbstractCard card) {
        if (card.hasTag(Enums.BRAND)) {
            AbstractPlayer player = AbstractDungeon.player;

            if (!player.drawPile.isEmpty()) {
                AbstractCard brandCard = player.drawPile.getRandomCard(true);
                logger.info(brandCard.cardID);
                brandCards.add(brandCard);
                brandCount++;
                AbstractDungeon.actionManager
                        .addToBottom(new ExhaustSpecificCardAction(brandCard, player.drawPile));
                AbstractDungeon.actionManager
                        .addToBottom(new ApplyPowerAction(player, player, new Brand(player)));
            }
        }
        // if (card.hasTag(Enums.BE_DRAGON)) {
        // BaseMod.openCustomScreen(SelectDragonScreen.Enum.SELECT_DRAGON_SCREEN, p);
        // }
    }

    @Override
    public void receiveEditRelics() {
        new AutoAdd(modID) // Loads files from this mod
                .packageFilter("dragonknight.relics") // In the same package as this class
                .any(BaseRelic.class, (info, relic) -> { // Run this code for any classes that extend this class
                    if (relic.pool != null)
                        BaseMod.addRelicToCustomPool(relic, relic.pool); // Register a custom character specific relic
                    else
                        BaseMod.addRelic(relic, RelicType.SHARED); // Register a shared or base game character specific
                                                                   // relic

                    // If the class is annotated with @AutoAdd.Seen, it will be marked as seen,
                    // making it visible in the relic library.
                    // If you want all your relics to be visible by default, just remove this if
                    // statement.
                    if (info.seen)
                        UnlockTracker.markRelicAsSeen(relic.relicId);
                });
    }

    @Override
    public void receiveOnBattleStart(AbstractRoom arg0) {
        brandCards.clear();
        Brand.triggerCount = 1;
        brandCount = 0;
    }

    // @Override
    // public boolean receivePreMonsterTurn(AbstractMonster arg0) {
    // for (AbstractCard brandCard : brandCards) {
    // if (brandCard.target.equals(CardTarget.SELF)) {
    // AbstractDungeon.actionManager
    // .addToBottom(new UseCardAction(brandCard, AbstractDungeon.player));
    // } else
    // AbstractDungeon.actionManager
    // .addToBottom(new UseCardAction(brandCard,
    // AbstractDungeon.getMonsters().getRandomMonster()));
    // }
    // brandCards.clear();
    // return true;
    // }

    public static void beDragon() {
        if (AbstractDungeon.player.powers.stream().filter((power) -> power.ID
                .equals(makeID("BlackDragon")) || power.ID.equals(makeID("WhiteDragon"))).count() == 0) {
            AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
                @Override
                public void update() {
                    BaseMod.openCustomScreen(SelectDragonScreen.Enum.SELECT_DRAGON_SCREEN);
                    isDone = true;
                }
            });
        }
    }
}
