package dragonknight.cards.common;

import static dragonknight.DragonKnightMod.*;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;

import basemod.abstracts.CustomCard;
import dragonknight.DragonKnightMod;
import dragonknight.character.DragonPrince;
import dragonknight.powers.BlackDragonAwakeningPower;

public class HauntingMurmur extends CustomCard {
    public static final String ID = makeID("HauntingMurmur");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 0;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ALL;

    public HauntingMurmur() {
        super(ID, NAME, imagePath("cards/skill/HauntingMurmur.png"), COST, DESCRIPTION, TYPE,
                DragonPrince.Enums.CARD_COLOR,
                RARITY,
                TARGET);
        this.baseBlock = 10;
        this.baseMagicNumber = 1;
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            this.upgradeName();
            // this.upgradeMagicNumber(8);
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (!p.hasPower(makeID("WhiteDragonAwakeningPower")) && !p.hasPower(makeID("BlackDragonAwakeningPower")))
            this.addToBot(new ApplyPowerAction(p, p, new BlackDragonAwakeningPower(p)));
        int tmp = this.baseMagicNumber;
        if (DragonKnightMod.isEnemyDamagedLastTurn) {
            tmp += 1;
            if (upgraded)
                tmp += 1;
            this.addToBot(new GainBlockAction(p, this.block));
        }
        for (AbstractMonster monster : AbstractDungeon.getCurrRoom().monsters.monsters) {
            this.addToBot(new ApplyPowerAction(monster, p, new WeakPower(monster, tmp, true)));
        }
    }

    @Override
    public void triggerOnGlowCheck() {
        if (DragonKnightMod.isEnemyDamagedLastTurn) {
            this.glowColor = GOLD_BORDER_GLOW_COLOR.cpy();
        } else {
            this.glowColor = BLUE_BORDER_GLOW_COLOR.cpy();
        }
    }

}
