package tp1.ezequiel_ramis;

import android.os.CountDownTimer;
import android.util.Log;

import org.cocos2d.nodes.Label;
import org.cocos2d.types.CCColor3B;
import org.cocos2d.types.CCPoint;
import org.cocos2d.types.CCSize;

import java.util.ArrayList;

public class tile {
    private ArrayList<Label> _labels;
    private String _text;
    private double _velocity;
    private double _repeat;
    private ArrayList<CCPoint> _ccPoints;
    private int _VEL = 5;
    private CCSize _size;
    private CCPoint ORIGINALCCPOINT;

    public tile(String text, CCPoint ccPoint, boolean left, CCSize size) {
        _labels = new ArrayList<Label>();
        _ccPoints = new ArrayList<CCPoint>();
        _labels.add(Label.label(text, "", 85));
        _text = text;
        _ccPoints.add(ccPoint);
        ORIGINALCCPOINT = ccPoint;
        _velocity = left? _VEL*sigmoid(_labels.get(0).getWidth()) : -_VEL*sigmoid(_labels.get(0).getWidth());
        _size = size;
        setColor();
    }

    public void setRepeat(double repeat) {
        _repeat = repeat;
    }

    public Label getLabel(int i) {
        return _labels.get(i);
    }

    public double getVelocity() {
        return _velocity;
    }

    public ArrayList<Label> getLabels() {
        return _labels;
    }

    public void addLabel() {
        _labels.add(Label.label(_text, "", 85));
        _ccPoints.add(ORIGINALCCPOINT);
    }

    public void deleteLabel(int i) {
        _labels.remove(i);
    }

    public String getText() {
        return _text;
    }

    public double getRepeat() {
        return _repeat;
    }

    private Double sigmoid(float w) {
        return 2/(1 + Math.pow(Math.E, .01*w)) + .5;
    }

    private void setCCPointY(float y) {
        for (CCPoint point: _ccPoints) {
            point.y = y;
        }
    }

    private void setCCPointX(double x) {
        for (CCPoint point: _ccPoints) {
            point.x += x;
        }
    }

    public void setColor(){
        for (Label label:_labels) {
            label.setColor(new CCColor3B(255,255,255));
            Log.d("Size", ""+label.getHeight());
        }
    }

    public void move(Label label, int i){
        /*delta[1] += _velocity;
        setCCPointX(_velocity);
        for (int i = 0; i < _labels.size(); i++) {
            _labels.get(i).setPosition(_ccPoints.get(i).x, _ccPoints.get(i).y*100);
        }
        updateTile();*/
        setCCPointX(_velocity);
        label.setPosition(_ccPoints.get(i).x, _ccPoints.get(i).y*100);
        removeLabel(i);
    }

    private void removeLabel(int i) {
        /*if (Math.abs(delta[1]) > _repeat) {
            _labels.add(Label.label(_text, "", 85));
            delta[1] = 0;
        }*/
        if (_velocity < 0) {
            if (_labels.get(i).getPositionX() + _labels.get(i).getWidth()/2 < 0) {
                _labels.remove(i);
                Log.d("Tile", _labels.size()+"");
            }
        }
        else if(_labels.get(i).getPositionX() - _labels.get(i).getWidth()/2 > _size.getWidth()) {
            _labels.remove(i);
            Log.d("Tile", _labels.size()+"");
        }
    }

    public boolean getXLimit(Label label) {
        if (_velocity > 0) {
            if (label.getPositionX() - label.getWidth()/2 > 100) {
                return true;
            }
        }
        else if(label.getPositionX() + label.getWidth()/2 < _size.getWidth() - 100) {
            return true;
        }
        return false;
    }
}
