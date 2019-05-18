package FinalUltimo2;

import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class EmisorRespuestaComunicacion  extends Behaviour {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean fin;
	private int estado;	
	private static final String ClaveUltimoMensajeRecibido="mensajeRecibido";
	private static final String  ClaveMensajeTemplate="ClaveMensajeTemplate";
	public EmisorRespuestaComunicacion() {
		// TODO Auto-generated constructor stub
		fin = false;
		estado=0;	
	}

	public void action() {
		
		//Espero recibir un mensaje del tipo del template creado en emisor
		ACLMessage mensaje = myAgent.receive((MessageTemplate) getDataStore().get(ClaveMensajeTemplate));		
		if(mensaje!=null){
             getDataStore().put(ClaveUltimoMensajeRecibido, mensaje); //Almaceno el mensaje que llego
             if(mensaje.getPerformative()==ACLMessage.ACCEPT_PROPOSAL){            
            	 estado=1;
            	 //emiezo la negociacion
             }                
            else{ //no empiezo la negociacion no quiere ver pelicula
            	 estado=0;
             } 
            fin = true;
         }		
		else
        {
            System.out.println(myAgent.getLocalName() +": Esperando a recibir respuesta de Aceptar o no negociar una comunicacion por una pelicula ...");
            block();
            
        }		
		
	}

	@Override
	public boolean done() {
		// TODO Auto-generated method stub
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
