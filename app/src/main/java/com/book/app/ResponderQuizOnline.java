package com.book.app;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.book.app.api_pojo.Palavra;
import com.book.app.api_pojo.Usuario;
import com.book.app.api_service.AlfabrinqueService;
import com.book.app.background.BackgroundService;
import com.book.app.data.AppDAO;
import com.book.app.pojo.EstadoUsuario;
import com.book.app.pojo.Questao;
import com.book.app.util.Shuffle;
import com.book.app.util.UsuarioEscolhido;
import com.book.app.util.UtilitarioUI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ResponderQuizOnline extends AppCompatActivity implements View.OnClickListener{

    private ProgressDialog dialog;
    private String shuffledWord;
    private Palavra palavra = new Palavra();
    private int length;
    private LinearLayout ll;
    private List<TextView> textViewsList = new ArrayList<>();
    private LinearLayout dropLayout;
    private ImageButton btnResponder;
    private Questao questao;
    private ImageView quizImage;
    private AppDAO dao;
    private int[] indexArray = new int[5];
    private int arrayLength;
    private int index = 1;
    public static final String CATEGORIA_EXTRA = "categoriaExtra";
    private int categoria;
    private UsuarioEscolhido usuarioEscolhidoPreferences;
    private EstadoUsuario estadoUsuario;
    private LinearLayout rootLayoutImg;
    private LinearLayout linearLayout;
    private int acertosCount = 0;
    private int errosCount = 0;
    private int questoesCount = 0;
    private long time = System.currentTimeMillis();
    // private TextView txtTimer;
    private int count = 0;
    private static int tileWidth = 0;
    private static TextView rootTextView;
    private boolean isTablet;
    private ImageButton btnApagar;

    Retrofit retrofit =  new Retrofit.Builder()
            .baseUrl(AlfabrinqueService.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    AlfabrinqueService service = retrofit.create(AlfabrinqueService.class);

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_responder_quiz);

        UtilitarioUI.hideSystemUI(getWindow());

        rootLayoutImg = (LinearLayout) findViewById(R.id.root_layout);

        rootLayoutImg.setBackgroundResource(R.drawable.teste_grama3);

        ll = (LinearLayout) findViewById(R.id.quiz_layout);
        dropLayout = (LinearLayout) findViewById(R.id.drop_layout);
        btnResponder = (ImageButton) findViewById(R.id.btnResponder);
        btnApagar = (ImageButton) findViewById(R.id.btn_apagar);

        carregarQuestao();

    }

    private void carregarQuestao() {
        if (btnResponder != null) {
            if (isTablet) {
                btnResponder.setImageResource(R.drawable.ic_play_circle_filled_black_24dp_quiz_tablet_disabled);
            } else {
                btnResponder.setImageResource(R.drawable.ic_play_circle_filled_black_24dp_quiz_disabled);
            }
            btnResponder.setEnabled(false);
        }

        if (btnApagar != null) {
            int count = 0;

            for (int i = 0; i < dropLayout.getChildCount(); i++) {
                LinearLayout lContainer = (LinearLayout) dropLayout.getChildAt(i);
                if (lContainer.getChildAt(0) != null) {
                    count++;
                }
            }

            if (count > 0) {
                btnApagar.setEnabled(true);
            } else {
                btnApagar.setEnabled(false);
            }
        }

        buscarPalavra();

        questao = new Questao();
        //questao.setImagemUrl(palavra.getImagem());
        questao.setQuestaoID(palavra.getId());
        questao.setRespostaCerta(palavra.getNome());

        shuffledWord = Shuffle.shuffleWord(questao.getRespostaCerta().toUpperCase());

        quizImage.setImageResource(questao.getImagemUrl());

        length = shuffledWord.length();

        tileWidth = 0;

        dropLayout.post(new Runnable() {
            @Override
            public void run() {
                tileWidth = dropLayout.getWidth() % length;
            }
        });

        Log.i("TEST", "WIDTH=>" + tileWidth);

        for (int i = 0; i < length; i++) {
            rootTextView = new TextView(this);
            rootTextView.setId(i);

            rootTextView.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD);

            rootTextView.setBackgroundResource(R.drawable.word_shape);
            rootTextView.setTextColor(ContextCompat.getColor(this, R.color.textViewTextColor));

            rootTextView.setGravity(View.TEXT_ALIGNMENT_CENTER);

            if (isTablet) {
                rootTextView.setTextSize(25);
                rootTextView.setPadding(20, 10, 20, 10);
            } else {
                rootTextView.setTextSize(30);
                rootTextView.setPadding(35, 20, 35, 20);
            }

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(new LinearLayoutCompat.LayoutParams(LinearLayoutCompat.LayoutParams.WRAP_CONTENT, LinearLayoutCompat.LayoutParams.MATCH_PARENT));

            params.setMargins(20, 10, 20, 10);

            rootTextView.setText(String.valueOf(shuffledWord.charAt(i)));

            rootTextView.setLayoutParams(params);

            ll.addView(rootTextView);

            //VERIFICAR
            rootTextView.setOnClickListener(this);
            rootTextView.setOnTouchListener(new MyTouchListener());

            textViewsList.add(rootTextView);
        }

        Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            public void run() {
                if (categoria == 1) {
                    if (questao.getSomUrl() != null) {
                        Uri som = Uri.parse(questao.getSomUrl());
                        MediaPlayer mp = MediaPlayer.create(ResponderQuizOnline.this, som);
                        mp.start();
                        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            public void onCompletion(MediaPlayer mp) {
                                mp.release();

                            }
                        });
                    }
                }

            }
        }, 1000);

        initDropTiles();
    }


    private void initDropTiles() {

        for (int i = 0; i < length; i++) {
            linearLayout = new LinearLayout(this);
            linearLayout.setId(i + 10);

            linearLayout.setBackgroundResource(R.drawable.word_tiles_shape);
            //linearLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent));
            // linearLayout.setPadding(40, 20, 40, 20);


            rootTextView.measure(0, 0);
            int width = rootTextView.getMeasuredWidth() + (int) (rootTextView.getMeasuredWidth() * 0.2);

            Log.i("SIZE", "WIDTH => " + width);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(new LinearLayoutCompat.LayoutParams(width, ViewGroup.LayoutParams.MATCH_PARENT));

            // LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(new LinearLayoutCompat.LayoutParams(180, ViewGroup.LayoutParams.MATCH_PARENT));
            //LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(new LinearLayoutCompat.LayoutParams(160, ViewGroup.LayoutParams.MATCH_PARENT));
            //params.setMargins(10, 20, 10, 20);
            params.setMargins(20, 10, 20, 20);
            linearLayout.setGravity(Gravity.CENTER);
            linearLayout.setLayoutParams(params);

            //VERIFICAR
            linearLayout.setOnDragListener(new MyDragListener());
            dropLayout.addView(linearLayout);
        }
    }

    public void apagarLetras(View view) {
        for (int i = 0; i < dropLayout.getChildCount(); i++) {
            LinearLayout lContainer = (LinearLayout) dropLayout.getChildAt(i);
            View v = lContainer.getChildAt(0);

            if (v != null) {
                lContainer.removeAllViews();
                LinearLayout.LayoutParams viewParams = new LinearLayout.LayoutParams(new LinearLayoutCompat.LayoutParams(LinearLayoutCompat.LayoutParams.WRAP_CONTENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT));
                TextView tv = (TextView) v;
                viewParams.setMargins(20, 10, 20, 10);
                tv.setLayoutParams(viewParams);

                tv.setBackgroundResource(R.drawable.word_shape);
                tv.setTextColor(ContextCompat.getColor(this, R.color.textViewTextColor));

                tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

                tv.setGravity(Gravity.CENTER);
                ll.addView(tv);
            }
        }
    }

    @Override
    public void onClick(View view) {

    }

    private class MyDragListener implements View.OnDragListener {

        @Override
        public boolean onDrag(View v, DragEvent event) {
            int action = event.getAction();

            View view = (View) event.getLocalState();

            switch (action) {
                case DragEvent.ACTION_DRAG_STARTED:
                    // do nothing
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    v.setBackgroundResource(R.drawable.word_tiles_shape_enter);
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    v.setBackgroundResource(R.drawable.word_tiles_shape);
                    break;
                case DragEvent.ACTION_DROP:

                    //View view = (View) event.getLocalState();
                    ViewGroup owner = (ViewGroup) view.getParent();
                    owner.removeView(view);

//                    if (owner.getId() == R.id.quiz_layout) {
//                        owner.removeView(view);
//                        dropLayout.addView(view);
//                        //view.setVisibility(View.VISIBLE);
//                    } else if (owner.getId() == R.id.drop_layout) {
//                        owner.removeView(view);
//                        ll.addView(view);
//                        //view.setVisibility(View.VISIBLE);
//                    }

                    //LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(new LinearLayoutCompat.LayoutParams(LinearLayoutCompat.LayoutParams.WRAP_CONTENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT));
                    //params.setMargins(10, 20, 10, 20);
                    //params.setMargins(20, 10, 20, 10);
                    //view.setLayoutParams(params);
                    // view.setPadding(10, 10, 10, 10);

                    LinearLayout layout = (LinearLayout) findViewById(v.getId());

                    if (layout != null) {
                        if (layout.getChildCount() == 0) {
                            LinearLayout.LayoutParams viewParams = new LinearLayout.LayoutParams(new LinearLayoutCompat.LayoutParams(LinearLayoutCompat.LayoutParams.MATCH_PARENT, LinearLayoutCompat.LayoutParams.MATCH_PARENT));
                            TextView tv = (TextView) view;
                            tv.setLayoutParams(viewParams);

                            tv.setBackgroundColor(ContextCompat.getColor(ResponderQuizOnline.this, android.R.color.transparent));
                            //view.setPadding(35, 20, 35, 20);
                            tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

                            tv.setGravity(Gravity.CENTER);

                            layout.addView(tv);

                            MediaPlayer mp = MediaPlayer.create(ResponderQuizOnline.this, R.raw.end_drag_sound);
                            mp.start();
                            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                public void onCompletion(MediaPlayer mp) {
                                    mp.release();

                                }
                            });

                        } else {
                            owner.removeView(view);
                            LinearLayout.LayoutParams viewParams = new LinearLayout.LayoutParams(new LinearLayoutCompat.LayoutParams(LinearLayoutCompat.LayoutParams.WRAP_CONTENT, LinearLayoutCompat.LayoutParams.MATCH_PARENT));
                            viewParams.setMargins(20, 10, 20, 10);
                            view.setLayoutParams(viewParams);
                            view.setBackgroundResource(R.drawable.word_shape);
                            ll.addView(view);
                        }
                    }

                    //linearLayout.addView(view);

                    view.setVisibility(View.VISIBLE);


                    int containerChildCount = ll.getChildCount();

                    Log.i("COUNT", "CHILD_COUNT => " + containerChildCount);

                    if (containerChildCount != 0) {
                        if (isTablet) {
                            btnResponder.setImageResource(R.drawable.ic_play_circle_filled_black_24dp_quiz_tablet_disabled);
                        } else {
                            btnResponder.setImageResource(R.drawable.ic_play_circle_filled_black_24dp_quiz_disabled);
                        }
                        btnResponder.setEnabled(false);
                    } else {
                        if (isTablet) {
                            btnResponder.setImageResource(R.drawable.ic_play_circle_filled_black_24dp_quiz_tablet);
                        } else {
                            btnResponder.setImageResource(R.drawable.ic_play_circle_filled_black_24dp_quiz);
                        }

                        btnResponder.setEnabled(true);
                    }

                    int count = 0;

                    for (int i = 0; i < dropLayout.getChildCount(); i++) {
                        LinearLayout lContainer = (LinearLayout) dropLayout.getChildAt(i);
                        if (lContainer.getChildAt(0) != null) {
                            count++;
                        }
                    }

                    if (count > 0) {
                        btnApagar.setEnabled(true);
                    } else {
                        btnApagar.setEnabled(false);
                    }

                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    v.setBackgroundResource(R.drawable.word_tiles_shape);
                default:
                    break;
            }
            return true;
        }
    }

    private final class MyTouchListener implements View.OnTouchListener {
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(
                        view);
                view.startDrag(data, shadowBuilder, view, 0);

                MediaPlayer mp = MediaPlayer.create(ResponderQuizOnline.this, R.raw.start_drag_sound);
                mp.start();
                mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    public void onCompletion(MediaPlayer mp) {
                        mp.release();

                    }
                });
                return true;
            } else {
                return false;
            }
        }
    }

    public void buscarPalavra() {
        Call<Palavra> call = service.buscarPalavra(Token.getInstance().getToken());

        /*call.enqueue(new Callback<Palavra>() {
            @Override
            public void onResponse(Call<Palavra> call, Response<Palavra> response) {
                if (response.isSuccessful()) {
                    palavra.setNome(response.body().getNome());
                    Toast.makeText(ResponderQuizOnline.this, response.body().getNome(), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(ResponderQuizOnline.this, "Não foi possivel encontrar Palavra :(", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Palavra> call, Throwable t) {
                Toast.makeText(ResponderQuizOnline.this, "Algum erro inesperado aconteceu :(", Toast.LENGTH_LONG).show();
            }
        });*/

        /*try {
            Palavra resultado = call.execute().body();
            Log.i("Retrofit", "Sucesso");
        } catch (IOException e) {
            Log.i("Retrofit", e.getMessage());
        }*/

        Intent intent = new Intent(ResponderQuizOnline.this, BackgroundService.class);
        startService(intent);
        int teste = 0;
    }

    public void enviarPalavra() {

    }
}
