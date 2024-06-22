package gui.game.view;

import java.awt.*;

public class RectangleLayout implements LayoutManager {

    private Component contained = null;

    @Override
    public void addLayoutComponent(String name, Component comp) {
        if (contained == null) {
            contained = comp;
        }
        else {
            throw new IllegalStateException("can not add multiple components to RectangleLayout");
        }
    }

    @Override
    public void removeLayoutComponent(Component comp) {
        if (contained == comp)
            contained = null;
    }

    @Override
    public Dimension preferredLayoutSize(Container parent) {
        return contained != null ? contained.getPreferredSize() : new Dimension(0, 0);
    }

    @Override
    public Dimension minimumLayoutSize(Container parent) {
        return contained != null ? contained.getMinimumSize() : new Dimension(0, 0);
    }

    @Override
    public void layoutContainer(Container parent) {
        synchronized (parent.getTreeLock()) {
            Component c = parent.getComponent(0);
            Dimension dim = parent.getSize();
            int size = Math.min(dim.width, dim.height);
            c.setSize(size, size);

            int xPos = 0;
            int yPos = 0;

            if (dim.width > dim.height){
                xPos = (dim.width / 2) - (size / 2);
            }
            else {
                yPos = (dim.width / 2) - (size / 2);
            }

            c.setLocation(xPos, yPos);
        }
    }

}
