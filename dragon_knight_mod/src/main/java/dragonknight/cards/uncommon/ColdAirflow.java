package dragonknight.cards.uncommon;

import static dragonknight.DragonKnightMod.*;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.utility.LoseBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import basemod.abstracts.CustomCard;
import dragonknight.DragonKnightMod;
import dragonknight.character.DragonPrince;
import dragonknight.powers.IceDevilFormPower;

public class ColdAirflow extends CustomCard {
    public static final String ID = makeID("ColdAirflow");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 2;
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;

    public ColdAirflow() {
        super(ID, NAME, imagePath("cards/attack/ColdAirflow.png"), COST, DESCRIPTION, TYPE,
                DragonPrince.Enums.CARD_COLOR,
                RARITY, TARGET);
        this.baseMagicNumber = 1;
        this.baseDamage = 12;
        this.tags.add(DragonKnightMod.Enums.NO_BRAND);
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            this.upgradeName();
            this.upgradeDamage(6);
            this.upgradeMagicNumber(1);
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (!this.exhaustOnUseOnce && !this.exhaust
                && !(p.hasPower(IceDevilFormPower.POWER_ID) && hasTag(DragonKnightMod.Enums.ANTI_BRAND))) {
            for (AbstractMonster mo : AbstractDungeon.getMonsters().monsters) {
                this.addToBot(new ApplyPowerAction(mo, p, new StrengthPower(mo, -this.baseMagicNumber)));
                this.addToBot(new ApplyPowerAction(mo, p, new GainStrengthPower(mo, this.baseMagicNumber)));
            }
            this.addToBot(
                    new DamageAllEnemiesAction(p, this.damage, this.damageTypeForTurn, AttackEffect.SLASH_DIAGONAL));
        }
    }

    @Override
    public void triggerOnExhaust() {
        AbstractPlayer p = AbstractDungeon.player;
        for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
            this.addToBot(new LoseBlockAction(m, p, m.currentBlock));
            this.addToBot(new ApplyPowerAction(m, p, new StrengthPower(m, -this.baseMagicNumber)));
            this.addToBot(new ApplyPowerAction(m, p, new GainStrengthPower(m, this.baseMagicNumber)));
        }
        this.addToBot(
                new DamageAllEnemiesAction(p, this.baseDamage, this.damageTypeForTurn, AttackEffect.SLASH_DIAGONAL));

    }
}
