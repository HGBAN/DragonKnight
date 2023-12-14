package dragonknight.cards.common;

import static dragonknight.DragonKnightMod.*;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.LoseBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import dragonknight.DragonKnightMod;
import dragonknight.character.DragonPrince;

public class Puncture extends CustomCard {
    public static final String ID = makeID("Puncture");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 1;
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    public Puncture() {
        super(ID, NAME, imagePath("cards/attack/Puncture.png"), COST, DESCRIPTION, TYPE, DragonPrince.Enums.CARD_COLOR,
                RARITY,
                TARGET);
        this.tags.add(DragonKnightMod.Enums.NO_BRAND);
        this.baseDamage = 7;
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            this.upgradeName();
            this.upgradeDamage(3);
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (!this.exhaustOnUseOnce && !this.exhaust) {
            this.addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn),
                    AttackEffect.FIRE));
        }
    }

    @Override
    public void triggerOnExhaust() {
        AbstractMonster m = AbstractDungeon.getCurrRoom().monsters.getRandomMonster(true);
        this.addToBot(new LoseBlockAction(m, AbstractDungeon.player, m.currentBlock));
        this.addToBot(new DamageAction(m,
                new DamageInfo(AbstractDungeon.player, this.damage, this.damageTypeForTurn),
                AttackEffect.FIRE));
    }
}
