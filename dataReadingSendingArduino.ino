#include <string.h>
 // Selecciona el pin de entrada analoga a leer temperatura (°C).
 int tempPin = 5;
 // Selecciona el pin de entrada analoga a leer concentración de gas (ppm).
 int gasPint = 4;
 // Selecciona el pin de entrada analoga a leer iluminación (Lux).
 int luxPin = 3;
 // Selecciona el pin de entrada analoga a leer volumen del sonido (dB).
 int soundPin = 2;
 // variable para guardar el valor del sensor de temperatura.
 int tempC = 0;
 // variable para guardar el valor del sensor de gas.
 int gasC = 0;
 // variable para guardar el valor del sensor de iluminacion.
 int luxC = 0;
 // variable para guardar el valor del sensor de sonido.
 int soundC = 0;
 // arreglo de chars para envio final del dato del sensor.
 String tempArray[2] = {"", ""};
 // variable temporal de conteo
 int i = 0;
 // preparacion del proceso
 void setup() { 
   // Abre puerto serial y lo configura a 9600 bps
     Serial.begin(9600);
 }
 // ejecuta el procesamiento del sensor
 void loop() {
   // lee el valor del sensor de temperatura en Volts
   tempC = analogRead(tempPin);
   gasC = analogRead(gasPin);
   luxC = analogRead(luxPin);
   soundC = analogRead(soundPin);
   // se transforma el dato int de temperatura a un char
  // tempArray[0] = String(tempC);
   // Envia los datos uno por uno del arreglo del sensor por puerto serial
   //for (i=0; i<2; i++){
     // imprime el elemento del arreglo por el puerto serial
     //Serial.print(tempArray[i]);
     // espacio para diferenciar elementos en el arreglo
    // if (i < 1){
       //Serial.print("\t");      
     //}
   //}
   // final de la trama de datos
   Serial.println("");

   delay(1000);

 }
