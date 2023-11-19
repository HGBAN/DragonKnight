package dragonknight.powers;

import static dragonknight.DragonKnightMod.makeID;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import dragonknight.DragonKnightMod;
import dragonknight.actions.CopyCradInHandAction;

public class WhiteDragon extends AbstractPower {
    public static final String POWER_ID = makeID("WhiteDragon");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public WhiteDragon(AbstractCreature owner) {
        // com.megacrit.cardcrawl.powers.AngryPower
        // logger.info(owner == null ? "null" : "exist");
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = -1;
        this.type = PowerType.BUFF;
        this.isTurnBased = true;
        this.description = DESCRIPTIONS[0];

        loadRegion("anger");

        // ApplyPowerAction
        // addToBot(new Hand);
        // import com.megacrit.cardcrawl.actions.common.DamageAction
    }

    // @Override
    // public void stackPower(int stackAmount) {
    // logger.info("stack");
    // }

    @Override
    public void atStartOfTurn() {
        addToBot(new RemoveSpecificPowerAction(owner, owner, this));
    }

    @Override
    public void onInitialApplication() {
        ArrayList<AbstractPower> powers = AbstractDungeon.player.powers;
        List<AbstractPower> blackPower = powers.stream()
                .filter(power -> power.ID.equals(makeID("BlackDragon")))
                .collect(Collectors.toList());
        if (blackPower.size() > 0) {
            addToBot(new RemoveSpecificPowerAction(owner, owner, this));
            return;
        }
        addToBot(new SelectCardsInHandAction(1, "复制", (card) -> !card.hasTag(DragonKnightMod.Enums.BRAND), (cards) -> {
            for (AbstractCard card : cards) {
                AbstractCard newCard = card.makeSameInstanceOf();

                newCard.rawDescription += " NL 烙印";
                newCard.initializeDescription();
                newCard.tags.add(DragonKnightMod.Enums.BRAND);
                addToBot(new CopyCradInHandAction(newCard));
            }
        }));

        List<AbstractPower> whiteBrandPower = powers.stream().filter(power -> power.ID.equals(makeID("WhiteBrandPower")))
                .collect(Collectors.toList());
        for (AbstractPower power : whiteBrandPower) {
            addToBot(new ApplyPowerAction(owner, owner, new StrengthPower(owner, power.amount)));
        }
        // com.megacrit.cardcrawl.actions.utility.
    }

    // @Override
    // public void onApplyPower(AbstractPower power, AbstractCreature target,
    // AbstractCreature source) {

    // }

    // @Override
    // public void duringTurn() {
    // logger.info("during turn");
    // }
}
