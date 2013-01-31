/****************************************************

View3d Class

View3d implements a basic 3d view

Part of:

Aper[na]tures
Author: Joshua Parker
Date: 2011

Inheritted License: GPL, V3 

****************************************************/
package view;

import processing.core.*;
import saito.objloader.*;
import model.*;

public class View3d implements ViewState{

	//The parent PApplet
	PApplet p;

	//composit view
	public View myview;

	//flags
	boolean bFill = false;

	//CONSTRUCTOR
	View3d(PApplet parent, View Myview){
		p = parent;
		this.myview = Myview;
	}

	// PUBLIC METHODS ////////////////////////////////////////////////////
	
	//turn off strokes, turn on surface fill
	public void enableFill(){
		bFill = true;
	}

	//turn off surface fill, turn on strokes
	public void disableFill(){
		bFill = false;
	}

	//get current fill
	public boolean getFill(){
		return bFill;
	}

	//draw the 3d view
	public void draw() {

		//setup lighting
		p.directionalLight(126, 126, 126, 0, 0, -1);
		//p.ambientLight(102, 102, 102);
		p.lights();
		p.stroke(10,10,10);

		//set camera position and target
/*		PVector v = new PVector(0,0,-1);
		PVector v2 = new PVector(0,0,0);
		myview.scene.camera().setUpVector(v,true); 
		myview.scene.camera().lookAt(v2);*/

		//just want vertices so do not draw the 3d model!!! this will slow sketch way down
		//myview.model.draw();

		//load faces in array
		Segment segment = myview.model.getSegment(0);
		Face[] faces = segment.getFaces();

		//for each face...
		for (int i = faces.length-1; i >= 0; i--) {

			//get face and corresponding panel
			Face nextFace = faces[i];
			Panel nextPanel = myview.myhanger.myskin.panels[i];

			//get vertices and normals of face and relative coordinates
			//of panel opening
			PVector[] vs = nextFace.getVertices();
			PVector[] ns = nextFace.getNormals();
			PVector[] cs = nextPanel.coords;

			//get panel color            
			int clr = nextPanel.pClr;

			//map panel space coordinates to world space coordinates.
			PVector[] pts = mapFace(vs, ns, cs);      

			//stroke normal
			//line(vs[0].x, vs[0].y, vs[0].z, vs[0].x+ns[0].x, vs[0].y+ns[0].y, vs[0].z+ns[0].z);

			//set fill and stroke
			if(bFill){
				p.fill(clr);
				p.noStroke();
			}else{
				p.noFill();
				p.stroke(10, 10, 10);
			}

			//draw four quad polygons 
			p.beginShape();
			p.vertex(vs[0].x, vs[0].y, vs[0].z);
			p.vertex(vs[1].x, vs[1].y, vs[1].z);
			p.vertex(pts[1].x, pts[1].y, pts[1].z);
			p.vertex(pts[0].x, pts[0].y, pts[0].z);
			p.endShape();

			p.beginShape();      
			p.vertex(vs[1].x, vs[1].y, vs[1].z);
			p.vertex(vs[2].x, vs[2].y, vs[2].z);
			p.vertex(pts[2].x, pts[2].y, pts[2].z);
			p.vertex(pts[1].x, pts[1].y, pts[1].z);
			p.endShape();

			p.beginShape();      
			p.vertex(vs[2].x, vs[2].y, vs[2].z);
			p.vertex(vs[3].x, vs[3].y, vs[3].z);
			p.vertex(pts[3].x, pts[3].y, pts[3].z);
			p.vertex(pts[2].x, pts[2].y, pts[2].z);
			p.endShape();

			p.beginShape();      
			p.vertex(vs[3].x, vs[3].y, vs[3].z);
			p.vertex(vs[0].x, vs[0].y, vs[0].z);
			p.vertex(pts[0].x, pts[0].y, pts[0].z);
			p.vertex(pts[3].x, pts[3].y, pts[3].z);
			p.endShape();

		}//end for loop

	}//end draw

	// PRIVATE METHODS ////////////////////////////////////////////////////
	
	//find hole vertices on face from a face vertices(vs), 
	//face normals(ns), and face-space coordinates(cs)
	private PVector[] mapFace(PVector[] vs, PVector[] n, PVector[] cs){

		PVector[] pts = new PVector[0];

		//for each corner, map 2d coord to 3d pt
		for (int i = vs.length-1; i >= 0; i--) {
			PVector pt = mapPt(vs[0], n[i], cs[i]);
			pts = (PVector[])PApplet.append(pts, pt);
		}
		return pts;

	}

	//find singe 3d pt from a vertice(v), normal(n), 
	//and 2d relative coordinate(c)
	private PVector mapPt(PVector v, PVector n, PVector c){

		//get angle of normal
		float a = (float)Math.atan(n.z/n.y);
		a = PApplet.radians(90-PApplet.abs(PApplet.degrees(a)));

		float x,y,z;

		if(n.y < 0){
			x = v.x + c.x;
			y = v.y - c.y*PApplet.cos(a);
			z = v.z - c.y*PApplet.sin(a);
		}else{
			x = v.x + c.x;
			y = v.y - c.y*PApplet.cos(a);
			z = v.z + c.y*PApplet.sin(a);
		}

		PVector pt3d = new PVector(x, y, z);

		return pt3d;    
	}

}