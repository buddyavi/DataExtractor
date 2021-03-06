DataExtractor
=============

The project aims at designing an efficient data retieval process for large scalable  NOSQL datastores like Hbase.
It uses Rest service to retrieve the data from the database. The project  uses University database 
for example but is flexible enough to fit  in any system.

One can request student data by sending a request xml in POST method body with the variables required for a particular
student. Sample Input Xml :-

&lt;request student_id=&apos;1234&apos; &gt;<br>
  &lt;variables&gt;<br>
    &lt;variable&gt;STUDENT_NAME&lt;/variable&gt;<br>
    &lt;variable&gt;STUDENT_ADDRESS&lt;/variable&gt;<br>
    &lt;variable&gt;STUDENT_COURSES&lt;/variable&gt;<br>
    &lt;variable&gt;STUDENT_FEES_DUE&lt;/variable&gt;<br>
  &lt;/variables&gt;<br>
&lt;/request&gt;<br>

Student Id is the row key for Hbase datastore. 

Sample Output:-

&lt;response&gt;<br>
&lt;metaData&gt;SUCCESS&lt;/metaData&gt;<br>
  &lt;row student_id=&quot;1234&quot;&gt;<br>
   &lt;columns&gt;<br>
    &lt;column name=&quot;STUDENT_NAME&quot; value=&quot;John&quot; timestamp=&quot;1410511550 &quot; /&gt;<br>
    &lt;column name=&quot;STUDENT_ADDRESS&quot; value=&quot;los angeles&quot; timestamp=&quot;1410511550&quot; /&gt;<br>
    &lt;column name=&quot;STUDENT_COURSES&quot; value=&quot;algorithms&quot; timestamp=&quot;14105115770&quot; /&gt;<br>
    &lt;column name=&quot;STUDENT_FEES_DUE&quot; value=&quot;1660&quot; timestamp=&quot;1410511580&quot; /&gt;<br>
   &lt;/columns&gt;<br>
  &lt;/row&gt;<br>
&lt;/response&gt;<br>

Hbase returns values in bytes so the  project uses the concept of in memory cache to maintain a column to data type
mapping and on the fly converts a byte value to corresponding data type and returns it.

All the project properties can be updated in extractor.properties file. 
