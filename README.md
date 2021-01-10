# Excel-Web-Query

A library for web queries as used by Microsoft Excel. 

## What is a web query and what is this library for?

A web query is a file that tells Microsoft Excel where and how to fetch data from the web.
This library lets you create web queries in the appropriate format. This allows your applications
to wrap URLs in a way that Microsoft Excel understands. Microsoft Excel can then import data by querying
these URLs.

## How to use web queries in Microsoft Excel

- Create a web query and save it with the file extension "iqy"
- Open Microsoft Excel, go to the "Data" menu, select "Get External Data" and
  "Run Web Query" / "Run Saved Query", select your web query file and import it
- Microsoft Excel will now automatically import the response from the URL in your web
  query whenever it imports external data

## How to use this library

Add Jitpack as a repository to your project and add a dependency for excel-web-query in order to
use this library (the code snippet below uses Gradle's Kotlin DSL):
```
repositories {
    maven { url = uri("https://jitpack.io") }
}

dependencies {
    implementation("com.github.spartusch:excel-web-query:latest.release")
}
```

The actual usage of the library is straightforward:
Create a new `WebQuery` by calling `WebQueryFactory.create`.
This library is written in Kotlin but tested with Java and Kotlin.

### Example on how to create a `ResponseEntity` for Spring MVC

#### Java
```
final var webQuery = WebQueryFactory.create(uri);
final var headers = new HttpHeaders();

headers.set(HttpHeaders.CONTENT_TYPE, webQuery.getContentType());
headers.set(HttpHeaders.CONTENT_LENGTH, String.valueOf(webQuery.getContentLength()));
headers.set(HttpHeaders.CONTENT_DISPOSITION, WebQuery.getContentDisposition("webquery.iqy"));

final var response = new ResponseEntity<>(new ByteArrayResource(webQuery.getContentBytes()), headers, HttpStatus.OK);
```

#### Kotlin
```
val webQuery = WebQueryFactory.create(uri)
val headers = HttpHeaders()

headers[HttpHeaders.CONTENT_TYPE] = webQuery.contentType
headers[HttpHeaders.CONTENT_LENGTH] = webQuery.contentLength.toString()
headers[HttpHeaders.CONTENT_DISPOSITION] = WebQuery.getContentDisposition("webquery.iqy")

val response = ResponseEntity(ByteArrayResource(webQuery.contentBytes), headers, HttpStatus.OK)
```

## How to build and publish this library

Build this library with Gradle and publish it, e.g. locally:
```
./gradlew publishToMavenLocal
```

You can also build a base image for Docker:
```
./bin/build-image.sh
```
