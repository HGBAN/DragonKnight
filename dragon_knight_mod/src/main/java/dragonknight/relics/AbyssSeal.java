package dragonknight.relics;

import static dragonknight.DragonKnightMod.makeID;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import dragonknight.character.DragonPrince;
import dragonknight.powers.BlackDragon;
import dragonknight.powers.WhiteDragon;

public class AbyssSeal extends BaseRelic {
    public static final String ID = makeID("AbyssSeal");

    public AbyssSeal() {
        super(ID, "AbyssSeal", RelicTier.STARTER, LandingSound.MAGICAL);
        pool = DragonPrince.Enums.CARD_COLOR;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void atBattleStart() {
        this.flash();

        int r = AbstractDungeon.cardRng.random.nextInt(2);
        AbstractPlayer player = AbstractDungeon.player;
        if (r == 0) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new BlackDragon(player), 0));
        } else {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new WhiteDragon(player), 0));
        }

        this.grayscale = true;
    }

    @Override
    public void justEnteredRoom(AbstractRoom room) {
        this.grayscale = false;
    }
}
