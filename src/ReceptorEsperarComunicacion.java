package FinalUltimo2;


import java.util.Vector;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.DataStore;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class ReceptorEsperarComunicacion extends Behaviour { 
//extends Agent{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	private static final String ClaveUltimoMensajeRecibido="mensajeRecibido";
	private Vector<String> peliculas;
	private Vector<Float> zeuthen;
	//private List<String> comunicacionesEntabladas;

	public ReceptorEsperarComunicacion(Vector<String> peliculas, Vector<Float> zeuthen) {
		// TODO Auto-generated constructor stub	
		
		this.peliculas=peliculas;
		this.zeuthen=zeuthen;
	//	comunicacionesEntabladas= new ArrayList<String>();
	}

	@Override
	public void action() {
		// TODO Auto-generated method stub
		
		 MessageTemplate mensajeTemplateNuevo = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
         ACLMessage mensaje = myAgent.receive(mensajeTemplateNuevo);			//ACLMessage mensaje = myAgent.receive();         
        if (mensaje!= null)
        {   
        	DataStore ds;
        	ds = new DataStore();
        	 ds.put(ClaveUltimoMensajeRecibido,mensaje);	
        	ACLMessage respuesta=mensaje.createReply();	
			respuesta.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
			respuesta.setContent("Acepto negociar una Pelicula");
			 myAgent.send(respuesta);        	
        	 myAgent.addBehaviour(new MaquinaEstado(peliculas,zeuthen,false,null,mensaje)); //Agrego nuevo comportamiento desde un comportamiento
        	 //ds = new DataStore();
        }
      //  }  		
        else
        {
            System.out.println(myAgent.getLocalName() +": Esperando a recibir propuestas de una negociacion de pelicula...");
             block();                 
        }	
		
	}	 
        	//esto es nuevo 
        	/*
        		//if ( getDataStore().get(ClaveMensajeTemplate)!=null){ //creo una respuesta de rechazo
        		if(!AgenteReceptor.comunicacionesEntabladas.isEmpty()){        			
        		
        			ACLMessage respuesta=mensaje.createReply();	
        			respuesta.setPerformative(ACLMessage.CANCEL);
        			 myAgent.send(respuesta);
				
        		}
        		else{*/
        			//AgenteReceptor.comunicacionesEntabladas.add(mensaje.getConversationId());
        	/*
				 //Creo el template
        			MessageTemplate mensajeTemplate =MessageTemplate.and(
		        		MessageTemplate.MatchConversationId(mensaje.getConversationId()),
		        		MessageTemplate.or(MessageTemplate.MatchPerformative(ACLMessage.PROPOSE), MessageTemplate.MatchPerformative(ACLMessage.CANCEL))
		        		);
        			getDataStore().put(ClaveMensajeTemplate, mensajeTemplate);    
		        	
		        	
		        	
		        	
		        	
		        	
		        	DataStore ds;
		        	ds = new DataStore();
		        	ds.put(ClaveUltimoMensajeRecibido, mensaje); //Almaceno el mensaje que llego   
		           // getDataStore().put(ClaveUltimoMensajeRecibido, mensaje); //Almaceno el mensaje que llego   
		            //addBehaviour(new MaquinaEstado(peliculas,false, null,null));
		            
		            //Guardo el msj template ClaveMensajeTemplate
		        	ds.put(ClaveMensajeTemplate, mensajeTemplate);
		        	//getDataStore().put(ClaveMensajeTemplate, mensajeTemplate);
		            fin = true;
		            
		       	 myAgent.addBehaviour(new MaquinaEstado(peliculas,zeuthen,false,null,ds)); //Agrego nuevo comportamiento desde un comportamiento
		          //  myAgent.addBehaviour(new MaquinaEstado(peliculas,false,null)); //Agrego nuevo comportamiento desde un comportamiento         
		           // Behaviour ReceptorEsperaComunicacion=(new  ReceptorEsperarComunicacion(peliculas));
		           // myAgent.removeBehaviour(ReceptorEsperaComunicacion);
		            //myAgent.removeBehaviour(this);//Borramos el primer comportamiento;
		        */
		       	 
		

	
	 
	
	@Override
	public boolean done() {
		// TODO Auto-generated method stub
		//ver si es false
		//return fin;
	return false;
		
	}
	/*
	 public int onEnd(){
	//	 myAgent.addBehaviour(new MaquinaEstado(peliculas,false,null)); //Agrego nuevo comportamiento desde un comportamiento    
	//	 myAgent.removeBehaviour(this);
		 return 0;
	 }

	*/
	
	 
}
