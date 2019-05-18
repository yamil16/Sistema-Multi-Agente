package FinalUltimo2.Ontologia;

import java.util.Date;

import FIPA.DateTime;
import jade.content.Concept;
import jade.content.onto.BasicOntology;

public class Movie  implements Concept{
	
	private static final long serialVersionUID = 1L;
	private String name;
	private Date year;
	//private DateTime year;
	//private String year; 
	private String director;
	private String actors;
	
	public Movie() {}
	
	public Movie(String MOVIE_NAME) {
		super();
		this.name = MOVIE_NAME;
		director="";
		actors="";
	}	
	
	
	public String getName() {
		return name;
	}
	/*
	public Date getYear() {
		return year;
	}
	*/
	public String getDirector() {
		return director;
	}
	public String getActors() {
		return actors;
	}


	public void setName(String name) {
		this.name = name;
	}

/*
	public void setYear(Date year) {
		this.year = year;
	}
*/

	public void setDirector(String director) {
		this.director = director;
	}


	public void setActors(String actors) {
		this.actors = actors;
	}

	public Date getYear() {
		return year;
	}

	public void setYear(Date year) {
		this.year = year;
	}
	
	
	
	


}
