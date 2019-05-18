package FinalUltimo2;




import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import FinalUltimo2.Ontologia.Ontologia;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

public class AgenteReceptor extends Agent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Vector<String> peliculas;
	Vector<Float> zeuthen;
	//ReceptorEsperarComunicacion receptor;
	public static  List<String> comunicacionesEntabladas;
	
	public AgenteReceptor() {
		super();
		peliculas= new Vector<String>();
		zeuthen= new Vector<Float>();
		peliculas.add("El conjuro");
		//zeuthen.add((float) Math.rint(Math.random()*9.5)+1);
		zeuthen.add((float) Math.random());
		peliculas.add("X-Men: Apocalipsis");
		zeuthen.add((float) Math.random());
		peliculas.add("CazaFantasmas");
		zeuthen.add((float) Math.random());
		peliculas.add("Imperium");
		zeuthen.add((float) Math.random());
		peliculas.add("El maestro del Dinero");
		zeuthen.add((float) Math.random());
		peliculas.add("Yo antes de ti");
		zeuthen.add((float) Math.random());
		
		
		comunicacionesEntabladas= new ArrayList<String>();
		//receptor=null;
	}
	
	protected void setup(){		
		getContentManager().registerLanguage(Ontologia.getCodecInstance());
		getContentManager().registerOntology(Ontologia.getInstance());
		
		//if(receptor==null)
			//receptor= new  ReceptorEsperarComunicacion(peliculas);
		
	//Behaviour ReceptorEsperaComunicacion=(receptor);
		
	  Behaviour ReceptorEsperaComunicacion=(new  ReceptorEsperarComunicacion(peliculas,zeuthen));
	   
	   
	   addBehaviour(ReceptorEsperaComunicacion);
			
			//addBehaviour(new  ComportamientoEsperarComunicacion());
			//addBehaviour(new MaquinaEstado(peliculas,false, null));  //false porque es un receptor
			
			// Registra el servicio en las paginas amarillas
			DFAgentDescription dfd = new DFAgentDescription();
			dfd.setName(getAID());
			ServiceDescription sd = new ServiceDescription();
			sd.setType("negociacion");
			sd.setName("peliculas");
			dfd.addServices(sd);
			try {
				DFService.register(this, dfd);
			}
			catch (FIPAException fe) {
				fe.printStackTrace();
			}
	   }	
	
	// doy de baja el servicio del DF
	protected void takeDown(){		
		try {
		DFService.deregister(this);
		}
		catch (FIPAException fe) {
		fe.printStackTrace();
		}
	}
}
