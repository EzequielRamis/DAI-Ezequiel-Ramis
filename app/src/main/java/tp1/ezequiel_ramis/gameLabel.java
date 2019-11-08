package tp1.ezequiel_ramis;

import org.cocos2d.nodes.Label;
import org.cocos2d.types.CCSize;

public class gameLabel {
    private Label _label;
    private float _x;
    private double _velocity;

    public gameLabel(String text, boolean left, CCSize size, int VEL) {
        _label = Label.label(text, "", 85);
        //_velocity = (float) velocity;
        _velocity = left? VEL*sigmoid(_label.getWidth()) : -VEL*sigmoid(_label.getWidth());
        _x = _velocity < 0 ? size.getWidth() + _label.getWidth()/2 : -_label.getWidth()/2;
    }

    public void move(float y) {
        _label.setPosition(_x + (float)_velocity, y);
    }

    private double sigmoid(float w) {
        return 2 / (1 + Math.pow(Math.E, .01 * w)) + .5;
    }
}
