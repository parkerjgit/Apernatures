/****************************************************

Listener Class

Listener recieves ui events, parses them and routes 
them to controller for handling.

Part of:

Aper[na]tures
Author: Joshua Parker
Date: 2011

Inheritted License: GPL, V3 

****************************************************/
package controller;

import controlP5.ControlEvent;
import controlP5.ControlListener;
import processing.core.PApplet;

public class Listener implements ControlListener{

	//The parent PApplet
	PApplet p; 

	//PROPS
	Controller myController;

	//CONSTRUCTOR
	public Listener(PApplet parent, Controller MyController){
		p = parent;
		myController = MyController;
	}

	// PUBLIC METHODS ////////////////////////////////////////////////////

	//route slider events to controller via private handlers
	public void controlEvent(ControlEvent theEvent) {

		switch(theEvent.getController().getParent().getId()){
		case(1):
			//route g1 (shortcut info) event! 
			//empty so break
			break;
		case(2):
			//route g2 (noise sliders) event!
			g2ControlEvent(theEvent);
		break;
		case(3):
			//route g3 (effect sliders) event!
			g3ControlEvent(theEvent);
		break;
		case(4):
			//route g4 (camera dials) event!
			g4ControlEvent(theEvent);
		break;
		}
	}

	//route mouse events to controller
	//this should be replaced with focus on hover
	public void mousePressed(int mx, int my) {
		if (mx < 200) {
			myController.focusGui();
		} else {
			myController.focusScene();
		}
	}

	//route key press events to controller
	public void keyPressed(char key) {

		if(key == 't') {
			//toggle the texture listed in .mtl file
			myController.toggleTexture();
		}	    
		else if(key == 'm') {
			//toggle the material listed in .mtl file
			myController.toggleMaterial();
		}	    
		else if(key == 'f') {
			//toggle the fill for new shapes
			myController.toggleFill();
		}
	}

	// PRIVATE METHODS ////////////////////////////////////////////////////

	//parse group 2 (noise sliders) event!  *******************************
	private void g2ControlEvent(ControlEvent theEvent){

		float v;
		switch(theEvent.getController().getId()){
		case(1):
			v = theEvent.getController().getValue();
		myController.setNoiseInc(v);
		break;
		case(2):
			v = theEvent.getController().getValue();
		myController.setNoiseSeed((int)(v));
		break;
		}
	}

	//parse group 3 (effect sliders) event! *******************************
	private void g3ControlEvent(ControlEvent theEvent){

		float v;
		switch(theEvent.getController().getId()){
		case(5):
			v = theEvent.getController().getValue();
		myController.setPanelXcf(0,v);
		break;
		case(6):
			v = theEvent.getController().getValue();
		myController.setPanelXcf(1,v);
		break;
		case(7):
			v = theEvent.getController().getValue();
		myController.setPanelXcf(2,v);
		break;
		case(8):
			v = theEvent.getController().getValue();
		myController.setPanelXcf(3,v);
		break;
		case(9):
			v = theEvent.getController().getValue();
		myController.setPanelXcf(4,v);
		break;
		case(10):
			v = theEvent.getController().getValue();
		myController.setPanelXcf(5,v);
		break;
		case(11):
			v = theEvent.getController().getValue();
		myController.setPanelXcf(6,v);
		break;
		case(12):
			v = theEvent.getController().getValue();
		myController.setPanelXcf(7,v);
		break;
		case(13):
			v = theEvent.getController().getValue();
		myController.setPanelXcf(8,v);
		break;
		case(14):
			v = theEvent.getController().getValue();
		myController.setPanelXcf(9,v);
		break;
		case(15):
			v = theEvent.getController().getValue();
		myController.setPanelXcf(10,v);
		break;
		case(16):
			v = theEvent.getController().getValue();
		myController.setPanelXcf(11,v);
		break;
		case(17):
			v = theEvent.getController().getValue();
		myController.setPanelXcf(12,v);
		break;
		case(18):
			v = theEvent.getController().getValue();
		myController.setPanelXcf(13,v);
		break;
		case(19):
			v = theEvent.getController().getValue();
		myController.setPanelXcf(14,v);
		break;
		case(20):
			v = theEvent.getController().getValue();
		myController.setPanelXcf(15,v);
		break;
		case(21):
			v = theEvent.getController().getValue();
		myController.setPanelXcf(16,v);
		break;
		case(22):
			v = theEvent.getController().getValue();
		myController.setPanelXcf(17,v);
		break;

		}
	}

	//parse group 4 (camera dials) event! *********************************
	private void g4ControlEvent(ControlEvent theEvent){  

		float v;
		switch(theEvent.getController().getId()){
		case(13):
			v = theEvent.getController().getValue();
		myController.moveCameraByPhi(v);
		break;
		case(14):
			v = theEvent.getController().getValue();
		myController.moveCameraByTheta(v);
		break;
		}
	} 

}

