package dragonknight.ui;

import static dragonknight.DragonKnightMod.imagePath;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.ui.buttons.Button;

public class Button1 extends Button {
    public String buttonText = "";
    public static final Texture BUTTON_TEXTURE = new Texture(imagePath("buttonL.png"));

    public Button1(float x, float y) {
        super(x, y, BUTTON_TEXTURE);

    }

    @Override
    public void render(SpriteBatch sb) {
        super.render(sb);

        FontHelper.renderFontCentered(sb, FontHelper.buttonLabelFont, buttonText, x + width / 2, y + height / 2,
                Color.WHITE.cpy());
    }
}
