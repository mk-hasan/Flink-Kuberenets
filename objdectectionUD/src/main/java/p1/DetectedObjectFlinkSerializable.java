package p1;

import org.deeplearning4j.nn.layers.objdetect.DetectedObject;
import org.nd4j.linalg.api.ndarray.INDArray;

import java.io.Serializable;

public class DetectedObjectFlinkSerializable extends DetectedObject implements Serializable {

    public DetectedObjectFlinkSerializable(DetectedObject detectedObject){
        this(detectedObject.getExampleNumber(),
             detectedObject.getCenterX(),
             detectedObject.getCenterY(),
             detectedObject.getHeight(),
             detectedObject.getWidth(),
             detectedObject.getClassPredictions(),
             detectedObject.getConfidence());

    }

    public DetectedObjectFlinkSerializable(int exampleNumber, double centerX, double centerY, double width, double height, INDArray classPredictions, double confidence) {
        super(exampleNumber, centerX, centerY, width, height, classPredictions, confidence);
    }


}
