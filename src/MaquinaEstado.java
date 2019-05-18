package FinalUltimo2;

import java.util.Vector;

import jade.core.AID;
import jade.core.behaviours.DataStore;
import jade.core.behaviours.FSMBehaviour;
import jade.lang.acl.ACLMessage;

public class MaquinaEstado extends FSMBehaviour {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	DataStore ds;
	private static final String Estado_emisor= "emisor";
	private static final String Estado_respEmisor= "respEmisor";
	private static final String Estado_finEmisor= "finEmisor";
	private static final String Estado_calcularEnviarZeuthen= "calcularEnviarZeuthen";
	private static final String Estado_recibirZeuthen= "recibirZeuthen";
	private static final String Estado_esperarPropuesta= "esperarPropuesta";
	private static final String Estado_evaluarPrpuestaResponder= "evaluarPrpuestaResponder";
	private static final String Estado_finReceptor= "finReceptor";
	private static final String Estado_emisorInicial="emisorInicial";
	private static final String estadoRespuestaComunicacion="respuestaComunicacion";
	//private static final String estadoRecibirPrimeraVezZeuthen="recibirPrimeraVezZeuthen";
	//private static final String estadoEnviarPrimeraVezZeuthen="enviarPrimeraVezZeuthen";
	
	private static final String estadoRecibirPrimeraVezZeuthenEmisor="recibirPrimeraVezZeuthenEmisor";
	private static final String estadoRecibirPrimeraVezZeuthenReceptor="recibirPrimeraVezZeuthenReceptor";
	private static final String estadoEnviarPrimeraVezZeuthenEmisor="enviarPrimeraVezZeuthenEmisor";
	private static final String estadoEnviarPrimeraVezZeuthenReceptor="enviarPrimeraVezZeuthenReceptor";
	
	private static final String ClaveAid="aid";
	private AID receptorAid;
	private AID emisorAid;
	private boolean agenteEmisor;
	private int propuestasEnviadas;
	private int zeuthenRecibidos;
	private static final String ClaveUltimoMensajeRecibido="mensajeRecibido";
	
	public MaquinaEstado(Vector<String>peliculas,Vector<Float> zeuthen, boolean emisor, AID receptorAid,ACLMessage  mensaje) {		
	
		DataStore ds;
    	ds = new DataStore();
    	 ds.put(ClaveUltimoMensajeRecibido,mensaje);		
		
		
		if(emisor)
			 ds.put(ClaveUltimoMensajeRecibido,mensaje);		
		ds.put(ClaveAid, receptorAid);
		this.agenteEmisor=emisor;
		this.propuestasEnviadas=0;
		setZeuthenRecibidos(0);
		
		
	//	EmisorIniciarConversacion e0 = new EmisorIniciarConversacion(peliculas);
	//	e0.setDataStore(ds);
	//	this.registerState(e0, Estado_emisorInicial);
		
		EmisorRespuesta e1 = new EmisorRespuesta();
		e1.setDataStore(ds);
		this.registerState(e1, Estado_respEmisor);
		
		EmisorFin e2 = new EmisorFin(agenteEmisor);
		e2.setDataStore(ds);
		this.registerState(e2, Estado_finEmisor);
		
		ZeuthenCalcularEnviar e3 = new ZeuthenCalcularEnviar(zeuthen);
		e3.setDataStore(ds);
		this.registerState(e3, Estado_calcularEnviarZeuthen);
		
		ZeuthenRecibir e4 = new ZeuthenRecibir(emisor);
		e4.setDataStore(ds);
		this.registerState(e4, Estado_recibirZeuthen);
		
		
		ReceptorEsperarMasPropuestas e5 = new ReceptorEsperarMasPropuestas();
		e5.setDataStore(ds);
		this.registerState(e5, Estado_esperarPropuesta);
		
		ReceptorRespuesta e6 = new ReceptorRespuesta();
		e6.setDataStore(ds);
		this.registerState(e6, Estado_evaluarPrpuestaResponder);
		
		ReceptorFin e7 = new ReceptorFin(agenteEmisor);
		e7.setDataStore(ds);
		this.registerState(e7, Estado_finReceptor);
		
		//este no va  a ir ahora
		/*
		ReceptorEsperarComunicacion e8 = new ReceptorEsperarComunicacion(peliculas);
		e8.setDataStore(ds);
		this.registerState(e8, Estado_esperarComunicacion);
		*/
		EmisorEnviarMensaje e9= new EmisorEnviarMensaje(peliculas);
		e9.setDataStore(ds);
		this.registerState(e9, Estado_emisor);
		
		//aca empieza a cambiar 
		EmisorRespuestaComunicacion e10= new EmisorRespuestaComunicacion();
		e10.setDataStore(ds);
		this.registerState(e10, estadoRespuestaComunicacion);
		//System.out.println("estoy en maquina estado "+myAgent.getLocalName()+" quiero saber si soy emisor "+emisor);
		
		/*
		ZeuthenRecibirPrimeraVez e11= new ZeuthenRecibirPrimeraVez(agenteEmisor);
		e11.setDataStore(ds);
		this.registerState(e11, estadoRecibirPrimeraVezZeuthen);
		
		ZeuthenCalcularPrimeraVez e12= new ZeuthenCalcularPrimeraVez(zeuthen);
		e12.setDataStore(ds);
		this.registerState(e12, estadoEnviarPrimeraVezZeuthen);
		
		*/
		
		
		ZeuthenRecibirPrimeraVez e13= new ZeuthenRecibirPrimeraVez(agenteEmisor);
		e13.setDataStore(ds);
		this.registerState(e13, estadoRecibirPrimeraVezZeuthenEmisor);
		
		ZeuthenCalcularPrimeraVez e14= new ZeuthenCalcularPrimeraVez(zeuthen);
		e14.setDataStore(ds);
		this.registerState(e14, estadoEnviarPrimeraVezZeuthenEmisor);
		
		ZeuthenRecibirPrimeraVez e15= new ZeuthenRecibirPrimeraVez(agenteEmisor);
		e15.setDataStore(ds);
		this.registerState(e15, estadoRecibirPrimeraVezZeuthenReceptor);
		
		ZeuthenCalcularPrimeraVez e16= new ZeuthenCalcularPrimeraVez(zeuthen);
		e16.setDataStore(ds);
		this.registerState(e16, estadoEnviarPrimeraVezZeuthenReceptor);
		
	
		
		
		
		
		
		if(emisor) {
			EmisorIniciarConversacion e0 = new EmisorIniciarConversacion();
			e0.setDataStore(ds);
			this.registerFirstState(e0,Estado_emisorInicial);
			
			
			
			
		}
		else{
		//	System.out.println("entro aca el receptor llamado "+myAgent.getLocalName());
			this.registerFirstState(e5,Estado_esperarPropuesta);
			
			
			
			
			
		}
		this.registerLastState(e2, Estado_finEmisor);
		this.registerLastState(e7, Estado_finReceptor);
		
		//REceptor
		
		String[] toBeResetEsperarMasPropuesta = {Estado_esperarPropuesta};
		this.registerTransition(Estado_esperarPropuesta, estadoEnviarPrimeraVezZeuthenReceptor,0,toBeResetEsperarMasPropuesta ); //propongo otra peli
		this.registerTransition(Estado_esperarPropuesta, Estado_finEmisor,1 ); //espero una propuesta
		
		this.registerDefaultTransition(estadoEnviarPrimeraVezZeuthenReceptor, estadoRecibirPrimeraVezZeuthenReceptor);
		
		String[] toBeResetPrimeraVezZeuthenReceptor = {estadoRecibirPrimeraVezZeuthenReceptor};
		this.registerDefaultTransition(estadoRecibirPrimeraVezZeuthenReceptor, Estado_evaluarPrpuestaResponder,toBeResetPrimeraVezZeuthenReceptor);
		
		String[] toBeReset3	= {Estado_evaluarPrpuestaResponder};		
		this.registerTransition(Estado_evaluarPrpuestaResponder, Estado_emisor,0 ,toBeReset3); //calculo zeuthen porque rechazo la propuesta
		this.registerTransition(Estado_evaluarPrpuestaResponder, Estado_finReceptor,1 ); //termino hay acuerdo
		
		//Hago la parte del emisor
		
		this.registerDefaultTransition(Estado_emisorInicial, estadoRespuestaComunicacion); //espero respuesta		
		
		String[] toBeReset1 = {estadoRespuestaComunicacion};		
		this.registerTransition(estadoRespuestaComunicacion, Estado_emisor,1 ,toBeReset1); //empezo la comunicacion
		this.registerTransition(estadoRespuestaComunicacion, Estado_finEmisor,0 ); //termina no se acepto la comunicacion
		
		String[] toBeResetEnviarMasMensjae = {Estado_emisor};
		this.registerTransition(Estado_emisor, Estado_finEmisor,1 ); //no hay acuerdo se llego al maximo de propuestas
		this.registerTransition(Estado_emisor, estadoEnviarPrimeraVezZeuthenEmisor,0,toBeResetEnviarMasMensjae ); //espero respuesta
		
		this.registerDefaultTransition(estadoEnviarPrimeraVezZeuthenEmisor, estadoRecibirPrimeraVezZeuthenEmisor);
		
		String[] toBeResetPrimeraVezZeuthenEmisor = {estadoRecibirPrimeraVezZeuthenEmisor};
		this.registerDefaultTransition(estadoRecibirPrimeraVezZeuthenEmisor, Estado_respEmisor,toBeResetPrimeraVezZeuthenEmisor);
		
		String[] toBeReset2 = {Estado_respEmisor};		
		this.registerTransition(Estado_respEmisor, Estado_esperarPropuesta,0 ,toBeReset2); //calculo zeuthen
		this.registerTransition(Estado_respEmisor, Estado_finEmisor,1 ); //termina hay acuerdo
		
		
		
		
		
		
		
		/*
		String[] toBeResetCalcularZeuthen = {Estado_calcularEnviarZeuthen};		
		this.registerDefaultTransition(Estado_calcularEnviarZeuthen,Estado_recibirZeuthen);//,toBeResetCalcularZeuthen);
		
		//DA INCONSISTENCIA SI LO SEPARO PERO EL PROBLEMA ESTA ACA
		String[] toBeResetZ = {Estado_recibirZeuthen};
		this.registerTransition(Estado_recibirZeuthen, Estado_emisor,0,toBeResetZ ); //propongo otra peli
		this.registerTransition(Estado_recibirZeuthen, Estado_esperarPropuesta,1,toBeResetZ ); //espero una propuesta
		
		*/
//Vamos a mejorar esta parte
		
		/*
		this.registerDefaultTransition(Estado_emisorInicial, estadoRespuestaComunicacion); //espero respuesta
		//this.registerDefaultTransition(Estado_emisorInicial,Estado_calcularEnviarZeuthen);
		String[] toBeReset1 = {estadoRespuestaComunicacion};
		//this.registerTransition(estadoRespuestaComunicacion, Estado_calcularEnviarZeuthen,1 ,toBeReset1); //empezo la comunicacion
		this.registerTransition(estadoRespuestaComunicacion, estadoEnviarPrimeraVezZeuthenEmisor,1 ,toBeReset1); //empezo la comunicacion
		this.registerTransition(estadoRespuestaComunicacion, Estado_finEmisor,0 ); //termina no se acepto la comunicacion
		
		
		//nuevo
		this.registerDefaultTransition(estadoEnviarPrimeraVezZeuthenEmisor, estadoRecibirPrimeraVezZeuthenEmisor);
		String[] toBeResetPrimeraVezZeuthenEmisor = {estadoRecibirPrimeraVezZeuthenEmisor};
		this.registerDefaultTransition(estadoRecibirPrimeraVezZeuthenEmisor, Estado_emisor,toBeResetPrimeraVezZeuthenEmisor);
		
		//nuevo
		this.registerDefaultTransition(estadoEnviarPrimeraVezZeuthenReceptor, estadoRecibirPrimeraVezZeuthenReceptor);
		String[] toBeResetPrimeraVezZeuthenReceptor = {estadoRecibirPrimeraVezZeuthenReceptor};
		this.registerDefaultTransition(estadoRecibirPrimeraVezZeuthenReceptor, Estado_esperarPropuesta,toBeResetPrimeraVezZeuthenReceptor);
		
		
//si me toca enviar mensaje
		String[] toBeResetEnviarMasMensjae = {Estado_emisor};
		this.registerTransition(Estado_emisor, Estado_finEmisor,1 ); //no hay acuerdo se llego al maximo de propuestas
		this.registerTransition(Estado_emisor, Estado_respEmisor,0,toBeResetEnviarMasMensjae ); //espero respuesta
		
		String[] toBeReset2 = {Estado_respEmisor};
		//this.registerTransition(Estado_respEmisor, Estado_calcularEnviarZeuthen,0 ,toBeReset1); //calculo zeuthen
		this.registerTransition(Estado_respEmisor, estadoEnviarPrimeraVezZeuthenReceptor,0 ,toBeReset2); //calculo zeuthen
		this.registerTransition(Estado_respEmisor, Estado_finEmisor,1 ); //termina hay acuerdo
		
//si me toca recibir propuestas
		String[] toBeResetEsperarMasPropuesta = {Estado_esperarPropuesta};
		this.registerTransition(Estado_esperarPropuesta, Estado_evaluarPrpuestaResponder,0,toBeResetEsperarMasPropuesta ); //propongo otra peli
		this.registerTransition(Estado_esperarPropuesta, Estado_finEmisor,1 ); //espero una propuesta
		//this.registerDefaultTransition(Estado_esperarPropuesta,Estado_evaluarPrpuestaResponder);
		
		String[] toBeReset3	= {Estado_evaluarPrpuestaResponder};
		//this.registerTransition(Estado_evaluarPrpuestaResponder, Estado_calcularEnviarZeuthen,0 ,toBeReset2); //calculo zeuthen porque rechazo la propuesta
		this.registerTransition(Estado_evaluarPrpuestaResponder, estadoEnviarPrimeraVezZeuthenEmisor,0 ,toBeReset3); //calculo zeuthen porque rechazo la propuesta
		this.registerTransition(Estado_evaluarPrpuestaResponder, Estado_finReceptor,1 ); //termino hay acuerdo
		
		
		
		
		
		
		
		
		
		*/
		
		
		
		
		/*
		this.registerTransition(Estado_emisor, Estado_finEmisor,1 ); //no hay acuerdo se llego al maximo de propuestas
		this.registerTransition(Estado_emisor, Estado_respEmisor,0 ); //espero respuesta
		
		String[] toBeReset1 = {Estado_respEmisor};
		this.registerTransition(Estado_respEmisor, Estado_calcularEnviarZeuthen,0 ,toBeReset1); //calculo zeuthen
		this.registerTransition(Estado_respEmisor, Estado_finEmisor,1 ); //termina hay acuerdo
		
		
		this.registerDefaultTransition(Estado_calcularEnviarZeuthen,Estado_recibirZeuthen);
		
		String[] toBeResetZ = {Estado_recibirZeuthen};
		this.registerTransition(Estado_recibirZeuthen, Estado_emisor,0,toBeResetZ ); //propongo otra peli
		this.registerTransition(Estado_recibirZeuthen, Estado_esperarPropuesta,1,toBeResetZ ); //espero una propuesta
		
		
		String[] toBeResetEsperarMasPropuesta = {Estado_esperarPropuesta};
		this.registerTransition(Estado_esperarPropuesta, Estado_evaluarPrpuestaResponder,0,toBeResetEsperarMasPropuesta ); //propongo otra peli
		this.registerTransition(Estado_esperarPropuesta, Estado_finEmisor,1 ); //espero una propuesta
		//this.registerDefaultTransition(Estado_esperarPropuesta,Estado_evaluarPrpuestaResponder);
		
		String[] toBeReset2	= {Estado_evaluarPrpuestaResponder};
		this.registerTransition(Estado_evaluarPrpuestaResponder, Estado_calcularEnviarZeuthen,0 ,toBeReset2); //calculo zeuthen porque rechazo la propuesta
		this.registerTransition(Estado_evaluarPrpuestaResponder, Estado_finReceptor,1 ); //termino hay acuerdo
		*/
		
		//esto es nuevo
		
		/*
		
		String[] toBeResetCiclicaVuelvoAEmpezarReceptor	= {Estado_finReceptor};
	//	this.registerDefaultTransition(Estado_finReceptor,Estado_esperarPropuesta,toBeResetCiclicaVuelvoAEmpezar);
		//this.registerTransition(Estado_finReceptor, Estado_evaluarPrpuestaResponder,0 ,toBeResetCiclicaVuelvoAEmpezarReceptor); //me voy a esperar mas propuestas
		this.registerTransition(Estado_finReceptor, Estado_esperarPropuesta,0 ,toBeResetCiclicaVuelvoAEmpezarReceptor); //me voy a esperar mas propuestas
		this.registerTransition(Estado_finReceptor, Estado_finEmisor,1,toBeResetCiclicaVuelvoAEmpezarReceptor ); //me voy a emisor fin
		
		String[] toBeResetCiclicaVuelvoAEmpezarEmisor	= {Estado_finEmisor};
		this.registerTransition(Estado_finEmisor, Estado_esperarPropuesta,0 ,toBeResetCiclicaVuelvoAEmpezarEmisor); //me voy a esperar mas propuestas
		this.registerTransition(Estado_finEmisor, Estado_finEmisor,1,toBeResetCiclicaVuelvoAEmpezarEmisor ); //me quedo em emisor fin
		
		*/
	
	}
	

	public AID getReceptorAid() {
		return receptorAid;
	}



	public void setReceptorAid(AID receptorAid) {
		this.receptorAid = receptorAid;
	}



	public AID getEmisorAid() {
		return emisorAid;
	}



	public void setEmisorAid(AID emisorAid) {
		this.emisorAid = emisorAid;
	}





	public int getPropuestasEnviadas() {
		return propuestasEnviadas;
	}



	public void setPropuestasEnviadas(int propuestasEnviadas) {
		this.propuestasEnviadas = propuestasEnviadas;
	}



	public int getZeuthenRecibidos() {
		return zeuthenRecibidos;
	}



	public void setZeuthenRecibidos(int zeuthenRecibidos) {
		this.zeuthenRecibidos = zeuthenRecibidos;
	}

}
