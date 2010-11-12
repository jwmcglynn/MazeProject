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

