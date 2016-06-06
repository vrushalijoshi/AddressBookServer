# AddressBookServer


## Description
AddressBook application performs basic CRUD operations to create, update, fetch and delete person’s details. This solution includes client and server projects. Person Details includes person’s basic information along with addresses, contact numbers and email addresses.

##	Software Used
-	MYSQL 5.6.0
-	Eclipse Mars
-	Maven 
-	Tomcat 8.0
-	Java 7

##	Projects and Details

### Service Project
Service project provides support for CRUD operations on person details record
#### Create Person Record Operation
This operation creates a person record with the provided details
- Input: Accepts the JSON format data (PersonDetails)
- Validations performed: 
--	Person's first, middle, name and last name is a required field.
--	Person's date of birth is a required field
--	At least one address should be provided to create a person's record 
--	At least one contact number should be provided to create a person's record 
--	At least one email address should be provided to create a person's record 
- Endpoint: POST http://localhost:8080/AddressBookServer/addAddressBookRecord

#### Update Person Record Operation
This operation updates person record contact details. Request should include person record id for which the record needs to be updated along with modified contact information. Only the changed fields will be updated
- Input: Accepts the JSON format data (PersonDetails)
- Validations performed: 
-- Check if the provided request is not null
-- If above is valid. Check if record identifiers are provided and are not null
- Endpoint: PUT http://localhost:8080/AddressBookServer/updateContactInformation

#### Delete Person Record Operation
This operation deletes a person’s record based on the provided identifier
- Input: Accepts identifier (UUID) as part of path parameter
- Validations performed: 
--	Check if not null value is provided as part of path parameter field.
--	If above is valid, validate if record for the provided identifier is present in database or not.
- Endpoint: DELETE http://localhost:8080/AddressBookServer/deleteAddressBookRecordById/a5762887-61b0-438b-9f40-9465f15d9f5e

#### Retrieve Person  Operation
This operation retrieves the a person’s  record based on the provided record identifier
- Input: Accepts identifier (UUID) as part of path parameter
- Validations performed: 
--	Record identifier should not be null
- Endpoint: GET http://localhost:8080/AddressBookServer/deleteAddressBookRecordById/a5762887-61b0-438b-9f40-9465f15d9f5e 

### Test Client Project
-	Test client testes all the CRUD operations provided by Address book service application
- Currently for testing purpose this test client executes service calls in following order - Creates a new person details record, Fetches the person details record based on the identifier returned by previous create call, updates the person’s contact information, Fetch the updated person details record, delete the person details record, call the fetch api to verify if the record was deleted successfully by the previous delete call.

###	Steps to make service ready
- Run maven build using command - mvn clean install
- Deploy the manifest to tomcat server
- Service should be ready for testing with base uri (http://localhost:8080/AddressBookServer/)

###	How to call test client
In order to test the client – 
-	Run maven build.
-	Run com.intuit.addressbook.client.service.AddressBookClient class as a java application

###	Possible Errors
| Error Message                                                        | Error Type |
|----------------------------------------------------------------------|------------|
| Invalid Request                                                      | ERROR      |
| Invalid name provided. First name and last name are required         | ERROR      |
| Email address not provided. At least one email address is required   | ERROR      |
| Email address is not valid                                           | ERROR      |
| Contact number not provided. At least one contact number is required | ERROR      |
| Contact number is not valid                                          | ERROR      |
| Id missing from delete request                                       | ERROR      |
| Address not provided. At least one address is required               | ERROR      |
| Invalid record id                                                    | ERROR      |
| Id missing from update request                                       | ERROR      |
| Record not found                                                     | WARNING    |
| Error occurred while persisting record                               | ERROR      |
| Error while persisting as record is already present                  | ERROR      |
| Error occurred as no record found                                    | ERROR      |
| Error occurred while deleting records                                | ERROR      |
| Error occurred while fetching record based on name and date of birth | ERROR      |
| Person id missing from the update request                            | ERROR      |
| Address id missing from the update request                           | ERROR      |
| Contact Number id missing from the update request                    | ERROR      |
| Email Address id missing from the update request                     | ERROR      |
| Date of Birth is mandatory field                                     | ERROR      |

###	Possible status code from service call
| Method call                 | Status Code Returned                                                                                             |
|-----------------------------|------------------------------------------------------------------------------------------------------------------|
| addAddressBookRecord        | 201: Successful creation of the person record, 400: Bad Request, 500: Internal Error, 409: Record already exists |
| updateAddressBookRecord     | 200: Successfully deleted the record 400: Bad Request, 500: Internal Error, 204: No content                      |
| getAddressBookRecordById    | 200: Successfully fetch the person record, 500: Internal Error, 400: Bad Request, 204 : No content               |
| deleteAddressBookRecordById | 200: Successful delete the person record, 400: Bad Request, 500: Internal Error, 204: No content                 |

