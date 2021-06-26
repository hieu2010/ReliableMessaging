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

The local component has three objectives: 
 - generate (simulate) sensor data
 - send the data to the Cloud Component
 - ...

### Data Generation

The class _DataGenerator.java_ is responsible for creating data periodically. Every 200ms a random row from a weather table (see Section Weather Data below) is taken, and the row's content is saved into the MongoDB.

### Data Delivery

The class _DeliveryTaskWrapper.java_ is a wrapper for a _DeliveryTask.java_, which represents a periodical attempt to send the sensor data to the cloud. The logic behind the delivery taks is the following:
 * get data from the local MongoDB instance (max. 100 entries),
 * make an attempt to send the data (as a string) to the cloud in a HTTP POST request,
 * if succeeded, delete the data from the DB,
 * if failed, retry.

# FAQ

#### How is reliable messaging ensured?

Reliable messaging is warranted through the HTTP and the underlying TCP protocol. 

#### What happens if Local Component is disconnected?

Answer 2

#### What happens if Cloud Component is disconnected?

The data generator still produces data, but the data delivery task is stopped. Repeated attemps to send the originally read data are made. 
When the cloud component is reconnected, the original data is sent, the delivery taks is restarted. 

#### What happens if MongoDB crashes?

Dunno xd

#### How are tasks scheduled?

Mocked sensor data is generated every 200m using Spring scheduler. A devivery tasks is also a scheduled task that can be stopped and started. This is needed in case Cloud Component is dead. 

# Reliable HTTP Client

## Weather Data

Our reliable HTTP Client uses historical weather .csv data from https://meteostat.net/ to simulate sensor data.

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

  
