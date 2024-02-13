package dragonknight.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;

import basemod.abstracts.CustomCard;

public abstract class BrandCopyCard extends CustomCard {
    public boolean brandExhaust = false;

    public BrandCopyCard(String id, String name, String img, int cost, String rawDescription,
            AbstractCard.CardType type, AbstractCard.CardColor color, AbstractCard.CardRarity rarity,
            AbstractCard.CardTarget target) {
        super(id, name, img, cost, rawDescription, type, color, rarity, target);

    }

    public BrandCopyCard(String id, String name, RegionName img, int cost, String rawDescription,
            AbstractCard.CardType type, AbstractCard.CardColor color, AbstractCard.CardRarity rarity,
            AbstractCard.CardTarget target) {
        super(id, name, img, cost, rawDescription, type, color, rarity, target);
    }

    @Override
    public void triggerOnExhaust() {
        if (brandExhaust)
            brandExhaust();
        else
            normalExhaust();
        brandExhaust = false;
    }

    public void normalExhaust() {

    }

    public void brandExhaust() {

    }
}
