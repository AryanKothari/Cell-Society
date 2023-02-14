# cell society
## TEAM NUMBER 06
## Trevon Helm (tmh85), Aryan Kothari (ak616), Charles Turpin (cht16)




This project implements a cellular automata simulator.


### Timeline


* Start Date: February 1st, 2023


* Finish Date: February 14th, 2023


* Hours Spent: 40 Hours




### Attributions


* Resources used for learning (including AI assistance)
* https://stackoverflow.com/questions/3793650/convert-boolean-to-int-in-java
  *https://stackoverflow.com/questions/2163045/how-to-remove-line-breaks-from-a-file-in-java
* https://docs.oracle.com/javafx/2/layout/builtin_layouts.htm
* https://stackoverflow.com/questions/10368202/java-how-do-i-simulate-probability#10368388
*
*https://docs.oracle.com/javase/8/javafx/api/javafx/animation/Animation.html#playFrom-javafx.util.Duration-
*https://codegym.cc/groups/posts/java-extends-keyword-with-examples


* Resources used directly (including AI assistance)
* https://www.geeksforgeeks.org/randomly-select-items-from-a-list-in-java/
  *https://stackoverflow.com/questions/28290814/how-to-slow-down-javafx-animation
  *https://stackoverflow.com/questions/12979941/recycling-a-javafx-animation




### Running the Program


* Main class:
   * Holds a default configuration and starts the UI subclass.
   * Really only here to start the program.


* Data files needed:
   * XML files that follow the format detailed in the "XML_Template.xml" file.


* Interesting data files:
   * Each simulation has an XML file with the phrase "random" in it. This will create
     a random grid of cell states specific to that simulation, which will typically
     result in something interesting!


* Key/Mouse inputs:
   * Left click on the buttons on screen to interact with the simulations.






### Notes/Assumptions


* Assumptions or Simplifications:
  *  Cell being a rectangle: we assumed that the cell would always be a rectangle/square and therefore had it extend that shape. However, we noticed in the future features that it could take on other shapes which we hadn’t considered earlier. This would have required a redesign from our end of the CellView class.
  * Simplified Wa-Tor to not have any reproduction.
  * XML Tags are hard-coded into the code– couldn’t figure out a cleaner way to get them. This means that the required tags will always be required with FileInfoXML.
  * decideSimulation (and decideNeighborhood) work with Switch statements. There’s probably a cleaner way to decide between the different subclasses, but this was simple enough to work for us.



* Known Bugs:
   * Wa-Tor simulation is not entirely working due to a lack of reproduction.
   * Although Neighborhood subclasses can be implemented, the method that decides
     between Neighborhood subclasses is not properly connected with Simulation yet.
   * The step button works, but only if pressed prior to play, and after clear. Trying to press play, then step does not work, and trying to press step after pause does not work.
* Features implemented:
* Complete implementation of Game of Life, Percolation, Schelling’s Segregation, and Spreading Fire. Partial implementation of Wa-Tor.
* Complete file parsing of XML files, allowing customization of initial configurations, grid sizes, extra values, and biographical information.
* A UI that allows you to view the simulation happening in real time. It also allows you to view biographical information, pause simulation, speed up and slow down simulation, and load new simulations seamlessly. Step button works if pressed prior to play or after clear (buggy but it steps through the entire program this way).


* Features unimplemented:
* All simulations added in the Change portion of the assignment ( Falling Sand/Water, Foraging Ants, Langton’s Loop, SugarScape)
* Edge and Shape Options
* UI Exception support
* Allowing any number of simulations to be viewed at once
* Dynamically adjusting values in a simulation


* Noteworthy Features:
* Properties file support for Error Messages, Button labels, and colors.
* Extendable classes for future changes.
* Preliminary support for additional neighborhoods.
* Completely customizable Simulation states and grid sizes.




### Assignment Impressions


*tmh85: The assignment was really cool, though the changes did feel quite impossible! The satisfaction of seeing a working Cellular Automata was quite immense, though the road there was bumpier than I anticipated.


*cht16: The project was a very good lesson into the value of abstractions and having multiple classes. UI was a painful yet enlightening process, and now I cannot even fathom having less than 5-6 classes for any program I write ever again. The extra features seemed insane to even attempt given how hard it was just getting the basics to work but they seem interesting to implement.

*ak616: I thought the assignment was a really good introduction to practising design. It was the first time I had to work in a team and really think through design multiple steps in advance. Yet, there were implementations that surprised me and that we weren't prepared to incorporate in the way we originally decided the design. It was a really good lesson in not making too many assumptions in advance.






