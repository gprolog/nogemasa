package com.nogemasa.management.pojo;

public class Student {
	private Long id;
	private String name;
	private String subject;
	private Long score;
	private String idname;
	private String scorename;
	public String getIdname() {
		return idname;
	}
	public void setIdname(String idname) {
		this.idname = idname;
	}
	public String getScorename() {
		return scorename;
	}
	public void setScorename(String scorename) {
		this.scorename = scorename;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getScore() {
		return score;
	}
	public void setScore(Long score) {
		this.score = score;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
}
