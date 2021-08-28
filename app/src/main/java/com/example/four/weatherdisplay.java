package com.example.four;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class weatherdisplay extends AppCompatActivity {
   private  String Latitude;
    private String Longitude;
       TextToSpeech mtts;
    private  Button say;
    private   TextView speechinput;
    private  TextView weatherdescription;
    private   TextView wind;
    private  TextView humidity;
    private  TextView pressure;
    private  TextView temperature;
    private  TextView tempminmax;
    private   TextView location;
    private  TextView coordinates;
    private  String descriptionvar;
    private   String windvar;
    private   String humidityvar;
    private   String pressurevar;
    private   String temperaturevar;
    private   String tempminmaxvar;
    private    String locationvar;
    private    String coordinatesvar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weatherdisplay);
        Intent intent = getIntent();
        say=findViewById(R.id.say);
        Latitude=String.valueOf(intent.getStringExtra("latitude"));
        Log.d("latfromprev", "onCreate: "+Latitude);
        Longitude=String.valueOf(intent.getStringExtra("longitude"));
        Log.d("insideweather", "onCreate: ");
        mtts= new TextToSpeech(weatherdisplay.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                Log.d("afterstatuc", "onInit: "+status);
                if(status==TextToSpeech.SUCCESS)
                {
                  int result=  mtts.setLanguage(Locale.ENGLISH);
                  if(result==TextToSpeech.LANG_MISSING_DATA || result==TextToSpeech.LANG_NOT_SUPPORTED)
                  {
                      Log.d("notlanguage", "onInit: ");
                  }
                  else
                  {
                      say.setEnabled(true);
                  }
                }
                else
                {
                    Log.d("canttalk", "onInit: ");
                }
            }
        });
        weatherdescription=findViewById(R.id.weatherdescription);
        wind = findViewById(R.id.wind);
        humidity=findViewById(R.id.humidity);
        pressure=findViewById(R.id.pressure);
        temperature=findViewById(R.id.tempdata);
        tempminmax=findViewById(R.id.mtemp);
        location=findViewById(R.id.location);
        coordinates=findViewById(R.id.coo);
        startapp();
        speechinput=findViewById(R.id.speechinput);
        say.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speak();
            }
        });

    }
    private void speak()
    {
        String text=speechinput.getText().toString();
        Log.d("inputspeech", "speak: "+text);
        if(text.equals("pressure"))
        {
            mtts.speak("the pressure is "+pressurevar+" mega pascals",TextToSpeech.QUEUE_FLUSH,null);
        }
        else if(text.equals("temperature"))
        {
            mtts.speak("the temperature is "+temperaturevar+"fahrenheit",TextToSpeech.QUEUE_FLUSH,null);
        }
        else if(text.equals("currently"))
        {
            mtts.speak(descriptionvar,TextToSpeech.QUEUE_FLUSH,null);

        }
        else if(text.equals("wind"))
        {
            mtts.speak("wind speed is "+windvar+" kilometers per hour",TextToSpeech.QUEUE_FLUSH,null);

        }
       else if(text.equals("humidity"))
        {
            mtts.speak("humidity in air is "+humidityvar+" percent ",TextToSpeech.QUEUE_FLUSH,null);

        }
       else if(text.equals("Location"))
        {
            mtts.speak("location is "+locationvar,TextToSpeech.QUEUE_FLUSH,null);

        }
      else  if(text.equals("Min/Max"))
        {
            mtts.speak("the temperature range is "+tempminmaxvar,TextToSpeech.QUEUE_FLUSH,null);
        }
      else  if(text.equals("Coordinates"))
        {
            mtts.speak(" your coordinates are "+coordinatesvar,TextToSpeech.QUEUE_FLUSH,null);

        }
        else
        {
            Toast toast = new Toast(this);
            toast.setText("invalid input and also avoid white psaces");
            toast.show();
        }

    }

    @Override
    protected void onDestroy() {
        if(mtts!=null)
        {
            mtts.stop();
            mtts.shutdown();
        }
        super.onDestroy();
    }

    public void  startapp()
    {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.openweathermap.org/data/2.5/").addConverterFactory(GsonConverterFactory.create()).build();
        APIcaller apicaller = retrofit.create(APIcaller.class);
        Call<location> call = apicaller.getlocation(String.valueOf(Latitude),String.valueOf(Longitude),"5b5bdf2962d3619abd5c4a0ba8974681");
        call.enqueue(new Callback<location>() {
            @Override
            public void onResponse(Call<location> call, Response<location> response) {
                Log.d("inside the response", "onResponse: ");
                if(!response.isSuccessful())
                {
                    Log.d("notsuccess", "onResponse: "+response.message());
                    Log.d("notsuccess", "onResponse: "+response.headers());
                    return ;
                }
                location data = response.body();
              //  Log.d("city", "onResponse: "+data.name);
                Log.d("nameofcity", "onResponse: "+data.weather[0].description);
                descriptionvar=data.weather[0].description;
                windvar=String.valueOf(data.wind.speed);
                humidityvar=String.valueOf((int)data.main.humidity);
                pressurevar=String.valueOf((int)data.main.pressure);
                locationvar=String.valueOf(data.name);
                coordinatesvar=String.valueOf(Latitude)+"latitude and "+String.valueOf(Longitude) +" longitude ";
                temperaturevar=String.valueOf((int)(data.main.temp_min+data.main.temp_max)/2);
                tempminmaxvar=String.valueOf((int)data.main.temp_max)+" fahrenheit  and "+String.valueOf((int)data.main.temp_min)+" fahrenheit ";
                weatherdescription.setText(data.weather[0].description);
                wind.setText(String.valueOf((int)data.wind.speed)+"Km/h");
                humidity.setText(String.valueOf((int)data.main.humidity));
                pressure.setText(String.valueOf((int)data.main.pressure)+" mb");
                temperature.setText(String.valueOf((int)(data.main.temp_min+data.main.temp_max)/2)+" F");
                tempminmax.setText(String.valueOf((int)data.main.temp_max)+"F/"+String.valueOf((int)data.main.temp_min)+"F");
                location.setText(String.valueOf(data.name));
                coordinates.setText(String.valueOf(Latitude)+" / "+String.valueOf(Longitude));



            }

            @Override
            public void onFailure(Call<location> call, Throwable t) {
                Log.d("error ", "onFailure: "+t.getMessage());
            }
        });
    }
}