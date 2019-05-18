package FinalUltimo2;

import jade.core.behaviours.Behaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;

public class EmisorFin extends Behaviour{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8948692666864639770L;
	/**
	 * 
	 */	

	private static final String ClaveUltimoMensajeRecibido="mensajeRecibido";
	boolean fin ;
	boolean agenteEmisor;
	int estado;
	
	public EmisorFin(boolean agenteEmisor) {
		super();
		this.fin = false;
		this.agenteEmisor=agenteEmisor;
		estado=1;
	}

	@Override
	public void action() {  
		 ACLMessage respuesta=(ACLMessage) getDataStore().get(ClaveUltimoMensajeRecibido);
		 if(respuesta.getPerformative()==ACLMessage.ACCEPT_PROPOSAL)
			 System.out.println(myAgent.getLocalName() +" Se acepto la respuesta: "+respuesta.getContent()+" hay acuerdo");
		 else
			 System.out.println(myAgent.getLocalName() +" No se acepto la respuesta: "+respuesta.getContent()+". No hay acuerdo");
		
		 fin=true;
		 
		/* 
		 //esto es nuevo
		 if(agenteEmisor){
			 myAgent.doDelete();
			 estado=1; //me quedo aca terminp
		 }	 
		 else
			 estado=0; //voy a esperar_propuestas
		*/
	}
	
	 protected void takeDown() {
         System.out.println("Agente "+myAgent.getLocalName()+" termino de ejecutarse.");
         //lo desagrego  de los servicios 
         try {            	 
			DFService.deregister(myAgent,myAgent.getAID());
		} catch (FIPAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    
     }

	@Override
	public boolean done() {
		//if(fin==true)
			//myAgent.doDelete();
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
