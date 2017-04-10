# Piu Piu #

### Information ###
This is a very basic game written for my final project in my computer science 1 
class.  It's not the best example of code by any means, but it's at least a fun 
game.

This game was developed as a NetBeans project, but can probably be used with any 
IDE.

When I demonstrated this in class, I used a font called Joystix and I used the 
song "Hello Earth!" by Chipzel in this game.  However, I don't have any right 
to distribute those publically, so I have replaced the font with an openly 
usable one and the song with a work-in-progress of my own.  The song was 
actually originally meant to be used in this project, but I never completed it.  
It starts out fairly nicely if you ask me, but it falls apart at the end because 
I ran into a creative block with where to take it.  I'm not providing the 
project file for my own song, but you can replace the bgm.mp3 file and recompile 
with any song you'd like, and I'll probably pick back up the project and finish 
the music at some point.

This game was coded over the course of four days, and it likely would have been 
better had I put more time into its development (and if it weren’t a school 
project that required using JavaFX), but I’m still rather proud of it, and it’s 
something I can enjoy wasting my time playing.

I may continue development of this for a bit, but chances are that if I do 
anything big with it, I'm going to rewrite it using an actual game library like 
LibGDX. 

### How to Play ###
Your ship is moved using either the arrow or WASD keys.  Space fires bullets.  
It pretty much comes down to just tapping space constantly and trying to dodge 
the bullets, which provides a surprising amount of fun.

The small enemies (alien larvae) shoot projectiles toward the player’s current 
location, and the large enemies shoot straight ahead.  The “homing missiles” 
produced by the small enemies are abundant, but more often easily dodged and 
deal less damage.  Perhaps counter-intuitively, your real concern should be 
over the large enemies and their more powerful ammunition.  They will take your 
health down very quickly and often you will find yourself being pushed into 
their projectiles while trying to dodge those of the small enemies.

Player health is displayed as a bar at the top of the screen, and enemies’ 
healths are displayed above their heads similarly.  The player earns a score 
(500 points per large enemy, and 100 per small enemy) which is displayed in the 
upper-right.

There is no technical win condition.  You lose when your health drops to 0, and 
you keep racking up points until then.  The goal is whatever you set for 
yourself.  And thankfully, most people keep increasing their goals, leading 
them to play my game far longer than it deserves to be played.

### Licensing Information ###
The font used is PressStart2P by codeman38 and is licensed under the SIL Open 
Font License, v1.1, included as OFL.txt.

The sprites are Copyright © 2014 William Barger, licensed under CC-BY-SA.

All code is licensed under the MIT License, included as LICENSE.txt.
