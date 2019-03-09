# DATA TEST

## Objective: 
Test the candidate ability to industrialize, no data science skills or clustering results would be asses.
Data: Static geographical information of CityBike‘s stations in Brisbane (“Brisbane_CityBike.json”)

## Deliveries:

### Code:
In this repository, you can see all the code for the given test. 
All code sources can be found under **src/main** and all test sources can be found under **src/test**
### Readme:

The main job of the code is to read multipile lined json file, apply KMeans Clustering algorithm on the data set and write the result of algorithm on a specified output path. 
Since I wanted to have a generic program, there is a possibility to configure the program using **src/main/resources/init.properties**
- number.cluster=2  // number of cluster for KMeans Algorithm
- number.iterations=20  // number of iterations while KMeans Clustering
- number.partition=2  // number of partition of the dataframe that reads the json file
- file.path=src/main/resources/Brisbane_CityBike.json  //input file path
- output.path=KMeansModelOutput //output path for the result
- you can also add all the other configurations from : https://spark.apache.org/docs/latest/configuration.html

 In order to launch the program you have two choices: 
 

 - **Launch on local:**  
	 - It is enough to get the code on your host 
	 - Open the code with your coding editor (Intelijj or Eclipse)
	 - Configure your Run/Debug Configurations  and set the main class as **main.Main**
	 - Change your program settings from **src/main/resources/init.properties** as your needs
	 - 	Launch your program
	 
 - **Launch on your cluster:** 
	 - Get the code to your working environement
	 - Be sure that you have maven installed to your machine
	 - Go to the project directory and launch **mvn clean package**, that will generate you the **FAT Jar of the application**.
	 - Get the bash script from [src/main/resources/spark_submit.sh](https://github.com/mathieuozer/spark_test/blob/master/src/main/resources/spark_submit.sh "spark_submit.sh") and set the **SPARK_HOME** in the script
	 - You can launch the script on your driver host
	 - The usage is : 
		 - **./spark_submit.sh -c main.Main -j < pathToFatJar > -e < number of executors > -f < init.properties file which can be found under src/main/resources/init.properties and can be personilised > -d < deploy mode >** 
	 
### Output:
 directory with the clustering result can be found in **KMeansModelOutput** and can be changed from **src/main/resources/init.properties** by changing **output.path**

### Backlog:
**DONE:** 
- Read the json file and apply KMeans Algorithm
- Script in order to be able to launch the jar on the cluster
- Externalisation of the configuration of the program
- Unit Tests

 **TO DO:** 
 Since this program will be launched in a daily basis routine, the code must generate a FAT Jar which contains Oozie coordinator and the Jar of the file with the Oozie coordinator can be pushed into Hadopp cluster and the coordinator can be launched automatically once we put the files on the cluster. Maybe in the futur, better to automatize with Jenkins pipeline. 
