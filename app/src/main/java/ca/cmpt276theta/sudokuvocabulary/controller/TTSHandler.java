package ca.cmpt276theta.sudokuvocabulary.controller;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.widget.Toast;

import java.util.Locale;


public class TTSHandler implements TextToSpeech.OnInitListener{
    private Context context;
    private TextToSpeech tts = null;

    public TTSHandler(Context context)
    {
        this.context = context;
        if (tts == null)
            tts = new TextToSpeech(context, this);
    }

    public void destroy()
    {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
    }
    public void speak(String speak, Locale locale)
    {
        if (tts != null) {
            tts.setLanguage(locale);
            tts.speak(speak, TextToSpeech.QUEUE_FLUSH, null, null);
        }
    }
    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int res = tts.setLanguage(Locale.US);
            if(res == TextToSpeech.LANG_MISSING_DATA || res == TextToSpeech.LANG_NOT_SUPPORTED)
                Toast.makeText(this.context, "TTS Initilization Failed!", Toast.LENGTH_LONG).show();
        }
        else
            Toast.makeText(this.context, "TTS Initilization Failed!", Toast.LENGTH_LONG).show();
    }
}
