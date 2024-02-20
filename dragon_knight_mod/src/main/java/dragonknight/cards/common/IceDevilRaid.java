package dragonknight.cards.common;

import static dragonknight.DragonKnightMod.*;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import dragonknight.DragonKnightMod;
import dragonknight.actions.DiscardAntiBrandCardAction;
import dragonknight.character.DragonPrince;

public class IceDevilRaid extends CustomCard {
    public static final String ID = makeID("IceDevilRaid");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 1;
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    public IceDevilRaid() {
        super(ID, NAME, imagePath("cards/attack/IceDevilRaid.png"), COST, DESCRIPTION, TYPE, DragonPrince.Enums.CARD_COLOR,
                RARITY,
                TARGET);
        this.baseDamage = 8;
        tags.add(DragonKnightMod.Enums.ANTI_BRAND);
        this.initializeDescription();
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(0);
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn),
                AttackEffect.FIRE));
        addToBot(new DiscardAntiBrandCardAction(false));
        addToBot(new AbstractGameAction() {

            @Override
            public void update() {
                addToBot(new DrawCardAction(Math.min(DiscardAntiBrandCardAction.count, 2)));
                DiscardAntiBrandCardAction.count = 0;
                isDone = true;
            }

        });
    }

}
