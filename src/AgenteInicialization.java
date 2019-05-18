package FinalUltimo2;


import java.util.*;

import FinalUltimo2.Ontologia.Ontologia;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.proto.SubscriptionInitiator;


public class AgenteInicialization extends Agent {
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Vector<String> preferencias;
	Vector<Float> zeuthen;
	public AgenteInicialization(){
		preferencias= new Vector<String>();
		zeuthen= new Vector<Float>();
		preferencias.add("Mision Imposible");
		zeuthen.add((float) Math.random());
		//zeuthen.add((float) Math.random()*10+1);
		preferencias.add("Buenos Vecinos");
		zeuthen.add((float) Math.random());
		preferencias.add("El hombre que conocia el infinito");
		zeuthen.add((float) Math.random());
		preferencias.add("mi villano Favorito");
		zeuthen.add((float) Math.random());
		preferencias.add("Mente implacable");
		zeuthen.add((float) Math.random());
		preferencias.add("Angry Bird");
		zeuthen.add((float) Math.random());
	}		
	
	protected void setup(){
		getContentManager().registerLanguage(Ontologia.getCodecInstance());
		getContentManager().registerOntology(Ontologia.getInstance());
		SubscripcionServicioMaquinaEstado();		
	}	
	
	private void SubscripcionServicioMaquinaEstado(){
		DFAgentDescription template = new DFAgentDescription();
		ServiceDescription sd1 = new ServiceDescription();
		sd1.setType("negociacion");
		sd1.setName("peliculas");
		template.addServices(sd1);
		addBehaviour( new SubscriptionInitiator(this,DFService.createSubscriptionMessage( this, getDefaultDF(),template, null))
		{
			private static final long serialVersionUID = 1L;
			protected void handleInform(ACLMessage inform) {
				try {
					DFAgentDescription[] dfds = DFService.decodeNotification(inform.getContent());

					for (DFAgentDescription dfAD : dfds){
						if (dfAD.getAllServices().hasNext()){
							System.out.println("Entablo comunicacion con el gente "+dfAD.getName());
							MaquinaEstado fsm = new MaquinaEstado(preferencias,zeuthen,true,dfAD.getName(),null); //true porque es un emisor
							addBehaviour(fsm);
						}
					}
				}
				catch (FIPAException fe) {fe.printStackTrace(); }
			}
		}
		);

	}
	
}
