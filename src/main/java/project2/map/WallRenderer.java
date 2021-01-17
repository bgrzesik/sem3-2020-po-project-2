package project2.map;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.GridPoint2;

public class WallRenderer {
    public static final GridPoint2[] BYTE_ORDER = {
            new GridPoint2(0, 1), // N
            new GridPoint2(1, 1), // NE
            new GridPoint2(1, 0), // E
            new GridPoint2(1, -1), // SE
            new GridPoint2(0, -1), // S
            new GridPoint2(-1, -1), // SW
            new GridPoint2(-1, 0), // W
            new GridPoint2(-1, 1), // NW
    };

    private final int neighbors;
    private final int x;
    private final int y;

    public WallRenderer(int x, int y, int neighbors) {
        this.neighbors = neighbors;
        this.x = x;
        this.y = y;
    }

    public void render(TextureRegion[][] atlas, SpriteBatch batch) {
        /* nw n ne
           w     e
           sw s se

           nw w sw s se e ne n */

        if (neighbors == 0b11111111) {
            return;
        }

        if (neighbors == 0b00000000) {
            draw(batch, atlas, 0, 2, 0);
            return;
        }

        final int[] uShaped = {0b00011111, 0b11000111, 0b11110001, 0b01111100};
        for (int i = 0; i < 4; i++) {
            if ((neighbors & uShaped[i]) == 0b00000000) {
                draw(batch, atlas, 2, 1, i);
                return;
            }
        }

        final int[] tinyLShapedMask = {0b11000001, 0b01110000, 0b00011100, 0b00000111};
        final int[] tinyLShapedCheck = {0b01000001, 0b01010000, 0b00010100, 0b00000101};
        for (int i = 0; i < 4; i++) {
            if ((neighbors & tinyLShapedMask[i]) == tinyLShapedCheck[i]) {
                draw(batch, atlas, 3, 1, i);
            }
        }


        final int[] lShaped = {0b00000111, 0b11000001, 0b01110000, 0b00011100};
        for (int i = 0; i < 4; i++) {
            if ((neighbors & lShaped[i]) == 0b00000000) {
                draw(batch, atlas, 1, 1, i);
                return;
            }
        }

        final int[] iShaped = {0b00000001, 0b01000000, 0b00010000, 0b00000100,};
        for (int i = 0; i < 4; i++) {
            if ((neighbors & iShaped[i]) == 0b00000000) {
                draw(batch, atlas, 0, 1, i);
            }
        }
    }

    public void draw(SpriteBatch batch, TextureRegion[][] atlas, int col, int row, int rotation) {
        batch.draw(atlas[row][col], x, y, 0.5f, 0.5f, 1f, 1f, 1f, 1f, rotation * 90f);
    }

}
