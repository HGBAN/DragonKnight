package dragonknight.powers;

import static dragonknight.DragonKnightMod.makeID;

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;

public class NextCardFreePower extends BasePower {
    public static final String POWER_ID = makeID("NextCardFreePower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    // private List<AbstractCard> cards = new ArrayList<>();

    public NextCardFreePower(AbstractCreature owner, int amount) {
        super(POWER_ID, PowerType.BUFF, true, owner, owner, amount);
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    // @Override
    // public void onInitialApplication() {
    //     // updateCards();
    //     // AbstractDungeon.player.hand
    // }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        this.amount--;
        if (this.amount <= 0) {
            addToBot(new RemoveSpecificPowerAction(owner, owner, NextCardFreePower.this));
        }
    }

    // @Override
    // public void onAfterUseCard(AbstractCard card, UseCardAction action) {
    // addToBot(new AbstractGameAction() {
    // @Override
    // public void update() {
    // updateCards();
    // amount--;
    // if (amount <= 0) {
    // for (AbstractCard card1 : cards) {
    // card1.freeToPlayOnce = false;
    // }
    // addToBot(new RemoveSpecificPowerAction(owner, owner,
    // NextCardFreePower.this));
    // }
    // isDone = true;
    // }
    // });
    // }

    // @Override
    // public void atStartOfTurnPostDraw() {
    // updateCards();
    // }

    // private void updateCards() {
    // if (owner.isPlayer) {
    // AbstractPlayer player = (AbstractPlayer) owner;

    // List<AbstractCard> newCards = player.hand.group.stream()
    // .filter((card) -> !cards.contains(card) && !card.freeToPlayOnce)
    // .collect(Collectors.toList());
    // for (AbstractCard card : newCards) {
    // card.freeToPlayOnce = true;
    // }
    // cards.addAll(newCards);
    // }
    // }
}
