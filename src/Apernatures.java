/****************************************************

Aper[na]tures
Author: Joshua Parker
Date: 2011

Interactive paneling tool developed for use in
design of structurally integrated prefabricated 
concrete panel system for design competition
of airship hanger in anhui, china for competition 
submission completed in collaboration with OPEN 
Architecture and the Chinese Academy of Building 
Research (CABR)

Implementated with Simple Model-View-Controller 
(MVC) pattern:

Gui - is a wrapper for controlP5, which supplies 
  2d user interface toolkit
MyControlListener - recieves ui events, ids and 
  routes them to controller
Controller - contains methods for updating data 
  model & controlling view(s)* 
View - contains the scene(empty space and camera), 
  imported 3d base model, and data model defining 
  panel system. View calls draws to screen via 
  viewState interface, though only one viewState 
  is implemented: View3d, a basic 3d view.
Hanger - data model defining panel system, composed 
  of subclass skin, which is composed of panels. 
  Panels are basically two sets of parameters: one 
  which defines the panel's size and position in 
  space, and another that defines the size and 
  relative position of a single rectangular void 
  in the panel. Panel also contains data from ecotect 
  about the amount of direct and diffuse radiation
  each panel recieves.

Uses the following processing libraries:

controlP5 - gui toolkit 
- http://www.sojamo.de/libraries/controlP5/
Proscene - 3d scene library 
- http://code.google.com/p/proscene/
0bjloader - 3d object loader 
- http://code.google.com/p/saitoobjloader/

Inheritted License: GPL, V3 

Fourth Natures Lab | www.fnl.com

****************************************************/
import processing.core.*;
import controlP5.*;
import processing.opengl.*;
import controller.Listener;
import controller.Controller;
import model.Hanger;
import view.*;
import gui.*;

public class Apernatures extends PApplet{
	
	private static final long serialVersionUID = 1L;

	//CONSTANTS
	int U = 37; //number of modules in U direction
	int V = 42; //number of modules in V direction
	float D = 10f; //panel lengh/width dimension, D (meters)
	float T = .5f; //thickness, T of frame (meters)
	float SUBDIVS = 20f; //subdivide panel for snapping
	
	//path to tabular data from ecotect analyses
	String PATH = "data/ecotectData.txt";
	
	//objects
	Listener myListener;
	Controller myController;
	Hanger myhanger;
	View myview;
	Gui mygui;
	
	public void setup(){
	  size(1280, 1020, PGraphicsOpenGL.OPENGL);
	  background(0);
	  noStroke();
	  //smooth();
	  
	  //MODEL: Parametric Data for panel system
	  float snap = (float)((D-(T*2))/SUBDIVS);
	  myhanger = new Hanger(this, U, V, D, T, snap, PATH);
	  
	  //VIEW: draws a representation of model to screen
	  myview = new View(this, myhanger);
	  
	  //GUI: needs to access view scene for drawing 2d gui on top of 3d scene
	  mygui = new Gui(this, myview); 
	   
	  //CONTROLLER: updates model & views in response to ui events routed to it
	  myController = new Controller(this, myhanger, mygui, myview);
	 
	 //LISTENER/ROUTER: recieves ui events, ids and routes them to controller
	  myListener = new Listener(this, myController);  
	  
	}
	
	public void draw(){
	  background(50);
	  
	  //Continually redraw view and gui via controller
	  myController.update();
	}
	
	//required to run as application
	public static void main(String args[]) {
	PApplet.main(new String[] { "--present", "Apernatures" });
	}
	
	//send ui events to listener, so it route them to controller
	public void controlEvent(ControlEvent theEvent){myListener.controlEvent(theEvent);}
	public void mousePressed(){myListener.mousePressed(mouseX, mouseY);}
	public void keyPressed(){myListener.keyPressed(key);}

}





