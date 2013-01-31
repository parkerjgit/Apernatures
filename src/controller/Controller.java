/****************************************************

Controller Class

MVC Controller contains methods for updating data 
model & controlling view(s)* 

Part of:

Aper[na]tures
Author: Joshua Parker
Date: 2011

Inheritted License: GPL, V3 

****************************************************/
package controller;

import processing.core.PApplet;
import processing.core.PVector;
import model.Hanger;
import view.View;
import gui.Gui;

public class Controller{
	
	//The parent PApplet
	PApplet p; 
  
	//PROPS
	private Hanger myhanger;
	private View myview;
	private Gui mygui;
  
	//CONSTRUCTOR
	public Controller(PApplet parent, Hanger Myhanger, Gui Mygui, View Myview){
		p = parent;
	    myhanger = Myhanger;
	    mygui = Mygui;
	    myview = Myview;
	}
  
	// PUBLIC METHODS ////////////////////////////////////////////////////
	
	//gui noise increment
	public void setNoiseInc(float v){
		myhanger.setInc(v); 
	}
  
	//set noise seed
	public void setNoiseSeed(int v){
		myhanger.setSeed(v); 
    }
  
	//set coefficient with index i
	public void setPanelXcf(int i, float C){
		myhanger.setXcf(i, C); 
    }
  
	//
	public void moveCameraByTheta(float v){
        PVector v0; 
        float r = 500;
        float phi = 0;
        float theta = 0;
        //r = myview.scene.camera().sceneRadius();
        //r = 500;
        v0 = myview.scene.camera().position(); 
        phi = (float)Math.atan(v0.y/v0.x);
        theta = PApplet.radians(v); //azimuth 0-2PI
        setCameraPosition(phi, theta, r);
	}
  
	//
	public void moveCameraByPhi(float v){
        PVector v0; 
        float r = 500;
        float phi = 0;
        float theta = 0;
        //r = myview.scene.camera().sceneRadius();
        //r = 500;
        v0 = myview.scene.camera().position();
        theta = (float)Math.acos(v0.z/r);
        phi = PApplet.radians(v); //azimuth 0-2PI
        setCameraPosition(phi, theta, r);
	}
  
	//
	public void setCameraPosition(float phi, float theta, float r){
	    float x = r*(PApplet.sin(theta)*PApplet.cos(phi));
	    float y = r*(PApplet.sin(theta)*PApplet.sin(phi));
	    float z = r*PApplet.cos(theta);
	      
	    PVector v = new PVector(x,y,z);
	    PVector v2 = new PVector(0,0,0);
	    PVector v3 = new PVector(0,0,-1);
	      
	    myview.scene.camera().setPosition(v);
	    myview.scene.camera().setOrientation(theta, phi);
	    myview.scene.camera().setUpVector(v3,true);
	    myview.scene.camera().lookAt(v2);
	}
  
	//
	public void focusGui(){
		myview.scene.disableMouseHandling(); 
		myview.scene.disableKeyboardHandling(); 
	}
  
	//  
	public void focusScene(){
		myview.scene.enableMouseHandling(); 
		myview.scene.enableKeyboardHandling();
	}
  
	//
	public void toggleTexture(){
        if(!myview.bTexture){
        	showTexture();
        } else {
        	hideTexture();
        }
	}
  
	//
	public void toggleMaterial(){
		if(!myview.bMaterial) {
			showMaterial();
		} else {
			hideMaterial();
		}
	}
  
	//
	public void toggleFill(){
		if(!myview.getFill()) {
			showFill();
		} else {
			hideFill();
		}
	}

	//           
	public void showTexture(){
		myview.model.enableTexture();
		myview.bTexture = true;
	}

	//          
	public void hideTexture(){
		myview.model.disableTexture();
		myview.bTexture = false;
	}

	//          
	public void showMaterial(){
		myview.model.enableMaterial();
		myview.bMaterial = true;
	}

	//         
	public void hideMaterial(){
		myview.model.disableMaterial();
		myview.bMaterial = false;
	}

	//           
	public void showFill(){
		myview.enableFill();
	}

	//          
	public void hideFill(){
		myview.disableFill();
	}

	//
	public void update(){
		myhanger.update(); //update the model
		myview.draw(); // draw the 3d scene
		mygui.draw(); // then draw the gui on top
	}
}
