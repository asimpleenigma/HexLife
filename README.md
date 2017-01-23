# HexLife
Like Conway's Game of Life, but on a hex grid.

This is still a work in progress. Pause/Play, step, clear, and randomize functions are working fine, but the cells cannot be toggled by the user. 
The SearchWorlds file has code to create all possible rulesets, automatically run them, and run some heuristic tests on them. The heuristics can decently be used to filter out most rulesets that are uninteresting or difficult to look at. However, acedemics have not examined hex automata nearly as much as square ones so the best rulesets are surely yet to be found. The initial results look exciting tho.

