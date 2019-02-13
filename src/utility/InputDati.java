package utility;

import static java.lang.Character.toUpperCase;
import static java.lang.String.valueOf;
import static java.lang.System.getProperty;

import java.util.*;

public class InputDati
{

    private static Scanner lettore = creaScanner();

    private final static String ERRORE_FORMATO = "Attenzione: il dato inserito non e' nel formato corretto";
    private final static String ERRORE_MINIMO = "Attenzione: e' richiesto un valore maggiore o uguale a ";
    private final static String ERRORE_STRINGA_VUOTA = "Attenzione: non hai inserito alcun carattere";
    private final static String ERRORE_MASSIMO = "Attenzione: e' richiesto un valore minore o uguale a ";
    private final static String MESSAGGIO_AMMISSIBILI = "Attenzione: i caratteri ammissibili sono: ";
    private final static String FORMATO_DATA ="(GG/MM/AAAA)";
    private final static int LUNGHEZZA_CARATTERI_DATA = 10;
    private final static int CHAR_SEPARATORE_DATA1 = 2;
    private final static int CHAR_SEPARATORE_DATA2 = 5;
    private final static char RISPOSTA_SI = 'S';
    private final static char RISPOSTA_NO = 'N';
    private final static char COLONNA ='\t';
    private final static String SEPARATORE = "/";
	private static final String FORMATO_DATA_ERR = "Errore formato data, reinserire.";
	private static final String FORMATO_ERR = "Errore formato, reinserire.";
	private static final String DATA_FUTURA_ERR = "Errore: inserire una data precedente o uguale ad oggi: ";
	private static final String[] DOMINIO_MAIL ={".it", ".com", ".net", ".de", ".uk"};
	

    private static Scanner creaScanner() {
        Scanner creato = new Scanner(System.in);
        creato.useDelimiter(getProperty("line.separator"));
        return creato;
    }

    public static String leggiStringa(String messaggio) {
        System.out.print(messaggio);
        return lettore.next();
    }

    public static String leggiStringaNonVuota(String messaggio) {
        boolean finito = false;
        String lettura = null;
        do
        {
            lettura = leggiStringa(messaggio);
            lettura = lettura.trim();
            if (lettura.length() > 0)
            {
                finito = true;
            } else
            {
                System.out.println(ERRORE_STRINGA_VUOTA);
            }
        } while (!finito);

        return lettura;
    }

    public static char leggiChar(String messaggio) {
        boolean finito = false;
        char valoreLetto = '\0';
        do
        {
            System.out.print(messaggio);
            String lettura = lettore.next();
            if (lettura.length() > 0)
            {
                valoreLetto = lettura.charAt(0);
                finito = true;
            } else
            {
                System.out.println(ERRORE_STRINGA_VUOTA);
            }
        } while (!finito);
        return valoreLetto;
    }

    public static char leggiUpperChar(String messaggio, String ammissibili) {
        boolean finito = false;
        char valoreLetto = '\0';

        do
        {
            valoreLetto = leggiChar(messaggio);
            valoreLetto = toUpperCase(valoreLetto);
            if (ammissibili.indexOf(valoreLetto) != -1)
            {
                finito = true;
            } else
            {
                System.out.println(MESSAGGIO_AMMISSIBILI + ammissibili);
            }
        } while (!finito);
        return valoreLetto;
    }

    public static int leggiIntero(String messaggio) {
        boolean finito = false;
        int valoreLetto = 0;
        do
        {
            System.out.print(messaggio);
            try
            {
                valoreLetto = lettore.nextInt();
                finito = true;
            } catch (InputMismatchException e)
            {
                System.out.println(ERRORE_FORMATO);
                String daButtare = lettore.next();
            }
        } while (!finito);
        return valoreLetto;
    }

    public static int leggiInteroPositivo(String messaggio) {
        return leggiInteroConMinimo(messaggio, 1);
    }

    public static int leggiInteroNonNegativo(String messaggio) {
        return leggiInteroConMinimo(messaggio, 0);
    }

    public static int leggiInteroConMinimo(String messaggio, int minimo) {
        boolean finito = false;
        int valoreLetto = 0;
        do
        {
            valoreLetto = leggiIntero(messaggio);
            if (valoreLetto >= minimo)
            {
                finito = true;
            } else
            {
                System.out.println(ERRORE_MINIMO + minimo);
            }
        } while (!finito);

        return valoreLetto;
    }

    public static int leggiIntero(String messaggio, int minimo, int massimo) {
        boolean finito = false;
        int valoreLetto = 0;
        do
        {
            valoreLetto = leggiIntero(messaggio);
            if (valoreLetto >= minimo && valoreLetto <= massimo)
            {
                finito = true;
            } else
            {
                if (valoreLetto < minimo)
                {
                    System.out.println(ERRORE_MINIMO + minimo);
                } else
                {
                    System.out.println(ERRORE_MASSIMO + massimo);
                }
            }
        } while (!finito);

        return valoreLetto;
    }

    public static double leggiDouble(String messaggio) {
        boolean finito = false;
        double valoreLetto = 0;
        do
        {
            System.out.print(messaggio);
            try
            {
                valoreLetto = lettore.nextDouble();
                finito = true;
            } catch (InputMismatchException e)
            {
                System.out.println(ERRORE_FORMATO);
                String daButtare = lettore.next();
            }
        } while (!finito);
        return valoreLetto;
    }

    public static double leggiDoubleConMinimo(String messaggio, double minimo) {
        boolean finito = false;
        double valoreLetto = 0;
        do
        {
            valoreLetto = leggiDouble(messaggio);
            if (valoreLetto >= minimo)
            {
                finito = true;
            } else
            {
                System.out.println(ERRORE_MINIMO + minimo);
            }
        } while (!finito);

        return valoreLetto;
    }

    public static boolean yesOrNo(String messaggio) {

        String mioMessaggio = messaggio + "(" + RISPOSTA_SI + "/" + RISPOSTA_NO + ")";
        char valoreLetto = leggiUpperChar(mioMessaggio, valueOf(RISPOSTA_SI) + valueOf(RISPOSTA_NO));

        if (valoreLetto == RISPOSTA_SI)
        {
            return true;
        } else
        {
            return false;
        }
    }
    
    public static GregorianCalendar getData(String messaggio){
    	int giorno;
    	int mese;
    	int anno;
    	int annoOdierno;
    	GregorianCalendar data = new GregorianCalendar();
    	GregorianCalendar oggi = new GregorianCalendar();
    	data.setLenient(false);
    	Boolean dataCorretta=false; 
    	
    	annoOdierno = data.get(Calendar.YEAR);
    	do{
	    	String dataString = leggiStringaNonVuota(messaggio + FORMATO_DATA + COLONNA);
	    	if (dataString.length()==LUNGHEZZA_CARATTERI_DATA && dataString.charAt(CHAR_SEPARATORE_DATA1) == '/' && dataString.charAt(CHAR_SEPARATORE_DATA2) =='/'){	    		
	    		giorno=Integer.parseInt(dataString.substring(0, CHAR_SEPARATORE_DATA1).toString());
	    		mese=Integer.parseInt(dataString.substring(CHAR_SEPARATORE_DATA1+1, CHAR_SEPARATORE_DATA2).toString())-1;
	    		anno=Integer.parseInt(dataString.substring(CHAR_SEPARATORE_DATA2+1).toString());	    		
	    		try {	    			
	    		data.set(anno,mese, giorno);
	    		data.getTime();
	    		if(data.getTimeInMillis()<=oggi.getTimeInMillis())
	    		dataCorretta=true;	    		
	    		else
	    			System.out.println(DATA_FUTURA_ERR + getData(oggi) + '.');	    			
	    		}
	    		catch (java.lang.IllegalArgumentException e){	    			
	    			dataCorretta=false;
	    			System.out.println(FORMATO_DATA_ERR); 
	    		}	    		
	    	}
	    	else
	    		System.out.println(FORMATO_DATA_ERR); 
	    		
    	}
    	while (!dataCorretta);
    	return data;    	
    }
    
    public static String getData(GregorianCalendar data){
    	StringBuffer stringBuffer = new StringBuffer();
    	
		int giorno= data.get(Calendar.DAY_OF_MONTH);
		int mese=data.get(Calendar.MONTH)+1;
		int anno=data.get(Calendar.YEAR);
		stringBuffer.append(giorno);
		stringBuffer.append(SEPARATORE);
		stringBuffer.append(mese);
		stringBuffer.append(SEPARATORE);
		stringBuffer.append(anno);
		return stringBuffer.toString();
    }
    
    public static String leggiStringaContenenteChar(String messaggio, char contenuta){
    	boolean finito = false;    	
        String stringaLetta;

        do
        {
        	stringaLetta = leggiStringaNonVuota(messaggio);

            if (stringaLetta.indexOf(contenuta) != -1)
            {
                finito = true;
            } else
            {
                System.out.println(FORMATO_ERR);
            }
        } while (!finito);
        return stringaLetta;    	
    }
    
    public static String leggiMail(String messaggio){
    	String stringaLetta;
    	boolean controllo = false;
    	
    	do{   	
	    	stringaLetta=leggiStringaContenenteChar(messaggio, '@');
	    	for (int i=0; i< DOMINIO_MAIL.length;i++){
	    		if(stringaLetta.endsWith(DOMINIO_MAIL[i])){
	    			controllo=true;
	    		}
	    	}
	    	if (!controllo)
                System.out.println(FORMATO_ERR);
    	}
    	while(!controllo);
    return stringaLetta;	
    }
    
}
