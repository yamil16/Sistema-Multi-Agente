package FinalUltimo2.Ontologia;

import jade.content.lang.sl.SLCodec;
import jade.content.onto.BasicOntology;
import jade.content.onto.Ontology;
import jade.content.schema.AgentActionSchema;
import jade.content.schema.ConceptSchema;
import jade.content.schema.PredicateSchema;
import jade.content.schema.PrimitiveSchema;

public class Ontologia extends Ontology {
	private static final long serialVersionUID = 314831391539979184L;

	// Nombre de la ontolog�a
	public static final String ONTOLOGY_NAME = "negotiation-movie-ontology";
	
	// Constantes, nombres de los esquemas y los slots (atributos) de la ontolog�a 
	public static final String MOVIE = "movie";
	public static final String SEE_MOVIE = "see-movie";
	private static final String MOVIE_NAME = "name";
	private static final String MOVIE_YEAR = "year";
	private static final String MOVIE_DIRECTOR = "director";
	private static final String MOVIE_ACTORS = "actors";
	private static final String MOVIE_DATE = "date";
	private static final String IS_MY_ZEUTHEN = "is-my-zeuthen";
	private static final String VALUE = "value";
	
	private static Ontology instance = new Ontologia();
	private static SLCodec codecInstance = new SLCodec();
	
	public static Ontology getInstance() { return instance; }
	
	public static SLCodec getCodecInstance() { return codecInstance; }
	
	public Ontologia() {
		super(ONTOLOGY_NAME, BasicOntology.getInstance());
		try {			
			
			ConceptSchema cs = new ConceptSchema(MOVIE);
			cs.add(MOVIE_NAME, (PrimitiveSchema)getSchema(BasicOntology.STRING), PrimitiveSchema.MANDATORY);
			cs.add(MOVIE_YEAR, (PrimitiveSchema)getSchema(BasicOntology.DATE), PrimitiveSchema.OPTIONAL);
			cs.add(MOVIE_DIRECTOR, (PrimitiveSchema)getSchema(BasicOntology.STRING), PrimitiveSchema.OPTIONAL);
			cs.add(MOVIE_ACTORS, (PrimitiveSchema)getSchema(BasicOntology.STRING), PrimitiveSchema.OPTIONAL); // Podr�a ser una lista
			add(cs, Movie.class);
			
			AgentActionSchema aas = new AgentActionSchema(SEE_MOVIE);
			aas.add(MOVIE, (ConceptSchema)getSchema(MOVIE), PrimitiveSchema.MANDATORY);
			aas.add(MOVIE_DATE, (PrimitiveSchema)getSchema(BasicOntology.DATE), PrimitiveSchema.OPTIONAL);
			add(aas, SeeMovie.class);
			
			PredicateSchema ps = new PredicateSchema(IS_MY_ZEUTHEN);
			ps.add(VALUE, (PrimitiveSchema)getSchema(BasicOntology.FLOAT), PrimitiveSchema.MANDATORY);
			add(ps, IsMyZeuthen.class);
			// Movie, SeeMovie, e IsMyZeuthen son las clases que implementan los esquemas de la ontolog�a
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
