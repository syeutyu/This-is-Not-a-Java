#include "MQ135.h"
//A0 MQ-7
//A3 MQ-2
//A5 MQ-135
#define ANALOGPIN A5    //  Define Analog PIN on Arduino Board
#define RZERO 206.85    //  Define RZERO Calibration Value2
MQ135 gasSensor = MQ135(ANALOGPIN);

void setup() {
  Serial.begin(9600);
  float rzero = gasSensor.getRZero();
  delay(3000);
  Serial.print("MQ135 RZERO Calibration Value : ");
  Serial.println(rzero);
  pinMode(A0,INPUT);
  pinMode(A3,INPUT);
  pinMode(13,OUTPUT);
}

void loop() {
  float ppm = gasSensor.getPPM();
  int mq2 = analogRead(A3);
  int mq7 = analogRead(A0);
  
  delay(1000);
  //digitalWrite(13,HIGH);
  Serial.print("CO2 ppm value : ");
  Serial.println(ppm);
  Serial.print("CO value : ");
  Serial.println(mq7);
  Serial.print("Gas value : ");
  Serial.println(mq2);
  if(ppm > 3)
  {
    Serial.println("HIGH");
    digitalWrite(13,HIGH);
  }
  else
  {
    digitalWrite(13,LOW);
    Serial.println("LOW");
  }
}
