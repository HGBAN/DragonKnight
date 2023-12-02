package dragonknight.ui;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase;
import com.megacrit.cardcrawl.rooms.RestRoom;
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;

import dragonknight.DragonKnightMod;

import static dragonknight.DragonKnightMod.canBrand;
import static dragonknight.DragonKnightMod.makeID;

import java.util.Iterator;

public class AntiBrandOption extends AbstractCampfireOption {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(makeID("AntiBrandOption"));
    public static final String[] TEXT = uiStrings.TEXT;

    public AntiBrandOption() {
        this.label = TEXT[0];
        this.usable = true;
        this.img = ImageMaster.CAMPFIRE_SMITH_BUTTON;
        this.description = TEXT[1];
        // SmithOption
    }

    @Override
    public void useOption() {
        if (this.usable) {
            DragonKnightMod.antiBrandSet = true;
            AbstractDungeon.gridSelectScreen.open(getNoBrandCards(), 1, TEXT[0],
                    false, false, true, false);

            this.usable = false;
            AbstractDungeon.getCurrRoom().phase = RoomPhase.COMPLETE;
            ((RestRoom) AbstractDungeon.getCurrRoom()).cutFireSound();
        }
    }

    private CardGroup getNoBrandCards() {
        CardGroup retVal = new CardGroup(com.megacrit.cardcrawl.cards.CardGroup.CardGroupType.UNSPECIFIED);

        Iterator<AbstractCard> var = AbstractDungeon.player.masterDeck.group.iterator();

        while (var.hasNext()) {
            AbstractCard c = (AbstractCard) var.next();
            if (canBrand(c)) {
                retVal.group.add(c);
            }
        }

        return retVal;
    }
}
