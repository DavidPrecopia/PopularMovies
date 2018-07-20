package com.example.android.popularmovies.datamodel;

public class Review {
	private final String author;
	private final String content;
	
	private Review(String author, String content) {
		this.author = author;
		this.content = content;
	}
	
	public String getAuthor() {
		return author;
	}
	
	public String getContent() {
		return content;
	}
}
