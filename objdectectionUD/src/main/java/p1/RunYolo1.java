package p1;

import java.io.File;
import java.util.List;

import javax.management.loading.PrivateClassLoader;

import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.utils.ParameterTool;
import org.deeplearning4j.nn.layers.objdetect.DetectedObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.flink.api.java.DataSet;


public class RunYolo1 {

    private static String videoFileName;

    static {
        videoFileName = System.getProperty("user.home") + "/bbdc/videoSample2.mp4";
    }
    public static void main(String[] args) throws Exception {
        final ParameterTool params = ParameterTool.fromArgs(args);
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
        env.getConfig().setGlobalJobParameters(params);
        //Yolo1 yolo=null;

        //Yolo yolo = null;
        Logger log = LoggerFactory.getLogger(RunYolo.class);

        VideoPlayer videoPlayer = new VideoPlayer();
        videoPlayer.startRealTimeVideoDetection(env,
                params,
                videoFileName,
                Speed.FAST, true);
        
        
    }
}
