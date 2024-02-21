package dragonknight.powers;

import static dragonknight.DragonKnightMod.*;

import java.util.List;
import java.util.stream.Collectors;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;

import dragonknight.DragonKnightMod;

public class SurefireEntwiningPower extends BasePower {
    public static final String POWER_ID = makeID("SurefireEntwiningPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public SurefireEntwiningPower(AbstractCreature owner) {
        super(POWER_ID, PowerType.BUFF, false, owner, owner, -1);
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (!owner.isPlayer)
            return;
        addToBot(new AbstractGameAction() {

            @Override
            public void update() {
                AbstractPlayer p = AbstractDungeon.player;
                if (card.hasTag(DragonKnightMod.Enums.ANTI_BRAND)) {
                    List<AbstractCard> hand = p.hand.group.stream()
                            .filter(x -> !x.hasTag(DragonKnightMod.Enums.ANTI_BRAND) && canBrand(x))
                            .collect(Collectors.toList());
                    if (hand.size() > 0) {
                        AbstractCard c = hand.get(AbstractDungeon.cardRng.random(hand.size() - 1));
                        flash();
                        c.tags.add(DragonKnightMod.Enums.ANTI_BRAND);
                        c.initializeDescription();
                    }
                }

                isDone = true;
            }

        });

    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        addToBot(new RemoveSpecificPowerAction(owner, owner, this));
    }

    
}
