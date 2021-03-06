package com.mygdx.game.Sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Playscreen.Hud;
import com.mygdx.game.Playscreen.MyGdxGame;
import com.mygdx.game.Playscreen.PlayScreen;

/**
 * Created by com on 1/12/2559.
 */
public class item1 extends Item {
    private Array<TextureRegion> frame;
    private float stateTime;
   private Animation walk;


    public item1(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        frame = new Array<TextureRegion>();

        for (int i = 0; i < 2; i++) {
            frame.add(new TextureRegion(screen.getAtlas().findRegion("item1"), i * 40, 0, 40, 40));

            walk = new Animation(0.4f,frame);
        }
        stateTime = 0;
        setBounds(getX(),getY(),60/ MyGdxGame.PPM,60/MyGdxGame.PPM);
    }

    public void update(float dt){
        super.update(dt);
        setPosition(body.getPosition().x-getWidth()/2,body.getPosition().y-getHeight()/3);
        setRegion(walk.getKeyFrame(stateTime));
        body.setLinearVelocity(velocity);

    }
    @Override
    public void defineItem() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(),getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(15/MyGdxGame.PPM);

        fdef.filter.categoryBits = MyGdxGame.ITEM_BIT;
        fdef.filter.maskBits = MyGdxGame.GROUND_BIT |
                MyGdxGame.BRICK_BIT |
                MyGdxGame.ENEMY_BIT |
                MyGdxGame.OBJECT_BIT |
                MyGdxGame.BOY_BIT|
                MyGdxGame.ITEM_BIT;

        fdef.shape = shape;
        body.createFixture(fdef).setUserData(this);
    }

    @Override
    protected void use(Boy boy) {
        Hud.addScore(100);
        destroy();
    }

}
