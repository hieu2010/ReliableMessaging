# ReliableMessaging
Reliable Messaging between Client - Server



# Data Generator

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

  
