package com.example.experimentationtest

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.experimentationtest.ui.theme.ExperimentationTestTheme
import com.google.firebase.installations.FirebaseInstallations
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings


class MainActivity : ComponentActivity() {
    val remoteConfig: FirebaseRemoteConfig = Firebase.remoteConfig
    val configSettings = remoteConfigSettings {
        minimumFetchIntervalInSeconds = 3600
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        remoteConfig.setConfigSettingsAsync(configSettings)
        remoteConfig.setDefaultsAsync(R.xml.remote_config_defaults)
        remoteConfig.fetchAndActivate()
        val buttonColor = remoteConfig.getString("button_color")
        val forceRefresh = true
        FirebaseInstallations.getInstance().getToken(forceRefresh)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("Installations", "Installation auth token: " + task.result?.token)
                } else {
                    Log.e("Installations", "Unable to get Installation auth token")
                }
            }
        enableEdgeToEdge()
        setContent {
            ExperimentationTestTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { _ ->
                    Greeting(
                        buttonColor = buttonColor,
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(buttonColor: String) {
    if (buttonColor.equals("red")){
        Button(onClick = {  }, colors = ButtonDefaults.buttonColors(containerColor = Color.Red) ) {
            Text("Click Me!")
        }
    } else if(buttonColor.equals("blue")) {
        Button(onClick = { }, colors = ButtonDefaults.buttonColors(containerColor = Color.Blue) ) {
            Text("Click Me!")
        }
    }
}
