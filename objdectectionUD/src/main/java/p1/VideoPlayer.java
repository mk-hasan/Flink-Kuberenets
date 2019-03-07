package p1;

import lombok.extern.slf4j.Slf4j;

import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.utils.ParameterTool;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.OpenCVFrameConverter;

import java.io.File;
import java.util.concurrent.atomic.AtomicInteger;

import static org.bytedeco.javacpp.opencv_highgui.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Slf4j
public class VideoPlayer {
	private static Logger log = LoggerFactory.getLogger(VideoPlayer.class);

    private static final String AUTONOMOUS_DRIVING = "Autonomous Driving(TU Berlin)";
    private String windowName;
    private volatile boolean stop = false;
    private Yolo yolo;
    private final OpenCVFrameConverter.ToMat converter = new OpenCVFrameConverter.ToMat();
    public final static AtomicInteger atomicInteger = new AtomicInteger();


    public void startRealTimeVideoDetection(ExecutionEnvironment env, ParameterTool params, String videoFileName, Speed selectedIndex, boolean yoloModel) throws java.lang.Exception {
        log.info("Start detecting video " + videoFileName);
        
        int id = atomicInteger.incrementAndGet();
        windowName = AUTONOMOUS_DRIVING + id;
        log.info(windowName);
        yolo = new Yolo(env,params,selectedIndex, yoloModel);
        startYoloThread(env,params);
        runVideoMainThread(videoFileName, converter);
    }

    private void runVideoMainThread(String videoFileName, OpenCVFrameConverter.ToMat toMat) throws FrameGrabber.Exception {
    	File videoFile = new File(videoFileName);
        FFmpegFrameGrabber grabber = initFrameGrabber(videoFileName);
        while (!stop) {
            Frame frame = grabber.grab();
            if (frame == null) {
                log.info("Stopping");
                stop();
                break;
            }
            if (frame.image == null) {
                continue;
            }
            yolo.push(frame);
            opencv_core.Mat mat = toMat.convert(frame);
            yolo.drawBoundingBoxesRectangles(frame, mat);
            imshow(windowName, mat);
            char key = (char) waitKey(20);
            // Exit this loop on escape:
            if (key == 27) {
                stop();
                break;
            }
        }
    }

    private FFmpegFrameGrabber initFrameGrabber(String videoFileName) throws FrameGrabber.Exception {
        FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(videoFileName);
        grabber.start();
        return grabber;
    }

    private void startYoloThread(ExecutionEnvironment env, ParameterTool params) {
        Thread thread = new Thread(() -> {
            while (!stop) {
                try {
                    yolo.predictBoundingBoxes(env,params);
                } catch (Exception e) {
                    //ignoring a thread failure
                    //it may fail because the frame may be long gone when thread get chance to execute
                }
            }
            yolo = null;
            log.info("YOLO Thread Exit");
        });
        thread.start();
    }

    public void stop() {
        if (!stop) {
            stop = true;
            destroyAllWindows();
        }
    }
}