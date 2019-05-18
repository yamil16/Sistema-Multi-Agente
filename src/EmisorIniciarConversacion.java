package FinalUltimo2;

import jade.core.*;
import jade.core.behaviours.*;
import jade.lang.acl.*;
import FinalUltimo2.Ontologia.Ontologia;
public class EmisorIniciarConversacion extends Behaviour {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	boolean fin ;
	private static final String  ClaveMensajeTemplate="ClaveMensajeTemplate";
	
	private static final String ClaveAid="aid";
	//private boolean agenteEmisor;
	
	 public EmisorIniciarConversacion() {
		super();		
	
		// this.agenteEmisor=agenteEmisor;	
		 
	}
	
	 
	@Override
	
	public void action() {
		System.out.println(myAgent.getLocalName() +": Preparandose para enviar una propuesta de negociacion al receptor ");
		
		 AID idDestino=(AID) getDataStore().get(ClaveAid);      
        	
		 // Creación del mensaje ACLMessage
 		ACLMessage mensaje = new ACLMessage(ACLMessage.REQUEST);
		 
      	//Rellenar los campos necesarios del mensaje
         mensaje.setSender(myAgent.getAID());		     	
         mensaje.addReceiver(idDestino);               
         mensaje.setLanguage(myAgent.getContentManager().getLanguageNames()[0]);
         mensaje.setOntology(Ontologia.ONTOLOGY_NAME);
         String nroconversacion="ci"+System.currentTimeMillis();
         mensaje.setConversationId(nroconversacion);
         mensaje.setReplyWith("rw"+System.currentTimeMillis());        
         mensaje.setContent("Realizar una negociacion de Peliculas");   
		 
         		 
       //Envia el mensaje a los destinatarios
	 	 myAgent.send(mensaje);
	 	 
	 	   MessageTemplate mensajeTemplate =MessageTemplate.and(
	        		MessageTemplate.MatchConversationId(nroconversacion),
	        		MessageTemplate.or(MessageTemplate.MatchPerformative(ACLMessage.ACCEPT_PROPOSAL), MessageTemplate.MatchPerformative(ACLMessage.REJECT_PROPOSAL))
	        		);
	         getDataStore().put(ClaveMensajeTemplate, mensajeTemplate);   
	 	 
	 	   	 
	 	 fin = true;        
		
	}

	@Override
	public boolean done() {
		// TODO Auto-generated method stub
		return fin;
	}


}
