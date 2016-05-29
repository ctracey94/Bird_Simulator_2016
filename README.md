# Bird_Simulator_2016
An action adventure game with a bird protagonist!

The project proposal (proposal.pdf) can be found at the top level of the repo.

Look for documentation in the repository wiki.


This is the source code for Bird_Simulator_2016.

File Contents:

	- Main.java
	- Entity.java
	- Bird.java
	- Environment.java

Main.java:
	This is the main engine of the game. It is where the main loop is run from

Entity.java:
	This is a super-class for all entities in the game (pc, npc, or object)
	It contains the logic for position and basic movement of all creatures or
	objects in the game.

Bird.java:
	This is a sub-class of Entity.java. It defines a bird entity which will be
	controlled by the player.

Environment.java:
	This is a file to store game environment variables and methods such as
	the floor and ceiling locations of the game world.
