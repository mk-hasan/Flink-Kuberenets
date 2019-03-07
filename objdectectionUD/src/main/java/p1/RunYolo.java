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


public class RunYolo {
    

    public static void main(String[] args) throws Exception {

    	DataSet<DetectedObject> resultflink;
    	
    	final ParameterTool params = ParameterTool.fromArgs(args);
    	ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
    	env.getConfig().setGlobalJobParameters(params); 
    	Yolo yolo=null;
    	//Yolo yolo = null;
    	DataSet<DetectedObject> finalresult;
    	Logger log = LoggerFactory.getLogger(RunYolo.class);
    	
    	VideoPlayer videoPlayer = new VideoPlayer();
 
	    videoPlayer.startRealTimeVideoDetection(env,
				                                params,
				                   "/home/anhletuan/Projects/Flink-ODS/objdectectionUD/src/main/resources/videoSample2.mp4",
											    Speed.FAST,
				                      true);
		//env.execute("car class detection");
		//finalresult = yolo.result();
		
		//resultflink = env.fromCollection(finalresult);
		//finalresult.print();
    	//System.out.println(finalresult);
   
	    //env.execute();
}
}
