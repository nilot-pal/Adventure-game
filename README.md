# Adventure-game
The game starts off with you outside in your driveway at night, and the goal of this scenario is to get into your house. The front door to your house is locked, and you don't have your key. You can go north to your garage, but the side door to your house (in the garage) is also locked.
![image](https://github.com/nilot-pal/Adventure-game/assets/72824334/32a3d330-4d82-4e6a-8604-bb05d8ea08da)
## Here are the 8 items of this scenario, sorted by their initial locations:

### front yard items:
#### front-door
#### wall-lantern (container)
#### fake-rock (container)
  - spare-key (content)
### garage items:
#### side-door
#### screw-driver
#### shoe-box (container)
  - light-bulb (content)

The pink circle in the front yard is a fake-rock, which is actually a closed container hiding your spare-key. The spare-key is not accessible until you open the fake-rock. However, the wall-lantern has no light-bulb, so it's dark here, and you can't open anything in the dark. Therefore you must fix the wall-lantern by using a new light-bulb.

To do this, you must first open the wall-lantern, by using a screw-driver. The screw-driver is in the garage, and so is a new light-bulb. However, the light-bulb is inside the shoe-box, which is closed. So you must open the shoe-box before you can take the light-bulb.

Once you've fixed the wall-lantern, you can retrieve the spare-key from the fake-rock. You can then use the spare-key to unlock the front-door and/or the side-door. Either way will let you finally enter your house, and thus solve this scenario.
#### Note: The item names must all be exactly as they appear above. The hyphens are necessary because the parser only allows the player to enter two words per command, each word separated by a space. The first word is a command, and the second word is interpreted (or ignored) by the command processor.

#### Note: Doors only reside within a single room, and operate only in one direction. The font-door is in the font yard, and the side-door is in the garage, as shown in the diagram above. When locked, these doors inhibit movement into the house. However, they're not considered at all when moving from inside the house to the front yard or to the garage.
