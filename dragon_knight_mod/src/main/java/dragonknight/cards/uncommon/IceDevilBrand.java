package dragonknight.cards.uncommon;

import static dragonknight.DragonKnightMod.*;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import basemod.abstracts.CustomCard;
import dragonknight.DragonKnightMod;
import dragonknight.character.DragonPrince;

public class IceDevilBrand extends CustomCard {
    public static final String ID = makeID("IceDevilBrand");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = -1;
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;

    public IceDevilBrand() {
        super(ID, NAME, imagePath("cards/attack/IceDevilBrand.png"), COST, DESCRIPTION, TYPE,
                DragonPrince.Enums.CARD_COLOR,
                RARITY, TARGET);
        this.tags.add(DragonKnightMod.Enums.ANTI_BRAND);
        this.baseDamage = 0;
        this.initializeDescription();
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            this.upgradeName();
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                if (!IceDevilBrand.this.freeToPlayOnce)
                    AbstractDungeon.player.energy.use(EnergyPanel.totalCount);
                this.isDone = true;
            }
        });
        for (int i = 0; i < getEffect(); i++)
            this.addToBot(
                    new DamageAllEnemiesAction(p, DragonKnightMod.enemyPowerCountThisTurn, this.damageTypeForTurn,
                            AttackEffect.SLASH_DIAGONAL));
    }

    // @Override
    // public void render(SpriteBatch sb) {
    // this.baseDamage = getEffect() * DragonKnightMod.brandCards.size();
    // super.render(sb);
    // }

    @Override
    public float calculateModifiedCardDamage(AbstractPlayer player, AbstractMonster mo, float tmp) {
        tmp += DragonKnightMod.enemyPowerCountThisTurn;
        return tmp;
    }

    private int getEffect() {
        int effect = EnergyPanel.totalCount;
        AbstractPlayer p = AbstractDungeon.player;
        if (p == null)
            return 0;
        if (p.hasRelic("Chemical X")) {
            effect += 2;
            p.getRelic("Chemical X").flash();
        }

        if (this.upgraded) {
            effect++;
        }
        return effect;
    }
}
