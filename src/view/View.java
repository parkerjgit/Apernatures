/****************************************************

View Class

View - contains the scene(empty space and camera), 
  imported 3d base model, and data model defining 
  panel system. View calls draws to screen via 
  viewState interface, though only one viewState 
  is implemented: View3d, a basic 3d view.

Part of:

Aper[na]tures
Author: Joshua Parker
Date: 2011

Inheritted License: GPL, V3 

****************************************************/
package view;

import processing.core.PApplet;
import remixlab.proscene.*;
import saito.objloader.*;
import model.Hanger;
import view.View3d;
import view.ViewState;

public class View{

	//parent PApplet
	PApplet p;

	//composit objs
	public Scene scene;
	public OBJModel model;
	public Hanger myhanger;

	//OBJModel flags - not used
	public boolean bTexture = true;
	public boolean bStroke = false;
	public boolean bMaterial = true;
	
	//states
	//private ViewState myView3d;
	//private ViewState myViewUnroll;

	public ViewState viewState;

	public View(PApplet parent, Hanger Myhanger){

		p = parent;

		//3d scene
		this.scene = new Scene(parent);
		this.scene.setAxisIsDrawn(false);
		this.scene.setGridIsDrawn(false);
		this.scene.enableFrustumEquationsUpdate();

		//imported 3d model; no need to use mtls or specify shapemode b/c
		//not drawing model, but just using the face vertices and normals
		this.model = new OBJModel(p, "bs10.obj");
		//this.model = new OBJModel(p, "bs10.obj", "relative", LINES);
		//this.model.shapeMode(LINES);
		//this.model.disableMaterial();
		//this.model.enableDebug();  
		this.model.scale(1); 
		this.myhanger = Myhanger;

		//view state
		setViewState(new View3d(p, this));

	} 

	// PUBLIC METHODS ////////////////////////////////////////////////////
	
	//set view state
	public void setViewState(ViewState newState){ 
		this.viewState = newState;
	}

	//get current fill
	public boolean getFill(){
		return this.viewState.getFill();
	}

	//turn off strokes, turn on surface fill
	public void enableFill(){
		this.viewState.enableFill();
	}

	//turn off surface fill, turn on strokes
	public void disableFill(){
		this.viewState.disableFill();
	}

	//draw the view
	public void draw() {
		this.viewState.draw();
	}
}
