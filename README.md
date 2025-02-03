Intergrated Swagger, API Gateway, Service Registry, Microserivce communication using Feign client, Juni test cases, Zipkin Log tracing system, Email Notification in this application.

To set up in local, download and extract all the services and do basic configuration for email notifications.


**Zipkin :** You need to setup zipkin in your local.
[Zipkin-localhost URL](http://127.0.0.1:9411/zipkin/) 	

**Swagger Links:**  
[Swagger - Employee localhost Link](http://localhost:8080/swagger-ui/index.html#/) 

[Swagger - Project Allocaion localhost Link](http://localhost:8081/swagger-ui/index.html#/)


**Employee :** 
There are 4 apis in employee application 
1.  Add employee (No need to add emp Id)
2.  Get employee based on first & secondary Skills
3.  Get second most experienced employee 
4.  Get employee based on skill which is not matching with employee skill set


**Project Allocation :**
1.  Get Project details to based on employee id
2.  Add project details
3.  Update employee details


**Sample Data**
**Employee Json Data:**
```json
{
    "employeeName": "Muthu",
    "capabilityCentre": "product and platform",  // Assuming it's an enum
    "dateOfJoining": "08-03-2014",
    "designation": "engineer",  // Assuming it's an enum
    "primarySkill": "C++,Gobal,.Net",
    "secondarySkill": "Java,CSS,Angular",
    "overallExperience":4
}
```
**Project Json Data:**
```json
{
    "empId":3,
    "accountName":"Paypal",
    "projectName":"Developer",
    "allocation":0.5,
    "projectStartDate":"20-12-2022",
    "projectEndDate":"02-12-2025",
    "remarks":"Good Work"

}
```



