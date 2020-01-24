## About SignNow

SignNow allows you to embed legally-binding e-signatures into your app, CRM or cloud storage. Send documents for signature directly from your website. Invite multiple signers to finalize contracts. Track status of your requests and download signed copies automatically.

This is a two module application, where client-lib is a library which intended to use for the SignNow API use, taking all the routines of authentication, request building and provides simple DTO objects to use in your applications. Second module, named example-app, is a Spring Boot application which contains example implementation of use-cases of SignNow API with use of introduced client library.

Get your account at https://www.signnow.com/developers

#### API Contact Information
If you have questions about the SignNow API, please visit https://docs.signnow.com or email api@signnow.com.<br>
**Support**: To contact SignNow support, please email support@signnow.com or api@signnow.com.<br>
**Sales**: For pricing information, please call (800) 831-2050, email sales@signnow.com or visit https://www.signnow.com/contact.

#### API and Application
| Resources | Sandbox | Production |
| --- | --- | --- |
| API: | api-eval.signnow.com:443 | api.signnow.com:443 |
| Application: | https://app-eval.signnow.com | https://app.signnow.com |
| Entry page: | https://eval.signnow.com |

#### Examples 
To run the examples you will need an API key. You can get one here https://www.signnow.com/api. For a full list of accepted parameters, refer to the SignNow REST Endpoints API guide: https://docs.signnow.com/reference.

## Contents

- [Documentation](#documentation)
- [Get started](#get-started)
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
This will add client lib into your local maven repo. Next add a dependency to your app pom file:
```xml
<dependency>
    <groupId>com.signnow</groupId>
    <artifactId>api-client-lib</artifactId>
    <version>1.0.0-SNAPSHOT</version>
</dependency>   
```

In your code get SN API client instance by calling next method with corresponding parameters:
```java
SNClientBuilder.get("apiUrl", "clientId", "clientSecret");
```

If you wish to run an example application - set parameters named in the ```\example-app\src\main\resources\application.properties```
Feel free to try provided examples and explore corresponding code.


## <a name="contribution-guidelines"></a>Contribution guidelines
Thanks to all contributors who got interested in this project. We're excited to hear from you. Here are some tips to make our collaboration meaningful and bring its best results to life:

* We accept pull requests from the community. Please, send your pull requests to the **DEVELOP branch** which is the consolidated work-in-progress branch. We don't accept requests to the Master branch.

* Please, check in with the documentation first before you open a new Issue.

* When suggesting new functionality, give as many details as possible. Add a test or code example if you can.

* When reporting a bug, please, provide full system information. If possible, add a test that helps us reproduce the bug. It will speed up the fix significantly.


## <a name="license"></a>License

This SDK is distributed under the MIT License,  see [LICENSE](https://github.com/signnow/SignNowJavaAPiClient/blob/master/LICENSE) for more information.
