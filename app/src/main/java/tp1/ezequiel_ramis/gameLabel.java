package tp1.ezequiel_ramis;

import org.cocos2d.nodes.Label;
import org.cocos2d.types.CCColor3B;
import org.cocos2d.types.CCPoint;
import org.cocos2d.types.CCSize;

public class gameLabel {
    private Label label;
    public double velocity;
    public CCPoint point;
    public CCSize size;

    public gameLabel(String text, CCSize size, float velocity) {
        label = Label.label(text, "", 85);
        label.setColor(new CCColor3B(255,255,255));
        this.velocity = velocity;
        this.size = size;
        point = new CCPoint();
    }

    public void moveBy(float x, float y) {
        point.x+=x;
        point.y+=y;
        label.setPosition(point.x, point.y);
    }

    public void moveTo(float x, float y) {
        point.x=x;
        point.y=y;
        label.setPosition(point.x, point.y);
    }

    public void moveToX(float x) {
        point.x=x;
        label.setPosition(point.x, point.y);
    }

    public void moveToY(float y) {
        point.y=y;
        label.setPosition(point.x, point.y);
    }

    public void moveToLeft() {
        point.x=-label.getWidth()/2;
        label.setPosition(point.x, point.y);
    }

    public void moveToRight() {
        point.x=size.getWidth()+label.getWidth()/2;
        label.setPosition(point.x, point.y);
    }

    public Label getLabel() {
        return label;
    }

    public void set_velocity(double velocity) {
        this.velocity = (float)velocity;
    }
}
