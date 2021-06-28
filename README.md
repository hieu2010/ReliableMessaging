# Introduction

This section presents the goal of the app and the laid down requirements.

## Reliable messaging

The goal of this project is to prepare an application that solves some 
fog-specific challenges. In particular, the focus of this project is on
reliable communication.

## Requirements

The following requirements have to be fulfiled:
 - the application consists of two components: a local component and a cloud component,
 - the local component has to simulate sensor data,
 - the components have to communicate with each other, i.e. the communication is bi-directional,
 - the communication between the components is frequent, i.e. the data transmission takes place several time a minute
 - upon a disconnection and/or crash, the components have to work and send the data when reconnected

# Implementation

This section describes the technology stack and the components together with their interactions.

## Tech Stack

Both **Local Component** and **Cloud Component** use Java 11, Spring Boot, and MongoDB. In addition, the **Server** runs in a VM on the Google Cloud Platform.

## Local Component

The local component has three main objectives: 
 - generate (simulate) sensor data
 - send the data to the Cloud Component
 - receive commands from the cloud

### Data Generation

The class _DataGenerator.java_ is responsible for creating data periodically. Every 200ms a random row from a weather table (see Section Weather Data below) is taken, and the row's content is saved into the MongoDB. The data from the csv contains various different measurements, and thus represents multiple sensors. As our target measurements we chose temperature and humidity. 

### Data Delivery

The class _DeliveryTaskWrapper.java_ is a wrapper for a _DeliveryTask.java_, which represents a periodical attempt to send the sensor data to the cloud. The logic behind the delivery taks is the following:
 * get data from the local MongoDB instance (max. 100 entries),
 * make an attempt to send the data (as a string) to the cloud in a HTTP POST request,
 * if succeeded, delete the data from the DB - a given set of measurements has been successfully processed,
 * if failed, retry.

## Cloud Component

The server components has two main objectives:
 - listen/receive data from the local component
 - send a command to the local component

### Cloud controller

The class _CloudController.java_ is responsible for exposing an endpoint to which data from local component is sent. 
Furthermore, it contains the logic that is responsible for extracting relevant information from a data string, checking the duplicates 
and saving received data to the database. After the controller has received and processed data from the local component, 
it sends the acknowledgement to the local component in a form of a string. 

The component check for duplicates in order to avoid processing the same data multiple times.

Moreover, the cloud component saves the receives data in the mongoDB in order to prepare commands for the local component in a separate process.

### Command delivery

The class _CommandTaskWrapper.java_ is a wrapper for a _CommandTask.java_, which represents a periodical attempt to send commands to the local component. Commands are based on the data from the local component and represent a specific action to undertake according to the average value of temperature and humidity.

# Ensuring reliable message delivery

This section collects some questions and answers concerning reliable message delivery

#### How is reliable messaging ensured?

Reliable messaging is warranted through the HTTP and the underlying TCP protocol. If the request was received and processed, a positive response code is returned to the sender. Until then, the sender would re-try until it was successful. Our components also do multithreading, so each component won't be blocked while attempting to send requests out: The local component would still gather weather data in the background, the cloud component would still listen to it's port in case new weather data is sent to it.

#### What happens if Local Component is disconnected?

The cloud component would still be working as is. Listening to it's port in case a request comes in and it would re-try to send the command to the client. Thanks to a database (MongoDB), the local component won't lose any weather data since weather data is being pulled from the database and only deleted if the weather data was successfully processed by the cloud component. The cloud component will also check for duplicates in case the local component crashed when a request was successful but the data wasn't removed from the database yet.

#### What happens if Cloud Component is disconnected?

The data generator still produces data and saves it in a MongoDB collection, but the data delivery task is stopped. Repeated attemps to send the originally read data are made. 
When the cloud component is reconnected, the original data is sent, the data is removed from the MongoDB collection and the delivery taks is restarted. We ensure that all weather data that was produced will be processed in the long run because the consumption rate is double of the production rate (client can send up to 100 data entries every 10 seconds whereas only 50 data entries are produced in that timeframe). Also the cloud component won't lose any of the stored weather data as well, since it also uses MongoDB to store all data.

#### What happens if MongoDB crashes?

We use MongoDB Atlas which currently provides this project with three available replicas and increases the availability of this whole setup.

#### How are tasks scheduled?

Mocked sensor data is generated every 200m using Spring scheduler. A devivery task is a scheduled task that can be stopped and started. This is needed in case when Cloud Component is dead. 


# Weather Data

Our reliable HTTP Client uses historical weather .csv data from https://meteostat.net/ to simulate sensor data. 
_time_, _temp_ and _rhum_ are send to the Cloud. In addition, _time_ is replaced with the actual time (Instant.now()).

### CSV Format: 
| time | time_local | temp | dwpt | rhum | prcp | snow | wdir | wspd | wpgt | pres | tsun | coco |
| ------------- | ------------- |------------- | ------------- | ------------- |------------- | ------------- | ------------- |------------- | ------------- | ------------- |------------- | ------------- |
| ... | ... |... | ... | ... |... | ... | ... |... | ... | ... |... | ... |
| ... | ... |... | ... | ... |... | ... | ... |... | ... | ... |... | ... |

### Dictionary:

| Parameter |	Description	 | Type |
| ------------- | ------------- |------------- |
time |	UTC time stamp (format: YYYY-MM-DD hh:mm:ss) |	String
time_local |	Local time stamp (format: YYYY-MM-DD hh:mm:ss); only provided if tz is set |	String
temp |	The air temperature in °C |	Float
dwpt |	The dew point in °C |	Float
rhum |	The relative humidity in percent (%) |	Integer
prcp |	The one hour precipitation total in mm |	Float
snow |	The snow depth in mm |	Integer
wdir |	The wind direction in degrees (°) |	Integer
wspd |	The average wind speed in km/h |	Float
wpgt |	The peak wind gust in km/h |	Float
pres |	The sea-level air pressure in hPa |	Float
tsun |	The one hour sunshine total in minutes (m) |	Integer
coco |	The weather condition code |	Integer

  
