package dragonknight.powers;

import static dragonknight.DragonKnightMod.*;

import java.util.List;
import java.util.stream.Collectors;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.DiscardToHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;

import dragonknight.DragonKnightMod;

public abstract class BeDragonPower extends BasePower {
    protected boolean done = false;

    public BeDragonPower(String id, PowerType powerType, boolean isTurnBased, AbstractCreature owner,
            AbstractCreature source, int amount) {
        super(id, powerType, isTurnBased, owner, source, amount);
    }

    protected abstract void initializeTemplate();

    @Override
    public void atStartOfTurn() {
        addToBot(new RemoveSpecificPowerAction(owner, owner, this));
    }

    @Override
    public void onInitialApplication() {
        initializeTemplate();

        if (done) {
            done = false;
            return;
        }

        // 检查幻影之龙的效果
        if (owner.hasPower(makeID("PhantomDragonPower"))) {
            PhantomDragonPower power = (PhantomDragonPower) owner.getPower(makeID("PhantomDragonPower"));
            if (!power.isUsed) {
                if (owner.isPlayer) {
                    power.flash();
                    addToBot(new ApplyPowerAction(owner, owner, new NextCardFreePower(owner, power.amount)));
                    power.isUsed = true;
                }
            }
        }

        // 检查王之烙印
        if (owner.isPlayer) {
            AbstractPlayer player = (AbstractPlayer) owner;
            List<AbstractCard> cards = player.discardPile.group.stream()
                    .filter(card -> card.cardID.equals(makeID("KingsBrand")))
                    .collect(Collectors.toList());
            for (AbstractCard card : cards) {
                addToBot(new DiscardToHandAction(card));
            }
        }

        // DragonKnightMod.beDragonCount++;
        DragonKnightMod.onBeDragon();
    }
}
