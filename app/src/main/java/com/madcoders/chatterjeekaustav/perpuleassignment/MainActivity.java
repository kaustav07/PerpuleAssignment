package com.madcoders.chatterjeekaustav.perpuleassignment;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.madcoders.chatterjeekaustav.perpuleassignment.ui.audioActivity.AudioActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import su.levenetc.android.textsurface.Text;
import su.levenetc.android.textsurface.TextBuilder;
import su.levenetc.android.textsurface.TextSurface;
import su.levenetc.android.textsurface.animations.Alpha;
import su.levenetc.android.textsurface.animations.ChangeColor;
import su.levenetc.android.textsurface.animations.Delay;
import su.levenetc.android.textsurface.animations.Parallel;
import su.levenetc.android.textsurface.animations.Rotate3D;
import su.levenetc.android.textsurface.animations.Sequential;
import su.levenetc.android.textsurface.animations.ShapeReveal;
import su.levenetc.android.textsurface.animations.SideCut;
import su.levenetc.android.textsurface.animations.Slide;
import su.levenetc.android.textsurface.animations.TransSurface;
import su.levenetc.android.textsurface.contants.Align;
import su.levenetc.android.textsurface.contants.Pivot;
import su.levenetc.android.textsurface.contants.Side;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.welcomeview)
    TextSurface mTextSurface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mTextSurface.reset();
        animateTextView();
    }

    private void animateTextView() {
        AssetManager assetManager = getAssets();
        final Typeface robotoBlack = Typeface.createFromAsset(assetManager, "fonts/Android-101.ttf");
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setTypeface(robotoBlack);

        Text textWelcome = TextBuilder
                .create("Welcome")
                .setPaint(paint)
                .setSize(34)
                .setAlpha(0)
                .setColor(Color.RED)
                .setPosition(Align.SURFACE_CENTER).build();


        Text textBraAnies = TextBuilder
                .create("Press the")
                .setPaint(paint)
                .setSize(14)
                .setAlpha(0)
                .setColor(Color.RED)
                .setPosition(Align.BOTTOM_OF, textWelcome).build();

        Text textFokkenGamBra = TextBuilder
                .create(" button")
                .setPaint(paint)
                .setSize(14)
                .setAlpha(0)
                .setColor(Color.RED)
                .setPosition(Align.RIGHT_OF, textBraAnies).build();

        Text textTo = TextBuilder
                .create("To")
                .setPaint(paint)
                .setSize(24)
                .setAlpha(0)
                .setColor(Color.RED)
                .setPosition(Align.BOTTOM_OF, textBraAnies).build();

        Text textDaaiAnies = TextBuilder
                .create(" Start the")
                .setPaint(paint)
                .setSize(24)
                .setAlpha(0)
                .setColor(Color.BLUE)
                .setPosition(Align.RIGHT_OF, textTo).build();

        Text texThyLamInnie = TextBuilder
                .create("awesome journey of songs")
                .setPaint(paint)
                .setSize(18)
                .setAlpha(0)
                .setColor(Color.BLUE)
                .setPosition(Align.BOTTOM_OF, textTo).build();
        mTextSurface.play(
                new Sequential(
                        ShapeReveal.create(textWelcome, 750, SideCut.show(Side.LEFT), false),
                        new Parallel(ShapeReveal.create(textWelcome, 600, SideCut.hide(Side.LEFT), false), new Sequential(Delay.duration(300), ShapeReveal.create(textWelcome, 600, SideCut.show(Side.LEFT), false))),
                        new Parallel(new TransSurface(500, textBraAnies, Pivot.CENTER), ShapeReveal.create(textBraAnies, 1300, SideCut.show(Side.LEFT), false)),
                        Delay.duration(500),
                        new Parallel(new TransSurface(750, textFokkenGamBra, Pivot.CENTER), Slide.showFrom(Side.LEFT, textFokkenGamBra, 750), ChangeColor.to(textFokkenGamBra, 750,Color.BLUE)),
                        Delay.duration(500),
                        new Parallel(TransSurface.toCenter(textTo, 500), Rotate3D.showFromSide(textTo, 750, Pivot.TOP)),
                        new Parallel(TransSurface.toCenter(textDaaiAnies, 500), Slide.showFrom(Side.TOP, textDaaiAnies, 500)),
                        new Parallel(TransSurface.toCenter(texThyLamInnie, 750), Slide.showFrom(Side.LEFT, texThyLamInnie, 500))
                )
        );
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    public void startAudio(View view){
        startActivity(new Intent(this, AudioActivity.class));
    }
}
