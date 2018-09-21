package net.digitalbd.smarttorchlight;

import android.app.Activity;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class TorchLIght extends Activity {
    Button btnToggle,btnPushOn;
    ImageView displayLight;
    LinearLayout backGround;
    protected boolean torchOn = true;
    private Camera camera = Camera.open();
    private Parameters pr = camera.getParameters();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_torch_light);
        displayLight = (ImageView) findViewById(R.id.displayLight);
        btnToggle = (Button) findViewById(R.id.toggletorchbtn);
        btnPushOn = (Button) findViewById(R.id.btn_push_on);
        backGround = (LinearLayout) findViewById(R.id.fullscreen_content_controls);
        // flash on when app start
        do_Flash_On();
        /*
        * Toggle Light
        * */
        btnToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(torchOn){
                    v.setBackgroundResource(R.drawable.btn_0);
                    displayLight.setImageResource(R.drawable.torch0);
                    do_Flash_Off();
                    torchOn = false;
                }else{
                    do_Flash_On();
                    torchOn = true;
                    displayLight.setImageResource(R.drawable.torch1);
                    v.setBackgroundResource(R.drawable.btn_1);
                    //backGround.setBackgroundColor(Color.parseColor("#00ff00"));
                }
            }
        });
        /*
        * Push to light on
        * */
        btnPushOn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction() & MotionEvent.ACTION_MASK) {

                    case MotionEvent.ACTION_DOWN:
                        torchMakeOn(true);
                        v.setBackgroundResource(R.drawable.btn_1);
                        do_Flash_On();
                        // Start action ...
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_OUTSIDE:
                    case MotionEvent.ACTION_CANCEL:
                        torchMakeOff(true);
                        v.setBackgroundResource(R.drawable.btn_0);
                        do_Flash_Off();
                        // Stop action ...
                        break;
                    case MotionEvent.ACTION_POINTER_DOWN:
                        break;
                    case MotionEvent.ACTION_POINTER_UP:
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                }

                return true;
            }
        });
    }
    /*
    * Make On Torch
    * */
    public void torchMakeOn( boolean toggleBotton){
        displayLight.setImageResource(R.drawable.torch1);
        if(toggleBotton){
            btnToggle.setBackgroundResource(R.drawable.btn_0);
        }
        this.torchOn = true;
    }

    /*
    * Make Off Torch
    * */
    public void torchMakeOff(boolean toggleBotton){
        displayLight.setImageResource(R.drawable.torch0);
        if(toggleBotton){
            btnToggle.setBackgroundResource(R.drawable.btn_0);
        }
        this.torchOn = false;
    }

    public void do_Flash_On(){
        pr.setFlashMode(Parameters.FLASH_MODE_TORCH);
        camera.setParameters(pr);
        camera.startPreview();
        torchOn = true;
    }
    public void do_Flash_Off(){
        pr.setFlashMode(Parameters.FLASH_MODE_OFF);
        camera.setParameters(pr);
        camera.stopPreview();
        torchOn = false;
    }

}
