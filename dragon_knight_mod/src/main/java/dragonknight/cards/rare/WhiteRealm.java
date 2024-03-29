package dragonknight.cards.rare;

import static dragonknight.DragonKnightMod.*;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;

import dragonknight.DragonKnightMod;
import dragonknight.cards.BrandCopyCard;
import dragonknight.character.DragonPrince;
import dragonknight.powers.WhiteDragonAwakeningPower;
import dragonknight.powers.WhiteRealmPower;

public class WhiteRealm extends BrandCopyCard {
    public static final String ID = makeID("WhiteRealm");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 2;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;

    public WhiteRealm() {
        super(ID, NAME, imagePath("cards/skill/WhiteRealm.png"), COST, DESCRIPTION, TYPE,
                DragonPrince.Enums.CARD_COLOR,
                RARITY, TARGET);
        // this.exhaustOnUseOnce = true;
        this.exhaust = true;
        this.tags.add(DragonKnightMod.Enums.BRAND);
        this.tags.add(DragonKnightMod.Enums.EXHAUST);
        this.initializeDescription();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(1);
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new WhiteRealmPower(p, 1)));
    }

    @Override
    public void brandExhaust() {
        AbstractPlayer p = AbstractDungeon.player;
        addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, 5)));
        addToBot(new ApplyPowerAction(p, p, new WhiteDragonAwakeningPower(p)));
    }

    // @Override
    // public void triggerOnExhaust() {
    // AbstractPlayer p = AbstractDungeon.player;
    // addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, 5)));
    // addToBot(new ApplyPowerAction(p, p, new WhiteDragonAwakeningPower(p)));
    // }

}
