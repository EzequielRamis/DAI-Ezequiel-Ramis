package tp1.ezequiel_ramis;

import org.cocos2d.nodes.Label;
import org.cocos2d.types.CCColor3B;
import org.cocos2d.types.CCSize;

public class gameLabel {
    private Label _label;
    private float _x;
    private double _velocity;

    public gameLabel(String text, CCSize size, float velocity) {
        _label = Label.label(text, "", 85);
        _label.setColor(new CCColor3B(255,255,255));
        //_velocity = (float) velocity;
        _velocity = velocity;
        _x = /*_velocity < 0 ? size.getWidth() + _label.getWidth()/2 : -_label.getWidth()/2*/size.getWidth()/2;
    }

    public void move(float y) {
        _x += (float) _velocity;
        _label.setPosition(_x, y);
    }

    public Label getLabel() {
        return _label;
    }

    public void set_velocity(float velocity) {
        _velocity = velocity;
    }
}
