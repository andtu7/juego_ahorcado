/**
 * @(#)Ahorcado.java
 *
 * Ahorcado Applet application
 *
 * @author J.C.A.R.
 * @version 1.00 2014/4/3
 */
 
import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;

public class Ahorcado extends Applet
implements KeyListener{
	
	int NUMPALABRAS ;
	
	final int MAXINTENTOS = 5;
	
	// No deberíamos modificar el número máximo de intentos,
	// porque vamos a dibujar 5 "cosas" cuando se equivoque
	
	String palabra; // La palabra a adivinar
	StringBuffer intento; // Lo que el jugador 2 va consiguiendo
	String letras=""; // Las letras que se han probado
	
	int oportunidades; // El número de intentos permitido
	char letra; // Cada letra que prueba el jug. dos
	
	int i; // Para mirar cada letra, con "for"
	
	boolean acertado; // Si ha acertado alguna letra
	boolean terminado; // Si la partida ha terminado
	
	String datosPalabras []={"Veracruz","Queretaro","Guadalajara","Sonora",
							 "Sinaloa","Monterrey","Zacatecas","Guerrero",
							 "Toluca","Puebla","Morelos","Chihuahua",
							 "Durango","Tamaulipas","Tabasco","Chiapas",
							 "Yucatan","Oaxaca","Secreto","Historias",
							 "Mexico","Sombrerete","Minas","Michoacan",
							 "Humanidad","Limones","Guanabana","Sandia",
							 "Chicozapote","Fruta","Verdura","Aguacate",
							 "Papaya","Naranja","Mandarina","Lima",
							 "Platano","Jitomate","Mango","Persimo","Datil",
							 "Tomate","Calabaza","Elote","Chile",
							 "Epazote","Laurel","Cilantro","Perejil"
							};
							
	void PrimerFallo(Graphics g){ // Primer fallo: Dibujamos la "plataforma"
		g.setColor(Color.cyan);
		g.drawLine(20, 180, 120, 180);
	}
	
	void SegundoFallo(Graphics g){ // Segundo fallo: Dibujamos el "palo vertical"
		g.drawLine(100, 180, 100, 125);
	}
	
	void TercerFallo(Graphics g){ // Tercer fallo: Dibujamos el "palo superior"
		g.drawLine(100, 125, 70, 125);
	}
	
	void CuartoFallo(Graphics g){ // Cuarto fallo: Dibujamos la "cuerda"
		g.drawLine(70, 125, 70, 130);
	}
	
	void QuintoFallo(Graphics g){ // Quinto fallo: Dibujamos la "persona"
		int j;
		
		// Cabeza
		g.setColor(Color.yellow);
		g.drawOval(62, 130, 16, 16);
		
		// Tronco
		g.drawLine(70, 146, 70, 160);

		// Brazos
		g.drawLine(50, 150, 90, 150);

		// Piernas
		g.drawLine(70, 160, 60, 175);
		g.drawLine(70, 160, 80, 175);
                
                oportunidades = 5;
                

	}


public void init() {
	
	// Valores iniciales
	setSize(640,480);
	NUMPALABRAS = datosPalabras.length;
	i = (int) Math.round(Math.random() * NUMPALABRAS);
	palabra = datosPalabras[ i ];
	oportunidades = MAXINTENTOS;
	
	// Relleno con * y " " lo que ve Jug. 2
	intento = new StringBuffer(palabra);
	
	for (i=1; i<=palabra.length(); i++)
		
		if (palabra.charAt(i-1) == ' ' )
			intento.setCharAt(i-1, ' ');
			
		else
			intento.setCharAt(i-1, '*');
			
		terminado = false;
		requestFocus();
		addKeyListener(this);
	}
	
	
public void paint(Graphics g) {
    
	 Font font = new Font("Courier", Font.BOLD,24);
               
        //set font 
         g.setFont(font);
	// Primero borro el fondo en negro
	g.setColor( Color.black );
	g.fillRect( 0, 0, 639, 479 );
	
	// Digo cuantos intentos le quedan
	g.setColor(Color.white);
	g.drawString("Te quedan " + oportunidades +
		 		 " intentos",80, 20);

	// Le muestro como va
	g.drawString(intento.toString(),80, 50);

	// Muestro las letras probadas
	g.setColor(Color.yellow);
	g.drawString("Letras intentadas:" + letras,20, 80);

	// Y le pido otra letra
	g.drawString("Que letra?", 20, 100);

	// Dibujo lo que corresponde del "patibulo"
	if (oportunidades <= 4) PrimerFallo(g);
	if (oportunidades <= 3) SegundoFallo(g);
	if (oportunidades <= 2) TercerFallo(g);
	if (oportunidades <= 1) CuartoFallo(g);

	// Si se acabo: Le felicito o le digo cual era
	if ((oportunidades <= 0) || (palabra.equals(intento.toString()))) {
		terminado = true;
		if ( palabra.equals(intento.toString() ) ){
		
			g.drawString("Acertaste!",20, 300);
			init();
		}
		else{
			g.drawString("Lo siento. Era: " + palabra,20, 300);
			QuintoFallo(g);
		}
	}
}
public void keyTyped(KeyEvent e) {
	
	letra=e.getKeyChar();
	
	if (! terminado) {
		letras=letras+letra;
		acertado = false; // Miro a ver si ha acertado
		
	for (i=1; i<=palabra.length(); i++)
		if (Character.toLowerCase(letra) ==
			Character.toLowerCase(palabra.charAt(i-1))){
				intento.setCharAt(i-1, palabra.charAt(i-1) );
				acertado = true;
		}
		
		if ( ! acertado ) // Si falló, le queda un intento menos
			oportunidades --;
	}
	
	repaint();
	e.consume();
}

public void keyReleased(KeyEvent e) {}
public void keyPressed(KeyEvent e) {}

}

