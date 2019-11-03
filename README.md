# hoolah
hoolah Coding Challenge

The main class is TransactionAnalyser.

It can be compiled and run using: ``mvn exec:java``

The test class is TransactionAnalyserTest

It can be compiled and run using: ``mvn test``

The input data file and other input properties can be modified in pom.xml, so as to not hardcode properties and recompile repeatedly.

``<properties> 
<dataFile>C:\Users\shari\eclipse-workspace\hoolah\src\main\resources\data.csv</dataFile> 
<fromDate>20/08/2018 12:00:00</fromDate> 
<toDate>20/08/2018 13:00:00</toDate> 
<merchant>Kwik-E-Mart</merchant> 
</properties>``
