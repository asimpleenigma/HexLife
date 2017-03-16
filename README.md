# HexLife
Like Conway's Game of Life, but on a hex grid.

Pause/Play, step, clear, and randomize functions are working fine, but the cells cannot be toggled by the user yet. I plan to eventually combine this into the same GUI as my square automata, but want to develop this more first.

The SearchWorlds file has code to create all possible rulesets, automatically run them, and calculate some statistics on them to be used as heuristics. The result is stored in rule_sets.ser. How these rulesets are filtered by these stats can be changed in HexModel.java. The heuristics can decently be used to filter out most rulesets that are uninteresting or difficult to look at. However, acedemics have not examined hex automata nearly as much as square ones so the best rulesets are surely yet to be found. The initial results look exciting tho.


![Alt Text](/ScreenShots/StarSeed.png)

![Alt Text](/ScreenShots/NightLattice.png)
