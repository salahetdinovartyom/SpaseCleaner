package ru.samsung.gamestudio;

import com.badlogic.gdx.utils.TimeUtils;

import static ru.samsung.gamestudio.GameSettings.*;

public class GameSession {
    long sessionStartTime,nextTrashSpawnTime;


    public void startGame() {
        sessionStartTime=TimeUtils.millis();
        nextTrashSpawnTime=sessionStartTime+(long) (STARTING_TRASH_APPEARANCE_COOL_DOWN*getTrashPeriodCoolDown());
    }
    public boolean shouldSpawnTrash() {
        if (nextTrashSpawnTime<=TimeUtils.millis()) {
            nextTrashSpawnTime=TimeUtils.millis()+(long) (STARTING_TRASH_APPEARANCE_COOL_DOWN*getTrashPeriodCoolDown());
            return true;
        }
        return false;
    }
    private float getTrashPeriodCoolDown() {
        return (float) Math.exp(-0.001*(TimeUtils.millis()-sessionStartTime)/1000);
    }

}
