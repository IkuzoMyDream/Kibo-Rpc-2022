package jp.jaxa.iss.kibo.rpc.sampleapk;

import android.util.Log;

import jp.jaxa.iss.kibo.rpc.api.KiboRpcService;

import gov.nasa.arc.astrobee.Result;
import gov.nasa.arc.astrobee.types.Point;
import gov.nasa.arc.astrobee.types.Quaternion;

import org.opencv.core.Mat;

/**
 * Class meant to handle commands from the Ground Data System and execute them in Astrobee
 */

public class YourService extends KiboRpcService {
    private final String TAG = this.getClass().getSimpleName();
    @Override
    protected void runPlan1(){
        Log.i(TAG,"start mission");
        final int LOOP_MAX = 5;
        final int LOOP_MIN = 5;
        int loopcounter = 0;
        // the mission starts
        api.startMission();
        // move to a point 1

        Point point = new Point(10.71000f, -7.77500f, 4.48000f);
        Quaternion quaternion = new Quaternion(0f, 0.707f, 0f, 0.707f);
        Result result =  api.moveTo(point, quaternion, false);

        while (!result.hasSucceeded()&& loopcounter<LOOP_MAX){
            result = api.moveTo(point,quaternion,true);
            ++loopcounter;
        }

        api.reportPoint1Arrival();
        api.flashlightControlFront(1);
        // get a camera image
        Mat image1 = api.getMatNavCam();
        api.saveMatImage(image1, "filename1.png");

        // irradiate the laser
        api.laserControl(true);

        // take target1 snapshots
        api.takeTarget1Snapshot();

        // turn the laser off
        api.laserControl(false);
        api.flashlightControlFront(0);

        point = new Point(11.27460f, -8.73813f, 4.48000f);
        quaternion = new Quaternion(0f, 0f, -0.707f, 0.707f);
        result =  api.moveTo(point, quaternion, false);

        while (!result.hasSucceeded()&& loopcounter>LOOP_MIN){
            result = api.relativeMoveTo(point, quaternion, true);
            --loopcounter;
        }


        point = new Point(11.27460f, -9.61000f, 4.48000f);
        quaternion = new Quaternion(0f, 0f, -0.707f, 0.707f);
        result =  api.moveTo(point, quaternion, false);


        while (!result.hasSucceeded()&& loopcounter<LOOP_MAX){
            result = api.moveTo(point,quaternion,true);
            ++loopcounter;
        }



        //point 2
        point = new Point(11.20410f, -8.92284f, 5.47031f);
        quaternion = new Quaternion(0f, 0f, -0.707f, 0.707f);
        result =  api.moveTo(point, quaternion, false);

        while (!result.hasSucceeded()&& loopcounter>LOOP_MIN){
            result= api.moveTo(point,quaternion,true);
            --loopcounter;

        }
        api.flashlightControlFront(1);
        Mat image2 = api.getMatNavCam();
        api.saveMatImage(image2,"filename2.png");

        // irradiate the laser
        api.laserControl(true);

        // take target1 snapshots
        api.takeTarget2Snapshot();

        // turn the laser off
        api.laserControl(false);
        api.flashlightControlFront(0);

        point = new Point(10.76150f, -8.97500f, 5.47031f);
        quaternion = new Quaternion(0f, 0f, -0.707f, 0.707f);
        result =  api.moveTo(point, quaternion, false);


        while (!result.hasSucceeded()&& loopcounter<LOOP_MAX){
            result= api.moveTo(point,quaternion,true);
            ++loopcounter;
        }

        // go to goal

        point = new Point(11.27460f, -7.89178f, 4.96538f);
        quaternion = new Quaternion(0f, 0f, -0.707f, 0.707f);
        result =  api.moveTo(point, quaternion, false);

        while (!result.hasSucceeded()&& loopcounter>LOOP_MIN){
            result = api.moveTo(point,quaternion,true);
            --loopcounter;
        }

        api.reportMissionCompletion();
    }

}