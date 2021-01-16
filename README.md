# Flink-Kuberenets
## Run Deep learning object Detection with Flink program in kuberents cluster and 
### This is the object detection code with YOLO algorithm to detect object from video or image. You can create jar file from this java code and run into your flink clusters using ui or curl...


1. Create the Kuberenets cluster (you will find the instructions in kuberenets website)
1. Deploy the flink jobmanager and taskmanager in kuberenets cluster (You can reconfigure the configuration of jobmanager or task manager using the config file like jobmanager heap size or number of taskslots)
1. After deploying you will find the flink we ui and you can run any jar file or object detection jar file there and get the results. The object detection code has been modified to get the result in text format. 
