package dragonknight.ui;

import static dragonknight.DragonKnightMod.copyCard;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.function.Consumer;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import dragonknight.DragonKnightMod;

public class BrandQueue {
    public AbstractPlayer player;
    private float x;
    private float y;
    public static final float HB_W = 300.0F * Settings.scale;
    public static final float HB_H = 420.0F * Settings.scale;

    private ArrayList<AbstractCard> brandCards = new ArrayList<>();

    private Consumer<AbstractCard> onAddBrandCardListener;
    private Runnable onClearBrandCardsListener;
    private Runnable onRemoveBrandCardsListener;

    private static Field renderColorField;

    static {
        try {
            renderColorField = AbstractCard.class.getDeclaredField("renderColor");
            renderColorField.setAccessible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public BrandQueue(AbstractPlayer player) {
        this.player = player;
        x = player.hb.cX - player.hb.width / 2.0F;
        y = player.hb.cY - player.hb.height / 2.0F + 250;

        onAddBrandCardListener = (card) -> {
            AbstractCard cp = copyCard(card);
            cp.drawScale = 0.2f;
            cp.target_x = cp.current_x = x + (HB_W * cp.drawScale + 10) * brandCards.size();
            cp.target_y = cp.current_y = y;
            cp.hb.move(cp.current_x, cp.current_y);
            cp.hb.resize(HB_W * cp.drawScale, HB_H * cp.drawScale);
            if (card.hasTag(DragonKnightMod.Enums.NO_BRAND)) {
                try {
                    renderColorField.set(cp, Color.GRAY.cpy());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            // cp.renderColor

            brandCards.add(cp);
        };
        onRemoveBrandCardsListener = () -> {
            brandCards.remove(0);
        };
        onClearBrandCardsListener = () -> {
            brandCards.clear();
        };
        DragonKnightMod.onAddBrandCard.add(new WeakReference<Consumer<AbstractCard>>(onAddBrandCardListener));
        DragonKnightMod.onRemoveBrandCards.add(new WeakReference<Runnable>(onRemoveBrandCardsListener));
        DragonKnightMod.onClearBrandCards.add(new WeakReference<Runnable>(onClearBrandCardsListener));
    }

    private ArrayList<AbstractCard> renderCards = new ArrayList<>();

    public void render(SpriteBatch sb) {
        if (brandCards.size() > 0) {
            FontHelper.charDescFont.draw(sb, "烙印：", x - 80, y);
        }

        renderCards.clear();
        boolean hover = false;
        for (int i = 0; i < brandCards.size(); i++) {
            AbstractCard card = brandCards.get(i);
            card.hb.update();
            card.update();
            if (card.hb.hovered && !hover) {
                // card.drawScale = 0.7f;
                card.targetDrawScale = 0.7f;
                hover = true;
                renderCards.add(card);
            } else {
                // card.drawScale = 0.2f;
                card.targetDrawScale = 0.2f;
                renderCards.add(0, card);
            }
            card.hb.move(card.current_x, card.current_y);
            card.hb.resize(HB_W * card.drawScale, HB_H * card.drawScale);
            // Settings.isDebug = true;
        }
        for (AbstractCard card : renderCards) {
            card.render(sb);
        }
    }
}
