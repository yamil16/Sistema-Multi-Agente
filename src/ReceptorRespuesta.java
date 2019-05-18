package FinalUltimo2;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import FinalUltimo2.Ontologia.IsMyZeuthen;
import FinalUltimo2.Ontologia.Movie;
import FinalUltimo2.Ontologia.SeeMovie;
import jade.content.ContentElement;
import jade.content.lang.Codec.CodecException;
import jade.content.onto.OntologyException;
import jade.content.onto.basic.Action;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;


public class ReceptorRespuesta  extends Behaviour{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//VER EL DONE
	 private boolean fin;
	 private int estado;
	 
	 
	 private Set<Date> fechasComprometidas;
	 //private Set<String> fechasComprometidas;
	 
	 
	 private static final String ClaveUltimoMensajeRecibido="mensajeRecibido";
	 private static final String ClaveUltimoMensajeZeuthenRecibido="mensajeZeuthenRecibido";
		private static final String ClaveUltimoMensajeZeuthen="mensajeZeuthen";

		private String nombrePelicula;

		public ReceptorRespuesta(){
		fin = false;
		estado=0;
		
		
	//	fechasComprometidas= new HashSet<String>();	
		fechasComprometidas= new HashSet<Date>();	
	}	
	public void action() {
		
	
		//Obtengo el mensaje recibido del DataSTore
		ACLMessage respuesta;
		respuesta=(ACLMessage) getDataStore().get(ClaveUltimoMensajeRecibido);	 
		if(respuesta==null){			
			System.out.println(myAgent.getLocalName() +": Error, no se ha almancenado correctamente el mensaje recibido del emisor en el data store");
			
		 	block();
		}
		else{
		
			ACLMessage mensaje=respuesta.createReply();	
			
			
			//estom hago para sacar la fecha de la pelicula y comparar que no exista con otra ya propuesta
			ContentElement ce;
			Date fechaString=new Date();
			nombrePelicula = "";
			try {
				ce=myAgent.getContentManager().extractContent(respuesta);			
				SeeMovie sm= (SeeMovie) ((Action)ce).getAction();
				 Movie pelicula= sm.getMovie();
				//SeeMovie sm=(SeeMovie) myAgent.getContentManager().extractContent(respuesta);
				 fechaString=sm.getDate();
				 nombrePelicula=pelicula.getName();
				
			} catch (CodecException | OntologyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		
		float valueEnviado=-1;
		float valueRecibido=-1;		
		ACLMessage mensajeRecibido=(ACLMessage)	 getDataStore().get(ClaveUltimoMensajeZeuthenRecibido);        	
   	 	ACLMessage mensajeEnviado=(ACLMessage) getDataStore().get(ClaveUltimoMensajeZeuthen);
   	 
   	 	ContentElement ceENVIADO;
   	 	ContentElement ceRECIBIDO;
       	 
       	 try {
       		ceENVIADO=myAgent.getContentManager().extractContent(mensajeEnviado);			
       		IsMyZeuthen smENVIADO= (IsMyZeuthen)ceENVIADO;   //((Action)ce).getAction();
       		ceRECIBIDO=myAgent.getContentManager().extractContent(mensajeRecibido);			
       		IsMyZeuthen smRECIBIDO= (IsMyZeuthen)ceRECIBIDO;   //((Action)ce).getAction();
       		valueEnviado=smENVIADO.getValue();
       		valueRecibido=smRECIBIDO.getValue();
				
			} catch (CodecException | OntologyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
			
			
			/*
			
			String Contenido=respuesta.getContent();
			int inicio = Contenido.indexOf("%");
			String fechaString=Contenido.substring(inicio+1, Contenido.length());
			
			*/
			
			
			
			//SE ACEPTA LA PROPUESTA
			//if((Math.random()>0.9)&& (!fechasComprometidas.contains(fechaString))){	//acepta				
       	if((valueEnviado < valueRecibido)&& (!fechasComprometidas.contains(fechaString))){	//acepta
				 fechasComprometidas.add(fechaString);	        	 
				 mensaje.setPerformative(ACLMessage.ACCEPT_PROPOSAL);	        	 
				 
				 
				 mensaje.setContent(respuesta.getContent());
				 
				 
				 //mensaje.setContent(nombrePelicula);
			     
				 
		
				
			     
			      estado=1;
			      
	         } //NO SE ACEPTA LA PROPUSTA			
	         else{ //sino que el emisor vuelva a proponer	        		        	
	        	 mensaje.setPerformative(ACLMessage.REJECT_PROPOSAL);
	        	 
	        	 
	        	mensaje.setContent(respuesta.getContent());
	        	 
	        	 
	        	 //mensaje.setContent(nombrePelicula);
	        	 
	        	 
	        	 System.out.println("Mensaje respuesta rechazo enviado al emisor "+mensaje.toString());
	        	 
	        	 estado=0;
	         }	
			 myAgent.send(mensaje);
			fin=true;
		 }
			
	}

	@Override
	public boolean done() {
		// TODO Auto-generated method stub
	//return true;
		return fin;
	}
	@Override
	public void reset() {
		fin = false;
		//fechasComprometidas= new HashSet<String>();	
	};
	public int onEnd() {
		// TODO Auto-generated method stub
		return estado ;
		}

}
