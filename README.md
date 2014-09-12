DataExtractor
=============

The project aims at designing an efficient data retieval process for large scalable  NOSQL datastores like Hbase.
It uses Rest service to retrieve the data from the database. The project  uses University database 
for example but is flexible enough to fit  in any system.

One can request student data by sending a request xml in POST method body with the variables required for a particular
student. Sample Input Xml :-

<request student_id="1234">
  <variables>
    <variable>STUDENT_NAME</variable>
    <variable>STUDENT_ADDRESS</variable>
    <variable>STUDENT_COURSES</variable>
    <variable>STUDENT_FEES_DUE</variable>
  </variables>
</request>

Student Id is the row key for Hbase datastore. 

Sample Output:-

<response>
<metaData>SUCCESS</metaData>
  <row student_id="1234">
   <columns>
    <column name="STUDENT_NAME" value="John" timestamp="1410511550 " />
    <column name="STUDENT_ADDRESS" value="los angeles" timestamp="1410511550" />
    <column name="STUDENT_COURSES" value="algorithms" timestamp="14105115770" />
    <column name="STUDENT_FEES_DUE" value="1660" timestamp="1410511580" />
   </columns>
  </row>
</response>

Hbase returns values in bytes so the  project uses the concept of in memory cache to maintain a column to data type
mapping and on the fly converts a byte value to corresponding data type and returns it.

All the project properties can be updated in extractor.properties file. 
