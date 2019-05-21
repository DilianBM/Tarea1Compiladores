CREATE TABLE A ( 
basura BOOLEAN  PRIMARY KEY


);
CREATE TABLE NOMBREDETABLA2 ( 
llave VARCHAR (255)  PRIMARY KEY,
variable VARCHAR (244) ,
,
PropiedadNum2 FLOAT  (0,0)
 C_FK VARCHAR () ,  FOREIGN KEY (C_FK) REFERENCES C(primaryK) 



);
CREATE TABLE C ( 
primaryK VARCHAR (244)  PRIMARY KEY,
,


 GInstanciaB_id  VARCHAR (45)  ,  FOREIGN KEY (GInstanciaB_id) REFERENCES NOMBREDETABLA2(llave) 

 B_id  VARCHAR (45)  ,  FOREIGN KEY (B_id) REFERENCES NOMBREDETABLA2(llave) 

);
CREATE TABLE basura_new ( 
clav VARCHAR (255)  PRIMARY KEY,
gg VARCHAR (255) ,
,
,
objeto BOOLEAN ,
valor VARCHAR (255) ,
,

 assS_id varchar () ,  FOREIGN KEY (assS_id) REFERENCES A(basura) 
 awss_id BOOLEAN ,  FOREIGN KEY (awss_id) REFERENCES NOMBREDETABLA2(llave) 
 assS_id varchar () ,  FOREIGN KEY (assS_id) REFERENCES A(basura) 
 awss_id BOOLEAN ,  FOREIGN KEY (awss_id) REFERENCES NOMBREDETABLA2(llave) 
 assS_id varchar () ,  FOREIGN KEY (assS_id) REFERENCES A(basura) 
 awss_id BOOLEAN ,  FOREIGN KEY (awss_id) REFERENCES NOMBREDETABLA2(llave) 
 assS_id varchar () ,  FOREIGN KEY (assS_id) REFERENCES A(basura) 
 awss_id BOOLEAN ,  FOREIGN KEY (awss_id) REFERENCES NOMBREDETABLA2(llave) 

 yyyy_id varchar () ,  FOREIGN KEY (yyyy_id) REFERENCES A(basura) 
 a2_id BOOLEAN ,  FOREIGN KEY (a2_id) REFERENCES NOMBREDETABLA2(llave) 


);
CREATE TABLE G ( 
llavePrimariaG BOOLEAN  PRIMARY KEY


);
CREATE TABLE H ( 
basya VARCHAR (255)  PRIMARY KEY


);
CREATE TABLE Tabla G_H (
llavePrimariaH VARCHAR (255) NOT NULL,
llavePrimariaG Boolean NOT NULL, 
PRIMARY KEY (llavePrimariaH,llavePrimariaG), 
FOREIGN KEY (llavePrimariaH) REFERENCES H(llavePrimariaH),
FOREIGN KEY (llavePrimariaG) REFERENCES G(llavePrimariaG)
);
