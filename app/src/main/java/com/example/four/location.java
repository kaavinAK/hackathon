package com.example.four;

public class location {
    Weather[] weather;
    Main main;
    Wind wind;
    String name;
    Sys sys;
    public location(Weather[] weather,Main main,Wind wind,String name , Sys sys)
    {
        this.weather=weather;
        this.main=main;
        this.wind=wind;
        this.name=name;
        this.sys=sys;
    }

}
class Main
{
    double temp_min;
    double temp_max;
    double pressure;
    double humidity;
    public Main(double temp_min,double temp_max,double pressure,double humidity)
    {
 this.temp_min=temp_min;
 this.temp_max=temp_max;
 this.pressure=pressure;
 this.humidity=humidity;
    }
}
class Weather
{
    String main;
    String description;
    public Weather(String main,String description)
    {
        this.main=main;
        this.description=description;
    }
}
class Wind
{
    double speed;
    public Wind(double speed)
    {
        this.speed=speed;
    }
}

class Sys{
    double sunrise;
    double sunset;
    String country;
    public Sys(double sunrise,double sunset,String country)
    {
           this.sunrise=sunrise;
           this.sunset=sunset;
           this.country=country;
    }
}


/*{"coord":{"lon":80.2785,"lat":13.0878},
"weather":[{"id":701,"main":"Mist","description":"mist","icon":"50d"}],
"base":"stations",
"main":{"temp":301.14,"feels_like":305.69,"temp_min":301.14,"temp_max":301.14,
"pressure":1004,"humidity":83},
"visibility":5000,
"wind":{"speed":3.6,"deg":260},
"clouds":{"all":75},
"dt":1630123037,
"sys":{"type":1,"id":9218,"country":"IN","sunrise":1630110468,"sunset":1630155179},
"timezone":19800,
"id":1264527,
"name":"Chennai","cod":200}*/
