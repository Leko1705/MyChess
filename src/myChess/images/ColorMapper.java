package myChess.images;

import java.awt.*;
import java.awt.image.LookupTable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

class ColorMapper
extends LookupTable {

    private final Map<int[], int[]> map = new HashMap<>();

    public ColorMapper(Map<Color, Color> map) {
        super(0, 4);

        for (Map.Entry<Color, Color> entry : map.entrySet()){
            this.map.put(
                    new int[]{
                         entry.getKey().getRed(),
                         entry.getKey().getGreen(),
                         entry.getKey().getBlue(),
                         entry.getKey().getAlpha()
                    },
                    new int[]{
                            entry.getValue().getRed(),
                            entry.getValue().getGreen(),
                            entry.getValue().getBlue(),
                            entry.getValue().getAlpha()
                    }
            );
        }
    }

    @Override
    public int[] lookupPixel(int[] src,
                             int[] dest) {
        if (dest == null) {
            dest = new int[src.length];
        }

        for (Map.Entry<int[], int[]> entry : map.entrySet()){
            int[] from = entry.getKey();
            int[] to = entry.getValue();
            int[] newColor = (Arrays.equals(src, from) ? to : src);
            System.arraycopy(newColor, 0, dest, 0, newColor.length);
        }

        return dest;
    }
}
