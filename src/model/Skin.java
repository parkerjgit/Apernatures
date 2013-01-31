/****************************************************

Skin Class

Skin is subclass of Hanger data model defining panel 
system, composed of subclass skin, which is composed 
of panels. 
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
import model.Panel;

public class Skin 
{
	//The parent PApplet
	PApplet p;

	//registration pt
	public float x, y;

	//composit panels
	public Panel[] panels;

	//skin properties
	int u; 			//number of modules in U direction
	int v; 			//number of modules in V direction
	float dim; 		//panel lengh/width dimension, D (meters)
	float thick; 	//thickness, T of frame (meters)

	//noise vars
	float inc;
	int seed;

	//CONSTRUCTOR
	Skin(PApplet parent, 
			int U,
			int V, 
			float D,
			float T,
			float snap){

		p = parent;

		//noise vars
		inc = 0.4f;
		seed = 8;

		this.u = U;
		this.v = V;
		this.dim = D;
		this.thick = T;

		//initialize 1d array for u x v panels  
		panels = new Panel[u*v];

		//create u x v panels in 1d array    
		int n = 0;   
		for(int i=0; i<v; i++) {
			for(int j=0; j<u; j++) {

				//calc panel dimension and reg pts
				float pd = D;
				float px = j*D;
				float py = i*D;

				//calc inner frame dimension and reg pts
				float id = pd-(T*2);
				float ix = px+T;
				float iy = py+T;

				//frame thickness
				float thick = T;

				//create panel
				panels[n] = new Panel(
						p,
						px, 
						py,
						pd,
						ix,
						iy,
						id,
						thick,
						snap);

				//incr counter                     
				n++;        
			}
		}

		//generate initial pattern
		update();

	}//end Skin()

	// PUBLIC METHODS ////////////////////////////////////////////////////

	//load data
	public void loadData(String PATH){

		//holders for rows and items in a row respectively
		String loadedStrings[];
		String splitString[];

		//load data into an array of rows
		loadedStrings = p.loadStrings(PATH);

		int n = 0;
		for(int i=0; i<v; i++) {

			//get data for first row, i+1 b/c first row of text file
			//is column headers
			splitString = PApplet.split(loadedStrings[i+1], '\t');

			for(int j=0; j<u; j++) {

				//load up panel
				panels[n].loadData(
						splitString[2],
						splitString[5],
						splitString[8],
						splitString[10],
						splitString[12],
						splitString[3],
						splitString[6],
						splitString[9],
						splitString[11],
						splitString[13]);

				n++;     
			}
		}

	}//end loadData()

	//set a coefficient
	public void setXcf(int id, float C){
		int n = 0;
		for(int i=0; i<v; i++) {    
			for(int j=0; j<u; j++) {
				panels[n].setXcf(id, C);
				n++;
			}
		}
	}

	//set noise increment
	public void setInc(float Inc){
		inc = Inc;
	}

	//set noise seed
	public void setSeed(int Seed){
		seed = Seed;   
	}

	//update pattern
	public void update(){    
		//generate noise pattern
		generateNoise();    
		//assign colors to panels
		colorize();    
		//generate perforation from ecotect data and noise
		perforate();     
	}

	//generate noise
	public void generateNoise(){

		//noise constants
		float vx = 0.0f;
		float vy = 0.0f;
		//float inc = 0.4;
		//float k = 0;
		p.noiseSeed(seed);

		int n = 0;
		for(int i=0; i<v; i++) {    
			for(int j=0; j<u; j++) {

				//calc noise
				panels[n].noiseVal = p.noise(vx, vy);

				//increment panel
				n++;

				//increment noise x param
				vx = vx + inc;
			}
			//increment noise y param
			vy = vy + inc;
		}
	}//end generateNoise()

	//find holes
	public void perforate(){ 
		int n = 0;
		for(int i=0; i<v; i++) {    
			for(int j=0; j<u; j++) {
				panels[n].perforate();
				n++;
			}
		}
	}//end perforate

	//find holes
	public void colorize(){ 
		int n = 0;
		for(int i=0; i<v; i++) {    
			for(int j=0; j<u; j++) {
				panels[n].colorize();
				n++;
			}
		}
	}//end perforate

}//end class
