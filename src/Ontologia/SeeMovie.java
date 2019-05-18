package FinalUltimo2.Ontologia;

import java.util.Date;

import jade.content.AgentAction;

public class SeeMovie  implements AgentAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Date date;
	//private String date;
	private Movie movie;
	
	public SeeMovie() {}
	
	public SeeMovie(Movie mOVIE) {
		super();
		movie = mOVIE;
	}
	/*
	public SeeMovie(String mOVIE_DATE, Movie mOVIE) {
		super();
		date = mOVIE_DATE;
		movie = mOVIE;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	*/
	public Movie getMovie() {
		return movie;
	}
	public void setMovie(Movie movie) {
		this.movie = movie;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
		public Date getDate() {
		return date;
	}

	
	
	public SeeMovie(Date mOVIE_DATE, Movie mOVIE) {
		super();
		date = mOVIE_DATE;
		movie = mOVIE;
	}
	
	
	
}
