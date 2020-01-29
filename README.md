## About SignNow

SignNow allows you to embed legally-binding e-signatures into your app, CRM or cloud storage. Send documents for signature directly from your website. Invite multiple signers to finalize contracts. Track status of your requests and download signed copies automatically.

This is a two module application which consists of the client-lib library and the example-app module. The client-lib library is used to interact with SignNow API. It covers all the authentication routines, request building and provides simple DTO objects to use in your applications. The second module, example-app, is a Spring Boot application which contains implementation examples of SignNow API use-cases.

Get your account at https://www.signnow.com/developers

#### API and Application
| Resources | Sandbox | Production |
| --- | --- | --- |
| API: | api-eval.signnow.com:443 | api.signnow.com:443 |
| Application: | https://app-eval.signnow.com | https://app.signnow.com |
| Entry page: | https://eval.signnow.com |

## Contents

- [Documentation](#documentation)
- [Get started](#get-started)
- [Examples](#examples)
- [Contribution guidelines](#contribution-guidelines)
- [License](#license)

## <a name="documentation"></a>Documentation

Read about the available SignNow features in [SignNow API Docs](https://docs.signnow.com) .

## <a name="get-started"></a>Get started
To start using the API you have to create a SignNow account [here](https://www.signnow.com/developers)

Make sure you have Maven installed. Checkout this repo, cd into repo root directory and run:
```
mvn clean install -pl client-lib
```
This will add client lib into your local Maven repo. Next, add a dependency to your app pom file:
```xml
<dependency>
    <groupId>com.signnow</groupId>
    <artifactId>api-client-lib</artifactId>
    <version>1.0.0-SNAPSHOT</version>
</dependency>   
```

In your code get SN API client instance by calling the next method with corresponding parameters:
```java
SNClientBuilder.get("apiUrl", "clientId", "clientSecret");
```

If you'd like to run an example application - set parameters named in the ```\example-app\src\main\resources\application.properties```
Feel free to try the provided examples and explore the corresponding code.

## <a name="examples"></a>Examples 
To run the examples you will need an API key. You can get one here https://www.signnow.com/api. For a full list of accepted parameters, refer to the SignNow REST Endpoints API guide: https://docs.signnow.com/reference.

**Note:** In the following examples we've listed only the common use-cases but this list shouldn't limit your requests. The client library provides an all-purpose wrapper and DTO objects for any API call to SignNow. Feel free to use any method from the client library.  

##### User:
* [Login](https://github.com/signnow/SignNowJavaAPiClient/blob/12de7d7c760229fa865a6550dc5113f86683d11c/example-app/src/main/java/com/signnow/examples/controller/Login.java#L37)

##### Document
* [Retrieve a List of the User’s Documents](https://github.com/signnow/SignNowJavaAPiClient/blob/12de7d7c760229fa865a6550dc5113f86683d11c/example-app/src/main/java/com/signnow/examples/controller/Login.java#L46)
* [Upload document](https://github.com/signnow/SignNowJavaAPiClient/blob/12de7d7c760229fa865a6550dc5113f86683d11c/example-app/src/main/java/com/signnow/examples/controller/Examples.java#L47)
* [Upload File & Extract Fields](https://github.com/signnow/SignNowJavaAPiClient/blob/12de7d7c760229fa865a6550dc5113f86683d11c/example-app/src/main/java/com/signnow/examples/controller/Examples.java#L406)
* [Update Document (add prefilled fields)](https://github.com/signnow/SignNowJavaAPiClient/blob/12de7d7c760229fa865a6550dc5113f86683d11c/example-app/src/main/java/com/signnow/examples/controller/Examples.java#L321)
* [Update Document (add fields)](https://github.com/signnow/SignNowJavaAPiClient/blob/12de7d7c760229fa865a6550dc5113f86683d11c/example-app/src/main/java/com/signnow/examples/controller/Examples.java#L439)
* [Create Free Form Invite](https://github.com/signnow/SignNowJavaAPiClient/blob/12de7d7c760229fa865a6550dc5113f86683d11c/example-app/src/main/java/com/signnow/examples/controller/Examples.java#L120)

##### Links
* [Create signing link](https://github.com/signnow/SignNowJavaAPiClient/blob/12de7d7c760229fa865a6550dc5113f86683d11c/example-app/src/main/java/com/signnow/examples/controller/Examples.java#L79)

##### Template
* [Create a Template](https://github.com/signnow/SignNowJavaAPiClient/blob/12de7d7c760229fa865a6550dc5113f86683d11c/example-app/src/main/java/com/signnow/examples/controller/Examples.java#L367)
* [Create Invite to Sign a Template for multiple signers](https://github.com/signnow/SignNowJavaAPiClient/blob/12de7d7c760229fa865a6550dc5113f86683d11c/example-app/src/main/java/com/signnow/examples/controller/Examples.java#L175)
* [Create Invite to Sign multiple Templates for multiple signers](https://github.com/signnow/SignNowJavaAPiClient/blob/12de7d7c760229fa865a6550dc5113f86683d11c/example-app/src/main/java/com/signnow/examples/controller/Examples.java#L248)


## <a name="contribution-guidelines"></a>Contribution guidelines
Many thanks to all the contributors who got interested in this project. We're excited to hear from you. Here are some tips to make our collaboration meaningful and bring its best results to life:

* We accept pull requests from the community. Please, send your pull requests to the **DEVELOP branch** which is the consolidated work-in-progress branch. We don't accept requests to the Master branch.

* Please, check in with the documentation first before you open a new Issue.

* When suggesting new functionality, give as many details as possible. Add a test or code example if you can.

* When reporting a bug, please, provide full system information. If possible, add a test that helps us reproduce the bug. It will speed up the fix significantly.


## <a name="license"></a>License

This SDK is distributed under the MIT License,  see [LICENSE](https://github.com/signnow/SignNowJavaAPiClient/blob/master/LICENSE) for more information.

#### API Contact Information
If you have questions about the SignNow API, please visit https://docs.signnow.com or email api@signnow.com.<br>
**Support**: To contact SignNow support, please email support@signnow.com or api@signnow.com.<br>
**Sales**: For pricing information, please call (800) 831-2050, email sales@signnow.com or visit https://www.signnow.com/contact.