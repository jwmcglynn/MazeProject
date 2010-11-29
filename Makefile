JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class: 
	$(JC) $(JFLAGS) $*.java

CLASSES = \
	maze/Pair.java \
	maze/Vertex.java \
	maze/Maze.java \
	CPair.java \
	MazeGenerator.java \
	MazeSolver.java \
	Test.java 

default: classes

classes: $(CLASSES:.java=.class)

clean: 
	$(RM) *.class maze/*.class
	$(RM) MazeProject.zip

dist: MazeProject.zip

MazeProject.zip: *.java README.txt writeup
	# Remove old version.
	$(RM) -r jmcglynn

	# Create new directory and copy files in.
	mkdir jmcglynn
	cp -r $^ jmcglynn

	## Zip up.
	-zip -9 -X -FS -r MazeProject.zip jmcglynn -x '*.DS_Store'