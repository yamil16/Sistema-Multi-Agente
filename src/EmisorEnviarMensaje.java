package FinalUltimo2;

import java.util.Date;
import java.util.Vector;

import FinalUltimo2.Ontologia.Movie;
import FinalUltimo2.Ontologia.SeeMovie;
import jade.content.lang.Codec.CodecException;
import jade.content.onto.OntologyException;
import jade.content.onto.basic.Action;
import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class EmisorEnviarMensaje extends Behaviour {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String ClaveUltimoMensajeRecibido="mensajeRecibido";
	private static final String  ClaveMensajeTemplate="ClaveMensajeTemplate";
	
	private Vector<String> peliculas;
	private int propuestasEnviadas;		
	private int estado;
	boolean fin;
	public EmisorEnviarMensaje(Vector<String> peliculas)  {
		// TODO Auto-generated constructor stub
		this.peliculas=peliculas;
		this.propuestasEnviadas=1; //se que va a enviar una pelicula antes de llegar aca
		
	}
	public void action(){
		ACLMessage respuesta=(ACLMessage) getDataStore().get(ClaveUltimoMensajeRecibido);
		ACLMessage mensaje = respuesta.createReply();
		
		 if(propuestasEnviadas<peliculas.size()){			
			 mensaje.setPerformative(ACLMessage.PROPOSE);
    		 Date fecha = new Date();
    		
    		 
             //Esto es nuevo
    		 Movie m= new Movie(peliculas.get(propuestasEnviadas));
    		 SeeMovie sm=new SeeMovie(fecha,m);
    		 try {
    			myAgent.getContentManager().fillContent(mensaje, new Action((AID)mensaje.getAllReceiver().next(),sm));
    		} catch (CodecException | OntologyException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    		 
    		
    		 
    		 
    		 //myAgent.getContentManager().fillContent(mensaje, SeeMovie); seteo objeto extracContac
    		 
    		 
		   // mensaje.setContent(peliculas.get(propuestasEnviadas)+"%"+fecha); //seria una propuesta  y la fecha de la misma
		    
		    
		     myAgent.send(mensaje);    	 	 
	         propuestasEnviadas++;	         	        
	         		         
	         //Creo el template y va porque en caso de recptor no se si va  a tener template
	       //  MessageTemplate mensajeTemplate = MessageTemplate.or(MessageTemplate.MatchPerformative(ACLMessage.ACCEPT_PROPOSAL), MessageTemplate.MatchPerformative(ACLMessage.REJECT_PROPOSAL));	        
	        // getDataStore().put(ClaveMensajeTemplate, mensajeTemplate);
	         
	         
	       //Creo el template
	         MessageTemplate mensajeTemplate =MessageTemplate.and(
	        		MessageTemplate.MatchConversationId(mensaje.getConversationId()),
	        		MessageTemplate.or(MessageTemplate.or(MessageTemplate.MatchPerformative(ACLMessage.ACCEPT_PROPOSAL), MessageTemplate.MatchPerformative(ACLMessage.CANCEL)), MessageTemplate.MatchPerformative(ACLMessage.REJECT_PROPOSAL))
	        		);
	         getDataStore().put(ClaveMensajeTemplate, mensajeTemplate);         
	         
	         //fijo el proximo estado
	         estado=0; //voy a estado EmisorResp
		 }
		 else{
        	 System.out.println(myAgent.getLocalName() +": llego al maximo de propuestas enviadas ");
    	
        	 mensaje.setPerformative(ACLMessage.CANCEL);
        	 
        	 //Esto es nuevo
        	 Date fecha = new Date();
    		 Movie m= new Movie("Fallo la Conversacion");
    		 SeeMovie sm=new SeeMovie(fecha,m);
    		 try {
    			myAgent.getContentManager().fillContent(mensaje, new Action((AID)mensaje.getAllReceiver().next(),sm));
    		} catch (CodecException | OntologyException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
        	 
        	// mensaje.setContent("Fallo la conversacion");
        	 
        	 
        	 myAgent.send(mensaje);
        	       	        	 
        	 
        	  estado=1; //Voy a estado Emisorfin	       
         }	 
      
		 fin=true;	 
	}

	@Override
	public boolean done() {
		// TODO Auto-generated method stub
		//return false;
		return fin;
	}
	@Override
	public void reset() {
		fin = false;
	};
	public int onEnd() {
		// TODO Auto-generated method stub
		return estado ;
		}
}
