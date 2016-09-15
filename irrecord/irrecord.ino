
const long NDATA = 500;
byte data[NDATA];

void setup() {
  Serial.begin(9600);
  pinMode(9, OUTPUT);
}

void loop() {
    int data0 = analogRead(A0);
    if (!(data0 > 2))
      return;
  
    digitalWrite(9, HIGH);
  
  long startt = micros();
  
  int tooLate = 0;
  
  for (int n = 0; n < NDATA; ++n) {
    data[n] = (byte)(analogRead(A0));
    long nextt = startt + (140 * (n + 1));
    long now = micros();
    long wait = nextt - now;
    if (wait > 2){
      delayMicroseconds(wait);
      //lastRun = micros();
    }
    else {
      ++tooLate;
    }
  }
 

  Serial.print("");
  for (int i=0; i<NDATA; ++i) {
    Serial.print(data[i]);
    Serial.print(", ");
  }
  Serial.println("");
  Serial.print("too late ");
  Serial.println(tooLate);
  
    digitalWrite(9, LOW);
}
