package tp1.ezequiel_ramis;

import android.util.Log;

import org.cocos2d.nodes.Label;
import org.cocos2d.types.CCColor3B;
import org.cocos2d.types.CCPoint;

import java.util.ArrayList;

public class tile {
    private ArrayList<Label> _labels;
    private String _text;
    private double _velocity;
    private double _repeat;
    private CCPoint _ccPoint;
    private int _VEL = 20;

    public tile(String text, CCPoint ccPoint, boolean left, double repeat) {
        _labels = new ArrayList<>();
        _labels.add(Label.label(text, "", 85));
        _text = text;
        _ccPoint = ccPoint;
        _velocity = left? _VEL*sigmoid(_labels.get(0).getWidth()) : -_VEL*sigmoid(_labels.get(0).getWidth());
        _repeat = repeat;
        setColor();
        moveX();
        moveY();
    }

    public Label getLabel(int i) {
        return _labels.get(i);
    }

    private Double sigmoid(float w) {
        return 2/(1 + Math.pow(Math.E, .01*w)) + .5;
    }

    private void setCCPointY(float y) {
        _ccPoint.y = y;
    }

    private void setCCPointX(double x) {
        _ccPoint.x += (float) x;
    }

    public void setColor(){
        for (Label label:_labels) {
            label.setColor(new CCColor3B(255,255,255));
            Log.d("Size", ""+label.getHeight());
        }
    }

    public void moveX(){
        setCCPointX(_velocity);
        for (Label label:_labels) {
            label.setPosition(_ccPoint.x, _ccPoint.y*100);
        }
    }

    public void moveY(){
        for (Label label:_labels) {
            label.setPosition(_ccPoint.x, _ccPoint.y*100);
        }
    }
}
