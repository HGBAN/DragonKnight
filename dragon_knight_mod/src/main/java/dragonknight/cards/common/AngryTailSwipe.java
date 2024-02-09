package dragonknight.cards.common;

import static dragonknight.DragonKnightMod.*;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import dragonknight.character.DragonPrince;
import dragonknight.powers.WhiteDragonAwakeningPower;

public class AngryTailSwipe extends CustomCard {
    public static final String ID = makeID("AngryTailSwipe");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 1;
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF_AND_ENEMY;

    public AngryTailSwipe() {
        super(ID, NAME, imagePath("cards/attack/AngryTailSwipe.png"), COST, DESCRIPTION, TYPE,
                DragonPrince.Enums.CARD_COLOR,
                RARITY,
                TARGET);
        this.baseDamage = 8;
        this.baseBlock = 8;
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            this.upgradeName();
            this.upgradeDamage(3);
            this.upgradeBlock(3);
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn),
                AttackEffect.BLUNT_LIGHT));
        this.addToBot(new GainBlockAction(p, block));
        if (!p.hasPower(makeID("WhiteDragonAwakeningPower")) && !p.hasPower(makeID("BlackDragonAwakeningPower")))
            this.addToBot(new ApplyPowerAction(p, p, new WhiteDragonAwakeningPower(p)));
    }

}
