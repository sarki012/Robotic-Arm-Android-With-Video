package com.esark.roboticarm;



import static com.esark.roboticarm.GameScreen.clawClosed;
import static com.esark.roboticarm.GameScreen.clawOpen;
import static com.esark.roboticarm.GameScreen.down;
import static com.esark.roboticarm.GameScreen.in;
import static com.esark.roboticarm.GameScreen.left;
import static com.esark.roboticarm.GameScreen.out;
import static com.esark.roboticarm.GameScreen.record;
import static com.esark.roboticarm.GameScreen.repeat;
import static com.esark.roboticarm.GameScreen.right;
import static com.esark.roboticarm.GameScreen.up;

import android.bluetooth.BluetoothSocket;
import android.os.Handler;

import android.os.SystemClock;
import android.util.Log;

import com.esark.framework.AndroidGame;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


//Connected Thread handles Bluetooth communication
public class ConnectedThread extends Thread {
    private final BluetoothSocket mmSocket;
    private final InputStream mmInStream;
    private final OutputStream mmOutStream;
    private final Handler mHandler;

    public ConnectedThread(BluetoothSocket socket, Handler handler) {
        mmSocket = socket;
        mHandler = handler;
        InputStream tmpIn = null;
        OutputStream tmpOut = null;

        // Get the input and output streams, using temp objects because
        // member streams are final
        try {
            tmpIn = socket.getInputStream();
            tmpOut = socket.getOutputStream();
        } catch (IOException e) { }

        mmInStream = tmpIn;
        mmOutStream = tmpOut;
    }

    @Override
    public void run() {
        int bytes; // bytes returned from read()
        // Keep listening to the InputStream until an exception occurs
        while (true) {
            try {
                // Read from the InputStream
                bytes = mmInStream.available();
                byte[] buffer = new byte[60];
                if (bytes != 0) {
                    //SystemClock.sleep(100); //pause and wait for rest of data. Adjust this depending on your sending speed. Originally 100
                    bytes = mmInStream.read(buffer, 0, 50); // record how many bytes we actually read
                    mHandler.obtainMessage(AndroidGame.MESSAGE_READ, bytes, -1, buffer)
                            .sendToTarget(); // Send the obtained bytes to the UI activity

                }
            } catch (IOException e) {
                e.printStackTrace();

                break;
            }

            if(clawOpen == 1){
                write("co");
                SystemClock.sleep(500);
            }
            if(clawClosed == 1){
                write("cc");
                SystemClock.sleep(500);
            }
            if(up == 1){
                write("u");
                SystemClock.sleep(500);
            }
            if(down == 1){
                write("d");
                SystemClock.sleep(500);
            }
            if(left == 1){
                write("l");
                SystemClock.sleep(500);
            }
            if(right == 1){
                write("r");
                SystemClock.sleep(500);
            }
            if(out == 1){
                write("o");
                SystemClock.sleep(500);
            }
            if(in == 1){
                write("i");
                SystemClock.sleep(500);
            }
            if(record == 1){
                write("rec");
                SystemClock.sleep(500);
            }
            if(repeat == 1){
                write("rep");
                SystemClock.sleep(500);
            }

        }
    }

    public void write(String input) {
        byte[] bytes = input.getBytes();           //converts entered String into bytes
        try {
            mmOutStream.write(bytes);
        } catch (IOException e) { }
    }

    /* Call this from the main activity to shutdown the connection */
    public void cancel() {
        try {
            mmSocket.close();
        } catch (IOException e) { }
    }
}