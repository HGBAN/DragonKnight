package dragonknight.cards.common;

import static dragonknight.DragonKnightMod.*;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import dragonknight.DragonKnightMod;
import dragonknight.character.DragonPrince;

public class StrongWind extends CustomCard {
    public static final String ID = makeID("StrongWind");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 3;
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    // private int damageIncreased = 2;
    // private boolean branded = false;

    public StrongWind() {
        super(ID, NAME, imagePath("cards/attack/StrongWind.png"), COST, DESCRIPTION, TYPE,
                DragonPrince.Enums.CARD_COLOR,
                RARITY,
                TARGET);
        this.baseDamage = 15;
        // this.baseMagicNumber = 10;
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            this.upgradeName();
            // this.upgradeMagicNumber(5);
            this.upgradeDamage(7);
            // damageIncreased++;
            // this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            // this.initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn),
                AttackEffect.SLASH_HORIZONTAL));
    }

    @Override
    public float calculateModifiedCardDamage(AbstractPlayer player, AbstractMonster mo, float tmp) {
        tmp += DragonKnightMod.attackUsed + DragonKnightMod.attackBranded * 2;

        return tmp;
    }

    // @Override
    // public void setBranded(boolean branded) {
    // this.branded = branded;
    // }
}
