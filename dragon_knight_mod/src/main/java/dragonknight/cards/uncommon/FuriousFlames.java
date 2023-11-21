package dragonknight.cards.uncommon;

import static dragonknight.DragonKnightMod.imagePath;
import static dragonknight.DragonKnightMod.makeID;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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

public class FuriousFlames extends CustomCard {
    public static final String ID = makeID("FuriousFlames");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 3;
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    public FuriousFlames() {
        super(ID, NAME, imagePath("cards/attack/default.png"), COST, DESCRIPTION, TYPE, DragonPrince.Enums.CARD_COLOR,
                RARITY,
                TARGET);

        this.baseDamage = 20;
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(2);
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn),
                AttackEffect.FIRE));
    }

    @Override
    public void render(SpriteBatch sb) {
        this.baseDamage = 20 + DragonKnightMod.brandCount * 5;
        // if (this.baseDamage > 20) {
        //     this.isDamageModified = true;
        // }
        super.render(sb);
    }

}
