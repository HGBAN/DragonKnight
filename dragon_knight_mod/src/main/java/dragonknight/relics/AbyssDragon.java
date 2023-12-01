package dragonknight.relics;

import static dragonknight.DragonKnightMod.beDragon;
import static dragonknight.DragonKnightMod.makeID;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import dragonknight.DragonKnightMod;
import dragonknight.character.DragonPrince;
import dragonknight.powers.BlackDragon;
import dragonknight.powers.WhiteDragon;

public class AbyssDragon extends BaseRelic {
    public static final String ID = makeID("AbyssDragon");

    public AbyssDragon() {
        super(ID, "AbyssDragon", RelicTier.BOSS, LandingSound.FLAT);
        pool = DragonPrince.Enums.CARD_COLOR;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void atBattleStart() {
        this.flash();

        beDragon();
    }

    @Override
    public boolean canSpawn() {
        return AbstractDungeon.player.hasRelic(makeID("AbyssSeal"));
    }

    @Override
    public void atTurnStartPostDraw() {
        this.flash();

        if (DragonKnightMod.brandCountLastTurn >= 6) {
            int r = AbstractDungeon.cardRng.random(1);
            AbstractPlayer player = AbstractDungeon.player;
            if (r == 0) {
                AbstractDungeon.actionManager
                        .addToBottom(new ApplyPowerAction(player, player, new BlackDragon(player), 0));
            } else {
                AbstractDungeon.actionManager
                        .addToBottom(new ApplyPowerAction(player, player, new WhiteDragon(player), 0));
            }
        }
    }



    // @Override
    // public void justEnteredRoom(AbstractRoom room) {
    // this.grayscale = false;
    // }

    // @Override
    // public void onVictory() {
    // this.grayscale = false;
    // }
}
