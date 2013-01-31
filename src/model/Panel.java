/****************************************************

Panel Class

Panel is subclass of Hanger data model defining panel 
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

import processing.core.*;

public class Panel 
{
	//The parent PApplet
	PApplet p;

	//panel properties
	public PVector[] coords;
	public float x,y,w,h; 			//normalized
	public float px, py, pd; 		//centerline of frame
	public float t; 				//thickness of frame
	public float ix, iy, id; 		//inside line, ie. panel
	public float hx, hy, hw, hh; 	//perforations, ie. holes 
	public float snap; 				//panel subdivisions
	public int pClr; 				//panel color

	//imported data
/*	private float avgDailyTotal; 
	private float avgDailyDirect;
	private float avgDailyDiffuse;
	private float grade;
	private float elevation;*/

	//normalized parameters
	private float totalNorm;
	private float directNorm;
	private float diffuseNorm;
	private float gradeNorm;
	private float elevationNorm;
	public float noiseVal;
	
	//parameter weights
	private float[] xcf;

	//constructor
	Panel(PApplet parent, 
			float px, 
			float py,
			float pd,
			float ix,
			float iy,
			float id,
			float t,
			float snap){

		p = parent;

		//println("new panel");

		this.px = px; 
		this.py = py;
		this.pd = pd;
		this.ix = ix;
		this.iy = iy;
		this.id = id;
		this.t = t;
		//hx = this.hx;
		//hy = this.hy;
		//hw = this.hw;
		//hh = this.hh;
		this.snap = snap;

		coords = new PVector[4];
		xcf = new float[18];

	}

	// PUBLIC METHODS ////////////////////////////////////////////////////

	//load data into panel
	public void loadData(String avgDailyTotal,
			String avgDailyDirect,
			String avgDailyDiffuse,
			String grade,
			String elevation,
			String totalNorm,
			String directNorm,
			String diffuseNorm,
			String gradeNorm,
			String elevationNorm){

/*		this.avgDailyTotal = Float.parseFloat(avgDailyTotal);
		this.avgDailyDirect = Float.parseFloat(avgDailyDirect);
		this.avgDailyDiffuse = Float.parseFloat(avgDailyDiffuse);
		this.grade = Float.parseFloat(grade);
		this.elevation = Float.parseFloat(elevation);*/

		this.totalNorm = Float.parseFloat(totalNorm);
		this.directNorm = Float.parseFloat(directNorm);
		this.diffuseNorm = Float.parseFloat(diffuseNorm);
		this.gradeNorm = Float.parseFloat(gradeNorm);
		this.elevationNorm = Float.parseFloat(elevationNorm);

	}//end loadData()

	//
	public void setXcf(int i, float C){
		xcf[i]=C;
	}

	//find hole
	public void perforate(){

		//name coefficients
		float totalWeight = xcf[0];
		float directWeight = xcf[1];
		float diffuseWeight = xcf[2];
		float gradeWeight = xcf[3];
		float elevationWeight = xcf[4];
		float noiseWeight = xcf[5];
		float xScale = xcf[6];
		float xOff = xcf[7];
		//float xTrim = xcf[8];
		float yScale = xcf[9];
		float yOff = xcf[10];
		//float yTrim = xcf[11];
		float wScale = xcf[12];
		float wOff = xcf[13];
		//float wTrim = xcf[14];
		float hScale = xcf[15];
		float hOff = xcf[16];
		//float hTrim = xcf[17];

		//calc smth like a weighted avg of pattern components
		float pattern =  (totalNorm * totalWeight +
				directNorm * directWeight +
				diffuseNorm * diffuseWeight +
				gradeNorm  * gradeWeight +
				elevationNorm * elevationWeight +
				noiseVal * noiseWeight +
				1)
				/(xcf[0] +xcf[1] +xcf[2] +xcf[3] +xcf[4] +xcf[5] +1);

		//calc normalized scale effects and offset x, y, w, z 
		//VALUE RANGE: 0.0 - 1.0
		float wv = (float)((((pattern-.5) * wScale) + .5) + wOff);      
		float hv = (float)((((pattern-.5) * hScale) + .5) + hOff);               
		float xv = (float)((((pattern-1) * xScale) + 0) + xOff);                
		float yv = (float)((((pattern-1) * yScale) + 0) + yOff);

		//calculate real hole dimensions and displacement in meters
		hw = wv * id;
		hh = hv * id;
		hx = xv * id + t;
		hy = yv * id + t;

		//snap and normalize coords to subgrid
		w = (int)(PApplet.round(hw/snap));
		h = (int)(PApplet.round(hh/snap));
		x = (int)(PApplet.round(hx/snap));
		y = (int)(PApplet.round(hy/snap));

		//convert back to absolute coords
		hw = w*snap;
		hh = h*snap;
		hx = px + x*snap;
		hy = py + y*snap;

		//load coordinate array (relative to basegrid)
		//adjust order for vertice mapping
		coords[3] = new PVector(x,y);
		coords[2] = new PVector(x+hw, y);
		coords[1] = new PVector(x+hw, y+hh);
		coords[0] = new PVector(x, y+hh);


	}//end perforate

	//
	public void colorize(){

		//calc smth like a weighted avg of pattern components
		float pattern =  (totalNorm * xcf[0] +
				directNorm * xcf[1] +
				diffuseNorm * xcf[2] +
				gradeNorm  * xcf[3] +
				elevationNorm * xcf[4] +
				noiseVal * xcf[5] +
				1)/
				(xcf[0] +xcf[1] +xcf[2] +xcf[3] +xcf[4] +xcf[5] +1);

		int c1 = p.color(50, 50, 50);
		int c2 = p.color(200, 255, 200);
		float v = pattern * 255;

		float deltaR = p.red(c2)-p.red(c1);
		float deltaG = p.green(c2)-p.green(c1);
		float deltaB = p.blue(c2)-p.blue(c1);

		int c = p.color(
				(p.red(c1)+(v)*(deltaR/255)),
				(p.green(c1)+(v)*(deltaG/255)),
				(p.blue(c1)+(v)*(deltaB/255)) 
				);

		pClr = c;
	}

}//end class
