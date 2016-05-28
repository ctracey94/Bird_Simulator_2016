# make to compile Bird Sim
# make new to create a new ClickMe.jar
# make run to run Bird Sim
# make clean to clean all .class file

my.class: Animation ELements MainFrame
	javac Animation/*.java ELements/*.java MainFrame/*.java

new:
	jar -cvfm ClickMe.jar manifest.txt Animation/*.class Animation/resources/ Elements/*.class MainFrame/*.class

run:  
	java -jar ClickMe.jar

make clean:
	rm Animation/*.class ELements/*.class MainFrame/*.class
