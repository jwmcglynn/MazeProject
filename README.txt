Maze Project for CS 171

by Jeff McGlynn, #72846979
and Jesus Quezada, #50022121

November 29th, 2010.

----

This submission includes only the code that is used in the final generator and solver.

Please see the write-up for more details and analysis.


Generator:
ImprovedWilsonGenerator - Generates mazes using Wilson's algorithm and then applies a post-processing step to make them more difficult for computer solvers.

Solver:
AdaptiveAStarSolver - Solves mazes using a bidirectional A* search with an enhanced heuristic.  The solver learns from past solutions and weights the heuristic based on those statistics.

----

Notes:
- Please increase the memory available for java by setting -Xmx1024m.  The generator is designed to trigger worst-case performance for breadth-first search and without this setting unoptimized solvers may run out of memory at 500x500 sizes.
- The generator runs in 2-4 seconds per maze, and runs in less than 6 minutes for 100 500x500 mazes on our test system.
- The solver runs in 1-2 seconds on our test system.
