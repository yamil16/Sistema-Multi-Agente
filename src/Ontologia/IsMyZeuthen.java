package FinalUltimo2.Ontologia;

import jade.content.Predicate;

public class IsMyZeuthen implements Predicate {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private float value;
	
	public IsMyZeuthen(){}

	public IsMyZeuthen(float value) {
		super();
		this.value=value;
	}


	
	public void CalcularNuevoZeuthen(IsMyZeuthen zeuthenOtraPelicula){
		setValue((value-zeuthenOtraPelicula.getValue())/value);
	}



	public float getValue() {
		return value;
	}



	public void setValue(float value) {
		this.value = value;
	}


	
}
