# "make" to run the game
# "make clean" to clean all .class files

prog: my.class 
	appletviewer BirdSimPackage.html

my.class: BirdSimPackage
	javac ./BirdSimPackage/*.java ./BirdSimPackage/Enemies/*.java

make clean:
	rm ./BirdSimPackage/*.class
