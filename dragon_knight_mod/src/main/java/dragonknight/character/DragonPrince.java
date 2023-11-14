package dragonknight.character;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.cards.red.Bash;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.events.beyond.SpireHeart;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ScreenShake.ShakeDur;
import com.megacrit.cardcrawl.helpers.ScreenShake.ShakeIntensity;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import basemod.abstracts.CustomEnergyOrb;
import basemod.abstracts.CustomPlayer;
import basemod.animations.AbstractAnimation;

import static dragonknight.DragonKnightMod.makeID;
import static dragonknight.DragonKnightMod.characterPath;
import static dragonknight.DragonKnightMod.logger;

public class DragonPrince extends CustomPlayer {
    // private static enum Form {
    // HUMAN,
    // BLACK,
    // WHITE
    // }

    // private Form form = Form.HUMAN;

    public DragonPrince() {
        super(NAMES[0], Enums.DRAGON_PRINCE, new CustomEnergyOrb(null, null, null), new AbstractAnimation() { // this.
            @Override
            public Type type() {
                return Type.NONE; // A NONE animation results in img being used instead.
            }
        });
        // ArrayList<String> relics = new ArrayList<>();

        // ArrayList<String> deck = new ArrayList<>();
        initializeClass(null,
                SHOULDER_2,
                SHOULDER_1,
                CORPSE, getLoadout(),
                -4.0F, -16.0F, 220.0F, 290.0F, new EnergyManager(ENERGY_PER_TURN));

        this.loadAnimation(characterPath("dragonprince/skeleton.atlas"),
                characterPath("dragonprince/skeleton.json"), 1.0F);
        AnimationState.TrackEntry e = this.state.setAnimation(0, "Idle", true);
        this.stateData.setMix("Hit", "Idle", 0.1F);
        e.setTimeScale(0.6F);
        // com.megacrit.cardcrawl.characters.Ironclad
    }

    // Stats
    public static final int ENERGY_PER_TURN = 3;
    public static final int MAX_HP = 70;
    public static final int STARTING_GOLD = 99;
    public static final int CARD_DRAW = 5;
    public static final int ORB_SLOTS = 0;

    // Strings
    private static final String ID = makeID("DragonPrince"); // This should match whatever you have in the
                                                             // CharacterStrings.json file
    private static final CharacterStrings characterStrings = CardCrawlGame.languagePack.getCharacterString(ID);
    private static final String[] NAMES = characterStrings.NAMES;
    private static final String[] TEXT = characterStrings.TEXT;

    // Image file paths
    private static final String SHOULDER_1 = characterPath("dragonprince/shoulder.png"); // Shoulder 1 and 2 are used at
                                                                                         // rest sites.
    private static final String SHOULDER_2 = characterPath("dragonprince/shoulder2.png");
    private static final String CORPSE = characterPath("dragonprince/corpse.png"); // Corpse is when you die.

    public static final Color CARD_COLOR = new Color(128f / 255f, 128f / 255f, 128f / 255f, 1f);

    public static class Enums {
        // These are used to identify your character, as well as your character's card
        // color.
        // Library color is basically the same as card color, but you need both because
        // that's how the game was made.
        @SpireEnum
        public static AbstractPlayer.PlayerClass DRAGON_PRINCE;
        @SpireEnum(name = "DARK_RED") // These two MUST match. Change it to something unique for your
                                      // character.
        public static AbstractCard.CardColor CARD_COLOR;

        @SpireEnum(name = "DARK_RED")
        @SuppressWarnings("unused")
        public static CardLibrary.LibraryType LIBRARY_COLOR;
    }

    @Override
    public void doCharSelectScreenSelectEffect() {
        CardCrawlGame.sound.playA("ATTACK_HEAVY", MathUtils.random(-0.2F, 0.2F));
        CardCrawlGame.screenShake.shake(ShakeIntensity.MED, ShakeDur.SHORT, true);
    }

    @Override
    public int getAscensionMaxHPLoss() {
        return 10;
    }

    @Override
    public CardColor getCardColor() {
        return Enums.CARD_COLOR;
    }

    @Override
    public Color getCardRenderColor() {
        return CARD_COLOR;
    }

    @Override
    public Color getCardTrailColor() {
        return CARD_COLOR;
    }

    @Override
    public String getCustomModeCharacterButtonSoundKey() {
        return "ATTACK_HEAVY";
    }

    @Override
    public BitmapFont getEnergyNumFont() {
        return FontHelper.energyNumFontPurple;
    }

    @Override
    public CharSelectInfo getLoadout() {
        return new CharSelectInfo(NAMES[0], TEXT[0], MAX_HP, MAX_HP, ORB_SLOTS, STARTING_GOLD, CARD_DRAW, this,
                getStartingRelics(), getStartingDeck(), false);
    }

    @Override
    public String getLocalizedCharacterName() {
        return NAMES[0];
    }

    @Override
    public Color getSlashAttackColor() {
        return Color.RED;
    }

    @Override
    public AttackEffect[] getSpireHeartSlashEffect() {
        return new AbstractGameAction.AttackEffect[] { AttackEffect.SLASH_HEAVY, AttackEffect.FIRE,
                AttackEffect.BLUNT_HEAVY, AttackEffect.SLASH_HEAVY, AttackEffect.FIRE, AttackEffect.BLUNT_HEAVY };
    }

    @Override
    public String getSpireHeartText() {
        return SpireHeart.DESCRIPTIONS[8];
    }

    @Override
    public AbstractCard getStartCardForEvent() {
        return new Bash();
    }

    @Override
    public ArrayList<String> getStartingDeck() {
        ArrayList<String> retVal = new ArrayList<>();
        retVal.add(makeID("Strike_DK"));
        retVal.add(makeID("Strike_DK"));
        retVal.add(makeID("Strike_DK"));
        retVal.add(makeID("Strike_DK"));
        retVal.add(makeID("Strike_DK"));
        retVal.add(makeID("Defend_DK"));
        retVal.add(makeID("Defend_DK"));
        retVal.add(makeID("Defend_DK"));
        retVal.add(makeID("Defend_DK"));
        retVal.add("Bash");
        return retVal;
    }

    @Override
    public ArrayList<String> getStartingRelics() {
        ArrayList<String> retVal = new ArrayList<>();
        retVal.add("Burning Blood");
        UnlockTracker.markRelicAsSeen("Burning Blood");
        return retVal;
    }

    @Override
    public String getTitle(PlayerClass arg0) {
        return TEXT[1];
    }

    @Override
    public String getVampireText() {
        return "";
    }

    @Override
    public AbstractPlayer newInstance() {
        return new DragonPrince();
    }

}
