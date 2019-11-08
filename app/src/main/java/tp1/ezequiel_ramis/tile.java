package tp1.ezequiel_ramis;

import android.os.CountDownTimer;
import android.util.Log;

import org.cocos2d.nodes.CocosNode;
import org.cocos2d.nodes.Label;
import org.cocos2d.types.CCColor3B;
import org.cocos2d.types.CCPoint;
import org.cocos2d.types.CCSize;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class tile {
    private String _text;
    private float _y;
    private CCSize _size;
    private ArrayList<gameLabel> _gameLabels;
    public gameLabel labelAtrr;
    private float _velocity;
    private int indexLastLabel = 0;
    CocosNode _coconode;


    public tile(String text, float y, CCSize size, CocosNode cocosNode) {
        _y = y;
        _text = text;
        _size = size;
        _gameLabels = new ArrayList<gameLabel>();
        _coconode = cocosNode;
        //setColor();
    }

    public void set_velocity(float velocity){
        _velocity = velocity;
    }

    public gameLabel getGameLabelAtrr() {
        return new gameLabel(_text, _size, _velocity);
    }

    public ArrayList<gameLabel> getGameLabels() {
        return _gameLabels;
    }

    public void addGameLabels(double t) {
        TimerTask addGameLabel = new TimerTask() {
            @Override
            public void run() {
                _gameLabels.add(new gameLabel(_text, _size, _velocity));
                gameLabel label =  _gameLabels.get(indexLastLabel);
                label.getLabel().setColor(new CCColor3B(255, 255, 255));
                _coconode.addChild(label.getLabel());
                Log.d("Label", _gameLabels.get(indexLastLabel).getLabel().getPositionX()+" "+_gameLabels.get(indexLastLabel).getLabel().getPositionY());
            }
        };
        TimerTask moveGameLabel = new TimerTask() {
            @Override
            public void run() {
                for (gameLabel label: _gameLabels) {
                    label.move(500);
                }
            }
        };
        Timer timer = new Timer();
        timer.schedule(addGameLabel, 0, Math.round(t)*1000);
        timer.schedule(moveGameLabel, 0, 1000/60);
    }
}
