# "make" to compile the files
# "make run" to run the game in appletviewer
# "make clean" to clean all .class files

my.class: BirdSimPackage
	javac ./BirdSimPackage/*.java ./BirdSimPackage/Enemies/*.java
	
run: my.class 
	appletviewer BirdSimPackage.html

make clean:
	rm ./BirdSimPackage/*.class
