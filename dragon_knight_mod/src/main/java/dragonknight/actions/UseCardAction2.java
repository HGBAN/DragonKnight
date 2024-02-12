package dragonknight.actions;

import java.util.Iterator;

import com.megacrit.cardcrawl.actions.utility.HandCheckAction;
import com.megacrit.cardcrawl.actions.utility.ShowCardAction;
import com.megacrit.cardcrawl.actions.utility.ShowCardAndPoofAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class UseCardAction2 extends UseCardAction {
    private AbstractCard card;

    public UseCardAction2(AbstractCard c, AbstractCreature m) {
        super(c, m);
        card = c;
    }

    @Override
    public void update() {
        if (this.duration == 0.15F) {
            Iterator<AbstractPower> var1 = AbstractDungeon.player.powers.iterator();

            while (var1.hasNext()) {
                AbstractPower p = (AbstractPower) var1.next();
                if (!card.dontTriggerOnUseCard) {
                    p.onAfterUseCard(card, this);
                }
            }

            Iterator<AbstractMonster> var2 = AbstractDungeon.getMonsters().monsters.iterator();

            while (var2.hasNext()) {
                AbstractMonster m = (AbstractMonster) var2.next();
                Iterator<AbstractPower> var3 = m.powers.iterator();

                while (var3.hasNext()) {
                    AbstractPower p = (AbstractPower) var3.next();
                    if (!card.dontTriggerOnUseCard) {
                        p.onAfterUseCard(card, this);
                    }
                }
            }

            card.freeToPlayOnce = false;
            card.isInAutoplay = false;
            if (card.purgeOnUse) {
                this.addToTop(new ShowCardAndPoofAction(card));
                this.isDone = true;
                AbstractDungeon.player.cardInUse = null;
                return;
            }

            if (card.type == CardType.POWER) {
                this.addToTop(new ShowCardAction(card));
                if (Settings.FAST_MODE) {
                    this.addToTop(new WaitAction(0.1F));
                } else {
                    this.addToTop(new WaitAction(0.7F));
                }

                AbstractDungeon.player.hand.empower(card);
                this.isDone = true;
                AbstractDungeon.player.hand.applyPowers();
                AbstractDungeon.player.hand.glowCheck();
                AbstractDungeon.player.cardInUse = null;
                return;
            }

            AbstractDungeon.player.cardInUse = null;

            card.exhaustOnUseOnce = false;
            card.dontTriggerOnUseCard = false;

            this.addToBot(new HandCheckAction());
        }
        this.tickDuration();
    }
}
