package dragonknight.events;

import static dragonknight.DragonKnightMod.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.curses.CurseOfTheBell;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;

import basemod.abstracts.events.PhasedEvent;
import basemod.abstracts.events.phases.TextPhase;

public class AbyssGiveaway extends PhasedEvent {
    public static final String ID = makeID("AbyssGiveaway");
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    private static final String title = eventStrings.NAME;

    public AbyssGiveaway() {
        super(ID, title, null);
        registerPhase("Preface", new TextPhase(DESCRIPTIONS[0]).addOption(OPTIONS[0], (i) -> transitionKey("Choose")));
        registerPhase("Choose", new TextPhase(DESCRIPTIONS[1]).addOption(OPTIONS[1], (i) -> handle())
                .addOption(OPTIONS[2], (i) -> transitionKey("Leave")));
        registerPhase("Good", new TextPhase(DESCRIPTIONS[2])
                .addOption(OPTIONS[2], (i) -> openMap()));
        registerPhase("Bad", new TextPhase(DESCRIPTIONS[3])
                .addOption(OPTIONS[2], (i) -> openMap()));
        registerPhase("Leave", new TextPhase(DESCRIPTIONS[4]).addOption(OPTIONS[2], (i) -> openMap()));

        transitionKey("Preface");
    }

    public static String[] relicList = { "BrandedCyclone", "BrandFate", "BrandsCall", "KingsInsight", "DivineFlameRing",
            "AbyssalSpectator" };

    private void handle() {
        int r = AbstractDungeon.miscRng.random(1, 4);
        switch (r) {
            case 1:
                List<String> relics = new ArrayList<String>();
                for (String n : relicList) {
                    if (!AbstractDungeon.player.hasRelic(makeID(n))) {
                        relics.add(n);
                    }
                }

                String relic;
                if (relics.size() > 0)
                    relic = makeID(relics.get(AbstractDungeon.miscRng.random(relics.size() - 1)));
                else
                    relic = "Circlet";

                AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F,
                        RelicLibrary
                                .getRelic(relic).makeCopy());
                transitionKey("Good");
                break;
            case 2:
                // BaseMod.getCustomCardsToAdd
                AbstractCard c = AbstractDungeon.rareCardPool.getRandomCard(true).makeCopy();
                AbstractDungeon.effectList
                        .add(new ShowCardAndObtainEffect(c, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
                transitionKey("Good");
                break;
            case 3:
                CardGroup group = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
                CurseOfTheBell curseOfTheBell = new CurseOfTheBell();
                UnlockTracker.markCardAsSeen(((AbstractCard) curseOfTheBell).cardID);
                group.addToBottom(curseOfTheBell.makeCopy());

                AbstractDungeon.gridSelectScreen.openConfirmationGrid(group, DESCRIPTIONS[5]);
                CardCrawlGame.sound.playA("BELL", MathUtils.random(-0.2F, -0.3F));
                transitionKey("Bad");
                break;
            case 4:
                getRandomFace();
                transitionKey("Bad");
                break;
            default:
                transitionKey("Leave");
                break;
        }
    }

    private void getRandomFace() {
        ArrayList<String> ids = new ArrayList<>();
        if (!AbstractDungeon.player.hasRelic("CultistMask")) {
            ids.add("CultistMask");
        }
        if (!AbstractDungeon.player.hasRelic("GremlinMask")) {
            ids.add("GremlinMask");
        }
        if (!AbstractDungeon.player.hasRelic("NlothsMask")) {
            ids.add("NlothsMask");
        }
        if (ids.size() <= 0) {
            ids.add("Circlet");
        }

        Collections.shuffle(ids, new Random(AbstractDungeon.miscRng.randomLong()));
        AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F,
        RelicLibrary
                .getRelic(ids.get(0)).makeCopy());
    }
}
