package ru.samsung.gamestudio.managers;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;

import ru.samsung.gamestudio.objects.GameObject;
import static ru.samsung.gamestudio.GameSettings.*;

public class ContactManager {
    World world;
    public ContactManager(World world) {
        this.world=world;
        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                Fixture fixA=contact.getFixtureA();
                Fixture fixB=contact.getFixtureB();

                int cDef=fixA.getFilterData().categoryBits;
                int cDef2=fixB.getFilterData().categoryBits;

                if (cDef+cDef2==TRASH_BIT+SHIP_BIT||cDef+cDef2==TRASH_BIT+BULLET_BIT) {
                    // Нет двух пар степеней двойки, которые дают одинаковую сумму,
                    // поэтому вместо того огромного куска кода можно использовать это условие.
                    ((GameObject) fixA.getUserData()).hit();
                    ((GameObject) fixB.getUserData()).hit();
                } else if (cDef+cDef2==BONUS_BIT+SHIP_BIT) {
                    if (cDef==SHIP_BIT) ((GameObject) fixB.getUserData()).hit();
                    else ((GameObject) fixA.getUserData()).hit();
                }
            }

            @Override
            public void endContact(Contact contact) {

            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {

            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {

            }
        });
    }
}
