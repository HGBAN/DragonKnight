package dragonknight.powers;

import static dragonknight.DragonKnightMod.addBrandToCard;
import static dragonknight.DragonKnightMod.makeID;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.StrengthPower;

import dragonknight.DragonKnightMod;
import dragonknight.actions.CopyCardInHandAction;

public class WhiteDragon extends BeDragonPower {
    public static final String POWER_ID = makeID("WhiteDragon");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public WhiteDragon(AbstractCreature owner) {
        super(POWER_ID, PowerType.BUFF, true, owner, owner, -1);
        // com.megacrit.cardcrawl.powers.AngryPower
        // logger.info(owner == null ? "null" : "exist");
        // this.name = NAME;
        // this.ID = POWER_ID;
        // this.owner = owner;
        // this.amount = -1;
        // this.type = PowerType.BUFF;
        // this.isTurnBased = true;
        // this.description = DESCRIPTIONS[0];

        // loadRegion("anger");

        // ApplyPowerAction
        // addToBot(new Hand);
        // import com.megacrit.cardcrawl.actions.common.DamageAction
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    @Override
    protected void initializeTemplate() {
        if (owner.hasPower(makeID("BlackDragon"))) {
            addToBot(new DrawCardAction(1));
            addToBot(new RemoveSpecificPowerAction(owner, owner, this));
            done = true;
            return;
        }
        addToBot(new SelectCardsInHandAction(1, "复制", (card) -> !card.hasTag(DragonKnightMod.Enums.ANTI_BRAND),
                (cards) -> {
                    for (AbstractCard card : cards) {
                        AbstractCard newCard = card.makeSameInstanceOf();

                        addBrandToCard(newCard);
                        newCard.initializeDescription();
                        addToBot(new CopyCardInHandAction(newCard));
                    }
                }));

        if (owner.hasPower(makeID("WhiteBrandPower"))) {
            addToBot(new ApplyPowerAction(owner, owner,
                    new StrengthPower(owner, owner.getPower(makeID("WhiteBrandPower")).amount)));
        }
    }

    @Override
    public void stackPower(int stackAmount) {
        addToBot(new DrawCardAction(1));
    }

    // @Override
    // public void stackPower(int stackAmount) {
    // logger.info("stack");
    // }

    // @Override
    // public void atStartOfTurn() {
    // addToBot(new RemoveSpecificPowerAction(owner, owner, this));
    // }

    // @Override
    // public void onInitialApplication() {
    // if (owner.hasPower(makeID("BlackDragon"))) {
    // addToBot(new RemoveSpecificPowerAction(owner, owner, this));
    // return;
    // }
    // addToBot(new SelectCardsInHandAction(1, "复制", (card) ->
    // !card.hasTag(DragonKnightMod.Enums.BRAND), (cards) -> {
    // for (AbstractCard card : cards) {
    // AbstractCard newCard = card.makeSameInstanceOf();

    // newCard.rawDescription += " NL 烙印";
    // newCard.initializeDescription();
    // newCard.tags.add(DragonKnightMod.Enums.BRAND);
    // addToBot(new CopyCradInHandAction(newCard));
    // }
    // }));

    // if (owner.hasPower(makeID("WhiteBrandPower"))) {
    // addToBot(new ApplyPowerAction(owner, owner,
    // new StrengthPower(owner, owner.getPower(makeID("WhiteBrandPower")).amount)));
    // }

    // if (owner.hasPower(makeID("PhantomDragonPower"))) {
    // PhantomDragonPower power = (PhantomDragonPower)
    // owner.getPower(makeID("PhantomDragonPower"));
    // if (!power.isUsed) {
    // if (owner.isPlayer) {
    // addToBot(new ApplyPowerAction(owner, owner, new NextCardFreePower(owner,
    // 1)));
    // power.isUsed = true;
    // }
    // }
    // }
    // // com.megacrit.cardcrawl.actions.utility.
    // }

}
