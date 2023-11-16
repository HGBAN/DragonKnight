package dragonknight.powers;

import static dragonknight.DragonKnightMod.makeID;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;

import basemod.BaseMod;
import dragonknight.DragonKnightMod;
import dragonknight.screens.SelectDragonScreen;

public class AbyssFormPower extends BasePower {
    public static final String POWER_ID = makeID("AbyssFormPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private boolean isUsed = false;

    public AbyssFormPower(AbstractCreature owner) {
        super(POWER_ID, PowerType.BUFF, true, owner, owner, -1);
        // this.name = NAME;
        // this.ID = POWER_ID;
        // this.owner = owner;
        // this.amount = -1;
        // this.type = PowerType.BUFF;
        // this.isTurnBased = true;
        // this.description = DESCRIPTIONS[0];

        // loadRegion("artifact");

    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    @Override
    public void atStartOfTurn() {
        isUsed = false;
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.hasTag(DragonKnightMod.Enums.BRAND) && !isUsed) {
            addToBot(new AbstractGameAction() {
                @Override
                public void update() {
                    BaseMod.openCustomScreen(SelectDragonScreen.Enum.SELECT_DRAGON_SCREEN, AbstractDungeon.player);
                    isDone = true;
                }
            });
            isUsed = true;
        }
    }

}
