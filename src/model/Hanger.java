/****************************************************

Hanger Class

Hanger is data model defining panel system, composed 
of subclass skin, which is composed of panels. 
Panels are basically two sets of parameters: one 
which defines the panel's size and position in 
space, and another that defines the size and 
relative position of a single rectangular void 
in the panel. Panel also contains data from ecotect 
about the amount of direct and diffuse radiation
each panel recieves. 

Part of:

Aper[na]tures
Author: Joshua Parker
Date: 2011

Inheritted License: GPL, V3 

 ****************************************************/
package model;

import processing.core.PApplet;
import model.Skin;

public class Hanger{

	//The parent PApplet
	PApplet p;

	//composit skin
	public Skin myskin;

	//CONSTRUCTOR
	public Hanger(PApplet parent, 
			int U,
			int V, 
			float D,
			float T,
			float snap,
			String PATH){

		p = parent;

		//create skin
		myskin = new Skin(p, U, V, D, T, snap);

		//pass data path to skin so it can load data into panels
		myskin.loadData(PATH);

		//update skin pattern
		update();

	}//end constructor

	// PUBLIC METHODS ////////////////////////////////////////////////////

	//set noise increment and update model
	public void setInc(float Inc){
		myskin.setInc(Inc);
	}

	//set noise seed and update model
	public void setSeed(int Seed){
		myskin.setSeed(Seed);
	}

	//set pattern coeffecients and update model
	public void setXcf(int i, float C){
		myskin.setXcf(i, C);
	}

	//update Hanger Model
	public void update(){    
		//update skin pattern
		myskin.update();
	}

}
