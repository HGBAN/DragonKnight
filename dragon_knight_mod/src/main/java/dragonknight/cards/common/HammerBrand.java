package dragonknight.cards.common;

import static dragonknight.DragonKnightMod.*;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;

import basemod.abstracts.CustomCard;
import dragonknight.DragonKnightMod;
import dragonknight.character.DragonPrince;

public class HammerBrand extends CustomCard {
    public static final String ID = makeID("HammerBrand");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 0;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    public HammerBrand() {
        super(ID, NAME, imagePath("cards/skill/HammerBrand.png"), COST, DESCRIPTION, TYPE,
                DragonPrince.Enums.CARD_COLOR,
                RARITY,
                TARGET);
        this.baseMagicNumber = 1;
        this.tags.add(DragonKnightMod.Enums.BRAND);
        this.tags.add(DragonKnightMod.Enums.EXHAUST);
        this.exhaust = true;
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(1);
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new ApplyPowerAction(m, p, new StrengthPower(m, -this.baseMagicNumber)));
        if (DragonKnightMod.brandCountLastTurn > 0) {
            this.addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, this.baseMagicNumber)));
        }
    }

    @Override
    public void triggerWhenDrawn() {
        if (DragonKnightMod.brandCountLastTurn > 0)
            this.glowColor = GOLD_BORDER_GLOW_COLOR;
    }
}
