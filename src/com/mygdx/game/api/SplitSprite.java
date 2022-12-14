package com.mygdx.game.api;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class SplitSprite {

    private final Texture texture;
    private final int FRAME_COLS_IN_TEXSTURE, FRAME_ROWS_IN_TEXSTURE;

    public SplitSprite(final Texture texture, final int FRAME_COLS, final int FRAME_ROWS) {
        this.texture = texture;
        this.FRAME_COLS_IN_TEXSTURE = FRAME_COLS;
        this.FRAME_ROWS_IN_TEXSTURE = FRAME_ROWS;
    }

    public TextureRegion getTexture(final int FRAME_ROW, final int FRAME_COL) {
        TextureRegion[][] tmp = TextureRegion.split(texture,
                texture.getWidth() / FRAME_COLS_IN_TEXSTURE,
                texture.getHeight() / FRAME_ROWS_IN_TEXSTURE);

        return tmp[FRAME_ROW - 1][FRAME_COL - 1];
    }

    public void dispose() {
        texture.dispose();
    }
}
