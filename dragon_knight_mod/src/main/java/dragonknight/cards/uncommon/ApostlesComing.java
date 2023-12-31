package dragonknight.cards.uncommon;

import static dragonknight.DragonKnightMod.*;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import basemod.abstracts.CustomCard;
import dragonknight.DragonKnightMod;
import dragonknight.character.DragonPrince;

public class ApostlesComing extends CustomCard {
    public static final String ID = makeID("ApostlesComing");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 1;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.NONE;

    public ApostlesComing() {
        super(ID, NAME, imagePath("cards/skill/ApostlesComing.png"), COST, DESCRIPTION, TYPE,
                DragonPrince.Enums.CARD_COLOR,
                RARITY, TARGET);
        this.tags.add(DragonKnightMod.Enums.NO_BRAND);
        this.tags.add(DragonKnightMod.Enums.BE_DRAGON);
        this.baseMagicNumber = 0;
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
        DragonKnightMod.beDragon();
        if (this.baseMagicNumber > 0) {
            this.addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, this.baseMagicNumber)));
            this.addToBot(new ApplyPowerAction(p, p, new LoseStrengthPower(p, this.baseMagicNumber)));
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        this.baseMagicNumber = DragonKnightMod.blockGainedThisTurn / 2;
        super.render(sb);
    }

}
