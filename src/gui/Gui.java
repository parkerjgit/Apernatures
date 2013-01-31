/****************************************************

Gui Class

Gui is a wrapper for controlP5 library, which 
provides user interface toolkit. more at:
http://www.sojamo.de/libraries/controlP5/

Part of:

Aper[na]tures
Author: Joshua Parker
Date: 2011

Inheritted License: GPL, V3 

****************************************************/
package gui;

import processing.core.PApplet;
import processing.core.PFont;
import controlP5.*;
import remixlab.proscene.*;
import view.*;

public class Gui
{
	//CONSTANTS
	private static final int DISABLE_DEPTH_TEST = 4;
	private static final int ENABLE_DEPTH_TEST = -4;

	//parent PApplet
	PApplet p;

	//cp5 objs
	private ControlP5 cp5;
	private Accordion accordion;
	
	//composit view
	private View myview;

	//CONSTRUCTOR
	public Gui(PApplet parent, View Myview){ 

		//initialize
		p = parent;
		cp5 = new ControlP5(p); 
		cp5.setAutoDraw(false);
		myview = Myview;

		//setup gui elements
		setupP5Gui();
	}

	// PUBLIC METHODS ////////////////////////////////////////////////////

	public void draw() {

		//save scene (so you can draw 2d gui on top)
		saveState(myview.scene); 

		//draw gui
		cp5.draw();

		//restore scene
		restoreState(myview.scene);
	}

	// PRIVATE METHODS ////////////////////////////////////////////////////

	//setup p5 gui elements
	private void setupP5Gui(){

		int BarH = 20;
		int BgClr = p.color(50); //bg of block
		int BarClr = p.color(50); //bar color 
		int HoverClr = 30; //bar color on hover 
		int LabelClr = p.color(255);

		int slideH = 10;
		int slideW = 100;
		int slideX = 10;
		int slideSpc = 5;
		int slideDY = slideH+slideSpc;

		PFont arial = p.createFont("ArialMT-30", 11);
		PFont arial10 = p.createFont("ArialMT-30", 10);

		//add footer
		cp5.addTextlabel("label")
				.setText("by Fourth Natures Lab | www.fnl.com")
				.setPosition(2,p.height - 20)
				.setFont(arial10)
				.setHeight(20) 
				.setWidth(200)
				;

		// ADD GROUPS *****************************************************************************

		// group 1 (shortcut info)
		controlP5.Group g1 = cp5.addGroup("myGroup1")
				.setId(1).setLabel("Aper[na]ture")
				.setBackgroundHeight(80)
				.setBarHeight(BarH)
				.setBackgroundColor(BgClr)
				.setColorBackground(BarClr)
				.setColorForeground(HoverClr)
				.setColorLabel(LabelClr)
				;

		// group 2 (noise sliders)
		controlP5.Group g2 = cp5.addGroup("myGroup2")
				.setId(2)
				.setLabel("Noise")
				.setBackgroundHeight(40)
				.setBarHeight(BarH)
				.setBackgroundColor(BgClr) //bg of block
				.setColorBackground(BarClr) //bar color 
				.setColorForeground(HoverClr) //bar color on hover 
				.setColorLabel(LabelClr) //label text color 
				;

		// group 3 (effect sliders)
		controlP5.Group g3 = cp5.addGroup("myGroup3")
				.setId(3)
				.setLabel("x = f()")
				.setBackgroundHeight(300)
				.setBarHeight(BarH)
				.setBackgroundColor(BgClr) //bg of block
				.setColorBackground(BarClr) //bar color 
				.setColorForeground(HoverClr) //bar color on hover 
				.setColorLabel(LabelClr) //label text color 
				;

		// group 4 (camera dials)
		controlP5.Group g4 = cp5.addGroup("myGroup4")
				.setId(4)
				.setLabel("view")
				.setBackgroundHeight(200)
				.setBarHeight(BarH)
				.setBackgroundColor(BgClr) //bg of block
				.setColorBackground(BarClr) //bar color 
				.setColorForeground(HoverClr) //bar color on hover 
				.setColorLabel(LabelClr) //label text color 
				;


		// ADD CONTROLLERS: sliders and dials ******************************************************

		//add group 1 controllers

		cp5.addTextarea("txt")
		.moveTo(g1)
		.setPosition(5,5)
		.setSize(195,90)
		.setFont(arial)
		.setLineHeight(14)
		.setColor(p.color(128))
		.setColorBackground(p.color(50))
		.setColorForeground(p.color(255,100))
		.setText("shortcuts: \n"
				+"f - toggle fills \n"
				+"e - toggle perspective \n"
				+"c - close controls \n"
				+"o - open controls \n");

		//add group 2 controllers

		cp5.addSlider("noise inc")
		.setId(1)
		.moveTo(g2)
		.setPosition(slideX,slideDY*1)
		.setSize(slideW,slideH)
		.setRange(0.0f,1.0f)
		.setValue(.4f);     

		cp5.addSlider("noise seed")
		.setId(2)
		.moveTo(g2)
		.setPosition(slideX,slideDY*2)
		.setSize(slideW,slideH)
		.setRange(0,50)
		.setValue(8);

		//add group 3 controllers

		cp5.addSlider("total radiation")
		.setId(5)
		.moveTo(g3)
		.setPosition(slideX,slideDY*1)
		.setSize(slideW,slideH)
		.setRange(0,10)
		.setValue(0); 

		cp5.addSlider("direct radiation")
		.setId(6)
		.moveTo(g3)
		.setPosition(slideX,slideDY*2)
		.setSize(slideW,slideH)
		.setRange(0,10)
		.setValue(0);  

		cp5.addSlider("diffuse radiation")
		.setId(7)
		.moveTo(g3)
		.setPosition(slideX,slideDY*3)
		.setSize(slideW,slideH)
		.setRange(0,10)
		.setValue(0); 

		cp5.addSlider("by grade")
		.setId(8)
		.moveTo(g3)
		.setPosition(slideX,slideDY*4)
		.setSize(slideW,slideH)
		.setRange(0,10)
		.setValue(0);

		cp5.addSlider("by elevation")
		.setId(9)
		.moveTo(g3)
		.setPosition(slideX,slideDY*5)
		.setSize(slideW,slideH)
		.setRange(0,10)
		.setValue(0);

		cp5.addSlider("noise")
		.setId(10)
		.moveTo(g3)
		.setPosition(slideX,slideDY*6)
		.setSize(slideW,slideH)
		.setRange(0,20)
		.setValue(0);

		cp5.addSlider("x scale")
		.setId(11)
		.moveTo(g3)
		.setPosition(slideX,slideDY*7)
		.setSize(slideW,slideH)
		.setRange(-1.0f,1.0f)
		.setValue(1.0f)
		.setNumberOfTickMarks(21)
		.snapToTickMarks(true)
		.showTickMarks(false);

		cp5.addSlider("x offset")
		.setId(12)
		.moveTo(g3)
		.setPosition(slideX,slideDY*8)
		.setSize(slideW,slideH)
		.setRange(-1,1)
		.setValue(.1f)
		.setNumberOfTickMarks(100)
		.snapToTickMarks(true)
		.showTickMarks(false);

		cp5.addSlider("x trim")
		.setId(13)
		.moveTo(g3)
		.setPosition(slideX,slideDY*9)
		.setSize(slideW,slideH)
		.setRange(0,3)
		.setValue(0)
		.setNumberOfTickMarks(11)
		.snapToTickMarks(true)
		.showTickMarks(false);

		cp5.addSlider("y scale")
		.setId(14)
		.moveTo(g3)
		.setPosition(slideX,slideDY*10)
		.setSize(slideW,slideH)
		.setRange(-3,3)
		.setValue(1)
		.setNumberOfTickMarks(100)
		.snapToTickMarks(false)
		.showTickMarks(false);

		cp5.addSlider("y offset")
		.setId(15)
		.moveTo(g3)
		.setPosition(slideX,slideDY*11)
		.setSize(slideW,slideH)
		.setRange(-1,1)
		.setValue(.1f)
		.setNumberOfTickMarks(100)
		.snapToTickMarks(true)
		.showTickMarks(false);

		cp5.addSlider("y trim")
		.setId(16)
		.moveTo(g3)
		.setPosition(slideX,slideDY*12)
		.setSize(slideW,slideH)
		.setRange(0,3)
		.setValue(0)
		.setNumberOfTickMarks(11)
		.snapToTickMarks(true)
		.showTickMarks(false);

		cp5.addSlider("w scale")
		.setId(17)
		.moveTo(g3)
		.setPosition(slideX,slideDY*13)
		.setSize(slideW,slideH)
		.setRange(-3,3)
		.setValue(1)
		.setNumberOfTickMarks(100)
		.snapToTickMarks(true)
		.showTickMarks(false);

		cp5.addSlider("w offset")
		.setId(18)
		.moveTo(g3)
		.setPosition(slideX,slideDY*14)
		.setSize(slideW,slideH)
		.setRange(-1,1)
		.setValue(0)
		.setNumberOfTickMarks(100)
		.snapToTickMarks(false)
		.showTickMarks(false);

		cp5.addSlider("w trim")
		.setId(19)
		.moveTo(g3)
		.setPosition(slideX,slideDY*15)
		.setSize(slideW,slideH)
		.setRange(0,3)
		.setValue(0)
		.setNumberOfTickMarks(11)
		.snapToTickMarks(true)
		.showTickMarks(false);

		cp5.addSlider("h scale")
		.setId(20)
		.moveTo(g3)
		.setPosition(slideX,slideDY*16)
		.setSize(slideW,slideH)
		.setRange(-3,3)
		.setValue(1)
		.setNumberOfTickMarks(100)
		.snapToTickMarks(true)
		.showTickMarks(false);

		cp5.addSlider("h offset")
		.setId(21)
		.moveTo(g3)
		.setPosition(slideX,slideDY*17)
		.setSize(slideW,slideH)
		.setRange(-1,1)
		.setValue(0)
		.setNumberOfTickMarks(100)
		.snapToTickMarks(true)
		.showTickMarks(false);

		cp5.addSlider("h trim")
		.setId(22)
		.moveTo(g3)
		.setPosition(slideX,slideDY*18)
		.setSize(slideW,slideH)
		.setRange(0,3)
		.setValue(0)
		.setNumberOfTickMarks(11)
		.snapToTickMarks(true)
		.showTickMarks(false);

		//add group 4 controllers

		cp5.addKnob("azimuth")
		.setId(13)
		.moveTo(g4)
		.setPosition(20,20)
		.setRadius(35)
		.setAngleRange(2*PApplet.PI)
		.setStartAngle((float)(.5*PApplet.PI))
		.setRange(0,360)
		.setValue(0)
		.setNumberOfTickMarks(16)
		.snapToTickMarks(true)
		.setColorForeground(p.color(255))
		.setColorBackground(p.color(40))
		.setColorActive(p.color(255,255,0))
		.setDragDirection(Knob.HORIZONTAL);           

		cp5.addKnob("elevation")
		.setId(14)
		.moveTo(g4)
		.setPosition(110,20)
		.setRadius(35)
		.setAngleRange(PApplet.PI)
		.setStartAngle((float)(1.5*PApplet.PI))
		.setRange(1,179)
		.setValue(0)
		.setNumberOfTickMarks(8)
		.snapToTickMarks(true)
		.setColorForeground(p.color(255))
		.setColorBackground(p.color(40))
		.setColorActive(p.color(255,255,0))
		.setDragDirection(Knob.HORIZONTAL);


		//Add Sidebar Accordion:  ******************************************************
		accordion = cp5.addAccordion("acc")
				.setPosition(0,0)
				.setWidth(200)
				.addItem(g1)
				.addItem(g2)
				.addItem(g3)
				.addItem(g4)
				;

		//initially open all groups
		accordion.open(0,1,2,3);

		//allow multiple groups open at a time.
		accordion.setCollapseMode(Accordion.MULTI);

		//Add keyboard shortcuts ******************************************************	
		cp5.mapKeyFor(new ControlKey() {public void keyEvent() {accordion.open(0,1,2,3);}}, 'o');
		cp5.mapKeyFor(new ControlKey() {public void keyEvent() {accordion.close(0,1,2,3);}}, 'c');

	}//end init()

	//function from https://forum.processing.org/topic/proscene-and-2d-drawing
	private void saveState(Scene scene) {

		// Disable depth test to draw 2d on top
		p.hint(DISABLE_DEPTH_TEST);

		//  Set processing projection and modelview matrices to draw in 2D:
		// 1. projection matrix:
		float cameraZ = ((p.height/2.0f) / PApplet.tan(PApplet.PI*60.0f/360.0f));
		scene.pg3d.perspective(
				PApplet.PI/3.0f, 
				scene.camera().aspectRatio(), 
				cameraZ/10.0f, 
				cameraZ*10.0f);

		// 2 model view matrix
		scene.pg3d.camera();
	}

	//function from https://forum.processing.org/topic/proscene-and-2d-drawing
	private void restoreState(Scene scene) {

		// 1. Restore processing projection matrix
		switch (scene.camera().type()) {
		case PERSPECTIVE:
			scene.pg3d.perspective(
					scene.camera().fieldOfView(), 
					scene.camera().aspectRatio(),
					scene.camera().zNear(), 
					scene.camera().zFar());
			break;
		case ORTHOGRAPHIC:
			float[] wh = scene.camera().getOrthoWidthHeight();
			scene.pg3d.ortho(
					-wh[0], wh[0], -wh[1], wh[1], 
					scene.camera().zNear(), 
					scene.camera().zFar());
			break;
		}

		// 2. Restore processing modelview matrix
		scene.pg3d.camera(
				scene.camera().position().x, 
				scene.camera().position().y, 
				scene.camera().position().z, 
				scene.camera().at().x, 
				scene.camera().at().y, 
				scene.camera().at().z, 
				scene.camera().upVector().x, 
				scene.camera().upVector().y, 
				scene.camera().upVector().z);

		//Re-enble depth test
		p.hint(ENABLE_DEPTH_TEST);
	}

}
