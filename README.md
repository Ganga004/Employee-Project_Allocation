Intergrated Swagger, API Gateway, Service Registry, Microserivce communication using Feign client, Juni test cases, Zipkin Log tracing system, Email Notification in this application.

To set up in local, download and extract all the services and do basic configuration for email notifications.


Zipkin :  You need to setup zipkin in your local.
Zipkin URL: 	
http://127.0.0.1:9411/zipkin/


Swagger Link 
Employee Details Link : http://localhost:8080/swagger-ui/index.html#/

Project Allocaion Link : http://localhost:8081/swagger-ui/index.html#/


Employee : 
There are 4 apis in employee application 
1.Add employee (No need to add emp Id)
2.Get employee based on first & secondary Skills
3.Get second most experienced employee 
4.Get employee based on skill which is not matching with employee skill set


Project Allocation :
1.Get Project details to based on employee id
2.Add project details
3.Update employee details


Sample Json Data
{
    "message": "Primary Skills not Allocated for these Employees",
    "data": [
        {
            "employeeId": 1,
            "employeeName": "Madhan",
            "capabilityCentre": "DEP_QUALITY",
            "dateOfJoining": "05-03-2022",
            "designation": "ASSOC_ENGINEER",
            "primarySkill": "Ruby,Angular",
            "secondarySkill": "GO,HTML,JavaScript",
            "overallExperience": 1.5
        },
        {
            "employeeId": 2,
            "employeeName": "Raja",
            "capabilityCentre": "PRODUCT_AND_PLATFORM",
            "dateOfJoining": "08-07-2019",
            "designation": "ENGINEER",
            "primarySkill": "C++,.Net",
            "secondarySkill": "Java,CSS,Angular",
            "overallExperience": 1.2
        }
    ]
}



