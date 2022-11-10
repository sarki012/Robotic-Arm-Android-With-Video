package com.esark.roboticarm;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.esark.framework.Game;
import com.esark.framework.Graphics;
import com.esark.framework.Input;
import com.esark.framework.Pixmap;
import com.esark.framework.Screen;
import com.esark.video.FloatingWindow;

import static com.esark.roboticarm.Assets.blueJoystick;
import static com.esark.roboticarm.Assets.excavatorTabletLandscapeBackground;
import static com.esark.roboticarm.Assets.redJoystick;
import static com.esark.roboticarm.Assets.robotPortraitBackground;

import java.util.List;

public class GameScreen extends Screen implements Input {
    Context context = null;
    int x = 0;
    int y = 0;
    public static int clawOpen = 0;
    public static int clawClosed = 0;
    public static int left = 0;
    public static int right = 0;
    public static int up = -1;
    public static int down = -1;
    public static int out = 0;
    public static int in = 0;
    public static int record = 0;
    public static int repeat = 0;

    public Pixmap backgroundPixmap = null;
    private static final int INVALID_POINTER_ID = -1;
    // The ‘active pointer’ is the one currently moving our object.
    private int mActivePointerId = INVALID_POINTER_ID;


    //Constructor
    public GameScreen(Game game) {
        super(game);
    }

    @Override
    public void update(float deltaTime, Context context) {
        //framework.input
        List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
        updateRunning(touchEvents, deltaTime, context);
    }

    private void updateRunning(List<TouchEvent> touchEvents, float deltaTime, Context context) {
        //updateRunning() contains controller code of our MVC scheme
        //Here is where we draw to the canvas
        Graphics g = game.getGraphics();
        backgroundPixmap = Assets.robotPortraitBackground;
        g.drawPortraitPixmap(backgroundPixmap, 0, 0);
        g.drawTestRect(2250, 400);
        int len = touchEvents.size();
        for (int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            mActivePointerId = event.pointer;
            //The pointer points to which finger or thumb. The first finger to touch is 0
            Log.d("ADebugTag", "mActivePointerId: " + mActivePointerId);
            Log.d("ADebugTag", "event.x: " + event.x);
            Log.d("ADebugTag", "event.y: " + event.y);
            x = event.x;          //Get the x and y coordinates of the first touch
            y = event.y;
            if (event.type == TouchEvent.TOUCH_UP) {    //A thumb is lifted. The following code figures out in which region of the screen the thumb was lifted
                if(x > 175 && x < 675 && y > 2600 && y < 3100){
                    clawOpen = 0;
                }
                else if(x > 600 && x < 1100 && y > 3550 && y < 4050){
                    left = 0;
                }
                else if(x > 2300 && x < 2800 && y > 3550 && y < 4050){
                    right = 0;
                }
                else if(x > 1120 && x < 1620 && y > 3075 && y < 3575){
                    up = 0;
                }
                else if(x > 1120 && x < 1620 && y > 4100 && y < 4600){
                    down = 0;
                }
                else if(x > 1825 && x < 2325 && y > 4100 && y < 4600){
                    in = 0;
                }
                else if(x > 1825 && x < 2325 && y > 3075 && y < 3575){
                    out = 0;
                }
                else if(x > 2800 && x < 3300 && y > 2600 && y < 3100){
                    clawClosed = 0;
                }
                else if(x > 950 && x < 1450 && y > 2100 && y < 2600){
                    record = 0;
                }
                else if(x > 1900 && x < 2400 && y > 2100 && y < 2600){
                    repeat = 0;
                }

            }
            if (event.type == TouchEvent.TOUCH_DRAGGED || event.type == TouchEvent.TOUCH_DOWN) {    //A thumb is pressed or dragging the screen
                //The following code determines which region of the screen the thumb was pressed
                if (x > 350 && x < 850 && y > 150 && y < 650) {       //Back button
                    //Back Button Code Here
                     backgroundPixmap.dispose();
                    Intent intent2 = new Intent(context.getApplicationContext(), RoboticArm.class);
                    context.startActivity(intent2);
                    return;
                }
                else if(x > 175 && x < 675 && y > 2600 && y < 3100){
                    clawOpen = 1;
                }
                else if(x > 600 && x < 1100 && y > 3550 && y < 4050){
                    left = 1;
                }
                else if(x > 2300 && x < 2800 && y > 3550 && y < 4050){
                    right = 1;
                }
                else if(x > 1120 && x < 1620 && y > 3075 && y < 3575){
                    up = 1;
                }
                else if(x > 1120 && x < 1620 && y > 4100 && y < 4600){
                    down = 1;
                }
                else if(x > 1825 && x < 2325 && y > 4100 && y < 4600){
                    in = 1;
                }
                else if(x > 1825 && x < 2325 && y > 3075 && y < 3575){
                    out = 1;
                }
                else if(x > 2800 && x < 3300 && y > 2600 && y < 3100){
                    clawClosed = 1;
                }
                else if(x > 950 && x < 1450 && y > 2100 && y < 2600){
                    record = 1;
                }
                else if(x > 1900 && x < 2400 && y > 2100 && y < 2600){
                    repeat = 1;
                }
             //   else if(x > 2250 && x < 2750 && y > 400 && y < 900){
               //     startService(new Intent(this, FloatingWindow.class));
                //}
            }
        }
    }

    @Override
    public void present ( float deltaTime){
        Graphics g = game.getGraphics();
    }

    @Override
    public void pause () {

    }

    @Override
    public void resume () {

    }

    @Override
    public void dispose () {
    }

    @Override
    public boolean isTouchDown(int pointer) {
        return false;
    }

    @Override
    public int getTouchX(int pointer) {
        return 0;
    }

    @Override
    public int getTouchY(int pointer) {
        return 0;
    }

    @Override
    public float getAccelX() {
        return 0;
    }

    @Override
    public float getAccelY() {
        return 0;
    }

    @Override
    public float getAccelZ() {
        return 0;
    }

    @Override
    public List<TouchEvent> getTouchEvents() {
        return null;
    }
}
