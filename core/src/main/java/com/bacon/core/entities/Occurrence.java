package com.bacon.core.entities;

public class Occurrence {

	private String location;
	private int beginLine;
	private int beginColumn;
	private int endLine;
	private int endColumn;
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public int getBeginLine() {
		return beginLine;
	}
	public void setBeginLine(int beginLine) {
		this.beginLine = beginLine;
	}
	public int getBeginColumn() {
		return beginColumn;
	}
	public void setBeginColumn(int beginColumn) {
		this.beginColumn = beginColumn;
	}
	public int getEndLine() {
		return endLine;
	}
	public void setEndLine(int endLine) {
		this.endLine = endLine;
	}
	public int getEndColumn() {
		return endColumn;
	}
	public void setEndColumn(int endColumn) {
		this.endColumn = endColumn;
	}
	
}
