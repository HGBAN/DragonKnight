package dragonknight.cards.uncommon;

import static dragonknight.DragonKnightMod.*;

import java.util.List;
import java.util.stream.Collectors;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import dragonknight.DragonKnightMod;
import dragonknight.actions.CopyCardInHandAction;
import dragonknight.character.DragonPrince;

public class HeavenlyGaze extends CustomCard {
    public static final String ID = makeID("HeavenlyGaze");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 2;
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    public HeavenlyGaze() {
        super(ID, NAME, imagePath("cards/attack/HeavenlyGaze.png"), COST, DESCRIPTION, TYPE,
                DragonPrince.Enums.CARD_COLOR,
                RARITY, TARGET);
        this.baseDamage = 12;
        this.shuffleBackIntoDrawPile = true;
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(1);
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn),
                AttackEffect.FIRE));
        addToBot(new GainEnergyAction(1));
    }

    @Override
    public void triggerOnExhaust() {
        AbstractPlayer p = AbstractDungeon.player;
        List<AbstractCard> cards = p.drawPile.group.stream().filter(x -> x.type.equals(CardType.ATTACK))
                .collect(Collectors.toList());
        for (int i = 0; i < 3; i++) {
            if (cards.size() <= 0)
                break;
            AbstractCard card = cards.remove(AbstractDungeon.cardRng.random(cards.size() - 1)).makeSameInstanceOf();
            card.isEthereal = true;
            card.tags.add(DragonKnightMod.Enums.ETHEREAL);
            addToBot(new CopyCardInHandAction(card));
        }
    }
}
