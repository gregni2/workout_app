package com.s4.wyaw.list.item;

import com.s4.wyaw.exercise.Routine;


public class EntryItem implements Item{

	public final String title;
	public final String subtitle;
	public final String requirements;
	public final int image;
	public final int routineNum;
	public final Routine routine;

	public EntryItem(String title, String subtitle, String requirements, int image, int routineNum, Routine routine) {
		this.title = title;
		this.subtitle = subtitle;
		this.requirements = requirements;
		this.image = image;
		this.routineNum = routineNum;
		this.routine = routine;
		
	}
	
	@Override
	public boolean isSection() {
		return false;
	}

}
