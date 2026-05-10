package ru.samsung.gamestudio.UI;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import static ru.samsung.gamestudio.GameSettings.SCREEN_WIDTH;

import java.util.ArrayList;

public class RecordsListView extends TextView{


    public RecordsListView(BitmapFont font, float y) {
        super(font, 0, y,"");
        this.font=font;
    }
    public void setRecords(ArrayList<Integer> recordsList) {
        text = "";
        int countOfRows=Math.min(recordsList.size(),5);
        for (int i=0;i<countOfRows;i++) {
            System.out.println(recordsList.get(i));
            text += (i+1)+". - "+recordsList.get(i)+"\n";
        }
        GlyphLayout glyphLayout=new GlyphLayout(font,text);
        x=(SCREEN_WIDTH-glyphLayout.width)/2;
    }
}
